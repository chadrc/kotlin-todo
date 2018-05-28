package com.chadrc.examples.todoapi

import org.springframework.data.annotation.Id

data class TodoCollection(
        var name: String,
        val todos: ArrayList<Todo> = ArrayList(),
        @Id
        var id: Int = -1
)