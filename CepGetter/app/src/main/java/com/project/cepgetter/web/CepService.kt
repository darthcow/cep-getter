package com.project.cepgetter.web

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CepService {
    //annotation specifies http request type and URL
    @GET("{cep}/json")
    fun getCep(@Path("cep") cep: String): Call<AddressResponse>

}