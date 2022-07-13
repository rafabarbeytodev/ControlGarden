package com.rafabarbeytodev.android.kotlin.controlgarden.view

data class Measure(

    var id:Long = 0,
    var date:String,
    var groundTemp:String = "",
    var airTemp:String = "",
    var rh:String = "",
    var lastIrrigateDate:String = "",
    var lastIrrigateTime:String = "",
    var timeIrrigate:String = "")
