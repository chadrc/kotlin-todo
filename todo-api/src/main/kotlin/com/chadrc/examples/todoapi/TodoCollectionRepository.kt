package com.chadrc.examples.todoapi

import org.springframework.data.mongodb.repository.MongoRepository

interface TodoCollectionRepository : MongoRepository<TodoCollection, Int> {
}