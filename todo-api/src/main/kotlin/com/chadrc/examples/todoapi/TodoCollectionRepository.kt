package com.chadrc.examples.todoapi

import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface TodoCollectionRepository : MongoRepository<TodoCollection, ObjectId> {
}