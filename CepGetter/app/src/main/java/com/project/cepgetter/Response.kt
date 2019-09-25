package com.project.cepgetter

data class AddressResponse(
    val bairro: String,
    val cep: String,
    val complemento: String,
    val gia: String,
    val ibge: String,
    val localidade: String,
    val logradouro: String,
    val uf: String,
    val unidade: String
)
