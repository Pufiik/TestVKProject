package ru.pugovishnikova.example.testvkproject.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.pugovishnikova.example.testvkproject.managers.ProductManager
import ru.pugovishnikova.example.testvkproject.models.Product
import ru.pugovishnikova.example.testvkproject.utilites.State

class ProductViewModel:  ViewModel() {

    private val state = MutableStateFlow<State<List<Product>>>(State.Idle())
    private val limit: Int = 5
    private var skip: Int = 0
    private val listProduct: MutableList<Product> = mutableListOf()
    fun requireState() = state.asStateFlow()

    fun getData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                state.value = State.Loading()
                delay(2000L)
                try {
                    val result = ProductManager.getData(skip, limit)
                    listProduct.addAll(result.data)
                    state.value = State.Success(listProduct)
                    skip += limit
                } catch (e: Exception) {
                    state.value = State.Fail(e)
                }
            }
        }
    }
}
