package bootRuntime.main

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManager

fun getProjectOrDefault(e: AnActionEvent): Project {
     return e.project ?: ProjectManager.getInstance().defaultProject
}
