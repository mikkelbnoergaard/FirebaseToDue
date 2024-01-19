package com.example.firebasetodue

import androidx.room.RewriteQueriesToDropUnusedColumns
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.firebasetodue.dataLayer.source.local.ToDo
import com.example.firebasetodue.dataLayer.source.local.ToDoRepository
import com.example.firebasetodue.di.DatabaseModules

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

//run test twice, it does not run correctly on first run
@RewriteQueriesToDropUnusedColumns
@RunWith(AndroidJUnit4::class)
class CreateToDoInDatabaseTest {

    @Test
    fun createToDo() {

        val toDoRepository = ToDoRepository(
            DatabaseModules.provideToDoDao(
                DatabaseModules.provideDataBase(
                    getApplicationContext()
                )
            )
        )

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

        toDoRepository.createTestToDoInDB(
            toDoObject.title,
            toDoObject.description,
            toDoObject.tag,
            toDoObject.dueDate,
            toDoObject.dueTime,
            toDoObject.finished
        )

        assertEquals("title was not inserted or fetched correctly", toDoRepository.getTestToDoTitle(toDoObject.id), toDoObject.title)
        assertEquals("description was not inserted or fetched correctly",toDoRepository.getTestToDoDescription(toDoObject.id), toDoObject.description)
        assertEquals("tag was not inserted or fetched correctly",toDoRepository.getTestToDoTag(toDoObject.id), toDoObject.tag)
        assertEquals("dueDate was not inserted or fetched correctly",toDoRepository.getTestToDoDueDate(toDoObject.id), toDoObject.dueDate)
        assertEquals("dueTime was not inserted or fetched correctly",toDoRepository.getTestToDoDueTime(toDoObject.id), toDoObject.dueTime)

    }

}