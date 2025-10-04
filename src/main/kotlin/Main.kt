package z3roco01.mcping

import z3roco01.mcping.network.HandshakePacket
import z3roco01.mcping.network.MCSocket
import z3roco01.mcping.network.Packet
import z3roco01.mcping.varint.VarInt

fun main() {
    val sock = MCSocket("2b2t.org")
    println(sock.getStatus())
}