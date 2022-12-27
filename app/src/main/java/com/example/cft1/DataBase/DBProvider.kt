package com.example.cft1.DataBase

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.cft1.DTOs.BINInfoDTO

class DBProvider(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    // ниже приведен метод создания базы данных с помощью запроса sqlite
    override fun onCreate(db: SQLiteDatabase) {
        // ниже приведен запрос sqlite, в котором имена столбцов
        // наряду с их типами данных дается
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                //общие данные
                + ID_COL + " INTEGER PRIMARY KEY, " +
                BIN_COl + " TEXT, " +
                //BINInfo
                SCHEME_COL + " TEXT, " +
                TYPE_COL + " TEXT, " +
                BRAND_COL + " TEXT, " +
                PREPAID_COL + " INTEGER, " + //на деле булеан
                //Number
                LENGTH_COL + " INTEGER, " +
                LUHN_COL + " INTEGER, " + //на деле булеан
                //Country
                NUMERIC_COL + " TEXT, " +
                ALPHA2_COL + " TEXT, " +
                NAME_COUNTRY_COL + " TEXT, " +
                EMOJI_COL + " TEXT, " +
                CURRENCY_COL + " TEXT, " +
                LATITUDE_COL + " INTEGER, " +
                LONGITUDE_COL + " INTEGER, " +
                //Bank
                NAME_BANK_COL + " TEXT, " +
                URL_COL + " TEXT, " +
                PHONE_COL + " TEXT, " +
                CITY_COL + " TEXT" +
                ")")

        // мы вызываем sqlite
        // способ выполнения нашего запроса
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // этот метод предназначен для проверки того, существует ли таблица уже
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    // Этот метод предназначен для добавления данных в нашу базу данных
    fun getData(BINInfoDTO: BINInfoDTO, bin: String){

        // ниже мы создаем переменную значений содержимого
        val values = ContentValues()

        // мы вставляем наши значения в виде пары ключ-значение
        //общие данные
        values.put(BIN_COl, bin)
        //MainData
        values.put(SCHEME_COL, BINInfoDTO.scheme)
        values.put(TYPE_COL, BINInfoDTO.type)
        values.put(BRAND_COL, BINInfoDTO.brand)
        when (BINInfoDTO.prepaid){
            true -> values.put(PREPAID_COL, 1)
            false -> values.put(PREPAID_COL, -1)
        }
        //Number
        values.put(LENGTH_COL, BINInfoDTO.number.length)

        when (BINInfoDTO.number.luhn){
            true -> values.put(LUHN_COL, 1)
            false -> values.put(LUHN_COL, -1)
        }
        //Country
        values.put(NUMERIC_COL, BINInfoDTO.country.numeric)
        values.put(ALPHA2_COL, BINInfoDTO.country.alpha2)
        values.put(NAME_COUNTRY_COL, BINInfoDTO.country.name)
        values.put(EMOJI_COL, BINInfoDTO.country.emoji)
        values.put(CURRENCY_COL, BINInfoDTO.country.currency)
        values.put(LATITUDE_COL, BINInfoDTO.country.latitude)
        values.put(LONGITUDE_COL, BINInfoDTO.country.longitude)
        //Bank
        values.put(NAME_BANK_COL, BINInfoDTO.bank.name)
        values.put(URL_COL, BINInfoDTO.bank.url)
        values.put(PHONE_COL, BINInfoDTO.bank.phone)
        values.put(CITY_COL, BINInfoDTO.bank.city)

        // здесь мы создаем доступную для записи переменную нашей базы данных,
        // поскольку мы хотим вставить значение в нашу базу данных
        val db = this.writableDatabase

        // все значения вставляются в базу данных
        db.insert(TABLE_NAME, null, values)

        // наконец-то мы закрываем нашу базу данных
        db.close()
    }

    // приведенный ниже метод заключается в получении всех данных из нашей базы данных
    fun setData(): Cursor? {
        // здесь мы создаем читаемую переменную нашей базы данных,
        // поскольку хотим считывать из нее значение
        val db = this.readableDatabase

        // приведенный ниже код возвращает курсор для чтения данных из базы данных
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null)
    }

    companion object{ // здесь мы определили переменные для нашей базы данных
        private val DATABASE_NAME = "bankDateBase"
        private val DATABASE_VERSION = 1
        val TABLE_NAME = "bankDateBase_table"

        val ID_COL = "id"
        val BIN_COl = "bin"

        val SCHEME_COL = "scheme"
        val TYPE_COL = "type"
        val BRAND_COL = "brand"
        val PREPAID_COL = "age"

        val LENGTH_COL = "length"
        val LUHN_COL = "luhn"

        val NUMERIC_COL = "numeric"
        val ALPHA2_COL = "alpha2"
        val NAME_COUNTRY_COL = "country_name"
        val EMOJI_COL = "emoji"
        val CURRENCY_COL = "currency"
        val LATITUDE_COL = "latitude"
        val LONGITUDE_COL = "longitude"

        val NAME_BANK_COL = "bank_name"
        val URL_COL = "url"
        val PHONE_COL = "phone"
        val CITY_COL = "city"
    }
}