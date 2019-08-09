// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package bootRuntime.command

import bootRuntime.bundles.Remote
import bootRuntime.main.BinTrayUtil
import bootRuntime.main.Controller
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.util.io.FileUtil
import bootRuntime.bundles.Runtime

import java.awt.event.ActionEvent

class Cleanup(project: Project, controller: Controller, private val installedRuntime: Runtime) : Command(project, controller, "Clean Up") {

    override fun actionPerformed(e: ActionEvent) {
        if (installedRuntime is Remote) {
            Messages.showInfoMessage("Currently you are using a downloaded Runtime to bootstrap the IDE. " + "This Runtime could not be removed.", "Some Files Could not Be Removed")
            return
        }

        runWithProgress("Cleaning up...") {
            FileUtil.delete(BinTrayUtil.downloadPath())
            FileUtil.delete(BinTrayUtil.getJdkConfigFilePath())
        }
    }
}
