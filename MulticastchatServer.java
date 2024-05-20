import java.net.*;

public class MulticastchatServer {

    public static void main(String args[]) throws Exception {

        // Default port number we are going to use
        int portnumber = 4446; // Changed default port number to 4446

        if (args.length > 0) { // Modified condition to check args.length > 0 instead of args.length >= 1
            portnumber = Integer.parseInt(args[0]);
        }

        // Create a MulticastSocket
        MulticastSocket serverMulticastSocket = new MulticastSocket(portnumber);
        System.out.println("MulticastSocket is created at port " + portnumber);

        // Determine the IP address of a host, given the host name
        InetAddress group = InetAddress.getByName("225.4.5.6");

        // getByName - returns IP address of given host
        // Join the multicast group using a specified network interface
        NetworkInterface networkInterface = NetworkInterface.getNetworkInterfaces().nextElement(); // This gets the first network interface, consider specifying if necessary
        serverMulticastSocket.joinGroup(new InetSocketAddress(group, portnumber), networkInterface);
        System.out.println("joinGroup method is called...");

        boolean infinite = true;
        // Continually receives data and prints them
        while (infinite) {
            byte buf[] = new byte[1024];
            DatagramPacket data = new DatagramPacket(buf, buf.length);
            serverMulticastSocket.receive(data);
            String msg = new String(data.getData(), 0, data.getLength()).trim();
            System.out.println("Message received from client = " + msg);
        }

        // Leave the multicast group on a specified network interface
        serverMulticastSocket.leaveGroup(new InetSocketAddress(group, portnumber), networkInterface);
        System.out.println("Left multicast group.");

        serverMulticastSocket.close();
    }
}