package dynamo.account.acid.actions.account

import dynamo.account.acid.actions.Action
import dynamo.account.acid.dao.AccountDAO
import dynamo.account.acid.model.account.Account

class GetAccount(private val accountDAO: AccountDAO) :
  Action<String, Account> {
  override fun perform(input: String): Account {
    return accountDAO.get(input)
  }
}