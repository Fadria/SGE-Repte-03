package com.jovian.mispedidos.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.jovian.mispedidos.adapter.PedidoAdapter
import com.jovian.mispedidos.databinding.ActivityMainBinding
import com.jovian.mispedidos.model.Pedido
import com.jovian.mispedidos.model.Producto
import java.lang.Thread.sleep

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var listaPedidos : MutableList<Pedido> = mutableListOf()
    private var pedido: Pedido? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Pedido.getPedidos(this)

        InitRecycler()

    }

    fun InitRecycler(){
        binding.rvPedidos.layoutManager = LinearLayoutManager(this)
        val adapter = PedidoAdapter(this)
        binding.rvPedidos.adapter = adapter
    }


}