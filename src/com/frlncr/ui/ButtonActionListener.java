package com.frlncr.ui;

import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonActionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public String selectFileClicked(){
		FileDialog fd = new FileDialog(new Frame(), "Open CSV", FileDialog.LOAD);
	    fd.setVisible(true);
	    return fd.getDirectory() + fd.getFile();
	}
}
