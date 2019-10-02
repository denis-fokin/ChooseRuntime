// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package bootRuntime.command;

import bootRuntime.main.BinTrayUtil;
import bootRuntime.main.Controller;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.util.io.Decompressor;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import bootRuntime.bundles.Runtime;

public class Extract extends RuntimeCommand {
  private static final Logger LOG = Logger.getInstance("#com.intellij.bootRuntime.command.Extract");

  Extract(Project project, Controller controller, Runtime runtime) {
    super(project, controller, "Extract", runtime);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    runWithProgress("Extracting...", true, indicator -> {
      String archiveFileName = getRuntime().getFileName();
      String directoryToExtractName = BinTrayUtil.archveToDirectoryName(archiveFileName);
      File jdkStoragePathFile = BinTrayUtil.getJdkStoragePathFile();
      if (!jdkStoragePathFile.exists()) {
        FileUtil.createDirectory(jdkStoragePathFile);
      }

      File directoryToExtractFile = new File(jdkStoragePathFile, directoryToExtractName);
      if (!directoryToExtractFile.exists()) {
        try {
          new Decompressor.Tar(myRuntime.getDownloadPath()).extract(directoryToExtractFile);
          FileUtil.delete(myRuntime.getDownloadPath());
        }
        catch (IOException ex) {
          LOG.warn(ex);
        }
      }
    });
  }
}