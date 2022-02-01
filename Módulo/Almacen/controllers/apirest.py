# -*- coding: utf-8 -*-
from odoo import http
from odoo.http import request
import json

# Clase del controlador web



class ApiRest(http.Controller):
    

    '''

    Probar GET (Consultando socio 3)
    Valor de data: {"num_socio":"3"}
    URL COMPLETA (Enviada con GET): http://localhost:8069/almacen/apirest/pedidos
    '''
    @http.route('/almacen/apirest/pedidos', auth="none", cors='*', csrf=False, methods=["GET"],
                type='http')
    def apiGet(self, **args):
        #Si es GET, ddeolvemos el registro de la busqueda
        record = http.request.env["pedidos"].sudo().search([('estado', '=', "0")])

        diccionarioCompleto = {}
        listaPedidos = {}
        if record and record[0]:
            for pedido in record:
                listaProductos = []
                for producto in pedido.productos:
                    datosProducto = http.request.env["productos"].sudo().search([('id', '=', producto.id)])
                    if datosProducto and datosProducto[0]:
                        contenidoProducto = {'id': datosProducto.id, 'nombre': datosProducto.nombre}
                        listaProductos.append(contenidoProducto)
                listaPedidos[pedido.id] = listaProductos
                diccionarioCompleto["pedidos"] = (listaPedidos)

            #diccionarioCompleto["pedidos"] = listaPedidos
            return http.Response( 
            json.dumps(diccionarioCompleto, default=str), 
                status=200,
                mimetype='application/json'
            )

        return "{'estado':'NOTFOUND'}"        

    @http.route('/almacen/apirest/producto/<idproducto>', auth="none", cors='*', csrf=False, methods=["GET"],
                type='http')
    def a(self, idproducto, **args):
        #Si es GET, ddeolvemos el registro de la busqueda
        record = http.request.env["productos"].sudo().search([('id', '=', idproducto)])

        if record and record[0]:
            return http.Response( 
            json.dumps(record.read(), default=str), 
                status=200,
                mimetype='application/json'
            )

        return "{'estado':'NOTFOUND'}"     