package main;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.io.*;
import java.net.*;
public class TCPServer {
public static void main(String argv[]) throws Exception
{
ServerSocket welcomeSocket = new ServerSocket(80);
while(true) {
Socket connectionSocket = welcomeSocket.accept();
BufferedReader inFromClient =
new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
InetAddress client = connectionSocket.getInetAddress();
System.out.println("Cliente: " + client.getHostAddress() + " conectou ao servidor!");
DataOutputStream outToClient =
new DataOutputStream(connectionSocket.getOutputStream());
System.out.println(inFromClient.readLine());
Scanner fromClient = new Scanner(connectionSocket.getInputStream());
while(fromClient.hasNextLine()){
	System.out.println(fromClient.nextLine());
}
String out="HTTP/1.1 200 OK\r\nContent-Type: text/html; charset=UTF-8\r\nContent-Type: text/html\r\n<!DOCTYPE html><html><head></head><body>ola, "+inFromClient.toString()+"</body></html>";
//capitalizedSentence = pw
outToClient.write(out.getBytes());
}
}
}
