package dynamo.account.acid.dao.dynamodb

import dynamo.account.acid.dao.AccountDAO
import dynamo.account.acid.model.account.Account

class AccountDynamoDB: AccountDAO {
    override fun create(account: Account) {
        TODO("Not yet implemented")
    }

    override fun get(id: Long): Account {
        TODO("Not yet implemented")
    }

    override fun getByUserId(userId: Long): Account {
        TODO("Not yet implemented")
    }
}