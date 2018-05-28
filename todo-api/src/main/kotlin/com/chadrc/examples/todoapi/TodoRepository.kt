package com.chadrc.examples.todoapi

import org.springframework.data.repository.CrudRepository

interface TodoRepository : CrudRepository<Todo, Int> {
}