// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package bootRuntime.command

import bootRuntime.bundles.Runtime
import bootRuntime.main.BinTrayUtil
import com.intellij.util.io.HttpRequests
import java.io.IOException
import bootRuntime.main.Controller
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import java.awt.event.ActionEvent
import java.util.function.Consumer

class Download internal constructor(project: Project?, controller: Controller?, runtime: Runtime) : RuntimeCommand(project, controller, "Download", runtime) {
    override fun actionPerformed(e: ActionEvent?) {
        val downloadDirectoryFile = runtime.downloadPath
        if (!BinTrayUtil.downloadPath().exists()) {
            BinTrayUtil.downloadPath().mkdir()
        }
        if (!downloadDirectoryFile.exists()) {
            val link = "https://bintray.com/jetbrains/intellij-jbr/download_file?file_path=" + runtime.fileName
            val oldLink = "https://bintray.com/jetbrains/intellij-jdk/download_file?file_path=" + runtime.fileName
            runWithProgress("Downloading...", true, Consumer { progressIndicator ->
                progressIndicator?.isIndeterminate = true
                try {
                    try {
                        HttpRequests.request(link).saveToFile(downloadDirectoryFile, progressIndicator)
                    } catch (ioe: HttpRequests.HttpStatusException) {
                        HttpRequests.request(oldLink).saveToFile(downloadDirectoryFile, progressIndicator)
                    }
                } catch (ex: IOException) {
                    LOG.warn(ex)
                }
            })
        }
    }

    companion object {
        private val LOG = Logger.getInstance("#com.intellij.bootRuntime.command.Download")
    }
}