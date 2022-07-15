package com.rafabarbeytodev.android.kotlin.controlgarden.view

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.database.IgnoreExtraProperties

@Entity(tableName = "MeasureEntity")
@IgnoreExtraProperties
data class MeasureEntity(

    @PrimaryKey(autoGenerate = true) var id:Long = 0,
    var date:String,
    var groundTemp:String = "",
    var airTemp:String = "",
    var rh:String = "",
    var lastIrrigateDate:String = "",
    var lastIrrigateTime:String = "",
    var timeIrrigate:String = "")
