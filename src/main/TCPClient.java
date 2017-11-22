package main;
import java.io.*;
import java.net.*;
public class TCPClient {
public static void main(String argv[]) throws Exception
{
String request;
String modifiedSentence;
BufferedReader inFromUser =
new BufferedReader(new InputStreamReader(System.in));
Socket clientSocket = new Socket("192.168.56.1", 80);
DataOutputStream outToServer =
new DataOutputStream(clientSocket.getOutputStream());
BufferedReader inFromServer =
new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
request = "GET / HTTP/1.1\r\nHost: 192.168.56.1\r\nConnection: Close";
outToServer.writeBytes(request);
modifiedSentence = inFromServer.toString();
System.out.println("FROM SERVER: " + modifiedSentence);
clientSocket.close();
}
}
