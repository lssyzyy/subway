package model;

import java.util.ArrayList;
import java.util.List;

public class station {
	private String sname; // 站点名称
	private String lname; // 所属线路名称
	private List<station> lstation = new ArrayList<>(); //相邻连接的站点
	

    public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public List<station> getLstation() {
		return lstation;
	}

	public void setLstation(List<station> lstation) {
		this.lstation = lstation;
	}

	public station(String sname, String lname) {
        this.sname = sname;
        this.lname = lname;
    }

    public station(String sname) {
        this.sname = sname;
    }
}
