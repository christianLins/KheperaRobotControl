import edu.wsu.KheperaSimulator.RobotController;


public class MotionManager {
	
	private RobotController ctrl;
	private int baseSpeed = 5;
	
	public MotionManager(RobotController controller) {
		this.ctrl = controller;
	}
	
	public void setBaseSpeed(int baseSpeed) {
		this.baseSpeed = baseSpeed;
	}
	
	public void setMotorSpeed(int left, int right) {
		ctrl.setMotorSpeeds(left, right);
	}
	
	public void goForward() {
		System.out.println("Go forward");
		ctrl.setMotorSpeeds(baseSpeed, baseSpeed);
	}
	
	public void turnLeft() {
		System.out.println("Turn left");
		ctrl.setMotorSpeeds(-baseSpeed, baseSpeed);
	}
	
	public void turnRight() {
		System.out.println("Turn right");
		ctrl.setMotorSpeeds(baseSpeed, -baseSpeed);
	}
	
	public void goToLeft() {
		System.out.println("Go left");
		ctrl.setMotorSpeeds(0, baseSpeed);
	}
	
	public void goToRight() {
		System.out.println("Go right");
		ctrl.setMotorSpeeds(baseSpeed, 0);
	}

	public void stop() {
		ctrl.setMotorSpeeds(0, 0);
		
	}
	
	
	

}
