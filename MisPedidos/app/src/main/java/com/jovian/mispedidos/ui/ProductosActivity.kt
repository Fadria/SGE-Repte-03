package com.jovian.mispedidos.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.jovian.mispedidos.R
import com.jovian.mispedidos.adapter.PedidoAdapter
import com.jovian.mispedidos.adapter.ProductoAdapter
import com.jovian.mispedidos.databinding.ActivityProductosBinding
import com.jovian.mispedidos.model.Pedido
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
            GlobalScope.launch {
                leerDatos()
            }
        }


    }

    private suspend fun leerDatos() {
        for(num in 1..1000) {
            Pedido.getPedidos(this, {
                openRecycler()
            }, {

            })
            delay(10000)
            comprobarChecks()
        }
    }

    private fun comprobarChecks() {
        var estanTodos = true
        Log.i("cantidad", Pedido.listaPedidos.get(pos).productos.size.toString())
        for(num in 0..Pedido.listaPedidos.get(pos).productos.size -1){
            if(Pedido.listaPedidos.get(pos).productos.get(num).checked == false){
                Log.i("chequeado",Pedido.listaPedidos.get(pos).productos.get(num).checked.toString() )
                estanTodos = false
                break
            }
        }
        if(estanTodos){
            Pedido.listaPedidos.get(pos).checked = true
        }
    }

    private fun openRecycler() {
        binding.rvProductos.layoutManager = LinearLayoutManager(this)
        val adapter = ProductoAdapter(this, pos)
        binding.rvProductos.adapter = adapter
    }




}