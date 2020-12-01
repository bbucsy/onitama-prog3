package onitama.controller;

import onitama.controller.ai.PlayerController;
import onitama.model.Game;
import onitama.model.Player;
import onitama.model.board.AbstractField;
import onitama.model.figures.Figure;
import onitama.model.moves.Move;
import onitama.model.moves.MoveCard;
import onitama.view.CardPanel;
import onitama.view.GameFrame;
import onitama.view.SquarePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import static java.lang.System.exit;

/**
 * This is the Controller of a game instance
 * This registers the MouseListeners on the GUI and, redirects the actions made on the GUI to the model.
 * This is also responsible for processing each turn, waiting for user input if necessary or calling the
 * appropriate function on the AI controllers.
 */
public class GameController {

    /**
     * The model containing the actual state of the game.
     */
    private final Game model;
    /**
     * The frame of the view.
     */
    private final GameFrame gui;
    /**
     * The controllers responsible for the actions of the player.
     * <code>null</code> means it's a human player, and input should be waited through the user interface.
     */
    private final PlayerController[] AIPlayers;
    /**
     * The lock object used in the game loop.
     * When there is the human player(s) turn, it halts the game loop, until the UI signals,
     * that the next turn can be made.
     */
    private final PlayerLock lock = new PlayerLock();

    /**
     * A boolean which is used to stop the game loop by the UI;
     */
    private boolean stopGameLoop;

    /**
     * Constructor for the controller
     *
     * @param model     The model of the game
     * @param gui       The frame of the UI
     * @param AIPlayers A list consisting of AI instances and/or nulls. Null means that the player is a human.
     */
    public GameController(Game model, GameFrame gui, PlayerController[] AIPlayers) {
        this.model = model;
        this.gui = gui;
        this.AIPlayers = AIPlayers;
        stopGameLoop = false;
        initActions();
    }

