package sudoku.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;

import sudoku.CONST;
import sudoku.GridElement;

@SuppressWarnings("serial")
public class PopupMenu extends JPopupMenu implements ActionListener {
	
	private GridElement gridElementGUI;

	public PopupMenu(int number, GridElementGUI gridElementGUI) {
		super();
		this.gridElementGUI = gridElementGUI;
		createPopupMenu(number + 1);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand().equals(CONST.LABEL_BLOCKED)) {
			gridElementGUI.setStateForNumber(new Integer(((JLabel)this.getInvoker()).getText())-1,CONST.STATE_BLOCKED);
		}
		else if (event.getActionCommand().equals(CONST.LABEL_POSSIBLE)) {
			gridElementGUI.setStateForNumber(new Integer(((JLabel)this.getInvoker()).getText())-1,CONST.STATE_POSSIBLE);
		}
		else if (event.getActionCommand().equals(CONST.LABEL_MARKED)) {
			gridElementGUI.setStateForNumber(new Integer(((JLabel)this.getInvoker()).getText())-1,CONST.STATE_MARKED);
		}
		else if (event.getActionCommand().equals(CONST.LABEL_FIXED)) {
			gridElementGUI.setStateForNumber(new Integer(((JLabel)this.getInvoker()).getText())-1,CONST.STATE_FIXED);
		}
		else if (event.getActionCommand().equals(CONST.LABEL_PROVIDED)) {
			gridElementGUI.setStateForNumber(new Integer(((JLabel)this.getInvoker()).getText())-1,CONST.STATE_PROVIDED);
		}
		PopupMenu.this.setVisible(false);
	}

	private void createPopupMenu(int number) {
		JRadioButton blockedButton = new JRadioButton(CONST.LABEL_BLOCKED);
		blockedButton.setActionCommand(CONST.LABEL_BLOCKED);
		blockedButton.addActionListener(this);
		JRadioButton unmarkedButton = new JRadioButton(CONST.LABEL_POSSIBLE);
		unmarkedButton.setActionCommand(CONST.LABEL_POSSIBLE);
		unmarkedButton.addActionListener(this);
		JRadioButton markedButton = new JRadioButton(CONST.LABEL_MARKED);
		markedButton.setActionCommand(CONST.LABEL_MARKED);
		markedButton.addActionListener(this);
		JRadioButton fixedButton = new JRadioButton(CONST.LABEL_FIXED);
		fixedButton.setActionCommand(CONST.LABEL_FIXED);
		fixedButton.addActionListener(this);
		JRadioButton proposedButton = new JRadioButton(CONST.LABEL_PROVIDED);
		proposedButton.setActionCommand(CONST.LABEL_PROVIDED);
		proposedButton.addActionListener(this);
		ButtonGroup group = new ButtonGroup();
	    group.add(blockedButton);
	    group.add(unmarkedButton);
	    group.add(markedButton);
	    group.add(fixedButton);
	    group.add(proposedButton);
	    //this.add(panel);
	    this.add(new JLabel(" - - - " + number + " - - - "));
	    this.add(blockedButton);
	    this.add(unmarkedButton);
	    this.add(markedButton);
	    this.add(fixedButton);
	    this.add(proposedButton);
	}

}
