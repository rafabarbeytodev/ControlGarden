package com.rafabarbeytodev.android.kotlin.controlgarden.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.rafabarbeytodev.android.kotlin.controlgarden.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(),OnClickListener {

    private lateinit var mBinding : ActivityMainBinding

    private lateinit var mAdapter: MeasureAdapter
    private lateinit var mGridLayout: GridLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setupRecyclerView() //Configuracion del RecyclerView

        //Cargamos unos datos iniciales que luego eliminaremos,
        // ya que cargaremos los datos del repositorio
        val measure = Measure(
            date = "13/07/2022 10:44:00",
            groundTemp = "14ºC",
            airTemp = "18ºC",
            rh = "60%",
            lastIrrigateDate = "13/07/2022",
            lastIrrigateTime = " 07:00:00",
            timeIrrigate = "00:10:00")
        mAdapter.add(measure)  //Esta función se crea en el Adaptador (MeasureAdapter)

    }

    private fun setupRecyclerView() {
        mAdapter = MeasureAdapter(mutableListOf(),this)  //Inicializamos el adpatadr con una lista vacia mutableListof()
        mGridLayout = GridLayoutManager(this,1)

        mBinding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = mGridLayout
            adapter = mAdapter
        }
    }

    override fun onClick(measure: Measure) {

    }


}