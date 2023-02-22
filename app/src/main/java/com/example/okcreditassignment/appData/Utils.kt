package com.example.okcreditassignment.appData

import android.content.Context
import android.net.ConnectivityManager
import android.support.multidex.BuildConfig
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


object Utils {

    fun logRequestBody(`object`: Any?) {
        try {
            if (BuildConfig.DEBUG) {
                val jsonString = prettyJson(`object`)
                val maxLogSize = 4000
                if (jsonString.length > 4000) {
                    for (i in 0..jsonString.length / maxLogSize) {
                        val start = i * maxLogSize
                        var end = (i + 1) * maxLogSize
                        end = Math.min(end, jsonString.length)
                        Log.i(
                            Utils::class.simpleName,
                            (if (i == 0) "Request Body :: " else "") + jsonString.substring(
                                start,
                                end
                            )
                        )
                    }
                } else Log.i(Utils::class.simpleName, "Request Body :: $jsonString")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun prettyJson(`object`: Any?): String {
        var json = "{}"
        if (BuildConfig.DEBUG && `object` != null) {
            val parser = GsonBuilder().setPrettyPrinting().create()
            try {
                json = parser.toJson(JsonParser().parse(objectToJson(`object`)))
            } catch (e: Exception) {
                try {
                    json = parser.toJson(JsonParser().parse(`object`.toString()))
                } catch (ignore: Exception) {
                }
            }
            if (isEmpty(json) || json.equals("{}", ignoreCase = true)) json = objectToJson(`object`)
        }
        return json
    }

    private fun objectToJson(`object`: Any): String {
        return Gson().toJson(`object`).replace("\\", "")
    }

    fun isEmpty(mString: String?): Boolean {
        return mString == null || mString.isEmpty() || mString.trim { it <= ' ' }.isEmpty() || mString == "null"
    }

    fun parseDateAs(formattedDate: String?, currentFormat: String?, desiredFormat: String?, locale: Locale? = Locale.ENGLISH): String {
        val date: Date?
        date = try {
            SimpleDateFormat(currentFormat, locale).parse(formattedDate)
        } catch (exTz: Exception) {
            exTz.printStackTrace()
            Date()
        }
        return SimpleDateFormat(desiredFormat, locale).format(date)
    }

    fun convertToLocal(dateTime: String, format: String, desiredFormat: String, locale: Locale? = Locale.ENGLISH): String {
        Log.e("UTC Date", dateTime + "")
        val utcFormat: DateFormat = SimpleDateFormat(format, locale)
        utcFormat.timeZone = TimeZone.getTimeZone("UTC")
        var date: Date?
        try {
            date = utcFormat.parse(dateTime)
        } catch (e: ParseException) {
            date = Date()
//            e.printStackTrace()
        }
        val pstFormat: DateFormat = SimpleDateFormat(desiredFormat, Locale.ENGLISH)
        val result = pstFormat.format(date)
        println(result)
        Log.e("Local Date", result + "")
        return result
    }

    fun isDeviceOnline(context: Context): Boolean {
        var isConnected = false
        try {
            val connectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            isConnected = networkInfo != null && networkInfo.isAvailable && networkInfo.isConnected
            return isConnected
        } catch (e: Exception) {
            Log.v("Internet Connectivity", e.toString())
        }
        return isConnected
    }
}