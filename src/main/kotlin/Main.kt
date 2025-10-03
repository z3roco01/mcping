package z3roco01.mcping

import z3roco01.mcping.varint.VarInt
import java.net.Socket

fun main() {
    //val sock = Socket("mc.hypixel.net", 25565)
    val varInt = VarInt(25565)
    val bytes = varInt.toBytes()
    for(b in bytes) {
        print("0x${b.toUByte().toHexString(HexFormat.UpperCase)} ")
    }
    println()
}