package ru.pugovishnikova.example.testvkproject.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.pugovishnikova.example.testvkproject.managers.CategoryManager
import ru.pugovishnikova.example.testvkproject.managers.SearchManager
import ru.pugovishnikova.example.testvkproject.models.Product
import ru.pugovishnikova.example.testvkproject.providers.CategoryProvider
import ru.pugovishnikova.example.testvkproject.utilites.State

class CategoryViewModel: ViewModel(){

    private val state = MutableStateFlow<State<List<Product>>>(State.Idle())

    fun requireState() = state.asStateFlow()

    fun getData(name: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                state.value = State.Loading()
                delay(2000L)
                try {
                    val result = CategoryManager.getProductsByCategories(name)
                    state.value = State.Success(result.data)
                } catch (e: Exception) {
                    state.value = State.Fail(e)
                }
            }
        }
    }
}