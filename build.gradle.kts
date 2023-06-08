import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.0"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.8.21"
    kotlin("plugin.spring") version "1.8.21"
    kotlin("plugin.jpa") version "1.8.21"
    kotlin("kapt") version "1.8.21"
}

group = "com.thepan"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

noArg {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    
    implementation("io.github.microutils:kotlin-logging:3.0.5")
    // 이거 없으면 Caused by: java.lang.NoClassDefFoundError: javax/xml/bind/DatatypeConverter 발생
    implementation("javax.xml.bind:jaxb-api:2.3.1")
    
    implementation("io.jsonwebtoken:jjwt:0.9.1")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-hibernate5:2.14.2")
    // Spring 3++ Kotlin Query-dsl 설정
    implementation("com.querydsl:querydsl-apt:5.0.0:jakarta")
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")
    // @ConfigurationProperties 사용
    kapt("org.springframework.boot:spring-boot-configuration-processor")
    
    runtimeOnly("com.h2database:h2")
    
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
