// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package bootRuntime.command;

import bootRuntime.main.Controller;
import com.intellij.openapi.project.Project;
import bootRuntime.bundles.Runtime;

import java.awt.event.ActionEvent;

public class Copy extends RuntimeCommand {
  Copy(Project project, Controller controller, Runtime runtime) {
    super(project, controller, "Copy", runtime);
  }

  @Override
  public void actionPerformed(ActionEvent e) {}
}
