package sudoku;

import java.awt.Color;


public class CONST {
	
	public final static int SIZE = 3;
	public final static int GRID_SIZE = SIZE*SIZE;
	
	public static final int INCREASE_SIZE = 2;
	
	public final static int NO_ROW = -1;
	public final static int NO_COL = -1;
	
	public final static int STATE_POSSIBLE = 0;
	public final static int STATE_MARKED = 1;
	public final static int STATE_BLOCKED = 2;
	public final static int STATE_FIXED = 3;
	public final static int STATE_PROVIDED = 4;
	
	public static final String LABEL_POSSIBLE = "nicht markiert";
	public static final String LABEL_MARKED = "markiert";
	public static final String LABEL_BLOCKED = "blockiert";
	public static final String LABEL_FIXED = "fixed";
	public static final String LABEL_PROVIDED = "vorgegeben";
	
	public static final Color COLOR_BLOCKED = Color.RED;
	public static final Color COLOR_POSSIBLE = Color.WHITE;
	public static final Color COLOR_MARKED = Color.BLUE;
	public static final Color COLOR_FIXED = Color.BLUE;
	public static final Color COLOR_PROVIDED = Color.BLACK;
	
	public static final Color COLOR_EQUALS = Color.GREEN;
	
	public static final Color COLOR_BACKGROUND = new Color(238,238,238);
	public static final Color COLOR_HIGHLIGHT = new Color(255,240,240);
	
	public static final Color COLOR_GRID_ELEMENT_BORDER = Color.YELLOW;
	
	public static final String MENU_FILE = "File";
	public static final String MENU_FILE_NEW = "New";
	public static final String MENU_FILE_LOAD = "Load";
	public static final String MENU_FILE_SAVE = "Save";
}
