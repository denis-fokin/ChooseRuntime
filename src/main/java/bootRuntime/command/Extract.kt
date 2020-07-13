// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package bootRuntime.command

import bootRuntime.bundles.Archive
import bootRuntime.bundles.Runtime
import bootRuntime.main.BinTrayUtil
import java.io.IOException
import bootRuntime.main.Controller
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.io.FileUtil
import com.intellij.util.io.Decompressor
import java.awt.event.ActionEvent
import java.io.File
import java.util.function.Consumer

class Extract internal constructor(project: Project?, controller: Controller?, runtime: Runtime) : RuntimeCommand(project, controller, "Extract", runtime) {
    override fun actionPerformed(e: ActionEvent?) {
        runWithProgress("Extracting...", true, Consumer { indicator ->

            val archiveFileName = runtime.fileName
            val directoryToExtractName = BinTrayUtil.archveToDirectoryName(archiveFileName)
            val jdkStoragePathFile = BinTrayUtil.getJdkStoragePathFile()

            if (!jdkStoragePathFile.exists()) {
                FileUtil.createDirectory(jdkStoragePathFile)
            }

            val directoryToExtractFile = File(jdkStoragePathFile, directoryToExtractName)

            FileUtil.delete(directoryToExtractFile)
            try {
                Decompressor.Tar(runtime.downloadPath).extract(directoryToExtractFile)
                if (runtime !is Archive) {
                    FileUtil.delete(runtime.downloadPath)
                }
            } catch (ex: IOException) {
                LOG.warn(ex)
            }
        })
    }

    companion object {
        private val LOG = Logger.getInstance("#com.intellij.bootRuntime.command.Extract")
    }
}