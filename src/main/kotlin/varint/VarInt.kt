package z3roco01.mcping.varint

import java.io.BufferedInputStream
import java.io.InputStream
import kotlin.experimental.or

/**
 * Variable Integer format used in minecraft's packets https://minecraft.wiki/w/Java_Edition_protocol/Packets#VarInt_and_VarLong
 * @param value the underlying value of the int
 */
class VarInt(var value: Int) {
    // parameterless constructor
    constructor(): this(0)
    /**
     * Converts the [value] into a byte array
     * @return the byte array with the appropriate amount of bytes
     */
    fun toBytes(): ByteArray {
        var array = ByteArray(5)
        // copy value so it does not affect the actual value
        var cpy = value
        // loop up to 4 times since VarInt cannot be longer than 5 bytes
        for(i in 0..4) {
            // if there are no other bits set except the ones in the segment, write the last byte and return
            if(cpy and SEGMENT_MASK.inv() == 0) {
                // add the current byte to the array
                array[i] = (cpy and 0xFF).toByte()
                // shrink the array if needed
                array = array.copyOf(i+1)
                // break since it has just encoded the last byte
                break
            }

            // get the current 7 bytes to include
            var byte = (cpy and SEGMENT_MASK).toByte()
            // add the continue bit, since this is not the last byte
            byte = byte or CONTINUE_MASK.toByte()
            // then add it to the array
            array[i] = byte
            // and shift the value including the sign by 7 bits
            cpy = cpy ushr 7
        }

        return array
    }

    override fun toString() = value.toString()
    fun toString(radix: Int) = value.toString(radix)

    companion object {
        /**
         * A mask to get just the value of the int from one byte
         */
        const val SEGMENT_MASK  = 0b01111111
        /**
         * A mask to get the continue bit
         */
        const val CONTINUE_MASK = 0b10000000

        /**
         * Reads in bytes from an input stream
         */
        fun fromStream(input: BufferedInputStream): VarInt {
            var value = 0
            // only read up to 5 bytes
            for(i in 0..4) {
                // read in the next byte
                val curByte = input.read()
                // if the read byte is -1 then this is the end of the stream
                // ( should not happen in a packet, but does happen in tests )
                if(curByte == -1)
                    break

                // or in the segment, shifted left by the correct amount
                value = value or ((curByte.toInt() and SEGMENT_MASK) shl i*7)

                // if the continue bit is not set then it is done reading
                if(curByte and CONTINUE_MASK == 0)
                    break
            }
            return VarInt(value)
        }
    }
}