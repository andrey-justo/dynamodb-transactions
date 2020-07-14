package dynamo.account.acid.dao.dynamodb.converter

import dynamo.account.acid.model.account.Account
import software.amazon.awssdk.services.dynamodb.model.AttributeValue

interface Converter<T> {

  fun load(doc: Map<String, AttributeValue>): T

  fun convert(entity: T): Map<String, AttributeValue>

}