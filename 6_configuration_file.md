# 6 - Configuration file

[sconfig](https://github.com/ekrich/sconfig) is a Scala port of Lightbend [config], it provides parsing of configuration files in various formats:

* Java properties (key=value)
* JSON
* HOCON

## Goal

* Fill out the blanks in [ConfigFile.scala](app/src/main/scala/com/tapad/app/ConfigFile.scala)

Your application should read an accesstoken from a configuration file in `~/.config/tws/tws.conf` (adhering to the [XDG Base Directory specification](https://wiki.archlinux.org/index.php/XDG_Base_Directory))