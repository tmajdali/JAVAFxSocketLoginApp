package javaFxSocketLoginApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;












import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
 
public class Client extends Application  { 
	Label myLabel ;
	String username;
	String password;
	String code =" ";
	String code02 = code;
	boolean logout = false;
	boolean logout02 = logout;
	String getCode()
	{
		return code;
	}
	
	boolean getlogout()
	{
		return logout;
	}
	 
    private BooleanProperty booleanProperty = new SimpleBooleanProperty(); 
    private StringProperty stringProperty02 = new SimpleStringProperty(" ");
    private StringProperty stringProperty03 = new SimpleStringProperty(" ");
    Server02 myObject = new Server02();
    
    
	     
		  public static void main(String[] args) { 
		 
		    launch(args);   
		  } 
		 
		  
		  public void start(Stage myStage) { 
			  
			  Label status = new Label("---");
			 
			  TextField userTF = new TextField();  
			  TextField passTF = new TextField();  
			  
			  Button loginBTN = new Button("Log in"); 
			  Button logoutBTN = new Button("Log out"); 
			  logoutBTN.setDisable(true);
			  //loginBTN.setVisible(false);
			  userTF.setPromptText("Enter Username");
			  passTF.setPromptText("Enter Password");
			  
		    myStage.setTitle("Use a JavaFX label."); 
		 
		 
		    FlowPane rootNode = new FlowPane(Orientation.VERTICAL,10, 30); 
		   // rootNode.setStyle("-fx-background-color: #000000");
		    rootNode.setAlignment(Pos.CENTER); 
		    
		    Scene myScene = new Scene(rootNode, 300, 300); 
		 
		   
		    myStage.setScene(myScene); 
		 
		   
		    
		     myLabel = new Label("No Connection"); 
		    // myLabel.setTextFill(Color.web("#00ff00"));
		    
		    //myLabel.setTranslateY(250);
		    // myLabel.setTranslateX(100);
		     loginBTN.setOnAction((ae) -> {
		    	 username = userTF.getText();
		    	 password = passTF.getText();
		    	 code= username+"-"+password;
		    	 System.out.println(code);
		    	 stringProperty03.set("disable");
		    	 
		     }
             ); 
		     
		     logoutBTN.setOnAction((ae) -> {
		    	logout = true;
		    	 
		     }
             ); 
		   
		     booleanProperty.addListener(new ChangeListener<Boolean>() {

		    	 @Override
		    	 public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		    		 System.out.println("changed " + oldValue + "->" + newValue);

		    		 Platform.runLater(new Runnable() {
		    			 @Override
		    			 public void run() {
		    				 if(newValue.booleanValue() == true){
		    					
		    					 myLabel.setText("Connected to Server");
		    				 }else{
		    				 
		    					 myLabel.setText("Lost Connecttion To Server");
		    				 }
		    				 
		    					 


		    				 
		    			 }
		    		 });
		    		 try {
		    			 Thread.sleep(1000);
		    		 } catch (InterruptedException e) {

		    			 e.printStackTrace();
		    		 }

		    	 }
		     });
		     
		     stringProperty02.addListener(new ChangeListener<String>() {

		    	 @Override
		    	 public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		    		 System.out.println("changed " + oldValue + "->" + newValue);

		    		 Platform.runLater(new Runnable() {
		    			 @Override
		    			 public void run() {
		    				 if(newValue.equals("login")){
		    					
		    					 status.setText("logged in");
		    					 logoutBTN.setDisable(false);
		    				 }else if(newValue.equals("loggedout")){
		    					 status.setText("logged out");
		    					 logoutBTN.setDisable(true);
		    					 userTF.setDisable(true);
		    					 passTF.setDisable(true);
		    					 
		    				 }else{
		    				     System.out.println("error here");
		    					 status.setText("username or/and password is incorrect");
		    				 }
		    				 
		    					 


		    				 
		    			 }
		    		 });
		    		 try {
		    			 Thread.sleep(1000);
		    		 } catch (InterruptedException e) {

		    			 e.printStackTrace();
		    		 }

		    	 }
		     });

		     stringProperty03.addListener(new ChangeListener<String>() {

		    	 @Override
		    	 public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		    		 System.out.println("changed " + oldValue + "->" + newValue);

		    		 Platform.runLater(new Runnable() {
		    			 @Override
		    			 public void run() {
		    				 if(newValue.equals("disable")){
		    					
		    					 loginBTN.setDisable(true);
		    					 userTF.setDisable(true);
		    					 passTF.setDisable(true);
		    				 }else{
		    					 loginBTN.setDisable(false);
		    					 userTF.setDisable(false);
		    					 passTF.setDisable(false);
		    				 }
		    				 
		    					 


		    				 
		    			 }
		    		 });
		    		 try {
		    			 Thread.sleep(1000);
		    		 } catch (InterruptedException e) {

		    			 e.printStackTrace();
		    		 }

		    	 }
		     });



		     rootNode.getChildren().addAll(userTF,passTF,status,loginBTN,logoutBTN,myLabel ); 


		     myStage.show(); 
		  }
		  public class Server02 implements Runnable{
				Thread th;
				
				Client cl ;
				Server02()
				{
					th = new Thread(this);
					th.start();
				}

				  

				@Override
				public void run() {
    	
					
        String hostName = "X.X.X.X";
        int portNumber = 0000;
       
        try (
            Socket echoSocket = new Socket(hostName, portNumber);
            PrintWriter out =
                new PrintWriter(echoSocket.getOutputStream(), true);
            BufferedReader in =
                new BufferedReader(
                    new InputStreamReader(echoSocket.getInputStream()));
            BufferedReader stdIn =
                new BufferedReader(
                    new InputStreamReader(System.in))
        ) {
        	if(echoSocket.isConnected()){
        		 try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		booleanProperty.set(true);
        		
        	}
            String userInput;
            while (true) {
            	code = " ";
            	code02 = " ";
            	while(code02.equals(" ")){
            		code02 = getCode();
            		try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            	}
            	//stringProperty03.set("disable");
            	System.out.println("inside while");
                out.println(code);
                //System.out.println("echo: " + in.readLine());
                if(in.readLine().equals("correct")){
                	System.out.println("correct");
                	stringProperty02.set("login");
                	break;
                }else{
                	
                	try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                	stringProperty03.set("enable");
                	System.out.println("not correct");
                	stringProperty02.set("!login");
                	
                	
                	
                }
                
            }
            while(!logout ){
            	logout02 = getlogout();
            	
            	try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        } 
        stringProperty02.set("loggedout");
        booleanProperty.set(false);
    }
}
}

