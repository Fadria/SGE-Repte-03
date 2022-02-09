package com.jovian.mispedidos.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jovian.mispedidos.R
import com.jovian.mispedidos.model.Pedido
import com.jovian.mispedidos.ui.ProductosActivity

class ProductoAdapter (private var ctx: Context, posicion: Int) :
    RecyclerView.Adapter<ProductoAdapter.MyViewHolder>() {
    var itemClickListener: AdapterView.OnItemClickListener? = null
    var pos: Int = posicion



    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var check : CheckBox
        var productoid: TextView
        lateinit var productoName: TextView

        init {

            check = itemView.findViewById<View>(R.id.checkBox) as CheckBox
            productoid = itemView.findViewById<View>(R.id.tvIdproduct) as TextView
            productoName = itemView.findViewById<View>(R.id.tvNameProduct) as TextView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoAdapter.MyViewHolder {
        val v: View = LayoutInflater.from(ctx).inflate(R.layout.item_producto, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return Pedido.listaPedidos[pos].productos.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.check
        holder.productoid.text =  Pedido.listaPedidos[pos].productos.get(position).idProducto.toString()
        holder.productoName.text = Pedido.listaPedidos[pos].productos.get(position).nombre


    }
}