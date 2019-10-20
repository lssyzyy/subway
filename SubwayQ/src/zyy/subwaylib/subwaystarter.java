package zyy.subwaylib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import zyy.control.Dijkstra;
import zyy.ui.FrmMain;

public class subwaystarter {

		public static void main(String[] args) throws IOException {
			// TODO Auto-generated method stub
			String str=null;
	        File file = new File("subway.txt");
	        BufferedReader in = new BufferedReader(new FileReader(file));
			while((str = in.readLine()) != null) {
				Dijkstra.lines.add(str);
			}
			in.close();
			new FrmMain();
		}
}
