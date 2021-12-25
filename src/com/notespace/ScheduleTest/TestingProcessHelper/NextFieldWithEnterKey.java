package com.notespace.ScheduleTest.TestingProcessHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
 
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
 
public class NextFieldWithEnterKey extends Application {
    private TextField fieldOne = new TextField();
    private TextField fieldTwo = new TextField();
    private TextField fieldThree = new TextField();
     
    private Map<Object, TextField> nextField = new HashMap<>();
    private Map<Object, Consumer<Void>> fieldToProcess = new HashMap<>();
     
    @Override
    public void start(Stage stage) throws Exception {
        fieldToProcess.put(fieldOne, fieldOneProcess);
        fieldToProcess.put(fieldTwo, fieldTwoProcess);
        fieldToProcess.put(fieldThree, fieldThreeProcess);
         
        nextField.put(fieldOne, fieldTwo);
        nextField.put(fieldTwo, fieldThree);
        nextField.put(fieldThree, fieldOne);
         
        fieldOne.setOnKeyPressed(e -> processKeyPress(e));
        fieldTwo.setOnKeyPressed(e -> processKeyPress(e));
        fieldThree.setOnKeyPressed(e -> processKeyPress(e));
         
        GridPane grid = new GridPane();
        grid.addRow(0, new Label("First Field:"), fieldOne);
        grid.addRow(1, new Label("Second Field:"), fieldTwo);
        grid.addRow(2, new Label("Third Field:"), fieldThree);
         
        Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.setTitle("Test Advance With Enter Key");
        stage.show();
    }
     
    private void processKeyPress(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            Object source = e.getSource();
            fieldToProcess.get(source).accept(null);
            nextField.get(source).requestFocus();;
        }
    }
     
    private Consumer<Void> fieldOneProcess = v -> {
        // process field one
        System.out.println("Processing field one");
    };
     
    private Consumer<Void> fieldTwoProcess = v -> {
        // process field two
        System.out.println("Processing field two");
    };
     
    private Consumer<Void> fieldThreeProcess = v -> {
        // process field three
        System.out.println("Processing field three");
    };
     
    public static void main(String[] args) {
        launch();
    }
}