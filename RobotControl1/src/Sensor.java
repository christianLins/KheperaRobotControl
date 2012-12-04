
public class Sensor {

	private final int id;	
	private String name;
	
	private int value;
	private double side;
	private boolean front = true;
	
	public Sensor(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Sensor(int id, String name, int value) {
		this(id, name);
		this.value = value;
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
		return value;
	}

	public void setValue(int value) {
		this.value = value;
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

	
	

}
