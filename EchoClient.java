
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import java.io.OutputStream;
import java.io.PrintStream;

public final class EchoClient {
    
    public static void main(String[] args) throws Exception {
        //Connect to Server
        try (Socket socket = new Socket("localhost", 22222)) {
            //Set up input stream to get message from server
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            System.out.println(br.readLine());
            
            
            //while its connected to server
            while(true){
                System.out.print("Client > ");
                //Buffer reader that reads user's input
                BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
                //set up output stream to send message to server
                PrintStream toServer = new PrintStream(socket.getOutputStream(), true, "UTF-8");
                String str = userInput.readLine();
                //send the user message to server
                toServer.printf("%s%n",str);
                //read the message send from server
                String fromServer = br.readLine();
                //if message is "exit", close socket and end while loop
                if(fromServer.equals("exit")){
                    socket.close();
                    break;
                }
                System.out.println("Server > "+fromServer);
            }
        }
    }
}















