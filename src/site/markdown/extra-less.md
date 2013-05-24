# Extra

## LESS

CSS preprocessors are useful tools to build coherent CSS files.
[LESS](http://lesscss.org) is one of them.

To use it with Maven, we'll use this [LESS maven plugin](https://github.com/marceloverdijk/lesscss-maven-plugin).

```xml
<build>
	<plugins>
		<plugin>
			<groupId>org.lesscss</groupId>
			<artifactId>lesscss-maven-plugin</artifactId>
			<configuration>
				<sourceDirectory>${project.basedir}/src/main/resources/less</sourceDirectory>
				<outputDirectory>${project.build.directory}/generated-html/v1/css</outputDirectory>
			</configuration>
		</plugin>
	</plugins>
</build>
```
