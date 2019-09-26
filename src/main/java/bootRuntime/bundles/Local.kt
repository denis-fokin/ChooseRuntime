// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package bootRuntime.bundles

import bootRuntime.command.CommandFactory
import bootRuntime.command.CommandFactory.produce
import bootRuntime.command.Processor.process
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.text.StringUtil
import java.io.File

class Local(val project: Project, location: File) : Runtime(location) {

  override val installationPath: File = location

  val version : String by lazy {
    // runs on first access of messageView
    fetchVersion() ?: "Undefined"
  }

  override fun install() {
    process(
      produce(CommandFactory.Type.EXTRACT, this),
      produce(CommandFactory.Type.COPY, this),
      produce(CommandFactory.Type.INSTALL, this)
    )
  }

  override fun toString(): String {
    val path = StringUtil.shortenPathWithEllipsis(installationPath.path, 40, true)
    return "$version [Local $path]"
  }
}