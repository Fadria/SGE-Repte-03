package com.jovian.repte.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jovian.repte.Adapter.PedidoAdapter
import com.jovian.repte.databinding.ActivityMainBinding
import com.jovian.repte.model.Pedido
import com.jovian.repte.viewmodel.PedidosViewModel

class MainActivity : AppCompatActivity() {

    //variable para preparar el binding
    lateinit var binding: ActivityMainBinding

    private lateinit var adapter: PedidoAdapter
    private val pedidos = mutableListOf<Pedido>()

    // Variable que contiene la referencia al ViewModel de pedidos
    private lateinit var pedidosViewModel: PedidosViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //binding para los elementos del layout
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyvlerView()

        // Indicamos el fichero que contiene el ViewModel
        pedidosViewModel = ViewModelProvider(this)[PedidosViewModel::class.java]

        pedidosViewModel.getPedidos()

        // Observador del live data para los pedidos
        pedidosViewModel.pedidosListLD.observe(this){
            pedidos.clear()
            pedidos.addAll(it)
            adapter.notifyDataSetChanged()
        }

        // Observamos del live data para los errores
        pedidosViewModel.errorLD.observe(this){
            showError(it.toString())
        }
    }



    /**
     * funcion para crear el recycler view
     */
    private fun initRecyvlerView() {
        adapter = PedidoAdapter(pedidos)
        binding.rvProductos.layoutManager = LinearLayoutManager(this)
        binding.rvProductos.adapter = adapter
    }

    /**
     * para mostrar en pantalla un mensaje cuando no el personaje no tenga amiibos
     */
    private fun showError(mensaje: String) {
        Toast.makeText(this, "Error: ${mensaje}", Toast.LENGTH_SHORT).show()
    }
}