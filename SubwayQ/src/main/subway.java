package main;

import java.io.File;
import java.io.IOException;

import control.Dijkstra;
import control.distance;
import model.lujing;
import model.station;


public class subway {
	public static void main(String[] args) throws IOException {
		distance d=new distance();
	       switch (args[0]){
	           case "-map":
	               if(args.length==2){
	                   distance.readdis=System.getProperty("user.dir") + File.separator + "\\" + args[1];
	                   distance.readinformation();
	                   System.out.println("成功读取subway.txt文件");
	               }else{
	                   System.out.println("读取错误");
	               }
	               break;

	           case "-a":
	               if(args.length==6){
	            	   distance.readdis = System.getProperty("user.dir") + File.separator + "\\" + args[3];
	            	   distance.writedis = System.getProperty("user.dir") + File.separator + "\\" + args[5];
	                   distance.readinformation();
	                   distance.writeLineData(args[1]);
	                   System.out.println("线路站点：");
	                   d.readwj();
	               }else{

	                   System.out.println("读取错误");
	               }
	               break;
	           case "-b":

	               if(args.length==7){
	            	   distance.readdis = System.getProperty("user.dir") + File.separator + "\\" + args[4];
	                   distance.writedis = System.getProperty("user.dir") + File.separator + "\\" + args[6];
	                   distance.readinformation();
	                   lujing l = Dijkstra.calculate(new station(args[1]), new station(args[2]));
	                   d.shortline(l);
	                   System.out.println("最短线路：");
	                   d.readshort();
	               }else{
	                   System.out.println("读取错误");
	               }
	               break;
	       }
	    }
}
