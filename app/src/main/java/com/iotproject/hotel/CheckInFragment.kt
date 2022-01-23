package com.iotproject.hotel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputLayout
import org.json.JSONObject

class CheckInFragment : Fragment() {

    var requestQueue: RequestQueue? = null

    val BASEURL = "http://192.168.1.27:8080/api/"

    private fun checkIn(volleyListener: VolleyListener, checkInJson: JSONObject){
        val urlAddGuest = BASEURL + "checkIN"
        requestQueue = Volley.newRequestQueue(requireContext())
        val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, urlAddGuest, checkInJson,
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

        val volleyListener: VolleyListener = object : VolleyListener {
            override fun onResponseReceived() {
                findNavController().navigate(R.id.action_checkInFragment_to_roomCardFragment)
            }
        }

        var guestId = 0

        parentFragmentManager.setFragmentResultListener("requestKeyId", viewLifecycleOwner){
            requestKey, bundle ->
            guestId = bundle.getInt("bundleKeyId")
        }

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