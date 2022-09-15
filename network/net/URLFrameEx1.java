package net;ㄹ

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
			// 사이트 접속과 동시에 html코드 ta에 append한다.
			connectHost(host);

		} else if (obj == save) {
			// ta에 문자열을 저장
			createFile(host, ta.getText());
		}

	}

	private void createFile(String host, String text) {
		//파일을 만든다.

	}

	public void connectHost(String host) {
		///호스트를 연결한다.
	}
	// 첫번째 매개변수는 파일명 지정

	public static void main(String[] args) {
		URLFrameEx1 ex = new URLFrameEx1();
	}
}
