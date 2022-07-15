package com.rafabarbeytodev.android.kotlin.controlgarden.view

import retrofit2.Call
import retrofit2.http.*

interface GetService {

    @Headers("Content-Type: application/json")
    @GET(Constants.API_PATH + Constants.GET_PATH)

    fun getMeasures() : Call<List<MeasureEntity>>

}