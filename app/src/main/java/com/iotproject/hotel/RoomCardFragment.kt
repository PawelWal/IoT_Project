package com.iotproject.hotel

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class RoomCardFragment : Fragment() {

    var requestQueue: RequestQueue? = null

    val BASEURL = "http://192.168.0.100:8080/api/"

    private fun blockCard(volleyListener: VolleyListener, blockCardJson: JSONObject){
        val urlAddGuest = BASEURL + "blockCard"
        requestQueue = Volley.newRequestQueue(requireContext())
        val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, urlAddGuest, blockCardJson,
                { response -> cardBlocked(response, volleyListener) },
                { error -> error.printStackTrace() })
        requestQueue?.add(jsonObjectRequest)
    }

    private fun cardBlocked(response: JSONObject, volleyListener: VolleyListener){
        Toast.makeText(activity, "Card blocked", Toast.LENGTH_SHORT).show()
        volleyListener.onResponseReceived()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_room_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(requireContext())
        val guestId = preferences.getInt("guest_id", 0)

        // TODO - set room number as value read from database
        val outputRoomNumber: TextView = view.findViewById(R.id.roomNumberText)

        // TODO - change listener
        val checkOutButton: Button = view.findViewById(R.id.checkOutButton)
        checkOutButton.setOnClickListener {
            Toast.makeText(activity, "saving to database (table CheckIns, field validUntil) happens now", Toast.LENGTH_SHORT).show()
        }

        val blockButton: Button = view.findViewById(R.id.blockCardButton)

        val volleyListener: VolleyListener = object : VolleyListener {
            override fun onResponseReceived() {
                blockButton.isEnabled = false
            }
        }

        blockButton.setOnClickListener {
            // TODO - data validation
            Log.d("guest_id", guestId.toString())
            val blockCardJsonObject = JSONObject()
            blockCardJsonObject.put("guest_id", guestId)
            blockCard(volleyListener, blockCardJsonObject)
        }
    }

}