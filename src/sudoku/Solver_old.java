package sudoku;

public class Solver_old {

	private GridConnections grid;

	public Solver_old(GridConnections grid) {
		this.grid = grid;
	}

	public void setAllBlockedStates() {
		resetBlockedNumbers();
		for (int row = 0; row < CONST.GRID_SIZE; row++)
			for (int col = 0; col < CONST.GRID_SIZE; col++) {
				GridElement fixGE = grid.getGrid()[row][col];
				if (fixGE.getState() >= CONST.STATE_FIXED) {
					blockNumberForAt(fixGE,
							grid.getMiniGridAt(fixGE.getRow(), fixGE.getCol()));
					blockNumberForAt(fixGE, grid.getRow(fixGE.getRow()));
					blockNumberForAt(fixGE, grid.getCol(fixGE.getCol()));
				}
			}
	}

	public void calculateFixedFromElement() {
		for (int row = 0; row < CONST.GRID_SIZE; row++)
			for (int col = 0; col < CONST.GRID_SIZE; col++) {
				GridElement testedGE = grid.getGrid()[row][col];
				if (testedGE.testForCalculatedFix()) {
					// System.out.println(testedGE);
					testedGE.setCalculatedFix();

				}
			}
	}

	public void calculateFixedInConnection() {
		setAllBlockedStates();
		for (int row = 0; row < CONST.GRID_SIZE; row++)
			for (int col = 0; col < CONST.GRID_SIZE; col++) {
				GridElement testedGE = grid.getGrid()[row][col];
				fixNumberFromGrid(testedGE, grid.getMiniGridAt(row, col));
				fixNumberFromGrid(testedGE, grid.getRow(row));
				fixNumberFromGrid(testedGE, grid.getCol(col));
			}
	}

	public void blockFromGrid() {
		setAllBlockedStates();
		// System.out.println("-------------------");
		boolean isGridChanged = true;
		while (isGridChanged) {
			isGridChanged = false;
			for (int row = 0; row < CONST.SIZE; row++)
				for (int col = 0; col < CONST.SIZE; col++) {
					GridElement[] miniGrid = grid.getMiniGrid(row, col);
					for (int numberToTest = 0; numberToTest < CONST.GRID_SIZE; numberToTest++)
						if (hasDouble(miniGrid, numberToTest)) {
							markDouble(miniGrid, numberToTest);
							isGridChanged = blockIfPossibleDoubleInRow(miniGrid, numberToTest) || isGridChanged;
						}
				}
		} 
	}

	private boolean hasDouble(GridElement[] miniGrid, int numberToTest) {
		int countBlocked = 0;
		for (int i = 0; i < CONST.GRID_SIZE; i++) {
			if (miniGrid[i].getStateForNumber(numberToTest) >= CONST.STATE_BLOCKED)
				countBlocked++;
		}
		// countBlocked = CONSTANTS.GRID_SIZE => number = fix
		// countBlocked < CONSTANTS.GRID_SIZE => number just once
		return (countBlocked > CONST.GRID_SIZE - CONST.SIZE && countBlocked < CONST.GRID_SIZE - 1);

	}

	private void markDouble(GridElement[] miniGrid, int numberToTest) {
		for (int i = 0; i < CONST.GRID_SIZE; i++) {
			if (miniGrid[i].getStateForNumber(numberToTest) < CONST.STATE_BLOCKED) {
				miniGrid[i].setStateForNumber(numberToTest, CONST.STATE_MARKED);
			}
		}
	}

	private boolean blockIfPossibleDoubleInRow(GridElement[] miniGrid,
			int numberToTest) {
		int row = -1;
		int col = -1;
		// find 1. marked
		int i = 0;
		while (i < CONST.GRID_SIZE
				&& miniGrid[i].getStateForNumber(numberToTest) != CONST.STATE_MARKED)
			i++;
		row = miniGrid[i].getRow();
		col = miniGrid[i].getCol();
		i++;
		// find next marked in Row
		while (i < CONST.GRID_SIZE) {
			while (i < CONST.GRID_SIZE
					&& miniGrid[i].getStateForNumber(numberToTest) != CONST.STATE_MARKED)
				i++;
			if (i < CONST.GRID_SIZE && row != miniGrid[i].getRow())
				row = -1;
			if (i < CONST.GRID_SIZE && col != miniGrid[i].getCol())
				col = -1;
			i++;
		}
		//System.out.println("row: " + row);
		if (row != -1) {
			return (blockNumberInConnection(numberToTest, miniGrid, grid.getRow(row)) > 0);
		}
		if (col != -1) {
			return (blockNumberInConnection(numberToTest, miniGrid, grid.getCol(col)) > 0);
		}
		return false;
	}

	private void resetBlockedNumbers() {
		for (int row = 0; row < CONST.GRID_SIZE; row++)
			for (int col = 0; col < CONST.GRID_SIZE; col++) {
				if (grid.getGrid()[row][col].getState() < CONST.STATE_FIXED) {
					grid.getGrid()[row][col].resetBlockedNumbers();
				}
			}
	}

	private void blockNumberForAt(GridElement fixGridElement,
			GridElement[] gridElementArray) {
		for (GridElement gE : gridElementArray) {
			if (!gE.equals(fixGridElement) && gE.getState() < CONST.STATE_FIXED) {
				gE.setStateForNumber(fixGridElement.getFix(),
						CONST.STATE_BLOCKED);
			}
		}
	}

	private int blockNumberInConnection(int number, GridElement[] source,
			GridElement[] destination) {
		int countBlocked = 0;
		for (GridElement gE : destination) {
			if (!grid.contains(gE, source)
					&& gE.getStateForNumber(number) < CONST.STATE_BLOCKED) {
				gE.setStateForNumber(number, CONST.STATE_BLOCKED);
				countBlocked++;
			}
		}
		return countBlocked;
	}

	private void fixNumberFromGrid(GridElement testedGE,
			GridElement[] gridElementArray) {
		for (int numberToTest = 0; numberToTest < CONST.GRID_SIZE; numberToTest++) {
			// Test, if GridElement can be set
			if (!(testedGE.getStateForNumber(numberToTest) >= CONST.STATE_BLOCKED)) {
				int count = 0;
				for (GridElement gE : gridElementArray)
					// Test, if number already fix in Grid and ...
					if (gE.getStateForNumber(numberToTest) >= CONST.STATE_FIXED)
						return;
					else if (gE.getStateForNumber(numberToTest) == CONST.STATE_BLOCKED
							|| gE.getState() >= CONST.STATE_FIXED)
						count++;
				if (count == CONST.GRID_SIZE - 1) {
					testedGE.setStateForNumber(numberToTest, CONST.STATE_FIXED);
				}
			}
		}
	}
}
