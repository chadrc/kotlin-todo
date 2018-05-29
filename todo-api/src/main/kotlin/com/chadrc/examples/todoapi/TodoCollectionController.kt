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

    data class EditRequest(val collectionId: String, val name: String)

    @PatchMapping
    fun edit(@RequestBody request: EditRequest): ResponseEntity<Any> {
        val optional = todoCollectionRepository.findById(ObjectId(request.collectionId))
        if (optional.isPresent) {
            val collection = optional.get()
            collection.name = request.name
            todoCollectionRepository.save(collection)
            return ResponseEntity.ok().build()
        }

        return ResponseEntity.notFound().build()
    }

    data class EditTodoRequest(val collectionId: String, val todoIndex: Int, val text: String?, val completed: Boolean?)

    @PatchMapping("/todo")
    fun editTodo(@RequestBody request: EditTodoRequest): ResponseEntity<Any> {
        if (request.todoIndex < 0) {
            return ResponseEntity.badRequest().body("todoIndex must be a positive integer.")
        }

        val optional = todoCollectionRepository.findById(ObjectId(request.collectionId))
        if (optional.isPresent) {
            val collection = optional.get()
            if (request.todoIndex >= collection.todos.size) {
                return ResponseEntity.notFound().build()
            }
            val todo = collection.todos[request.todoIndex]
            if (request.text != null) {
                todo.text = request.text
            }

            if (request.completed != null) {
                todo.completed = request.completed
            }

            todoCollectionRepository.save(collection)

            return ResponseEntity.ok().build()
        }

        return ResponseEntity.notFound().build()
    }

    @DeleteMapping
    fun deleteCollection(@RequestParam collectionId: String) = todoCollectionRepository.deleteById(ObjectId(collectionId))

    @DeleteMapping("/todo")
    fun deleteTodo(@RequestParam collectionId: String, @RequestParam todoIndex: Int): ResponseEntity<Any> {
        if (todoIndex < 0) {
            return ResponseEntity.badRequest().body("todoIndex must be a positive integer.")
        }

        val optional = todoCollectionRepository.findById(ObjectId(collectionId))
        if (optional.isPresent) {
            val collection = optional.get()
            if (todoIndex >= collection.todos.size) {
                return ResponseEntity.notFound().build()
            }
            collection.todos.removeAt(todoIndex)
            todoCollectionRepository.save(collection)
            return ResponseEntity.ok().build()
        }

        return ResponseEntity.notFound().build()
    }
}