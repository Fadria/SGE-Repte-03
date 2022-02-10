# -*- coding: utf-8 -*-
from odoo import models, fields, api
from odoo.exceptions import ValidationError


class Productospedido(models.Model):
    # Nombre y descripcion del modelo
    _name = 'productospedido'
    _description = 'Tabla que contendrá los productos de un pedido en concreto'

    # ATRIBUTOS

    # PARA CUANDO NO HAY UN ATRIBUTO LLAMADO NAME PARA MOSTRAR NOMBRE DE UN REGISTRO
    # https://www.odoo.com/es_ES/forum/ayuda-1/how-defined-display-name-in-custom-many2one-91657
    
    # Indicamos que atributo sera el que se usara para mostrar nombre.
    # Por defecto es "name", pero si no hay un atributo que se llama name, aqui lo indicamos
    # Aqui indicamos que se use el atributo "id"
    _rec_name = 'id'    


    #Elementos de cada fila del modelo de datos
    #Los tipos de datos a usar en el ORM son 
    # https://www.odoo.com/documentation/14.0/developer/reference/addons/orm.html#fields

    pedido = fields.Many2one("pedidos", "ID del pedido")
    producto = fields.Many2one("productos", "ID del producto")
    recibido = fields.Boolean("Producto recibido")