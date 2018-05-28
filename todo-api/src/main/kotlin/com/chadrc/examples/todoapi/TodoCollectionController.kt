package com.chadrc.examples.todoapi

import org.springframework.web.bind.annotation.RestController

@RestController
class TodoCollectionController(val todoCollectionRepository: TodoCollectionRepository) {

}