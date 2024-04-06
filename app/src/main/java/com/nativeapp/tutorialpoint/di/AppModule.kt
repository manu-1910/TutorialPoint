package com.nativeapp.tutorialpoint.di

import com.google.gson.GsonBuilder
import com.nativeapp.tutorialpoint.Config
import com.nativeapp.tutorialpoint.repositories.ClassesApiService
import com.nativeapp.tutorialpoint.repositories.ClassesRepository
import com.nativeapp.tutorialpoint.repositories.EncryptionInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideEncryptionInterceptor(): EncryptionInterceptor{
        return EncryptionInterceptor()
    }

    @Singleton
    @Provides
    fun provideClassesApiService(interceptor: EncryptionInterceptor): ClassesApiService{
        val client : OkHttpClient.Builder = OkHttpClient.Builder().addInterceptor(interceptor)
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder()
            .baseUrl(Config.REMOTE_REPOSITORY_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client.build())
            .build()
            .create(ClassesApiService::class.java)
    }
    
    @Singleton
    @Provides
    fun provideRepository(classesApi: ClassesApiService): ClassesRepository{
        return ClassesRepository(classesApi)
    }

}