package z3roco01.mcping.network

interface Packet {
    fun toBytes(): ByteArray

    /**
     * The intent values for settings the state when handshaking
     */
    enum class Intent{
        ZERO,
        STATUS,
        LOGIN,
        TRANSFER
    }
}