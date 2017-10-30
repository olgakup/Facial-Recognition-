package controllers;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;

//import it.polito.elite.teaching.cv.utils.Utils;


public class FourierTController {
	
	
	//TRANSFORMATION variables:
	
		private Mat imageHelper;
		private List<Mat> matrix;
		private Mat floats;
		
		
		//Constructor:
		protected FourierTController(){
			this.imageHelper = Imgcodecs.imread(getClass().getResource("/cropedimage.jpg").getPath(),  Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
			this.matrix = new ArrayList<>();
			this.floats = new Mat();
		}
		
		//Methods:
		
		//discrete Fourier transform:
		protected void dft() {
				
			//Variables:
			Mat optImage = new Mat();	
			ArrayList<Mat> newMatrix = new ArrayList<Mat>();
			Mat transformedImage = new Mat();
			
			//Optimize image size
			System.out.println("Optimizing image");
			int addPixelRows = Core.getOptimalDFTSize(getImageHelper().rows());
			int addPixelCols = Core.getOptimalDFTSize(getImageHelper().cols());
			Core.copyMakeBorder(getImageHelper(), optImage, 0, addPixelRows - getImageHelper().rows(), 0, addPixelCols - getImageHelper().cols(), Core.BORDER_CONSTANT, Scalar.all(0));
			optImage.convertTo(optImage, CvType.CV_32F );
			
			//Get float values for matrix:
			System.out.println("Getting floaf values.");
			getMatrix().add(optImage);
			getMatrix().add(Mat.zeros(optImage.size(), CvType.CV_32F));
			Core.merge(this.matrix, this.floats);
			
			//Apply DFT:
			System.out.println("Appling DFT");
			Core.dft(this.floats, this.floats);
			Core.split(this.floats, newMatrix);
			Core.magnitude(newMatrix.get(0), newMatrix.get(1), transformedImage);
			
			//Transform to logarithmic scale (grey scale) (* try to delete for final submition) 
			Core.add(Mat.ones(transformedImage.size(), CvType.CV_32F), transformedImage, transformedImage);
			Core.log(transformedImage, transformedImage);
			
			Core.normalize(transformedImage, transformedImage, 0, 255, Core.NORM_MINMAX);
			
			//Save transformed image
			System.out.println("Saving Transformed Image as fouriertransformed");
			Imgcodecs.imwrite("fouriertransformed.jpg",transformedImage );
			
		}
		//Method to create an inverse image
		protected void inverse()
		{
			//Variables:
			Mat newImage = new Mat();
			
			System.out.println("Starting inverse process: ");
			Core.idft(getFloats(), this.floats);
			
			System.out.println("Extracting the real values from the complex, normalizing the result,");
			Core.split(getFloats(), getMatrix());
			Core.normalize(getMatrix().get(0), newImage, 0, 255, Core.NORM_MINMAX);
			newImage.convertTo(newImage, CvType.CV_8U);
			
			//Save inverse image
			System.out.println("Saving inversed Image as inverse.jpg");
			Imgcodecs.imwrite("inverse.jpg", newImage );
			
		}

		
		
		//Getters and setters:
		public Mat getImageHelper() {
			return imageHelper;
		}

		public void setImageHelper(Mat imageHelper) {
			this.imageHelper = imageHelper;
		}

		public List<Mat> getMatrix() {
			return matrix;
		}

		public void setMatrix(List<Mat> matrix) {
			this.matrix = matrix;
		}

		public Mat getFloats() {
			return floats;
		}

		public void setFloats(Mat floats) {
			this.floats = floats;
		}

		

}
