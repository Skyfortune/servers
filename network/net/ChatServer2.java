package net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ChatServer2 {

	Vector<ClientThread2> vc;
	ServerSocket server;
	int port = 8002;

	public ChatServer2() {
		try {
			vc = new Vector<ClientThread2>();
			server = new ServerSocket(port);
		} catch (Exception e) {
			System.err.println("Error in Server");
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("*Chat Server 2.0*****************");
		System.out.println("*클라이언트 접속을 기다리고 있습니다*");
		System.out.println("*********************************");

		try {
			while (true) {
				Socket sock = server.accept();
				ClientThread2 ct = new ClientThread2(sock);
				ct.start();
				vc.addElement(ct);
			}
		} catch (Exception e) {
			System.err.println("Error in Socket");
			e.printStackTrace();
		}
	}

	public void sendAllMessage(String msg) {
		for (int i = 0; i < vc.size(); i++) {
			ClientThread2 ct = vc.elementAt(i);
			ct.sendMessage(msg);
		}
	}

	public void removeClient(ClientThread2 ct) {
		vc.remove(ct);
	}

	// 접속된 모든 id 리스트 리턴 ex)aaa; bbb; ccc; ddd; 홍길동;
	public String getIds() {
		String ids = "";
		for (int i = 0; i < vc.size(); i++) {
			ClientThread2 ct = vc.get(i);
			ids += ct.id + ";";
		}
		return ids;
	}

	// 매개변수 id값으로 ClientThread2를 검색
	public ClientThread2 findClient(String id) {
		ClientThread2 ct = null;
		for (int i = 0; i < vc.size(); i++) {
			ct = vc.get(i);
			if (ct.id.equals(id)) {// 매개변수 id와 Client의 id와 같다면...
				break;
			}

		}
		return ct;
	}

	class ClientThread2 extends Thread {

		// 4개의 필드 생성
		Socket sock;
		BufferedReader in;
		PrintWriter out;
		String id;

		public ClientThread2(Socket sock) {
			try {
				this.sock = sock;
				in = new BufferedReader(new InputStreamReader(sock.getInputStream() ));
				out = new PrintWriter(sock.getOutputStream(), true);
				// sock=>sock.toString() 호출
				System.out.println(sock + "접속됨...");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			try {
				out.println("사용하실 아이디를 입력하세요");
				while(true) {
					String line = in.readLine();
					if(line == null)
						break;
					else
						routine(line);
				}
			} catch (Exception e) {
				removeClient(this);
				System.err.println(sock + "[" + id + "] 끊어짐...");
				e.printStackTrace();
			}
		}

		public void routine(String line) {
			System.out.println("line : " + line);
			//chatall:오늘은 목요일 입니다. 
			int idx = line.indexOf(':');
			String cmd = line.substring(0, idx); //CHATALL
			String data = line.substring(idx+1); //오늘은 목요일입니다.
			if(cmd.equals(ChatProtocol2.ID)) {	//ID:aaa
				id=data;
				//새로운 접속자가 추가 되었기 때문에 리스트 재전송
				sendAllMessage(ChatProtocol2.CHATLIST+":"+getIds());
				//모든 접속자에게 welcome 메세지 전송
				sendAllMessage(ChatProtocol2.CHATALL+":"+"["+id+"] 님이 입장하였습니다.");
				
			}else if(cmd.equals(ChatProtocol2.CHAT)) { //CHAT:bbb; 밥먹자
				idx = data.indexOf(';');
				cmd /*bbb*/ = data.substring(0, idx);
				data /*밥먹자*/ = data.substring(idx+1);
				//id : bbb 찾아야함.
				ClientThread2 ct = findClient(cmd);
				if(ct!=null) {
					//상대방(bbb)
					ct.sendMessage(ChatProtocol2.CHAT+":"+"["+id+"(S)]"+data);
					//내자신(aaa)
					sendMessage(ChatProtocol2.CHAT+":"+"["+id+"(S)]"+data);
				}else {//내 자신에게
					sendMessage(ChatProtocol2.CHAT+":" + "[" + cmd + "]님이 접속자가 아닙니다.");
				}
			}else if(cmd.equals(ChatProtocol2.CHATALL)) {
				sendAllMessage(ChatProtocol2.CHATALL + ":" + "[" + id + "]" + data);
			}else if(cmd.equals(ChatProtocol2.MESSAGE)) {
				idx = data.indexOf(';');
				cmd = data.substring(0, idx);
				data = data.substring(idx+1);
				ClientThread2 ct = findClient(cmd);
				if(ct!=null) {
					ct.sendMessage(ChatProtocol2.MESSAGE+":" + id + ";" + data);
				}else {
					sendMessage(ChatProtocol2.CHAT+":"+"["+cmd+"]님이 접속자 아닙니다.");
				}
			}
		}

		public void sendMessage(String msg) {
			out.println(msg);
		}
	}

	public static void main(String[] args) {
		new ChatServer2();
		/*
		 * String str = "CHATALL:오늘은 목요일 입니다."; int idx = str.indexOf(':'); String cmd =
		 * str.substring(0, idx); String data = str.substring(idx+1);
		 * System.out.println(cmd); System.out.println(data);
		 */
	}

}
