plugins {
    kotlin("jvm") version "1.9.21"
}

group = "com.djawnstj"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // 톰캣 의존성 주입
    val tomcatVersion = "8.5.99"
    implementation("org.apache.tomcat.embed:tomcat-embed-core:$tomcatVersion")
    implementation("org.apache.tomcat.embed:tomcat-embed-jasper:$tomcatVersion")

    // 서블릿 의존성 주입
    implementation("javax.servlet:javax.servlet-api:4.0.1")
    implementation("javax.servlet:jstl:1.2")

    // 로그 출력을 위한 logback 의존성 주입
    implementation("ch.qos.logback:logback-classic:1.4.14")

    // 테스트
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.25.3")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
