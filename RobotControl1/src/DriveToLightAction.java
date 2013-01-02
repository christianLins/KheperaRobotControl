
public class DriveToLightAction extends Action {

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
	
	
	float left = 0;
    float right = 0;
    
    boolean isInitDone;

	private int noLightCounter = 0;
	
	
	public DriveToLightAction(ActionContext context) {
		super(context);
	}
	
	private void init() {
		if(!getSensorManager().getBrightestLightValue().isFront()) {
			getActionContect().addTemporarySubAction(new DriveAroundBallAction(getActionContect()));
		}
		isInitDone = true;
	}

	public DriveToLightAction(ActionContext context, int threshold) {
		super(context);
	}
	

	@Override
	public void doRealAction() {
		if(!isInitDone) {
			init();
		}
		
		if(!isTarget()){
			if(!getSensorManager().isLightInFront(1)){
				if(noLightCounter++ >= 15) {
					Sensor brightestLightValue = getSensorManager().getBrightestLightValue();
					if(brightestLightValue.isLeft()) {
						getMotionManager().goToLeft();
					} else {
						getMotionManager().goToRight();
					}
					noLightCounter = 0;
					return;
				}
			} 
			float[] lvtLightVector = getSensorManager().getLvtLightVector();
			
			for (int i = 0; i < lvtLightVector.length; i++) {
	            left += controlMatrix[0][i] * lvtLightVector[i];
	            right += controlMatrix[1][i] * lvtLightVector[i];
		     }
			
			 int finalLeft = (int)(BASE_SPEED_TERM + (left) * PROP_BASE_SPEED);
		     int finalRight = (int)(BASE_SPEED_TERM + (right)* PROP_BASE_SPEED);
		    
		     
		     getMotionManager().setMotorSpeeds(finalLeft, finalRight);
		}
		else{
			System.out.println("Found light - action done");
			getMotionManager().stop();
			actionDone();
		}
	}
	
	private boolean isTarget() {
		return getSensorManager().isLightInFront(30);
	}


}
