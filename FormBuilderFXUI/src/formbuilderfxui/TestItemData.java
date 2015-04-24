package formbuilderfxui;

import java.io.File;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import java.io.IOException;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;


public class TestItemData {
    @FXML private HBox hBox;
    @FXML private Label fileName;
    @FXML private Button removeButton;
    
    private String name;
    private RemovalCallbackInterface removalCallback;

    public TestItemData() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/TestItemCell.fxml"));
        fxmlLoader.setController(this);
        
        try {
            fxmlLoader.load();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        initializeRemovalButton();
    }

    public void setInfo(String string) {
        name = string;
        
        fileName.setText(name);
    }

    public HBox getBox() {
        return hBox;
    }
    
    private void initializeRemovalButton() {
        removeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    removalCallback.removeByName(name);
                }
            }
        });
    }

    public void registerRemovalCallback(RemovalCallbackInterface cb) {
        removalCallback = cb;
    }
}