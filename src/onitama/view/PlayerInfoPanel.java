package onitama.view;

import onitama.model.Player;
import onitama.utils.SubjectObserver;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * A panel containing the important information of a player.
 * It is an observer attached to the model
 */
public class PlayerInfoPanel extends JPanel implements SubjectObserver<Player> {

    /**
     * Label displaying the name of the player
     */
    private final JLabel name;
    /**
     * Label displaying the number of figures
     */
    private final JLabel figures;
    /**
     * A border used to indicate that it's the players turn
     */
    private final Border highlightBorder;

    /**
     *  Construcor of this panel
     * @param player The model that this view attaches to
     */
    public PlayerInfoPanel(Player player) {
        name = new JLabel(player.getName());
        figures = new JLabel();
        highlightBorder = BorderFactory.createLineBorder(Color.orange, 5, true);
        initialize();

        player.attachObserver(this);
    }

    /**
     * Sets the layout of the panel
     */
    private void initialize() {

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        name.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        figures.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        this.add(new JLabel("Name: "));
        this.add(name);
        this.add(new JLabel("Figures: "));
        this.add(figures);

    }

    /**
     *  Updates the information of the player
     * @param message The observed player object
     */
    @Override
    public void update(Player message) {
        this.setBorder((message.isTurn()) ? highlightBorder : null);

        figures.setText("" + message.getFigures().size());
    }
}
