package com.frlncr.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.frlncr.calculations.Formulas;
import com.frlncr.util.Constants;

public class DrawShapes {
	
	private List<Double> scores;
	private List<Double> xValues;
	private List<Double> yValues;
	private int height;
	private int width;
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public List<Double> getScores() {
		return scores;
	}

	public void setScores(List<Double> scores) {
		this.scores = scores;
	}

	public List<Double> getxValues() {
		return xValues;
	}

	public void setxValues(List<Double> xValues) {
		this.xValues = xValues;
	}

	public void drawGraphPoint2Ds(Graphics2D g2, List<Point2D.Double> graphPoint2Ds){
		
		for (int i = 0; i < graphPoint2Ds.size() - 1; i++) {
			Point2D.Double currentPoint2D = graphPoint2Ds.get(i);
			Double x = currentPoint2D.x + Constants.TOP_BORDER_GAP;
			Double y = yValues.get(i);// * (((Double)currentPoint2D.x).intValue()) + ((getHeight() - Constants.BORDER_GAP)/2);
			int ovalW = Constants.GRAPH_Point2D_WIDTH;
			int ovalH = Constants.GRAPH_Point2D_WIDTH;
			g2.setColor(Constants.GRAPH_Point2D_COLOR);
			
			double difY = 0;
			if(yValues.get(i+1) > y)
				difY = (yValues.get(i+1) - y) * 10000;
			else
				difY = (y - yValues.get(i+1)) * 10000;
			
			double difX = (graphPoint2Ds.get(i+1).x - currentPoint2D.x);
//			System.out.println(graphPoint2Ds.get(i+1).x + " " + currentPoint2D.x);
			System.out.println("----------------- " + x + " " + (x+difX*i) + " " + (y) + " " + difX);
			g2.fill(new Ellipse2D.Double(x+difX*i, y - difY, ovalW, ovalH));
			
//			g2.setColor(Color.BLACK);
//			if(i > 5300 && i < 5338)
//			g2.drawString(String.valueOf(y), ((Double)x).intValue(), ((Double)(y+15)).intValue());
		}
	}
	
	public void drawConnectionLine(Graphics2D g2, List<Point2D.Double> graphPoint2Ds, int linesCount){
		
		for (int i = 0; i < linesCount; i++) {
			Point2D.Double currentPoint = graphPoint2Ds.get(i);
//			graphPoint2Ds.get(i+1).y -= 0.1;
			Point2D.Double nextPoint = graphPoint2Ds.get(i+1);
			double x1 = currentPoint.x + Constants.LEFT_BORDER_GAP + Constants.GRAPH_Point2D_WIDTH/2;
			double y1 = yValues.get(i);
			//(-1*currentPoint.y + Constants.GRAPH_Point2D_WIDTH) * (((Double)currentPoint.x).intValue()) + ((getHeight() - Constants.BORDER_GAP)/2);
			double x2 = nextPoint.x + Constants.LEFT_BORDER_GAP + Constants.GRAPH_Point2D_WIDTH/2;
			double y2 = yValues.get(i+1);
			//(-1*nextPoint.y + Constants.GRAPH_Point2D_WIDTH) * (((Double)nextPoint.x).intValue())
						//+ ((getHeight() - Constants.BORDER_GAP)/2);
			double difY = 0;
			if(y2 > y1)
				difY = (y2 - y1) * 10000;
			else
				difY = (y1 - y2) * 10000;
			
			double difX = (x2 - x1);
			
			y2 -= difY;
			if(y2 <= 0)
				y2 = ((getHeight() - Constants.TOP_BORDER_GAP))/2;
			
			g2.draw(new Line2D.Double(x1+difX*i,y1,x2+difX*i ,y2));
		}
		System.out.println("+++++++++++++++++++++++++++++++"+linesCount);
	}
	
	public void drawAxis(Graphics2D g2){
		// create y axis
		g2.drawLine(Constants.LEFT_BORDER_GAP, Constants.TOP_BORDER_GAP/2,
				Constants.LEFT_BORDER_GAP, getHeight() - Constants.TOP_BORDER_GAP - (Constants.FIELD_HEIGHT + 30) - 50);

		// x axis
		g2.drawLine(Constants.LEFT_BORDER_GAP, (getHeight() - Constants.TOP_BORDER_GAP)/2,
				getWidth() - Constants.LEFT_BORDER_GAP - 50, (getHeight() - Constants.TOP_BORDER_GAP)/2);
	}
	
	public void drawHatchMarks(Graphics2D g2){
		// create hatch marks for y axis.
		for (int i = 0; i < Constants.Y_HATCH_CNT; i++) {
			int x0 = Constants.LEFT_BORDER_GAP;
			int x1 = Constants.GRAPH_Point2D_WIDTH + Constants.LEFT_BORDER_GAP;
			int y0 = (getHeight() - Constants.TOP_BORDER_GAP/2) -
					(((i + 1) * (getHeight() - Constants.TOP_BORDER_GAP * 2)) / Constants.Y_HATCH_CNT + Constants.TOP_BORDER_GAP);
			int y1 = y0;
			g2.drawLine(x0, y0, x1, y1);
		}

		// and for x axis
		// TODO: CHANGE MAXIMUM SIZ OF I!!!!
		//======================================================================================
		for (int i = 0; i < 20; i++) {
			int x0 = (i + 1) * (getWidth() - Constants.LEFT_BORDER_GAP * 2) / (20) + Constants.LEFT_BORDER_GAP;
			int x1 = x0;
			int y0 = (getHeight() - Constants.TOP_BORDER_GAP)/2;
			int y1 = y0 - Constants.GRAPH_Point2D_WIDTH;
			g2.drawLine(x0, y0, x1, y1);
		}
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		CreateShapes cs = new CreateShapes();
		List<Point2D.Double> graphPoint2Ds = new ArrayList<Point2D.Double>();
		graphPoint2Ds = cs.makeGraphPoint2Ds(scores, xValues);

		drawAxis(g2);
		
//		drawHatchMarks(g2);

		Stroke oldStroke = g2.getStroke();
		g2.setColor(Constants.GRAPH_COLOR);
		g2.setStroke(Constants.GRAPH_STROKE);
		
		makeYValues(graphPoint2Ds);
		
		Formulas f = new Formulas();
		double max = f.findMax(scores);
		double mid = f.findMiddle(scores.subList((scores.size()/2)+1, scores.size()));
		System.out.println(mid + " " + max);
		if(mid < max/2){
			System.out.println("CDDDDDDDDDDDDDDDDDDDDD");
			Toolkit.getDefaultToolkit().beep();
		}
		
		drawConnectionLine(g2, graphPoint2Ds, (scores.size()-1));
		
//		g2.setStroke(oldStroke);
//		g2.setColor(Constants.GRAPH_Point2D_COLOR);
//		drawGraphPoint2Ds(g2, graphPoint2Ds);
	}
	
	private void makeYValues(List<Point2D.Double> graphPoint2Ds){
		yValues = new ArrayList<Double>();
		for (int i = 0; i < scores.size(); i++) {
			Point2D.Double currentPoint = graphPoint2Ds.get(i);
			double y1 = (-1*currentPoint.y) * (((Double)currentPoint.x).intValue()) + ((getHeight() - Constants.TOP_BORDER_GAP)/2);
			
			yValues.add(y1);
		}
	}
}
