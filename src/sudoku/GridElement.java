package sudoku;

/*
 * GridElement works intern with 
 * index_inside 0 .. n-1
 */

public abstract class GridElement {

	private int col;
	private int row;
	private int state;
	private int[] states;
	private GridConnections gridConnections;

	public GridElement(int row, int col, GridConnections gridConnections) {
		super();
		this.col = col;
		this.row = row;
		this.gridConnections = gridConnections;
		this.state = CONST.STATE_POSSIBLE;
		this.states = new int[CONST.GRID_SIZE];
		// System.out.println(this);
	}

	public String toString() {
		StringBuffer temp = new StringBuffer(55);
		temp.append("row: " + (row + 1) + " col: " + (col + 1) + " state: "
				+ state + " states:");
		for (int i = 0; i < states.length; i++)
			temp.append(" " + states[i]);
		return new String(temp);
	}

	public abstract Object getGui();
	

	public int getCol() {
		return col;
	}

	public int getRow() {
		return row;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public void setStates(int[] states) {
		this.states = states.clone();
	}

	public GridConnections getGridConnections() {
		return gridConnections;
	}

	public int getStateForNumber(int pos) {
		return states[pos];
	}

	public void setStateForNumber(int pos, int newState) {
		if (this.state < CONST.STATE_FIXED && newState >= CONST.STATE_FIXED) 
			blockAllNumbers();
		if (this.state >= CONST.STATE_FIXED && newState < CONST.STATE_FIXED)
			resetBlockedNumbers();
		this.state = newState;
		states[pos] = newState;
	}

	public boolean testForCalculatedFix() {
		if (this.state < CONST.STATE_FIXED) {
			int countBlocked = 0;
			for (int i = 0; i < CONST.GRID_SIZE; i++)
				if (states[i] == CONST.STATE_BLOCKED)
					countBlocked++;
			if (countBlocked == CONST.GRID_SIZE - 1)
				return true;
		}
		return false;
	}

	public int getFix() {
		int i = 0;
		while (i < CONST.GRID_SIZE && states[i] < CONST.STATE_FIXED)
			i++;
		// System.out.println("fix: " + (i+1));
		return i;
	}

	public void setCalculatedFix() {
		if (this.state < CONST.STATE_FIXED) {
			int i = 0;
			while (states[i] == CONST.STATE_BLOCKED)
				i++;
			states[i] = CONST.STATE_FIXED;
			state = CONST.STATE_FIXED;
		}
	}

	public void resetBlockedNumbers() {
		for (int i = 0; i < CONST.GRID_SIZE; i++)
			states[i] = CONST.STATE_POSSIBLE;
	}

	public void reset() {
		for (int i = 0; i < CONST.GRID_SIZE; i++)
			states[i] = CONST.STATE_POSSIBLE;
		state = CONST.STATE_POSSIBLE;
	}

	public void blockAllNumbers() {
		for (int i = 0; i < CONST.GRID_SIZE; i++)
			states[i] = CONST.STATE_BLOCKED;
	}
}
