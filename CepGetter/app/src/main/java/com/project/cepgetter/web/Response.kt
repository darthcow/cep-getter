package com.project.cepgetter.web

// specifies de response body of the call
data class AddressResponse(
    val bairro: String,
    val cep: String,
    val complemento: String,
    val gia: String,
    val ibge: String,
    val localidade: String,
    val logradouro: String,
    val uf: String,
    val unidade: String,
    val erro: Boolean
)
