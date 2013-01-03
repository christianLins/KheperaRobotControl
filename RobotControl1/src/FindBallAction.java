
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
	
	
	private float left = 0;
    private float right = 0;

	

	public FindBallAction(ActionContext context) {
		super(context);
	}

	@Override
	public void doRealAction() {
		try{	
			
		float[] lvtBallVector = getSensorManager().getLvtBallVector();
		
		
		left = 0;
	    right = 0;
	     
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
	     
	     if(!findBall) {
	    	 // try something different or stop
	    	 System.out.println("DEAD LOCK");
	    	 setDeadlock(true);
	    	 return;
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
	
	

}
