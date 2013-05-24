# Usage

## Generate simple project with archetype

The simplest way is to generate your project with the archetype :

```
mvn archetype:generate \
	-DarchetypeCatalog=http://smile-sa.github.com/maven-repository/releases/archetype-catalog.xml \
	-DarchetypeGroupId=org.fast.archetype \
	-DarchetypeVersion=RELEASE
```

If you're starting with FAST, try the simpliest example : fast-archetype-simple.

Available archetypes are listed and described on their own website : [FAST archetypes](https://smile-sa.github.com/fast-archetype).

## How to start ?

- Pages in folder src/main/resources/pages can use the [ESIgate aggregator syntax](http://www.esigate.org/aggregator.html) in order to include template or block
- Modules in folder src/main/resources/modules are the template and block to reuse

To generate the target HTML, just type :

```
mvn compile
```

You'll find the resulting HTML into folder "target > generated-html" 

To make a package of the resulting static files, just type :

```
mvn package
```

This will generate a zip-archive into target folder.
Then you can send this archive to anyone wanting to see results of your work =)

## More plugins ?

To ease working with FAST, we defined a parent project that contains default initialisation for some useful plugin : LESS, SASS, minify, JSlint, ...
They are described on their respective page.
