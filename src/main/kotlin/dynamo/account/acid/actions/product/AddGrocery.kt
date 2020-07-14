package dynamo.account.acid.actions.product

import dynamo.account.acid.actions.Action
import dynamo.account.acid.actions.product.input.GroceryDTO
import dynamo.account.acid.actions.product.input.MedicineDTO
import dynamo.account.acid.dao.ProductDAO
import dynamo.account.acid.model.product.Grocery
import dynamo.account.acid.model.product.Medicine

class AddGrocery(private val productDAO: ProductDAO) : Action<GroceryDTO, Grocery> {
  override fun perform(input: GroceryDTO): Grocery {
    val grocery = Grocery(
      imageUrl = input.imageUrl,
      stock = input.stock,
      name = input.name,
      description = input.description,
      sku = input.sku,
      groceryId = input.groceryId,
      calories = input.calories,
      price = input.price
    )
    productDAO.add(grocery)
    return grocery
  }
}