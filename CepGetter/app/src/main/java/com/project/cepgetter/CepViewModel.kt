package com.project.cepgetter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.cepgetter.web.AddressResponse
import com.project.cepgetter.web.WebClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CepViewModel : ViewModel() {

    private val address = MutableLiveData<String>()
    var errorMessage: String? = null
    val resultAddres: LiveData<String>
    get() = address


    fun getCep(cep: String): String? {
        val call = WebClient().cepService().getCep(cep)
        call.enqueue(object : Callback<AddressResponse?> {
            override fun onResponse(call: Call<AddressResponse?>, response: Response<AddressResponse?>) {
                response.body()?.let {
                    address.value =
                        "Cep: ${it.cep}\nLogradouro: ${it.logradouro}" +
                                "\nBairro: ${it.bairro}\nCidade: ${it.localidade}\nEstado: ${it.uf}"
                }
            }

            override fun onFailure(call: Call<AddressResponse?>, t: Throwable?) {
                errorMessage = t?.message.toString()
            }
        })
        return errorMessage
    }


}