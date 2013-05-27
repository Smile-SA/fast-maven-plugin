# Filtering

Sometimes you may want to externalize some properties.
This is particularly handy if you want to manage all those settings from a unique location (pom.xml for example).

If you want to use filtering on your project, you have to define a property file :

```xml
<build>
	<filters>
		<filter>src/main/filters/filter.properties</filter>
	</filters>
	...
</build>
```

Then, you'll be able to use variable within all files into src.main/resources, that means **modules** and **pages**.

For now, FAST is configured to filter those files : *.html, *.css, *.scss, *.less.

If you want to add new file types, you'll have to configure it :

```xml
<build>
	<resources>
		<resource>
			<directory>src/main/resources</directory>
			<filtering>true</filtering>
			<includes>
				<include>**/*.html</include>
				<include>**/*.xml</include>
				<include>**/*.foo</include>
			</includes>
		</resource>
		<resource>
			<directory>src/main/resources</directory>
			<filtering>false</filtering>
			<excludes>
				<exclude>**/*.html</exclude>
				<exclude>**/*.xml</exclude>
				<exclude>**/*.foo</exclude>
			</excludes>
		</resource>
	</resources>
	...
</build>
```
## Redefining delimiters

If you use variable in file containing **${myProperty}** or **@myProperty**, you may have issue with filtering, it simply may not work.
To fix this, you have to configure delimiters :

```xml
<build>
	<plugin>
		<groupId>org.apache.maven.plugins</groupId>
		<artifactId>maven-resources-plugin</artifactId>
		<configuration>
			<delimiters>
				<delimiter>${*}</delimiter>
			</delimiters>
			<useDefaultDelimiters>false</useDefaultDelimiters>
		</configuration>
	</plugin>
	...
</build>
```
