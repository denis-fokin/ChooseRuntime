// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package bootRuntime.main

import bootRuntime.bundles.Archive
import bootRuntime.bundles.Local
import bootRuntime.bundles.Remote
import com.intellij.openapi.util.io.FileUtil
import bootRuntime.bundles.Runtime

enum class BundleState {
  ARCHIVED,
  REMOTE,
  LOCAL,
  DOWNLOADED,
  EXTRACTED,
  INSTALLED,
  UNINSTALLED
}

class Model(var selectedBundle: Runtime, val bundles:MutableList<Runtime>) {

  fun updateBundle(newBundle:Runtime) {
    selectedBundle = newBundle
  }

  fun currentState () : BundleState {
     return when {
       isArchived(selectedBundle) -> BundleState.ARCHIVED
       isInstalled(selectedBundle) -> BundleState.INSTALLED
       isLocal(selectedBundle) -> BundleState.LOCAL
       isExtracted(selectedBundle) -> BundleState.EXTRACTED
       isDownloaded(selectedBundle) -> BundleState.DOWNLOADED
       isRemote(selectedBundle) -> BundleState.REMOTE
       else -> BundleState.UNINSTALLED
     }
  }

  fun isInstalled(bundle:Runtime):Boolean = bundle.installationPath.exists() &&
          BinTrayUtil.getJdkConfigFilePath().exists() &&
          FileUtil.loadFile(BinTrayUtil.getJdkConfigFilePath()).startsWith(bundle.installationPath.absolutePath)

  private fun isExtracted(bundle:Runtime):Boolean = bundle.installationPath.exists()

  private fun isDownloaded(bundle:Runtime):Boolean = bundle.downloadPath.exists()

  private fun isArchived(bundle:Runtime):Boolean = bundle is Archive

  fun isRemote(bundle:Runtime):Boolean = bundle is Remote

  fun isLocal(bundle:Runtime):Boolean = bundle is Local
}
