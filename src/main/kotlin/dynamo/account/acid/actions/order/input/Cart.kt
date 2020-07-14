package dynamo.account.acid.actions.order.input

import dynamo.account.acid.model.product.Product

data class Cart(val accountId: String, val products: List<Product>)