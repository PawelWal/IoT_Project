package com.iotproject.hotel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class UserFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
        // TODO - set country list as list read from database
        val inputCountry: TextView = view.findViewById(R.id.countryTextField)

        // TODO - change listener
        val buttonSave: Button = view.findViewById(R.id.saveButton)
        buttonSave.setOnClickListener {
            Toast.makeText(activity, "saving to database (table Guests) happens now", Toast.LENGTH_SHORT).show()
        }
    }

}