package com.example.firebasetodue.dataLayer.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {


    @Insert
    suspend fun createUser(user: User)

    @Query("SELECT(EXISTS(SELECT userKey FROM user WHERE userKey IS NOT ''))")
    suspend fun checkIfUserExists(): Boolean

    @Query("SELECT subscribedKeys FROM user")
    fun getSubscribedKeys(): Flow<List<String>>

    @Query("INSERT INTO User (userKey, subscribedKeys) VALUES('', :key)")
    suspend fun subscribeToKey(key:String)

    @Query("SELECT userKey FROM user")
    suspend fun getUserKey(): String

}