package z3roco01.mcping.varint

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertTrue

class VarIntTest {
    @Test
    fun testVarIntEncode() {
        // from https://minecraft.wiki/w/Java_Edition_protocol/Packets#VarInt_and_VarLong
        testEncodeCase(0,           byteArrayOf(0x00))
        testEncodeCase(1,           byteArrayOf(0x01))
        testEncodeCase(2,           byteArrayOf(0x02))
        testEncodeCase(127,         byteArrayOf(0x7F))
        testEncodeCase(128,         byteArrayOf(0x80.toByte(), 0x01))
        testEncodeCase(255,         byteArrayOf(0xFF.toByte(), 0x01))
        testEncodeCase(25565,       byteArrayOf(0xDD.toByte(), 0xC7.toByte(), 0x01))
        testEncodeCase(2097151,     byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0x7F.toByte()))
        testEncodeCase(2147483647,  byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0x07))
        testEncodeCase(-1,          byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0x0F))
        testEncodeCase(-2147483648, byteArrayOf(0x80.toByte(), 0x80.toByte(), 0x80.toByte(), 0x80.toByte(), 0x08))
    }

    /**
     * Test one encode case
     */
    private fun testEncodeCase(realValue: Int, expected: ByteArray) {
        val varInt = VarInt(realValue)
        val real = varInt.toBytes()
        assertTrue(real.contentEquals(expected),
            "For value: ${realValue}, expected: ${expected.contentToString()} got: ${real.contentToString()}")
    }
}