//Author: Ryan Illies
//Due Date: 11/11/2020
//Class: CSIS[335]
//Description: This final project is making my vehicle class can be altered within a data base and therefore saved over each use.
// This project essentially does the same inventory handlng as the projects before it but it can now save data.


package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Project 2");
        primaryStage.setScene(new Scene(root, 600, 450));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
