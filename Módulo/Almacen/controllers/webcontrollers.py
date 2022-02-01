# -*- coding: utf-8 -*-
from odoo import http
from odoo.http import request
import json

#Clase del controlador web
class Main(http.Controller):
    @http.route('/almacen/pedidoCompletado<idPedido>', type='http', auth='none')
    def obtenerDatosEquiposJSON(self, idPedido, **kw):
        record = http.request.env["pedidos"].sudo().search([('id', '=', idPedido)])
        
        for pedido in record:
            pedido