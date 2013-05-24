# Extra

## Merge and minification

Minifying your javascript or CSS is an important step to optimize your web application.
As it can highlight some bug in your code, it is useful to detect it as soon as possible.
For this reason, we can add minification to the mockup generation and validate that our webpage is correct with minified elements. 

To activate minification with FAST, all you have to do is to add a plugin :

```xml
<build>
	<plugins>
		<plugin>
			<groupId>com.samaxes.maven</groupId>
			<artifactId>maven-minify-plugin</artifactId>
			<configuration>
				<cssSourceDir>v1/css</cssSourceDir>
				<cssTargetDir>v1/minified</cssTargetDir>
				<suffix>-min</suffix>
				<jsSourceDir>v1/js</jsSourceDir>
				<jsTargetDir>v1/minified</jsTargetDir>
				<webappSourceDir>${basedir}/src/main/resources/pages</webappSourceDir>
				<webappTargetDir>${basedir}/src/main/resources/pages</webappTargetDir>
			</configuration>
			<executions>
				...
			</executions>
		</plugin>
	</plugins>
</build>
```

Into the *executions* section you can define as many minified elements you want, one section for each element (be sure to have unique id though) :

```xml
<execution>
	<id>minify-js</id>
	<configuration>
		<jsSourceFiles>
			<jsSourceFile>jquery-1.7.1.min.js</jsSourceFile>
			<jsSourceFile>init-utilities.js</jsSourceFile>
			<jsSourceFile>jquery.metadata.js</jsSourceFile>
			<jsSourceFile>jquery.tablesorter.min.js</jsSourceFile>
			<jsSourceFile>init.js</jsSourceFile>
		</jsSourceFiles>
		<jsFinalFile>script.js</jsFinalFile>
	</configuration>
</execution>
```

With this configuration, we create a file script.js which contain the listed javascript.
A file script-min.js is also created, it is a minified version of script.js.

You can create as many execution block as you need for each minified files you want to create (JS or CSS).

For more information, check [the maven-samaxes-plugin official website](http://code.google.com/p/maven-samaxes-plugin).
