package com.chadrc.examples.todoapi

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id

class TodoCollection(
        var name: String,
        val todos: ArrayList<Todo> = ArrayList(),
        @Id
        var _id: ObjectId = ObjectId()
) {
    var id: String
        get() = _id.toHexString()
        set(value) {
            _id = ObjectId(value)
        }
}