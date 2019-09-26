package com.project.cepgetter

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
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

    }

    //TODO change this code to kotlin extension later
    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
        view.clearFocus()
    }

    override fun onResume() {
        super.onResume()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                //improvised maskText
//                if (s.length == 5){
//                    s.insert(5,"-")
//                }
                if (s.length == 8) {
                    getCep(s.toString())
                    hideKeyboard(this@MainActivity)

                }
            }
        })

    }

    private fun getCep(cep: String) {
        val call = WebClient().cepService().getCep(cep)
        call.enqueue(object : Callback<AddressResponse?> {
            override fun onResponse(
                call: Call<AddressResponse?>,
                response: Response<AddressResponse?>
            ) {
                response.body().let {
                    textView.text = //it?.logradouro + ", " + it?.localidade + ", " + it?.uf
                        "Logradouro: ${it?.logradouro}\nCidade: ${it?.localidade}\nEstado: ${it?.uf}"

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
