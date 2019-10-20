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
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import zyy.control.station;
import zyy.model.Beansubway;
import zyy.util.BaseException;

public class FrmSearchLine extends JDialog implements ActionListener{
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOK = new Button("OK");
	private JLabel labelline = new JLabel("查询线路（1号线、2号线、3号线、5号线、6号线、9号线）：");
	private JTextField edtline = new JTextField(13);
	private JTextArea edtsubway = new JTextArea(60,110);
	public FrmSearchLine(FrmMain f, String s, boolean b) {
		super(f, s, b);		
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(labelline);
		toolBar.add(edtline);
		toolBar.add(btnOK);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		edtsubway.setFont(new Font("Monospaced", Font.BOLD, 14));
		edtsubway.setLineWrap(true);        //激活自动换行功能
        edtsubway.setWrapStyleWord(false);  
		workPane.add(edtsubway);
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		
		this.setSize(1000, 600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);
		this.validate();
		this.btnOK.addActionListener(this);		
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				
			}
		});	
		}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.btnOK) {
     	    station.writeStationFile(edtline.getText());
            String pathname = "station.txt"; 
	        try (FileReader reader = new FileReader(pathname);
	             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
	        ) {
	            String line;
	            while ((line = br.readLine()) != null) {
	            	edtsubway.setText(line);
	            }
	        } catch (IOException e1) {
	            e1.printStackTrace();
	        }
		}
	}

}
