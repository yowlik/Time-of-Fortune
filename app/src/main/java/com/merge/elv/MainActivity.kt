package com.merge.elv

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.facebook.applinks.AppLinkData
import com.merge.elv.databinding.ActivityMainBinding

import com.merge.elv.nas.Fazas.Companion.cara
import com.merge.elv.nas.Fazas.Companion.chara
import com.merge.elv.nas.Fazas.Companion.dara
import com.merge.elv.nas.Fazas.Companion.para1
import com.merge.elv.nas.Fazas.Companion.para2
import com.merge.elv.oas.Gras
import com.merge.elv.was.Wras
import kotlinx.coroutines.*
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var baran: ActivityMainBinding

    var checker: String = "null"
    lateinit var jsoup: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baran = ActivityMainBinding.inflate(layoutInflater)
        setContentView(baran.root)
        jsoup = ""
        depa(this)

        val prefs = getSharedPreferences("ActivityPREF", MODE_PRIVATE)
        if (prefs.getBoolean("activity_exec", false)) {
            //второе включение
            val sharPref = getSharedPreferences("SP", MODE_PRIVATE)
            when (sharPref.getString(chara, "null")) {

                "2" -> {
                    skapa()
                }
                "3" -> {
                    teswa()
                }
                "nm" -> {
                    teswa()
                }
                "dp" -> {
                    teswa()
                }
                "org" -> {
                    skapa()
                }
                else -> {
                    skapa()
                }
            }

        } else {
            //первое включение
            val exec = prefs.edit()
            exec.putBoolean("activity_exec", true)
            exec.apply()

            val job = GlobalScope.launch(Dispatchers.IO) {
                checker = getCara(para1+para2)
            }
            runBlocking {
                try {
                    job.join()
                } catch (_: Exception){
                }
            }

            when (checker) {
                "1" -> {

                    Wisnocheck(1500)
                }
                "2" -> {
                    skapa()
                }
                "3" -> {
                    teswa()
                }

            }
        }
    }



    private suspend fun getCara(link: String): String {
        val url = URL(link)
        val oneStr = "1"
        val twoStr = "2"
        val testStr = "3"
        val activeStrn = "0"
        val urlConnection = withContext(Dispatchers.IO) {
            url.openConnection()
        } as HttpURLConnection

        return try {
            when (val text = urlConnection.inputStream.bufferedReader().readText()) {
                "2" -> {
                    val sharPref = applicationContext.getSharedPreferences("SP", MODE_PRIVATE)
                    val editor = sharPref.edit()
                    editor.putString(chara, twoStr)
                    editor.apply()
                    Log.d("jsoup status", text)
                    twoStr
                }
                "1" -> {
                    Log.d("jsoup status", text)
                    oneStr
                }
                "3" -> {
                    val sharPref = applicationContext.getSharedPreferences("SP", MODE_PRIVATE)
                    val editor = sharPref.edit()
                    editor.putString(chara, testStr)
                    editor.apply()
                    Log.d("jsoup status", text)
                    testStr
                }
                else -> {
                    Log.d("jsoup status", "is null")
                    activeStrn
                }
            }
        } finally {
            urlConnection.disconnect()
        }

    }

    private fun Wisnocheck(timeInterval: Long): Job {

        val sharPref = getSharedPreferences("SP", MODE_PRIVATE)
        return CoroutineScope(Dispatchers.IO).launch {
            while (NonCancellable.isActive) {
                val hawk1: String? = sharPref.getString(cara, null)
                val hawkdeep: String? = sharPref.getString(dara, "null")
                if (hawk1 != null) {
                    Log.d("TestInUIHawk", hawk1.toString())
                    if(hawk1.contains("tdb2")){
                        Log.d("zero_filter_2", "hawkname received")
                        val editor = sharPref.edit()
                        editor.putString(chara, "nm")
                        editor.apply()
                        teswa()
                    } else if (hawkdeep != null){
                        if(hawkdeep.contains("tdb2"))
                        {
                            Log.d("zero_filter_2", "hawkdeep received")
                            teswa()
                        }
                        else{
                            Log.d("zero_filter_2", "hawkdeep wrong")
                            val editor = sharPref.edit()
                            editor.putString(chara, "org")
                            editor.apply()
                            skapa()
                        }

                    }
                    break
                } else {
                    val hawk1: String? = sharPref.getString(cara, null)
                    Log.d("TestInUIHawkNulled", hawk1.toString())
                    delay(timeInterval)
                }
            }
        }
    }




    private fun teswa() {
        Intent(this, Wras::class.java)
            .also { startActivity(it) }
        finish()
    }


    private fun skapa() {
        Intent(this, Gras::class.java)
            .also { startActivity(it) }
        finish()
    }

    fun depa(context: Context) {
        val sharPref = applicationContext.getSharedPreferences("SP", MODE_PRIVATE)
        val editor = sharPref.edit()
        AppLinkData.fetchDeferredAppLinkData(
            context
        ) { appLinkData: AppLinkData? ->
            appLinkData?.let {
                val params = appLinkData.targetUri.host
                //тест
                editor.putString(dara,params.toString())
                editor.apply()
                if (params!!.contains("tdb2")){
                    editor.putString(chara, "dp")
                    editor.apply()
                }

            }
            if (appLinkData == null) {
//                //тест
//                editor.putString(D1,"tdb2vasyaidinahui")
//                editor.apply()
            }

        }
    }





}