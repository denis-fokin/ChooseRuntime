// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package bootRuntime.main

import bootRuntime.bundles.Local
import bootRuntime.bundles.Remote
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.SystemInfo
import java.io.File
import java.net.URL
import java.util.regex.Pattern
import bootRuntime.bundles.Runtime
import java.net.UnknownHostException

private val WINDOWS_X64_JVM_LOCATION = File.listRoots().map { r ->  File(r.absolutePath + "Program Files/Java") }.toList()
private val WINDOWS_X86_JVM_LOCATION = File.listRoots().map { r ->  File(r.absolutePath + "Program Files (x86)/Java") }.toList()
private val MAC_OS_JVM_LOCATIONS = File.listRoots().map { r ->  File(r.absolutePath + "/Library/Java/JavaVirtualMachines") }.toList()
private val LINUX_JVM_LOCATIONS = File.listRoots().flatMap { r -> listOf(File(r.absolutePath + "/usr/lib/jvm"),
                                                                         File(r.absolutePath + "/usr/java"))}.toList()

enum class OperationSystem {
  Windows32, Windows64, Linux, MacOSX
}

fun javaHomeFromFile(file:File): File {
  return file.parentFile.parentFile
}

fun javaHomeToInstallationLocation(file:File): File {
  return if (SystemInfo.isMac) {
    file.parentFile.parentFile
  } else {
    file
  }
}

class RuntimeLocationsFactory {

  fun runtimesFrom (locations: List<File>) : List<File> {
    return locations.flatMap{ l -> l.walk().filter {
      file -> file.name == "tools.jar" ||
              file.name == "jrt-fs.jar"
    }.map{ file -> javaHomeToInstallationLocation(javaHomeFromFile(file)) }.toList()}.toList()
  }

  fun runtimeLocations(operationSystem: OperationSystem): List<File> {
    return when (operationSystem) {
      OperationSystem.Windows32, OperationSystem.Windows64 -> WINDOWS_X86_JVM_LOCATION + WINDOWS_X64_JVM_LOCATION
      OperationSystem.Linux -> LINUX_JVM_LOCATIONS
      OperationSystem.MacOSX -> MAC_OS_JVM_LOCATIONS
    }
  }

  fun bundlesFromLocations(project: Project, locations: List<File>): List<Runtime> {
      return locations.map { location -> Local(project, location) }.filter { !it.fileName.contains("1.9") }.toList()
  }

  fun operationSystem() : OperationSystem {
    return when  {
      SystemInfo.is64Bit && SystemInfo.isWindows -> OperationSystem.Windows64
      SystemInfo.is32Bit && SystemInfo.isWindows -> OperationSystem.Windows32
      SystemInfo.isMac -> OperationSystem.MacOSX
      else -> OperationSystem.Linux
    }
  }

  fun localBundles(project: Project) : List<Runtime> {
    return bundlesFromLocations(project, runtimesFrom(runtimeLocations(operationSystem())))
  }

  fun bintrayBundles(project: Project): List<Runtime> {

    val subject = BinTrayConfig.subject
    val repoName = BinTrayConfig.repoName
    val jbrRepoName = BinTrayConfig.jbrRepoName
    val linkTemplate = "https://dl.bintray.com/%s/%s";

    val runtimes = collectRuntimes(String.format(linkTemplate, subject, repoName), project, ".*\"(jbsdk.*%s.*%s.*?)\"")
    runtimes.addAll((collectRuntimes(String.format(linkTemplate, subject, jbrRepoName), project, ".*\".*?(jbrsdk.*%s.*%s.*?)\"")))

    return runtimes
  }

  private fun collectRuntimes(link: String,
                              project: Project,
                              bundleNamePattern: String): MutableList<Runtime> {
    val response =  try {
      URL(link).readText()
    } catch (uhe:UnknownHostException) {
      ""
    }

    val osFilter = when {
      SystemInfo.isMac -> "osx"
      SystemInfo.isLinux -> "linux"
      SystemInfo.isWindows -> "win"
      else -> ""
    }

    val archFilter = when {
      SystemInfo.is32Bit -> "x86"
      else -> "x64"
    }

    val r = Pattern.compile(String.format(bundleNamePattern, osFilter, archFilter))
    val m = r.matcher(response)

    val list = mutableListOf<Runtime>()

    while (m.find()) {
      val remoteFileName = m.group(1)
      if (!remoteFileName.contains("sdk9")) {
        list.add(Remote(project, remoteFileName))
      }
    }

    return list
  }
}


