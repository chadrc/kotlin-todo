package com.chadrc.examples.todoapi

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("collection")
class TodoCollectionController(val todoCollectionRepository: TodoCollectionRepository) {

    @GetMapping
    fun findAll(): Iterable<TodoCollection> = todoCollectionRepository.findAll()

    @PostMapping
    fun create(@RequestBody request: CreateRequest) =
            todoCollectionRepository.save(TodoCollection(request.name))


    data class CreateRequest(val name: String)
}