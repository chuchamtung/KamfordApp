package com.kamford.app.data.local.users

import androidx.room.Dao
import androidx.room.Query
import com.kamford.app.data.local.BaseDao
import kotlinx.coroutines.flow.Flow

/**
 * [Room] DAO for [User] related operations.
 */
@Dao
abstract class UsersDao : BaseDao<User> {

    @Query("SELECT * FROM users WHERE id = :id")
    abstract suspend fun getUserById(id: String): User?

    @Query("SELECT * FROM users WHERE android_finger = :androidID")
    abstract fun getUserByAndroidId(androidID: String): User?

    @Query("SELECT * FROM users WHERE android_finger = :androidID")
    abstract fun findUserByAndroidId(androidID: String): Flow<User?>

    @Query("SELECT * FROM users WHERE name = :name")
    abstract fun findUserByName(name: String): Flow<User?>

    @Query("SELECT COUNT(*) FROM users")
    abstract suspend fun count(): Int

}
