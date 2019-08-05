// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package bootRuntime.command;


import bootRuntime.main.Controller;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.function.Consumer;

public abstract class Command extends AbstractAction {
  private final Project myProject;
  Controller myController;

  Command(Project project, Controller controller, String name) {
    super(name);
    myProject = project;
    myController = controller;
  }

  protected void handleFinished() {
    myController.updateRuntime();
  }

  void runWithProgress(String title, final Consumer<? super ProgressIndicator> progressIndicatorConsumer) {
    ProgressManager.getInstance().run(new Task.Modal(myProject, title, false) {
      @Override
      public void run(@NotNull ProgressIndicator progressIndicator) {
        progressIndicatorConsumer.accept(progressIndicator);
      }

      @Nullable
      @Override
      public NotificationInfo notifyFinished() {
        handleFinished();
        return super.notifyFinished();
      }
    });
  }
}