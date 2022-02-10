package com.jovian.mispedidos.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.jovian.mispedidos.R
import com.jovian.mispedidos.model.Pedido
import com.jovian.mispedidos.ui.ProductosActivity
import com.jovian.mispedidos.utils.QRActivity

/**
* @author Cassandra Sowa, Federico Adria, Esther Talavera, Javier Tamarit, Jorge Victoria
* @since 10Feb2022
* @version 1.0
* @description: Adapter para mostrar un listado con los productos de un pedido que ha llegado desde la central
 * lleva incluida la clase viewholder para tener los elementos  en un mismo conjunto
**/

class ProductoAdapter (private var ctx: Context, posicion: Int) :
    RecyclerView.Adapter<ProductoAdapter.MyViewHolder>() {
    var itemClickListener: AdapterView.OnItemClickListener? = null
    var pos: Int = posicion

    //clase para capturar los diferentes elementos del item y darles un nombre de variable.
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

    //metodo para rellenar con datos los items del listado
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        if(Pedido.listaPedidos[pos].productos.get(position).checked == true) holder.check.setChecked(true)
        holder.productoid.text =  Pedido.listaPedidos[pos].productos.get(position).idProducto.toString()
        holder.productoName.text = Pedido.listaPedidos[pos].productos.get(position).nombre

        //DANGEROUS ZONE. Desde aqui al pulsar click sobre el item, abrimos el scanner de codigos qr
        holder.itemView.setOnClickListener {
            //primero tenemos que controlar si el producto ya ha sido chqueado.
            //en caso correcto, mostramos un toast
            if(Pedido.listaPedidos[pos].productos.get(position).checked == true){
                Toast.makeText(ctx, "Este producto ya ha sido escaneado", Toast.LENGTH_SHORT).show()
            }
            //si el producto no ha sido chequeado, ya podemos abrir el escaner de codigos qr
            //le pasamos el id del producto que va a ser lo que va a escanear
            //ademas le pasamos la posicion del producto en el array de productos
            //y la posicion del pedido en el array de la lista de pedidos
            //para que en el caso de que la lectura sea correcta, marque el check de visto
            else {
                val intent = Intent(ctx, QRActivity::class.java)
                intent.putExtra(
                    "idProducto",
                    Pedido.listaPedidos[pos].productos.get(position).idProducto
                )
                intent.putExtra("posicion", position)
                intent.putExtra("posPedido", pos)
                ctx.startActivity(intent)
            }
        }


    }
}