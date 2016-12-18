package sudoku;

import java.awt.Dimension;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import sudoku.gui.SudokuPanel;

public class StartSudokuApplet extends JApplet {

	private static final long serialVersionUID = 3546365024081097528L;

	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			} 
		});
	};

	static void createAndShowGUI() {
		// JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame aFrame = new JFrame("Sudoku");
		aFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		aFrame.setSize(new Dimension(700, 600));
		//aFrame.setContentPane(new SudokuPanel());
		SudokuPanel sp = new SudokuPanel();
		aFrame.setJMenuBar(sp.getMenuBar());
		aFrame.setContentPane(sp);
		aFrame.setLocation(130, 30);
		aFrame.setVisible(true);
	}

	public void init() {
		setContentPane(null);
	}
}
