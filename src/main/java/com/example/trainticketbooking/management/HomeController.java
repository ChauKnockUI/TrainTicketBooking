package com.example.trainticketbooking.management;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class HomeController {
	
    @FXML
    private BorderPane bp;

    @FXML
    private AnchorPane ap;

    @FXML
    void home(MouseEvent event) {
    	bp.setCenter(ap);
    }

    @FXML
    void page1(MouseEvent event) {
    	loadPage("page1");
    }

    @FXML
    void page2(MouseEvent event) {
    	loadPage("page2");
    }

    @FXML
    void page3(MouseEvent event) {
    	loadPage("page3");
    }
    
    @FXML
    void page4(MouseEvent event) {
    	loadPage("page4");
    }
    
    private void loadPage(String page) {
    	Parent root = null;
    	
    	try {
            root = FXMLLoader.load(getClass().getResource("/com/example/trainticketbooking/management/pages/" + page + ".fxml"));
		} catch (IOException e) {
			Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, e);
		}
    	
    	bp.setCenter(root);
    }

}
