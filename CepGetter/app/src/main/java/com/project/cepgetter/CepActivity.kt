package com.project.cepgetter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.project.cepgetter.databinding.ActivityCepBinding
import com.project.cepgetter.util.TextMask.CEP_MASK
import com.project.cepgetter.extensions.hideKeyboard
import com.project.cepgetter.extensions.longToast
import com.project.cepgetter.extensions.onTextChange
import com.project.cepgetter.extensions.shortToast
import com.project.cepgetter.util.TextMask.unmask
import com.project.cepgetter.util.insertMask
import kotlinx.android.synthetic.main.activity_cep.*


class CepActivity : AppCompatActivity() {

    private val cepViewModel by lazy { ViewModelProviders.of(this).get(CepViewModel::class.java) }
    private lateinit var binding: ActivityCepBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cep)
        binding.viewModel = cepViewModel
        subscribeUi()
    }

    //fun to copy address result to clipboard
    private fun copyAddress() {
        try {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("address", cepViewModel.resultAddres.value)
            clipboard.setPrimaryClip(clip)
            this.shortToast("Endereço copiado!")
        } catch (e: Exception) {
            this.longToast("Erro ao copiar endereço: ${e.localizedMessage}")
            println(e.localizedMessage)
        }
    }

    //observe viewmodel's variables and what to do with value
    private fun subscribeUi() {
//        cepViewModel.resultAddres.observe(this, Observer<String> { tv_result_address.text = it })
    }

    override fun onResume() {
        super.onResume()
        //set copyAddress fun to click listener
        tv_result_address.setOnClickListener { copyAddress() }
        //insert mask in editText
        edt_cep_field.insertMask(CEP_MASK)
        //called extension fun onTextChange which implements TextWatcher
        edt_cep_field.onTextChange {
            //checks if field has required number of characters to make request
            if (unmask(it).length == 8) {
                //Only displays toast if fun returns a message different than null
                cepViewModel.getCep(unmask(it))?.let { it1 -> this@CepActivity.longToast(it1) }
                //hides keyboard after getting address
                this@CepActivity.hideKeyboard()
                longToast(binding.viewModel?.resultAddres?.value.toString())
            }
        }
    }
}
