package ru.pugovishnikova.example.testvkproject.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.pugovishnikova.example.testvkproject.managers.ItemProductManager
import ru.pugovishnikova.example.testvkproject.models.Product
import ru.pugovishnikova.example.testvkproject.utilites.State

class ItemProductViewModel: ViewModel() {

    private val state = MutableStateFlow<State<Product>>(State.Idle())
    fun requireState() = state.asStateFlow()

    fun getData(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                state.value = State.Loading()
                delay(2000L)
                try {
                    val result = ItemProductManager.getData(id)
                    state.value = State.Success(result)
                } catch (e: Exception) {
                    state.value = State.Fail(e)
                }
            }
        }
    }
}