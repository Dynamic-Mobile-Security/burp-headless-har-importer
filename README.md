# Burp headless HAR Importer
This is a headless plugin for Burp HAR importer (https://github.com/Dynamic-Mobile-Security/burp-har-importer/)

To work in headless mode, you need both jar side by side:
* burpsuite_pro.jar
* headless-burp-scanner-master-SNAPSHOT-jar-with-dependencies.jar.
  
It will run from the command line like this:

```java -Xmx1G -Djava.awt.headless=true -classpath headless-burp-scanner-master-SNAPSHOT-jar-with-dependencies.jar:burpsuite_pro_v2021.9.1.jar burp.StartBurp --project-file=project.burp --user-config-file=burp-config-file.json```

Example of a part of a file `<user-config-filename.json>`:

```
"extender":{
  "extensions":[
  { // plugin example installed manually
  "errors":"ui",
  "extension_file":"/home/kali/.BurpSuite/bapps/HarSiteMapCli.jar",
  "extension_type":"java",
  "loaded":true,
  "name":"HarSiteMapCli",
  "output":"ui"},
  ] 
}
```

## Plugin setup:
In the working directory (from where burp.jar starts) there should be a configuration file named `har_files_to_import.json`
from which har-files will be read (you can specify several).
```
{
  "harfiles":[
    {
      "path":"/home/user/temp/burp-plugin-test-files/mock_api.har"
    },
    {
      "path":"data.har"
    }
  ]
}
```

## Building the plugin
gradle -v
Gradle 6.4.1

gradle buildFatJar