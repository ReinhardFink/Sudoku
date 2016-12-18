package sudoku.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import sudoku.CONST;
import sudoku.GridConnections;


@SuppressWarnings("serial")
public class GridGUI extends JPanel{
	
	private GridConnections gridConnections;

	public GridGUI() {
		super();
		createConnections();
		createGUI();
	}

	public GridConnections getGridConnections() {
		return gridConnections;
	}

	public void createConnections() {
		gridConnections = new GridConnections(CONST.GRID_SIZE,CONST.GRID_SIZE);
		for (int row = 0; row < CONST.GRID_SIZE; row++) 
			for (int col = 0; col < CONST.GRID_SIZE; col++) 	
				gridConnections.setElementAt(row, col, new GridElementGUI(row,col, gridConnections));
	}
	
	public void reset() {
		gridConnections.reset();
	}

	private void createGUI() {
		this.setSize(new Dimension(600, 600));
		this.setLayout(new GridLayout(CONST.SIZE,CONST.SIZE));
		this.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
		for (int row = 0; row < CONST.SIZE; row++) {
			for (int col = 0; col < CONST.SIZE; col++) {
				JPanel innerPanel = new JPanel(new GridLayout(CONST.SIZE, CONST.SIZE));
				innerPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
				for (GridElementGUI gE : (GridElementGUI[])gridConnections.getMiniGrid(row, col))
					innerPanel.add(gE.getGui());
				this.add(innerPanel);
			}
		}
	}
}