    /**
     * Registers the MouseListeners and ActionListeners on the elements of the GUI
     */
    private void initActions() {

        // add listeners to the Cards
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                new CardMouseListener(model.getPlayer(i), gui.getCardHolders()[i].getCard(j));
            }
        }

        // add listeners to the Squares
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                new SquareMouseListener(model.getBoard().getField(i, j), gui.getBoardPanel().getSquare(i, j));
            }
        }

        // add listeners to menu buttons
        gui.getExitMenuItem().addActionListener(e->{
            int userChoice = JOptionPane.showConfirmDialog(gui.getContentPane(),
                    "Do you really want to exit?", "Confirm exit", JOptionPane.YES_NO_OPTION);
            if (userChoice == JOptionPane.YES_OPTION) {
                exit(0);
            }
        });

        gui.getSaveMenuItem().addActionListener(e->{
            MainController.getInstance().saveGame(model);
        });

        gui.getBackToMainMenuItem().addActionListener(e->{
            gui.dispose();
            MainController.getInstance().getMainFrame().setVisible(true);
            stopGameLoop = true;
            lock.playerInputDone();
        });
    }

    /**
     * Starts the main loop of the game. Returns when the game is finished.
     * Displays the winner in a dialog box.
     */
    public void startGame() {

        while (!stopGameLoop && model.getState() == Game.GameState.RUNNING) {
            nextTurn();
        }

        if (!stopGameLoop){
            // don't show Game over message if game is interrupted
            String gameOverMessage = "Game Over! \n" + ((model.getState() == Game.GameState.PLAYER_1_WON) ? model.getPlayer(0).getName() : model.getPlayer(1).getName()) + " won the match";
            JOptionPane.showMessageDialog(this.gui, gameOverMessage);
        }
    }

    /**
     * Handles each turn.
     * If there is no AI assigned to the player on turn(the PlayerController is null), it waits for input from the UI,
     * otherwise asks the ai to make a move.
     */
    private void nextTurn() {
        int currentPlayer = model.getPlayerOnTurn();
        if (AIPlayers[currentPlayer] == null) {
            // Human player's turn, wait for input.
            try {
                while (model.getPlayerOnTurn() == currentPlayer && !stopGameLoop)
                    lock.waitForPlayerInput();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } else {
            // Computers turn, call method to make a move
            AIPlayers[currentPlayer].ExecuteMove();
        }
    }

    /**
     * This handles the clicks on the Movement Cards on the UI.
     * A private subclass of the MainController.
     */
    private class CardMouseListener extends MouseAdapter {

        /**
         * The player whose cards are represented in the UI component.
         */
        private final Player player;
        /**
         * The UI component that this Listener is attached to.
         */
        private final CardPanel parent;

        /**
         * Constructor of the listener, it attaches itself to the Component provided in the params.
         *
         * @param player The player whose actions this listener is responsible for
         * @param parent The Component that the listener attaches itself to
         */
        public CardMouseListener(Player player, CardPanel parent) {
            this.player = player;
            this.parent = parent;
            parent.addMouseListener(this);
        }

        /**
         * A function called when a card is clicked
         *
         * @param e The event that created by the UI.
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            // return, if the click is invalid, or there is a figure already selected.
            if (!player.isTurn() || !lock.isWaitingForInput() || player.getSelectedFigure() != null) return;


            if (player.getSelectedCard() == null) {
                //Select a card for the player
                MoveCard card = parent.getCard();
                player.setSelectedCard(card);
                parent.setHighlighted(true);
            } else if (player.getSelectedCard() == parent.getCard()) {
                //This card was selected, deselect it.
                player.setSelectedCard(null);
                parent.setHighlighted(false);
            }
        }
    }

    /**
     * This handles the clicks on the squares of the board on the UI.
     * A private subclass of the MainController.
     */
    private class SquareMouseListener extends MouseAdapter {

        /**
         * The square in the model, that the UI component represents
         */
        private final AbstractField field;
        /**
         * The graphical component of a square in the model
         */
        private final SquarePanel parent;

        /**
         * Constructor of the listener. Attaches itself to the provided graphical component.
         *
         * @param field  The square in the model, that the UI component represents
         * @param parent The graphical component of a square in the model
         */
        public SquareMouseListener(AbstractField field, SquarePanel parent) {
            this.field = field;
            this.parent = parent;

            parent.addMouseListener(this);
        }

        /**
         * Called when a square is clicked on.
         * Calls the appropriate methods, depending of the state of the model.
         *
         * @param e The event created by the gui
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            //if there is no need for user input, exit function
            if (!lock.isWaitingForInput())
                return;

            if (parent.getPossibleMove() != null) {
                // The square is "highlighted", player can make a move here.
                makeMove();
            } else {
                // There is no possible movement to this square
                Figure f = field.getFigure();
                if (field.getFigure() != null) {
                    // There is a figure on this square
                    Player p = f.getPlayer();
                    //if its an enemy figure exit function
                    if (!p.isTurn()) return;

                    if (p.getSelectedFigure() == f)
                        //Clicked figure is the selected figure
                        deselectFigure(f);
                    else if (p.getSelectedFigure() != null) {
                        // There is a selected figure, but not on this square
                        deselectFigure(p.getSelectedFigure());
                        selectFigure(f);
                    } else
                        //No selected figure, but the figure on this square can be selected
                        selectFigure(f);

                }
                // else -> no possible movement here, nor any figure

            }
            //notify lock, that there was user input
            lock.playerInputDone();
        }

        /**
         * When there is a selected card, figure and destination, this function calls the
         * model to execute the move.
         */
        private void makeMove() {
            //Execute move
            Move move = parent.getPossibleMove();
            move.getPlayer().executeMove(move);

            //Reset Squares to default
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    SquarePanel p = gui.getBoardPanel().getSquare(i, j);
                    p.setHighlight(SquarePanel.HighlightLevel.NO);
                    p.setPossibleMove(null);
                }
            }

            //Reset cards to default
            for (int i = 0; i < 4; i++)
                gui.getCardHolders()[i / 2].getCard(i % 2).setHighlighted(false);

        }

        /**
         * When possible, this function selects a figure on the board, to make a move with.
         *
         * @param f The figure to be selected
         */
        private void selectFigure(Figure f) {
            //Chek if its legal to select a move at this point.
            Player p = f.getPlayer();
            if (p.getSelectedCard() == null || p.getSelectedFigure() != null) return;

            //Get all available moves, and highlight the corresponding squares
            p.setSelectedFigure(f);
            List<Move> moves = p.getAvailableMoves();
            for (Move m : moves) {
                Point pos = m.getDestination().getPosition();
                SquarePanel panel = gui.getBoardPanel().getSquare(pos.x, pos.y);
                panel.setHighlight(SquarePanel.HighlightLevel.MEDIUM);
                panel.setPossibleMove(m);
            }

            //Highlight the base of the move
            this.parent.setHighlight(SquarePanel.HighlightLevel.HIGH);

        }

        /**
         * Deselects a figure, and resets the GUI
         *
         * @param f The figure to deselect
         */
        private void deselectFigure(Figure f) {
            //reset square highlights
            Player p = f.getPlayer();
            List<Move> moves = p.getAvailableMoves();
            for (Move m : moves) {
                Point pos = m.getDestination().getPosition();
                SquarePanel panel = gui.getBoardPanel().getSquare(pos.x, pos.y);
                panel.setHighlight(SquarePanel.HighlightLevel.NO);
                panel.setPossibleMove(null);
            }

            //The base is not always the currently clicked square
            Point prevPos = f.getCurrentField().getPosition();
            gui.getBoardPanel().getSquare(prevPos.x, prevPos.y).setHighlight(SquarePanel.HighlightLevel.NO);

            //Tell the model to deselect the figure
            p.setSelectedFigure(null);
        }
    }

}
