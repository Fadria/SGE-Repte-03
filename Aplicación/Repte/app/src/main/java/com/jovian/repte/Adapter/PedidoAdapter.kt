package com.jovian.repte.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jovian.repte.R
import com.jovian.repte.model.Pedido

class PedidoAdapter (val pedidos: List<Pedido>): RecyclerView.Adapter<PedidoAdapter.PedidoViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoViewHolder {
        val layoutInflate = LayoutInflater.from(parent.context)

        return PedidoViewHolder(layoutInflate.inflate(R.layout.item_pedido,parent, false))

    }

    override fun getItemCount(): Int = pedidos.size

    override fun onBindViewHolder(holder: PedidoViewHolder, position: Int) {
        val item: Pedido = pedidos[position]

        holder.tv_id.setText(item.idPedido.toString())
    }

    inner class PedidoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tv_id : TextView = itemView.findViewById(R.id.tvIdproduct)
        val tv_name : TextView = itemView.findViewById(R.id.tvNameProduct)
    }

}