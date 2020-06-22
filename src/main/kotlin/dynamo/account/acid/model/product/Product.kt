package dynamo.account.acid.model.product

import java.math.BigDecimal

abstract class Product(open val id: Long, open val price: BigDecimal, open val description: String, open val name: String, open val imageUrl: String, open val stock: Long)
