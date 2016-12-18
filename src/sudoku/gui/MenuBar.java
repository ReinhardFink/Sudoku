package sudoku.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import sudoku.CONST;
import sudoku.GridElement;

@SuppressWarnings("serial")
public class MenuBar extends JMenuBar implements ActionListener {

	private SudokuPanel sudokuPanel;
	private JFileChooser fileChooser;

	public MenuBar(SudokuPanel sudokuPanel) {
		super();
		this.sudokuPanel = sudokuPanel;
		this.fileChooser = new JFileChooser();
		this.createMenuBar();
	}

	public void createMenuBar() {
		JMenu fileMenu = new JMenu(CONST.MENU_FILE);
		this.add(fileMenu);
		JMenuItem newMI = new JMenuItem(CONST.MENU_FILE_NEW);
		newMI.addActionListener(this);
		fileMenu.add(newMI);
		JMenuItem loadMI = new JMenuItem(CONST.MENU_FILE_LOAD);
		loadMI.addActionListener(this);
		fileMenu.add(loadMI);
		JMenuItem saveMI = new JMenuItem(CONST.MENU_FILE_SAVE);
		saveMI.addActionListener(this);
		fileMenu.add(saveMI);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(CONST.MENU_FILE_NEW)) {
			sudokuPanel.getGridGUI().reset();
		}
		else if (e.getActionCommand().equals(CONST.MENU_FILE_LOAD)) {
			sudokuPanel.getGridGUI().reset();
			int returnVal = fileChooser.showOpenDialog(MenuBar.this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				// This is where a real application would open the file.
				try {
					Scanner scanner = new Scanner(file);
					GridElement[][] grid = sudokuPanel.getGridGUI().getGridConnections().getGrid();
					int[] states = new int[CONST.GRID_SIZE];
					while (scanner.hasNext()) {
						//System.out.println(scanner.next());
						scanner.next();
						int row = scanner.nextInt() - 1;
						scanner.next();
						int col = scanner.nextInt() - 1;
						scanner.next();
						//System.out.println(row + " row col " + col);
						int state = scanner.nextInt();
						scanner.next();
						for (int i = 0; i < CONST.GRID_SIZE; i++) {
							states[i] = scanner.nextInt();
							//System.out.println(scanner.nextInt());
						}
						grid[row][col].setStates(states);
						grid[row][col].setState(state);
					}
					
					scanner.close();
				} catch (IOException io) {
					System.out.println("Exception ");
				}

			}
		}
		else if (e.getActionCommand().equals(CONST.MENU_FILE_SAVE)) {
			int returnVal = fileChooser.showSaveDialog(MenuBar.this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				try {
					FileWriter writer = new FileWriter(file);
					sudokuPanel.getGridGUI().getGridConnections();
					writer.write(sudokuPanel.getGridGUI().getGridConnections().toString());
					writer.close();
				} catch (IOException io) {
					System.out.println("Exception ");
				}
			}
		}
	}
}
