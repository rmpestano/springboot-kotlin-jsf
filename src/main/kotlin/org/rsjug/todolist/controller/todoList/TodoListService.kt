package org.rsjug.todolist.controller.todoList

import org.jetbrains.exposed.sql.transactions.transaction
import org.rsjug.todolist.interceptor.database.ConnectToDatabase
import org.rsjug.todolist.model.todoList.*
import org.springframework.stereotype.Service

@Service
class TodoListService {


    @ConnectToDatabase
    fun insert (todoListDTO: TodoListDTO): TodoListDTO = transaction {
        val todoList = TodoList.new {
            description = todoListDTO.description
        }
        TodoListDTO(todoList)
    }

    @ConnectToDatabase
    fun addItem (todoListItemDTO: TodoListItemDTO, todoListId: Long) : TodoListWithItemDTO = transaction {
        val todoListById = TodoList[todoListId]
        TodoListItem.new {
            description = todoListItemDTO.description
            todoList = todoListById
        }
        TodoListWithItemDTO(todoListById)
    }

    fun update(id: Long, todoListDTO: TodoListDTO): TodoListDTO = transaction {
        val todoList = TodoList[id]
        todoList.description = todoListDTO.description
        TodoListDTO(todoList)
    }

    @ConnectToDatabase
    fun count() : Int = transaction {
        TodoList.count()
    }

}