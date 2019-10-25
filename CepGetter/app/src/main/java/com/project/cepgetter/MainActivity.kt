package com.project.cepgetter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.project.cepgetter.util.TextMask.CEP_MASK
import com.project.cepgetter.extensions.hideKeyboard
import com.project.cepgetter.extensions.longToast
import com.project.cepgetter.extensions.onTextChange
import com.project.cepgetter.extensions.shortToast
import com.project.cepgetter.util.TextMask.unmask
import com.project.cepgetter.util.insertMask
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProviders.of(this).get(CepViewModel::class.java) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
<<<<<<< HEAD
        //observe viewmodel's variables and what to do with value
        viewModel.resultAddres.observe(this, Observer<String> { result_address.text = it })
=======
        //observe viewmodel's variables and set what to do with value
        viewModel.address.observe(this, Observer<String> { result_address.text = it })
>>>>>>> 7d4367ff4416fd1ddece5a8e2327c8add39d5e51

    }

    //fun to copy address result to clipboard
    private fun copyAddress() {
        try {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("address", viewModel.resultAddres.value)
            clipboard.setPrimaryClip(clip)
            this.shortToast("Endreço copiado!")
        } catch (e: Exception) {
            this.longToast("Erro ao copiar endereço: ${e.localizedMessage}")
            println(e.localizedMessage)
        }
    }

    override fun onResume() {
        super.onResume()

        //set copyAddress fun to click listener
        result_address.setOnClickListener { copyAddress() }
        //insert mask in editText
        cep_field.insertMask(CEP_MASK)
        //called extension fun onTextChange which implements TextWatcher
        cep_field.onTextChange {


            //checks if field has required number of characters to make request
            if (unmask(it).length == 8) {
                //Only displays toast if fun returns a message different than null
                viewModel.getCep(unmask(it))?.let { it1 -> this@MainActivity.longToast(it1) }
                //hides keyboard after getting address
                this@MainActivity.hideKeyboard()
            }
        }
    }

}
