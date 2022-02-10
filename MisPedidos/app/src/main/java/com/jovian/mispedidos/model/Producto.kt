package com.jovian.mispedidos.model


/**
 * @author Cassandra Sowa, Federico Adria, Esther Talavera, Javier Tamarit, Jorge Victoria
 * @since 10Feb2022
 * @version 1.0
 * @description: Sencillo data class para mostrar los datos que describen un producto.
 * Desde el api rest solo cogemos el id y nombre del producto
 * el campo checked sirve para controlar que si un producto ya ha sido escaneado
 */
data class Producto(
    var checked: Boolean,
    val idProducto: Long,
    val nombre: String
) {

}
