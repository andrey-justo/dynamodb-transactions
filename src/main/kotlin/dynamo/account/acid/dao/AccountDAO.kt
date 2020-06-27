package dynamo.account.acid.dao

import dynamo.account.acid.model.account.Account

interface AccountDAO {

    fun create(account: Account)
    fun get(id: Long): Account
    fun getByUserId(userId: Long): Account

}