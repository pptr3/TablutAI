package it.unibo.ai.didattica.competition.tablut.failurestate.game;

import java.util.List;

public interface Game<S, A, P> {

    S getInitialState();

    P[] getPlayers();

    P getPlayer(S state);

    List<A> getActions(S state);

    S getResult(S state, A action);

    boolean isTerminal(S state);

    double getUtility(S state, P player);
    
    int getCurrentDepth(S state);
    
    void setCurrentDepth(S state, int depth);
}