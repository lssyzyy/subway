package zyy.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import zyy.control.Dijkstra;
import zyy.control.station;
import zyy.model.Beansubway;
import zyy.util.BaseException;

public class FrmMain extends JDialog implements ActionListener{
	private JMenuBar menubar=new JMenuBar();
	private JLabel label=new JLabel();
	private JPanel workPane = new JPanel();
	private JMenu menuSubway=new JMenu("天津地铁管理");
	private JMenuItem menuitemLine=new JMenuItem("线路搜索");
	private JMenuItem menuitemStation=new JMenuItem("最短路径搜索");
	public FrmMain() {
		this.setTitle("天津地铁线路");
		this.setSize(400, 300);
		workPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        label=new JLabel("总站点数: "+ station.totalStaion);
        workPane.add(label);
		menuSubway.add(menuitemLine);
		menuitemLine.addActionListener(this);
		menuSubway.add(menuitemStation);
		menuitemStation.addActionListener(this);
		menubar.add(menuSubway);
		menubar.add(workPane);
		this.setJMenuBar(menubar);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);
		this.addWindowListener(new WindowAdapter(){   
			public void windowClosing(WindowEvent e){ 
			    System.exit(0);
		    }
		    });
			    this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.menuitemLine){
			FrmSearchLine dlg=new FrmSearchLine(this,"线路搜索",true);
			dlg.setVisible(true);
		}
		if(e.getSource()==this.menuitemStation){
			FrmSearchStation dlg=new FrmSearchStation(this,"最短路径搜索",true);
			dlg.setVisible(true);
		}
	}
	
}
