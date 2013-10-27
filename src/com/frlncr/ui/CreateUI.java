package com.frlncr.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import com.frlncr.calculations.Formulas;
import com.frlncr.parser.CSVParser;
import com.frlncr.util.Constants;

public class CreateUI extends JPanel{
	private List<Double> scores;
	private List<Double> xValues;
	private String fileName = "C:\\Users\\Administrator\\Desktop\\test3_scan.csv";
	private FileDialog fd = new FileDialog(new Frame(), "Open CSV", FileDialog.LOAD);
	private CreateUI mainPanel = null;
	private JFrame frame = new JFrame();
	private static double diameter = 1.0;
	private static double thickness = 1.0;
	private JTextField thicknessField = new JTextField();
	private JTextField diameterField = new JTextField();
	private JPanel graphPanel = new JPanel();

	public double getDiameter() {
		return diameter;
	}

	public void setDiameter(double diameter) {
		this.diameter = diameter;
	}

	public double getThickness() {
		return thickness;
	}

	public void setThickness(double thickness) {
		this.thickness = thickness;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public CreateUI(List<Double> scores, List<Double> xValues) {
		this.scores = scores;
		this.xValues = xValues;
	}
	
	public CreateUI() {}
	
	@SuppressWarnings("rawtypes")
	public void createAndShowGui() {

		try {
			JPanel contentPanel = new JPanel();
			contentPanel.setPreferredSize(new Dimension(Constants.PREF_W, Constants.PREF_H));
			contentPanel.setSize(Constants.PREF_W, Constants.PREF_H);
			
			graphPanel = new JPanel();
			
			JPanel buttonPanel = new JPanel();
			buttonPanel.setSize((Constants.PREF_W - Constants.LEFT_BORDER_GAP), (Constants.FIELD_HEIGHT + 30));
			buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			buttonPanel.setBorder(new LineBorder(Color.gray, 1, true));
			buttonPanel.setLocation(Constants.LEFT_BORDER_GAP, 10);
			buttonPanel.setPreferredSize(new Dimension((Constants.PREF_W - Constants.LEFT_BORDER_GAP), (Constants.FIELD_HEIGHT + 30)));
			
			
			JButton selectFileButton = new JButton();
			selectFileButton.setSize(100, Constants.FIELD_HEIGHT);
			selectFileButton.setLocation(10, 10);
			selectFileButton.setText("Select File");
			selectFileButton.setFocusPainted( false );
			
			JLabel diamLabel = new JLabel("Diameter:");
			diamLabel.setLocation(140, 10);
			diamLabel.setSize(150, Constants.FIELD_HEIGHT);
			
			diameterField.setSize(50, Constants.FIELD_HEIGHT);
			diameterField.setLocation(200, 10);
			diameterField.setPreferredSize(new Dimension(50, Constants.FIELD_HEIGHT));
			
			//=============================================
			JLabel thickLabel = new JLabel("Thickness:");
			thickLabel.setLocation(270, 10);
			thickLabel.setSize(150, Constants.FIELD_HEIGHT);
			
			thicknessField.setSize(50, Constants.FIELD_HEIGHT);
			thicknessField.setPreferredSize(new Dimension(50, Constants.FIELD_HEIGHT));
			thicknessField.setLocation(337, 10);
			
			//=============================================
			JButton submitButton = new JButton();
			submitButton.setSize(100, Constants.FIELD_HEIGHT);
			submitButton.setLocation(10, 50);
			submitButton.setText("Submit");
			submitButton.setFocusPainted(false);
			
			frame = new JFrame(fileName);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
//			ButtonActionListener ba = new ButtonActionListener();
			selectFileButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fd.setVisible(true);
					setFileName(fd.getDirectory() + fd.getFile());
				}
			});
			
			submitButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
//					fd.setVisible(true);
					setThickness(Double.valueOf(thicknessField.getText()));
					setDiameter(Double.valueOf(diameterField.getText()));
					
					if(mainPanel != null)
						frame.getContentPane().remove(graphPanel);
					
					frame.setVisible(false);
					createAndShowGui();
				}
			});
			buttonPanel.add(selectFileButton);
			buttonPanel.add(diamLabel);
			buttonPanel.add(diameterField);
			buttonPanel.add(thickLabel);
			buttonPanel.add(thicknessField);
			buttonPanel.add(submitButton);
			
			contentPanel.add(buttonPanel);
			
			graphPanel.setSize((contentPanel.getWidth() - 30), (contentPanel.getHeight() - buttonPanel.getHeight() - 30));
			graphPanel.setPreferredSize(new Dimension((contentPanel.getWidth() - 30), (contentPanel.getHeight() - buttonPanel.getHeight() - 30)));
//			System.out.println("_____________________________________ " + (contentPanel.getHeight() - buttonPanel.getHeight() - 10));
			graphPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
			graphPanel.setBorder(new LineBorder(Color.gray, 1, true));
			graphPanel.setLocation(Constants.LEFT_BORDER_GAP, buttonPanel.getY() + 10);
			
			if(fileName.length() > 0){
				CSVParser csvParser = new CSVParser();
				List[] vals = csvParser.readScores(fileName);
				scores = vals[0];
				xValues = vals[1];

				mainPanel = new CreateUI(scores, xValues);
				mainPanel.setPreferredSize(new Dimension(graphPanel.getWidth() - 10, graphPanel.getHeight()-10));
				mainPanel.setSize(graphPanel.getWidth() - 10, graphPanel.getHeight()-10);
				System.out.println(mainPanel.getWidth());
				graphPanel.add(mainPanel);
				contentPanel.add(graphPanel);
			}
			frame.getContentPane().add(contentPanel);
			frame.setLocationByPlatform(true);
			frame.pack();
			frame.setResizable(true);
			frame.setVisible(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	@Override
//	public Dimension getPreferredSize() {
//		return new Dimension(Constants.PREF_W, Constants.PREF_H);
//	}
	
	@Override
	protected void paintComponent(Graphics g) {
		try {
			super.paintComponent(g);
			
			DrawShapes ds = new DrawShapes();
			ds.setHeight(getHeight());
			ds.setWidth(getWidth());
			
			Formulas formul = new Formulas();
			List<Double> fScores = formul.calculateSigma(scores, diameter, thickness);
			List<Double> elastis = formul.calculateElasti(fScores, 4);
			
			ds.setScores(elastis);
			ds.setxValues(xValues);
			
			ds.paintComponent(g);
//			g.setColor(Color.GRAY);
//			g.drawLine(20, 80, getWidth()-20, 80);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
