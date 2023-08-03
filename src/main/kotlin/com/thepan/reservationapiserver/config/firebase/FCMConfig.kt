package com.thepan.reservationapiserver.config.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.thepan.reservationapiserver.utils.FirebaseAdminsPath
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource

@Configuration
class FCMConfig {
    @Bean
    fun firebaseMessaging(): FirebaseMessaging {
        val firebaseApp = FirebaseApp.getApps().firstOrNull { it.name == FirebaseApp.DEFAULT_APP_NAME }
            ?: run {
                val resource = ClassPathResource(FirebaseAdminsPath)
                val options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(resource.inputStream))
                    .build()
                FirebaseApp.initializeApp(options)
            }
        
        return FirebaseMessaging.getInstance(firebaseApp)
    }
}