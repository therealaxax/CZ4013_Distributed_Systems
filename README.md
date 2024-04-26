# CZ4013_Distributed_Systems

## Introduction
<p> CZ4013_Distributed_Systems contains our Java code implementing the server and client programs for our distributed flight information system. The design of our request and reply message structures for use in our marshalling and unmarshalling functions, as well as our simulation of at-least-once and at-most-once invocation semantics on idempotent and non-idempotent operations, is explained in our project report. </p>

## Running The Code
1. git clone the repo

2. cd to the com\client folder

3. In com\client\Main.java, change the server IP address in the line
   ```InetSocketAddress serverAddress = new InetSocketAddress("192.168.0.101", 50001);
   ```
   to reflect the IP address of the machine the server will run on. This project was built with the client and server running on the same local network.

4. Run com\server\Main.java and com\client\Main.java to run the server and client respectively.

5. Test the communication between the client and server. In the client program, enter '1' to select option 1, then enter 'Singapore' as the Source and 'Malaysia' as the Destination. Check that the Flight Identifier information was successfully received by the client program.
