package z3roco01.mcping.network

import z3roco01.mcping.varint.VarInt
import kotlin.math.min

/**
 * 13w41b+ ( 1.7 snapshot ) Handshake Packet
 * does NOT support 13w41a, it uses a VarInt for the port, but has the same id as 13w41b which does not use VarInt
 */
class HandshakePacket(val protocolVersion: VarInt, val hostname: String, val port: UShort, val intent: Packet.Intent):
    Packet{
    override fun toBytes(): ByteArray {
        // the varint encoded protocol version
        val protocolBytes = protocolVersion.toBytes()
        // the length of the hostname as a varint byte array, only up to 255 characters
        val hostnameLengthBytes = VarInt(min(hostname.length, 255)).toBytes()
        // 1 for id, protocol version length, hostname length bytes size, hostname length, port length, intent length
        val length = 1 + protocolBytes.size + hostnameLengthBytes.size + hostname.length + 2 + 1

        val bytes = ByteArray(length)
        // to keep track of the offset and make it cleaner
        var curOffest = 0

        bytes[curOffest] = HANDSHAKE_ID
        curOffest = 1

        // copy protocol version into
        protocolBytes.copyInto(bytes, curOffest)
        curOffest += protocolBytes.size

        // copy in the hostname length ( VarInt )
        hostnameLengthBytes.copyInto(bytes, curOffest)
        curOffest += hostnameLengthBytes.size

        // get hostname as bytes
        var hostnameBytes = hostname.toByteArray(Charsets.UTF_8)
        // keep it within range of 255 bytes
        if(hostnameBytes.size > 255) hostnameBytes = hostnameBytes.copyOf(255)
        // copy it in
        hostnameBytes.copyInto(bytes, curOffest)
        curOffest += hostnameBytes.size

        // write in the 2 server port bytes
        short2ByteArray(port).copyInto(bytes, curOffest)
        curOffest += 2

        // write in the intent as a varint
        VarInt(intent.ordinal).toBytes().copyInto(bytes, curOffest)

        // its finally done so return it
        return bytes
    }

    /**
     * Takes in a UShort and returns a ByteArray ( to make conversion easier )
     */
    private fun short2ByteArray(short: UShort) =byteArrayOf(
        (short.toInt() shr 8).toByte(), // High byte
        short.toByte()         // Low byte
    )

    companion object {
        // the id of the handshake packet
        const val HANDSHAKE_ID = 0x00.toByte()
    }
}