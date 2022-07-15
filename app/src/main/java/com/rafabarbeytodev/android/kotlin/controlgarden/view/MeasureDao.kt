package com.rafabarbeytodev.android.kotlin.controlgarden.view

import androidx.room.*

@Dao
interface MeasureDao {

    @Query("SELECT * FROM MeasureEntity")
    fun getAllMeasures() : MutableList<MeasureEntity>

    @Insert
    fun addMeasure(measureEntity: MeasureEntity)

    @Update
    fun updateMeasure(measureEntity: MeasureEntity)

    @Delete
    fun deleteMeasure(measureEntity: MeasureEntity)
}
