package com.jovian.mispedidos.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.jovian.mispedidos.R
import com.jovian.mispedidos.adapter.PedidoAdapter
import com.jovian.mispedidos.adapter.ProductoAdapter
import com.jovian.mispedidos.databinding.ActivityProductosBinding
import kotlin.properties.Delegates

class ProductosActivity : AppCompatActivity() {

   private lateinit var binding: ActivityProductosBinding
   private var pos: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.hasExtra("posicion")){
            pos = intent.getIntExtra("posicion",0)
            openRecycler()
        }


    }

    private fun openRecycler() {
        binding.rvProductos.layoutManager = LinearLayoutManager(this)
        val adapter = ProductoAdapter(this, pos)
        binding.rvProductos.adapter = adapter
    }
}