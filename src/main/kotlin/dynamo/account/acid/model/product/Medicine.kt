package dynamo.account.acid.model.product

import java.math.BigDecimal

data class Medicine(
    override val id: Long,
    override val price: BigDecimal,
    override val description: String,
    override val name: String,
    override val imageUrl: String,
    override val stock: Long,
    val needsPrescription: Boolean,
    val attachedPrescriptionUrl: String,
    val barCode: String
) : Product(id = id, name = name, description = description, imageUrl = imageUrl, price = price, stock = stock)
