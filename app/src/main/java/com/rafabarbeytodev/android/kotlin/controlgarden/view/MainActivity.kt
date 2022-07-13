package com.rafabarbeytodev.android.kotlin.controlgarden.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.rafabarbeytodev.android.kotlin.controlgarden.databinding.ActivityMainBinding
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var mBinding: ActivityMainBinding

    private lateinit var mAdapter: MeasureAdapter
    private lateinit var mGridLayout: GridLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setupRecyclerView() //Configuracion del RecyclerView

        //Cargamos unos datos iniciales manualmente, pero en fases posteriores
        // cargaremos los datos con retrofit
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        val currentDate = sdf.format(Date())

        val measureEntity = MeasureEntity(
            date = currentDate,
            groundTemp = "14ºC",
            airTemp = "18ºC",
            rh = "60%",
            lastIrrigateDate = "13/07/2022",
            lastIrrigateTime = " 07:00:00",
            timeIrrigate = "00:10:00"
        )

        //Insertamos los datos en Room de forma manual pero en fases posteriores
        //los incorporaremos con retrofit

        //Ejecutamos en un hilo independiente con ANKO
        doAsync {
            MeasureApplication.database.measureDao().addMeasure(measureEntity)
            uiThread {
                mAdapter.add(measureEntity)  //Esta función se crea en el Adaptador (MeasureAdapter)
            }
        }
    }

    private fun setupRecyclerView() {
        mAdapter = MeasureAdapter(
            mutableListOf(),
            this
        )  //Inicializamos el adaptador con una lista vacia mutableListof()
        mGridLayout = GridLayoutManager(this, 1)
        getMeasures()

        mBinding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = mGridLayout
            adapter = mAdapter
        }
    }
    private fun getMeasures(){
        //Usamos ANKO para el uso de hilos
        doAsync{
            val measures = MeasureApplication.database.measureDao().getAllMeasures()
            uiThread {
                mAdapter.setMeasures(measures)
            }
        }
    }

    override fun onClick(measureEntity: MeasureEntity) {

    }

    override fun onDeleteMeasure(measureEntity: MeasureEntity) {
        doAsync {
            MeasureApplication.database.measureDao().deleteMeasure(measureEntity)
            uiThread {
                mAdapter.delete(measureEntity)
            }
        }
    }
}