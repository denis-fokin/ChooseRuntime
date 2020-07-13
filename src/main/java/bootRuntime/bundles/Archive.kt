// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package bootRuntime.bundles
import bootRuntime.command.CommandFactory
import bootRuntime.command.CommandFactory.Companion.produce
import bootRuntime.command.Processor.process
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.text.StringUtil
import java.io.File

class Archive(val project: Project, private val archivePath: File) : Runtime(archivePath) {

  val version : String by lazy {
    // runs on first access of messageView
    fetchVersion() ?: "Undefined"
  }

  override val downloadPath: File by lazy {
    archivePath
  }

  override fun install() {
    process(
      produce(CommandFactory.Type.EXTRACT, this),
      produce(CommandFactory.Type.COPY, this),
      produce(CommandFactory.Type.INSTALL, this)
    )
  }

  override fun toString(): String {
    val path = StringUtil.shortenPathWithEllipsis(archivePath.path, 40, true)
    return "$version [Local $path]"
  }

}