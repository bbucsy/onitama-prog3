package onitama;


import onitama.controller.MainController;
import onitama.utils.ImageResourceManager;
import onitama.utils.MoveCardStorage;

import javax.swing.*;

/**
 * Main class for the application.
 */
public class Main {

    private static final String[] resources = new String[]{"board.png", "card.png"};

    /**
     * Entry point of the application.
     * <p>
     * This function loads the Image resources needed, and the card-data that the program needs,
     * then calls the main controller to show the main window.
     *
     * @param args
     */
    public static void main(String[] args) {

        try {
            // load card pack;
            MoveCardStorage.getInstance().loadCards("basic.json");
            MoveCardStorage.getInstance().loadCards("dlc.json");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Couldn't start program","ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //load resources
        ImageResourceManager.getInstance().loadResources(resources);

        MainController mc = MainController.getInstance();
        mc.getMainFrame().setVisible(true);


    }


}
