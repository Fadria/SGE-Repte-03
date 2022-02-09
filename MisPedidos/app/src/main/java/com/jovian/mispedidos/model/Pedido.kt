package com.jovian.mispedidos.model

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.jovian.mispedidos.ui.MainActivity

data class Pedido(
    var checked: Boolean,
    val idPedido: Long,
    val productos: ArrayList<Producto>


)
{
    companion object{

        var listaPedidos:MutableList<Pedido> = ArrayList()
        var existe: Boolean = false

        fun getPedidos(ctx: Context, onReceive: (idPedido: Long) -> Unit, onNew:()->Unit)
        {

            //Create an instances of Volley's queue
            var queue = Volley.newRequestQueue(ctx)

            //URL
            val url = "https://mocki.io/v1/ad331397-c6bf-4056-be43-ec671739c4fa"
            //val url = "http://172.26.80.44:8069/almacen/apirest/obtenerPedidos"
            //jsonObject Request
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                { response ->
                    //Response is the jsonObject retrieved from de URL
                    Log.i("RESPONSE:", "Response is: ${response}")
                    onReceive(9)
                    val pedidos = response.getJSONArray("pedidos")
                    for(num in 0..pedidos.length()-1){
                        val pedido = pedidos.getJSONObject(num)
                        val idPedido = pedido.getLong("idPedido")
                        compararPedido(idPedido)
                        if(existe) continue
                        else onNew()
                        val productos = pedido.getJSONArray("productos")
                        val listaProductos: ArrayList<Producto> = ArrayList<Producto>()
                        val objetoPedido = Pedido(false,idPedido, listaProductos)

                        for(nom in 0..productos.length()-1){
                            val producto = productos.getJSONObject(nom)
                            val idProducto = producto.getLong("idProducto")
                            val nombre = producto.getString("nombre")
                            objetoPedido.productos.add(Producto(false,idProducto,nombre))
                        }
                        listaPedidos.add(objetoPedido)
                        Log.i("tamaño", listaPedidos.size.toString())
                    }
                },
                { error -> error.printStackTrace() }
            )

            //Adding our request to the Volley's queue
            queue.add(jsonObjectRequest)

        }

        private fun compararPedido(idPedido: Long) {
            existe = false
            if(listaPedidos.isNotEmpty()){
                for(num in 0..listaPedidos.size-1){
                    if(listaPedidos[num].idPedido == idPedido){
                        existe = true
                    }
                }
            }
        }
    }
}


