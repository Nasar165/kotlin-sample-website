package com.mcsunity

import com.mcsunity.route.registerAccountRoute
import com.mcsunity.service.AccountService
import com.mongodb.client.MongoDatabase
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.serialization.*
import kotlinx.serialization.json.Json
import org.litote.kmongo.KMongo
import org.litote.kmongo.id.serialization.IdKotlinXSerializationModule

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

private fun startMongodb(mongodbUri: String): MongoDatabase {
    return KMongo.createClient(mongodbUri)
        .getDatabase("kotlin")
}

fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
        json(Json { serializersModule = IdKotlinXSerializationModule })
    }

    install(CORS) {
        anyHost()
        method(HttpMethod.Options)
        method(HttpMethod.Get)
        method(HttpMethod.Put)
        method(HttpMethod.Patch)
        method(HttpMethod.Post)
        method(HttpMethod.Delete)
        //header("authorization")
    }

    val database = startMongodb(environment.config.property("ktor.deployment.mongodbUri").getString())
    registerAccountRoute(AccountService(database))
}