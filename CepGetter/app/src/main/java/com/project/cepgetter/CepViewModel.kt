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
    var errorMessage: String? = null
    private val _shouldShowResult = MutableLiveData<Boolean>()
    private val _resultAddres = MutableLiveData<String>()

    val resultAddres: LiveData<String> get() = _resultAddres
    val shouldShowResult: LiveData<Boolean> get() = _shouldShowResult

    fun getCep(cep: String): String? {
        val call = WebClient().cepService().getCep(cep)
        call.enqueue(object : Callback<AddressResponse?> {
            override fun onResponse(call: Call<AddressResponse?>, response: Response<AddressResponse?>) {
                response.body()?.let {
                    if (it.erro) {
                        _shouldShowResult.value = false
                        _resultAddres.value = "Cep inválido"
                    } else {
                        _resultAddres.value =
                            "Cep: ${it.cep}\nLogradouro: ${it.logradouro}" +
                                    "\nBairro: ${it.bairro}\nCidade: ${it.localidade}\nEstado: ${it.uf}"
                        _shouldShowResult.value = true
                    }
                }
            }

            override fun onFailure(call: Call<AddressResponse?>, t: Throwable?) {
                errorMessage = t?.message.toString()
            }
        })
        return errorMessage
    }
}