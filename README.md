HAR-plugin-for-Burp-CLI
это плагин для Burp работающий в headless режиме.

## Запускаться он будет из командной строки примерно так:
`java -Xmx1G -Djava.awt.headless=true -classpath headless-burp-scanner-master-SNAPSHOT-jar-with-dependencies.jar:burpsuite_pro_v2021.9.1.jar burp.StartBurp --project-file=project.burp --user-config-file=burp-config-file.json`

пример части файла <user-config-filename.json>

`
"extender":{
  "extensions":[
  {//пример плагина, установленного вручную
  "errors":"ui",
  "extension_file":"/home/kali/.BurpSuite/bapps/HarSiteMapCli.jar",
  "extension_type":"java",
  "loaded":true,
  "name":"HarSiteMapCli",
  "output":"ui"},
  ] 
}
`
## Сборка плагина
gradle -v
Gradle 6.4.1

gradle buildFatJar