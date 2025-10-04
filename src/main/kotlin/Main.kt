package z3roco01.mcping

import z3roco01.mcping.network.HandshakePacket
import z3roco01.mcping.network.MCSocket
import z3roco01.mcping.network.Packet
import z3roco01.mcping.varint.VarInt

fun main() {
    val sock = MCSocket("127.0.0.1")
    sock.getStatus()
    /*var varint = VarInt(772)
    var varintB = varint.toBytes()
    for(b in varintB) {
        print("${b.toHexString(HexFormat.UpperCase)} ")
    }
    println()
    varint.value = 14
    varintB = varint.toBytes()
    for(b in varintB) {
        print("${b.toHexString(HexFormat.UpperCase)} ")
    }
    println()

    val pack = HandshakePacket(VarInt(772), "mc.hypixel.net", 25565u, Packet.Intent.STATUS)
    val bytes = pack.toBytes()
    for(b in bytes) {
        print("${b.toHexString(HexFormat.UpperCase)} ")
    }
    println()*/
}