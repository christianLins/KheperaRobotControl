
public class Sensor {

	private final int id;	
	private String name;
	
	private int valueLight = 1000;
	private int valueDistance = 1000;
	private double side = 1;
	private boolean front = true;
	
	public Sensor(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public int getValue() {
		return valueLight;
	}

	public void setValue(int value) {
		this.valueLight = value;
	}

	public double getSide() {
		return side;
	}
	
	public void setSide(double direction) {
		this.side = direction;
	}

	public boolean isFront() {
		return front;
	}

	/**
	 * rear or front sensor
	 * @param front
	 */
	public void setFront(boolean front) {
		this.front = front;
	}

	public int getValueDistance() {
		return valueDistance;
	}

	public void setValueDistance(int valueDistance) {
		this.valueDistance = valueDistance;
	}

	
	

}
