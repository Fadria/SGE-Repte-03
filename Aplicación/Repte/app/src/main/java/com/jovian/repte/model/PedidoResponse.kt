package com.jovian.repte.model

import com.google.gson.annotations.SerializedName

class PedidoResponse (

    // Campo que contiene la lista de usuarios
    @SerializedName("pedidos") var pedidos: List<Pedido>,

    )