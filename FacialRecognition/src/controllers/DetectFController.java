package controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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



public class DetectFController {
	
	//Variables:
	private Mat personimage;
	private CascadeClassifier detector;
	MatOfRect faceDetections;
	
	//Constructor:
	protected DetectFController() {
		this.detector = new CascadeClassifier(DetectFController.class.getResource("haarcascade_frontalface_alt.xml").getPath());
		String a = getClass().getResource("/input.jpg").getPath();
        this.personimage = Imgcodecs.imread(getClass().getResource("/input.jpg").getPath());
		this.faceDetections = new MatOfRect();
	}
	
	//Methods:
	
	//Method to detect face
	public void detect() throws IOException {
		 System.out.println("\nStarting Face Detector program");
		 	
		 
		 	greyscale();
	        // Detect faces in the image.
	        this.detector.detectMultiScale(this.personimage, this.faceDetections);
	        System.out.println("\nProgram has detected " + faceDetections.toArray().length + " face/faces");

	        // Draw a blue box around each face and crop image.
	        System.out.println("\nDrawing a box");
	        Rect rectCrop=null;
	        for (Rect rect : this.faceDetections.toArray()) {
	            Imgproc.rectangle(this.personimage, new Point(rect.x, rect.y), new Point(rect.x+ rect.width, rect.y + rect.height), new Scalar(255, 155, 0), 4);
	            rectCrop = new Rect(rect.x, rect.y, rect.width, rect.height);
	        }

	        // Save:
	        System.out.println(String.format("\nSaving"));
	        Imgcodecs.imwrite("output.png", personimage);
	        //Cropped image for further processing 
	        Mat markedImage = new Mat(this.personimage,rectCrop);
	        Imgcodecs.imwrite("cropedimage.jpg",markedImage );
	    }
	
	//Method to change image to grey scale image
	protected void greyscale() throws IOException {
		
		try {
		    // retrieve image
		    File newf = new File(getClass().getResource("/input.jpg").getPath());
		    BufferedImage newImage = ImageIO.read(newf);
		    int w = newImage.getWidth();
		    int h = newImage.getHeight();
	  
		    //Convert by traversing each pixel :
		    //get rows y columns x:
		    for(int y = 0; y < h; y++){
		    	for(int x = 0; x < w; x++){
		    		int p = newImage.getRGB(x,y);
		    		//extract the Alpha, Red, Green and Blue value from the pixel value.
		    		int alpha = (p>>24)&0xff;
		    		int red = (p>>16)&0xff;
		    		int green = (p>>8)&0xff;
		    		int blue = p&0xff;

		        //get average
		        int average = (red+green+blue)/3;

		        //replace RGB value with average
		        p = (alpha<<24) | (average<<16) | (average<<8) | average;

		        newImage.setRGB(x, y, p);
		      }
		    }
		    
		    //Save Image:
		    try {
		    	ImageIO.write(newImage, "JPEG", newf);
		    } catch (IOException e) {
			    System.out.println("Cant save an image");
			}
		    
		} catch (IOException e) {
		    System.out.println("Cant read an image");
		}
		
	    

	}

	}
