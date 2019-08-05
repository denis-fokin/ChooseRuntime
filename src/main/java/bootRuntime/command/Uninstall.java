// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package bootRuntime.command;

import bootRuntime.main.BinTrayUtil;
import bootRuntime.main.Controller;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.io.FileUtil;


import java.awt.event.ActionEvent;
import bootRuntime.bundles.Runtime;

public class Uninstall extends RuntimeCommand {
  public Uninstall(Project project, Controller controller, Runtime runtime) {
    super(project, controller, "Uninstall", runtime);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    runWithProgress("Uninstalling...", indicator -> {
      FileUtil.delete(BinTrayUtil.getJdkConfigFilePath());
      myController.restart();
    });
  }
}
