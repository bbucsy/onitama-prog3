package onitama.controller.ai;

import onitama.model.Game;
import onitama.model.Player;
import onitama.model.moves.Move;

import java.io.IOException;
import java.util.*;

public class MinMaxAi extends AbstractAi {

    private final int maxDepth;
    private int playerNumber;
    private int enemyPlayerNumber;

    public MinMaxAi(int maxDepth) {
        super();
        this.maxDepth = maxDepth;
    }

    @Override
    protected Move getNextMove() {
        if (model == null || player == null) return null;

        //we need the index of the player, because we are deep cloning the model;
        playerNumber = model.getPlayerNumber(player);
        enemyPlayerNumber = (playerNumber+1)%2;

        List<Move> possibleMoves = player.getAllPossibleMoves();
        if(possibleMoves.size() == 0) return null;


        Map<Move,Double> moveScores = new HashMap<>();


        for (int i = 0; i < possibleMoves.size(); i++) {
            try {
                Game clonedModel = model.deepClone();
                Player clonedPlayer = clonedModel.getPlayer(playerNumber);
                clonedPlayer.executeMove(clonedPlayer.getAllPossibleMoves().get(i));


                double score = MinMaxEvaluate(clonedModel,1);
                // if move is instant win don't look for other moves
                if (Double.isInfinite(score) && score > 0)
                    return possibleMoves.get(i);


                moveScores.put(possibleMoves.get(i),score);
            }
            catch (Exception e){
                // shouldn't be here, but if possible get random move
                e.printStackTrace();
                Random r = new Random();
                return possibleMoves.get(r.nextInt(possibleMoves.size()));
            }
        }

        return Collections.max(moveScores.entrySet(), Comparator.comparingDouble(Map.Entry::getValue)).getKey();
    }

    private double MinMaxEvaluate(Game model, int level) throws IOException, ClassNotFoundException {
        if(level == maxDepth || model.getState() != Game.GameState.RUNNING) return evaluateLeafScore(model);

        int currentPlayer = (level%2 == 0)? playerNumber: enemyPlayerNumber;

        List<Move> possibleMoves = model.getPlayer(currentPlayer).getAllPossibleMoves();
        if (possibleMoves.size() == 0) return (level%2 == 0)? Double.NEGATIVE_INFINITY: Double.POSITIVE_INFINITY;

        List<Double> moveScores = new ArrayList<>();

        for (int i = 0; i < possibleMoves.size(); i++) {

            Game clonedModel = model.deepClone();
            Player clonedPlayer = clonedModel.getPlayer(currentPlayer);
            clonedPlayer.executeMove(clonedPlayer.getAllPossibleMoves().get(i));
            double score = MinMaxEvaluate(clonedModel,level+1);
            if (Double.isInfinite(score)){
                if((level%2==0 && score > 0) || (level%2 == 1 && score < 0))
                    return score;
            }

            moveScores.add(score);
        }

       return (level%2 == 0)? Collections.max(moveScores): Collections.min(moveScores);
    }

    private double evaluateLeafScore(Game model) {

        // Return POS/NEG infinity if game is over;

        if ((model.getState() == Game.GameState.PLAYER_1_WON && playerNumber == 0) ||
                (model.getState() == Game.GameState.PLAYER_2_WON && playerNumber == 1))
            return Double.POSITIVE_INFINITY;

        if((model.getState() == Game.GameState.PLAYER_1_WON && playerNumber == 1)||
                (model.getState() == Game.GameState.PLAYER_2_WON && playerNumber == 0))
            return Double.NEGATIVE_INFINITY;


        //evaluate based on number of figures vs enemy
        int figureDiff =  model.getPlayer(playerNumber).getFigures().size() - model.getPlayer(enemyPlayerNumber).getFigures().size();
        int posMoves = model.getPlayer(playerNumber).getAllPossibleMoves().size();

        return figureDiff*100.0 + posMoves*10.0;
    }


}
