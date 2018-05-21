package org.rsjug.todolist.model.todoList

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.LongIdTable

class TodoList(id: EntityID<Long>) : LongEntity(id) {
    var description by TodoLists.description
    val todoListItems by
        TodoListItem referrersOn TodoListItems.todoList

    companion object : LongEntityClass<TodoList>(TodoLists)
}

object TodoLists : LongIdTable("todo_list", "id") {
    val description = varchar("description", 100)
}

data class TodoListDTO(val id: Long?,
                       val description: String) {
    constructor(todoList: TodoList) : this(todoList.id.value, todoList.description)
}


data class TodoListItemDTO(val id: Long?, val description: String) {
    constructor(todoListItem: TodoListItem) : this(todoListItem.id.value,  todoListItem.description)
}


data class TodoListWithItemDTO(val id: Long,
                               val description: String,
                               val todoListItems: List<TodoListItemDTO>) {
    constructor(todoList: TodoList) : this(todoList.id.value, todoList.description,
            todoList.todoListItems.map { TodoListItemDTO(it) })
}

