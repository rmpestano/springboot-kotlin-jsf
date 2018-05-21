package org.rsjug.todolist.controller.todoList

import org.jetbrains.exposed.sql.transactions.transaction
import org.rsjug.todolist.interceptor.database.ConnectToDatabase
import org.rsjug.todolist.model.todoList.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/todoList")
class TodoListController {

    @field:Autowired
    lateinit var todoListService: TodoListService

    @GetMapping("")
    @ConnectToDatabase
    fun list() = transaction {
        TodoList.all().map { TodoListDTO(it) }
    }

    @GetMapping("/{id}")
    @ConnectToDatabase
    fun view(@PathVariable id: Long): TodoListWithItemDTO = transaction {
        val todoList = TodoList[id]
        TodoListWithItemDTO(todoList)
    }

    @PostMapping("")
    fun save(@RequestBody todoListDTO: TodoListDTO): TodoListDTO = todoListService.insert(todoListDTO)


    @PostMapping("{id}/item")
    @ConnectToDatabase
    fun addItem(@PathVariable id: Long,
                @RequestBody todoListItemDTO: TodoListItemDTO): TodoListWithItemDTO = todoListService.addItem(todoListItemDTO,id)

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long,
               @RequestBody todoListDTO: TodoListDTO): TodoListDTO =  todoListService.update(id,todoListDTO)

    @DeleteMapping("/{id}")
    @ConnectToDatabase
    fun delete(@PathVariable id: Long): TodoListDTO = transaction {
        val todoList = TodoList[id]
        todoList.todoListItems.forEach { it.delete() }
        todoList.delete()
        TodoListDTO(todoList)
    }

}
