package controllers;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;

//import it.polito.elite.teaching.cv.utils.Utils;


public class FourierTController {
	
	
	//TRANSFORMATION variables:
	
		private Mat imageHelper;
		private List<Mat> matrix;
		private Mat floats;
		private Mat transformed;
		
		//Constructor:
		protected FourierTController(){
			this.imageHelper = Imgcodecs.imread("FacialRecognition/pictures/processed/detectedfaceimage.jpg",  Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
			this.matrix = new ArrayList<>();
			this.floats = new Mat();
			this.transformed = new Mat();
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
			
			//Get float values:
			System.out.println("Getting floaf values.");
			getMatrix().add(optImage);
			getMatrix().add(Mat.zeros(optImage.size(), CvType.CV_32F));
			Core.merge(this.matrix, this.floats);
			
			//Apply DFT:
			System.out.println("Appling DFT");
			Core.dft(this.floats, this.floats);
			Core.split(this.floats, newMatrix);
			Core.magnitude(newMatrix.get(0), newMatrix.get(1), transformedImage);
			
			//Transform to logarithmic scale to reduce dynamic range of the Fourier coefficients
			Core.add(Mat.ones(transformedImage.size(), CvType.CV_32F), transformedImage, transformedImage);
			Core.log(transformedImage, transformedImage);

			//Make the origin to correspond with the center:

			this.imageHelper = getImageHelper().submat(new Rect(0, 0, getImageHelper().cols() & -2, getImageHelper().rows() & -2));
			int cx = getImageHelper().cols() / 2;
			int cy = getImageHelper().rows() / 2;

			Mat q0 = new Mat(getImageHelper(), new Rect(0, 0, cx, cy));
			Mat q1 = new Mat(getImageHelper(), new Rect(cx, 0, cx, cy));
			Mat q2 = new Mat(getImageHelper(), new Rect(0, cy, cx, cy));
			Mat q3 = new Mat(getImageHelper(), new Rect(cx, cy, cx, cy));

			Mat tmp = new Mat();
			q0.copyTo(tmp);
			q3.copyTo(q0);
			tmp.copyTo(q3);

			q1.copyTo(tmp);
			q2.copyTo(q1);
			tmp.copyTo(q2);
			
			Core.normalize(transformedImage, transformedImage, 0, 255, Core.NORM_MINMAX);
			
			//Save transformed image
			System.out.println("Saving Transformed Image as fouriertransformed");
			Imgcodecs.imwrite("FacialRecognition/pictures/processed/fouriertransformed.jpg",transformedImage );
			setTransformed(transformedImage);
		}
		//Method to create an inverse image
		protected void inverse()
		{
			//Variables:
			Mat newImage = new Mat();

			//Get center for the mask:
			int cy = getTransformed().cols() / 2;
			int cx = getTransformed().rows() / 2;
			
			System.out.println("Starting inverse process: ");
			Core.idft(getFloats(), this.floats);
			//Create HF mask
			Mat mask = getTransformed();

					//new Mat(getTransformed().44rows(), getTransformed().cols(), CvType.CV_32F);

			Mat transformed  = getTransformed().setTo(new Scalar(0.0), mask);
			//mask(cy-30:cy+30, cx-30:cx+30] = 1;

			System.out.println("Extracting the real values from the complex, normalizing the result,");
			Core.split(getFloats(), getMatrix());
			Core.normalize(getMatrix().get(0), newImage, 0, 255, Core.NORM_MINMAX);
			newImage.convertTo(newImage, CvType.CV_8U);
			
			//Save inverse image
			System.out.println("Saving inversed Image as inverse.jpg");
			Imgcodecs.imwrite("FacialRecognition/pictures/processed/inverse.jpg", newImage );
			
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

	public Mat getTransformed() {
		return transformed;
	}

	public void setTransformed(Mat transformed) {
		this.transformed = transformed;
	}

		

}
