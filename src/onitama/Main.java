package onitama;


import onitama.controller.MainController;
import onitama.utils.ImageResourceManager;
import onitama.utils.MoveCardStorage;
import onitama.view.ErrorReport;

public class Main {

    private static final String[] resources = new String[]{"board.png", "card.png"};

    public static void main(String[] args) {

        try {
            //load resources
            ImageResourceManager.getInstance().loadResources(resources);
            // load card pack;
            MoveCardStorage.getInstance().loadCards("basic.json");
            MoveCardStorage.getInstance().loadCards("dlc.json");

        } catch (Exception e) {
            new ErrorReport(e);
        }

        MainController mc = MainController.getInstance();
        mc.getMainFrame().setVisible(true);


    }


}
