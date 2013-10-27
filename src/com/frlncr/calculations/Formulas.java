package com.frlncr.calculations;

import java.util.ArrayList;
import java.util.List;

public class Formulas {
	
	public double calculateElasti(double sigma, double epsilon){
		if(epsilon != 0)
			return sigma/epsilon;
		
		return 0.0;
	}
	
	public List<Double> calculateElasti(List<Double> sigmas, double epsilon){
		List<Double> returnList= new ArrayList<Double>();
		if(epsilon != 0){
			for(int j = 0; j < sigmas.size(); j++)
				returnList.add(sigmas.get(j)/epsilon);
		}
		System.out.println("elasti: " + returnList);
		return returnList;
	}
	
	public double calculateSigma(double force, double diameter, double thicknes){
		if(thicknes > 0 && diameter > 0){
			double sigma = (2*force)/(Math.PI*diameter*thicknes);
			return sigma;
		}
		
		return -1.0;
	}
	
	public List<Double> calculateSigma(List<Double> forces, double diameter, double thicknes){
		List<Double> returnList= new ArrayList<Double>();
		if(thicknes > 0 && diameter > 0){
			for(int i = 0; i < forces.size(); i++){
				double sigma = (2*forces.get(i))/(Math.PI*diameter*thicknes);
				returnList.add(sigma);
			}
		}
		System.out.println("sigma: " + returnList);
		return returnList;
	}
	
	public Double findMax(List<Double> nums){
		double max = nums.get(0);
		for(int i = 0; i < nums.size(); i++){
			double current = nums.get(i);
			if(current > max)
				max = current;
		}
		return max;
	}
	
	public double findMiddle(List<Double> nums){
		double sum = 0;
		for(int i = 0; i < nums.size(); i++){
			sum += nums.get(i);
		}
		
		return sum/nums.size();
	}
}
