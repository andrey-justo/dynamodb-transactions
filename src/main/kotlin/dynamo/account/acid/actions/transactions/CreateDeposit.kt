package dynamo.account.acid.actions.transactions

import dynamo.account.acid.actions.Action
import dynamo.account.acid.actions.product.input.MedicineDTO
import dynamo.account.acid.actions.transactions.input.DepositDTO
import dynamo.account.acid.dao.AccountDAO
import dynamo.account.acid.dao.ProductDAO
import dynamo.account.acid.dao.TransactionDAO
import dynamo.account.acid.model.account.Deposit
import dynamo.account.acid.model.account.Transaction
import dynamo.account.acid.model.product.Medicine

class CreateDeposit(private val transactionDAO: TransactionDAO, private val accountDAO: AccountDAO) : Action<DepositDTO, Transaction> {
  override fun perform(input: DepositDTO): Transaction {
    val account = accountDAO.get(input.accountId)
    val deposit = Deposit(
      amount = input.amount,
      accountId = account.id
    )
    transactionDAO.add(deposit)
    return deposit
  }
}