package z3roco01.mcping.network

import kotlinx.serialization.Serializable

@Serializable
data class StatusRequestData(val version: VersionData, val enforcesSecureChat: Boolean = false, val description: String = "", val players: PlayersData, val favicon: String = "") {
    override fun toString() = "$version enforcesSecureChat?: $enforcesSecureChat motd: \"$description\" $players"

    @Serializable
    data class VersionData(val name: String, val protocol: Int = 0) {
        override fun toString() = "version: \"$name\" ( protocol version: \"$protocol\" )"
    }
    @Serializable
    data class PlayersData(val max: Int = 0, val online: Int = 0, val sample: List<PlayerData>) {
        override fun toString(): String {
            var str = "$online/$max players: "
            for(player in sample)
                str += "$player "

            return str
        }


        @Serializable
        data class PlayerData(val id: String, val name: String) {
            override fun toString() = "$name:$id"
        }
    }
}
