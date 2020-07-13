// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package bootRuntime.command

import bootRuntime.bundles.Runtime
import bootRuntime.main.Controller
import com.intellij.openapi.project.Project

abstract class RuntimeCommand internal constructor(project: Project?, controller: Controller?, name: String?, val runtime: Runtime) : Command(project!!, controller!!, name) {

    override fun handleFinished() {
        myController.runtimeSelected(runtime)
    }

}