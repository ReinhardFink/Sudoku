package sudoku;

import java.util.Vector;

import sudoku.CONST;
import sudoku.GridElement;
import sudoku.gui.GridElementGUI;

public class GridConnections {

	private GridElement[][] grid;

	public GridConnections(int rows, int cols) {
		super();
		this.grid = new GridElement[rows][cols];
	}

	public String toString() {
		StringBuffer temp = new StringBuffer(55 * CONST.GRID_SIZE
				* CONST.GRID_SIZE);
		for (int row = 0; row < CONST.GRID_SIZE; row++)
			for (int col = 0; col < CONST.GRID_SIZE; col++)
				temp.append(grid[row][col] + "\n");
		return new String(temp);
	}

	public GridElement[][] getGrid() {
		return grid;
	}

	public void setElementAt(int row, int col, GridElementGUI element) {
		grid[row][col] = element;
	}

	public GridElement[] getMiniGrid(int row, int col) {
		GridElement[] miniGrid = new GridElementGUI[CONST.GRID_SIZE];
		for (int i = 0; i < CONST.GRID_SIZE; i++)
			miniGrid[i] = grid[row * CONST.SIZE + i / CONST.SIZE][col
					* CONST.SIZE + i % CONST.SIZE];
		return miniGrid;
	}

	public GridElement[] getMiniGridAt(int row, int col) {
		return getMiniGrid(row / CONST.SIZE, col / CONST.SIZE);
	}

	public GridElement[] getRow(int row) {
		return grid[row];
	}

	public GridElement[] getCol(int col) {
		GridElement[] colum = new GridElement[CONST.GRID_SIZE];
		for (int i = 0; i < CONST.GRID_SIZE; i++)
			colum[i] = grid[i][col];
		return colum;
	}

	public boolean contains(GridElement testElement, GridElement[] connection) {
		for (GridElement gE : connection)
			if(gE.equals(testElement))
				return true;
		return false;
	}

	public GridElement[] getCollisionFixed(int row, int col) {
		Vector<GridElement> gEV = new Vector<GridElement>();
		int number = grid[row][col].getFix();
		for (GridElement gE : getMiniGridAt(row, col))
			if (!grid[row][col].equals(gE) && gE.getFix() == number)
				gEV.add(gE);
		for (GridElement gE : getRow(row))
			if (!grid[row][col].equals(gE) && gE.getFix() == number)
				gEV.add(gE);
		for (GridElement gE : getCol(col))
			if (!grid[row][col].equals(gE) && gE.getFix() == number)
				gEV.add(gE);
		// Copy Elements to Array without loosing Type
		GridElement[] temp = new GridElement[gEV.size()];
		for (int i = 0; i < gEV.size(); i++)
			temp[i] = gEV.get(i);
		return temp;
	}

	public GridElement[] getEqualFixed(int row, int col) {
		Vector<GridElement> gEV = new Vector<GridElement>();
		int number = grid[row][col].getFix();
		for (int r = 0; r < CONST.GRID_SIZE; r++)
			for (int c = 0; c < CONST.GRID_SIZE; c++)
				if (grid[r][c].getState() >= CONST.STATE_FIXED
						&& grid[r][c].getFix() == number)
					gEV.add(grid[r][c]);
		// Copy Elements to Array without loosing Type
		GridElement[] temp = new GridElement[gEV.size()];
		for (int i = 0; i < gEV.size(); i++)
			temp[i] = gEV.get(i);
		return temp;
	}

	public void reset() {
		for (int row = 0; row < CONST.GRID_SIZE; row++)
			for (int col = 0; col < CONST.GRID_SIZE; col++)
				grid[row][col].reset();
	}
}
