package dynamo.account.acid.actions.account

import dynamo.account.acid.actions.Action
import dynamo.account.acid.actions.account.exceptions.AccountAlreadyExists
import dynamo.account.acid.actions.account.input.CreateAccountDTO
import dynamo.account.acid.dao.AccountDAO
import dynamo.account.acid.model.account.Account
import java.util.*

class AddAccount(private val accountDAO: AccountDAO):
  Action<CreateAccountDTO, Account> {
  override fun perform(input: CreateAccountDTO): Account {
    return when(accountDAO.findByUserId(input.userId)) {
      null -> insertAccount(input)
      else -> throw AccountAlreadyExists("The ${input.userId} already has an account")
    }

  }

  private fun insertAccount(input: CreateAccountDTO): Account {
    val account = Account(id = UUID.randomUUID().toString(), limits = input.limits, userId = input.userId)
    accountDAO.create(account)
    return account
  }
}