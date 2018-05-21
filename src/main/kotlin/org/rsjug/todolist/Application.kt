package org.rsjug.todolist

import org.jetbrains.exposed.sql.Database
import org.rsjug.todolist.controller.todoList.TodoListService
import org.rsjug.todolist.model.todoList.TodoListDTO
import org.rsjug.todolist.model.todoList.TodoListItemDTO
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.support.SpringBootServletInitializer
import org.springframework.context.annotation.ComponentScan
import javax.sql.DataSource

@SpringBootApplication
@ComponentScan("org.rsjug.*")
class Application : SpringBootServletInitializer()

fun main(args: Array<String>) {
    var context = SpringApplication.run(Application::class.java, *args)
    val todoListService = context.getBean(TodoListService::class.java)

    Database.connect(context.getBean(DataSource::class.java))
    if (todoListService.count() == 0) {


        for (i in 1..10) {
            val todoListDTO = todoListService.insert(TodoListDTO(null, "TODO $i"))
            todoListService.addItem(TodoListItemDTO(null, "item $i"), todoListDTO.id!!);
        }


    }
}


