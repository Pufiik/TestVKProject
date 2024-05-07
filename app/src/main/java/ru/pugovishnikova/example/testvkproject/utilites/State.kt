package ru.pugovishnikova.example.testvkproject.utilites

sealed class State<out T> {
    class Idle : State<Nothing>()
    class Loading : State<Nothing>()
    class Success<out T>(val data:T) : State<T>()
    class Fail(val exception: Exception) : State<Nothing>()
}
