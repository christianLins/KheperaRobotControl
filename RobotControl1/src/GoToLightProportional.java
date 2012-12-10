
public class GoToLightProportional extends Action {
	
	private static final int LIGHT_MAX = 1200;
	private float[][] controlMatrix = new float[][] { 
			new float[] { 0.55f, 0.35f, 0.1f, 0f, 0f, 0f },
			new float[] { 0, 0, 0, 0.1f, 0.35f, 0.55f } 
			};

	public GoToLightProportional(ActionContext context) {
		super(context);
	}

	@Override
	public void doAction() {
		float left = 0;
		float right = 0;
		
		float[] sensors = getSensorManager().getSensorLightVectorFront();
		System.out.println("Sensor light vector front is "+ sensors.length +  " long");

		for (int i = 0; i < sensors.length; i++) {
			left += controlMatrix[0][i] * sensors[i];
			right += controlMatrix[1][i] * sensors[i];
		}

		left = left / LIGHT_MAX;
		right = right / LIGHT_MAX;
		
		getMotionManager().scaleMotorSpeedByBaseSpeed(left, right);
	}

}
