package com.rafabarbeytodev.android.kotlin.controlgarden.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.rafabarbeytodev.android.kotlin.controlgarden.R
import com.rafabarbeytodev.android.kotlin.controlgarden.databinding.ItemMeasureBinding

class MeasureAdapter(private var measures: MutableList<MeasureEntity>,
                     private var listener: OnClickListener) :
                                        RecyclerView.Adapter<MeasureAdapter.ViewHolder>() {

    private lateinit var mContext: Context


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val mBinding = ItemMeasureBinding.bind(view)

        fun setListener(measureEntity: MeasureEntity){
            with(mBinding.root){
                setOnClickListener{listener.onClick(measureEntity)}
                //Usamos el click mantenido para el borrado
                setOnLongClickListener {
                    listener.onDeleteMeasure(measureEntity)
                    true
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_measure, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val measure = measures.get(position)

        with(holder){
            setListener(measure)
            mBinding.tvDate.text  = measure.date
            mBinding.tvGroundTemp.text = measure.groundTemp
            mBinding.tvAirTemp.text = measure.airTemp
            mBinding.tvRH.text = measure.rh
            mBinding.tvLastIrrigationDate.text = measure.lastIrrigateDate
            mBinding.tvLastIrrigationTime.text = measure.lastIrrigateTime
            mBinding.tvTimeIrrigation.text = measure.timeIrrigate
        }

    }

    override fun getItemCount(): Int = measures.size

    fun add(measureEntity: MeasureEntity) {
        measures.add(measureEntity)
        notifyDataSetChanged() //Refrescamos la vista
    }

    fun setMeasures(measures: MutableList<MeasureEntity>) {
        this.measures = measures
        notifyDataSetChanged()

    }

    fun delete(measureEntity: MeasureEntity){
        val index = measures.indexOf(measureEntity)
        if(index != -1){
            measures.removeAt(index)
            notifyItemRemoved(index)
        }
    }
}