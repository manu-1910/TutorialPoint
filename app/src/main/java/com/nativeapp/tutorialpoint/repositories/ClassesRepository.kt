package com.nativeapp.tutorialpoint.repositories

import android.util.Log
import javax.inject.Inject

class ClassesRepository @Inject constructor(val apiService: ClassesApiService){
    suspend fun getClasses(): ApiResponse<Any>{
        try{
            Log.d("Api call","Calling...")
            val response = apiService.getClasses(hashMapOf("class" to "class6", "subject" to "physics"))
            if(response.isSuccessful){
                Log.d("Api Success: ", "Success")
                return ApiResponse.Success<ClassModel>(response.body() as ClassModel)
            } else {
                Log.d("Api exception: ", "Failure")
                return ApiResponse.ApiError(response.errorBody().toString())
            }
        } catch (e: Exception){
            Log.d("Api exception: ", e.toString())
            return ApiResponse.Failure(e)
        }
    }
}