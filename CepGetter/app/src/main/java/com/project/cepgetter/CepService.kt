package com.project.cepgetter

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CepService {
    @GET("{cep}/json")
    fun getCep(@Path("cep") cep: String): Call<AddressResponse>

}