
public class FindBallAction extends Action {
	
	
	private float[][] controlMatrix = new float[][] { 
			new float[] {0,0,0, 0,0,0, 0,0,0, 0,0,0, 0,0,0, 0,0,0, 0,0,0, 0,0,0, 0,0,0, 0,0,0, 0,0,
            		0.05f, 0.05f, 0.05f, 
            		0.1f, 0.1f, 0.1f,
            		0.015f, 0.015f, 0.015f,
            		0.02f, 0.02f, 0.02f,
            		0.025f, 0.025f, 0.025f, 
            		0.03f, 0.03f, 0.03f, 0.03f, 
            		0.035f, 0.035f, 0.035f,            		
            		0.04f, 0.04f, 0.04f, 
            		0.045f, 0.045f, 0.045f, 
            		0.05f, 0.05f, 0.05f, 
            		0.055f, 0.055f, 0.055f, 
            		0.06f, 0.06f, 0.06f    
            		 }, // left
            new float[] {0.06f, 0.06f, 0.06f, 
            		0.055f, 0.055f, 0.055f, 
            		0.05f, 0.05f, 0.05f, 
            		0.045f, 0.045f, 0.045f, 
            		0.04f, 0.04f, 0.04f, 
            		0.035f, 0.035f, 0.035f, 
            		0.03f, 0.03f, 0.03f, 0.03f, 
            		0.025f, 0.025f, 0.025f, 
            		0.02f, 0.02f, 0.02f, 
            		0.015f, 0.015f, 0.015f, 
            		0.1f, 0.1f, 0.1f, 
            		0.05f, 0.05f, 0.05f, 
            		0,0,0, 0,0,0, 0,0,0, 0,0,0, 0,0,0, 0,0,0, 0,0,0, 0,0,0, 0,0,0, 0,0,0, 0,0 }//right
            }; 
	
	private float PROP_BASE_SPEED = 9;
	private int BASE_SPEED_TERM = 1;
	
	private int deadlockMode = 0;
	

	public FindBallAction(ActionContext context) {
		super(context);
	}

	@Override
	public void doRealAction() {
		try{
		
		if(deadlockMode != 0) {
			doDeadLockAction();
			return;
		}
			
		float[] lvtBallVector = getSensorManager().getLvtBallVector();
		
//		System.out.println("ball vector length = " + lvtBallVector.length + "\n" + lvtBallVector);
		
		float left = 0;
	    float right = 0;
	     
	    boolean findBall = false;
	    
	     
	     
	     for (int i = 0; i < lvtBallVector.length; i++) {
	    	 boolean visibleBall = (lvtBallVector[i] == 1);
	    	 if(findBall && visibleBall) {
	    		 // go on
	    	 } else if(findBall && !visibleBall) {
	    		 // end of ball --> concentrate on current ball (left side)
//	    		 break;
	    	 } else if(!findBall && visibleBall) {
	    		 // ball found
	    		 findBall = true;
	    	 } else if(!findBall && !visibleBall) {
	    		 // go on (nothing found)
	    	 }
	    	 
	            left += controlMatrix[0][i] * lvtBallVector[i];
	            right += controlMatrix[1][i] * lvtBallVector[i];
	     }
	     
//	     System.out.println("left = " + left + "; right = " + right);
	     System.out.println("Find Ball Action");
	     
	     if(!findBall) {
	    	 // try something different or stop
	    	 
	    	 System.out.println("DEAD LOCK");
	    	 deadlockMode = 1;
	    	 doDeadLockAction();
	     } else if(getSensorManager().isBallInFront()) {
	    	 actionDone();
	     } else {
	    	 int finalLeft = (int)(BASE_SPEED_TERM + (left) * PROP_BASE_SPEED);
		     int finalRight = (int)(BASE_SPEED_TERM + (right)* PROP_BASE_SPEED);
		    
		     
		     getMotionManager().setMotorSpeeds(finalLeft, finalRight);
	     }
	     
		
		
		} catch (Exception e) {
			e.printStackTrace();
			
		}

	}

	private void doDeadLockAction() {
		if(deadlockMode == 1) {
	   		getMotionManager().driveBack();
   			nextDeadLockMode();
	   	 } else if(deadlockMode == 2) {
	   		 getMotionManager().turnLeft();
	   		 if (getSensorManager().isObjectInBack()) {
	   			 nextDeadLockMode();
	   		 }
	   	 }
		System.out.println("Deadlockmode = " + deadlockMode);
	}

	private void nextDeadLockMode() {
		deadlockMode = (deadlockMode + 1) % 3;		
	}


}
