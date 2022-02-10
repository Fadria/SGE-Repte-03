# -*- coding: utf-8 -*-
from odoo import models, fields, api
from odoo.exceptions import ValidationError


class Proveedores(models.Model):
    # Nombre y descripcion del modelo
    _name = 'proveedores'
    _description = 'proveedores de la empresa de transporte'

    # Parametros de ordenacion por defecto
    _order = 'identificador'

    # ATRIBUTOS

    # PARA CUANDO NO HAY UN ATRIBUTO LLAMADO NAME PARA MOSTRAR NOMBRE DE UN REGISTRO
    # https://www.odoo.com/es_ES/forum/ayuda-1/how-defined-display-name-in-custom-many2one-91657
    
    # Indicamos que atributo sera el que se usara para mostrar nombre.
    # Por defecto es "name", pero si no hay un atributo que se llama name, aqui lo indicamos
    # Aqui indicamos que se use el atributo "identificador"
    _rec_name = 'identificador'    


    #Elementos de cada fila del modelo de datos
    #Los tipos de datos a usar en el ORM son 
    # https://www.odoo.com/documentation/14.0/developer/reference/addons/orm.html#fields

    identificador = fields.Integer(string = "ID proveedor", readonly = True, default = lambda self: self.env['ir.sequence'].
    next_by_code('increment_your_field'))
    nombre = fields.Char("Nombre del proveedor")
    direccion = fields.Char("Dirección del proveedor")
    telefono = fields.Char("Teléfono del proveedor")
    email = fields.Char("Email del proveedor")
    productospedido = fields.One2many("productospedido", "pedido")