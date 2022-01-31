# -*- coding: utf-8 -*-
{
    'name': "Almacén de productos",

    'summary': """
    Aplicación destinada a manejar el proceso de recibimiento de productos de una empresa de transportes""",

    'description': """
    Aplicación destinada a manejar el proceso de recibimiento de productos de una empresa de transportes
    """,

    'author': "Equipo",
    #Indicamos que es una aplicación
    'application': True,

    # En la siguiente URL se indica que categorias pueden usarse
    # https://github.com/odoo/odoo/blob/14.0/odoo/addons/base/data/ir_module_category_data.xml
    # Vamos a utilizar la categoria Productivity
    'category': 'Productivity',
    'version': '0.1',

    # Indicamos lista de modulos necesarios para que este funcione correctamente
    # En este ejemplo solo depende del modulo "base"
    'depends': ['base'],

    # Esto siempre se carga
    'data': [
        #El primer fichero indica la politica de acceso del modelo
        #Mas información en https://www.odoo.com/documentation/14.0/es/developer/howtos/rdtraining/05_securityintro.html
        'security/ir.model.access.csv',
        #Cargamos las vistas y las plantillas
        'views/pedidos.xml',
        'views/productos.xml',
    ]
}
