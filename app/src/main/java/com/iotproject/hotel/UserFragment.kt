package com.iotproject.hotel

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputLayout
import org.json.JSONObject
import androidx.core.content.edit


class UserFragment : Fragment() {

    var requestQueue: RequestQueue? = null
    var requestQueueGuest: RequestQueue? = null
    var countriesList = mutableListOf<String>()
    var countriesCodesMap = HashMap<Int, String>()
    var guestId: Int = 0

    // TODO - get ip address from computer
    val BASEURL = "http://192.168.0.100:8080/api/"

    //TODO - check the response (success/fail)

    private fun getJsonDataFromApi(volleyListener: VolleyListener){
        val urlCountries = BASEURL + "countries"
        requestQueue = Volley.newRequestQueue(requireContext())
        val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, urlCountries, null,
                { response -> parseJson(response, volleyListener) },
                { error -> error.printStackTrace() })
        requestQueue?.add(jsonObjectRequest)
    }

    private fun parseJson(response: JSONObject, volleyListener: VolleyListener){
        for (i in 1..response.length()){
            val country = response.getString(i.toString())
            countriesList.add(country)
            countriesCodesMap[i] = country
        }
        volleyListener.onResponseReceived()
    }

    private fun addGuest(volleyListener: VolleyListener, guestJson: JSONObject){
        val urlAddGuest = BASEURL + "addGuest"
        requestQueueGuest = Volley.newRequestQueue(requireContext())
        val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, urlAddGuest, guestJson,
            { response -> guestAdded(response, volleyListener) },
                { error -> error.printStackTrace() })
        requestQueueGuest?.add(jsonObjectRequest)
    }

    private fun guestAdded(response: JSONObject, volleyListener: VolleyListener){
        Toast.makeText(activity, "Data saved", Toast.LENGTH_SHORT).show()
        guestId = response.getInt("guest_id")
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

        val inputName: TextInputLayout = view.findViewById(R.id.nameTextField)
        val inputSurname: TextInputLayout = view.findViewById(R.id.surnameTextField)
        val inputDocument: TextInputLayout = view.findViewById(R.id.documentTextField)
        val inputPhone: TextInputLayout = view.findViewById(R.id.phoneTextField)
        val inputEmail: TextInputLayout = view.findViewById(R.id.emailTextField)
        val inputAddress: TextInputLayout = view.findViewById(R.id.addressTextField)
        val inputCity: TextInputLayout = view.findViewById(R.id.cityTextField)
        val inputCode: TextInputLayout = view.findViewById(R.id.codeTextField)

        val inputCountry: TextInputLayout = view.findViewById(R.id.countryTextField)

        val volleyListener: VolleyListener = object : VolleyListener {
            override fun onResponseReceived() {
                val countryAdapter = ArrayAdapter(requireContext(), R.layout.country_item, countriesList)
                (inputCountry.editText as? AutoCompleteTextView)?.setAdapter(countryAdapter)
            }
        }

        getJsonDataFromApi(volleyListener)

        val buttonSave: Button = view.findViewById(R.id.saveButton)
        buttonSave.setOnClickListener {

            // TODO - data validation
            val guestName = inputName.editText?.text.toString()
            val guestSurname = inputSurname.editText?.text.toString()
            val guestDocument = inputDocument.editText?.text.toString()
            val guestPhone = inputPhone.editText?.text.toString()
            val guestEmail = inputEmail.editText?.text.toString()
            val guestAddress = inputAddress.editText?.text.toString()
            val guestCity = inputCity.editText?.text.toString()
            val guestCode = inputCode.editText?.text.toString()
            val guestCountry = inputCountry.editText?.text.toString()
            val guestCountryCode = countriesCodesMap.filterValues { it == guestCountry }.keys.first().toInt()

            val guestJsonObject = JSONObject()
            guestJsonObject.put("email", guestEmail)
            guestJsonObject.put("name", guestName)
            guestJsonObject.put("surname", guestSurname)
            guestJsonObject.put("doc_no", guestDocument)
            guestJsonObject.put("phone_no", guestPhone)
            guestJsonObject.put("address", guestAddress)
            guestJsonObject.put("zip_code", guestCode)
            guestJsonObject.put("city", guestCity)
            guestJsonObject.put("country_code", guestCountryCode)

            val volleyListenerGuest: VolleyListener = object : VolleyListener {
                override fun onResponseReceived() {
                    val preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(requireContext())
                    preferences.edit {
                        putInt("guest_id", guestId)
                        putBoolean("checked_in", false)
                    }
                    Log.d("guest_id", guestId.toString())
                    findNavController().navigate(R.id.action_userFragment_to_checkInFragment)
                }
            }

            addGuest(volleyListenerGuest, guestJsonObject)
        }
    }
}

