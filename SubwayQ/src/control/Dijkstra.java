package control;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import model.*;


public class Dijkstra {
    private static HashMap<station, lujing> h = new HashMap<>();
    private static List<station> l = new ArrayList<>();
    public static lujing calculate(station star, station end) {
        
        if (!l.contains(star)) {//将开始站点加入到分析过的站点集合。
            l.add(star);
        }
        
        if (star.equals(end)) {//如果开始站点等于终止站点，则设置result，设置距离和station。
            lujing lujing = new lujing();
            
            lujing.setDistance(0.0D);
            lujing.setEnd(star);
            lujing.setStart(star);
            return h.put(star, lujing);
        }
        
        if (h.isEmpty()) {//第一次调用calculate，且起始点和终止点不同，则h为空。
           
            List<station> ls = getLinkStations(star); //第一次调用获取起始点的相邻站点（在所有地铁线中，这里涉及转线交叉的周围站点）
            
            for (station station : ls) {//把相邻站点集合中的所有站点，加入h中。 因为相邻，则可以直接获取Distance。

                lujing lujing = new lujing();
                lujing.setStart(star);
                lujing.setEnd(station);
                String key = star.getSname() + ":" + station.getSname();
                lujing.setDistance(1.0);
                lujing.getPassStations().add(station);
                h.put(station, lujing);
            }
        }
        station parent = getNextStation();
       
        if (parent == null) { //如果h所有点keySet被分析完了，则返回的parent为null。
            lujing lujing = new lujing();
            lujing.setDistance(0.0D);
            lujing.setStart(star);
            lujing.setEnd(end);
            
            return h.put(end, lujing);//put方法的返回值就是value值。
        }
        
        if (parent.equals(end)) {//如果得到的最佳邻点与目标点相同，则直接返回最佳邻点对应的result对象。
            return h.get(parent);
        }


        //在路径经过点中加入parent后，更新h集合，要么起始点经过parent达到parent相邻点是最优的，要么起始点到parent相邻点不可达，而通过parent可达。
        //获取parent对象(最佳点)的相邻点。
        //分析一个parent最佳点后，把它的相邻点都会加入到h中，在下一次调用getNextStation获取h中未被标记且距离（起始点到该station的距离）最短。
        List<station> childLinkStations = getLinkStations(parent);
        //D：B C E
        for (station child : childLinkStations) {
            if (l.contains(child)) {
                continue;
            }
            String key = parent.getSname() + ":" + child.getSname();
            Double distance;

            if (parent.getSname().equals(child.getSname())) {
                distance = 0.0D;
            }

            Double parentDistance = h.get(parent).getDistance();
            distance = parentDistance+1.0;

            List<station> parentPassStations = h.get(parent).getPassStations();
            lujing childResult = h.get(child);
            if (childResult != null) {
                
                if (childResult.getDistance() > distance) {
                   
                    childResult.setDistance(distance); //如果通过最佳点比直接到距离小，则更新h中的对应result对象。
                    childResult.getPassStations().clear();
                    
                    childResult.getPassStations().addAll(parentPassStations);//路径更新为A->最佳点->child点。
                    childResult.getPassStations().add(child);
                }
            } else {
                
                childResult = new lujing();//如果在h中没有最佳点的相邻点，则往h中添加通过最佳点（初始为起始点的最佳邻点）到达该点。
                childResult.setDistance(distance);
                childResult.setStart(star);
                childResult.setEnd(child);
                childResult.getPassStations().addAll(parentPassStations);
                childResult.getPassStations().add(child);
            }
            h.put(child, childResult);
        }
        //初始时，即第一次调用该方法时，在分析点中加入起始点的最佳相邻点，后面嵌套调用时，就为获取某点的最佳邻点，在用最佳邻点更新h后，往l中加入最佳邻点。
        l.add(parent);
        //加入最佳邻点后，更新h，再次调用calculate
        return calculate(star, end);
        //或：
        // calculate(star, end); 继续往下走，选择最佳点，然后更新h。
        // return h.get(end);
    }

   
    public static List<station> getLinkStations(station station) { //传入起始点station对象。
        List<station> linkedStaions = new ArrayList<station>();

       for (List<station> line : distance.lset) {
            for (int i = 0; i < line.size(); i++) {//遍历每条地铁线，若地铁线中存在该站点，则判断，如果该站点位于地铁线的起始站，则相邻站为地铁线的第二个站点(i+1)，
                //如果该站点位于地铁线的最后一个站，则相邻站为地铁线的倒数第二个站点（i-1），
                //如果该站点位于地铁线的其余位置，则相邻站点为该站点前后位置（i-1/i+1）
                
                if (station.equals(line.get(i))) {
                    if (i == 0) {
                        linkedStaions.add(line.get(i + 1));
                    } else if (i == (line.size() - 1)) {
                        linkedStaions.add(line.get(i - 1));
                    } else {
                        linkedStaions.add(line.get(i + 1));
                        linkedStaions.add(line.get(i - 1));
                    }
                }
            }
        }
        return linkedStaions;
    }
    private static station getNextStation() {
        Double min = Double.MAX_VALUE;
        station rets = null;
        
        Set<station> stations = h.keySet();//获取h中的station集合。
        for (station station : stations) {
            
            if (l.contains(station)) {//如果该点被标记为“已被分析”（已被分析表示起始点到该点的最短路径已求出）
                continue;
            }
            
            lujing result = h.get(station);//循环分析h中未被标记的点，求出最短路径的result对象。
            if (result.getDistance() < min) {
                min = result.getDistance();
                //得到终点的station对象
                rets = result.getEnd();
            }
        }
        return rets;
    }

    private static double doubleAdd(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }
    public static void getResultToText(String filePath) throws IOException {
        if (filePath == null) {
            throw new FileNotFoundException("兄弟来个路径保存路径吧");
        }
        BufferedWriter writer = null;
        //追加路径信息...
        writer = new BufferedWriter(new FileWriter(filePath, true));
        Set<List<station>> lineSet = distance.lset;
        for (List<station> stations : lineSet) {
            for (station station : stations) {
                for (List<station> stations2 : lineSet) {
                    for (station stationTarget : stations2) {
                       Dijkstra d = new Dijkstra();
                        lujing lujing = d.calculate(station, stationTarget);
                        h = new HashMap<>();
                        l = new ArrayList<>();
                        for (station s : lujing.getPassStations()) {
                            if (s.getSname().equals(stationTarget.getSname())) {
                                String text = station.getSname() + "\t" + stationTarget.getSname() + "\t" + lujing.getPassStations().size() + "\t" + lujing.getDistance() + "\t";
                                for (station test : lujing.getPassStations()) {
                                    text = text + test.getSname() + ",";
                                }
                                writer.write(text);
                                writer.newLine();
                            }
                        }
                    }

                }
            }
        }
        writer.flush();
        writer.close();
    }
}
