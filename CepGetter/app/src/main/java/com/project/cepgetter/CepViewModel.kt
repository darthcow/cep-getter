package com.project.cepgetter

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.cepgetter.web.AddressResponse
import com.project.cepgetter.web.WebClient
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CepViewModel : ViewModel() {

    val address = MutableLiveData<String>()
    var errorMessage: String? = null


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