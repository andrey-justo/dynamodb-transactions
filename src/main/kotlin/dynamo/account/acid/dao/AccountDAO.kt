package dynamo.account.acid.dao

import dynamo.account.acid.model.account.Account

interface AccountDAO {

    fun create(account: Account)
    fun get(id: String): Account
    fun getByUserId(userId: Long): Account
    fun findByUserId(userId: Long): Account?

}