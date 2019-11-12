package com.example.testside.Interceptor

import android.content.Context
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.CacheControl
import java.util.concurrent.TimeUnit


class CacheInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain?.proceed(chain.request())
        val cacheControl = CacheControl.Builder()
        if (isConnected()) {
            cacheControl
                .maxAge(0, TimeUnit.DAYS)
                .build()
        } else {
            cacheControl
                .maxStale(30, TimeUnit.DAYS)
                .build();
        }

        return response?.newBuilder()
            ?.removeHeader("Pragma")
            ?.removeHeader("Cache-Control")
            ?.header("Cache-Control", cacheControl.toString())
            ?.build()
    }

    fun isConnected(): Boolean {
        try {
            val e = context.getSystemService(
                Context.CONNECTIVITY_SERVICE
            ) as android.net.ConnectivityManager
            val activeNetwork = e.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        } catch (e: Exception) {
            Log.w("IsConnected", e.toString())
        }
        return false
    }

}