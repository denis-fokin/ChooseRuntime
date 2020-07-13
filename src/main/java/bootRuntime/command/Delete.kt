// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package bootRuntime.command

import bootRuntime.bundles.Local
import bootRuntime.bundles.Runtime
import bootRuntime.main.Controller
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.io.FileUtil
import java.awt.event.ActionEvent
import java.util.function.Consumer

class Delete internal constructor(project: Project?, controller: Controller?, runtime: Runtime) : RuntimeCommand(project, controller, "Delete", runtime) {
    override fun actionPerformed(e: ActionEvent?) {
        runWithProgress("Deleting...", Consumer { indicator: ProgressIndicator? ->
            FileUtil.delete(runtime.downloadPath)
            FileUtil.delete(runtime.installationPath)
        })
    }

    init {
        isEnabled = runtime !is Local
    }
}