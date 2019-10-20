package zyy.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Font;
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

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import zyy.control.Dijkstra;
import zyy.model.Beanlujing;
import zyy.control.station;
import zyy.model.Beansubway;
import zyy.util.BaseException;

public class FrmSearchStation extends JDialog implements ActionListener{
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnSearch = new Button("Search");
	private JLabel labelstart = new JLabel("起点：");
	private JLabel labelend= new JLabel("终点：");
	private JTextField edtstart = new JTextField(13);
	private JTextField edtend = new JTextField(13);
	private JTextArea edtsubway = new JTextArea(60,80);
	public FrmSearchStation(FrmMain f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(labelstart);
		toolBar.add(edtstart);
		toolBar.add(labelend);
		toolBar.add(edtend);
		toolBar.add(btnSearch);	
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		edtsubway.setFont(new Font("Monospaced", Font.BOLD, 14));
		edtsubway.setLineWrap(true);        //激活自动换行功能
		edtsubway.setWrapStyleWord(false); 
		workPane.add(edtsubway);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(800, 600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);
		this.validate();
		this.btnSearch.addActionListener(this);	
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				
			}
		});	
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		 if(e.getSource()==this.btnSearch){
			String s1=edtstart.getText();
			String s2=edtend.getText();
			try {
                int flag1=0,flag2=0;
                for(Beansubway s:station.mapOfStation) {
                    if(s1.equals(s.getStation())) {
                        flag1=1;
                    }
                    if(s2.equals(s.getStation())) {
                        flag2=1;
                    }
                }
                if(flag1==0) throw new BaseException("起点站不存在");
                if(flag2==0) throw new BaseException("终点站不存在");
                edtsubway.setText("");//清空JTextArea
                if(s1.equals(s2))
                	edtsubway.append("起点站不能与终点站相同");
                else {
                	Dijkstra sw = new Dijkstra();
                	Dijkstra.route.clear();//再次运行清空路线
                    sw.calculate(new Beansubway(s1), new Beansubway(s2));
                    for(String s:Dijkstra.route)
                    {
                    	edtsubway.append(s);
                    }
                }
                edtsubway.paintImmediately(edtsubway.getBounds());
	        } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
	            e1.printStackTrace();
	        }
	}
	}
}
