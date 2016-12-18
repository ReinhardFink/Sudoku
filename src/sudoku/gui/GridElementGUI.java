package sudoku.gui;

import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import sudoku.CONST;
import sudoku.GridConnections;
import sudoku.GridElement;

public class GridElementGUI extends GridElement  {

	private JPanel gui;
	private LabelWithPopup[] miniGrid;
	private int lastState;

	public GridElementGUI(int col, int row,	GridConnections gridConnections) {
		super(col, row, gridConnections);
		this.lastState = CONST.STATE_POSSIBLE;
		this.miniGrid = new LabelWithPopup[CONST.GRID_SIZE];
		this.gui = new JPanel();
		this.gui.setBorder(BorderFactory
				.createLineBorder(CONST.COLOR_GRID_ELEMENT_BORDER));
		this.gui.setLayout(new GridLayout(CONST.SIZE, CONST.SIZE));
		for (int i = 0; i < CONST.GRID_SIZE; i++) {
			miniGrid[i] = new LabelWithPopup(i, this);
			gui.add(miniGrid[i]);
		}
	}
	
	@Override
	public JPanel getGui() {
		return gui;
	}

	@Override
	public void setState(int state) {
		this.lastState = this.getState();
		super.setState(state);
		reloadGUI();
	}

	@Override
	public void setStateForNumber(int pos, int state) {
		this.lastState = this.getState();
		super.setStateForNumber(pos, state);
		reloadGUI();
	}
	
	@Override
	public void setCalculatedFix() {
		super.setCalculatedFix();
		reloadGUI();
	}
	
	@Override
	public void resetBlockedNumbers() {
		super.resetBlockedNumbers();
		reloadGUI();
	}
	
	@Override
	public void reset() {
		super.reset();
		gui.removeAll();
		gui.setLayout(new GridLayout(CONST.SIZE, CONST.SIZE));
		for (int i = 0; i < CONST.GRID_SIZE; i++) 
			gui.add(miniGrid[i]);
		for (Component p : gui.getComponents()) 
			((LabelWithPopup)p).reloadStates();
		lastState = CONST.STATE_POSSIBLE;
	}


	private void reloadGUI() {
		if  (lastState < CONST.STATE_FIXED && getState() >= CONST.STATE_FIXED) {
			gui.removeAll();
			gui.setLayout(new GridLayout(1, 1));
			super.setCalculatedFix();
			//miniGrid[this.getFix()-1].reloadStates();
			gui.add(miniGrid[this.getFix()]);
		}
		if (lastState >= CONST.STATE_FIXED && getState() < CONST.STATE_FIXED) {
			gui.removeAll();
			gui.setLayout(new GridLayout(CONST.SIZE, CONST.SIZE));
			for (int i = 0; i < CONST.GRID_SIZE; i++) {
				//miniGrid[i].reloadStates();
				gui.add(miniGrid[i]);
			}
		}
		for (Component p : gui.getComponents()) 
			((LabelWithPopup)p).reloadStates();
		gui.validate();
		gui.repaint();
	}
}
