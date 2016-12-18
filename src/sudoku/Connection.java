package sudoku;


public class Connection {

	private GridElement[] connection;

	public Connection(GridElement[] connection) {
		this.connection = connection;
	}
	
	public void blockNumbersFromDoubleDouble() {
		int[] positionsOfNumber = new int[CONST.GRID_SIZE];
		for (int numberToTest = 0; numberToTest < CONST.GRID_SIZE; numberToTest++)
			if (hasDouble(numberToTest)) {
				collectPositionsOfNumber(numberToTest, positionsOfNumber);
				blockIfPossibleFromDoubleDouble(positionsOfNumber);
			}
	}
	
	private boolean hasDouble(int numberToTest) {
		int countFree = 0;
		for (int i = 0; i < CONST.GRID_SIZE; i++) {
			if (connection[i].getStateForNumber(numberToTest) < CONST.STATE_BLOCKED) {
				countFree++;
			}
		}
		return (countFree == 2);
	}

	private void collectPositionsOfNumber(int numberToTest, int[] positionsOfNumber) {
		int factor = 1;
		for (int i = 0; i < CONST.GRID_SIZE; i++) 
			if (connection[i].getStateForNumber(numberToTest) < CONST.STATE_BLOCKED) {
				positionsOfNumber[numberToTest] += i * factor;
				factor *= CONST.GRID_SIZE;
			}
	}

	private void blockIfPossibleFromDoubleDouble(int[] positionsOfNumber) {
		for (int lower = 0; lower < CONST.GRID_SIZE - 1; lower++)
			for (int upper = lower + 1; upper < CONST.GRID_SIZE; upper++) 
				if (positionsOfNumber[lower] > 0 
						&& positionsOfNumber[upper] > 0 
						&& positionsOfNumber[lower] == positionsOfNumber[upper]) {
					int firstPosition = positionsOfNumber[lower] % CONST.GRID_SIZE;
					int secondPosition = positionsOfNumber[lower] / CONST.GRID_SIZE;
					connection[firstPosition].blockAllNumbers();
					connection[firstPosition].setStateForNumber(lower, CONST.STATE_MARKED);
					connection[firstPosition].setStateForNumber(upper, CONST.STATE_MARKED);
					connection[secondPosition].blockAllNumbers();
					connection[secondPosition].setStateForNumber(lower, CONST.STATE_MARKED);
					connection[secondPosition].setStateForNumber(upper, CONST.STATE_MARKED);
				}
	}

}
