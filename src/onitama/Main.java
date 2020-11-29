package onitama;


import onitama.controller.MainController;
import onitama.utils.MoveCardStorage;
import onitama.utils.ResourceManager;
import onitama.view.ErrorReport;

public class Main {

    private static final String[] resources = new String[] {"board.png","card.png"};

    public static void main(String[] args) {

        try{
            //load resources
            ResourceManager.getInstance().loadResources(resources);
            // load card pack;
            MoveCardStorage.getInstance().loadCards("cards.json");

        }catch (Exception e){
            new ErrorReport(e);
        }

        MainController mc = MainController.getInstance();
        mc.getMainFrame().setVisible(true);


    }



}
