package dynamo.account.acid.actions.product.input

import java.math.BigDecimal

data class MedicineDTO(
  val imageUrl: String,
  val needsPrescription: Boolean,
  val name: String,
  val description: String,
  val barCode: String,
  val stock: Long,
  val price: BigDecimal
)