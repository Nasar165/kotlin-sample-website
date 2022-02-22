package com.mcsunity.route

import com.mcsunity.model.Account
import com.mcsunity.model.inteface.IAccount
import com.mcsunity.model.inteface.IService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.litote.kmongo.eq
import org.litote.kmongo.setValue
import org.litote.kmongo.toId

fun Route.accountRouting(service: IService) {
    route("/account") {
        get {
            val result = service.get<List<IAccount>>()
            if (result.isEmpty()) {
                call.respondText("No accounts found", status = HttpStatusCode.NotFound)
            } else {
                call.respond(result)
            }
        }

        get("{id}") {
            val id = call.parameters["id"] ?: return@get call.respondText("parameter id is missing from the url",
                status = HttpStatusCode.BadRequest)

            val account = service.post<Account?>(id) ?: return@get call.respondText("Could not find account",
                status = HttpStatusCode.NotFound)
            call.respond(account)
        }

        put {
            val data = call.receive<Account>()
            val validation = data.objectIsInValid()
            if (validation.isNotEmpty()) {
                call.respond(HttpStatusCode.BadRequest, validation.toString())
            }
            call.respond(service.put(data))
        }

        patch {
            val data = call.receive<Account>()
            val result = service.patch(Account::_id eq data._id, setValue(Account::username, data.username))
            if (!result) call.respondText("No data has changed in the database", status = HttpStatusCode.Conflict)
            else call.respond(true)
        }

        delete("{id}") {
            val id = call.parameters["id"] ?: return@delete call.respondText("parameter id is missing from the url",
                status = HttpStatusCode.BadRequest)
            val result = service.delete(Account::_id eq id.toId())
            if (!result) call.respondText("Failed to delete data", status = HttpStatusCode.Conflict)
            else call.respondText("", status = HttpStatusCode.NoContent)
        }
    }
}

fun Application.registerAccountRoute(service: IService) {
    routing {
        accountRouting(service)
    }
}