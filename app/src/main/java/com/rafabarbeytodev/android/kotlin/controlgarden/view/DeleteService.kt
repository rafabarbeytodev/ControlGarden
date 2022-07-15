package com.rafabarbeytodev.android.kotlin.controlgarden.view

import retrofit2.Call
import retrofit2.http.*

interface DeleteService {

    @Headers("Content-Type: application/json")
    @DELETE(Constants.API_PATH + Constants.DELETE_PATH)

    fun deleteMeasures(@Path("id") id: String) : Call<MeasureEntity>

}