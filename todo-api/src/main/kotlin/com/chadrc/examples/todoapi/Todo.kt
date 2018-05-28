package com.chadrc.examples.todoapi

import org.springframework.data.annotation.Id

data class Todo(
        var text: String,
        var completed: Boolean = false,
        @Id
        var id: Int = -1
)