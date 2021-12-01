HAR-plugin-for-Burp-CLI
это плагин для Burp работающий в headless режиме.

Запускаться он будет из командной строки примерно так:
java -Xmx1G -Djava.awt.headless=true -classpath burpsuite_pro.jar burp.StartBurp --project-file=project.burp --user-config-file=<user-config-filename.json>

пример файла <user-config-filename.json>
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

## Сборка плагина
todo