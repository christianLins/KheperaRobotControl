import java.util.HashMap;
import java.util.Map;

import edu.wsu.KheperaSimulator.RobotController;


public class SensorManager {

	/**
	 * controller to access sensor values etc.
	 */
	private RobotController ctrl;
		
	private Map<String,Sensor> sensors;

	public SensorManager(RobotController controller) {
		this.ctrl = controller;
		init();
	}

	/**
	 * init roboter sensors
	 */
	private void init() {
		if(ctrl == null) return;
		
		sensors = new HashMap<String, Sensor>();
		Sensor left = new Sensor(0, "left");
		left.setSideFactor(-0.5);
		sensors.put(left.getName(), left);
		
		Sensor leftFront = new Sensor(1, "leftFront");
		leftFront.setSideFactor(-0.3);
		sensors.put(leftFront.getName(), leftFront);
		
		Sensor frontLeft = new Sensor(2, "frontLeft");
		frontLeft.setSideFactor(-0.1);
		sensors.put(frontLeft.getName(), frontLeft);
		
		Sensor frontRight = new Sensor(3, "frontRight");
		frontRight.setSideFactor(0.1);
		sensors.put(frontRight.getName(), frontRight);
		
		Sensor rightFront = new Sensor(4, "rightFront");
		rightFront.setSideFactor(0.3);
		sensors.put(rightFront.getName(), rightFront);
		
		Sensor right = new Sensor(5, "right");
		left.setSideFactor(0.5);
		sensors.put(right.getName(), right);
		
		Sensor rearRight = new Sensor(6, "rearRight");
		rearRight.setSideFactor(-0.2);
		rearRight.setFront(false);
		sensors.put(rearRight.getName(), rearRight);
		
		Sensor rearLeft = new Sensor(7, "rearLeft");
		rearLeft.setSideFactor(0.2);
		rearLeft.setFront(false);
		sensors.put(rearLeft.getName(), rearLeft);
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
	
	/**
	 * the highest value is the darkest value in reality!
	 * @return highest sensor value
	 */
	public Sensor getHighestLightValue() {
		Sensor max = null;
		for(Sensor s : sensors.values()) {
			if(max == null) max = s;
			if(s.getLightValue() > max.getLightValue()) max = s;
		}
		return max;
	}
	
	/**
	 * update all sensor values
	 */
	public void update() {
		for(Sensor s : sensors.values()) {
			int lightValue = 0;
			try {
				lightValue = ctrl.getLightValue(s.getId());
				s.setLightValue(lightValue);
				int distanceValue = ctrl.getDistanceValue(s.getId());
				s.setValueDistance(distanceValue);
				System.out.println("Updated sensor " + s.getName() + " to light value" + s.getLightValue() + " and to dist value " + s.getValueDistance());
			} catch(Exception e) {
				System.out.println("Problems during sensor updating " + s.getId());
			}
		}
	}

	/**
	 * the lowest value is the brightest value in reality!
	 * @return lowest sensor value
	 */
	public Sensor getLowestLightValue() {
		Sensor min = null;
		for(Sensor s : sensors.values()) {
			if(min == null) min = s;
			if(s.getLightValue() < min.getLightValue()) min = s;
		}
		return min;
	}

	/**
	 * the min value is the max distance in reality!
	 * @return min sensor value
	 */
	public Sensor getMinDistanceValue() {
		Sensor min = null;
		for(Sensor s : sensors.values()) {
			if(min == null) min = s;
			if(s.getValueDistance() < min.getValueDistance()) min = s;
		}
		return min;
		
	}

	/**
	 * the max value is the min distance in reality!
	 * @return max sensor value
	 */
	public Sensor getMaxDistanceValue() {
		Sensor max = null;
		for(Sensor s : sensors.values()) {
			if(max == null) max = s;
			if(s.getValueDistance() > max.getValueDistance()) max = s;
		}
		return max;
	}
	
}
