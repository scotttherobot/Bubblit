package formbuilderfxui;

interface RemovalCallbackInterface { // Java is awful for callbacks, it's unbearably sad
    public void removeByName(String name);
}

public class TestItemRemovalCallback implements RemovalCallbackInterface {
    @Override public void removeByName(String name) {
        System.out.println(name);
    }
}
