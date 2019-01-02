package cz.fajkus

import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith


class CalculatorTest {
    @Test
    fun testCalculatesPlus() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/calculate?operation=plus&operand1=10&operand2=20").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("The result is 30", response.content)
            }
        }
    }

    @Test
    fun testCalculatesMinus() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/calculate?operation=minus&operand1=10&operand2=20").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("The result is -10", response.content)
            }
        }
    }

    @Test
    fun testCalculatesProduct() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/calculate?operation=product&operand1=10&operand2=20").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("The result is 200", response.content)
            }
        }
    }

    @Test
    fun testCalculatesDivision() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/calculate?operation=division&operand1=20&operand2=2").apply {
            }
        }
    }


    @Test
    fun testDoesNotDivideByZero() {
        withTestApplication({ module(testing = true) }) {
            assertFailsWith<DivisionByZeroException> {
                handleRequest(HttpMethod.Get, "/calculate?operation=division&operand1=20&operand2=0").apply {
                }
            }
        }
    }


    @Test
    fun testFailsForUnknownOperation() {
        withTestApplication({ module(testing = true) }) {
            assertFailsWith<UnknownOperationException> {
                handleRequest(HttpMethod.Get, "/calculate?operation=UNKNOWN&operand1=20&operand2=0").apply {
                    assertEquals(HttpStatusCode.InternalServerError, response.status())
                    assertEquals("Cannot divide by zero!", response.content)
                }
            }

        }
    }
}
