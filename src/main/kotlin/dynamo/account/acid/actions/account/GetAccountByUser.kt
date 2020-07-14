package dynamo.account.acid.actions.account

import dynamo.account.acid.actions.Action
import dynamo.account.acid.dao.AccountDAO
import dynamo.account.acid.model.account.Account

class GetAccountByUser(private val accountDAO: AccountDAO) :
  Action<Long, Account> {
  override fun perform(input: Long): Account {
    return accountDAO.getByUserId(input)
  }
}