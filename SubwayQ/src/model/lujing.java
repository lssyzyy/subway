package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class lujing {
	private station start;//起点
    private station end;//终点
    private Double distance = 0.0D;//站点距离
    private List<station> passStations = new ArrayList<>();//经过的站点
	public station getStart() {
		return start;
	}
	public void setStart(station start) {
		this.start = start;
	}
	public station getEnd() {
		return end;
	}
	public void setEnd(station end) {
		this.end = end;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	public List<station> getPassStations() {
		return passStations;
	}
	public void setPassStations(List<station> passStations) {
		this.passStations = passStations;
	}
	
}
