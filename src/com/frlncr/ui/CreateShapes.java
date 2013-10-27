package com.frlncr.ui;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class CreateShapes {
	public List<Point2D.Double> makeGraphPoint2Ds(List<Double> scores, List<Double> xValues){
		List<Point2D.Double> graphPoint2Ds = new ArrayList<Point2D.Double>();
		for (int i = 0; i < scores.size(); i++) {
			Point2D.Double p = new Point2D.Double(xValues.get(i),scores.get(i));
			graphPoint2Ds.add(p);
		}
		
		return graphPoint2Ds;
	}
}
