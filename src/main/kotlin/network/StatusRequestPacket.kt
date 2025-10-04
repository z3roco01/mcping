package z3roco01.mcping.network

/**
 * 13w41b+ Status Request packet
 */
class StatusRequestPacket: Packet {
    // very simple packet, just the id
    override fun toBytes() = byteArrayOf(STATUS_REQUEST_ID)

    companion object {
        const val STATUS_REQUEST_ID = 0x00.toByte()
    }
}