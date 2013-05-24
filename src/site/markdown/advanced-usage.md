# Advanced Usage

## Configuring the plugin in an existing project

You can also add this plugin to an existing project.

Add this plugin to the pom.xml :

```xml
<plugins>
	[...]
	<plugin>
		<groupId>org.fast.maven.plugin</groupId>
		<artifactId>fast-maven-plugin</artifactId>
		<version>${currentVersion}</version>
		<configuration>
			<charset>UTF-8</charset>
		</configuration>
	</plugin>
	[...]
</plugins>
```

And add FAST repository as plugin repository :

```xml
<pluginRepositories>
	<pluginRepository>
		<id>smile-github-repository</id>
		<name>Smile Github repository</name>
		<url>http://smile-sa.github.com/maven-repository/releases</url>
	</pluginRepository>
</pluginRepositories>
```

Create the following directory structure

```
|--src/main/resources/
	|-- modules/
	|-- pages/
```

## Bind to build lifecyle

To bind the plugin to the standard build lifecycle :

```xml
<plugin>
	<groupId>org.fast.maven.plugin</groupId>
	<artifactId>fast-maven-plugin</artifactId>
	<version>${currentVersion}</version>
	<executions>
		<execution>
			<phase>process-resources</phase>
			<goals>
				<goal>assembly</goal>
			</goals>
		</execution>
	</executions>
	<configuration>
		<charset>UTF-8</charset>
	</configuration>
</plugin>	
```

## Filtering page before assembly

Resources files (pages, modules) can be filtered before plugin execution :

```xml
<resources>
	<resource>
		<directory>src/main/resources</directory>
		<filtering>true</filtering>
		<includes>
			<include>**/*.html</include>
			<include>**/*.xml</include>
		</includes>
	</resource>
	<resource>
		<directory>src/main/resources</directory>
		<filtering>false</filtering>
		<excludes>
			<exclude>**/*.html</exclude>
			<exclude>**/*.xml</exclude>
		</excludes>
	</resource>
</resources>
```

## Escaping taglib

Suppose you need to generate html with esigate directives (includetemplate, beginparam, ...) :

```html
<!--$beginparam$title$--><title>Title to be replaced</title><!--$endparam$title$--> 
```

You must "escape" thoses directives in html sources (pages or modules) :

```html
<!--#$beginparam$title$--><title>Title to be replaced</title><!--#$endparam$title$-->  
```

