package net;

import java.awt.Button;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;

import javax.swing.text.AbstractDocument.Content;

import io.MFrame;

public class URLFrameEx1 extends MFrame implements ActionListener {

	TextArea ta;
	TextField tf;
	Button connect;
	Button save;

	public URLFrameEx1() {
		super(500, 500);
		setTitle("ViewHost");
		Panel p = new Panel();
		p.add(tf = new TextField("http://", 40));
		p.add(connect = new Button("connect"));
		p.add(save = new Button("save"));
		ta = new TextArea();
		add("North", p);
		add("Center", ta);
		save.setEnabled(false);
		connect.addActionListener(this);
		save.addActionListener(this);
		tf.addActionListener(this);
		validate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		String host = tf.getText().trim();
		if (obj == tf || obj == connect) {
			ta.setText("");
			// 사이트 접속과 동시에 html코드 ta에 append한다.
			connectHost(host);
			save.setEnabled(true);
		} else if (obj == save) {
			// ta에 문자열을 저장
			createFile(host, ta.getText());
			save.setEnabled(false);
			tf.setText("http://");
			ta.setText("");
			ta.append("저장하였습니다.");
			tf.requestFocus();
		}

	}

	
	//독립적인 기능은 최대한 세부적으로 구현하기
	public void connectHost(String host) {
		///호스트를 연결한다.
		try {
			URL url = new URL(host);
			BufferedReader br = new BufferedReader(
					new InputStreamReader(url.openStream(), "UTF-8"));
			String line = "";
			while(true) {
				line = br.readLine();
				if(line==null) break;
				ta.append(line+"\n");
			}
					
			
		} catch (Exception e) {
			//e.printStackTrace();
			ta.append("해당되는 호스트가 없습니다.");
		}
		
	}
	
	//첫번째 매개변수는 파일명 지정
	private void createFile(String file, String content) {
		//파일을 만든다.
		try {
											//http:// 제외하고 파일이름 저장
			FileWriter fw = new FileWriter("net/"+file.substring(7)+".txt");
			fw.write(content);
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// 첫번째 매개변수는 파일명 지정

	public static void main(String[] args) {
		URLFrameEx1 ex = new URLFrameEx1();
	}
}
