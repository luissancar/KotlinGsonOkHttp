package com.example.luissancar.kotlingsonokhttp

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import java.io.FileReader
import java.io.IOException
import okhttp3.OkHttpClient
import okhttp3.Request;
import okhttp3.Response;
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    GetJsonWithOkHttpClient(textView ).execute()

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


open class GetJsonWithOkHttpClient(textView: TextView) : AsyncTask<Unit, Unit, String>() {

    val mInnerTextView = textView

    override fun doInBackground(vararg params: Unit?): String? {
        val networkClient = NetworkClient()
        val stream = BufferedInputStream(
                networkClient.get("https://raw.githubusercontent.com/irontec/android-kotlin-samples/master/common-data/bilbao.json"))
        return readStream(stream)
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)

        mInnerTextView.text = result

    }

    fun readStream(inputStream: BufferedInputStream): String {
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()
        bufferedReader.forEachLine { stringBuilder.append(it) }
        return stringBuilder.toString()
    }
}

