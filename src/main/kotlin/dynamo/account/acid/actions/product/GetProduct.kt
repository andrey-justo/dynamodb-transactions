package dynamo.account.acid.actions.product

import dynamo.account.acid.actions.Action
import dynamo.account.acid.dao.ProductDAO
import dynamo.account.acid.model.product.Product

class GetProduct(private val productDAO: ProductDAO): Action<Long, Product> {
  override fun perform(input: Long): Product {
    return productDAO.get(input)
  }
}