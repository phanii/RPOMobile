package com.rpo.mobile.utils

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class BasicAuthInterceptor : Interceptor {


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authenticatedRequest = request.newBuilder()

                .header("Content-Type", "application/json")

                .build()
        return chain.proceed(authenticatedRequest)
    }
}