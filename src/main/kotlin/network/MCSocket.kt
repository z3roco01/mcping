package z3roco01.mcping.network

import z3roco01.mcping.varint.VarInt
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
    constructor(hostname: String): this(hostname, 772)

    fun getStatus() {
        val sock = Socket(hostname, port)
        val output = sock.getOutputStream()
        // send a handshake packet immediately
        val handshake = HandshakePacket(VarInt(protocolVersion), hostname, port.toUShort(), Packet.Intent.STATUS)
        output.write(handshake.toBytes())
        output.flush()

        // create and send a status request packet
        output.write(StatusRequestPacket().toBytes())
        output.flush()
    }
}