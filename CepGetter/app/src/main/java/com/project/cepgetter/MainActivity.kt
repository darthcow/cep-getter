package com.project.cepgetter

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

//    private lateinit var viewModel: CepViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        viewModel = ViewModelProviders.of(this).get(CepViewModel::class.java)

        button.setOnClickListener { getCep() }
    }



    private fun getCep() {
        val call = WebClient().cepService().getCep(editText.text.toString())
        call.enqueue(object : Callback<AddressResponse?> {
            override fun onResponse(
                call: Call<AddressResponse?>,
                response: Response<AddressResponse?>
            ) {
                response.body().let {
                    textView.text = //it?.logradouro + ", " + it?.localidade + ", " + it?.uf
                        "${it?.logradouro}, ${it?.localidade}, ${it?.uf}"
                }
            }


            override fun onFailure(
                call: Call<AddressResponse?>,
                t: Throwable?
            ) {
                Toast.makeText(this@MainActivity, t?.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

}
