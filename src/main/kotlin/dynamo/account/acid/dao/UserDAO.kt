package dynamo.account.acid.dao

import dynamo.account.acid.model.user.User

interface UserDAO {

    fun add(user: User)
    fun update(user: User)
    fun get(id: String): User

}