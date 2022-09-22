package net;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;


public class ChatClient2 extends MFrame implements ActionListener, Runnable {

	Button bt1, bt2, bt3, bt4;
	TextField tf1, tf2, tf3;
	TextArea area;
	List list;
	Socket sock;
	BufferedReader in;
	PrintWriter out;
	String listTitle = "*******대화자명단*******";
	boolean flag = false;

	public ChatClient2() {
		super(450, 500);
		setTitle("ChatClient 2.0");
		Panel p1 = new Panel();
		p1.add(new Label("Host", Label.RIGHT));
		p1.add(tf1 = new TextField("127.0.0.1", 25));
		p1.add(new Label("Port", Label.RIGHT));
		p1.add(tf2 = new TextField("8002"));
		bt1 = new Button("connect");
		bt1.addActionListener(this);
		p1.add(bt1);
		add(BorderLayout.NORTH, p1);
		// //////////////////////////////////////////////////////////////////////////////////////////
		area = new TextArea("ChatClient 2.0");
		area.setBackground(Color.DARK_GRAY);
		area.setForeground(Color.PINK);
		area.setEditable(false);
		add(BorderLayout.CENTER, area);
		// /////////////////////////////////////////////////////////////////////////////////////////
		Panel p2 = new Panel();
		p2.setLayout(new BorderLayout());
		list = new List();
		list.add(listTitle);
		p2.add(BorderLayout.CENTER, list);
		Panel p3 = new Panel();
		p3.setLayout(new GridLayout(1, 2));
		bt2 = new Button("Save");
		bt2.addActionListener(this);
		bt3 = new Button("Message");
		bt3.addActionListener(this);
		p3.add(bt2);
		p3.add(bt3);
		p2.add(BorderLayout.SOUTH, p3);
		add(BorderLayout.EAST, p2);
		// ///////////////////////////////////////////////////////////////////////////////////////////
		Panel p4 = new Panel();
		tf3 = new TextField("", 50);
		tf3.addActionListener(this);
		bt4 = new Button("send");
		bt4.addActionListener(this);
		p4.add(tf3);
		p4.add(bt4);
		add(BorderLayout.SOUTH, p4);
		validate();
	}

