package it.unibo.ai.didattica.competition.tablut.client.petru.aima;

import java.util.List;
import aima.core.search.adversarial.Game;
import it.unibo.ai.didattica.competition.tablut.domain.StateTablut;

public class TablutGame implements  Game<StateTablut, XYWho, String> {

	@Override
	public List<XYWho> getActions(StateTablut arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StateTablut getInitialState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPlayer(StateTablut arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getPlayers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getUtility(StateTablut arg0, String arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isTerminal(StateTablut arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public StateTablut getResult(StateTablut arg0, XYWho arg1) {
		// TODO Auto-generated method stub
		return null;
	}

}
