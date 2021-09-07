package edu.moraes.mobile.todolist.model

public final  data class Task (
    val titulo: String,
    val data: String,
    val hora: String,
    val id: Int = 0
    ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Task

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}