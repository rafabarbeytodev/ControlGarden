package com.rafabarbeytodev.android.kotlin.controlgarden.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rafabarbeytodev.android.kotlin.controlgarden.R
import com.rafabarbeytodev.android.kotlin.controlgarden.databinding.ItemMeasureBinding

class MeasureAdapter(private var measures: MutableList<Measure>,
                     private var listener: OnClickListener) :
                                        RecyclerView.Adapter<MeasureAdapter.ViewHolder>() {

    private lateinit var mContext: Context


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val mBinding = ItemMeasureBinding.bind(view)

        fun setListener(measure: Measure){
            mBinding.root.setOnClickListener{listener.onClick(measure)}
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

    fun add(measure: Measure) {
        measures.add(measure)
        notifyDataSetChanged() //Refrescamos la vista
    }
}