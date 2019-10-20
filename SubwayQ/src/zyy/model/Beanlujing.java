package zyy.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import zyy.control.station;


public class Beanlujing {
    private String name;
    private List<Beansubway> passStations;//经过的站点
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Beansubway> getPassStations() {
		return passStations;
	}
	public void setPassStations(List<Beansubway> passStations) {
		this.passStations = passStations;
	}
	 public List<String> loadAllLineName(){//罗列所有线路名字
	        List<String> list=new ArrayList<>();
	        for(int i=0;i<station.AllLine.size();i++){
	            list.add(station.AllLine.get(i).getName());
	        }
	        return list;
	    }
}

