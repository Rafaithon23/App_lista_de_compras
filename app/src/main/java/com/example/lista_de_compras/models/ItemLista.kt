package com.example.lista_de_compras.models

data class ItemLista(
    var id: Int = 0,
    var nome: String = "",
    var quantidade: Int = 1,
    var unidade: String = "un",  // Ex: "kg", "l"
    var categoria: String = "",
    var comprado: Boolean = false,
    var listaId: Int = 0
)