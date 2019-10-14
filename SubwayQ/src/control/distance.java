package control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import model.lujing;
import model.station;

public class distance {
	public  static String readdis;
    public  static String writedis;
    
    public static LinkedHashSet<List<station>> lset = new LinkedHashSet<>();//所有地铁线路的集合
    public static HashMap<String,List<station>> ldata;//地铁线路数据
    
    public static void createlineData(){//读取线路时存储的信息
        ldata = new HashMap<>();
        for (List<station> stations : lset) {
            ldata.put(stations.get(1).getLname(),stations);
        }
    }
    public static String getLineName(station station){//通过站点名称获取线路信息
        createlineData();
        String startname = station.getSname();
        for (Map.Entry<String,List<station>> entry : ldata.entrySet()) {
            List<station> stations =  entry.getValue();
            for (station sta : stations){
                if(sta.getSname().equals(startname)){
                    return entry.getKey();
                }
            }
        }
        return "";
    }
    public static ArrayList<station> getLine(String l1,String l2){//获取线路信息
        ArrayList<station> l =  new ArrayList<station>();
        String[] a = l1.split(",");//每个站点之间用逗号分隔
        for (String s : a) {
            l.add(new station(s,l2));
        }
        return l;
    }
    public static String writeLineData(String lname){//写入线路数据
        createlineData();
        lname = lname.substring(0,1);
        List<station> a = ldata.get(lname);
        String lstr = a.stream().map(x->x.getSname()).collect(Collectors.joining(","));
        try {
            Files.write(Paths.get(writedis), lstr.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  lstr;
    }
    public static void shortline(lujing l){//输出最短线路
    	FileWriter a= null;
    	BufferedWriter b = null;
    	try {
    		a = new FileWriter(new File(writedis),true);
    	    b = new BufferedWriter(a);
    	    b.write((l.getPassStations().size()+1) + "\t\n");//写入总站数
    	    b.write(l.getStart().getSname() + "\t\n");//写入起点站
    	    String startlname = getLineName(l.getStart());//通过起点站名称得到线路名
    	    String c = startlname;
    	    for (station station : l.getPassStations()){
    	    	if(!c.equals(station.getLname())){
    	    		b.write(station.getLname()+"号线" + "\t\n");//写入转乘站名
    	            b.write(station.getSname()+ "\t\n");
    	            c = station.getLname();
    	            }else{
    	            	b.write(station.getSname() + "\t\n");
    	            	}
    	            }
    	            a.close();
    	            b.close();
    	            } catch (IOException e) {
    	        	e.printStackTrace();
    	            }
    	}
    public static void readinformation() {//读取文件
		File file = new File(readdis);
		BufferedReader reader = null;
		try {
			InputStreamReader i = new InputStreamReader(new FileInputStream(file), "UTF-8");
			reader = new BufferedReader(i);
			String l = null;
			String lname = "1";
			while ((l = reader.readLine()) != null) {
				if (l.trim().startsWith("*")) {
					String[] a = l.substring(1).split("-");
					lset.add(getLine(a[1].trim(), a[0].trim()));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
	}
    public void readwj(){//读取线路信息
		try {
			FileReader f = new FileReader("station.txt");
			BufferedReader b = new BufferedReader(f);
			String str=b.readLine();
			while(str!=null) {
				System.out.println(str);
				str=b.readLine();
			}
			f.close();
			b.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("文件不存在");
		}
		
    }
    public void readshort(){//读取最短路径
		try {
			FileReader f = new FileReader("routine.txt");
			BufferedReader b = new BufferedReader(f);
			String str=b.readLine();
			while(str!=null) {
				System.out.println(str);
				str=b.readLine();
			}
			f.close();
			b.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("文件不存在");
		}
		
    }
}
