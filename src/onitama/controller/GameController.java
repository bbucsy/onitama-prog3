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

public class GameController {

    private final Game model;
    private final GameFrame gui;
    private final PlayerController[] AIPlayers;

    private final PlayerLock lock = new PlayerLock();

    public GameController(Game model, GameFrame gui, PlayerController[] AIPlayers) {
        this.model = model;
        this.gui = gui;
        this.AIPlayers = AIPlayers;
        initActions();
    }


    private void initActions() {

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                new CardMouseListener(model.getPlayer(i), gui.getCardHolders()[i].getCard(j));
            }
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                new SquareMouseListener(model.getBoard().getField(i, j), gui.getBoardPanel().getSquare(i, j));
            }
        }

    }


    public void startGame() {
        while (model.getState() == Game.GameState.RUNNING) {
            nextTurn();
        }
        String gameOverMessage = "Game Over! \n" + ((model.getState() == Game.GameState.PLAYER_1_WON) ? model.getPlayer(0).getName() : model.getPlayer(1).getName()) + " won the match";
        JOptionPane.showMessageDialog(this.gui, gameOverMessage);
    }

    private void nextTurn() {
        int currentPlayer = model.getPlayerOnTurn();
        if (AIPlayers[currentPlayer] == null) {
            // Human player's turn, wait for input.
            try {
                while (model.getPlayerOnTurn() == currentPlayer)
                    lock.waitForPlayerInput();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } else {
            AIPlayers[currentPlayer].ExecuteMove();
        }
    }

    private class CardMouseListener extends MouseAdapter {

        private final Player player;
        private final CardPanel parent;

        public CardMouseListener(Player player, CardPanel parent) {
            this.player = player;
            this.parent = parent;

            parent.addMouseListener(this);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (!player.isTurn() || !lock.isWaitingForInput() || player.getSelectedFigure() != null) return;

            if (player.getSelectedCard() == null) {
                MoveCard card = parent.getCard();
                player.setSelectedCard(card);
                parent.setHighlighted(true);
            } else if (player.getSelectedCard() == parent.getCard()) {
                player.setSelectedCard(null);
                parent.setHighlighted(false);
            }
        }
    }


    private class SquareMouseListener extends MouseAdapter {

        final AbstractField field;
        final SquarePanel parent;

        public SquareMouseListener(AbstractField field, SquarePanel parent) {
            this.field = field;
            this.parent = parent;

            parent.addMouseListener(this);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (!lock.isWaitingForInput())
                return;

            if (parent.getPossibleMove() != null) {
                makeMove();
            } else {
                Figure f = field.getFigure();
                if (field.getFigure() != null) {
                    Player p = f.getPlayer();
                    if (!p.isTurn()) return;

                    if (p.getSelectedFigure() == f)
                        deselectFigure(f);
                    else if (p.getSelectedFigure() != null) {
                        deselectFigure(p.getSelectedFigure());
                        selectFigure(f);
                    } else
                        selectFigure(f);

                }
            }
            lock.playerInputDone();
        }

        private void makeMove() {
            Move move = parent.getPossibleMove();
            move.getPlayer().executeMove(move);
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    SquarePanel p = gui.getBoardPanel().getSquare(i, j);
                    p.setHighlight(SquarePanel.HighlightLevel.NO);
                    p.setPossibleMove(null);
                }
            }

            for (int i = 0; i < 4; i++)
                gui.getCardHolders()[i / 2].getCard(i % 2).setHighlighted(false);

        }

        private void selectFigure(Figure f) {
            Player p = f.getPlayer();
            if (p.getSelectedCard() == null || p.getSelectedFigure() != null) return;

            p.setSelectedFigure(f);
            List<Move> moves = p.getAvailableMoves();
            for (Move m : moves) {
                Point pos = m.getDestination().getPosition();
                SquarePanel panel = gui.getBoardPanel().getSquare(pos.x, pos.y);
                panel.setHighlight(SquarePanel.HighlightLevel.MEDIUM);
                panel.setPossibleMove(m);
            }
            this.parent.setHighlight(SquarePanel.HighlightLevel.HIGH);

        }

        private void deselectFigure(Figure f) {
            Player p = f.getPlayer();
            List<Move> moves = p.getAvailableMoves();
            for (Move m : moves) {
                Point pos = m.getDestination().getPosition();
                SquarePanel panel = gui.getBoardPanel().getSquare(pos.x, pos.y);
                panel.setHighlight(SquarePanel.HighlightLevel.NO);
                panel.setPossibleMove(null);
            }

            Point prevPos = f.getCurrentField().getPosition();
            gui.getBoardPanel().getSquare(prevPos.x, prevPos.y).setHighlight(SquarePanel.HighlightLevel.NO);
            p.setSelectedFigure(null);
        }
    }

}
