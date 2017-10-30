package controllers;

import java.io.IOException;

import org.opencv.core.Core;

public class Maindc {

	public static void main(String[] args) throws IOException {
 		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		DetectFController detectFace = new DetectFController();
 		detectFace.detect();
 		FourierTController transform = new FourierTController();
 		transform.dft();
 		transform.inverse();
	}

}
