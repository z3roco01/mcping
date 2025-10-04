package z3roco01.mcping.varint

import org.junit.jupiter.api.Test
import java.io.BufferedInputStream
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class VarIntTest {
    @Test
    fun testVarInt() {
        // from https://minecraft.wiki/w/Java_Edition_protocol/Packets#VarInt_and_VarLong
        testEncodeDecode(0,           byteArrayOf(0x00))
        testEncodeDecode(1,           byteArrayOf(0x01))
        testEncodeDecode(2,           byteArrayOf(0x02))
        testEncodeDecode(127,         byteArrayOf(0x7F))
        testEncodeDecode(128,         byteArrayOf(0x80.toByte(), 0x01))
        testEncodeDecode(255,         byteArrayOf(0xFF.toByte(), 0x01))
        testEncodeDecode(25565,       byteArrayOf(0xDD.toByte(), 0xC7.toByte(), 0x01))
        testEncodeDecode(2097151,     byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0x7F.toByte()))
        testEncodeDecode(2147483647,  byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0x07))
        testEncodeDecode(-1,          byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0x0F))
        testEncodeDecode(-2147483648, byteArrayOf(0x80.toByte(), 0x80.toByte(), 0x80.toByte(), 0x80.toByte(), 0x08))
    }

    /**
     * Tests one case of both encode and decode
     * @param expectedInt the expected int when decoding, and the supplied in when encoding
     * @param expectedBytes the supplied byte array when decoding, and the expected int when encoding
     */
    fun testEncodeDecode(expectedInt: Int, expectedBytes: ByteArray) {
        testEncode(expectedInt, expectedBytes)
        testDecode(expectedInt, expectedBytes)
    }

    /**
     * Test one encode case
     */
    private fun testEncode(integer: Int, expected: ByteArray) {
        val varInt = VarInt(integer)
        val real = varInt.toBytes()
        assertTrue(real.contentEquals(expected),
            "For value: ${integer}, expected: ${expected.contentToString()} got: ${real.contentToString()}")
    }

    /**
     * Test one decode case
     */
    private fun testDecode(expected: Int, bytes: ByteArray) {
        // create a stream since it expects one from the socket
        val stream = BufferedInputStream(bytes.inputStream())
        val varInt = VarInt.fromStream(stream)
        assertEquals(expected, varInt.value)
    }
}