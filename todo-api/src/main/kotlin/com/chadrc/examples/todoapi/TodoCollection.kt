package com.chadrc.examples.todoapi

data class TodoCollection(var name: String, val todos: ArrayList<Todo> = ArrayList()) {
    var id: Int = -1
}