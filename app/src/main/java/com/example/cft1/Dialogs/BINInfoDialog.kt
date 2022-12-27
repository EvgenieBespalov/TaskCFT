package com.example.cft1.Dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.cft1.Models.BINInfoModel

class BINInfoDialog(var BINinfo: BINInfoModel) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            var message: String

            message = "BIN cards: " + BINinfo.BIN
            if (BINinfo.bank.name != null) message = message + "\nBank name: " + BINinfo.bank.name
            if (BINinfo.bank.city != null) message = message + "\nBank city: " + BINinfo.bank.city
            if (BINinfo.bank.url != null) message = message + "\nBank site: " + BINinfo.bank.url
            if (BINinfo.bank.phone != null) message = message + "\nBank phone: " + BINinfo.bank.phone
            if (BINinfo.scheme != null) message = message + "\nCard scheme/Network: " + BINinfo.scheme
            if (BINinfo.brand != null) message = message + "\nCard brand: " + BINinfo.brand
            if (BINinfo.number.length != 0) message = message + "\nNumber length: " + BINinfo.number.length
            if (BINinfo.number.luhn != null) message = message + "\nNumber luhn: " + BINinfo.number.luhn
            if (BINinfo.type != null) message = message + "\nCard type: " + BINinfo.type
            if (BINinfo.prepaid != null) message = message + "\nCard prepaid: " + BINinfo.prepaid
            if (BINinfo.country.name != null) message = message + "\nCountry name: " + BINinfo.country.name
            if (BINinfo.country.emoji != null) message = message + BINinfo.country.emoji
            if (BINinfo.country.latitude != 0) message = message + "\nCountry latitude: " + BINinfo.country.latitude
            if (BINinfo.country.longitude != 0) message = message + "\nCountry longitude: " + BINinfo.country.longitude

            builder.setMessage(message)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}