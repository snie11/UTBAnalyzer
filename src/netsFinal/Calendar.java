package netsFinal;

public class Calendar {
	private int day;
	private int month;
	private int year;
	private int hour;
	private int minute;
	public Calendar(int y,int m, int d, int h, int mi) {
		year = y;
		month = m;
		day = d;
		hour = h;
		minute = mi;
	}
	public int getYear() {
		return year;
	}
	public int getDay() {
		return day;
	}
	public int getMonth() {
		return month;
	}		
	public int getHour() {
		return hour;
	}
	public int getMinute() {
		return minute;
	}
}
