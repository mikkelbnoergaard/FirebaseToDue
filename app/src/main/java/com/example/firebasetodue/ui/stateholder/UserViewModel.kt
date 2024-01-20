package com.example.firebasetodue.ui.stateholder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasetodue.dataLayer.source.local.UserRepository
import com.example.firebasetodue.state.UserState
import com.example.firebasetodue.ui.event.UserEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserViewModel(
    private val userRepository: UserRepository,
): ViewModel() {

    private val _subscribedKeys = userRepository.getSubscribedKeys()

    private val _userState = MutableStateFlow(UserState())
    val userState = combine(_userState, _subscribedKeys){ userState, subscribedKeys ->
        userState.copy(
            subscribedKeys = subscribedKeys
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserState())

    fun onEvent(userEvent: UserEvent) {

        when(userEvent) {
            is UserEvent.CreateUser -> {
                viewModelScope.launch {
                    userRepository.createUser(userEvent.user)
                }
            }

            is UserEvent.KeyTyping -> {
                _userState.update { it.copy (
                    keyTyping = userEvent.key
                )}
            }

            is UserEvent.SubscribeToKey -> {
                viewModelScope.launch {
                    userRepository.subscribeToKey(userState.value.keyTyping)
                    _userState.update { it.copy (
                        keyTyping = ""
                    )}
                }
            }

            is UserEvent.RetrieveFirebaseToDos -> {
                viewModelScope.launch {

                }
            }

            is UserEvent.SetUserKey -> {
                viewModelScope.launch {
                    val userKey = userRepository.getUserKey()
                    delay(1000)
                    _userState.update { it.copy(
                        userKey = userKey
                    )}
                }
            }
        }
    }
}