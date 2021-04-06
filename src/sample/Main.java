package sample;

import javafx.application.Application;
import javafx.application.Platform;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage pane){
        pane.setTitle("lab10-Client");
        Label bt1 = new Label("Username: ");
        Label bt2 = new Label("Message: ");
        TextField user = new TextField();
        TextField text = new TextField();
        Button send = new Button("send");
        Button exit = new Button("exit");
        send.setOnAction(actionEvent -> {
            PrintWriter out=null;
            Socket key = null;
            try{
                key = new Socket("0.0.0.0", 4567);
                out = new PrintWriter( key.getOutputStream(),true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String a = user.getText();
            String b = text.getText();
            out.println(a+" "+b);
        });
        exit.setOnAction(actionEvent -> {
            Platform.exit();
        });
        GridPane blank = new GridPane();
        blank.add(bt1, 0, 0, 1, 1);
        blank.add(user, 1, 0, 1, 1);
        blank.add(bt2, 0, 1, 1, 1);
        blank.add(text, 1, 1, 1, 1);
        blank.add(send, 6, 2, 1, 1);
        blank.add(exit, 6, 3, 1, 1);
        blank.setHgap(30);
        blank.setVgap(30);
        pane.setScene(new Scene(blank, 800, 800));
        pane.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}