package com.thepan.reservationapiserver.domain.sms.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class NaverSensResponse(
    @JsonProperty("requestId") val requestId: String,
    @JsonProperty("requestTime") val requestTime: String,
    @JsonProperty("statusCode") val statusCode: String,
    @JsonProperty("statusName") val statusName: String
)
