package org.usfirst.frc.team4215.robot.commands;

import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
//import org.usfirst.frc.team4215.robot.GripPipelineOh;

import edu.wpi.first.wpilibj.vision.VisionRunner;

public abstract class ListenerforVision implements VisionRunner.Listener<GripPipelineOh>{
	int IMG_WIDTH = 320;
	int IMG_HEIGHT = 240;
	double closedLoopPosBounds = 7*12;
	private final Object imgLock = new Object();
	Thread visionThread;
	double turn;
	boolean isFirsttime = true;
	double offSet;
	double centerX;
	
	public void copyPipelineOutputs(GripPipelineOh pipeline) {
		// TODO Auto-generated method stub
		 if (!pipeline.filterContoursOutput().isEmpty()) {
			 Rect r = null;
			 for (MatOfPoint mop : pipeline.filterContoursOutput()){
				 if (r==null){
					 r = Imgproc.boundingRect(mop);
				 }
				 else {
					 r = Merge(r, Imgproc.boundingRect(mop));
				 }
			 }	 

			 synchronized (imgLock) {
                centerX = r.x + (r.width / 2);
                offSet = centerX - (IMG_WIDTH / 2);
        		turn = offSet/IMG_WIDTH;
        		//System.out.println("o:" + offSet + ", t: "+ turn);
            }
	            
	     }
		 else {
			turn = 0.0;
 			//System.out.println("No Contours");
		 }		
	}
	private Rect Merge(Rect r, Rect boundingRect) {

		int left = Math.min(r.x, boundingRect.x);
		int top = Math.min(r.y, boundingRect.y);
		int right = Math.max(r.x + r.width, boundingRect.x + boundingRect.width);
		int bottom = Math.max(r.y + boundingRect.height, boundingRect.y + boundingRect.height);
		
		
		return new Rect(left, top, right - left, bottom - top);
	}
	

}

