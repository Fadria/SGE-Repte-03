# -*- coding: utf-8 -*-
from odoo import http
from odoo.http import request
import json

# Clase del controlador web



class ApiRest(http.Controller):
     

    '''
        Ejemplo de ruta: http://172.26.80.37:8069/almacen/apirest/obtenerPedidos
    '''
    @http.route('/almacen/apirest/obtenerPedidos', auth="none", cors='*', csrf=False, methods=["GET"],
                type='http')
    def obtenerPedidos(self, **args):
        #Si es GET, ddeolvemos el registro de la busqueda
        record = http.request.env["pedidos"].sudo().search([('estado', '=', '1')])

        diccionarioCompleto = {} # Diccionario principal
        listaPedidos = [] # Contendrá una lista de diccionarios de productos
        
        if record and record[0]: # Si disponemos de al menos un pedido procederemos
            for pedido in record: # Bucle que contendrá cada pedido
                diccionarioPedido = {} # Diccionario del pedido
                diccionarioPedido["idPedido"] = pedido.id # Añadimos la id del pedido al diccionario

                listaProductos = [] # Lista que contendrá todos los productos del pedido

                # Obtenemos todos los productos de este pedido
                productosDelPedido = http.request.env["productospedido"].sudo().search([('pedido', '=', pedido.id)])

                # Bucle ejecutado por cada producto del pedido
                for productoDelPedido in productosDelPedido:
                    diccionarioProducto = {} # Diccionario del producto, contendrá la id y el nombre
                    diccionarioProducto["idProducto"] = productoDelPedido.producto.id # Añadimos la id del producto
                    diccionarioProducto["nombre"] = productoDelPedido.producto.nombre # Añadimos el nombre del producto

                    listaProductos.append(diccionarioProducto) # Añadimos el diccionario a la lista de productos
                    diccionarioPedido["productos"] = listaProductos 

                listaPedidos.append(diccionarioPedido)
                # Finaliza el bucle de pedidos

            # Añadimos la lista de pedidos a nuestro diccionario
            diccionarioCompleto["pedidos"] = listaPedidos

            return http.Response( 
            json.dumps(diccionarioCompleto, default=str), 
                status=200,
                mimetype='application/json'
            )

        return "{'estado':'NOTFOUND'}"   

        return "{'estado':'NOTFOUND'}" 