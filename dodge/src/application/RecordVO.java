package application;

public class RecordVO {
	public double time;
	public String name;
	
	@Override
	public String toString() {
		return name + " [" + time + "]";
	}
}
