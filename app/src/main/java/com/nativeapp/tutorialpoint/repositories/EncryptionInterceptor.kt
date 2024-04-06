package com.nativeapp.tutorialpoint.repositories

import android.util.Base64
import android.util.Log
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject

class EncryptionInterceptor @Inject constructor(): Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        Log.d("Inteceptor", "Entered in interceptor")
        var request: Request = chain.request()
        val rawBody: RequestBody? = request.body()
        var encryptedBody = encrypt(rawBody.toString())
        Log.d("Encrypted-body", encryptedBody)
        val mediaType: MediaType? = MediaType.parse("text/plain; charset=utf-8")
        val body = RequestBody.create(mediaType, encryptedBody)
        Log.d("Content-typeeeee", body.toString())
        /*request = request.newBuilder()
            .header("Content-Type", body.contentType().toString())
            .header("Content-Length", body.contentLength().toString())
            .method(request.method(), body).build()*/
        Log.d("headers", request.headers().toString())
        val response = chain.proceed(request)
        response.body()?.let { Log.d("Response-body-body-body", response.toString()) }
        return response.newBuilder().body(ResponseBody.create(response.body()?.contentType(), response.body()?.string())).build()
    }

    fun encrypt(requestBody: String): String{
        Log.d("Rawww-body", requestBody)
        val cipher : Cipher = Cipher.getInstance("AES")
        val key : SecretKeySpec = SecretKeySpec("123456789tutorix".toByteArray(),"AES")
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val bytes = cipher.doFinal(requestBody.toByteArray())
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }

}