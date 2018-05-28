package com.chadrc.examples.todoapi

import org.springframework.data.mongodb.repository.MongoRepository

interface TodoRepository : MongoRepository<Todo, Int> {
}