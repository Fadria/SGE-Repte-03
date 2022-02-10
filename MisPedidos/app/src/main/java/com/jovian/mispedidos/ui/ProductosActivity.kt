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

/**
 * @author Cassandra Sowa, Federico Adria, Esther Talavera, Javier Tamarit, Jorge Victoria
 * @since 10Feb2022
 * @version 1.0
 * @description: Activity para los productos de la app
 * desde aqui invocamos distintas funciones para la lectura de datos de api y carga en recyclerview
 **/
class ProductosActivity : AppCompatActivity() {

   private lateinit var binding: ActivityProductosBinding
   private var pos: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //cuando nos llamen desde la main activity, recogemos la posicion del pedido en la lista de pedidos
        //para trabajar con el.
        //Llamamos a la funcion leer datos
        if(intent.hasExtra("posicion")){
            pos = intent.getIntExtra("posicion",0)
            GlobalScope.launch {
                leerDatos()
            }
        }


    }

    //esto creo que no es muy etico, pero aprovecho lo hecho en el main activity
    //para poder recargar la info del recycler view, sobretodo para los checks de lectura
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

    //desde esta funcion comprobamos si todos los productos del listado han sido chequeados
    //en caso positivo, marcamos a true el pedido el check del pedido, con lo cual ya podemos decirle
    //al servidor que el pedido esta completo
    //TODO crear el post
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
            finish()
        }
    }

    //funcion para cargar del recyclerview
    private fun openRecycler() {
        binding.rvProductos.layoutManager = LinearLayoutManager(this)
        val adapter = ProductoAdapter(this, pos)
        binding.rvProductos.adapter = adapter
    }




}