# Agile Engine Smart XML Analyzer

## Compile
To compile the source code run the following command

```$ mvn clean package```

It generates a file in the target directory:

```./target/HtmlCrawler-1.0.0.jar```

## Execute

To execute the program you need to run

````java -jar target/HtmlCrawler-1.0.0.jar````

The system receives up to three parameters

- The firs one is the path to the file you want to analyze. Its default value is "samples/sample0/sample0.html". A relative path to the root directory of the project.

- The secoond one is the element id you are looking for. The default value is "make-everything-ok-button".

- The third one is the source of the file: WEB for a web page or LOCAL for the local file system. The default value is LOCAL. But if you enter anything different to "WEB" (not key sensitive) it takes LOCAL too.

But if you want to change the target element id, you must enter the path. And if you want to change the source, you must enter the path and the target element. 

So you can run:


 ````
 $ java -jar target/HtmlCrawler-1.0.0.jar $https://agileengine.bitbucket.io/beKIvpUlPMtzhfAy/samples/sample-0-origin.html make-everithing-ok-button WEB
 
 $ java -jar target/HtmlCrawler-1.0.0.jar samples/sample0/sample0.html make-everithing-ok-button LOCAL
 
 $ java -jar target/HtmlCrawler-1.0.0.jar samples/sample0/sample0.html make-everithing-ok-button
 
 $ java -jar target/HtmlCrawler-1.0.0.jar samples/sample0/sample0.html
 
 $ java -jar target/HtmlCrawler-1.0.0.jar
 
 ````
 
 All of them with the same result.
 
 
 Run the following commands for the requested exaples:
 ````
 $ java -jar target/HtmlCrawler-1.0.0.jar samples/sample0/sample0.html
 2018-05-25 22:24:33.696  INFO 23407 --- [           main] a.c.t.a.HtmlCrawlerStandaloneApp         : The following profiles are active: default
 2018-05-25 22:24:34.296  INFO 23407 --- [           main] a.c.t.a.HtmlCrawlerStandaloneApp         : Started HtmlCrawlerStandaloneApp in 1.033 seconds (JVM running for 1.448)
 2018-05-25 22:24:34.298  INFO 23407 --- [           main] a.c.t.a.HtmlCrawlerStandaloneApp         : Analyzing file: samples/sample0/sample0.html
 2018-05-25 22:24:34.299  INFO 23407 --- [           main] a.c.t.a.HtmlCrawlerStandaloneApp         : Looking for element ID: make-everything-ok-button
 2018-05-25 22:24:34.392  INFO 23407 --- [           main] a.c.t.a.HtmlCrawlerStandaloneApp         : Result element path: <a id="make-everything-ok-button" class="btn btn-success" href="#ok" title="Make-Button" rel="next" onclick="javascript:window.okDone(); return false;"> Make everything OK </a> > div > div > div > div > div > div > body > html > #document
 
 $ java -jar target/HtmlCrawler-1.0.0.jar samples/sample1/sample1.html
 2018-05-25 22:22:39.562  INFO 23396 --- [           main] a.c.t.a.HtmlCrawlerStandaloneApp         : Starting HtmlCrawlerStandaloneApp v1.0.0 on Marianos-MacBook-Pro.local with PID 23396 (/Users/tuchorc/Development/agieleengine/target/HtmlCrawler-1.0.0.jar started by tuchorc in /Users/tuchorc/Development/agieleengine)
 2018-05-25 22:22:39.565  INFO 23396 --- [           main] a.c.t.a.HtmlCrawlerStandaloneApp         : The following profiles are active: default
 2018-05-25 22:22:40.136  INFO 23396 --- [           main] a.c.t.a.HtmlCrawlerStandaloneApp         : Started HtmlCrawlerStandaloneApp in 1.186 seconds (JVM running for 1.605)
 2018-05-25 22:22:40.137  INFO 23396 --- [           main] a.c.t.a.HtmlCrawlerStandaloneApp         : Analyzing file: samples/sample1/sample1.html
 2018-05-25 22:22:40.137  INFO 23396 --- [           main] a.c.t.a.HtmlCrawlerStandaloneApp         : Looking for element ID: make-everything-ok-button
 2018-05-25 22:22:40.233  INFO 23396 --- [           main] a.c.t.a.HtmlCrawlerStandaloneApp         : Result element path: <a class="btn btn-success" href="#check-and-ok" title="Make-Button" rel="done" onclick="javascript:window.okDone(); return false;"> Make everything OK </a> > div > div > div > div > div > div > body > html > #document
 
 
 $ java -jar target/HtmlCrawler-1.0.0.jar samples/sample2/sample2.html
 2018-05-25 22:23:14.679  INFO 23400 --- [           main] a.c.t.a.HtmlCrawlerStandaloneApp         : Starting HtmlCrawlerStandaloneApp v1.0.0 on Marianos-MacBook-Pro.local with PID 23400 (/Users/tuchorc/Development/agieleengine/target/HtmlCrawler-1.0.0.jar started by tuchorc in /Users/tuchorc/Development/agieleengine)
 2018-05-25 22:23:14.683  INFO 23400 --- [           main] a.c.t.a.HtmlCrawlerStandaloneApp         : The following profiles are active: default
 2018-05-25 22:23:15.319  INFO 23400 --- [           main] a.c.t.a.HtmlCrawlerStandaloneApp         : Started HtmlCrawlerStandaloneApp in 1.096 seconds (JVM running for 1.519)
 2018-05-25 22:23:15.320  INFO 23400 --- [           main] a.c.t.a.HtmlCrawlerStandaloneApp         : Analyzing file: samples/sample2/sample2.html
 2018-05-25 22:23:15.320  INFO 23400 --- [           main] a.c.t.a.HtmlCrawlerStandaloneApp         : Looking for element ID: make-everything-ok-button
 2018-05-25 22:23:15.423  INFO 23400 --- [           main] a.c.t.a.HtmlCrawlerStandaloneApp         : Result element path: <a class="btn test-link-ok" href="#ok" title="Make-Button" rel="next" onclick="javascript:window.okComplete(); return false;"> Make everything OK </a> > div > div > div > div > div > div > div > body > html > #document
 
 
 $ java -jar target/HtmlCrawler-1.0.0.jar samples/sample3/sample3.html
 2018-05-25 22:23:31.193  INFO 23401 --- [           main] a.c.t.a.HtmlCrawlerStandaloneApp         : The following profiles are active: default
 2018-05-25 22:23:31.743  INFO 23401 --- [           main] a.c.t.a.HtmlCrawlerStandaloneApp         : Started HtmlCrawlerStandaloneApp in 0.977 seconds (JVM running for 1.418)
 2018-05-25 22:23:31.744  INFO 23401 --- [           main] a.c.t.a.HtmlCrawlerStandaloneApp         : Analyzing file: samples/sample3/sample3.html
 2018-05-25 22:23:31.744  INFO 23401 --- [           main] a.c.t.a.HtmlCrawlerStandaloneApp         : Looking for element ID: make-everything-ok-button
 2018-05-25 22:23:31.851  INFO 23401 --- [           main] a.c.t.a.HtmlCrawlerStandaloneApp         : Result element path: <a class="btn btn-success" href="#ok" title="Do-Link" rel="next" onclick="javascript:window.okDone(); return false;"> Do anything perfect </a> > div > div > div > div > div > div > body > html > #document
 
 $ java -jar target/HtmlCrawler-1.0.0.jar samples/sample4/sample4.html
 2018-05-25 22:23:56.034  INFO 23403 --- [           main] a.c.t.a.HtmlCrawlerStandaloneApp         : The following profiles are active: default
 2018-05-25 22:23:56.601  INFO 23403 --- [           main] a.c.t.a.HtmlCrawlerStandaloneApp         : Started HtmlCrawlerStandaloneApp in 0.999 seconds (JVM running for 1.446)
 2018-05-25 22:23:56.602  INFO 23403 --- [           main] a.c.t.a.HtmlCrawlerStandaloneApp         : Analyzing file: samples/sample4/sample4.html
 2018-05-25 22:23:56.602  INFO 23403 --- [           main] a.c.t.a.HtmlCrawlerStandaloneApp         : Looking for element ID: make-everything-ok-button
 2018-05-25 22:23:56.703  INFO 23403 --- [           main] a.c.t.a.HtmlCrawlerStandaloneApp         : Result element path: <a class="btn btn-success" href="#ok" title="Make-Button" rel="next" onclick="javascript:window.okFinalize(); return false;"> Do all GREAT </a> > div > div > div > div > div > div > body > html > #document 
 ````