package com.rafabarbeytodev.android.kotlin.controlgarden.view

data class GetResponse(var id:Long,
                       var date:String,
                       var groundTemp:String,
                       var airTemp:String ,
                       var rh:String,
                       var lastIrrigateDate:String,
                       var lastIrrigateTime:String,
                       var timeIrrigate:String) : SuccessResponse(id,date,groundTemp,airTemp,rh,
                                                    lastIrrigateDate,lastIrrigateTime,timeIrrigate)

