package onitama.view;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {

    private final SquarePanel[][] squares;

    public BoardPanel() {
        super();
        squares = new SquarePanel[5][5];
        initialize();
    }

    private void initialize() {
        this.setBackground(Color.gray);
        GridLayout grid = new GridLayout(5,5);
        grid.setHgap(5);
        grid.setVgap(5);
        setLayout(grid);
        setPreferredSize(new Dimension(600,600));
        setBounds(0,0,600,600);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                SquarePanel p = new SquarePanel(new Point(i,j));
                squares[i][j] = p;
                add(p);
            }
        }
    }

    public SquarePanel getSquare(int i, int j){
        return squares[i][j];
    }

}
