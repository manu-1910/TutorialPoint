package com.nativeapp.tutorialpoint.repositories

import com.nativeapp.tutorialpoint.Config
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface ClassesApiService {
    @Headers(
        "Content-Type: application/x-www-form-urlencoded"
    )
    @FormUrlEncoded
    @POST(Config.INTERVIEW_TEST)
    suspend fun getClasses(@Field("json_data") json: HashMap<String, String>): Response<ClassModel>
}