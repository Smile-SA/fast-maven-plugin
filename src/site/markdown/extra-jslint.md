# Extra

## JSLint

Writing good javascript code is good, writing better javascript code is even better =).
The idea behind jsLint plugin is to validate your javascript code to fix some usual mistakes.

To activate minification with FAST, all you have to do is to add a plugin :

```xml
<build>
	<plugins>
		<plugin>
			<groupId>com.googlecode.jslint4java</groupId>
			<artifactId>jslint4java-maven-plugin</artifactId>
			<executions>
				<execution>
					<configuration>
						<failOnError>true</failOnError>
						<sourceFolders>
							<sourceFolder>js</sourceFolder>
						</sourceFolders>
						<includes>
							<include>init-utilities.js</include>
							<include>jquery.bistatelink.js</include>
							<include>init.js</include>
							<include>localization.js</include>
							<include>service.js</include>
							<include>init-mockjax.js</include>
						</includes>
					</configuration>
				</execution>
			</executions>
			<configuration>
				<options>
					<sloppy>true</sloppy>
					<predef>console,jQuery,localization,confirm,serviceUrl</predef>
					<browser>true</browser>
					<vars>true</vars>
					<plusplus>true</plusplus>
				</options>
			</configuration>
		</plugin>
	</plugins>
</build>
```

The *configuration* section can be modified to fit your needs.

The *includes* section contains a list of files you want to validate.

For more information, check [the jslint4java maven plugin official website](http://jslint4java.googlecode.com/svn/docs/2.0.0/maven.html).
