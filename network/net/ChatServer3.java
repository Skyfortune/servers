package net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Vector;

public class ChatServer3 {

	Vector<ClientThread3> vc;
	ServerSocket server;
	int port = 8003;
	ChatMgr3 mgr;
	MsgMgr3 mgr2;

	public ChatServer3() {
		try {
			vc = new Vector<ClientThread3>();
			server = new ServerSocket(port);
			mgr = new ChatMgr3();
			mgr2 = new MsgMgr3();
		} catch (Exception e) {
			System.err.println("Error in Server");
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("************************************************");
		System.out.println("***********Welcome Chat Server 3.0**************");
		System.out.println("***********클라이언트 접속을 기다리고 있습니다***********");
		System.out.println("************************************************");

		try {
			while (true) {
				Socket sock = server.accept();
				ClientThread3 ct = new ClientThread3(sock);
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
			ClientThread3 ct = vc.elementAt(i);
			ct.sendMessage(msg);
		}
	}

	public void removeClient(ClientThread3 ct) {
		vc.remove(ct);
	}

	// 접속된 모든 id 리스트 리턴 ex)aaa; bbb; ccc; ddd; 홍길동;
	public String getIds() {
		String ids = "";
		for (int i = 0; i < vc.size(); i++) {
			ClientThread3 ct = vc.get(i);
			ids += ct.id + ";";
		}
		return ids;
	}

	// 매개변수 id값으로 ClientThread3를 검색
	public ClientThread3 findClient(String id) {
		ClientThread3 ct = null;
		for (int i = 0; i < vc.size(); i++) {
			ct = vc.get(i);
			if (ct.id.equals(id)) {// 매개변수 id와 Client의 id와 같다면...
				break;
			}

		}
		return ct;
	}

	class ClientThread3 extends Thread {

		// 4개의 필드 생성
		Socket sock;
		BufferedReader in;
		PrintWriter out;
		String id = "익명";

		public ClientThread3(Socket sock) {
			try {
				this.sock = sock;
				in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				out = new PrintWriter(sock.getOutputStream(), true);
				// sock=>sock.toString() 호출
				System.out.println(sock + "접속됨...");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 아예 로그인 창이 뜨도록 만들자.
		@Override
		public void run() {
			try {
				while (true) {
					String line = in.readLine();
					if (line == null)
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
			int idx = line.indexOf(':');
			String cmd = line.substring(0, idx);
			String data = line.substring(idx + 1);
			// ID:aaa;1234
			if (cmd.equals(ChatProtocol3.ID)) {
				idx = data.indexOf(';');
				cmd = data.substring(0, idx); // aaa
				data = data.substring(idx + 1); // 1234
				if (mgr.loginChk(cmd, data)) {
					// 로그인 성공
					ClientThread3 ct = findClient(cmd); // aaa
					if (ct != null && ct.id.equals(cmd)) {
						// 이중 접속
						sendMessage(ChatProtocol3.ID + ":" + "C");
					} else {
						id = cmd;
						sendMessage(ChatProtocol3.ID + ":" + "T");
						sendAllMessage(ChatProtocol3.CHATLIST + ":" + getIds());
						sendAllMessage(ChatProtocol3.CHATALL + ":" + "[" + id + "]님이 입장하였습니다.");

					}
				} else {
					// 로그인 실패
					sendMessage(ChatProtocol3.ID + ":" + "F");
				}

			} else if (cmd.equals(ChatProtocol3.CHAT)) { // CHAT:bbb; 밥먹자
				idx = data.indexOf(';');
				cmd /* bbb */ = data.substring(0, idx); // bbb
				data /* 밥먹자 */ = data.substring(idx + 1); // 밥먹자
				// id : bbb 찾아야함.
				ClientThread3 ct = findClient(cmd);
				if (ct != null) { // 상대방과 자신에게 보냄

					// 상대방(bbb)
					/*
					 * MessageBean3 bean = new MessageBean3(); bean.setFid(id); // aaa
					 * bean.setTid(cmd); // bbb bean.setMsg(data); // 밥먹자 mgr2.insertMsg(bean); //
					 */
					ct.sendMessage(ChatProtocol3.CHAT + ":" + "[" + id + "(S)]" + data); // bbb에게 날라가는것(상대방) , data =
																							// 귓속말
					sendMessage(ChatProtocol3.CHAT + ":" + "[" + id + "(S)]" + data); // 자신(aaa)에게 날라옴(sendMessage)
				} else {// 내 자신에게
					sendMessage(ChatProtocol3.CHAT + ":" + "[" + cmd + "]님이 접속자가 아닙니다.");
				}
			} else if (cmd.equals(ChatProtocol3.CHATALL)) {
				sendAllMessage(ChatProtocol3.CHATALL + ":" + "[" + id + "]" + data);
			} else if (cmd.equals(ChatProtocol3.MESSAGE)) {
				idx = data.indexOf(';');
				cmd = data.substring(0, idx);
				data = data.substring(idx + 1);
				ClientThread3 ct = findClient(cmd);
				if (ct != null) {
					MessageBean3 bean = new MessageBean3();
					bean.setFid(id); // aaa
					bean.setTid(cmd);// bbb
					bean.setMsg(data);// 밥먹자
					mgr2.insertMsg(bean);// db저장
					ct.sendMessage(ChatProtocol3.MESSAGE + ":" + id + ";" + data);
				} else { // 자신에게 보내기
					sendMessage(ChatProtocol3.CHAT + ":" + "[" + cmd + "]님이 접속자 아닙니다.");
				}
			} else if (cmd.equals(ChatProtocol3.MSGLIST)) {
				// DB에서 MsgList 리턴 받아서 Client로 보냄
				Vector<MessageBean3> vlist = mgr2.getMsgList(id);
				// MSGLIST:aaa,bbb,밥먹자;bbb,ccc,하이...
				String str = "";
				for (int i = 0; i < vlist.size(); i++) {
					MessageBean3 bean = vlist.get(i);
					str += bean.getFid() + ",";
					str += bean.getTid() + ",";
					str += bean.getMsg() + ";";
				}
				sendMessage(ChatProtocol3.MSGLIST + ":" + str);
			}
		}

		public void sendMessage(String msg) {
			out.println(msg);
		}
	}

	public static void main(String[] args) {
		new ChatServer3();
		/*
		 * String str = "CHATALL:오늘은 목요일 입니다."; int idx = str.indexOf(':'); String cmd =
		 * str.substring(0, idx); String data = str.substring(idx+1);
		 * System.out.println(cmd); System.out.println(data);
		 */
	}

}
