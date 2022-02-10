package com.jovian.mispedidos.utils

/**
 * @author Cassandra Sowa, Federico Adria, Esther Talavera, Javier Tamarit, Jorge Victoria
 * @since 10Feb2022
 * @version 1.0
 * @description: clase para las constantes de la app
 **/

//constantes para las url
const val GET_PEDIDOS = "http://172.26.80.40:8069/almacen/apirest/obtenerPedidos"
const val SET_PEDIDOS = "http://172.26.80.40:8069/almacen/apirest/finalizarPedido/"
const val URL_APIFALSA = "https://mocki.io/v1/ad331397-c6bf-4056-be43-ec671739c4fa"

//constantes usadas para las notificaciones
const val PENDING_REQUEST = 10
const val CHANNEL_ID = "Notification_ID"
const val CHANNEL_NAME = "My Notifications"
const val NOTIFICATION_ID = 10
const val CHANNEL_DESCRIPTION = "MY NOTIFICATION"