// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package bootRuntime.command

import bootRuntime.bundles.Runtime
import bootRuntime.main.Controller
import com.intellij.openapi.project.Project
import java.awt.event.ActionEvent

class Copy internal constructor(project: Project?, controller: Controller?, runtime: Runtime) : RuntimeCommand(project, controller, "Copy", runtime) {
    override fun actionPerformed(e: ActionEvent?) {}
}