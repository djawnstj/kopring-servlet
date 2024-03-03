package com.kopring.server

import org.apache.catalina.startup.Tomcat
import org.apache.catalina.webresources.DirResourceSet
import org.apache.catalina.webresources.StandardRoot
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.nio.file.Paths
import java.util.logging.Level
import kotlin.io.path.pathString

class KopringServer: Tomcat() {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(KopringServer::class.java)
    }

    fun startServer() {
        setTomcat()

        start()
        server.await()
    }

    private fun setTomcat() {
        offTomcatLogger()
        setPort(port)
        setServerContext()
    }

    private fun offTomcatLogger() {
        val tomcatCoreLogger = java.util.logging.Logger.getLogger("org.apache")

        tomcatCoreLogger.level = Level.OFF
    }

    private fun setServerContext() {
        val resourcesPath = Paths.get("src", "main", "resources").pathString
        val absoluteResourcesPath = File(resourcesPath).absolutePath
        log.info("resource path: {}", absoluteResourcesPath)
        val context = addWebapp("/", absoluteResourcesPath)

        val classesPath = getClassesPath()
        log.info("current class path: {}", classesPath)

        val resources = StandardRoot(context)
        resources.addPostResources(DirResourceSet(resources, "/WEB-INF/classes", classesPath, "/"))

        context.resources = resources
    }

    private fun getClassesPath(): String {
        val codeSource = javaClass.protectionDomain.codeSource
            ?: throw IllegalStateException("Can't getCodeSource.")

        val classesPath = File(codeSource.location.toURI()).absolutePath
        log.info("found class path: {}", classesPath)

        return classesPath
    }

}
