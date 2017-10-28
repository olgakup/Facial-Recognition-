package controllers;

import org.opencv.core.Core;

public class Maindc {

	public static void main(String[] args) {
 		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		DetectController detectFace = new DetectController();
 		detectFace.detect();
	}

}
