package me.ezeh.safarinet.i18n

enum class LangKey(val defaultString: String) {
    VERSION("SafariNet is running version {{version}}"),
    USAGE(
            """|SafariNet: Version {{version}}
            |Usage: /{{command}} <command>
            |  - give <player> [single | reusable] count:
            |        Gives player a 'Safari-Net' to use, when 'single', the net may only be used once, whereas when 'reusable', the net can be used multiple times.
            |  - reload:
            |        Reloads the plugin
            """.trimMargin()),
    INVALID_PERMISSION("You don't have the permission to do this, to  do this, you will need to have the permission {{permission}}"),
    MUST_BE_PLAYER("You must be a player to do this"),
    PLAYER_NOT_ONLINE("The player {{player}} is not online"),
    RELOAD_SUCCESSFUL("The config has been reloaded successfully");
    val string: String
        get() = defaultString // TODO: i18n
}