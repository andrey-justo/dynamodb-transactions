package dynamo.account.acid.actions

interface Action<in INPUT, out OUTPUT> {

  fun perform(input: INPUT): OUTPUT

}