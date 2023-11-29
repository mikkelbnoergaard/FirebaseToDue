package com.example.todue.ui.screens.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todue.dataLayer.source.local.ToDo
import com.example.todue.dataLayer.source.local.ToDoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CalendarViewModel(
    private val toDoRepository: ToDoRepository
) : ViewModel() {

    private val _todoList = MutableLiveData<List<ToDo>>()
    val todoList: LiveData<List<ToDo>> get() = _todoList

    private val _selectedDate = MutableLiveData<Date>()
    val selectedDate: LiveData<Date> get() = _selectedDate

    // Function to set the selected date
    fun setSelectedDate(date: Date) {
        _selectedDate.value = date
        // Fetch todos for the selected date when it changes
        getToDosForDate(date)
    }
    fun getToDosForDate(selectedDate: Date) {
        viewModelScope.launch {
            toDoRepository.observeAll()
                .map { toDos ->
                    toDos.filter {
                        val toDoDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(it.dueDate)
                        toDoDate == selectedDate
                    }
                }
                .collect { filteredToDos ->
                    _todoList.value = filteredToDos
                }
        }
    }
}