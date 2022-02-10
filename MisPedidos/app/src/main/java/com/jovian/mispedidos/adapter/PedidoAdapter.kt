package com.jovian.mispedidos.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.jovian.mispedidos.R
import com.jovian.mispedidos.model.Pedido
import com.jovian.mispedidos.ui.ProductosActivity


/**
 * @author Cassandra Sowa, Federico Adria, Esther Talavera, Javier Tamarit, Jorge Victoria
 * @since 10Feb2022
 * @version 1.0
 * @description: Adapter para mostrar un listado con los pedidos que llegan desde la central
 *
 */
class PedidoAdapter(private var ctx: Context) :
    RecyclerView.Adapter<PedidoAdapter.MyViewHolder>() {
    var itemClickListener: AdapterView.OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v: View = LayoutInflater.from(ctx).inflate(R.layout.item_pedido, parent, false)
        return MyViewHolder(v)
    }

    //metodo para rellenar con datos los items del listado
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        //si el pedido ha sido comprobado, o sea estan todos los productos,marcamos el check
        if(Pedido.listaPedidos[position].checked == true) holder.check.setChecked(true)
        holder.productoid.text = "Numero de pedido" + Pedido.listaPedidos[position].idPedido.toString()

        //Cuando pulsemos sobre el item, este hará una llamada a la activity que muestra el listado de productos del pedido
        //seleccionado. Pasamos la posicion del pedido en el array para que la activity muestre los productos correspondientes
        holder.itemView.setOnClickListener {
            val intent = Intent(ctx, ProductosActivity::class.java)
            intent.putExtra("posicion", position)
            ctx.startActivity(intent)
        }
    }

    //funcion que devuelve eñ total de pedidos en la lista
    override fun getItemCount(): Int {
        return Pedido.listaPedidos.size
    }

    //clase para capturar los diferentes elementos del item y darles un nombre de variable. La pongo dentro para tener los elementos del adapter juntos
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var productoid: TextView
        var check : CheckBox

        init {
            check = itemView.findViewById<View>(R.id.checkBox) as CheckBox
            productoid = itemView.findViewById<View>(R.id.tvItemPedido) as TextView
        }
    }
}