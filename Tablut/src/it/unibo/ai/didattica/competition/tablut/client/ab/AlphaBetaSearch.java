package it.unibo.ai.didattica.competition.tablut.client.ab;

import aima.core.search.adversarial.AdversarialSearch;
import aima.core.search.framework.Metrics;

public class AlphaBetaSearch<S, A, P> implements AdversarialSearch<S, A> {

    public final static String METRICS_NODES_EXPANDED = "nodesExpanded";
    Game<S, A, P> game;
    private Metrics metrics = new Metrics();
    private int depth;

    /**
     * Creates a new search object for a given game.
     */
    /*public static <STATE, ACTION, PLAYER> AlphaBetaSearch<STATE, ACTION, PLAYER> createFor(
            Game<STATE, ACTION, PLAYER> game) {
        return new AlphaBetaSearch<STATE, ACTION, PLAYER>(game, 3);
    }*/

    public AlphaBetaSearch(Game<S, A, P> game, int depth) {
        this.game = game;
        this.depth = depth;
    }

    @Override
    public A makeDecision(S state) {
        metrics = new Metrics();
        A result = null;
        int depth = this.depth;
        double resultValue = Double.NEGATIVE_INFINITY;
        P player = game.getPlayer(state);
        for (A action : game.getActions(state)) {
            double value = minValue(game.getResult(state, action), player,
                    Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, depth - 1);
            if (value > resultValue) {
                result = action;
                resultValue = value;
            }
        }
        return result;
    }

    public double maxValue(S state, P player, double alpha, double beta, int depth) {
        metrics.incrementInt(METRICS_NODES_EXPANDED);
        this.game.setCurrentDepth(state, depth);
        if (game.isTerminal(state))
            return game.getUtility(state, player);
        double value = Double.NEGATIVE_INFINITY;
        for (A action : game.getActions(state)) {
            value = Math.max(value, minValue( //
                    game.getResult(state, action), player, alpha, beta, depth - 1));
            if (value >= beta)
                return value;
            alpha = Math.max(alpha, value);
        }
        return value;
    }

    public double minValue(S state, P player, double alpha, double beta, int depth) {
        metrics.incrementInt(METRICS_NODES_EXPANDED);
        this.game.setCurrentDepth(state, depth);
        if (game.isTerminal(state))
            return game.getUtility(state, player);
        double value = Double.POSITIVE_INFINITY;
        for (A action : game.getActions(state)) {
            value = Math.min(value, maxValue( //
                    game.getResult(state, action), player, alpha, beta, depth - 1));
            if (value <= alpha)
                return value;
            beta = Math.min(beta, value);
        }
        return value;
    }

    @Override
    public Metrics getMetrics() {
        return metrics;
    }
}