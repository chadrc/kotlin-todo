package com.chadrc.examples.todoapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TodoApiApplication

fun main(args: Array<String>) {
    runApplication<TodoApiApplication>(*args)
}
