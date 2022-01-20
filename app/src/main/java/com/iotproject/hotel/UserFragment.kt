package com.iotproject.hotel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputLayout
import org.json.JSONObject


class UserFragment : Fragment() {

    var requestQueue: RequestQueue? = null
    var countriesList = mutableListOf<String>()

    private fun getJsonDataFromApi(volleyListener: VolleyListener){
        val urlCountries = "http://192.168.0.101:8080/api/countries"
        requestQueue = Volley.newRequestQueue(requireContext())
        val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, urlCountries, null,
                { response ->
                    parseJson(response, volleyListener)
                }, { error ->
            error.printStackTrace()
        })
        requestQueue?.add(jsonObjectRequest)
    }

    private fun parseJson(response: JSONObject, volleyListener: VolleyListener){
        for (i in 1 until response.length()){
            countriesList.add(response.getString(i.toString()))
        }
        volleyListener.onResponseReceived()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inputName: TextView = view.findViewById(R.id.nameTextField)
        val inputSurname: TextView = view.findViewById(R.id.surnameTextField)
        val inputDocument: TextView = view.findViewById(R.id.documentTextField)
        val inputPhone: TextView = view.findViewById(R.id.phoneTextField)
        val inputEmail: TextView = view.findViewById(R.id.emailTextField)
        val inputAddress: TextView = view.findViewById(R.id.addressTextField)
        val inputCity: TextView = view.findViewById(R.id.cityTextField)
        val inputCode: TextView = view.findViewById(R.id.codeTextField)

        val inputCountry: TextInputLayout = view.findViewById(R.id.countryTextField)

        val volleyListener: VolleyListener = object : VolleyListener {
            override fun onResponseReceived() {
                val countryAdapter = ArrayAdapter(requireContext(), R.layout.country_item, countriesList)
                (inputCountry.editText as? AutoCompleteTextView)?.setAdapter(countryAdapter)
            }
        }

        getJsonDataFromApi(volleyListener)

        // TODO - change listener
        val buttonSave: Button = view.findViewById(R.id.saveButton)
        buttonSave.setOnClickListener {
            Toast.makeText(
                    activity,
                    "saving to database (table Guests) happens now",
                    Toast.LENGTH_SHORT
            ).show()
        }
    }
}

