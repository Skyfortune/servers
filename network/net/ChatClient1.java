package net;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import io.MFrame;

public class ChatClient1 extends MFrame
implements ActionListener, Runnable{
	
	Button btn1, btn2;
	TextField tf1, tf2;
	TextArea ta;
	Panel p1, p2;
	BufferedReader in;
	PrintWriter out;
	int port = 8001;
	String id;
	
	public ChatClient1() {
		super(350,400);
		setTitle("MyChat 1.0");
		p1 = new Panel();
		p1.setBackground(new Color(100,200,100));
		p1.add(new Label("HOST ",Label.CENTER));
		p1.add(tf1 = new TextField("127.0.0.1",25));
		p1.add(btn1 = new Button("Connect"));
		
		p2 = new Panel();
		p2.setBackground(new Color(100,200,100));
		p2.add(new Label("CHAT ",Label.CENTER));
		p2.add(tf2 = new TextField("",25));
		p2.add(btn2 = new Button("SEND"));	
		
		tf1.addActionListener(this);
		tf2.addActionListener(this);
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		
		add(p1,BorderLayout.NORTH);
		add(ta=new TextArea());
		add(p2,BorderLayout.SOUTH);
		validate();//갱신
	}
	
	@Override
	//커넥션 버튼
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj==tf1||obj==btn1) {
			connect(tf1.getText().trim());
			tf1.setEnabled(false);
			btn1.setEnabled(false);
			tf2.requestFocus();
		}else if(obj==tf2||obj==btn2) {
			String str = tf2.getText().trim();
			if(str.length()==0)
				return; //메소드 종료
			if(id==null) { //최초 한번만 실행
				id = str;
				setTitle(getTitle() + " [" + id + "]");
				ta.setText("채팅을 시작합니다. \n" );
			}
			out.println(str); //서버로 입력한 문자열 보내는 코드
			tf2.setText("");
			tf2.requestFocus();
		}
		
	}//--actionPerformed
	
	@Override
	//서버로 부터 메세지가 들어오면 반응하는 기능 
	//apend 기능
	public void run() {
		try {
			while(true) {
				ta.append(in.readLine()+ "\n");
				tf2.requestFocus();
			}
			
		} catch (Exception e) {
			System.err.println("Error in run");
			e.printStackTrace();
			System.exit(1);
			
		}
	}//--run
	
	//send버튼
	public void connect(String host){
		try {
			Socket sock = new Socket(host,port);
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out = new PrintWriter(sock.getOutputStream(), true);
			//서버에서 최초로 보내는 메세지 전달
			ta.append(in.readLine() + "\n");
			tf2.requestFocus();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//쓰레드 시작 
		Thread t = new Thread(this);
		t.start();
	}//--connect
	
	public static void main(String[] args) {
		new ChatClient1();
	}
}
