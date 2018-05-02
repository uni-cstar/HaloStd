//package halo.stdlib.kotlin.udp
//
//import halo.stdlib.kotlin.bg
//import halo.stdlib.kotlin.util.orDefault
//import java.net.*
//
///**
// * Created by Lucio on 17/7/12.
// * UDP 写数据
// */
//
//object UDPSender {
//
//    fun send(message: String, serverPort: Int, serverIp: String) {
//        var soket: DatagramSocket? = null
//        try {
//            soket = DatagramSocket()
//            val inetAddress = InetAddress.getByName(serverIp)
//            val messageByte = message.toByteArray()
//            val packet = DatagramPacket(messageByte,
//                    message.length, inetAddress, serverPort)
//            soket.send(packet)
//        } catch (e: SocketException) {
//            e.printStackTrace()
//        } catch (e: UnknownHostException) {
//            e.printStackTrace()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        } finally {
//            if (!soket?.isClosed.orDefault(true))
//                soket?.close()
//        }
//
//    }
//
//
//    fun send(message: String, serverPort: Int, inetAddress: InetAddress) {
//        var soket: DatagramSocket? = null
//        try {
//            soket = DatagramSocket()
//            val messageByte = message.toByteArray()
//            val packet = DatagramPacket(messageByte,
//                    message.length, inetAddress, serverPort)
//            soket.send(packet)
//        } catch (e: SocketException) {
//            e.printStackTrace()
//        } catch (e: UnknownHostException) {
//            e.printStackTrace()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        } finally {
//            if (!soket?.isClosed.orDefault(true))
//                soket?.close()
//        }
//
//    }
//
//    fun sendAsync(message: String, serverPort: Int, serverIp: String) {
//        bg {
//            send(message, serverPort, serverIp)
//        }
//    }
//
//    fun sendAsync(message: String, serverPort: Int, inetAddress: InetAddress) {
//        bg {
//            send(message, serverPort, inetAddress)
//        }
//    }
//
//}