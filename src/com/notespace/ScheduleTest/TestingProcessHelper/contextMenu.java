package com.notespace.ScheduleTest.TestingProcessHelper;// Program to create a context menu and add it to label
// and associate the context menu with window event listener

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class contextMenu extends Application {
    // labels
    Label label;

    public static void main(String args[]) {
        // launch the application
        launch(args);
    }

    // launch the application
    public void start(Stage stage) {
        // set title for the stage
        stage.setTitle("creating contextMenu ");

        // create a label
        Label label1 = new Label("This is a ContextMenu example ");

        // create a menu
        ContextMenu contextMenu = new ContextMenu();

        // create menuitems
        MenuItem menuItem1 = new MenuItem("menu item 1");
        MenuItem menuItem2 = new MenuItem("menu item 2");
        MenuItem menuItem3 = new MenuItem("menu item 3");

        // add menu items to menu
        contextMenu.getItems().add(menuItem1);
        contextMenu.getItems().add(menuItem2);
        contextMenu.getItems().add(menuItem3);

        // label to display events
        Label label = new Label("context menu hidden");

        // create window event
        EventHandler<WindowEvent> event = new EventHandler<WindowEvent>() {
            public void handle(WindowEvent e) {
                if (contextMenu.isShowing())
                    label.setText("context menu showing");
                else
                    label.setText("context menu hidden");
            }
        };


        // add event
        contextMenu.setOnShowing(event);
        contextMenu.setOnHiding(event);

        // create a tilepane
        TilePane tilePane = new TilePane(label1);

        tilePane.getChildren().add(label);

        // setContextMenu to label
        label.setContextMenu(contextMenu);

        // create a scene
        Scene sc = new Scene(tilePane, 200, 200);

        // set the scene
        stage.setScene(sc);

        stage.show();


    }
}