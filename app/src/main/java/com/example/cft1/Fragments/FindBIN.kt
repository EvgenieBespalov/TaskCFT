package com.example.cft1.Fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.example.cft1.DTOs.BINInfoDTO
import kotlinx.serialization.json.Json

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.cft1.DataBase.DBProvider
import com.example.cft1.Dialogs.ErrorDialog
import com.example.cft1.R
import kotlinx.serialization.decodeFromString
import java.net.URL
import kotlinx.coroutines.*
import java.io.FileNotFoundException


const val ARGG_OBJECT = "object"

class LookUpBin : Fragment() {
    private lateinit var binCards: EditText
    private lateinit var bankName: TextView
    private lateinit var cityOfBank: TextView
    private lateinit var siteOfBank: TextView
    private lateinit var numberPhoneOfBank: TextView
    private lateinit var schemeOfBank: TextView
    private lateinit var brand: TextView
    private lateinit var lengthBin: TextView
    private lateinit var luhn: TextView
    private lateinit var type: TextView
    private lateinit var prepaid: TextView
    private lateinit var emoji: TextView
    private lateinit var country: TextView
    private lateinit var latitude: TextView
    private lateinit var longitude: TextView

    private var URL = "https://lookup.binlist.net/"
    private lateinit var binInfoDTO: BINInfoDTO


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_find_bin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binCards = view.findViewById(R.id.binCards)
        bankName = view.findViewById(R.id.bankName)
        cityOfBank = view.findViewById(R.id.cityOfBank)
        siteOfBank = view.findViewById(R.id.siteOfBank)

        numberPhoneOfBank = view.findViewById(R.id.numberPhoneOfBank)
        schemeOfBank = view.findViewById(R.id.schemeOfBank)
        brand = view.findViewById(R.id.brand)
        lengthBin = view.findViewById(R.id.length)
        luhn = view.findViewById(R.id.luhn)
        type = view.findViewById(R.id.type)
        prepaid = view.findViewById(R.id.prepaid)
        emoji = view.findViewById(R.id.emoji)
        country = view.findViewById(R.id.country)
        latitude = view.findViewById(R.id.latitude)
        longitude = view.findViewById(R.id.longitude)

        binCards.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    var length = s.length
                    if (length == 8){
                        var newURL = URL + s

                        GlobalScope.launch() {
                            try {
                                binInfoDTO = Json.decodeFromString<BINInfoDTO>(readJSON(newURL))

                                withContext(Dispatchers.Main) {
                                    bankName.setText(binInfoDTO.bank.name)
                                    cityOfBank.setText(binInfoDTO.bank.city)

                                    siteOfBank.setText(binInfoDTO.bank.url)
                                    siteOfBank.setOnClickListener{
                                        activity?.let{
                                            val intent = Intent(Intent.ACTION_VIEW)
                                            if (binInfoDTO.bank.url != null)
                                            {
                                                intent.data = Uri.parse("https://" + siteOfBank.text.toString())
                                                it.startActivity(intent)
                                            }
                                        }
                                    }

                                    numberPhoneOfBank.setText(binInfoDTO.bank.phone)
                                    numberPhoneOfBank.setOnClickListener{
                                        activity?.let{
                                            val intent = Intent(Intent.ACTION_DIAL)
                                            if (binInfoDTO.bank.phone != null)
                                            {
                                                intent.data = Uri.parse("tel:" + binInfoDTO.bank.phone)
                                                it.startActivity(intent)
                                            }
                                        }
                                    }

                                    schemeOfBank.setText(binInfoDTO.scheme)
                                    brand.setText(binInfoDTO.brand)
                                    lengthBin.setText(binInfoDTO.number.length.toString())
                                    if (binInfoDTO.number.luhn == true) luhn.setText("Yes")
                                    else luhn.setText("No")
                                    type.setText(binInfoDTO.type)
                                    if (binInfoDTO.prepaid == true) prepaid.setText("Yes")
                                    else prepaid.setText("No")
                                    emoji.setText(binInfoDTO.country.emoji)

                                    country.setText(binInfoDTO.country.name)
                                    country.setOnClickListener{
                                        activity?.let{
                                            val intent = Intent(Intent.ACTION_VIEW)
                                            if (binInfoDTO.country.latitude != null && binInfoDTO.country.longitude != null){
                                                intent.data = Uri.parse("geo:" + binInfoDTO.country.latitude.toString() + "," + binInfoDTO.country.longitude.toString())
                                                it.startActivity(intent)
                                            }
                                        }
                                    }

                                    latitude.setText(binInfoDTO.country.latitude.toString())
                                    longitude.setText(binInfoDTO.country.longitude.toString())

                                    val db =
                                        getActivity()?.let { DBProvider(it.getApplicationContext(), null) }

                                    if (db != null) {
                                        db.getData(binInfoDTO, s.toString())
                                    }
                                }

                            }
                            catch (error: FileNotFoundException){
                                withContext(Dispatchers.Main) {
                                    val errorMessage = ErrorDialog()
                                    errorMessage.show(parentFragmentManager, "errorMessage")
                                    binCards.setText("")
                                }
                            }
                        }



                    }
                }
            }

            suspend fun readJSON(myURL: String): String {
                var JSONstring = URL(myURL).readText()

                return JSONstring
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }



}