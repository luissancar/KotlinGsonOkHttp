package com.example.luissancar.kotlingsonokhttp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.gson.Gson
import java.io.FileReader
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




    }

    fun objetoAJson(v: View){
        val guitarra = Guitarra("Fender","Stratocaster",1500)
        val gson=Gson()
        val json:String = gson.toJson(guitarra)
        Log.d("RESULTADO", json)

    }

    fun jsonAObjeto(v :View){

        val gson=Gson()
        val json:String="{\"marca\":\"Gibson\",\"modelo\":\"SG\",\"precio\":1700}"
        val guitarra:Guitarra=gson.fromJson(json,Guitarra::class.java)
        Log.d("RESULTADO", guitarra.toString())

    }



    fun ficheroJsonObjeto( v: View){
        val gson=Gson()
        val json= leerFichero("guitarras.json")

        val guitarra = gson.fromJson(json,GuitarraArray::class.java)


        for (item in guitarra.guitarras!!.iterator()){
              Log.d("RESULTADO", item.marca)
        }
    }

    fun leerFichero(fichero: String): String {
        var stringFichero=""
        try {
            val stream = assets.open(fichero)
            val tamano = stream.available()
            val buffer = ByteArray(tamano)
            stream.read(buffer)
            stream.close()
            stringFichero= String(buffer)

        }
        catch (e: IOException){

        }
        return stringFichero

    }
}
