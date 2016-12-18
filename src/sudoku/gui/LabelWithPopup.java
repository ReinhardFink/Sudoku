package sudoku.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

import sudoku.CONST;
import sudoku.GridElement;

@SuppressWarnings("serial")
public class LabelWithPopup extends JLabel {

	private GridElementGUI gridElementGUI;
	private PopupMenu popupMenu;
	private int number;
	private int standardFontSize;

	public LabelWithPopup(int number, GridElementGUI gridElement) {
		super();
		this.gridElementGUI = gridElement;
		this.number = number;
		this.setText("" + (number + 1));
		this.setHorizontalAlignment(JLabel.CENTER);
		this.standardFontSize = this.getFont().getSize();
		this.reloadStates();
		this.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				setBackgroundcolorForConnections(CONST.COLOR_HIGHLIGHT);
				setBackgroundcolorForEqualElements(CONST.COLOR_EQUALS);
				setBackgroundcolorForEqualElementsInConnection(Color.RED);
			}
			public void mouseExited(MouseEvent e) {
				setBackgroundcolorForConnections(CONST.COLOR_BACKGROUND);
				setBackgroundcolorForEqualElements(CONST.COLOR_BACKGROUND);
				//setBackgroundcolorForEqualElementsInConnection(Color.RED);
			}
			public void mouseClicked(MouseEvent e) {
				popupMenu.show(e.getComponent(), e.getX(), e.getY());
			}
		});
		this.popupMenu = new PopupMenu(number, gridElement);
	}

	public void reloadStates() {
		this.setFont(new Font(this.getFont().getName(),	Font.PLAIN, this.standardFontSize));
		switch (gridElementGUI.getStateForNumber(number)) {
		case CONST.STATE_POSSIBLE:
			this.setForeground(CONST.COLOR_POSSIBLE);
			break;
		case CONST.STATE_MARKED:
			this.setForeground(CONST.COLOR_MARKED);
			break;
		case CONST.STATE_BLOCKED:
			this.setForeground(CONST.COLOR_BLOCKED);
			break;
		case CONST.STATE_FIXED:
			//System.out.println("fixed");
			this.setForeground(CONST.COLOR_FIXED);
			this.setFont(new Font(this.getFont().getName(),	Font.ITALIC, this.standardFontSize * CONST.INCREASE_SIZE));
			break;
		case CONST.STATE_PROVIDED:
			//System.out.println("provided");
			this.setForeground(CONST.COLOR_PROVIDED);
			this.setFont(new Font(this.getFont().getName(),	Font.PLAIN, this.standardFontSize * CONST.INCREASE_SIZE));
			//this.setForeground(CONSTANTS.COLOR_PROVIDED);
			break;
		}
	}
	
	private void setBackgroundcolorForConnections(Color color) {
		for (GridElement gE : gridElementGUI.getGridConnections().getRow(gridElementGUI.getRow())) {
			((JPanel)gE.getGui()).setBackground(color);
		}
		for (GridElement gE : gridElementGUI.getGridConnections().getCol(gridElementGUI.getCol())) {
			((JPanel)gE.getGui()).setBackground(color);
		}
		for (GridElement gE : gridElementGUI.getGridConnections().getMiniGridAt(gridElementGUI.getRow(),gridElementGUI.getCol())) {
			((JPanel)gE.getGui()).setBackground(color);
		}
	}
	
	private void setBackgroundcolorForEqualElements(Color color) {
		if (gridElementGUI.getState() < CONST.STATE_FIXED) return;
		for (GridElement gE : gridElementGUI.getGridConnections().getEqualFixed(gridElementGUI.getRow(),gridElementGUI.getCol())) {
			((JPanel)gE.getGui()).setBackground(color);
		}
	}
	
	private void setBackgroundcolorForEqualElementsInConnection(Color color) {
		if (gridElementGUI.getState() < CONST.STATE_FIXED) return;
		for (GridElement gE : gridElementGUI.getGridConnections().getCollisionFixed(gridElementGUI.getRow(),gridElementGUI.getCol())) {
			((JPanel)gE.getGui()).setBackground(color);
		}
	}
}
