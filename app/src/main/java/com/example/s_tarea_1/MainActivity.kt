package com.example.s_tarea_1

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.s_tarea_1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnCalcular.setOnClickListener{
            binding.tvMostrar.text = ""
            val seed = binding.edtNum.text.toString().toLong()
            if(seed.toString().length > 3){
                val arregloCadenas = Array<String>(8) { "" }
                arregloCadenas[0] = seed.toString()

                for (i in 0..6){
                    val operation = arregloCadenas[i].toLong() * arregloCadenas[i].toLong()
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
                }
            }else{
                Toast.makeText(this, "El número debe ser mayor de 3 digitos", Toast.LENGTH_SHORT).show()
            }

            //Ocultar teclado
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }

    //
    fun digitosMedios(cadena: String, seed: String):String{
        val longitud = cadena.length
        val longitudSeed = seed.length

        if(longitud % 2 == 0){
            if(longitudSeed % 2 != 0){
                val cadenaOficial = cadena.padStart(longitud + 1, '0')
                val longitudOficial = cadenaOficial.length
                val corteInicio = (longitudOficial - longitudSeed) / 2
                Log.d("corteInicio", "$corteInicio")
                val corteFin = corteInicio + longitudSeed
                Log.d("corteFin", "$corteFin")

                Log.d("cadena", "$cadenaOficial")
                return cadenaOficial.substring(corteInicio,corteFin)
            }else{
                val corteInicio = (longitud - longitudSeed) / 2
                val corteFin = corteInicio + longitudSeed

                return cadena.substring(corteInicio,corteFin)
            }
        }else{
            if(longitudSeed % 2 != 0 ){
                val corteInicio = (longitud - longitudSeed) / 2
                Log.d("corteInicio", "$corteInicio")
                val corteFin = corteInicio + longitudSeed
                Log.d("corteFin", "$corteFin")

                Log.d("cadena", "$cadena")
                return cadena.substring(corteInicio,corteFin)
            }else{
                val cadenaOficial = cadena.padStart(longitud + 1, '0')
                val longitudOficial = cadenaOficial.length
                val corteInicio = (longitudOficial - longitudSeed) / 2
                Log.d("corteInicio", "$corteInicio")
                val corteFin = corteInicio + longitudSeed
                Log.d("corteFin", "$corteFin")

                Log.d("cadena", "$cadenaOficial")
                return cadenaOficial.substring(corteInicio,corteFin)
            }
        }
    }
}