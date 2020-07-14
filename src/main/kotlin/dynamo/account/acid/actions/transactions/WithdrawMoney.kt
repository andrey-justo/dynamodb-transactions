package dynamo.account.acid.actions.transactions

import dynamo.account.acid.actions.Action
import dynamo.account.acid.actions.product.input.MedicineDTO
import dynamo.account.acid.actions.transactions.input.DepositDTO
import dynamo.account.acid.actions.transactions.input.WithdrawDTO
import dynamo.account.acid.dao.AccountDAO
import dynamo.account.acid.dao.ProductDAO
import dynamo.account.acid.dao.TransactionDAO
import dynamo.account.acid.model.account.Deposit
import dynamo.account.acid.model.account.Transaction
import dynamo.account.acid.model.account.Withdraw
import dynamo.account.acid.model.product.Medicine

class WithdrawMoney(private val transactionDAO: TransactionDAO, private val accountDAO: AccountDAO) : Action<WithdrawDTO, Transaction> {
  override fun perform(input: WithdrawDTO): Transaction {
    val account = accountDAO.get(input.accountId)
    val withdraw = Withdraw(
      amount = input.amount,
      accountId = account.id
    )
    transactionDAO.add(withdraw)
    return withdraw
  }
}