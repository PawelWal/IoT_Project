package com.iotproject.hotel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class RoomCardFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_room_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO - set room number as value read from database
        val outputRoomNumber: TextView = view.findViewById(R.id.roomNumberText)

        // TODO - change listener
        val checkOutButton: Button = view.findViewById(R.id.checkOutButton)
        checkOutButton.setOnClickListener {
            Toast.makeText(activity, "saving to database (table CheckIns, field validUntil) happens now", Toast.LENGTH_SHORT).show()
        }

        // TODO - change listener
        val blockButton: Button = view.findViewById(R.id.blockCardButton)
        blockButton.setOnClickListener {
            Toast.makeText(activity, "saving to database (table RFIDs, field status) happens now", Toast.LENGTH_SHORT).show()
        }
    }

}