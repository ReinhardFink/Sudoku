package sudoku;

public class Solver {

	private GridConnections grid;

	public Solver(GridConnections grid) {
		this.grid = grid;
	}

	public void setBlockedStatesFromElements() {
		resetAllBlockedNumbers();
		for (int row = 0; row < CONST.GRID_SIZE; row++)
			for (int col = 0; col < CONST.GRID_SIZE; col++) {
				GridElement fixGE = grid.getGrid()[row][col];
				if (fixGE.getState() >= CONST.STATE_FIXED) {
					blockNumberFromElementInConnection(fixGE,
							grid.getMiniGridAt(fixGE.getRow(), fixGE.getCol()));
					blockNumberFromElementInConnection(fixGE, grid.getRow(fixGE.getRow()));
					blockNumberFromElementInConnection(fixGE, grid.getCol(fixGE.getCol()));
				}
			}
	}

	public void setFixElementsFromElements() {
		for (int row = 0; row < CONST.GRID_SIZE; row++)
			for (int col = 0; col < CONST.GRID_SIZE; col++) {
				GridElement testedGE = grid.getGrid()[row][col];
				if (testedGE.testForCalculatedFix()) {
					// System.out.println(testedGE);
					testedGE.setCalculatedFix();
				}
			}
	}

	public void setFixElementsFromConnections() {
		//setBlockedStatesFromElements();
		for (int row = 0; row < CONST.GRID_SIZE; row++)
			for (int col = 0; col < CONST.GRID_SIZE; col++) {
				GridElement testedGE = grid.getGrid()[row][col];
				fixNumberFromConnection(testedGE, grid.getMiniGridAt(row, col));
				fixNumberFromConnection(testedGE, grid.getRow(row));
				fixNumberFromConnection(testedGE, grid.getCol(col));
			}
	}

	public void setBlockedElementsFromMiniGrids() {
		//setBlockedStatesFromElements();
		// System.out.println("-------------------");
		boolean isGridChanged = true;
		while (isGridChanged) {
			isGridChanged = false;
			for (int row = 0; row < CONST.SIZE; row++)
				for (int col = 0; col < CONST.SIZE; col++) {
					MiniGrid miniGrid = new MiniGrid(grid, grid.getMiniGrid(row, col));
					isGridChanged = miniGrid.blockNumbersInConnection() || isGridChanged;
				}
		} 
	}
	
	public void setBlockedElementsFromDoublesDoublesInConnections() {
		// System.out.println("-------------------");
		for (int row = 0; row < CONST.GRID_SIZE; row++) {
			Connection connection = new Connection(grid.getRow(row));
			connection.blockNumbersFromDoubleDouble();
		}
		for (int col = 0; col < CONST.GRID_SIZE; col++) {
			Connection connection = new Connection(grid.getCol(col));
			connection.blockNumbersFromDoubleDouble();
		}
		for (int row = 0; row < CONST.SIZE; row++)
			for (int col = 0; col < CONST.SIZE; col++) {
				Connection connection = new Connection(grid.getMiniGrid(row, col));
				connection.blockNumbersFromDoubleDouble();
			}
	}

	private void resetAllBlockedNumbers() {
		for (int row = 0; row < CONST.GRID_SIZE; row++)
			for (int col = 0; col < CONST.GRID_SIZE; col++) {
				if (grid.getGrid()[row][col].getState() < CONST.STATE_FIXED) {
					grid.getGrid()[row][col].resetBlockedNumbers();
				}
			}
	}

	private void blockNumberFromElementInConnection(GridElement fixGridElement,
			GridElement[] connection) {
		for (GridElement gE : connection) {
			if (!gE.equals(fixGridElement) && gE.getState() < CONST.STATE_FIXED) {
				gE.setStateForNumber(fixGridElement.getFix(),
						CONST.STATE_BLOCKED);
			}
		}
	}

	private void fixNumberFromConnection(GridElement testedGE,
			GridElement[] connection) {
		for (int numberToTest = 0; numberToTest < CONST.GRID_SIZE; numberToTest++) {
			// Test, if GridElement can be set
			if (!(testedGE.getStateForNumber(numberToTest) >= CONST.STATE_BLOCKED)) {
				int count = 0;
				for (GridElement gE : connection)
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
