package com.example.s_tarea_1

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.s_tarea_1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var positionSeleccionada: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val opciones = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20","21",
            "22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, opciones)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                positionSeleccionada = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }


        binding.btnCalcular.setOnClickListener{
            val seed = binding.edtNum.text.toString()
            if(seed.isNotEmpty()){
                binding.tvMostrar.text = ""
                if(seed.length > 3){
                    val arregloCadenas = Array<String>(51) { "" }
                    arregloCadenas[0] = seed

                    for (i in 0..positionSeleccionada){
                        val operation = arregloCadenas[i].toLong() * arregloCadenas[i].toLong()
                        Log.d("operation", "$operation")
                        val digit = operation.toString().length.toString()
                        if(digit.toInt() % 2 == 0){
                            val digitMedium = digitosMedios(operation.toString(),arregloCadenas[i])
                            binding.tvMostrar.append("Y${i} = (${arregloCadenas[i]})²  X${i+1} = ${digitMedium}  r${i+1} = 0.${digitMedium}\n")
                            arregloCadenas[i+1] = digitMedium
                        }else{
                            val digitMedium = digitosMedios(operation.toString(),arregloCadenas[i])
                            binding.tvMostrar.append("Y${i} = (${arregloCadenas[i]})²  X${i+1} = ${digitMedium}  r${i+1} = 0.${digitMedium}\n")
                            arregloCadenas[i+1] = digitMedium
                        }

                        if (isOnlyZeros(arregloCadenas[i+1])) {
                            break
                        }
                    }
                }else{
                    Toast.makeText(this, "El número debe ser mayor de 3 digitos", Toast.LENGTH_SHORT).show()
                }

                //Ocultar teclado
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            }
        }
    }

    fun digitosMedios(cadena: String, seed: String): String {

        val longitudCadena = cadena.length
        val longitudSeed = seed.length

        var cadenaTransformada = cadena

        if (longitudSeed % 2 == 0) {
            // Seed par, cadena resultado debe ser par
            if (longitudCadena % 2 != 0) {
                // Cadena impar, le agrego 0
                cadenaTransformada = "0$cadena"
            }
        }
        else {
            // Seed impar, cadena resultado debe ser impar
            if (longitudCadena % 2 == 0){
                // Cadena par, le agrego 0
                cadenaTransformada = "0$cadena"
            }
        }

        val corteInicio = (cadenaTransformada.length - longitudSeed) / 2
        val corteFin = corteInicio + longitudSeed

        return cadenaTransformada.substring(corteInicio, corteFin)

    }

    //

    fun isOnlyZeros(text: String): Boolean {
        val sum = text.sumBy {
            if (it.isDigit()) {
                it.digitToInt()
            } else {
                0
            }
        }

        return sum == 0
    }
}