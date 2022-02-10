package com.jovian.mispedidos.model

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.jovian.mispedidos.ui.MainActivity
import com.jovian.mispedidos.utils.GET_PEDIDOS
import com.jovian.mispedidos.utils.URL_APIFALSA
import java.lang.reflect.InvocationTargetException

/**
 * @author Cassandra Sowa, Federico Adria, Esther Talavera, Javier Tamarit, Jorge Victoria
 * @since 10Feb2022
 * @version 1.0
 * @description: data class para mostrar los datos que describen un pedido.
 * Desde el api rest solo cogemos el id del pedido y la lista de productos que forman parte de dicho pedido
 * el campo checked sirve para controlar si un pedido ya ha sido escaneado
 */
data class Pedido(
    var checked: Boolean,
    val idPedido: Long,
    val productos: ArrayList<Producto>


)
{
    //desde aqui vamos a controlar la construccion de objetos pedido, para posteriormente trabajar con ellos
    companion object{

        //listado donde vamos a almacenar localmente los pedidos que nos llegan desde la central
        var listaPedidos:MutableList<Pedido> = ArrayList()
        //esta variable la usamos en la funcion de comparacion de id's de productos
        var existe: Boolean = false

        //funcion para leer pedidos desde la Api rest de odoo
        //ademas del contexto, recibe 2 callbacks del main para controlar las notificaciones
        //y que no se cargue el recyclerview de pedidos hasta que no haya finalizado la lectura de datos
        fun getPedidos(ctx: Context, onReceive: (idPedido: Long) -> Unit, onNew:()->Unit)
        {

            //Creamos una instancia de volley
            var queue = Volley.newRequestQueue(ctx)

            //URL Url de un api rest creado para pruebas sin servidor
            //val url = URL_APIFALSA
            //Url real de odoo
            //val url = GET_PEDIDOS
            //jsonObject Request
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET,
                GET_PEDIDOS,
                null,
                { response ->
                    //El valor Response es el objeto Json encontrado en la URL
                    //A partir de ahi vamos desgranando el response
                    //construyendo objetos pedido y producto
                    //y rellenando el array de lista de pedidos
                    Log.i("RESPONSE:", "Response is: ${response}")
                    onReceive(9)
                    //nos puede ocurrir que no hayan pedidos que leer, con lo cual creamos el try catch
                    //para recoger el error y que el programa no finalice por error
                    try {
                        val pedidos = response.getJSONArray("pedidos")
                        for (num in 0..pedidos.length() - 1) {
                            val pedido = pedidos.getJSONObject(num)
                            val idPedido = pedido.getLong("idPedido")
                            //OJO: tenemos que comprobar que el pedido leido no los tenemos en nuestro array
                            //asi que llamamos a la funcion que comprueba el id del pedido recibido con los que tenemos localmente
                            compararPedido(idPedido)
                            //si el pedido ya existe, rompemos el bucle para avanzar una posicion en el
                            if (existe) continue
                            //si el pedido no existe usamos el callback del main, para enviar una notificacion al movil
                            else onNew()
                            //y a partir de aqui seguimos con la construcciom
                            val productos = pedido.getJSONArray("productos")
                            val listaProductos: ArrayList<Producto> = ArrayList<Producto>()
                            val objetoPedido = Pedido(false, idPedido, listaProductos)

                            for (nom in 0..productos.length() - 1) {
                                val producto = productos.getJSONObject(nom)
                                val idProducto = producto.getLong("idProducto")
                                val nombre = producto.getString("nombre")
                                objetoPedido.productos.add(Producto(false, idProducto, nombre))
                            }
                            //una vez tenemos desgranado el responsey construidos los objetos, ya podemos almacenar el dato
                            listaPedidos.add(objetoPedido)
                            //para comprobacion del programador
                            Log.i("tamaño", listaPedidos.size.toString())
                        }
                        //en el caso de que no hayan pedidos, salvamos el error y mostramos un toast
                    } catch(e:Exception){
                        Toast.makeText(ctx, "No hay pedidos", Toast.LENGTH_LONG).show()
                    }
                },
                { error -> error.printStackTrace() }
            )

            //Adding our request to the Volley's queue
            queue.add(jsonObjectRequest)

        }

        //sencillo metodo para comprobar id's de pedido,
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


