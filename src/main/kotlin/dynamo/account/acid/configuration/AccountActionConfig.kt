package dynamo.account.acid.configuration

import dynamo.account.acid.actions.account.AddAccount
import dynamo.account.acid.actions.account.GetAccount
import dynamo.account.acid.actions.account.GetAccountByUser
import dynamo.account.acid.dao.AccountDAO
import org.koin.dsl.module

class AccountActionConfig {
  fun addAccount(accountDAO: AccountDAO) = AddAccount(accountDAO)

  fun getAccount(accountDAO: AccountDAO) = GetAccount(accountDAO)

  fun getAccountByUser(accountDAO: AccountDAO) = GetAccountByUser(accountDAO)

  companion object {
    private val instance = AccountActionConfig()
    val actionConfig = module {
      single { instance.addAccount(get()) }
      single { instance.getAccount(get()) }
      single { instance.getAccountByUser(get()) }
    }
  }
}