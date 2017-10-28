package controllers;

import java.util.ArrayList;


import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;


public class FourierTController {
	
	
	//TRANSFORMATION variables:
	
		private Mat imageHelper;
		private ArrayList<Mat> matrix;
		private Mat floats;
		
		
		//Constructor:
		protected FourierTController(){
			this.imageHelper = Imgcodecs.imread(getClass().getResource("/cropedimage.jpg").getPath());
			this.matrix = new ArrayList<>();
			this.floats = new Mat();
		}
		
		//Methods:
		
		//discrete Fourier transform:
		protected void dft() {
			
			//Variables:
			Mat optImage= new Mat();
			int type = CvType.CV_32F;
			ArrayList<Mat> newMatrix = new ArrayList<Mat>();
			Mat transformedImage = new Mat();
			
			//Optimize image size
			System.out.println("Optimizing image");
			int addPixelRows = Core.getOptimalDFTSize(getImageHelper().rows());
			int addPixelCols = Core.getOptimalDFTSize(getImageHelper().cols());
			Core.copyMakeBorder(getImageHelper(), optImage, 0, addPixelRows - getImageHelper().rows(), 0, addPixelCols - getImageHelper().cols(), Core.BORDER_CONSTANT, Scalar.all(0));
			optImage.convertTo(optImage, type);
			
			//Get float values for matrix:
			getMatrix().add(optImage);
			getMatrix().add(Mat.zeros(optImage.size(), type));
			Core.merge(getMatrix(), getFloats());
			
			//Apply DFT:
			System.out.println("Appling DFT");
			Core.dft(getFloats(), this.floats);
			Core.split(getFloats(), newMatrix);
			Core.magnitude(newMatrix.get(0), newMatrix.get(1), transformedImage);
			
			//Transform to logarithmic scale (grey scale) (maybe don't need this one) 
			Core.add(Mat.ones(transformedImage.size(), type), transformedImage, transformedImage);
			Core.log(transformedImage, transformedImage);
			
			Core.normalize(transformedImage, transformedImage, 0, 255, Core.NORM_MINMAX);
			
			//Save transformed image
			System.out.println("Saving Transformed Image");
			Imgcodecs.imwrite("fouriertransformed.jpg",transformedImage );
		}
		
		
		//Getters and setters:
		
		public Mat getImageHelper() {
			return imageHelper;
		}

		public void setImageHelper(Mat imageHelper) {
			this.imageHelper = imageHelper;
		}

		public ArrayList<Mat> getMatrix() {
			return matrix;
		}

		public void setMatrix(ArrayList<Mat> matrix) {
			this.matrix = matrix;
		}

		public Mat getFloats() {
			return floats;
		}

		public void setFloats(Mat floats) {
			this.floats = floats;
		}
		

}
