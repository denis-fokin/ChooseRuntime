// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package bootRuntime.command

import bootRuntime.bundles.Runtime
import bootRuntime.main.BinTrayUtil
import bootRuntime.main.Controller
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.SystemInfo
import com.intellij.openapi.util.io.FileUtil
import com.intellij.util.io.toByteArray
import java.awt.event.ActionEvent
import java.io.File
import java.io.IOException
import java.nio.CharBuffer
import java.nio.charset.Charset
import java.util.function.Consumer

class Install internal constructor(project: Project, controller: Controller, runtime: Runtime) : RuntimeCommand(project, controller, "Install", runtime) {

    private fun javaHomeFromInstallationPath(installationPath:File): String {
        val javaHomeFile = installationPath.walk().filter { f ->
            f.name == "tools.jar" ||
                    f.name == "jrt-fs.jar"
        }.first().parentFile.parentFile

        return if (SystemInfo.isMac) {
            javaHomeFile.parentFile.parentFile.absolutePath
        } else {
            javaHomeFile.absolutePath
        }
    }

    override fun actionPerformed(e: ActionEvent?) {
        runWithProgress("Installing...", Consumer {
            try {
                if (SystemInfo.isWindows) {
                    val encoding = System.getProperty("sun.jnu.encoding")
                    val javaHomeFromInstallationPath = javaHomeFromInstallationPath(runtime.installationPath)
                    val charset = Charset.forName(encoding)
                    val charsetEncoder = charset.newEncoder()
                    FileUtil.writeToFile(BinTrayUtil.getJdkConfigFilePath(), charsetEncoder.encode(CharBuffer.wrap(javaHomeFromInstallationPath)).toByteArray()) }
                else {
                    FileUtil.writeToFile(BinTrayUtil.getJdkConfigFilePath(), javaHomeFromInstallationPath(runtime.installationPath))
                }
                myController.restart()
            } catch (ioe: IOException) {
                LOG.warn(ioe)
            }
        })
    }

    companion object {
        private val LOG = Logger.getInstance("#com.intellij.bootRuntime.command.Install")
    }
}
