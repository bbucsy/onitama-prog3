package onitama.view;

import onitama.controller.ai.MinMaxAi;
import onitama.controller.ai.PlayerController;
import onitama.controller.ai.RandomAi;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Vector;

public class PlayerMenuPanel extends JPanel {

    private final String caption;
    private final JTextField nameField;
    private final JComboBox<Object> playerType;

    public PlayerMenuPanel(String caption) {
        super();
        this.caption = caption;
        nameField = new JTextField(caption,25);
        playerType = new JComboBox<>(getComboBoxItems());
        initialize();
    }

    private void initialize(){
        this.setLayout(new GridLayout(3,1));
        this.add(new JLabel(caption,SwingConstants.CENTER));

        JPanel np = new JPanel();
        np.add(new JLabel("Name: "));
        np.add(nameField);
        this.add(np);

        this.add(playerType);


    }

    public String getName(){
        return nameField.getText();
    }

    public PlayerController getPlayerType(){
        ComboBoxItem item = (ComboBoxItem)playerType.getSelectedItem();
        return item.ai;
    }


    private Object[] getComboBoxItems(){
        Vector<ComboBoxItem> elements = new Vector<>();
        elements.addElement(new ComboBoxItem(null, "Human player"));
        elements.addElement(new ComboBoxItem(new RandomAi(), "Random Ai"));
        elements.addElement(new ComboBoxItem(new MinMaxAi(3), "HAL 3000"));
        elements.addElement(new ComboBoxItem(new MinMaxAi(4), "HAL 4000"));


        return elements.toArray();
    }

    private static class ComboBoxItem{
        private final PlayerController ai;
        private final String name;

        public ComboBoxItem(PlayerController ai, String name) {
            this.ai = ai;
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

}
