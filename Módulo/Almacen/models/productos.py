# -*- coding: utf-8 -*-
from odoo import models, fields, api
from odoo.exceptions import ValidationError


class Productos(models.Model):
    # Nombre y descripcion del modelo
    _name = 'productos'
    _description = 'Productos de la empresa de transporte'

    # Parametros de ordenacion por defecto
    _order = 'identificador'

    # ATRIBUTOS

    # PARA CUANDO NO HAY UN ATRIBUTO LLAMADO NAME PARA MOSTRAR NOMBRE DE UN REGISTRO
    # https://www.odoo.com/es_ES/forum/ayuda-1/how-defined-display-name-in-custom-many2one-91657
    
    # Indicamos que atributo sera el que se usara para mostrar nombre.
    # Por defecto es "name", pero si no hay un atributo que se llama name, aqui lo indicamos
    # Aqui indicamos que se use el atributo "identificador"
    _rec_name = 'identificador'    

    # Lista de los estados que podrá tener un pedido
    ESTADOSPEDIDO = [
        ('0', 'En espera'),
        ('1', 'Recibido')
    ]

    #Elementos de cada fila del modelo de datos
    #Los tipos de datos a usar en el ORM son 
    # https://www.odoo.com/documentation/14.0/developer/reference/addons/orm.html#fields

    identificador = fields.Integer(string = "ID Producto", readonly = True, default = lambda self: self.env['ir.sequence'].
    next_by_code('increment_your_field'))
    nombre = fields.Char("Nombre del producto")
    descripcion = fields.Html('Descripción', sanitize=True, strip_style=False)
    precio = fields.Float("Precio")
    pedidos = fields.Many2one("pedidos", "Pedidos")