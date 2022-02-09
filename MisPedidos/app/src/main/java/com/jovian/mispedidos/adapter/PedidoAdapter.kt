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

class PedidoAdapter(private var ctx: Context) :
    RecyclerView.Adapter<PedidoAdapter.MyViewHolder>() {
    var itemClickListener: AdapterView.OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v: View = LayoutInflater.from(ctx).inflate(R.layout.item_pedido, parent, false)
        return MyViewHolder(v)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        if(Pedido.listaPedidos[position].checked == true) holder.check.setChecked(true)
        holder.productoid.text = "Numero de pedido" + Pedido.listaPedidos[position].idPedido.toString()

        holder.itemView.setOnClickListener {
            val intent = Intent(ctx, ProductosActivity::class.java)
            intent.putExtra("posicion", position)
            ctx.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return Pedido.listaPedidos.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var productoid: TextView
        var check : CheckBox

        init {
            check = itemView.findViewById<View>(R.id.checkBox) as CheckBox
            productoid = itemView.findViewById<View>(R.id.tvItemPedido) as TextView
        }
    }
}