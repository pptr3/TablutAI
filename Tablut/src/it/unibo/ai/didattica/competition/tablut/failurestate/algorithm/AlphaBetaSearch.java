package it.unibo.ai.didattica.competition.tablut.failurestate.algorithm;

import aima.core.search.adversarial.AdversarialSearch;
import aima.core.search.framework.Metrics;
import it.unibo.ai.didattica.competition.tablut.failurestate.game.Game;

public class AlphaBetaSearch<S, A, P> implements AdversarialSearch<S, A> {

    public final static String METRICS_NODES_EXPANDED = "nodesExpanded";
    Game<S, A, P> game;
    private Metrics metrics = new Metrics();
    private int depth;

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
        if (game.isTerminal(state)) {
            return game.getUtility(state, player);
        }
        double value = Double.NEGATIVE_INFINITY;
        for (A action : game.getActions(state)) {
            value = Math.max(value, minValue( //
                    game.getResult(state, action), player, alpha, beta, depth - 1));
            if (value >= beta && (value != Double.POSITIVE_INFINITY)) {
                return value;
            }
            alpha = Math.max(alpha, value);
        }
        return value != Double.POSITIVE_INFINITY? value : 0;
    }

    public double minValue(S state, P player, double alpha, double beta, int depth) {
        metrics.incrementInt(METRICS_NODES_EXPANDED);
        this.game.setCurrentDepth(state, depth);
        if (game.isTerminal(state)) {
            return game.getUtility(state, player);
        }
        double value = Double.POSITIVE_INFINITY;
        for (A action : game.getActions(state)) {
            value = Math.min(value, maxValue( //
                    game.getResult(state, action), player, alpha, beta, depth - 1));
            if (value <= alpha && (value != Double.NEGATIVE_INFINITY)) {
                return value;
            }
            beta = Math.min(beta, value);
        }
        return value != Double.NEGATIVE_INFINITY? value : 0;
    }

    @Override
    public Metrics getMetrics() {
        return metrics;
    }
}