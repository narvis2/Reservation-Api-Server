package com.thepan.reservationapiserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class ReservationApiServerApplication

fun main(args: Array<String>) {
    runApplication<ReservationApiServerApplication>(*args)
}
