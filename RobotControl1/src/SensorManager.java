import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.wsu.KheperaSimulator.RobotController;

/**
 * handles inputs
 * additional functions
 * - interpolation of sensor values
 * - pattern recognition (ball, wall,...)
 * @author Chris
 *
 */
public class SensorManager {

	/**
	 * controller to access sensor values etc.
	 */
	private RobotController ctrl;
		
	private Map<String,Sensor> sensors;
	private List<Sensor> sensorList;

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
		sensorList = new LinkedList<Sensor>();
		
		
		Sensor left = new Sensor(0, "left");
		left.setSideFactor(-0.5);
		addSensor(left);
		
		
		Sensor leftFront = new Sensor(1, "leftFront");
		leftFront.setSideFactor(-0.3);
		addSensor(leftFront);
		
		Sensor frontLeft = new Sensor(2, "frontLeft");
		frontLeft.setSideFactor(-0.1);
		addSensor(frontLeft);
		
		Sensor frontRight = new Sensor(3, "frontRight");
		frontRight.setSideFactor(0.1);
		addSensor(frontRight);
		
		Sensor rightFront = new Sensor(4, "rightFront");
		rightFront.setSideFactor(0.3);
		addSensor(rightFront);
		
		Sensor right = new Sensor(5, "right");
		left.setSideFactor(0.5);
		addSensor(right);
		
		Sensor rearRight = new Sensor(6, "rearRight");
		rearRight.setSideFactor(-0.2);
		rearRight.setFront(false);
		addSensor(rearRight);
		
		Sensor rearLeft = new Sensor(7, "rearLeft");
		rearLeft.setSideFactor(0.2);
		rearLeft.setFront(false);
		addSensor(rearLeft);
	}
	
	private void addSensor(Sensor sensor) {
		sensors.put(sensor.getName(), sensor);
		sensorList.add(sensor);
	}

	public float[] getSensorDistanceVector() {
		float[] matrix = new float[sensorList.size()];
		int i = 0;
		for(Sensor s : sensorList) {
			matrix[i++] = s.getDistanceValue();
		}		
		return matrix;
	}
	
	public float[] getSensorLightVector() {
		float[] matrix = new float[sensorList.size()];
		int i = 0;
		for(Sensor s : sensorList) {
//			System.out.println("light matrix " + i + " = " + s.getName());
			matrix[i++] = s.getLightValue();
		}		
		return matrix;
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
//				System.out.println("Updated sensor " + s.getName() + " to light value" + s.getLightValue() + " and to dist value " + s.getDistanceValue());
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
	public Sensor getFarestDistanceValue() {
		Sensor min = null;
		for(Sensor s : sensors.values()) {
			if(min == null) min = s;
			if(s.getDistanceValue() < min.getDistanceValue()) min = s;
		}
		return min;
		
	}

	/**
	 * the max value is the min distance in reality!
	 * @return max sensor value
	 */
	public Sensor getShortestDistanceValue() {
		Sensor max = null;
		for(Sensor s : sensors.values()) {
			if(max == null) max = s;
			if(s.getDistanceValue() > max.getDistanceValue()) max = s;
		}
		return max;
	}
	
	public boolean isWallOnLeftSide() {
		Sensor max = null;
		Sensor max2 = null;
		for(Sensor s : sensors.values()) {
			if(max == null) {
				max = s;
			} else if(max2 == null) {
				max2 = s;
			}			
			if(s.getDistanceValue() > max.getDistanceValue()) {
				max2 = max;
				max = s;
			} else if(max2 != null) {
				if(s.getDistanceValue() > max2.getDistanceValue()) max2 = s;
			}
		}
		if (((max == getLeft() && max2 == getRearLeft()) || (max2 == getLeft() && max == getRearLeft())) || 
				((max == getLeft() && max2 == getLeftFront()) || (max2 == getLeft() && max == getLeftFront()))) {
			System.out.println("wall is on left side");
			return true;
		}
		System.out.println("wall is NOT on left side - max sensors are " + max.getName() + " and " + max2.getName());
		return false;
	}
	
	public boolean isWallOnRightSide() {
		Sensor max = null;
		Sensor max2 = null;
		for(Sensor s : sensors.values()) {
			if(max == null) {
				max = s;
			} else if(max2 == null) {
				max2 = s;
			}			
			if(s.getDistanceValue() > max.getDistanceValue()) {
				max2 = max;
				max = s;
			} else if(max2 != null) {
				if(s.getDistanceValue() > max2.getDistanceValue()) max2 = s;
			}
		}
		if (((max == getRight() && max2 == getRearRight()) || (max2 == getRight() && max == getRearRight())) ||
				((max == getRight() && max2 == getRightFront()) || (max2 == getRight() && max == getRightFront()))) {
			System.out.println("Wall is on right side");
			return true;
		}
		System.out.println("wall is NOT on right side - max sensors are " + max.getName() + " and " + max2.getName());
		return false;
	}

	public boolean isBallInFront() {
		Sensor max = null;
		Sensor max2 = null;
		for(Sensor s : sensors.values()) {
			if(max == null) {
				max = s;
			} else if(max2 == null) {
				max2 = s;
			}			
			if(s.getDistanceValue() > max.getDistanceValue()) {
				max2 = max;
				max = s;
			} else if(max2 != null) {
				if(s.getDistanceValue() > max2.getDistanceValue()) max2 = s;
			}
		}
		if (((max == getFrontLeft() && max2 == getFrontRight()) || (max2 == getFrontRight() && max == getFrontLeft())) ) {
			System.out.println("Ball is in front");
			return true;
		}
		System.out.println("ball is NOT in front - max sensors are " + max.getName() + " and " + max2.getName());
		return false;
	}
	
}
