package edu.moraes.mobile.todolist.datasource

import edu.moraes.mobile.todolist.model.Task

object TaskDataSource {
    private val list = arrayListOf<Task>()
    fun getList() = list.toList()
    fun insertTask(t: Task){
        if ( t.id == 0 ) {
            list.add(t.copy( id = list.size + 1 ))
        }else {
            var aux : Task = t
            val index : Int = list.indexOf(t)
            list.remove(t)
            list.add(index,aux)
        }
    }
    fun findById(taskId: Int) = list.find { it.id == taskId }

    fun deleteTask(task: Task) {
        list.remove(task)
    }
}