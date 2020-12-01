package onitama.controller.ai;

import onitama.model.Game;
import onitama.model.Player;
import onitama.model.moves.Move;

import java.io.IOException;
import java.util.*;

/**
 * This Player controller uses a recursive MinMax AI to a certain depth to select the best possible move
 */
public class MinMaxAi extends PlayerController {

    /**
     * The maximum depth the recursion goes
     */
    private final int maxDepth;

    /**
     * The index of the player this is controlling.
     * This is needed, because the algorithm uses deep cloning of the model,
     * and the reference of the player couldn't be used anymore.
     */
    private int playerNumber;
    /**
     * The index of the enemy player.
     * This is needed for the same reason the as <code>playerNumber</code>
     */
    private int enemyPlayerNumber;

    /**
     *
     * @param maxDepth The maximum depth of the recursion
     */
    public MinMaxAi(int maxDepth) {
        super();
        this.maxDepth = maxDepth;
    }

    /**
     * Calculates a value for each possible move and chooses the one with the biggest score
     * @return The chosen move
     */
    @Override
    protected Move getNextMove() {
        if (model == null || player == null) return null;

        //we need the index of the player, because we are deep cloning the model;
        playerNumber = model.getPlayerNumber(player);
        enemyPlayerNumber = (playerNumber + 1) % 2;

        List<Move> possibleMoves = player.getAllPossibleMoves();
        if (possibleMoves.size() == 0) return null;


        Map<Move, Double> moveScores = new HashMap<>();


        for (int i = 0; i < possibleMoves.size(); i++) {
            try {
                Game clonedModel = model.deepClone();
                Player clonedPlayer = clonedModel.getPlayer(playerNumber);
                clonedPlayer.executeMove(clonedPlayer.getAllPossibleMoves().get(i));


                double score = MinMaxEvaluate(clonedModel, 1);
                // if move is instant win don't look for other moves
                if (Double.isInfinite(score) && score > 0)
                    return possibleMoves.get(i);


                moveScores.put(possibleMoves.get(i), score);
            } catch (Exception e) {
                // shouldn't be here, but if possible get random move
                e.printStackTrace();
                Random r = new Random();
                return possibleMoves.get(r.nextInt(possibleMoves.size()));
            }
        }

        return Collections.max(moveScores.entrySet(), Comparator.comparingDouble(Map.Entry::getValue)).getKey();
    }

    /**
     * A recursive function to evaluate a possible state of the game
     * @param model The model to be evaluated
     * @param level The level of the recursion, when called by other function should be 0
     * @return  The score of the game model in it's state
     * @throws IOException  If the cloning of the model fails
     * @throws ClassNotFoundException If the cloning of the model fails
     */
    private double MinMaxEvaluate(Game model, int level) throws IOException, ClassNotFoundException {
        if (level == maxDepth || model.getState() != Game.GameState.RUNNING) return evaluateLeafScore(model);

        int currentPlayer = (level % 2 == 0) ? playerNumber : enemyPlayerNumber;

        List<Move> possibleMoves = model.getPlayer(currentPlayer).getAllPossibleMoves();
        if (possibleMoves.size() == 0) return (level % 2 == 0) ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;

        List<Double> moveScores = new ArrayList<>();

        for (int i = 0; i < possibleMoves.size(); i++) {

            Game clonedModel = model.deepClone();
            Player clonedPlayer = clonedModel.getPlayer(currentPlayer);
            clonedPlayer.executeMove(clonedPlayer.getAllPossibleMoves().get(i));
            double score = MinMaxEvaluate(clonedModel, level + 1);
            if (Double.isInfinite(score)) {
                if ((level % 2 == 0 && score > 0) || (level % 2 == 1 && score < 0))
                    return score;
            }

            moveScores.add(score);
        }

        return (level % 2 == 0) ? Collections.max(moveScores) : Collections.min(moveScores);
    }

    /**
     * Evaluates a model in the last level of the recursive tree, or if the game ended
     * with a possible move.
     *
     * The give score is based on the state of the game, the possible moves end the difference in figures
     * @param model The game model to be evaluated
     * @return A score based on the model
     */
    private double evaluateLeafScore(Game model) {

        // Return POS/NEG infinity if game is over;

        if ((model.getState() == Game.GameState.PLAYER_1_WON && playerNumber == 0) ||
                (model.getState() == Game.GameState.PLAYER_2_WON && playerNumber == 1))
            return Double.POSITIVE_INFINITY;

        if ((model.getState() == Game.GameState.PLAYER_1_WON && playerNumber == 1) ||
                (model.getState() == Game.GameState.PLAYER_2_WON && playerNumber == 0))
            return Double.NEGATIVE_INFINITY;


        //evaluate based on number of figures vs enemy
        int figureDiff = model.getPlayer(playerNumber).getFigures().size() - model.getPlayer(enemyPlayerNumber).getFigures().size();
        int posMoves = model.getPlayer(playerNumber).getAllPossibleMoves().size();

        return figureDiff * 100.0 + posMoves * 10.0;
    }

    /**
     * Because it is possible to run more than one game at a time
     * there needs to be a way to make a new empty controller from an object
     * @return  A new controller instance
     */
    @Override
    public PlayerController clone() {
        return new MinMaxAi(maxDepth);
    }
}
