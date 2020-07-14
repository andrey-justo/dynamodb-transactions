package dynamo.account.acid.configuration

import dynamo.account.acid.actions.product.AddGrocery
import dynamo.account.acid.actions.product.AddMedicine
import dynamo.account.acid.actions.product.GetProduct
import dynamo.account.acid.dao.ProductDAO
import org.koin.dsl.module

class ProductActionConfig {
  fun addGrocery(productDAO: ProductDAO) = AddGrocery(productDAO)

  fun addMedicine(productDAO: ProductDAO) = AddMedicine(productDAO)

  fun getProduct(productDAO: ProductDAO) = GetProduct(productDAO)

  companion object {
    private val instance = ProductActionConfig()
    val actionConfig = module {
      single { instance.addGrocery(get()) }
      single { instance.addMedicine(get()) }
      single { instance.getProduct(get()) }
    }
  }
}