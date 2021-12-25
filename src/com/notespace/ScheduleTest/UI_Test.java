package com.notespace.ScheduleTest;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import product_out.___Bundle;

public class UI_Test extends Application
{
    public static void main ( String[] args )
    {
	  Application.launch ( args );
    }

    @Override
    public void start ( Stage primaryStage ) throws Exception
    {
	  Scene scene = new Scene ( ___Bundle.__ViewLoader._getInstance ( )._load ( "Ui" , new Ui ( ) ) );
	  primaryStage.setScene ( scene );
	  primaryStage.show ();
    }
}
