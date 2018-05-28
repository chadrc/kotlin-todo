package com.chadrc.examples.todoapi

import org.springframework.data.repository.CrudRepository

interface TodoCollectionRepository : CrudRepository<TodoCollection, Int> {
}