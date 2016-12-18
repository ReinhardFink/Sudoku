package sudoku;


public class MiniGrid {

	private GridConnections gridConnections;
	private GridElement[] miniGrid;

	public MiniGrid(GridConnections gridConnections, GridElement[] miniGrid) {
		this.gridConnections = gridConnections;
		this.miniGrid = miniGrid;
	}

	public boolean blockNumbersInConnection() {
		boolean isGridChanged = false;
		for (int numberToTest = 0; numberToTest < CONST.GRID_SIZE; numberToTest++)
			if (hasDoubleOrTripple(numberToTest)) {
				// TODO
				// Achtung auch Doppelte. die nicht blocken werden markiert
				// BUG oder FEATURE
				//markDoubleOrTripple(numberToTest);
				isGridChanged = blockIfPossibleFromDoubleInRowCol(numberToTest)
						|| isGridChanged;
			}
		return isGridChanged;
	}	

	private boolean hasDoubleOrTripple(int numberToTest) {
		int countBlocked = 0;
		for (int i = 0; i < CONST.GRID_SIZE; i++) {
			if (miniGrid[i].getStateForNumber(numberToTest) >= CONST.STATE_BLOCKED)
				countBlocked++;
		}
		// countBlocked = CONSTANTS.GRID_SIZE - 1 => number = fix
		// countBlocked = CONSTANTS.GRID_SIZE - 1 => number just once
		return (countBlocked >= CONST.GRID_SIZE - CONST.SIZE && countBlocked < CONST.GRID_SIZE - 1);
	}

	private void markDoubleOrTripple(int numberToTest) {
		for (int i = 0; i < CONST.GRID_SIZE; i++) {
			if (miniGrid[i].getStateForNumber(numberToTest) < CONST.STATE_BLOCKED) {
				miniGrid[i].setStateForNumber(numberToTest, CONST.STATE_MARKED);
			}
		}
	}

	private boolean blockIfPossibleFromDoubleInRowCol(int numberToTest) {
		int[] rowcol = checkNumberWithStateInSameRowCol(numberToTest, CONST.STATE_MARKED);
		// System.out.println("row: " + row);
		// important rowcol[0] != -1 and rowcol[1] != -1 can not appear at same time
		if (rowcol[0] != CONST.NO_ROW) {
			markDoubleOrTripple(numberToTest);
			return (blockNumberInConnection(numberToTest,
					gridConnections.getRow(rowcol[0])) > 0);
		}
		if (rowcol[1] != CONST.NO_COL) {
			markDoubleOrTripple(numberToTest);
			return (blockNumberInConnection(numberToTest,
					gridConnections.getCol(rowcol[1])) > 0);
		}
		return false;
	}

	private int[] checkNumberWithStateInSameRowCol(int numberToTest, int state) {
		int row = CONST.NO_ROW;
		int col = CONST.NO_COL;
		// find 1. marked
		int i = 0;
		while (i < CONST.GRID_SIZE
				&& miniGrid[i].getStateForNumber(numberToTest) > state)
			i++;
		row = miniGrid[i].getRow();
		col = miniGrid[i].getCol();
		i++;
		// find next marked in Row
		while (i < CONST.GRID_SIZE) {
			while (i < CONST.GRID_SIZE
					&& miniGrid[i].getStateForNumber(numberToTest) > state)
				i++;
			if (i < CONST.GRID_SIZE && row != miniGrid[i].getRow())
				row = CONST.NO_ROW;
			if (i < CONST.GRID_SIZE && col != miniGrid[i].getCol())
				col = CONST.NO_COL;
			i++;
		}
		int[] temp = new int[2];
		temp[0] = row;
		temp[1] = col;
		return temp;
	}

	private boolean contains(GridElement gridElement) {
		for (GridElement gE : miniGrid)
			if (gE.equals(gridElement))
				return true;
		return false;
	}

	private int blockNumberInConnection(int number, GridElement[] destination) {
		int countBlocked = 0;
		for (GridElement gE : destination) {
			if (!contains(gE)
					&& gE.getStateForNumber(number) < CONST.STATE_BLOCKED) {
				gE.setStateForNumber(number, CONST.STATE_BLOCKED);
				countBlocked++;
			}
		}
		return countBlocked;
	}
}
