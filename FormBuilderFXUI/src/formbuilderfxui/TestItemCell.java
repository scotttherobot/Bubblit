package formbuilderfxui;

import javafx.scene.control.ListCell;

public class TestItemCell extends ListCell<String> {
    private RemovalCallbackInterface removalCallback;
    
    @Override public void updateItem(String string, boolean empty) {
        super.updateItem(string, empty);
        
        if(string != null) {
            TestItemData data = new TestItemData();
            data.setInfo(string);
            setGraphic(data.getBox());
            
            data.registerRemovalCallback(new CellItemRemovalCallback());
        }
    }
    
    public void registerRemovalCallback(RemovalCallbackInterface cb) {
        removalCallback = cb;
    }
     
    class CellItemRemovalCallback implements RemovalCallbackInterface {
        @Override public void removeByName(String name) {
            removalCallback.removeByName(name);
        }
    }
}

