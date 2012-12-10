
public class GoToLightProportional extends Action {
	
	private float[][] controlMatrix = new float[][] { 
			new float[] { 0.0f,	 6.50f, 60f, 	60f, 	60f, 	60f},
			new float[] { 6.25f, 0.00f,	-6.50f, -6.50f, -6.50f, -6.50f, } 
			};

	public GoToLightProportional(ActionContext context) {
		super(context);
	}

	@Override
	public void doAction() {
		float left = 0;
		float right = 0;
		
		float[] sensors = getSensorManager().getSensorLightVector();

		for (int i = 0; i < sensors.length; i++) {
			left += (sensors[i]) * controlMatrix[0][i];
			right += (sensors[i]) * controlMatrix[1][i];
		}

		float max = Math.abs(Math.max(left, right));
		
		getMotionManager().scaleMotorSpeedByBaseSpeed(left/max, right/max);
	}

}
