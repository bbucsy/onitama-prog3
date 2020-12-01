package onitama.view;

import onitama.model.moves.MoveCard;
import onitama.utils.ImagePanel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * This is a view panel that renders out a <code>MoveCard</code>
 */
public class CardPanel extends ImagePanel {

    /**
     * Label component containing the name of the MoveCard
     */
    private final JLabel nameLabel;
    /**
     * Matrix of JPanel components. Used as a mini board to draw out the relative moves of the MoveCard
     */
    private final JPanel[][] squares;
    /**
     * A border that is used to display if the card is selected (highlighted)
     */
    private final Border highlightBorder;
    /**
     * The card that this view needs to display
     */
    private MoveCard card;
    /**
     * Stores weather this card is selected/highlighted
     */
    private boolean highlighted = false;

    public CardPanel() {
        super("card.png");
        nameLabel = new JLabel("CardName");
        squares = new JPanel[5][5];
        highlightBorder = BorderFactory.createLineBorder(Color.yellow, 5, true);
        initializeLayout();
        initializeSquares();
    }

    /**
     * Sets the size and layout of the panel
     */
    private void initializeLayout() {
        this.setPreferredSize(new Dimension(280, 200));
        this.setLayout(new GridLayout(1, 2));
        this.add(nameLabel);
        nameLabel.setBorder(new EmptyBorder(0, 20, 0, 0));

    }

    /**
     * Creates the JPanel matrix and sets the layout of them.
     */
    private void initializeSquares() {
        JPanel squareLayoutPanel = new JPanel();;
        squareLayoutPanel.setLayout(new GridLayout(5, 5, 1, 1));
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                JPanel p = new JPanel();
                p.setBackground(Color.lightGray);
                squareLayoutPanel.add(p);
                squares[i][j] = p;
            }
        }
        squareLayoutPanel.setBorder(new EmptyBorder(50, 20, 50, 20));
        squareLayoutPanel.setOpaque(false);
        this.add(squareLayoutPanel);
    }

    /**
     *  Displays the information of the MoveCard object that is set in this.
     */
    private void renderCard() {
        if (card == null) return;
        nameLabel.setText(card.getName());

        if (highlighted)
            this.setBorder(highlightBorder);
        else
            this.setBorder(null);


        //reset grid
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                squares[i][j].setBackground(Color.LIGHT_GRAY);
            }
        }

        //highlight possible moves grid
        ArrayList<Point> relativeMoves = card.getRelativeMoves();
        for (Point p : relativeMoves) {
            squares[p.x + 2][p.y + 2].setBackground(Color.ORANGE);
        }
        squares[2][2].setBackground(Color.BLACK);
    }

    /**
     *
     * @return The card that this view displays
     */
    public MoveCard getCard() {
        return card;
    }

    /**
     *
     * @param card The card that this view should display
     */
    public void setCard(MoveCard card) {
        this.card = card;
        renderCard();
    }

    /**
     *
     * @param b The highlighted state this view should have
     */
    public void setHighlighted(boolean b) {
        this.highlighted = b;
        this.renderCard();
    }
}
