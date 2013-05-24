# Extra

## SASS

CSS preprocessors are useful tools to build coherent CSS files.
[SASS](http://sass-lang.com) is one of them.

To use it with Maven, we'll use this [SASS maven plugin](https://github.com/Jasig/sass-maven-plugin)

```xml
<build>
	<plugins>
		<plugin>
			<groupId>org.jasig.maven</groupId>
			<artifactId>sass-maven-plugin</artifactId>
			<configuration>
				<sassSourceDirectory>${project.basedir}/src/main/resources/sass</sassSourceDirectory>
				<outputDirectory>${project.build.directory}/generated-html/v1/css</outputDirectory>
				<baseOutputDirectory>${project.build.directory}/generated-html/v1/css</baseOutputDirectory>
			</configuration>
		</plugin>
	</plugins>
</build>
```
