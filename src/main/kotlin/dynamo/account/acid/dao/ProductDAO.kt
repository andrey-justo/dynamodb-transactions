package dynamo.account.acid.dao

import dynamo.account.acid.model.product.Product

interface ProductDAO {

    fun add(product: Product)
    fun get(id: Long): Product

}