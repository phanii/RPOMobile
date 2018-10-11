package com.rpo.mobile.utils

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiBuilder {
    companion object {
        val BASE_URL = "http://153.58.142.220:3000/"


        fun create(): APIinterface {
            val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client1)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return retrofit.create(APIinterface::class.java)
        }

        var client = OkHttpClient.Builder()
                .addInterceptor(BasicAuthInterceptor())
                .build()


        private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        private val client1: OkHttpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
            this.addInterceptor(BasicAuthInterceptor())


        }.build()

    }

}

