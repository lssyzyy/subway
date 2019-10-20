package zyy.control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import zyy.model.Beanlujing;
import zyy.model.Beansubway;
import zyy.ui.FrmSearchLine;
import zyy.util.*;

public class station {
    public static Set<List<Beansubway>> lineSet = new HashSet<List<Beansubway>>();//所有线集合
    public static List<Beanlujing> AllLine = new ArrayList<Beanlujing>();//所有线集合
    public static Map<String, List<Beansubway>> map = new HashMap<String, List<Beansubway>>();
    public static List<Beansubway> mapOfStation = new ArrayList<Beansubway>();//存放所有站点
    public static int totalStaion = 0;//总的站点数量

	public static void writeStationFile(String line) {
        try {
            File writeName = new File("station.txt"); 
            writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
            try (FileWriter writer = new FileWriter(writeName);
                 BufferedWriter out = new BufferedWriter(writer)
            ) {
            	if(line.equals("1号线")) {
                out.write("刘园--->西横堤--->果酒厂--->本溪路--->勤俭道--->洪湖里--->西站--->西北角--->西南角--->二纬路--->海光寺--->鞍山道--->营口道--->小白楼--->下瓦房--->南楼--->土楼--->陈塘庄--->复兴门--->华山里--->财经大学--->双林--->李楼\r\n"); // \r\n即为换行
                out.flush();
            	}
            	else if(line.equals("2号线")) {
                    out.write("曹庄--->卞兴--->芥园西道--->咸阳路--->长虹公园--->广开四马路--->西南角--->鼓楼--->东南角--->建国道--->天津站--->远洋国际中心--->顺驰桥--->靖江路--->翠阜新村--->屿东城--->登州路--->国山路--->空港经济区--->滨海国际机场\r\n");
                    out.flush();
                }
            	else if(line.equals("3号线")) {
                    out.write("小淀--->丰产河--->华北集团--->天士力--->宜兴埠--->张兴庄--->铁东路--->北站--->中山路--->金狮桥--->天津站--->津湾广场--->和平路--->营口道--->西康路--->吴家窖--->天塔--->周邓纪念馆--->红旗南路--->王顶堤--->华苑--->大学城--->高新区--->学府工业区--->杨伍庄--->南站\r\n");
                    out.flush();
                }
            	else if(line.equals("5号线")) {
                    out.write("北辰科技园北--->丹河北道--->北辰道--->职业大学--->淮河道--->辽河北道--->宜兴埠北--->张兴庄--->志成路--->思源道--->建昌道--->金钟河大街--->月牙河--->幸福公园--->靖江路--->成林道--->津塘路--->直沽--->下瓦房--->西南楼--->文化中心--->天津宾馆--->肿瘤医院--->体育中心--->凌宾路--->昌凌路--->中医一附院--->李七庄南\r\n");
                    out.flush();
                }
            	else if(line.equals("6号线")) {
                    out.write("南孙庄--->南何庄--->大毕庄--->金钟街--->徐庄子--->金钟河大道--->民权门--->北宁公园--->北站--->新开河--->外院附中--->天泰路--->北运河--->北竹林--->西站--->复兴路--->人民医院--->长虹公园--->宜宾道--->鞍山西道--->天拖--->一中心医院--->红旗南路--->迎风道--->南翠屏--->水上公园东路--->肿瘤医院--->天津医院--->文化中心--->乐园道--->尖山路--->黑牛城道--->梅江道--->梅江会展中心--->解放南路--->洞庭路--->梅林路\r\n");
                    out.flush();
                }else if(line.equals("9号线")) {
                    out.write("天津站--->大王庄--->十一经路--->直沽--->东兴路--->中山门--->一号桥--->二号桥--->张贵庄--->新立,东丽开发区--->小东庄--->军粮城--->钢管公司--->胡家园--->塘沽--->泰达--->市民广场--->太湖路--->会展中心--->东海路\r\n");
                    out.flush();
                }else {
                	out.write("输入错误\r\n");
                    out.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	public static void setStations(List<Beansubway> linemodel, String str) {
        String line=str;
        String[] line1Arr = line.split(",");
        int flag=0;
        String linename = null;
        Beanlujing newline=new Beanlujing();
        for(String s : line1Arr){
            if(flag==0){
                linename=s;
                newline.setName(linename);
            }
            else{
            	Beansubway a=new Beansubway(s);
                for(List<Beansubway> lineP : station.lineSet){
                    if(lineP.contains(a)){
                    	station.totalStaion -=1;//在其他线路出现过就减1站，不然会重复计算
                        break;
                    }
                }
                mapOfStation.add(a);
                a.setLine(linename);
                linemodel.add(a);
            }
            flag=1;
        }
        for(int i =0;i<linemodel.size();i++){
            if(i<linemodel.size()-1){
                linemodel.get(i).next = linemodel.get(i+1);
                linemodel.get(i+1).prev = linemodel.get(i);
            }
        }
        newline.setPassStations(linemodel);
        AllLine.add(newline);
        map.put(linename,linemodel);
    }
    static {
        for(int i=0;i<Dijkstra.lines.size();i++){
            List<Beansubway> line = new ArrayList<Beansubway>();
            setStations(line,Dijkstra.lines.get(i));
            totalStaion += line.size();
            lineSet.add(line);
        }
    }
}
