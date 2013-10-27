package com.frlncr.main;

import javax.swing.SwingUtilities;

import com.frlncr.ui.CreateUI;

/**
 * @param args
 */
@SuppressWarnings("serial")
public class Main{
	
	private static void executeCommand(){
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				CreateUI cUI = new CreateUI();
				cUI.createAndShowGui();
			}
		});
	}

	public static void main(String[] args) {
		executeCommand();
	}
}
