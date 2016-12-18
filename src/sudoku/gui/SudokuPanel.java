package sudoku.gui;

import java.awt.BorderLayout;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import sudoku.Solver;


@SuppressWarnings("serial")
public class SudokuPanel extends JPanel {
	
	private GridGUI gridGUI;
	private MenuBar menuBar;
	private Solver solver;

	public SudokuPanel() {
		super();
		this.createGUI();
		this.menuBar = new MenuBar(this);
		this.solver = new Solver(gridGUI.getGridConnections());
		//createMenuBar();
	}
	
	public GridGUI getGridGUI() {
		return gridGUI;
	}

	public JMenuBar getMenuBar() {
		return menuBar;
	}

	private void createGUI() {
		this.setLayout(new BorderLayout());
		gridGUI = new GridGUI();
		this.add(gridGUI,BorderLayout.CENTER);
		JPanel buttonPanel = new JPanel(new GridLayout(6,1));
		buttonPanel.add(createButtonShowFixed());
		buttonPanel.add(createButtonCalculateFixedFromElement());
		buttonPanel.add(createButtonCalculateFixedFromConnection());
		buttonPanel.add(createButtonCalculateBlockedFromGrid());
		buttonPanel.add(calculateBlockedFromDoubleDouble());
		JTextArea undoList = new JTextArea(4,14);
		JScrollPane aJScrollPane = new JScrollPane(undoList);
		buttonPanel.add(aJScrollPane);
		this.add(buttonPanel,BorderLayout.EAST);
		
	}

	private JButton createButtonShowFixed() {
		JButton buttonShowFixed = new JButton("Show Blocked");
		buttonShowFixed.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				solver.setBlockedStatesFromElements();
				//System.out.println("buttonShowFixe");
			}
		});
		return buttonShowFixed;
	}

	private JButton createButtonCalculateFixedFromElement() {
		JButton buttonShowCalculatedFromElement = new JButton("calc Fix from Element");
		buttonShowCalculatedFromElement.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				solver.setFixElementsFromElements();
				//System.out.println("calc Fix from Element");
			}
		});
		return buttonShowCalculatedFromElement;
	}

	private JButton createButtonCalculateFixedFromConnection() {
		JButton buttonShowCalculatedFromGroup = new JButton("calc Fix from Group");
		buttonShowCalculatedFromGroup.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				solver.setFixElementsFromConnections();
				//System.out.println("calc Fix from Group");
			}
		});
		return buttonShowCalculatedFromGroup;
	}

	private JButton createButtonCalculateBlockedFromGrid() {
		JButton buttonBlockFromGrid = new JButton("block from Grid");
		buttonBlockFromGrid.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				solver.setBlockedElementsFromMiniGrids();
				//System.out.println("calc Fix from Group");
			}
		});
		return buttonBlockFromGrid;
	}

	private JButton calculateBlockedFromDoubleDouble() {
		JButton buttonBlockFromDoublesInGrid = new JButton("block from Doubles");
		buttonBlockFromDoublesInGrid.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				solver.setBlockedElementsFromDoublesDoublesInConnections();
			}
		});
		return buttonBlockFromDoublesInGrid;
	}
}
