package br.com.originalsoftware.taxifone.todolist.datasource

import br.com.originalsoftware.taxifone.todolist.model.Task

object TaskDataSource {
    private val list = arrayListOf<Task>()

    //pegar os objetos lista
    fun getList() = list.toList()

    //funcao popular a lista
    fun insertTask(task: Task) {
        if (task.id == 0) {
            list.add(task.copy(id = list.size + 1))
        } else {
            list.remove(task)
            list.add(task)
        }
    }


    fun findById(taskId:Int) = list.find {it.id == taskId}

    fun deleteTask(task: Task) {
        list.remove(task)
    }

}