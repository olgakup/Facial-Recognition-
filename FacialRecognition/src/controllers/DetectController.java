package controllers;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;



public class DetectController {
	
	//Variables:
	private Mat image;
	private Mat grayFrame;
	private CascadeClassifier faceDetector;
	MatOfRect faceDetections;
	
	//Constructor:
	protected DetectController() {
		this.faceDetector = new CascadeClassifier(DetectController.class.getResource("haarcascade_frontalface_alt.xml").getPath());
        this.image = Imgcodecs.imread(getClass().getResource("/input.jpg").getPath());
		this.grayFrame = new Mat();
		this.faceDetections = new MatOfRect();
	}
	
	//Methods:
	public void detect() {
		 System.out.println("\nStarting Face Detector program");
		 
	        // Detect faces in the image.
	        this.faceDetector.detectMultiScale(this.image, this.faceDetections);
	        System.out.println(String.format("Program has detected " + faceDetections.toArray().length) + " face/faces");

	        // Draw a red box around each face and crop image.
	        Rect rectCrop=null;
	        for (Rect rect : this.faceDetections.toArray()) {
	            Imgproc.rectangle(this.image, new Point(rect.x, rect.y), new Point(rect.x+ rect.width, rect.y + rect.height), new Scalar(255, 155, 0));
	            rectCrop = new Rect(rect.x, rect.y, rect.width, rect.height);
	        }

	        // Save:
	        
	        System.out.println(String.format("Writing "));
	        Imgcodecs.imwrite("output.png", image);
	        //Croped image for further processing 
	        Mat markedImage = new Mat(this.image,rectCrop);
	        Imgcodecs.imwrite("cropedimage.jpg",markedImage );
	    }
	}
