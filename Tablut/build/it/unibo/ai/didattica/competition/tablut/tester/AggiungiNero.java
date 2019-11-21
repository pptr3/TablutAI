package it.unibo.ai.didattica.competition.tablut.tester;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JTextField;

import it.unibo.ai.didattica.competition.tablut.client.petru.StateTablut;
import it.unibo.ai.didattica.competition.tablut.client.petru.StateTablut.Pawn;
import it.unibo.ai.didattica.competition.tablut.client.petru.StateTablut.Turn;
import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.gui.Gui;

public class AggiungiNero implements ActionListener {

	private Gui theGui;
	private JTextField posizione;
	private StateTablut state;
	private TestGuiFrame ret;
	
	
	
	public AggiungiNero(Gui theGui, JTextField field, StateTablut state, TestGuiFrame ret) {
		super();
		this.theGui = theGui;
		this.posizione = field;
		this.state = state;
		this.ret = ret;
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		String casella = posizione.getText();
		posizione.setText("");
		Action a = null; 
		try {
			a = new Action(casella, casella, Turn.WHITE);
			int column = a.getColumnFrom();
			int row = a.getRowFrom();
			this.state.getBoard()[row][column]=Pawn.BLACK;
			this.theGui.update(state);
			this.ret.setState(state);
		} catch (IOException e1) {
			System.out.println("Wrong format of the position. Write position as \"A1\" where A1 is the cell");
		}
	}

}