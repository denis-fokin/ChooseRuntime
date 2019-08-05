// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package bootRuntime.command;

import bootRuntime.bundles.Remote;
import bootRuntime.main.Controller;
import com.intellij.openapi.project.Project;
import bootRuntime.bundles.Runtime;

public class CommandFactory {

  public enum Type {
    DOWNLOAD,
    EXTRACT,
    COPY,
    INSTALL,
    UNINSTALL,
    DELETE,
    REMOTE_INSTALL
  }

  private final Project myProject;
  private final Controller myController;

  private CommandFactory(Project project, Controller controller) {
    myProject = project;
    myController = controller;
  }

  private static CommandFactory instance;

  public static void initialize(Project project, Controller controller) {
    instance = new CommandFactory(project, controller);
  }

  private static CommandFactory getInstance() {
    if (instance == null) throw new IllegalStateException("Command Factory has not been initialized");
    return instance;
  }

  public static RuntimeCommand produce(Type commandType, Runtime runtime) {
    switch (commandType) {
      case REMOTE_INSTALL:
        return new RemoteInstall(getInstance().myProject, getInstance().myController, runtime);
      case DOWNLOAD:
        return new Download(getInstance().myProject, getInstance().myController, runtime);
      case EXTRACT:
        return new Extract(getInstance().myProject, getInstance().myController, runtime);
      case COPY:
        return new Copy(getInstance().myProject, getInstance().myController, runtime);
      case INSTALL:
        return new Install(getInstance().myProject, getInstance().myController, runtime);
      case UNINSTALL:
        Install install = new Install(getInstance().myProject, getInstance().myController, runtime);
        install.setEnabled(false);
        return install;
      case DELETE:
        Delete delete = new Delete(getInstance().myProject, getInstance().myController, runtime);
        delete.setEnabled(runtime instanceof Remote);
        return delete;
    }
    throw new IllegalStateException("Unknown Command Type");
  }
}
