// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package bootRuntime.command

import bootRuntime.main.Controller
import com.intellij.openapi.progress.ProgressIndicator
import javax.swing.AbstractAction
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.Project
import java.util.function.Consumer

abstract class Command internal constructor(private val myProject: Project, var myController: Controller, name: String?) : AbstractAction(name) {
    protected open fun handleFinished() {
        myController.updateRuntime()
    }

    fun runWithProgress(title: String?, progressIndicatorConsumer: Consumer<in ProgressIndicator?>) {
        runWithProgress(title, false, progressIndicatorConsumer)
    }

    fun runWithProgress(title: String?, canBeCancelled: Boolean, progressIndicatorConsumer: Consumer<in ProgressIndicator?>) {
        ProgressManager.getInstance().run(object : Task.Modal(myProject, title!!, canBeCancelled) {
            override fun run(progressIndicator: ProgressIndicator) {
                progressIndicatorConsumer.accept(progressIndicator)
            }

            override fun notifyFinished(): NotificationInfo? {
                handleFinished()
                return super.notifyFinished()
            }
        })
    }

}