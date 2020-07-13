// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package bootRuntime.main

import bootRuntime.command.Cleanup
import bootRuntime.command.CommandFactory
import bootRuntime.command.RuntimeCommand
import bootRuntime.command.UseDefault
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.application.ex.ApplicationManagerEx
import com.intellij.openapi.project.Project
import com.intellij.ui.components.JBOptionButton
import com.intellij.util.ui.UIUtil
import java.awt.GridBagConstraints
import java.awt.Insets
import javax.swing.JButton
import javax.swing.SwingUtilities
import bootRuntime.bundles.Runtime
import bootRuntime.command.CommandFactory.Companion.initialize
import bootRuntime.command.CommandFactory.Companion.produce

class Controller(val project: Project, val actionPanel:ActionPanel, val model: Model, val installed:Runtime) {

  init {
    initialize(project, this)
  }

  fun updateRuntime() {
    runtimeSelected(model.selectedBundle)
  }

  // the fun is supposed to be invoked on the combobox selection
  fun runtimeSelected(runtime:Runtime) {
    model.updateBundle(runtime)
    actionPanel.removeAll()
    val list = runtimeStateToActions(runtime, model.currentState()).toList()

    val job = if (list.size > 1)
      JBOptionButton(list.firstOrNull(), list.subList(1, list.size).toTypedArray())
    else
      JButton(list.firstOrNull())

    val constraint = GridBagConstraints()
    constraint.insets = Insets(0,0,0, 0)
    constraint.weightx = 1.0
    constraint.anchor = GridBagConstraints.WEST

    val resetButton = JButton(UseDefault(project, this))
    resetButton.toolTipText = "Reset boot Runtime to the default one"
    resetButton.isEnabled = BinTrayUtil.getJdkConfigFilePath().exists()


    actionPanel.add(resetButton, constraint)
    val cleanButton = JButton(Cleanup(project, this, installed))
    cleanButton.toolTipText = "Remove all installed runtimes"
    actionPanel.add(cleanButton, constraint )

    constraint.anchor = GridBagConstraints.EAST

    actionPanel.rootPane?.defaultButton = job

    actionPanel.add(job, constraint)
    actionPanel.repaint()
    actionPanel.revalidate()
  }

  private fun runtimeStateToActions(runtime:Runtime, currentState: BundleState) : List<RuntimeCommand> {
    return when (currentState) {
      BundleState.ARCHIVED -> listOf(produce(CommandFactory.Type.ARCHIVE, runtime), produce(CommandFactory.Type.DELETE, runtime))
      BundleState.REMOTE -> listOf(produce(CommandFactory.Type.REMOTE_INSTALL, runtime), produce(CommandFactory.Type.DOWNLOAD, runtime))
      BundleState.DOWNLOADED -> listOf(produce(CommandFactory.Type.EXTRACT, runtime), produce(CommandFactory.Type.DELETE, runtime))
      BundleState.EXTRACTED -> listOf(produce(CommandFactory.Type.INSTALL, runtime), produce(CommandFactory.Type.DELETE, runtime))
      BundleState.UNINSTALLED -> listOf(produce(CommandFactory.Type.INSTALL, runtime), produce(CommandFactory.Type.DELETE, runtime))
      BundleState.INSTALLED -> listOf(produce(CommandFactory.Type.UNINSTALL, runtime), produce(CommandFactory.Type.DELETE, runtime))
      BundleState.LOCAL -> listOf(produce(CommandFactory.Type.INSTALL, runtime))
    }
  }

  fun add(local: Runtime) {
    model.bundles.add(local)
    model.selectedBundle = local
  }

  fun restart() {
    ApplicationManager.getApplication().invokeLater {
      SwingUtilities.getWindowAncestor(actionPanel).dispose()
      ApplicationManagerEx.getApplicationEx().restart(true)
    }
  }

  fun noRuntimeSelected() {
    UIUtil.uiTraverser(actionPanel).filter(JButton::class.java).forEach{b -> b.isEnabled = false}
  }
}
