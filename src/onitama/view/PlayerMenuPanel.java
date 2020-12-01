package onitama.view;

import onitama.controller.ai.MinMaxAi;
import onitama.controller.ai.PlayerController;
import onitama.controller.ai.RandomAi;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * An options panel used in the main menu, to set information about each player
 */
public class PlayerMenuPanel extends JPanel {

    /**
     * A label displaying a caption text set by the constructor
     */
    private final String caption;
    /**
     * Text field to set the players name
     */
    private final JTextField nameField;
    /**
     * A ComboBox to select what kind of player is playing (Human/AI)
     */
    private final JComboBox<Object> playerType;

    /**
     * Constructor of an options panel
     * @param caption The text to display on top of the panel
     */
    public PlayerMenuPanel(String caption) {
        super();
        this.caption = caption;
        nameField = new JTextField(caption, 25);
        playerType = new JComboBox<>(getComboBoxItems());
        initialize();
    }

    /**
     * Sets up the layout and components of the panel
     */
    private void initialize() {
        this.setLayout(new GridLayout(3, 1));
        this.add(new JLabel(caption, SwingConstants.CENTER));

        JPanel np = new JPanel();
        np.add(new JLabel("Name: "));
        np.add(nameField);
        this.add(np);

        this.add(playerType);


    }

    /**
     *
     * @return The name of the player
     */
    public String getName() {
        return nameField.getText();
    }

    /**
     * Return what kind of player is playing.
     * @return An instance of a PlayerController or null if player is human.
     */
    public PlayerController getPlayerType() {
        ComboBoxItem item = (ComboBoxItem) playerType.getSelectedItem();
        return (item.ai != null) ? item.ai.clone() : null;
    }

    /**
     * Generates the items for the combo box
     * @return Array of combo box item
     */
    private Object[] getComboBoxItems() {
        Vector<ComboBoxItem> elements = new Vector<>();
        elements.addElement(new ComboBoxItem(null, "Human player"));
        elements.addElement(new ComboBoxItem(new RandomAi(), "Random Ai"));
        elements.addElement(new ComboBoxItem(new MinMaxAi(3), "HAL 3000"));
        elements.addElement(new ComboBoxItem(new MinMaxAi(4), "HAL 4000"));


        return elements.toArray();
    }

    /**
     * This is a private class, to store the PlayerController instances in an array
     * but display a custom text in the combo box
     */
    private static class ComboBoxItem {
        /**
         * The stored instance
         */
        private final PlayerController ai;
        /**
         * The displayed name
         */
        private final String name;

        /**
         * Constructor of an item
         * @param ai The PlayerController to store
         * @param name The name to display
         */
        public ComboBoxItem(PlayerController ai, String name) {
            this.ai = ai;
            this.name = name;
        }

        /**
         *
         * @return The name tos display in combo box
         */
        @Override
        public String toString() {
            return name;
        }
    }

}
