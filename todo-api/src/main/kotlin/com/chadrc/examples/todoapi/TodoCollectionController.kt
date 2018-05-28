package com.chadrc.examples.todoapi

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TodoCollectionController(val todoCollectionRepository: TodoCollectionRepository) {

    @GetMapping
    fun findAll(): Iterable<TodoCollection> = todoCollectionRepository.findAll()

    @PostMapping
    fun create(name: String) = todoCollectionRepository.save(TodoCollection(name))
}