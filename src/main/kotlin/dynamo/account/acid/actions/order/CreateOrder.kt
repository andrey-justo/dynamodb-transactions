package dynamo.account.acid.actions.order

import dynamo.account.acid.actions.Action
import dynamo.account.acid.actions.order.input.Cart
import dynamo.account.acid.actions.product.input.GroceryDTO
import dynamo.account.acid.actions.product.input.MedicineDTO
import dynamo.account.acid.dao.ProductDAO
import dynamo.account.acid.dao.TransactionDAO
import dynamo.account.acid.model.account.Order
import dynamo.account.acid.model.product.Grocery
import dynamo.account.acid.model.product.Medicine

class CreateOrder(private val transactionDAO: TransactionDAO) : Action<Cart, Order> {
  override fun perform(input: Cart): Order {
    val order = Order(
      accountId = input.accountId,
      products = input.products
    )
    transactionDAO.add(order)
    return order
  }
}