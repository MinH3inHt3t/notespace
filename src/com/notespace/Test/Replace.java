package com.notespace.Test;

import com.notespace.Minimalist.Memory.EditHistoryObj;

import java.util.ArrayList;

import com.notespace.Minimalist.MinimalistPage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import product_out.___Bundle;

public class Replace extends Application{

    public static void main(String[] args)
    {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/notespace/Minimalist/MinimalistPage.fxml"));
        loader.setController(new MinimalistPage());
        Parent parent = loader.load();


        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.show();

    }
}
