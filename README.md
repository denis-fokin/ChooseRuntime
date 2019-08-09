# Choose Runtime Plugin

IntelliJ IDEA includes JetBrains Runtime. It is recommended to run IntelliJ IDEA using JetBrains Runtime, which fixes various known OpenJDK and Oracle JDK bugs, and provides better performance and stability. But in some cases you may be required to use another Java runtime or a specific version of JetBrains Runtime.

## Where the Runtimes come from?

With the plugin you can
* specify a location on the disk
* choose among Java Runtimes installed in common places
* choose a runtime from runtimes stored on our BinTray storage

## How to run the action?

As far as the plugin is mostly intended for IntelliJ support assistance, the action is not discoverable through menus or toolbars. In order to open "Choose Runtime" dialog, invoke Search Everywhere or Find Action first and search for "Choose Runtime" action

## What am I supposed to see?

The first "Choose Runtime" dialog opening takes time while Runtime list is loaded from the server. This process is indicated with a progress bar. Right after this job done the dialog appears. It contains a combobox that lists available runtimes (both local and remote). Runtime which is currently used to bootstrap the IDE is marked in bold.

## What can I do?

You can use the combobox to select a desired Runtime from the list. It is possible to just type a build or java version to filter out the content of the list. Every time you pick a  Runtime, the action on the bottom-right button is updated.

## What if I want to use the Runtime that the IDE was initially bundled with?

If you need to reset the Runtime, press "Use Default" button.

## Every Runtime wastes space, right?

If you need to remove downloaded runtimes, use the "Clean Up” button. Note that if the currently used boot Runtime is a downloaded one, it will not be deleted.

## How to install a Runtime that I have downloaded manually?

In order to install a local Runtime from the disk, use the ellipsis [...] button on the right of the combobox.

## Eventually...

Each time you choose the "Install" action, the IDE reboots with the selected boot runtime.
