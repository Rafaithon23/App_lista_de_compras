package com.example.lista_de_compras.models

data class ListaDeCompras(
    var id: Int = 0,
    var titulo: String = "",
    val userId: String,
    var imagemUri: String? = null
)