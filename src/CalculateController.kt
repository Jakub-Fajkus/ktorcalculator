package cz.fajkus

import io.ktor.application.ApplicationCall
import io.ktor.response.respondText

class CalculateController {
    suspend fun handleGet(call: ApplicationCall, location: CalculateLocation) {
        val result: Int = when (location.operation) {
            "plus" -> location.operand1 + location.operand2
            "minus" -> location.operand1 - location.operand2
            "product" -> location.operand1 * location.operand2
            "division" -> {
                if (location.operand2 == 0)
                    throw DivisionByZeroException("Cannot divide by zero!")

                location.operand1 / location.operand2
            }
            else -> {
                throw UnknownOperationException("Unknown operation ${location.operation}")
            }
        }

        call.respondText("The result is $result")
    }
}

class UnknownOperationException(message: String) : Exception(message)
class DivisionByZeroException(message: String) : Exception(message)