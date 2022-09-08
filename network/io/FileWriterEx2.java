package io;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.jar.Attributes.Name;

public class FileWriterEx2 extends MFrame
implements ActionListener{
	
	TextArea ta;
	//처음에 회색
	TextField tf;
	//입력창
	Button save;
	//세이브 버튼
	
	public FileWriterEx2() {
		super(300, 400);
		setTitle("FileWriterFrameEx1");
		add(ta=new TextArea());
		Panel p = new Panel();
		p.add(tf = new TextField(30));
		p.add(save = new Button("SAVE"));
		ta.setEditable(false);
		tf.addActionListener(this);
		save.addActionListener(this);
		add(p,BorderLayout.SOUTH);
		validate();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj==tf) {
			ta.append(tf.getText() + "\n");
			tf.setText("");
			tf.requestFocus();
		}else if(obj==save) {
			saveFile(ta.getText());
			ta.setText("");
		}
	}
	
	public void saveFile(String str) {
		try {
			long name = System.currentTimeMillis();
			FileWriter fw = new FileWriter("io/"+name+".txt");
			fw.write(str);
			fw.flush();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public static void main(String[] args) {
		new FileWriterEx2();
		//매우 유니크한 파일명으로 좋다. 
		//1970.1.1 0시부터 ~ 현재까지 : 1초를 1,000단위로 계산
		long name = System.currentTimeMillis();
		System.out.println(name);
	}
}






