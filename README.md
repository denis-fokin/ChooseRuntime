# Plugin to switch the Java runtime

IntelliJ IDEA includes JetBrains Runtime. It is recommended to run IntelliJ IDEA using JetBrains Runtime, which fixes various known OpenJDK and Oracle JDK bugs, and provides better performance and stability. But in some cases you may be required to use another Java runtime or a specific version of JetBrains Runtime.

With the plugin you can do the following:

* choose any build of JetBrains Runtime available in the JetBrains BinTray storage
* choose among detected local Java runtimes installed in common places
* manually specify the location of a Java runtime anywhere in your file system

## How to use the plugin

The plugin is intended for JetBrains support assistance (for example, when support engineers ask a user with an issue to run IntelliJ IDEA on a specific Java runtime). For this reason, the dialog is not discoverable through menus or toolbars to avoid unintended use.

To open the **Choose Runtime** dialog, invoke **Search Everywhere** (press Shift twice) or **Find Action** (Ctrl + Shift + A or Cmd + Shift + A) and search for **Choose Runtime**.

When you open the **Choose Runtime** dialog for the first time, it may take a while to load the list of runtimes from the server. This process is indicated with a progress bar. The list contains available local and remote runtimes. The runtime which is currently used to bootstrap the IDE is written in bold.

You can type a build number or Java version to filter the list. Every time you select a runtime, the bottom-right button is updated.

If you want to reset to the default runtime that the IDE initially used, click **Use Default**.

If you want to remove previously downloaded runtimes, use the **Clean Up** button. Note that the currently used boot runtime is not deleted in this case.

To install a local runtime from disk, use the ellipsis **...** button to the right of the list.

When you click the **Install** button, the IDE reboots with the selected runtime.
