package com.kamford.app.data.local.transaction

import androidx.room.Dao
import androidx.room.Ignore
import androidx.room.Transaction

/**
 * [Room] DAO which provides the implementation for our [TransactionRunner].
 * 用於循環操作，比如 添加 & 刪除 書籤操作，可以做此配置
 */
@Dao
abstract class TransactionRunnerDao : TransactionRunner {
    @Transaction
    protected open suspend fun runInTransaction(tx: suspend () -> Unit) = tx()

    @Ignore
    override suspend fun invoke(tx: suspend () -> Unit) {
        runInTransaction(tx)
    }
}

/**
 * Interface with operator function which will invoke the suspending lambda within a database
 * transaction.
 */
interface TransactionRunner {
    suspend operator fun invoke(tx: suspend () -> Unit)
}
