package onitama.view;

import onitama.model.moves.MoveCard;
import onitama.utils.ImagePanel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class CardPanel extends ImagePanel {

    private JLabel nameLabel;
    private JPanel[][] squares;
    private JPanel squareLayoutPanel;
    private MoveCard card;
    private boolean highlighted = false;

    public CardPanel() {
        super("res/card.png");
        nameLabel = new JLabel("CardName");
        squares = new JPanel[5][5];
        squareLayoutPanel = new JPanel();
        initializeLayout();
        initializeSquares();
    }

    private void initializeLayout() {
        this.setPreferredSize(new Dimension(280, 200));
        this.setLayout(new GridLayout(1,2));
        this.add(nameLabel);
        nameLabel.setBorder(new EmptyBorder(0,20,0,0));
        this.add(squareLayoutPanel);
    }

    private void initializeSquares() {
        GridLayout layout = new GridLayout(5, 5);
        layout.setVgap(1);
        layout.setHgap(1);
        squareLayoutPanel.setLayout(layout);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                JPanel p = new JPanel();
                p.setBackground(Color.lightGray);
                squareLayoutPanel.add(p);
                squares[i][j] = p;
            }
        }
        squareLayoutPanel.setBorder(new EmptyBorder(50,20,50,20));
        squareLayoutPanel.setOpaque(false);
    }

    private void renderCard() {
        if (card == null) return;
        nameLabel.setText(card.getName());

        if(highlighted)
            this.setBorder(BorderFactory.createLineBorder(Color.yellow,5,true));
        else
            this.setBorder(null);


        //reset grid
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                squares[i][j].setBackground(Color.LIGHT_GRAY);
            }
        }

        //change representing grid
        ArrayList<Point> relativeMoves = card.getRelativeMoves();
        for(Point p : relativeMoves){
            squares[p.x+2][p.y+2].setBackground(Color.ORANGE);
        }
        squares[2][2].setBackground(Color.BLACK);
    }


    public MoveCard getCard() {
        return card;
    }

    public void setHighlighted(boolean b){
        this.highlighted = b;
        this.renderCard();
    }

    public void setCard(MoveCard card) {
        this.card = card;
        renderCard();
    }
}
