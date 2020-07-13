// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package bootRuntime.command

import bootRuntime.bundles.Remote
import bootRuntime.bundles.Runtime
import java.lang.IllegalStateException
import bootRuntime.main.Controller
import com.intellij.openapi.project.Project

class CommandFactory private constructor(private val myProject: Project, private val myController: Controller) {
    enum class Type {
        DOWNLOAD, EXTRACT, COPY, INSTALL, UNINSTALL, DELETE, REMOTE_INSTALL, ARCHIVE
    }

    companion object {
        private var instance: CommandFactory? = null
        fun initialize(project: Project, controller: Controller) {
            instance = CommandFactory(project, controller)
        }

        private fun getInstance(): CommandFactory? {
            checkNotNull(instance) { "Command Factory has not been initialized" }
            return instance
        }

        @JvmStatic
        fun produce(commandType: Type?, runtime: Runtime): RuntimeCommand {
            when (commandType) {
                Type.ARCHIVE -> return ArchiveInstall(getInstance()!!.myProject, getInstance()!!.myController, runtime)
                Type.REMOTE_INSTALL -> return RemoteInstall(getInstance()!!.myProject, getInstance()!!.myController, runtime)
                Type.DOWNLOAD -> return Download(getInstance()!!.myProject, getInstance()!!.myController, runtime)
                Type.EXTRACT -> return Extract(getInstance()!!.myProject, getInstance()!!.myController, runtime)
                Type.COPY -> return Copy(getInstance()!!.myProject, getInstance()!!.myController, runtime)
                Type.INSTALL -> return Install(getInstance()!!.myProject, getInstance()!!.myController, runtime)
                Type.UNINSTALL -> {
                    val install = Install(getInstance()!!.myProject, getInstance()!!.myController, runtime)
                    install.isEnabled = false
                    return install
                }
                Type.DELETE -> {
                    val delete = Delete(getInstance()!!.myProject, getInstance()!!.myController, runtime)
                    delete.isEnabled = runtime is Remote
                    return delete
                }
            }
            throw IllegalStateException("Unknown Command Type")
        }
    }

}