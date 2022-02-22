package com.mcsunity.model

import com.mcsunity.model.inteface.IAccount
import com.mcsunity.model.inteface.IValidation
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.litote.kmongo.Id
import org.litote.kmongo.newId

@Serializable
data class Account(
    @Contextual @SerialName("_id") val _id: Id<Account> = newId(),
    override val username: String,
    override val password: String,
    override val person: Person?,
) : IAccount, IValidation {

    override fun objectIsInValid(): List<String> {
        val list = ArrayList<String>()
        if (username.isEmpty()) {
            list.add("Username is missing or is not an email")
        }

        if (password.isEmpty() || password.count() < 6) {
            list.add("Password is missing or less than 6 characters")
        }
        return list
    }
}