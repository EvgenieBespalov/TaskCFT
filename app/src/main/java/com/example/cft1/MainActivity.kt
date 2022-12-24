package com.example.cft1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Contacts
import android.text.Editable
import android.text.TextWatcher
import com.example.cft1.Models.MainData
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import android.util.Log
import android.view.Gravity
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import kotlinx.serialization.decodeFromString
import java.net.URL
import kotlinx.coroutines.*
import java.io.FileNotFoundException

class MainActivity : AppCompatActivity(){
    private var URL = "https://lookup.binlist.net/"
    private lateinit var MainData: MainData

    val jsonString = """
        {
              "number": {
                "length": 16,
                "luhn": true
              },
              "scheme": "visa",
              "type": "debit",
              "brand": "Visa/Dankort",
              "prepaid": false,
              "country": {
                "numeric": "208",
                "alpha2": "DK",
                "name": "Denmark",
                "emoji": "🇩🇰",
                "currency": "DKK",
                
                "longitude": 10
              },
              "bank": {
                
                "url": "www.jyskebank.dk",
                "phone": "+4589893300",
                "city": "Hjørring"
              }
        }       """.trimIndent()
    /*
    {

        {
              "number": {
                "length": 16,
                "luhn": true
              },
              "scheme": "visa",
              "type": "debit",
              "brand": "Visa/Dankort",
              "prepaid": false,
              "country": {
                "numeric": "208",
                "alpha2": "DK",
                "name": "Denmark",
                "emoji": "🇩🇰",
                "currency": "DKK",
                "latitude": 56,
                "longitude": 10
              },
              "bank": {
                "name": "Jyske Bank",
                "url": "www.jyskebank.dk",
                "phone": "+4589893300",
                "city": "Hjørring"
              }
            }
   }*/

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

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binCards = findViewById(R.id.binCards)
        bankName = findViewById(R.id.bankName)
        cityOfBank = findViewById(R.id.cityOfBank)
        siteOfBank = findViewById(R.id.siteOfBank)
        numberPhoneOfBank = findViewById(R.id.numberPhoneOfBank)
        schemeOfBank = findViewById(R.id.schemeOfBank)
        brand = findViewById(R.id.brand)
        lengthBin = findViewById(R.id.length)
        luhn = findViewById(R.id.luhn)
        type = findViewById(R.id.type)
        prepaid = findViewById(R.id.prepaid)
        emoji = findViewById(R.id.emoji)
        country = findViewById(R.id.country)
        latitude = findViewById(R.id.latitude)
        longitude = findViewById(R.id.longitude)

        binCards.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s != null) {
                    var length = s.length
                    if (length == 8){
                        var newURL = URL + s

                        GlobalScope.launch() {
                            try {
                                MainData = Json.decodeFromString<MainData>(readJSON(newURL))

                                withContext(Dispatchers.Main) {
                                    bankName.setText(MainData.bank.name)
                                    cityOfBank.setText(MainData.bank.city)
                                    siteOfBank.setText(MainData.bank.url)
                                    numberPhoneOfBank.setText(MainData.bank.phone)
                                    schemeOfBank.setText(MainData.scheme)
                                    brand.setText(MainData.brand)
                                    lengthBin.setText(MainData.number.length.toString())
                                    if (MainData.number.luhn == true) luhn.setText("Yes")
                                    else luhn.setText("No")
                                    type.setText(MainData.type)
                                    if (MainData.prepaid == true) prepaid.setText("Yes")
                                    else prepaid.setText("No")
                                    emoji.setText(MainData.country.emoji)
                                    country.setText(MainData.country.name)
                                    latitude.setText(MainData.country.latitude.toString())
                                    longitude.setText(MainData.country.longitude.toString())
                                }
                            }
                            catch (error: FileNotFoundException){
                                withContext(Dispatchers.Main) {
                                    val errorMessage = ErrorDialog()
                                    errorMessage.show(supportFragmentManager, "errorMessage")
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
