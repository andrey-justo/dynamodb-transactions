package dynamo.account.acid.dao

import dynamo.account.acid.model.account.Account
import dynamo.account.acid.model.account.Transaction

interface TransactionDAO {

    fun add(transaction: Transaction)

}