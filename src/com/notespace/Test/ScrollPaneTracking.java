package com.notespace.Test;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ScrollPaneTracking extends Application {

    @Override
    public void start(Stage primaryStage) {
        Pane pane = new Pane();
        populatePane(pane);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(pane);

        ObjectBinding<Bounds> visibleBounds = Bindings.createObjectBinding(() -> {
            Bounds viewportBounds = scrollPane.getViewportBounds();
            Bounds viewportBoundsInScene = scrollPane.localToScene(viewportBounds);
            Bounds viewportBoundsInPane = pane.sceneToLocal(viewportBoundsInScene);
            return viewportBoundsInPane ;
        }, scrollPane.hvalueProperty(), scrollPane.vvalueProperty(), scrollPane.viewportBoundsProperty());


        FilteredList<Node> visibleNodes = new FilteredList<>(pane.getChildren());
        visibleNodes.predicateProperty().bind(Bindings.createObjectBinding(() -> 
            node -> node.getBoundsInParent().intersects(visibleBounds.get()),
            visibleBounds));


        visibleNodes.addListener((ListChangeListener.Change<? extends Node> c) -> {
                visibleNodes.forEach(System.out::println);
                System.out.println();
        });

        Scene scene = new Scene(scrollPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void populatePane(Pane pane) {

        Rectangle rect = new Rectangle(50, 50, 250, 100);
        rect.setFill(Color.CORNFLOWERBLUE);

        Circle circle = new Circle(450, 450, 75);
        circle.setFill(Color.SALMON);

        Polygon triangle = new Polygon(600, 600, 750, 750, 450, 750);
        triangle.setFill(Color.CHARTREUSE);

        Ellipse ellipse = new Ellipse(200, 600, 50, 150);
        ellipse.setFill(Color.DARKORCHID);

        pane.getChildren().addAll(rect, circle, triangle, ellipse);
    }

    public static void main(String[] args) {
        launch(args);
    }
}