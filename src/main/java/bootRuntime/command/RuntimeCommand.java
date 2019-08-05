// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package bootRuntime.command;

import bootRuntime.main.Controller;
import com.intellij.openapi.project.Project;
import bootRuntime.bundles.Runtime;

public abstract class RuntimeCommand extends Command {
  final Runtime myRuntime;

  RuntimeCommand(Project project, Controller controller, String name, Runtime runtime) {
    super(project, controller, name);
    myRuntime = runtime;
  }

  Runtime getRuntime() {
    return myRuntime;
  }

  @Override
  protected void handleFinished() {
    myController.runtimeSelected(myRuntime);
  }
}
