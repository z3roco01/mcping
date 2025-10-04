package z3roco01.mcping.network

import java.net.Socket

/**
 * A Socket which connects to a minecraft server for pinging
 * @param hostname the hostname or ip of the server
 * @param port the port the server is on ( usually 25565)
 */
class MCSocket(hostname: String, port: Int): Socket(hostname, port) {
    // a constructor without the port, uses the default port
    constructor(hostname: String): this(hostname, 25565)

    init {
        // send a handshake packet immediately

    }
}