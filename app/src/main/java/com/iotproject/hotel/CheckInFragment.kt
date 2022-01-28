package com.iotproject.hotel

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputLayout
import org.json.JSONObject

class CheckInFragment : Fragment() {

    private var requestQueue: RequestQueue? = null

    private val BASEURL = "http://192.168.0.101:8080/api/"

    //TODO - check the responses (success/fail)

    private fun checkIn(volleyListener: VolleyListener, checkInJson: JSONObject){
        val urlCheckIn = BASEURL + "checkIN"
        requestQueue = Volley.newRequestQueue(requireContext())
        val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, urlCheckIn, checkInJson,
                { response -> checkInComplete(volleyListener) },
                { error -> error.printStackTrace() })
        requestQueue?.add(jsonObjectRequest)
    }

    private fun checkInComplete(volleyListener: VolleyListener){
        Toast.makeText(activity, "Checked-in successfully", Toast.LENGTH_SHORT).show()
        volleyListener.onResponseReceived()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_check_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inputToken: TextInputLayout = view.findViewById(R.id.outlinedTextFieldToken)
        val preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(requireContext())

        val volleyListener: VolleyListener = object : VolleyListener {
            override fun onResponseReceived() {
                preferences.edit {
                    putBoolean("checked_in", true)
                }
                findNavController().navigate(R.id.action_checkInFragment_to_roomCardFragment)
            }
        }

        val guestId = preferences.getInt("guest_id", 0)

        val buttonCheckIn: Button = view.findViewById(R.id.checkInButton)
        buttonCheckIn.setOnClickListener {
            val token = inputToken.editText?.text.toString().toInt()
            val checkInJsonObject = JSONObject()
            // TODO - data validation
            checkInJsonObject.put("token", token)
            checkInJsonObject.put("guest_id", guestId)
            checkIn(volleyListener, checkInJsonObject)
        }
    }
}