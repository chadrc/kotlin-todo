package com.chadrc.examples.todoapi

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id

class TodoCollection(
        var name: String,
        var todos: ArrayList<Todo> = ArrayList(),
        @Id
        private var _id: ObjectId = ObjectId()
) {
    var id: String
        get() = _id.toHexString()
        set(value) {
            _id = ObjectId(value)
        }
}