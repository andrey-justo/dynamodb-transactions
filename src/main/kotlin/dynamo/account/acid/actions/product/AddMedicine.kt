package dynamo.account.acid.actions.product

import dynamo.account.acid.actions.Action
import dynamo.account.acid.actions.product.input.MedicineDTO
import dynamo.account.acid.dao.ProductDAO
import dynamo.account.acid.model.product.Medicine

class AddMedicine(private val productDAO: ProductDAO) : Action<MedicineDTO, Medicine> {
  override fun perform(input: MedicineDTO): Medicine {
    val medicine = Medicine(
      imageUrl = input.imageUrl,
      needsPrescription = input.needsPrescription,
      barCode = input.barCode,
      attachedPrescriptionUrl = "",
      stock = input.stock,
      name = input.name,
      description = input.description,
      price = input.price
    )
    productDAO.add(medicine)
    return medicine
  }
}