/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formbuilderfxui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.util.Callback;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.util.Callback;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author CY Tan
 * GUIs are for the weak #nottruewishwewereusingjdk8soIcouldusescenebuilder
 * 
 */

public class FormBuilderFXUI extends Application {
    @FXML private ListView<String> listView;
    @FXML private HBox addPane;
    @FXML private AnchorPane activeItemView;
    @FXML private AnchorPane addButton;
    @FXML private HBox actionButtonGrade;
    @FXML private HBox actionButtonCancel;
    @FXML private HBox actionButtonBg;
    @FXML private HBox actionButtonFg;
    
    private FileChooser fbFileChooser;
    private Stage fbStage;
    private Boolean isLoading;
    private List<File> testFileList;
    
    @Override public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/FormBuilder.fxml"));
        fxmlLoader.setController(this);
        
        Parent root = (Parent)fxmlLoader.load();
        Scene scene = new Scene(root);
        
        fbStage = stage;
        fbStage.setScene(scene);
        //stage.setHeight(480);
        //stage.setWidth(720);
        //fbStage.setResizable(false);
        fbStage.show();
        
        setupFileChooser();
        setupTestList();
        setupAddPane();
        setupActionButton();
    }

    /**
     * @param args the command line arguments
     */
    
    
    private void setupFileChooser() {
        FileChooser fileChooser = new FileChooser();
        
        fileChooser.setTitle("Open Resource File");
        fileChooser.setInitialDirectory(
            new File(System.getProperty("user.home"))
        );
        
        fileChooser.getExtensionFilters().addAll(
            //new FileChooser.ExtensionFilter("All Files (temporary for ver. 1)", "*.*"),
            new FileChooser.ExtensionFilter("Portable Document Format (PDF) & Comma Separated Values (CSV)", "*.pdf", "*.csv")
        );
         
        fbFileChooser = fileChooser; 
    }
    
    private List<File> openFiles(FileChooser fc) {
        return fc.showOpenMultipleDialog(fbStage); // change to add
    }
    
    private void setupAddPane() {
        EventHandler addHandler = new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    List<File> newList = openFiles(fbFileChooser);
                    
                    if (newList != null) {
                        for (File file : newList) {
                            // Here's the absolute file name, and well, the files
                            addToList(file);
                            //System.out.println(file.getAbsolutePath()); //There's also getCanonicalPath if you prefer that
                        }
                    }
                }
            }
        };
                
        // This error is wrong, netbeans isn't very smart when it comes to namespace intersections
        addPane.setOnMouseClicked(addHandler);
        addButton.setOnMouseClicked(addHandler);
    }
    
    private void setupTestList() {
        testFileList = new ArrayList<>();
        
        listView.setCellFactory(new Callback<ListView<String>, javafx.scene.control.ListCell<String>>() {
            @Override public ListCell<String> call(ListView<String> listView) {
                TestItemCell cell = new TestItemCell();
                
                cell.registerRemovalCallback(new removeFromList());
                        
                return cell;
            }
        });
        
        Rectangle clip = new Rectangle(
            listView.getWidth(), listView.getHeight()
        );
        
        clip.setArcWidth(25);
        clip.setArcHeight(25);
        listView.setClip(clip);
        
        activeItemView.setVisible(false);
    }
    
     private void addToList(final File item) {
         if (item != null) {
             ObservableList currentList = listView.getItems();
            currentList.add(item.getName());
            listView.setItems(currentList);

            testFileList.add(item);
            
            if (!activeItemView.isVisible()) activeItemView.setVisible(true);
         }
    }
     
    class removeFromList implements RemovalCallbackInterface {
        @Override public void removeByName(String name) {
            ObservableList currentList = listView.getItems();
            currentList.remove(name);
            listView.setItems(currentList);
            
            for (File f : testFileList) {
                if (f.getName().equals(name)) {
                    testFileList.remove(f); // Doing it based off name will present future problems, change to canonical paths when I have time
                    break;
                }
            }
            
            if (testFileList.isEmpty()) {
                if (activeItemView.isVisible()) activeItemView.setVisible(false);
            }
        }
    }
    
    private void setupActionButton() {
        isLoading = false;
        
        actionButtonCancel.setVisible(false);
    }
    
    public List<File> getFileList() {
        return testFileList;
    }
}
