# Choose Runtime Plugin

IntelliJ IDEA includes JetBrains Runtime. It is recommended to run IntelliJ IDEA using JetBrains Runtime, which fixes various known OpenJDK and Oracle JDK bugs, and provides better performance and stability. But in some cases you may be required to use another Java runtime or a specific version of JetBrains Runtime.

## Where the Runtimes come from?

With the plugin you can
specify a location on the disk
choose among Java Runtimes installed in common places
choose a runtime from runtimes stored on our BinTray storage

## How to run the action?

As far as the plugin mostly intended for IntelliJ support assistance, the action is not discoverable through menus or toolbars. To open the Choose Runtime dialog, use Search Everywhere or Find Action actions.

## What am I supposed to see?

To load data from the server the first show of the Choose Runtime dialog takes time. You see a progress at this moment. Right after the job done a dialog has appeared. When the dialog opens, there is a combobox. The combobox contains an item in bold. This item is a Runtime that is currently used to bootstrap the IDE.

## What can I do?

You can use the combobox to choose one of the Runtimes on the list. You can just type a build or java version to filter out the content of the list. Every time you pick out another Runtime, corresponding actions in the bottom right corner are updated.

## If I want to use the Runtime that the IDE was initially bundled with?

If you need to reset the Runtime, press "Use Default" button.

## Every Runtime wastes space, right?

If you need to clean up space, use the "Clean Up” button. If the current bootstrap Runtime is a downloaded one, it is not deleted.

## How to install a Runtime that I have downloaded manually?

In order to install a local Runtime, which location is not a usual one, use the ellipsis [...] button on the right of the combobox.

## Eventually...

Each time you choose the install action, the IDE ends up in reboot.
