
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import java.io.InputStreamReader;
import java.io.BufferedReader;

public final class EchoServer {
    
    public static void main(String[] args) throws Exception {
        
        //setup server
        try (ServerSocket serverSocket = new ServerSocket(22222)) {
            while (true) {
                try {
                    //accept client connect to this server
                    Socket socket = serverSocket.accept();
                    
                    //runnable object of server
                    Runnable server = () -> {
                        try{
                            String address = socket.getInetAddress().getHostAddress();
                            System.out.printf("Client connected: %s%n", address);
                            //out stream message to client
                            OutputStream os = socket.getOutputStream();
                            PrintStream out = new PrintStream(os, true, "UTF-8");
                            out.printf("Hi %s, thanks for connecting!%n", address);
                            //while client is connected, read the message sent from client, and send the message back to client.
                            //If message is exit, break the while loop and display disconnected messge
                            while(true){
                                //buffer reader to read message from client
                                BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                                String message = fromClient.readLine();
                                if(message.equals("exit")){
                                    out.printf("%s%n",message);
                                    System.out.printf("Client disconnected: %s%n", address);
                                    break;
                                }
                                else out.printf("%s%n",message);
                            }
                        }
                        catch(Exception e){
                            
                        }
                        
                    };
                    //run multi thread for server
                    Thread serverThread = new Thread(server);
                    serverThread.start();
                    
                }
                catch(Exception e){
                    
                }
                
            }
        }
        catch(Exception e){
            
        }
    }
    
}
