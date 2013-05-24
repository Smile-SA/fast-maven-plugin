# Eclipse

## Introduction

To ease working with FAST, the best to do is to use a tool that can execute the Maven task automatically.

Having Eclipse configured to work hand-in-hand with Maven really helps !
To do so, we'll use an Eclipse plugin called [m2Eclipse](http://eclipse.org/m2e).

(of course if you want to use any other mechanism, you're free to do so ; keep in mind that having the Maven task automatic makes the process transparent for developer).

## Install : Eclipse

FAST through m2eclipse has been tested with those configurations :

- Helios (Eclipse 3.6)
- Indigo (Eclipse 3.7)
- Juno (Eclipse 4.2)
- Kepler (Eclipse 4.xx)

You can find those bundles on [Eclipse official website](http://www.eclipse.org/downloads).

## Install : m2Eclipse

You can find m2eclipse on its official website : http://eclipse.org/m2e.
The easier to use it is by defining a new update site into eclipse :

1. Inside Eclispe : Help > Install new software
1. Use this "update site" : http://download.eclipse.org/technology/m2e/releases
1. Check "m2e - Maven integration for Eclipse" then click "Next".
1. Eclipse should be "Calculating requirements and dependencies" for a while.
1. Eclipse wil show what you're about to install, click "Next", accept the agreement and click "Finish"
1. Wait for the install to end ...

## Eclipse : 'Package explorer' view

Even for PHP bundle, the 'Package explorer' view is the best to work with Maven project as it easier to access resources.
But, it is not mandatory =)

## Eclipse : Importing your project

1. Go to : File > Import > Existing maven projects
1. Browse to your project, where you can find the pom.xml file.
1. If you go a screen "Setup maven plugin connectors", click "Resolve All Later" and "Finish".
1. Validate the alert.

Project should be in "package explorer".
