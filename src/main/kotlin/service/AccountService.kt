package com.mcsunity.service

import com.mcsunity.model.Account
import com.mcsunity.model.inteface.IService
import com.mongodb.client.MongoDatabase
import org.bson.conversions.Bson
import org.bson.types.ObjectId
import org.litote.kmongo.*

class AccountService(mongodbClient: MongoDatabase) : IService {
    private var collection = mongodbClient.getCollection<Account>("accounts")

    override fun <T> get(): T {
        val result =
            collection.find(Account::username eq "nasar")
                .projection(
                    fields(
                        include(Account::username, Account::person)
                    )
                ).ascendingSort(Account::_id)
                .toList()
        return result as T
    }

    override fun <T> put(data: T): String {
        val input = data as Account
        val result = collection.insertOne(input)
        return result.insertedId.toString()
    }

    override fun <T> post(data: Any): T {
        val account = collection.findOneById(ObjectId(data as String))
        return account as T
    }

    override fun patch(filter: Bson, data: Bson): Boolean {
        val result = collection.updateOne(filter, data)
        return result.modifiedCount > 0
    }

    override fun delete(filter: Bson): Boolean {
        val result = collection.deleteOne(filter)
        return result.deletedCount > 0
    }
}