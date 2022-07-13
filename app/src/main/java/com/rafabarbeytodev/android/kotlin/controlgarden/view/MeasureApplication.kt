package com.rafabarbeytodev.android.kotlin.controlgarden.view

import android.app.Application
import androidx.room.Room

/** object configura el patrón Singleton y companion lo hace accesible desde cualquier lugar */
class MeasureApplication : Application(){
    companion object{
        lateinit var database: MeasureDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(this,
            MeasureDatabase::class.java,
            "MeasureDatabase")
            .build()
    }

}