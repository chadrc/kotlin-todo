package com.chadrc.examples.todoapi

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id

class TodoCollection(
        var name: String,
        val todos: ArrayList<Todo> = ArrayList(),
        @Id
        var id: ObjectId = ObjectId()
)