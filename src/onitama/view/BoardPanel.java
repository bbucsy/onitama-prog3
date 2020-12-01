package onitama.view;

import javax.swing.*;
import java.awt.*;

/**
 * A panel containing the squares of the board
 */
public class BoardPanel extends JPanel {

    /**
     * Matrix of squares
     */
    private final SquarePanel[][] squares;

    /**
     * Constructor of this panel
     */
    public BoardPanel() {
        super();
        squares = new SquarePanel[5][5];
        initialize();
    }

    /**
     * This function created the squares, sets the size, layout and color of the board
     */
    private void initialize() {
        this.setBackground(Color.gray);
        setLayout(new GridLayout(5, 5, 5, 5));
        setPreferredSize(new Dimension(600, 600));
        setBounds(0, 0, 600, 600);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                SquarePanel p = new SquarePanel(new Point(i, j));
                squares[i][j] = p;
                add(p);
            }
        }
    }

    /**
     *
     * @param i Row
     * @param j Column
     * @return The square in the i-th row and j-th column
     */
    public SquarePanel getSquare(int i, int j) {
        return squares[i][j];
    }

}
