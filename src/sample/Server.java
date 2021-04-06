package sample;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import javafx.application.Application;
import javafx.application.Platform;

public class Server extends Application {
    TextArea fillinginBlank;
    @Override
    public void start(Stage server_ui){
        server_ui.setTitle("lab10-server");
        Button exit = new Button("exit");
        fillinginBlank = new TextArea();
        GridPane blank = new GridPane();
        Severprogression text = new Severprogression();
        exit.setOnAction(actionEvent -> {
            Platform.exit();
        });
        blank.setHgap(50);
        blank.setVgap(50);
        blank.add(fillinginBlank, 3, 0, 6, 6);
        blank.add(exit, 10, 1, 10, 10);
        server_ui.setScene(new Scene(blank, 600, 300));
        server_ui.show();
        text.progress();
    }
    class ClientConnection extends Thread{
        protected Socket clientSocket;
        protected PrintWriter out;
        protected BufferedReader in;
        public void run() {
            String input="";
            try {
                input = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String a = input.split(" ",2)[0];
            String b = input.split(" ",2)[1];
            fillinginBlank.appendText(a+ ": " + b +"\n");
        }
        public ClientConnection(Socket target_socket) throws IOException {
            super();
            clientSocket = target_socket;
            in = new BufferedReader(new InputStreamReader(target_socket.getInputStream()));
            out = new PrintWriter(target_socket.getOutputStream(), true);
        }
    }
    public class Severprogression extends Thread{
        protected int port = 4567;
        protected int Max_Thread = 50;
        public void progress(){
            try {
                ServerSocket serverSocket;
                serverSocket = new ServerSocket(port);
                Server.ClientConnection[] threads = new Server.ClientConnection[Max_Thread];
                new Thread(()->{
                    try {
                        int count = 1;
                        while (true){
                            var clientSocket = serverSocket.accept();
                            threads[count]= new Server.ClientConnection(clientSocket);
                            threads[count].start();
                            count++;
                        }
                    }catch(Exception e){
                        System.err.println(e);
                    }
                }).start();
            }catch (Exception e){
                System.err.println(e);
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
