package com.project.cepgetter

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WebClient {
private var retrofit : Retrofit = Retrofit.Builder()
    .baseUrl("https://viacep.com.br/ws/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()


    fun cepService(): CepService = retrofit.create(CepService::class.java)


}