package com.chadrc.examples.todoapi

data class Todo(var text: String, var completed: Boolean = false) {
    var id: Int = -1
}