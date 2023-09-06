package com.thepan.reservationapiserver.domain.sms.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.thepan.reservationapiserver.domain.sms.dto.NaverSensResponse
import com.thepan.reservationapiserver.domain.sms.helper.NaverSensHelper
import com.thepan.reservationapiserver.exception.NetworkErrorException
import mu.KotlinLogging
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

@Service
class NaverSensV2Service(
    @Value("\${naver_sens.url.base}")
    private val baseUrl: String,
    @Value("\${naver_sens.url.end_point}")
    private val endPoint: String,
    @Value("\${naver_sens.key.access}")
    private val accessKey: String,
    @Value("\${naver_sens.key.secret}")
    private val secretKey: String,
    @Value("\${naver_sens.id.service}")
    private val serviceId: String,
    private val naverSensHelper: NaverSensHelper
) {
    
    /**
     * @author choi young-jun
     * 📌 SMS 문자 보내기
     * @param phoneNumber : SMS 문자를 받을 사람 전화번호
     * @param rand : 난수 (인증번호)
     */
    fun sendSMSMessage(phoneNumber: String, rand: String): NaverSensResponse {
        val hostNameUrl = baseUrl
        var requestUrl = endPoint
        val requestUrlType = "/messages"
        val method = "POST"
        val timestamp = System.currentTimeMillis().toString()
        requestUrl += serviceId + requestUrlType
        val apiUrl = hostNameUrl + requestUrl
        
        val bodyJson = JSONObject()
        val toJson = JSONObject()
        val toArray = JSONArray()
        
        // 난수와 함께 전송
        toJson["content"] = rand
        toJson["to"] = phoneNumber
        toArray.add(toJson)
        
        // 메시지 Type (sms | 1ms)
        bodyJson["type"] = "sms"
        bodyJson["content"] = rand
        bodyJson["contentType"] = "COMM" // 일반 메시지
        bodyJson["countryCode"] = "82" // 국가 번호
        
        // 발신번호 * 사전에 인증/등록된 번호만 사용 가능
        bodyJson["from"] = "01022696141"
        bodyJson["messages"] = toArray
        
        val body = bodyJson.toJSONString()
        log.info {
            "💬 SMS 문자 보내기 body 👇 \n $body"
        }
        
        return try {
            val url = URL(apiUrl)
            val con = url.openConnection() as HttpURLConnection
            con.apply {
                useCaches = false
                doOutput = true
                doInput = true
                setRequestProperty("content-type", "application/json")
                setRequestProperty("x-ncp-apigw-timestamp", timestamp)
                setRequestProperty("x-ncp-iam-access-key", accessKey)
                setRequestProperty(
                    "x-ncp-apigw-signature-v2",
                    naverSensHelper.makeSensSignature(
                        requestUrl,
                        timestamp,
                        method,
                        accessKey,
                        secretKey
                    )
                )
                requestMethod = method
                doOutput = true
            }
            
            val wr = DataOutputStream(con.outputStream)
            wr.write(body.toByteArray())
            wr.flush()
            wr.close()
            
            val responseCode = con.responseCode
            val br = if (responseCode == 202) {
                BufferedReader(InputStreamReader(con.inputStream))
            } else {
                BufferedReader(InputStreamReader(con.errorStream))
            }
            
            val response = StringBuffer()
            var inputLine: String?
            
            while (br.readLine().also { inputLine = it } != null) {
                response.append(inputLine)
            }
            
            br.close()
            
            log.info {
                "💬 SMS 문자 보내기 response 👇 \n $response"
            }
            val naverSensResponse = ObjectMapper().readValue(response.toString(), NaverSensResponse::class.java)
            
            naverSensResponse
        } catch (e: Exception) {
            log.error {
                "💬 SMS 문자 보내기 Exception 👉 $e, ${e.message}"
            }
            throw NetworkErrorException()
        }
    }
    
    companion object {
        private val log = KotlinLogging.logger {}
    }
}