package com.merge.elv.was

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.provider.MediaStore
import android.util.Log
import android.webkit.*
import android.widget.Toast

import com.google.android.material.snackbar.Snackbar
import com.merge.elv.R
import com.merge.elv.databinding.ActivityWrasBinding
import com.merge.elv.nas.Fazas
import com.onesignal.OneSignal
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.IOException

class Wras : AppCompatActivity() {
    private val FILECHOOSERRESULTCODE = 1


    var mFilePathCallback: ValueCallback<Array<Uri>>? = null
    var mCameraPhotoPath: String? = null
    lateinit var evav: WebView
    lateinit var bindLind: ActivityWrasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindLind = ActivityWrasBinding.inflate(layoutInflater)
        setContentView(bindLind.root)
        evav = bindLind.viewWeb
        Snackbar.make(
            bindLind.root, "Loading...",
            Snackbar.LENGTH_LONG
        ).show()

        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        cookieManager.setAcceptThirdPartyCookies(evav, true)
        settings()
        val activity: Activity = this

        evav.webViewClient = object : WebViewClient() {


            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                try {
                    if (URLUtil.isNetworkUrl(url)) {
                        return false
                    }
                    if (appInstalledOrNot(url)) {

                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(url)
                        startActivity(intent)
                    } else {

                        Toast.makeText(
                            applicationContext,
                            "Application is not installed",
                            Toast.LENGTH_LONG
                        ).show()
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=org.telegram.messenger")
                            )
                        )
                    }
                    return true
                } catch (e: Exception) {
                    return false
                }
                view.loadUrl(url)
            }


            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                //Save the last visited URL
                saveUrl(url)
            }

            override fun onReceivedError(
                view: WebView,
                errorCode: Int,
                description: String,
                failingUrl: String
            ) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show()
            }


        }
        evav.webChromeClient = object : WebChromeClient() {
            override fun onShowFileChooser(
                webView: WebView, filePathCallback: ValueCallback<Array<Uri>>,
                fileChooserParams: FileChooserParams
            ): Boolean {
                mFilePathCallback?.onReceiveValue(null)
                mFilePathCallback = filePathCallback
                var takePictureIntent: Intent? = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (takePictureIntent!!.resolveActivity(packageManager) != null) {

                    // create the file where the photo should go
                    var photoFile: File? = null
                    try {
                        photoFile = createImageFile()
                        takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath)
                    } catch (ex: IOException) {
                        // Error occurred while creating the File
                    }

                    // continue only if the file was successfully created
                    if (photoFile != null) {
                        mCameraPhotoPath = "file:" + photoFile.absolutePath
                        takePictureIntent.putExtra(
                            MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(photoFile)
                        )
                    } else {
                        takePictureIntent = null
                    }
                }
                val contentSelectionIntent = Intent(Intent.ACTION_GET_CONTENT)
                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE)
                contentSelectionIntent.type = "image/*"
                val intAra: Array<Intent?> =
                    takePictureIntent?.let { arrayOf(it) } ?: arrayOfNulls(0)
                val chooserIntent = Intent(Intent.ACTION_CHOOSER)
                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent)
                chooserIntent.putExtra(Intent.EXTRA_TITLE, getString(R.string.image_chooser))
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intAra)
                startActivityForResult(
                    chooserIntent, FILECHOOSERRESULTCODE
                )
                return true
            }

            // creating image files (Lollipop only)
            @Throws(IOException::class)
            private fun createImageFile(): File {
                var storageDir = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    "DirectoryNameHere"
                )
                if (!storageDir.exists()) {
                    storageDir.mkdirs()
                }

                // create an image file name
                storageDir =
                    File(storageDir.toString() + File.separator + "IMG_" + System.currentTimeMillis() + ".jpg")
                return storageDir
            }

        }

        evav.loadUrl(getsik())
    }


    private fun toOneSignal(string: String) {
// Setting External User Id with Callback Available in SDK Version 4.0.0+
        OneSignal.setExternalUserId(
            string,
            object : OneSignal.OSExternalUserIdUpdateCompletionHandler {
                override fun onSuccess(results: JSONObject) {
                    try {
                        if (results.has("push") && results.getJSONObject("push").has("success")) {
                            val isPushSuccess = results.getJSONObject("push").getBoolean("success")
                            OneSignal.onesignalLog(
                                OneSignal.LOG_LEVEL.VERBOSE,
                                "Set external user id for push status: $isPushSuccess"
                            )
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                    try {
                        if (results.has("email") && results.getJSONObject("email").has("success")) {
                            val isEmailSuccess =
                                results.getJSONObject("email").getBoolean("success")
                            OneSignal.onesignalLog(
                                OneSignal.LOG_LEVEL.VERBOSE,
                                "Set external user id for email status: $isEmailSuccess"
                            )
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                    try {
                        if (results.has("sms") && results.getJSONObject("sms").has("success")) {
                            val isSmsSuccess = results.getJSONObject("sms").getBoolean("success")
                            OneSignal.onesignalLog(
                                OneSignal.LOG_LEVEL.VERBOSE,
                                "Set external user id for sms status: $isSmsSuccess"
                            )
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

                override fun onFailure(error: OneSignal.ExternalIdError) {
                    // The results will contain channel failure statuses
                    // Use this to detect if external_user_id was not set and retry when a better network connection is made
                    OneSignal.onesignalLog(
                        OneSignal.LOG_LEVEL.VERBOSE,
                        "Set external user id done with error: $error"
                    )
                }
            })
    }

    private fun settings() {
        val webSettings = evav.settings
        webSettings.javaScriptEnabled = true

        webSettings.useWideViewPort = true

        webSettings.loadWithOverviewMode = true
        webSettings.allowFileAccess = true
        webSettings.domStorageEnabled = true
        webSettings.userAgentString = webSettings.userAgentString.replace("; wv", "")

        webSettings.javaScriptCanOpenWindowsAutomatically = true
        webSettings.setSupportMultipleWindows(false)

        webSettings.displayZoomControls = false
        webSettings.builtInZoomControls = true
        webSettings.setSupportZoom(true)

        webSettings.pluginState = WebSettings.PluginState.ON
        webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        webSettings.setAppCacheEnabled(true)

        webSettings.allowContentAccess = true
    }

    private fun getsik(): String {


        val spaan = getSharedPreferences("SP_WEBVIEW_PREFS", AppCompatActivity.MODE_PRIVATE)

        val ge = getSharedPreferences("SP", MODE_PRIVATE)

        val cpOne: String? = ge.getString(Fazas.cara, "null")
        val dpOne: String? = ge.getString(Fazas.dara, "null")
        val mainid: String? = ge.getString(Fazas.MAIN_ID, null)

        val pack = "com.merge.elv"


        val one = "sub_id_1="
        val two = "deviceID="
        val three = "ad_id="
        val four = "sub_id_4="
        val five = "sub_id_5="
        val six = "sub_id_6="


        val oas = "http://"
        val duas = "timeoffortune.xyz/go.php?to=2&"

        val namingI = "naming"
        val linkornull = "deeporg"

        val androidVersion = Build.VERSION.RELEASE

        val resultAB = oas + duas

        var after = ""
        if (cpOne != "null") {
            //after =
            //    "$resultAB$one$cpOne&$two$afId&$three$mainid&$four$pack&$five$androidVersion&$six$namingI"
        } else {
            //after =
            //    "$resultAB$one$dpOne&$two$afId&$three$mainid&$four$pack&$five$androidVersion&$six$linkornull"
        }
        Log.d("TESTAG", "Test Result $after")
        //toOneSignal(afId.toString())
        return spaan.getString("SAVED_URL", after).toString()
    }


    private fun appInstalledOrNot(uri: String): Boolean {

        val pm = packageManager
        try {

            pm.getPackageInfo("org.telegram.messenger", PackageManager.GET_ACTIVITIES)


            return true
        } catch (e: PackageManager.NameNotFoundException) {

        }
        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode != FILECHOOSERRESULTCODE || mFilePathCallback == null) {
            super.onActivityResult(requestCode, resultCode, data)
            return
        }
        var results: Array<Uri>? = null

        // check that the response is a good one
        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (data == null || data.data == null) {
                // if there is not data, then we may have taken a photo
                results = arrayOf(Uri.parse(mCameraPhotoPath))
            } else {
                val dataString = data.dataString
                if (dataString != null) {
                    results = arrayOf(Uri.parse(dataString))
                }
            }
        }
        mFilePathCallback?.onReceiveValue(results)
        mFilePathCallback = null
    }


    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {


        if (evav.canGoBack()) {
            if (doubleBackToExitPressedOnce) {
                evav.stopLoading()
                evav.loadUrl(firstUrl)
            }
            this.doubleBackToExitPressedOnce = true
            evav.goBack()
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                doubleBackToExitPressedOnce = false
            }, 2000)

        } else {
            super.onBackPressed()
        }
    }

    var firstUrl = ""
    fun saveUrl(url: String?) {
        if (!url!!.contains("t.me")) {

            if (firstUrl == "") {
                firstUrl = getSharedPreferences(
                    "SP_WEBVIEW_PREFS",
                    AppCompatActivity.MODE_PRIVATE
                ).getString(
                    "SAVED_URL",
                    url
                ).toString()

                val sp = getSharedPreferences("SP_WEBVIEW_PREFS", AppCompatActivity.MODE_PRIVATE)
                val editor = sp.edit()
                editor.putString("SAVED_URL", url)
                editor.apply()
            }
        }
    }
}