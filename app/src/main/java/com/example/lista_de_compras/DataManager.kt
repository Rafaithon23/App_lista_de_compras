package com.example.lista_de_compras

import com.example.lista_de_compras.models.ItemLista
import com.example.lista_de_compras.models.ListaDeCompras
import com.example.lista_de_compras.models.User

object DataManager {
    private var nextListId = 1
    private var nextItemId = 1

    var currentUser: User? = null
    val users = mutableListOf<User>()
    val listas = mutableListOf<ListaDeCompras>()
    val itens = mutableListOf<ItemLista>()

    init {
        val testUserEmail = "teste@teste.com"

        addUser(User("Usuário Teste", testUserEmail, "123456"))

        val item1 = ItemLista(
            nome = "Banana",
            quantidade = 6.0,
            unidade = "un",
            categoria = "FRUTA",
            comprado = false
        )
        val item2 = ItemLista(
            nome = "Contra Filé",
            quantidade = 1.5,
            unidade = "kg",
            categoria = "CARNE",
            comprado = true
        )

        val listaSupermercado = ListaDeCompras(
            titulo = "Supermercado Semanal",
            userId = testUserEmail,
            imagemUri = null
        )

        val listaFeira = ListaDeCompras(
            titulo = "Feira",
            userId = testUserEmail,
            imagemUri = null
        )

        addLista(listaSupermercado)
        addLista(listaFeira)

        item1.listaId = 1
        item2.listaId = 1
        addItem(item1)
        addItem(item2)

        currentUser = users.find { it.email == testUserEmail }
    }

    fun addUser(user: User) {
        if (users.none { it.email == user.email }) {
            users.add(user)
        }
    }

    fun addLista(lista: ListaDeCompras) {
        lista.id = nextListId++
        listas.add(lista)
    }

    fun updateLista(oldId: Int, newLista: ListaDeCompras) {
        val index = listas.indexOfFirst { it.id == oldId }
        if (index != -1) {
            newLista.id = listas[index].id
            newLista.userId = listas[index].userId
            listas[index] = newLista
        }
    }

    fun deleteLista(listId: Int) {
        listas.removeIf { it.id == listId }
        itens.removeIf { it.listaId == listId }
    }

    fun getUserLists(): List<ListaDeCompras> {
        val userEmail = currentUser?.email ?: return emptyList()

        return listas
            .filter { it.userId == userEmail }
            .sortedBy { it.titulo.lowercase() }
    }

    fun getListaById(id: Int): ListaDeCompras? {
        return listas.find { it.id == id }
    }

    fun addItem(item: ItemLista) {
        item.id = nextItemId++
        itens.add(item)
    }

    fun updateItem(oldId: Int, newItem: ItemLista) {
        val index = itens.indexOfFirst { it.id == oldId }
        if (index != -1) {
            newItem.id = itens[index].id
            newItem.listaId = itens[index].listaId
            itens[index] = newItem
        }
    }

    fun deleteItem(itemId: Int) {
        itens.removeIf { it.id == itemId }
    }

    fun getItensForLista(listId: Int): List<ItemLista> {
        return itens.filter { it.listaId == listId }.sortedBy { it.nome.lowercase() }
    }

    fun toggleComprado(itemId: Int) {
        val item = itens.find { it.id == itemId }
        item?.let { it.comprado = !it.comprado }
    }

    fun clearUserData() {
        currentUser = null
    }
}