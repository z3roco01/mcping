package z3roco01.mcping.network

import kotlinx.serialization.json.Json
import z3roco01.mcping.varint.VarInt
import java.io.BufferedInputStream
import java.io.DataOutputStream
import java.net.Socket

/**
 * A Socket which connects to a minecraft server for pinging
 * @param hostname the hostname or ip of the server
 * @param port the port the server is on ( usually 25565)
 */
class MCSocket(val hostname: String, val port: Int, val protocolVersion: Int) {
    // a constructor without the port, uses the default port
    constructor(hostname: String, protocolVersion: Int): this(hostname, 25565, protocolVersion)
    // constructor with just the hostname, uses most recent protocol version
    constructor(hostname: String): this(hostname, 773)

    fun getStatus(): StatusRequestData? {
        val sock = Socket(hostname, port)
        val output = DataOutputStream(sock.getOutputStream())
        val input = BufferedInputStream(sock.getInputStream())
        // send a handshake packet immediately
        val handshake = HandshakePacket(VarInt(protocolVersion), hostname, port.toUShort(), Packet.Intent.STATUS)
        output.write(handshake.toBytes())
        output.flush()

        // create and send a status request packet
        output.write(StatusRequestPacket().toBytes())
        output.flush()

        // read in the packet lenght
        val packetLen = VarInt.fromStream(input)
        // then read in the payload
        val payload = input.readNBytes(packetLen.value)
        if(payload[0].toInt() != 0x00)
            throw RuntimeException("invalid packet id for status reponse: ${payload[0]}")

        // get the payload as a string
        val jsonStr = payload.copyOfRange(3, payload.size).toString(Charsets.UTF_8)

        return Json.decodeFromString<StatusRequestData>(jsonStr)
    }
}