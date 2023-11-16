package com.hackvita.pathcare

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitUtilClass {
    companion object {
        private lateinit var retrofit: Retrofit


        val API_BASE_URL_PROD = "https://healthify-8z0y.onrender.com/"
        fun getRetrofit(): Retrofit {

            if (!::retrofit.isInitialized) {

                retrofit = Retrofit.Builder()
                    .baseUrl(API_BASE_URL_PROD)
                    .addConverterFactory(
                        GsonConverterFactory.create()
                    ).client(OkHttpClient.Builder()
                        .connectTimeout(15, TimeUnit.SECONDS) // set connect timeout
                        .readTimeout(15, TimeUnit.SECONDS) // set read timeout
                        .writeTimeout(15, TimeUnit.SECONDS)
                        .addInterceptor { chain ->
                            chain.proceed(chain.request().newBuilder().also {

                            }.build())
                        }.also { client ->
                            val logging = HttpLoggingInterceptor()
//                            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//                            client.addInterceptor(null);
                        }.build()
                    ).build()
                return retrofit
            }
            return retrofit
        }

    }
}