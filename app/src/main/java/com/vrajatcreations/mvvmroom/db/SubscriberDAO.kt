package com.vrajatcreations.mvvmroom.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SubscriberDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubscriber(subscriber: Subscriber):Long

    @Update
    suspend fun updateSubscriber(subscriber: Subscriber):Int

    @Delete
    suspend fun deleteSubscriber(subscriber: Subscriber):Int

    @Query("DELETE from subscriber_data")
    suspend fun deleteAll():Int

    @Query("SELECT * from subscriber_data")
    fun getAllSubscribers():LiveData<List<Subscriber>>
}