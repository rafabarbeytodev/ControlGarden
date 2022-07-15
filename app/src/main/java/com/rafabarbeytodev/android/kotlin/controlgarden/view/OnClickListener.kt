package com.rafabarbeytodev.android.kotlin.controlgarden.view

interface OnClickListener {

    fun onClick(measureEntity: MeasureEntity)
    fun onLoadMeasure(measureEntity: List<MeasureEntity>)
    fun onDeleteMeasure(measureEntity: MeasureEntity)
}