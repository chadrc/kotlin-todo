package com.chadrc.examples.todoapi

import org.bson.types.ObjectId
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("collection")
class TodoCollectionController(val todoCollectionRepository: TodoCollectionRepository) {

    @GetMapping
    fun findAll(): Iterable<TodoCollection> = todoCollectionRepository.findAll()

    data class CreateRequest(val name: String)
    @PostMapping
    fun create(@RequestBody request: CreateRequest) =
            todoCollectionRepository.save(TodoCollection(request.name))

    data class AddTodoRequest(val collectionId: String, val text: String)

    @PostMapping("/todo")
    fun addTodo(@RequestBody request: AddTodoRequest): ResponseEntity<Any> {
        val optional = todoCollectionRepository.findById(ObjectId(request.collectionId))
        if (optional.isPresent) {
            val collection = optional.get()
            val todo = Todo(request.text)
            collection.todos.add(todo)
            todoCollectionRepository.save(collection)
            return ResponseEntity.ok(todo)
        }

        return ResponseEntity.notFound().build()
    }
}