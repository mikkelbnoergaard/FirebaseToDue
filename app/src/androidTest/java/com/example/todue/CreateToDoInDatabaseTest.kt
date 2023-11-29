package com.example.todue


import androidx.room.RewriteQueriesToDropUnusedColumns
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.todue.dataLayer.source.local.ToDo
import com.example.todue.dataLayer.source.local.ToDoRepository
import com.example.todue.di.DatabaseModules

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

//does not run

@RewriteQueriesToDropUnusedColumns
@RunWith(AndroidJUnit4::class)
class CreateToDoInDatabaseTest {

    @Test
    fun createToDo() {

        val toDoRepository = ToDoRepository(DatabaseModules.provideToDoDao(DatabaseModules.provideDataBase(getApplicationContext())))

        val title = "Test title"
        val description = "Test description"
        val tag = "Test tag"
        val dueDate = "19-11-2022"
        val dueTime = "22:00"
        val finished = false


        val toDoObject = ToDo(
            title = title,
            description = description,
            tag = tag,
            dueDate = dueDate,
            dueTime = dueTime,
            finished = finished,
            id = 1
        )
        toDoRepository.createTodo(
            toDoObject.title,
            toDoObject.description,
            toDoObject.tag,
            toDoObject.dueDate,
            toDoObject.dueTime,
            toDoObject.finished
        )

        assertEquals(toDoRepository.getTestToDoTitle(toDoObject.id), toDoObject.title)
        assertEquals(toDoRepository.getTestToDoDescription(toDoObject.id), toDoObject.description)
        assertEquals(toDoRepository.getTestToDoTag(toDoObject.id), toDoObject.tag)
        assertEquals(toDoRepository.getTestToDoDueDate(toDoObject.id), toDoObject.dueDate)
        assertEquals(toDoRepository.getTestToDoDueTime(toDoObject.id), toDoObject.dueTime)

    }

}



