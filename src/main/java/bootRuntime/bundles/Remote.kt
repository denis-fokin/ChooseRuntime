// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package bootRuntime.bundles
import bootRuntime.command.CommandFactory
import bootRuntime.command.CommandFactory.produce
import bootRuntime.command.Processor.process
import com.intellij.openapi.project.Project
import java.io.File

class Remote(val project: Project, remoteFileName: String) : Runtime(File(remoteFileName)) {

  override val fileName: String = remoteFileName

  override fun install() {
    process(
      produce(CommandFactory.Type.DOWNLOAD, this),
      produce(CommandFactory.Type.EXTRACT, this),
      produce(CommandFactory.Type.COPY, this),
      produce(CommandFactory.Type.INSTALL, this)
    )
  }
}