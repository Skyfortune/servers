package io;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FileDialog;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.FileWriter;

public class FileCopyEx2 extends MFrame 
implements ActionListener{
	
	Button open, save;
	TextArea ta;
	FileDialog openDialog, saveDialog;
	String sourceDir;
	String sourceFile;
	
	public FileCopyEx2() {
		super(400,500);
		setTitle("FileCopyEx2");
		add(ta = new TextArea());
		Panel p = new Panel();
		p.add(open = new Button("OPEN"));
		p.add(save = new Button("SAVE"));
		ta.setEditable(false);
		open.addActionListener(this);
		save.addActionListener(this);
		add(p,BorderLayout.SOUTH);
		validate();
	}

	//버튼 클릭했을때 액션 이벤트
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj==open) {
			openDialog = new FileDialog(this, "파일열기", FileDialog.LOAD);
			openDialog.setVisible(true);
			String dir,file;
			dir = openDialog.getDirectory();
			file = openDialog.getFile();
			fileReader(dir+file);
		}else if(obj==save) {
			saveDialog = new FileDialog(this, "파일저장", FileDialog.SAVE);
			saveDialog.setVisible(true);
			String dir,file;
			dir = saveDialog.getDirectory();
			file = saveDialog.getFile();
			fileReader(dir+file);
		}
	}
	
	//왜 쪼개놓을지 고민을 할 필요가 있다.
	public void fileReader(String file){
		try {
			FileReader fr = new FileReader(file);
			int c;
			String s = "";
			while((c=fr.read())!=-1) {
				s+=(char)c;
			}
			ta.setText(s);
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void fileWriter(String file){
	try {
		FileWriter fw = new FileWriter(file);
		fw.write(ta.getText());
		for (int i = 5; i > 0; i--) {
			ta.setText("저장하였습니다 - " + i + "초 후에 사라집니다.");
			Thread.sleep(1000);//1초 단위로 wait
		}
		ta.setText("");
		fw.close();
	} catch (Exception e) {
		e.printStackTrace();
	}
	}
	
	public static void main(String[] args) {
		new FileCopyEx2();
	}
}



