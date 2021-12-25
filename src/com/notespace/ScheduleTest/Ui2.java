package com.notespace.ScheduleTest;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class Ui2 implements Initializable
{
    @FXML
    Button stopBtn;

    ScheduledService scheduledService = new ScheduledService ( ) {
	  @Override
	  protected Task createTask ( )
	  {
		return new Task ( ) {
		    @Override
		    protected Object call ( ) throws Exception
		    {
			  return null;
		    }
		};
	  }
    };

    private void Ui_Actions(){
        stopBtn.setOnAction ( e->{
//            this.scheduledService.cancel ();

	  } );

    }
    private void Sync_Actions(){
        scheduledService.setRestartOnFailure ( true );
        scheduledService.setPeriod ( Duration.seconds ( 1 ) );
	  scheduledService.setOnRunning ( e->{
	             System.out.println ( scheduledService.getState () );
	 	  } );
	  scheduledService.setOnCancelled ( e->{
	             System.out.println ( scheduledService.getState () );
	 	  } );
        scheduledService.setOnSucceeded ( e->{
            System.out.println ( scheduledService.getState () );
	  } );
    }

    private void init(){
        Ui_Actions();
        Sync_Actions();
    }

    @Override
    public void initialize ( URL location , ResourceBundle resources )
    {
        this.init();
//        this.scheduledService.restart ();
    }

}
