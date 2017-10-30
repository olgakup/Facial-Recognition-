package controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class RandomFController {
	
	//Variables:
	
	private File trainmodel;
	private LBPParameters newP;
	private LBPModel model;
	
	RandomFController(){
		//initialize:
		int[] p1 = {8, 26, 24};
		int[] p2 = {1,2,3};
		int[] p3 = {10,10,10};
		this.newP = new LBPParameters(p1, p2, p3);
		
		//File[] images //parse all 60 images
		//this.model = new LBPModel(this.newP, images);
		
			
	}
	
	/*
	protected void start{
	RandomForest model = new RandomForest.Trainer()
				    .setNumTrees(100) 
				    .setNodeSize(4)
				    .setSamplingRates(0.7)
				    .setSplitRule(SplitRule.ENTROPY)
				    .setNumRandomFeatures(3)
				    .train(X, y);
				    
	}*/

}
