// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package bootRuntime.command

import bootRuntime.bundles.Runtime
import bootRuntime.command.Processor.process
import bootRuntime.command.CommandFactory.Companion.produce
import bootRuntime.main.Controller
import com.intellij.openapi.project.Project
import java.awt.event.ActionEvent

class RemoteInstall internal constructor(project: Project?, controller: Controller?, runtime: Runtime) : RuntimeCommand(project, controller, "Install", runtime) {
    override fun actionPerformed(e: ActionEvent?) {
        process(
                produce(CommandFactory.Type.DOWNLOAD, runtime),
                produce(CommandFactory.Type.EXTRACT, runtime),
                produce(CommandFactory.Type.INSTALL, runtime)
        )
    }
}