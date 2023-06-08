package com.thepan.reservationapiserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ReservationApiServerApplication

fun main(args: Array<String>) {
    runApplication<ReservationApiServerApplication>(*args)
}
