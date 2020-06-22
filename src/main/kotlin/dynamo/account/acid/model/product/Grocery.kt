package dynamo.account.acid.model.product

import java.math.BigDecimal

data class Grocery(override val id: Long, override val price: BigDecimal, override val description: String, override val name: String, override val imageUrl: String, override val stock: Long, val calories: String, val groceryId: Long, val sku: String)
    : Product(id = id, name = name, description = description, imageUrl = imageUrl, price = price, stock = stock)
