package com.iotproject.hotel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class CheckInFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_check_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inputToken: TextView = view.findViewById(R.id.tokenTextField)

        // TODO - change listener
        val buttonCheckIn: Button = view.findViewById(R.id.checkInButton)
        buttonCheckIn.setOnClickListener {
            Toast.makeText(activity, "saving to database (table CheckIns) happens now", Toast.LENGTH_SHORT).show()
        }
    }

}