package com.rafabarbeytodev.android.kotlin.controlgarden.view

import androidx.room.Database
import androidx.room.RoomDatabase

@Database (entities = [MeasureEntity::class], version = 1)
abstract class MeasureDatabase : RoomDatabase() {
    abstract fun measureDao():MeasureDao
}