	public void run() {

		try {
			String host = tf1.getText().trim();
			int port = Integer.parseInt(tf2.getText().trim());
			connect(host, port);
			//서버 -> 사용하실 아이디 입력하세요
			area.append(in.readLine()+"\n");
			while(true) {
				String line = in.readLine();
				if(line==null)
					break;
				else
					routine(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// --run

	public void routine(String line) {
		System.out.println("line: " + line);
		int idx = line.indexOf(':');
		String cmd = line.substring(0, idx);
		String data = line.substring(idx+1);
		if(cmd.equals(ChatProtocol2.CHATLIST)) {
			//data = aaa; bbb; ccc; 홍길동;
			list.removeAll(); //기존에 리스트 전부 삭제
			list.add(listTitle);
			StringTokenizer st = new StringTokenizer(data, ";");
			while(st.hasMoreTokens()) {
				list.add(st.nextToken());
			}
			
		}else if(cmd.equals(ChatProtocol2.CHAT) || 
				cmd.equals(ChatProtocol2.CHATALL)) {
			//CHATL[aaa(s)] 메세지 & CHATALL :[aaa]메세지
			area.append(data + "\n");
		}else if(cmd.equals(ChatProtocol2.MESSAGE)) {
			//data = bbb;밥묵자
			idx = data.indexOf(';');
			cmd = data.substring(0, idx); //bbb
			data = data.substring(idx+1); //밥묵자
			new Message("FROM:", cmd, data);
		}
	}// --routine

	//run() 메소드 호출 시키는 용도
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj==bt1) {//connect
			new Thread(this).start(); //run 메소드 호출 결과
			bt1.setEnabled(false);
			tf1.setEnabled(false);
			tf2.setEnabled(false);
			area.setText("");
		}else if(obj==bt2) {//save
			long fileName = System.currentTimeMillis();
			try {
				FileWriter fw = new FileWriter("net/" + fileName + ".txt");
				fw.write(area.getText());
				fw.close();
				area.setText("");
				new MDialog(this, "save", "대화내용을 저장 하였습니다.");
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}else if(obj==bt3) {//message
			//리스트에 현재 커서가 있는 위치값
			int idx = list.getSelectedIndex();
			if(idx==0||idx==-1) {
				new MDialog(this, "경고", "아이디를 선택하세요.");
			}else {
				new Message("TO:");
			}
		}else if(obj==bt4||obj==tf3) {//send
			String str = tf3.getText();
			if(filterMgr(str)) {
				new MDialog(this, "경고", "입력하신 글자는 금지어 입니다.");
				return;
			}
			if (!flag /* 아이디입력 */) {
				sendMessage(ChatProtocol2.ID+":"+str);
				setTitle(getTitle()+"-"+str+"님 반갑습니다.");
				area.setText("");
				tf3.setText("");
				tf3.requestFocus();
				flag = true;
			}else /*일반채팅*/{
				int idx = list.getSelectedIndex();
				if(idx==-1||idx==0) {
					sendMessage(ChatProtocol2.CHATALL+":"+str);
				}else /* 귓속말 대화*/{
					String id = list.getSelectedItem();
					sendMessage(ChatProtocol2.CHAT+ ":" +id + ";" +str);
				}
				tf3.setText("");
				tf3.requestFocus();
			}
				
		}
	}// --actionPerformed

	public void connect(String host, int port) {
		try {
			sock = new Socket(host, port);
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out = new PrintWriter(sock.getOutputStream(), true/* auto flush */);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// --connect

	public boolean filterMgr(String msg) {

		boolean flag = false;// false이면 금지어 아님
		String str[] = { "바보", "개새끼", "새끼", "자바", "java" };
		// msg : 하하 호호 히히
		StringTokenizer st = new StringTokenizer(msg);// 생략하면 구분자는 공백
		String msgs[] = new String[st.countTokens()];
		for (int i = 0; i < msgs.length; i++) {
			msgs[i] = st.nextToken();
		}
		for (int i = 0; i < str.length; i++) {
			if (flag)
				break;// 첫번째 for문 빠져나감.
			for (int j = 0; j < msgs.length; j++) {
				if (str[i].equalsIgnoreCase(msgs[j])) {
					flag = true;
					break; // 두번째 for문 빠져나감.
				} // if
			} // for2
		} // for1
		return flag;
	}

	public void sendMessage(String msg) {
		out.println(msg);
	}

	class Message extends Frame implements ActionListener {

		Button send, close;
		TextField name;
		TextArea ta;
		String mode;// to/from
		String id;

		public Message(String mode) {
			setTitle("쪽지보내기");
			this.mode = mode;
			id = list.getSelectedItem();
			layset("");
			validate();
		}

		public Message(String mode, String id, String msg) {
			setTitle("쪽지읽기");
			this.mode = mode;
			this.id = id;
			layset(msg);
			validate();
		}

		public void layset(String msg) {
			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					dispose();
				}
			});
			Panel p1 = new Panel();
			p1.add(new Label(mode, Label.CENTER));
			name = new TextField(id, 20);
			p1.add(name);
			add(BorderLayout.NORTH, p1);

			ta = new TextArea("");
			add(BorderLayout.CENTER, ta);
			ta.setText(msg);
			Panel p2 = new Panel();
			if (mode.equals("TO:")) {
				p2.add(send = new Button("send"));
				send.addActionListener(this);
			}
			p2.add(close = new Button("close"));
			close.addActionListener(this);
			add(BorderLayout.SOUTH, p2);

			setBounds(200, 200, 250, 250);
			setVisible(true);
		}

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == send) {
				sendMessage(ChatProtocol2.MESSAGE + ":" + id + ";" + ta.getText());
			}
			setVisible(false);
			dispose();
		}
	}

	class MDialog extends Dialog implements ActionListener {

		Button ok;
		ChatClient2 ct2;

		public MDialog(ChatClient2 ct2, String title, String msg) {
			super(ct2, title, true);
			this.ct2 = ct2;
			//////////////////////////////////////////////////////////////////////////////////////////
			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					dispose();
				}
			});
			/////////////////////////////////////////////////////////////////////////////////////////
			setLayout(new GridLayout(2, 1));
			Label label = new Label(msg, Label.CENTER);
			add(label);
			add(ok = new Button("확인"));
			ok.addActionListener(this);
			layset();
			setVisible(true);
			validate();
		}

		public void layset() {
			int x = ct2.getX();
			int y = ct2.getY();
			int w = ct2.getWidth();
			int h = ct2.getHeight();
			int w1 = 150;
			int h1 = 100;
			setBounds(x + w / 2 - w1 / 2, y + h / 2 - h1 / 2, 200, 100);
		}

		public void actionPerformed(ActionEvent e) {
			tf3.setText("");
			dispose();
		}
	}

	public static void main(String[] args) {
		new ChatClient2();
		
		/*
		 * System.setProperty("file.encoding","UTF-8"); 
		 * Field charset = Charset.class.getDeclaredField("defaultCharset");
		 * charset.setAccessible(true); charset.set(null,null);
		 */
		
		/*
		 * String str = "aaa;bbb;ccc;홍길동"; //의미가 있게끔 자르는 방법, StringTokenizer st = new
		 * StringTokenizer(str, ";"); while(st.hasMoreElements()) {
		 * System.out.println(st.nextToken()); }
		 */
	}
}
