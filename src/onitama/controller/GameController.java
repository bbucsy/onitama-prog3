package onitama.controller;

import onitama.model.Game;
import onitama.model.Player;
import onitama.model.board.Field;
import onitama.model.board.SimpleField;
import onitama.model.figures.Figure;
import onitama.model.moves.Move;
import onitama.model.moves.MoveCard;
import onitama.view.CardPanel;
import onitama.view.GameFrame;
import onitama.view.SquarePanel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GameController {

    private Game model;
    private GameFrame gui;


    public GameController(Game model, GameFrame gui){
        this.model = model;
        this.gui = gui;
        initObservers();
        initActions();
    }


    private void initObservers(){
        model.getPlayer(0).attachObserver(gui.getCardPanelUp());
        model.getPlayer(1).attachObserver(gui.getCardPanelDown());
        model.getExchangeCard().attachObserver(gui.getExchangeCard());

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                SimpleField field  = (SimpleField) model.getBoard().getField(i,j);
                field.attachObserver(gui.getBoardPanel().getSquare(i,j));
            }
        }
    }

    private void initActions(){
        new CardMouseListener(model.getPlayer(0),gui.getCardPanelUp().getCards(0));
        new CardMouseListener(model.getPlayer(0),gui.getCardPanelUp().getCards(1));
        new CardMouseListener(model.getPlayer(1),gui.getCardPanelDown().getCards(0));
        new CardMouseListener(model.getPlayer(1),gui.getCardPanelDown().getCards(1));


        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                new SquareMouseListener(model.getBoard().getField(i,j),gui.getBoardPanel().getSquare(i,j));
            }
        }
    }

    private class CardMouseListener extends MouseAdapter{

        private Player player;
        private CardPanel parent;

        public CardMouseListener(Player player, CardPanel parent) {
            this.player = player;
            this.parent = parent;

            parent.addMouseListener(this);
        }


        @Override
        public void mouseClicked(MouseEvent e) {
            if (!player.isTurn() || player.getSelectedFigure() != null) return;
            if(player.getSelectedCard() == null){
                MoveCard card = parent.getCard();
                player.setSelectedCard(card);
                parent.setHighlighted(true);
            }else if (player.getSelectedCard() == parent.getCard()){
                player.setSelectedCard(null);
                parent.setHighlighted(false);
            }
        }
    }


    private class SquareMouseListener extends MouseAdapter{

        Field field;
        SquarePanel parent;

        public SquareMouseListener(Field field, SquarePanel parent) {
            this.field = field;
            this.parent = parent;

            parent.addMouseListener(this);
        }

        @Override
        public void mouseClicked(MouseEvent e) {

            if(parent.getPossibleMove() != null)
                makeMove();

            Figure f = field.getFigure();
            if (field.getFigure() != null ){
                Player p = f.getPlayer();
                if(!p.isTurn()) return;

                if(p.getSelectedFigure() == f)
                    deselectFigure(f);
                else
                    selectFigure(f);

            }
        }
        
        private void makeMove() {
            Move move = parent.getPossibleMove();
            move.makeMove();
            move.getFigure().getPlayer().nexTurn();
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    SquarePanel p = gui.getBoardPanel().getSquare(i,j);
                    p.setHighlight(SquarePanel.HighlightLevel.NO);
                    p.setPossibleMove(null);
                }
            }
            gui.getCardPanelUp().getCards(0).setHighlighted(false);
            gui.getCardPanelUp().getCards(1).setHighlighted(false);
            gui.getCardPanelDown().getCards(0).setHighlighted(false);
            gui.getCardPanelDown().getCards(1).setHighlighted(false);

        }

        private void selectFigure(Figure f) {
            Player p = f.getPlayer();
            if(p.getSelectedCard() == null || p.getSelectedFigure() != null) return;

            p.setSelectedFigure(f);
            List<Move> moves = p.getAvaibleMoves();
            for(Move m : moves){
                Point pos = m.getDestination().getPosition();
                SquarePanel panel = gui.getBoardPanel().getSquare(pos.x,pos.y);
                panel.setHighlight(SquarePanel.HighlightLevel.MEDIUM);
                panel.setPossibleMove(m);
            }
            this.parent.setHighlight(SquarePanel.HighlightLevel.HIGH);

        }

        private void deselectFigure(Figure f) {
            Player p = f.getPlayer();
            List<Move> moves = p.getAvaibleMoves();
            for(Move m : moves){
                Point pos = m.getDestination().getPosition();
                SquarePanel panel = gui.getBoardPanel().getSquare(pos.x,pos.y);
                panel.setHighlight(SquarePanel.HighlightLevel.NO);
                panel.setPossibleMove(null);
            }
            this.parent.setHighlight(SquarePanel.HighlightLevel.NO);
            p.setSelectedFigure(null);
        }
    }

}
