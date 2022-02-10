# -*- coding: utf-8 -*-
from odoo import http
from odoo.http import request
import json

# Clase que usaremos para los diferentes endpoints de la API
class ApiRest(http.Controller):
     

    '''
        Ejemplo de ruta: http://172.26.80.37:8069/almacen/apirest/obtenerPedidos

        Únicamente aceptaríamos peticiones GET y devolveríamos todos los pedidos
    '''
    @http.route('/almacen/apirest/obtenerPedidos', auth="none", cors='*', csrf=False, methods=["GET"],
                type='http')
    def obtenerPedidos(self, **args):
        #Si es GET, devolvemos el registro de la busqueda
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



    '''
        Ejemplo de ruta: http://172.26.80.44:8069/almacen/apirest/finalizarPedido/3

        Únicamente aceptaríamos peticiones PUT y actualizaríamos el pedido indicando que este se ha finalizado
    '''
    @http.route('/almacen/apirest/finalizarPedido/<pedido>', auth="none", cors='*', csrf=False, methods=["PUT"],
                type='json')
    def finalizarPedido(self, pedido, **args):
        # Obtenemos el pedido cuya ID recibimos
        record = http.request.env["pedidos"].sudo().search([('id', '=', pedido)])
        
        # Recibimos un conjunto de datos, pero comprobamos que tengamos al menos un pedido
        if record and record[0]:
            # Bucle donde únicamente tendríamos el record[0]
            for pedido in record:
                # Si se trata de un pedido que se pueda visualizar en la aplicación móvil actualizaremos su estado a finalizado
                if pedido.estado == "1":
                    pedido.estado = "2"
                    return "{'actualizacion':'true'}" # Indicamos que la actualización se ha realizado

        return "{'actualizacion':'false'}" # Indicamos que la actualización no se ha realizado