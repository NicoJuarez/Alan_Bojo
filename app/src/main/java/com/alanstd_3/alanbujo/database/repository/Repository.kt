package com.alanstd_3.alanbujo.database.repository

import android.content.Context
import androidx.room.Room

class Repository(context: Context) {

    private val db = Room.databaseBuilder(
        context.applicationContext, BUJODataBase::class.java, BUJODataBase.DB_NAME
    ).build()


}