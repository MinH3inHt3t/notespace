package com.notespace.NoteCard;

import com.notespace.FileHandler.FileHandlings;
import com.notespace.FileHandler.NoteCardSettingStorage;
import com.notespace.FileHandler.SystemConfigStorage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import product_out.___Bundle;

import java.io.IOException;


public class NoteCardUI_Test extends Application {
    private static double xOffset = 0;
    private static double yOffset = 0;

    String absolutePath;


    public NoteCardUI_Test() {
        absolutePath = "D:\\NoteSpaceHere\\Dry Pot.txt";
    }

    public NoteCardUI_Test(String absolutePath) {
        this.absolutePath = absolutePath;

    }


    public static void main(String[] args) throws IOException {
        Application.launch(args);

    }

    public Parent startUI() {
        NoteCard controller = new NoteCard(absolutePath, false);

        Parent parent = ___Bundle.__ViewLoader._getInstance()._load("NoteCard", controller);


        return parent;
        //	  dragFrame ( controller , stage );
    }

    public void startUINewWindow(Stage stage) {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();


        //	  NoteCard controller = new NoteCard ( absolutePath , false , screenBounds.getWidth ( ) / 2 , screenBounds.getHeight ( ) / 2 );
        NoteCard controller = new NoteCard(absolutePath, false, 300, 300);


        Parent parent = ___Bundle.__ViewLoader._getInstance()._load("NoteCard", controller);
        Scene scene = new Scene(parent);
        //		  Stage stage = new Stage ( );
        stage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);

        //	  stage.centerOnScreen ();
        //	  stage.initModality ( Modality.APPLICATION_MODAL ); //No Need
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                scene.getStylesheets().add("http://fonts.googleapis.com/css?family=Indie+Flower");
                scene.getStylesheets().add("http://fonts.googleapis.com/css?family=Material+Icons");

            }
        });

        stage.setScene(scene);
        stage.show();


        MenuItem[] disableNodes = {controller.cardsizeMenu, controller.cardshapeMenu, controller.cardappearanceMenuItem, controller.cardanimationMenu, controller.pinNoteMenuItem, controller.unpinNoteMenuItem, controller.deleteNoteMenuItem, controller.glasswindowmode, controller.setpasswordMenu};
        int k = 0;

        while (k < disableNodes.length) {
            disableNodes[k].setVisible(false);
            k++;
        }

        //	  controller.cardsizeMenu.setDisable ( true );
        controller.cardsizeMenu.setText("Card Size ( disable in this mode. )");
        //	  controller.pinNoteMenuItem.setDisable ( true );
        controller.pinNoteMenuItem.setText("Pin Note ( disable in this mode. )");
        //	  controller.unpinNoteMenuItem.setDisable ( true );
        controller.unpinNoteMenuItem.setText("UnPin Note ( disable in this mode. )");
        //	  controller.glasswindowmode.setDisable ( true );


        controller.minNoteCardLabel.setVisible(true);
        controller.closeNoteCardLabel.setVisible(true);


        controller.minNoteCardLabel.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                stage.setIconified(true);
            }
        });

        controller.closeNoteCardLabel.setOnMouseClicked(e -> {      //-> double click label to change textarea
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                stage.close();

            }
        });

        controller.setPasswordAllNote.setOnAction(e -> {
            FileHandlings.getTextObjectLists().stream().forEach(textobj -> {
                NoteCardSettingStorage.setPassword(textobj.getAbsolutePath(), SystemConfigStorage.ReadSettings().password);

            });
        });
        controller.setPasswordOnlyThisNote.setOnAction(e -> {
            NoteCardSettingStorage.setPassword(controller.returnTextObject().getAbsolutePath(), SystemConfigStorage.ReadSettings().password);
        });


        controller.editLabel.setOnMouseClicked(e -> {

            controller.textLabel.setVisible(false);      //change label to textarea
            controller.textArea.setVisible(true);
            //				textArea.setText ( textLabel.getText ( ) );
            controller.textArea.setText(controller.TextObject_Service.getValue().getText());

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    controller.textArea.requestFocus();
                }
            });
        });

        Node node[] = {controller.pane, controller.optionsHbox, controller.textLabel, controller.imageView, controller.webviewAnchor};
        for (
                int i = 0;
                i < node.length;
                i++
        ) {
            Node n = node[i];
            n.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    n.setCursor(Cursor.CLOSED_HAND);
                    xOffset = stage.getX() - event.getScreenX();
                    yOffset = stage.getY() - event.getScreenY();
                }
            });
            n.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    n.setCursor(Cursor.CLOSED_HAND);
                    stage.setX(event.getScreenX() + xOffset);
                    stage.setY(event.getScreenY() + yOffset);
                }
            });
            n.setOnMouseReleased(e -> {
                n.setCursor(Cursor.HAND);
            });
        }


        controller.putlabel.setOnMouseClicked(new EventHandler<MouseEvent>()      //-> double click label to change textarea
        {

            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 1) {
                        //				putlabel.setVisible ( false );
                        controller.putlabelhbox.setVisible(false);
                        controller.putlabelhbox2.setVisible(true);
                        controller.puttextfield.setVisible(true);
                        controller.puttextfield.setText(controller.putlabel.getText().trim());
                    }
                }
            }
        });


    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        startUINewWindow(new Stage());

    }
}
