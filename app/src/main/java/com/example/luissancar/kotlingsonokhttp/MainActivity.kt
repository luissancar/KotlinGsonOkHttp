package com.example.luissancar.kotlingsonokhttp
////

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.gson.Gson
import java.io.IOException
import java.net.URL
import android.os.StrictMode
import android.text.method.ScrollingMovementMethod
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        textView.setMovementMethod(ScrollingMovementMethod())


    }

    fun insertar(v : View){
        if ((editTextMarca.length()>0) and (editTextModelo.length()>0) and (editTextPrecio.length()>0)  ) {
            val url = "http://iesayala.ddns.net/json/insertguitar.php/?marca=" + editTextMarca.text.toString() + "&modelo=" + editTextModelo.text.toString() + "&precio=" + editTextPrecio.text.toString()
            leerUrl(url)
        }
    }

    fun objetoAJson(v: View){
        val guitarra = Guitarra("Fender","Stratocaster",1500)
        val gson=Gson()
        val json:String = gson.toJson(guitarra)
        Log.d("RESULTADO", json)
        textView.text=json

    }

    fun jsonAObjeto(v :View){

        val gson=Gson()
        val json:String="{\"marca\":\"Gibson\",\"modelo\":\"SG\",\"precio\":1700}"
        val guitarra:Guitarra=gson.fromJson(json,Guitarra::class.java)
        Log.d("RESULTADO", guitarra.toString())
        textView.text=guitarra.toString()

    }


    fun URLJsonObjeto( v: View) {

        val gson = Gson()
        try {


        val json = leerUrl("http://iesayala.ddns.net/json/jsonguitarras.php")

        val guitarra = gson.fromJson(json, GuitarraArray::class.java)

            textView.text=""
        for (item in guitarra.guitarras!!.iterator()) {
            Log.d("RESULTADO", item.marca)
            textView.text=textView.text.toString()+item.marca+" "+item.modelo+"\n"
        }}
        catch (e: Exception){
            Log.d("RESULTADO", "error")
            textView.text="Error"
        }

    }

        fun ficheroJsonObjeto( v: View){

        val gson=Gson()
        val json= leerFichero("guitarras.json")

        val guitarra = gson.fromJson(json,GuitarraArray::class.java)

            textView.text=""
        for (item in guitarra.guitarras!!.iterator()){
            Log.d("RESULTADO", item.marca)
            textView.text=textView.text.toString()+item.marca+" "+item.modelo+"\n"
        }



        Log.d("RESULTADO", leerUrl("http://iesayala.ddns.net/json/jsonguitarras.php"))
    }

    private fun leerUrl(urlString:String): String{

        val response = try {
            URL(urlString)
                    .openStream()
                    .bufferedReader()
                    .use { it.readText() }
        } catch (e: IOException) {
            "Error with ${e.message}."
        }

        return response
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
