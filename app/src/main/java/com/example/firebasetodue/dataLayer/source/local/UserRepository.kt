package com.example.firebasetodue.dataLayer.source.local

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor (
    private val dataSource: UserDao
){

    suspend fun createUser(user: User) {
        if(!dataSource.checkIfUserExists()) {
            dataSource.createUser(user)
        }
    }

    suspend fun subscribeToKey(key: String) {
        dataSource.subscribeToKey(key)
    }

    fun getSubscribedKeys(): Flow<List<String>> {
        return dataSource.getSubscribedKeys()
    }

}