package dynamo.account.acid.configuration

import dynamo.account.acid.actions.transactions.CreateDeposit
import dynamo.account.acid.actions.transactions.WithdrawMoney
import dynamo.account.acid.dao.AccountDAO
import dynamo.account.acid.dao.TransactionDAO
import org.koin.dsl.module

class TransactionActionConfig {
  fun createDeposit(transactionDAO: TransactionDAO, accountDAO: AccountDAO) = CreateDeposit(transactionDAO, accountDAO)

  fun withdrawMoney(transactionDAO: TransactionDAO, accountDAO: AccountDAO) = WithdrawMoney(transactionDAO, accountDAO)

  companion object {
    private val instance = TransactionActionConfig()
    val actionConfig = module {
      single { instance.createDeposit(get(), get()) }
      single { instance.withdrawMoney(get(), get()) }
    }
  }
}