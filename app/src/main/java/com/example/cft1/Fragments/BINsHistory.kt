package com.example.cft1.Fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cft1.Adapters.BINsHistoryAdapter
import com.example.cft1.DataBase.DBProvider
import com.example.cft1.DTOs.Bank
import com.example.cft1.DTOs.Country
import com.example.cft1.DTOs.Number
import com.example.cft1.Dialogs.BINInfoDialog
import com.example.cft1.Models.*
import com.example.cft1.R
import com.example.cft1.Listeners.RecyclerItemClickListener


const val ARG_OBJECT = "object"

class HistoryOfLookUp() : Fragment() {
    private lateinit var db: DBProvider
    private var adapter = BINsHistoryAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bins_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var recyclerView: RecyclerView

        recyclerView = view.findViewById(R.id.historyRecyclerView)

        recyclerView.layoutManager = LinearLayoutManager(getActivity())
        recyclerView.adapter = adapter

        recyclerView.setOnItemClickListener{}

        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            (recyclerView.layoutManager as LinearLayoutManager).getOrientation()
        )
        recyclerView.addItemDecoration(dividerItemDecoration)
        view.setBackgroundColor(Color.parseColor("#000000"))
    }

    inline fun RecyclerView.setOnItemClickListener(crossinline listener: (position: Int) -> Unit) {
        addOnItemTouchListener(
            RecyclerItemClickListener(this,
            object : RecyclerItemClickListener.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    listener(position)

                    val infoMessage = getMainData(position)?.let { BINInfoDialog(it) }
                    if (infoMessage != null) {
                        infoMessage.show(parentFragmentManager, "infoMessage")
                    }
                }
            })
        )
    }

    fun getMainData(position: Int): BINInfoModel? {
        return adapter.binInfo?.get(position)
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onResume(){
        super.onResume()
        db =
            getActivity()?.let { DBProvider(it.getApplicationContext(), null) }!!

        adapter.update(getData())
    }

    @SuppressLint("Range")
    fun getData(): MutableList<BINInfoModel>{
        val cursor = db?.setData()
        var binInfoList = mutableListOf<BINInfoModel>()
        lateinit var binInfo: BINInfoModel
        lateinit var bank: Bank
        lateinit var country: Country
        lateinit var number: Number

        cursor!!.moveToFirst()

        bank = Bank(
            cursor.getString(cursor.getColumnIndex(DBProvider.NAME_BANK_COL)),
            cursor.getString(cursor.getColumnIndex(DBProvider.URL_COL)),
            cursor.getString(cursor.getColumnIndex(DBProvider.PHONE_COL)),
            cursor.getString(cursor.getColumnIndex(DBProvider.CITY_COL))
        )

        country = Country(
            cursor.getString(cursor.getColumnIndex(DBProvider.NUMERIC_COL)),
            cursor.getString(cursor.getColumnIndex(DBProvider.ALPHA2_COL)),
            cursor.getString(cursor.getColumnIndex(DBProvider.NAME_COUNTRY_COL)),
            cursor.getString(cursor.getColumnIndex(DBProvider.EMOJI_COL)),
            cursor.getString(cursor.getColumnIndex(DBProvider.CURRENCY_COL)),
            cursor.getInt(cursor.getColumnIndex(DBProvider.LATITUDE_COL)),
            cursor.getInt(cursor.getColumnIndex(DBProvider.LONGITUDE_COL))
        )

        val luhn = when (cursor.getInt(cursor.getColumnIndex(DBProvider.LUHN_COL))){
            1 -> true
            -1 -> false
            else -> null
        }
        number = Number(
            cursor.getInt(cursor.getColumnIndex(DBProvider.LENGTH_COL)),
            luhn
        )

        val prepaid = when (cursor.getInt(cursor.getColumnIndex(DBProvider.SCHEME_COL))){
            1 -> true
            -1 -> false
            else -> null
        }
        binInfo = BINInfoModel(
            cursor.getString(cursor.getColumnIndex(DBProvider.BIN_COl)),
            cursor.getString(cursor.getColumnIndex(DBProvider.SCHEME_COL)),
            number,
            cursor.getString(cursor.getColumnIndex(DBProvider.TYPE_COL)),
            cursor.getString(cursor.getColumnIndex(DBProvider.BRAND_COL)),
            prepaid,
            country,
            bank
        )

        binInfoList.add(binInfo)

        // перемещение курсора в следующую позицию и добавление значений
        while(cursor.moveToNext()){
            bank = Bank(
                cursor.getString(cursor.getColumnIndex(DBProvider.NAME_BANK_COL)),
                cursor.getString(cursor.getColumnIndex(DBProvider.URL_COL)),
                cursor.getString(cursor.getColumnIndex(DBProvider.PHONE_COL)),
                cursor.getString(cursor.getColumnIndex(DBProvider.CITY_COL))
            )

            country = Country(
                cursor.getString(cursor.getColumnIndex(DBProvider.NUMERIC_COL)),
                cursor.getString(cursor.getColumnIndex(DBProvider.ALPHA2_COL)),
                cursor.getString(cursor.getColumnIndex(DBProvider.NAME_COUNTRY_COL)),
                cursor.getString(cursor.getColumnIndex(DBProvider.EMOJI_COL)),
                cursor.getString(cursor.getColumnIndex(DBProvider.CURRENCY_COL)),
                cursor.getInt(cursor.getColumnIndex(DBProvider.LATITUDE_COL)),
                cursor.getInt(cursor.getColumnIndex(DBProvider.LONGITUDE_COL))
            )

            val luhn = when (cursor.getInt(cursor.getColumnIndex(DBProvider.LUHN_COL))){
                1 -> true
                -1 -> false
                else -> null
            }
            number = Number(
                cursor.getInt(cursor.getColumnIndex(DBProvider.LENGTH_COL)),
                luhn
            )

            val prepaid = when (cursor.getInt(cursor.getColumnIndex(DBProvider.SCHEME_COL))){
                1 -> true
                -1 -> false
                else -> null
            }
            binInfo = BINInfoModel(
                cursor.getString(cursor.getColumnIndex(DBProvider.BIN_COl)),
                cursor.getString(cursor.getColumnIndex(DBProvider.SCHEME_COL)),
                number,
                cursor.getString(cursor.getColumnIndex(DBProvider.TYPE_COL)),
                cursor.getString(cursor.getColumnIndex(DBProvider.BRAND_COL)),
                prepaid,
                country,
                bank
            )

            binInfoList.add(binInfo)
        }

        // наконец мы закрываем наш курсор
        cursor.close()

        binInfoList.reverse()

        return binInfoList
    }
}