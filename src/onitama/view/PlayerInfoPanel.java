package onitama.view;

import onitama.model.Player;
import onitama.model.figures.Figure;
import onitama.model.figures.FigureType;
import onitama.utils.SubjectObserver;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

public class PlayerInfoPanel extends JPanel implements SubjectObserver<Player> {

    private final JLabel name;
    private final JLabel figures;
    private final Border highlightBorder;

    public PlayerInfoPanel(Player player) {
        name = new JLabel(player.getName());
        figures = new JLabel();
        highlightBorder = BorderFactory.createLineBorder(Color.orange,5,true);
        initialize();


        player.attachObserver(this);
    }

    private void initialize() {

        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        name.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        figures.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        this.add(new JLabel("Name: "));
        this.add(name);
        this.add(new JLabel("Figures: "));
        this.add(figures);

    }

    @Override
    public void update(Player message) {
        this.setBorder((message.isTurn())?highlightBorder:null);

       figures.setText(""+message.getFigures().size());
    }
}
