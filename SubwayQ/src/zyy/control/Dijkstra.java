package zyy.control;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import zyy.model.Beanlujing;
import zyy.model.Beansubway;


public class Dijkstra {
	public static List<String> lines = new ArrayList<>();
	public static List<String> route = new ArrayList<>();
	private List<Beansubway> outList = new ArrayList<Beansubway>();//记录已经分析过的站点
	
	public void calculate(Beansubway s1, Beansubway s2){//计算从s1站到s2站的最短经过路径
    String line="初始线";
    if(outList.size() == station.totalStaion){
    	
        route.add("找到目标站点："+s2.getStation()+"，共经过"+(s1.getAllPassedStations(s2).size()-1)+"站\n");
        int flag=0;
        for(Beansubway station : s1.getAllPassedStations(s2)){
            if(station.getLine()==null){//出发站
                route.add(station.getStation()+"-->");
            }
            else if(station.getStation().equals(s2.getStation())){//最后1站
                if(!station.getLine().equals(line)){
                    route.add("换乘"+station.getLine()+"\t\n"+"-->"+"到达 "+"-->"+station.getStation());
                }
                else {
                    route.add("到达 " +"-->"+ station.getStation());
                }
            }
            else if(!station.getLine().equals(line)&&flag==1){//换乘后1站
                line=station.getLine();
                route.add("换乘"+station.getLine()+"\t\n"+"-->"+station.getStation()+"-->");
            }
            else if(!station.getLine().equals(line)&&flag==0){//第2站
                line=station.getLine();
                route.add("乘坐"+station.getLine()+"\t\n"+"-->"+station.getStation()+"-->");
                flag=1;
            }
            else{//其余站
                line=station.getLine();
                route.add(station.getStation()+"-->");
            }
        }
        return;
    }
    if(!outList.contains(s1)){
        outList.add(s1);
    }
    //如果起点站的OrderSetMap为空，则第一次用起点站的前后站点初始化之
    if(s1.getOrderSetMap().isEmpty()){
        List<Beansubway> Linkedstations = getAllLinkedStations(s1);
        for(Beansubway s : Linkedstations){
            s1.getAllPassedStations(s).add(s);
        }
    }
    Beansubway parent = getShortestPath(s1);//获取距离起点站s1最近的一个站（有多个的话，随意取一个）
    if(parent == s2){
        System.out.println("找到目标站点："+s2+"，共经过"+(s1.getAllPassedStations(s2).size()-1)+"站");
        for(Beansubway station : s1.getAllPassedStations(s2)){
            System.out.print(station.getStation()+"->");
        }
        return;
    }
    for(Beansubway child : getAllLinkedStations(parent)){
        if(outList.contains(child)){
            continue;
        }
        int shortestPath = (s1.getAllPassedStations(parent).size()-1) + 1;//前面这个1表示计算路径需要去除自身站点，后面这个1表示增加了1站距离
        if(s1.getAllPassedStations(child).contains(child)){
            //如果s1已经计算过到此child的经过距离，那么比较出最小的距离
            if((s1.getAllPassedStations(child).size()-1) > shortestPath){
                //重置S1到周围各站的最小路径
                s1.getAllPassedStations(child).clear();
                s1.getAllPassedStations(child).addAll(s1.getAllPassedStations(parent));
                s1.getAllPassedStations(child).add(child);
            }
        } else {
            //如果s1还没有计算过到此child的经过距离
            s1.getAllPassedStations(child).addAll(s1.getAllPassedStations(parent));
            s1.getAllPassedStations(child).add(child);
        }
    }
    outList.add(parent);
    calculate(s1,s2);//重复计算，往外面站点扩展
}

//取参数station到各个站的最短距离，相隔1站，距离为1，依次类推
private Beansubway getShortestPath(Beansubway station){
    int minPatn = Integer.MAX_VALUE;
    Beansubway rets = null;
    for(Beansubway s :station.getOrderSetMap().keySet()){
        if(outList.contains(s)){
            continue;
        }
        LinkedHashSet<Beansubway> set  = station.getAllPassedStations(s);//参数station到s所经过的所有站点的集合
        if(set.size() < minPatn){
            minPatn = set.size();
            rets = s;
        }
    }
    return rets;
}

//获取参数station直接相连的所有站，包括交叉线上面的站
private List<Beansubway> getAllLinkedStations(Beansubway s){
    List<Beansubway> linkedStaions = new ArrayList<Beansubway>();
    for(List<Beansubway> line : station.lineSet){
        if(line.contains(s)){//如果某一条线包含了此站，注意由于重写了hashcode方法，只有name相同，即认为是同一个对象
        	Beansubway str = line.get(line.indexOf(s));
            if(str.prev != null){
                linkedStaions.add(str.prev);
            }
            if(str.next != null){
                linkedStaions.add(str.next);
            }
        }
    }
    return linkedStaions;
}
}