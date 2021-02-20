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

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Window.fxml"));
        Parent root = loader.load();
        stage.setTitle("Pathfinding Visualizer");

        Scene scene = new Scene(root,920, 920);

        stage.setScene(scene);
        stage.show();

        Controller controller = loader.getController();
        controller.setup();
    }
}
