package main;
import java.io.*;
import java.net.*;
import java.util.StringTokenizer;
public class HTTPServer {
	public static void HTTPHeader(DataOutputStream os,int info) throws IOException{
		if(info==1) {
			os.writeBytes("HTTP/1.1 200 OK\r\n");
		}else {
			os.writeBytes("HTTP/1.1 404 Not Found\r\n");
		}
		os.writeBytes("Server: Renan\r\n");
		os.writeBytes("Content-Type: text/html\r\n");
		os.writeBytes("\r\n");
	}
	public static void sendBytes(FileInputStream fis,OutputStream os) throws Exception{
		byte[] buffer = new byte[1024];
		int bytes=0;
		while((bytes = fis.read(buffer)) != -1) {
			os.write(buffer, 0, bytes);
		}
	}
	public static void main(String argv[]) throws Exception
	{
		ServerSocket welcomeSocket = new ServerSocket(8090);
		while(true) {
			Socket connectionSocket = welcomeSocket.accept();
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			InetAddress client = connectionSocket.getInetAddress();
			System.out.println("Cliente: " + client + " conectou ao servidor!");
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			String reqLine = inFromClient.readLine();
			StringTokenizer token = new StringTokenizer(reqLine);
			FileInputStream fis=null;
			boolean exists=true;
			if(token.nextToken().equals("GET")) {
				String filename=token.nextToken();
				System.out.println(filename);
				//tratamento do arquivo
				try {
					fis = new FileInputStream("html"+filename);
				}catch(FileNotFoundException e) {
					exists=false;
				}
				if(exists) {
					HTTPHeader(outToClient,1);
					sendBytes(fis, outToClient);
					outToClient.close();
					inFromClient.close();
					connectionSocket.close();
				}else {
					System.out.println("Bad Request Message");
					HTTPHeader(outToClient, 0);
					outToClient.writeBytes("<html><head><title>Erro</title></head><body><h1>Erro 404</h1><br>Página não encontrada</body></html>");
					outToClient.close();
					inFromClient.close();
					connectionSocket.close();
				}
				
			}
		}
	}
}
