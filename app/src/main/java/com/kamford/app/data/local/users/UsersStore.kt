package com.kamford.app.data.local.users

import kotlinx.coroutines.flow.Flow


/**
 * @description
 * @author chamtungchu
 * @date 2023/9/25
 * @version 1.0
 */
class UsersStore(
    private val usersDao: UsersDao,
) {

    suspend fun addUserById(user: User): String {
        return when (val local = usersDao.getUserById(user.id)) {
            null -> usersDao.insert(user).toString()
            else -> {
                usersDao.update(user)
                local.id
            }
        }
    }

    fun findUserByName(name: String): Flow<User?> {
        return usersDao.findUserByName(name)
    }

    fun findUserByAndroidId(androidId: String): Flow<User?> {
        return usersDao.findUserByAndroidId(androidId)
    }

    fun getUserByAndroidId(androidId: String): User? {
        return usersDao.getUserByAndroidId(androidId)
    }

    suspend fun updateUser(user: User) {
        return usersDao.update(user)
    }

    suspend fun isEmpty(): Boolean = usersDao.count() == 0
}