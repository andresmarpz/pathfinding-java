package com.andresmarpz.pathfinding.window;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Window extends Application{

    private List<Node> pathfinding = new ArrayList<>();
    private Controller controller;

    private static Window instance;
    public static Window getInstance() {
        return instance;
    }

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception{
        instance = this;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Window.fxml"));
        Parent root = loader.load();
        stage.setTitle("Pathfinding Visualizer");

        Scene scene = new Scene(root,920, 920);

        stage.setScene(scene);
        stage.show();

        controller = loader.getController();
        controller.setup();
    }

    public Controller getController(){
        return controller;
    }
}
