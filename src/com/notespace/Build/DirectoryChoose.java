package com.notespace.Build;

import com.notespace.FileHandler.BuildNoteSpace;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import product_out.___Bundle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DirectoryChoose implements Initializable {

    @FXML
    HBox partitionHBox;
    @FXML
    TextField txt1, txt11, txt2;

    ArrayList<String> arrayList = new ArrayList<>();

    ScheduledService<Void> syncPartitionLists = new ScheduledService<Void>() {
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    arrayList.clear();
                    arrayList = BuildNoteSpace.getPartitionsList();
                    return null;
                }
            };
        }
    };

    private void Sync_Actions() {
        this.syncPartitionLists.setRestartOnFailure(true);
        this.syncPartitionLists.setPeriod(Duration.seconds(1));
        this.syncPartitionLists.setOnSucceeded(e -> {

            this.AddPartitionsToHBox();

        });
    }

    private void UI_Actions() {
        this.txt1.textProperty().bindBidirectional(this.txt2.textProperty());
        this.txt1.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent k) {
                if (k.getCode().equals(KeyCode.ENTER)) {
                    CreateDirectory();
                }
            }
        });

        BuildNoteSpace.getPartitionsList();
        this.AddPartitionsToHBox();


    }

    private void AddPartitionsToHBox() {

        this.partitionHBox.getChildren().clear();  //clear UI first
        for (
                String s : this.arrayList
        ) {

            PartitionCard controller = new PartitionCard(s);
            Parent parent = ___Bundle.__ViewLoader._getInstance()._load("PartitionCard", controller);
            this.partitionHBox.getChildren().add(parent);

            controller.partitionAnchor.setOnMouseClicked(e -> {
                this.txt11.setText(controller.throwPartitionString());

            });
        }


    }


    private void CreateDirectory() {
        String st1 = this.txt11.getText().trim();
        String st2 = this.txt2.getText().trim();

        String path = st1 + st2;  // D:\
        BuildNoteSpace.BuildSpace(path);

        File file = new File(path + "\\New Text Document.txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void init()      //init
    {
        this.Sync_Actions();
        this.UI_Actions();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.init();
        this.syncPartitionLists.restart();


    }
}


//Flow
//GetPartitions
//AddPartitionsToHBox
