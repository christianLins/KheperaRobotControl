import java.util.HashMap;
import java.util.Map;

import edu.wsu.KheperaSimulator.RobotController;


public class SensorManager {

	private RobotController ctrl;
	
	
	private Map<String,Sensor> sensors;

	public SensorManager(RobotController controller) {
		this.ctrl = controller;
		init();
	}

	private void init() {
		if(ctrl == null) return;
		
		sensors = new HashMap<String, Sensor>();
		Sensor left = new Sensor(0, "left");
		left.setSide(-0.5);
		sensors.put(left.getName(), left);
		
		Sensor leftFront = new Sensor(1, "leftFront");
		leftFront.setSide(-0.3);
		sensors.put(leftFront.getName(), leftFront);
		
		Sensor frontLeft = new Sensor(2, "frontLeft");
		frontLeft.setSide(-0.1);
		sensors.put(frontLeft.getName(), frontLeft);
		
		Sensor frontRight = new Sensor(3, "frontRight");
		frontRight.setSide(0.1);
		sensors.put(frontRight.getName(), frontRight);
		
		Sensor rightFront = new Sensor(4, "rightFront");
		rightFront.setSide(0.3);
		sensors.put(rightFront.getName(), rightFront);
		
		Sensor right = new Sensor(5, "right");
		left.setSide(0.5);
		sensors.put(right.getName(), right);
		
		Sensor rearRight = new Sensor(6, "rearRight");
		rearRight.setSide(-0.2);
		rearRight.setFront(false);
		sensors.put(rearRight.getName(), rearRight);
		
		Sensor rearLeft = new Sensor(7, "rearLeft");
		rearLeft.setSide(0.2);
		rearLeft.setFront(false);
		sensors.put(rearLeft.getName(), rearLeft);
		
		update();
	}
	
	public Sensor getSensor(String location) {
		return sensors.get(location);
	}
	
	
	
	public Sensor getLeft() {
		return sensors.get("left");
	}
	
	public Sensor getLeftFront() {
		return sensors.get("leftFront");
	}
	
	public Sensor getFrontLeft() {
		return sensors.get("frontLeft");
	}
	
	public Sensor getFrontRight() {
		return sensors.get("frontRight");
	}

	public Sensor getRightFront() {
		return sensors.get("rightFront");
	}

	public Sensor getRight() {
		return sensors.get("right");
	}

	
	public Sensor getRearRight() {
		return sensors.get("rearRight");
	}

	
	public Sensor getRearLeft() {
		return sensors.get("rearLeft");
	}
	
	public Sensor getHighestLightValue() {
		Sensor max = null;
		for(Sensor s : sensors.values()) {
			if(max == null) max = s;
			if(s.getValue() > max.getValue()) max = s;
		}
		return max;
	}
	
	
	public void update() {
		for(Sensor s : sensors.values()) {
			int lightValue = 0;
			try {
				lightValue = ctrl.getLightValue(s.getId());
				s.setValue(lightValue);
				System.out.println("Updated sensor " + s.getName() + " to " + s.getValue());
			} catch(Exception e) {
				System.out.println("Problems during sensor updating " + s.getId());
			}
		}
	}

	public Sensor getLowestLightValue() {
		Sensor min = null;
		for(Sensor s : sensors.values()) {
			if(min == null) min = s;
			if(s.getValue() < min.getValue()) min = s;
		}
		return min;
	}
	
}
