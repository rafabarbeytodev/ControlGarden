package com.rafabarbeytodev.android.kotlin.controlgarden.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.rafabarbeytodev.android.kotlin.controlgarden.databinding.ActivityMainBinding
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var mBinding: ActivityMainBinding

    private lateinit var mAdapter: MeasureAdapter
    private lateinit var mGridLayout: GridLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.progressBar.visibility = View.GONE
        setupRecyclerView() //Configuracion del RecyclerView

        val measureEntity: List<MeasureEntity> = emptyList()
        onLoadMeasure(measureEntity)

    }

    private fun setupRecyclerView() {
        mAdapter = MeasureAdapter(
            mutableListOf(),
            this
        )  //Inicializamos el adaptador con una lista vacia mutableListof()
        mGridLayout = GridLayoutManager(this, 1)
        //getMeasures()
        //loadMeasures()

        mBinding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = mGridLayout
            adapter = mAdapter
        }
    }

    private fun getMeasures() {
        //Usamos ANKO para el uso de hilos
        doAsync {
            mBinding.progressBar.visibility = View.VISIBLE
            val measures = MeasureApplication.database.measureDao().getAllMeasures()
            uiThread {
                mAdapter.setMeasures(measures)
                mBinding.progressBar.visibility = View.INVISIBLE
            }
        }
    }

    private fun loadMeasures() {

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(GetService::class.java)

        service.getMeasures().enqueue(
            object : Callback<List<MeasureEntity>> {
                override fun onResponse(
                    call: Call<List<MeasureEntity>>,
                    response: Response<List<MeasureEntity>>
                ) {
                    val result = response.body()
                    if (result != null) {
                        mBinding.progressBar.visibility = View.VISIBLE
                        result.forEach { measure ->
                            mAdapter.add(measure)  //Esta función se crea en el Adaptador (MeasureAdapter)
                        }
                        mBinding.progressBar.visibility = View.INVISIBLE
                        //saveMeasures(result)
                    }
                }

                override fun onFailure(call: Call<List<MeasureEntity>>, t: Throwable) {
                    Log.e("Retrofit", "Problemas con el servidor")
                }
            }
        )
    }

    private fun saveMeasures(measureEntity: List<MeasureEntity>) {
        //Insertamos los datos en Room
        //Ejecutamos en un hilo independiente con ANKO
        mBinding.progressBar.visibility = View.VISIBLE
        doAsync {
            measureEntity.forEach { measure ->
                MeasureApplication.database.measureDao().addMeasure(measure)
            }
            uiThread {
                measureEntity.forEach { measure ->
                    mAdapter.add(measure)  //Esta función se crea en el Adaptador (MeasureAdapter)
                }
            }
        }
        mBinding.progressBar.visibility = View.INVISIBLE
    }

    override fun onClick(measureEntity: MeasureEntity) {
        val id: String = measureEntity.id.toString()
        Toast.makeText(this, "Has clickado el id $id", Toast.LENGTH_LONG).show()
    }

    override fun onLoadMeasure(measureEntity: List<MeasureEntity>) {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(GetService::class.java)

        service.getMeasures().enqueue(
            object : Callback<List<MeasureEntity>> {
                override fun onResponse(
                    call: Call<List<MeasureEntity>>,
                    response: Response<List<MeasureEntity>>
                ) {
                    when (response.code()) {
                        200 -> {
                            val result = response.body()
                            if (result != null) {
                                mBinding.progressBar.visibility = View.VISIBLE
                                result.forEach { measure ->
                                    mAdapter.add(measure)  //Esta función se crea en el Adaptador (MeasureAdapter)
                                }
                                mBinding.progressBar.visibility = View.INVISIBLE
                            }
                        }
                        400 -> {
                            Log.e("Retrofit", "Problema 400 con el servidor")
                        }
                        else -> {
                            Log.e("Retrofit", "Problema ${response.code()} con el servidor")
                        }
                    }
                }

                override fun onFailure(call: Call<List<MeasureEntity>>, t: Throwable) {
                    Log.e("Retrofit", "Problemas con el servidor")
                }
            }
        )
    }

    override fun onDeleteMeasure(measureEntity: MeasureEntity) {

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(DeleteService::class.java)

        service.deleteMeasures(measureEntity.id.toString()).enqueue(
            object : Callback<MeasureEntity> {
                override fun onResponse(
                    call: Call<MeasureEntity>,
                    response: Response<MeasureEntity>
                ) {
                    when (response.code()) {
                        200 -> {
                            val result = response.body()
                            if (result != null) {
                                mBinding.progressBar.visibility = View.VISIBLE
                                mAdapter.delete(result)  //Esta función se crea en el Adaptador (MeasureAdapter)
                                mBinding.progressBar.visibility = View.INVISIBLE
                            }
                        }
                        400 -> {
                            Log.e("Retrofit", "Problema 400 con el servidor")
                        }
                        else -> {
                            Log.e("Retrofit", "Problema ${response.code()} con el servidor")
                        }
                    }
                }

                override fun onFailure(call: Call<MeasureEntity>, t: Throwable) {
                    Log.e("Retrofit", "Problemas con el servidor")
                }
            }
        )
        /*doAsync {
            mBinding.progressBar.visibility = View.VISIBLE
            MeasureApplication.database.measureDao().deleteMeasure(measureEntity)
            uiThread {
                mAdapter.delete(measureEntity)
                mBinding.progressBar.visibility = View.INVISIBLE
            }
        }*/
    }


}

