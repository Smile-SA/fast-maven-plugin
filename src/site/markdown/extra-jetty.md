# Extra

## Jetty

As some browser doesn't like you to play with local file, you may want to test your work through a web server.
For example, if you use tools like the excelent [-prefix-free](http://leaverou.github.com/prefixfree), you may find it useful =)

To do so, we don't need to install anything, we simply will use command line interface (CLI) :

```
cd $PROJECT_HOME
mvn jetty:run
```

Then, you can access [http://localhost:8080](http://localhost:8080) in your browser.