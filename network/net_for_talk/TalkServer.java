package net_for_talk;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class TalkServer {

	Vector<TalkThread> vc;
	ServerSocket server;
	int port = 8005;
	TalkMgr mgr;

	public TalkServer() {
		try {
			vc = new Vector<TalkThread>();
			server = new ServerSocket(port);
			mgr = new TalkMgr();
		} catch (Exception e) {
			System.err.println("Error in Server");
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("************************************************");
		System.out.println("***********Welcome Talk Server 1.0**************");
		System.out.println("***********클라이언트 접속을 기다리고 있습니다***********");
		System.out.println("************************************************");

		try {
			while (true) {
				Socket sock = server.accept();
				TalkThread ct = new TalkThread(sock);
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
			TalkThread ct = vc.elementAt(i);
			ct.sendMessage(msg);
		}
	}

	public void removeClient(TalkThread ct) {
		vc.remove(ct);
	}

	// 접속된 모든 id 리스트 리턴 ex)aaa; bbb; ccc; ddd; 홍길동;
	public String getIds() {
		String ids = "";
		for (int i = 0; i < vc.size(); i++) {
			TalkThread ct = vc.get(i);
			ids += ct.id + ";";
		}
		return ids;
	}

	// 매개변수 id값으로 Thread를 검색
	public TalkThread findClient(String id) {
		TalkThread ct = null;
		for (int i = 0; i < vc.size(); i++) {
			ct = vc.get(i);
			if (ct.id.equals(id)) {// 매개변수 id와 Client의 id와 같다면...
				break;
			}

		}
		return ct;
	}

	class TalkThread extends Thread {

		// 4개의 필드 생성
		Socket sock;
		BufferedReader in;
		PrintWriter out;
		String id = "익명";

		public TalkThread(Socket sock) {
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
			if (cmd.equals(TalkProtocal.ID)) {
				idx = data.indexOf(';');
				cmd = data.substring(0, idx); // aaa
				data = data.substring(idx + 1); // 1234
				if (mgr.loginChk(cmd, data)) {
					// 로그인 성공
					TalkThread ct = findClient(cmd); // aaa
					if (ct != null && ct.id.equals(cmd)) {
						// 이중 접속
						System.out.println("이미 접속된 아이디 입니다.");
						sendMessage(TalkProtocal.ID + ":" + "C");
						
					} else {
						id = cmd;
						System.out.println("접속.");
						sendMessage(TalkProtocal.ID + ":" + "T");
						sendAllMessage(TalkProtocal.CHATLIST + ":" + getIds());
						sendAllMessage(TalkProtocal.CHATALL + ":" + "[" + id + "]님이 입장하였습니다.");

					}
				} else {
					// 로그인 실패
					sendMessage(TalkProtocal.ID + ":" + "F");
				}

			} else if (cmd.equals(TalkProtocal.CHAT)) { // CHAT:bbb; 밥먹자
				idx = data.indexOf(';');
				cmd /* bbb */ = data.substring(0, idx);
				data /* 밥먹자 */ = data.substring(idx + 1);
				// id : bbb 찾아야함.
				TalkThread ct = findClient(cmd);
				if (ct != null) {
					// 상대방(bbb)
					ct.sendMessage(TalkProtocal.CHAT + ":" + "[" + id + "(S)]" + data);
					// 내자신(aaa)
					sendMessage(TalkProtocal.CHAT + ":" + "[" + id + "(S)]" + data);
				} else {// 내 자신에게
					sendMessage(TalkProtocal.CHAT + ":" + "[" + cmd + "]님이 접속자가 아닙니다.");
				}
			} else if (cmd.equals(TalkProtocal.CHATALL)) {
				sendAllMessage(TalkProtocal.CHATALL + ":" + "[" + id + "]" + data);
			} else if (cmd.equals(TalkProtocal.MESSAGE)) {
				idx = data.indexOf(';');
				cmd = data.substring(0, idx);
				data = data.substring(idx + 1);
				TalkThread ct = findClient(cmd);
				if (ct != null) {
					ct.sendMessage(TalkProtocal.MESSAGE + ":" + id + ";" + data);
				} else {
					sendMessage(TalkProtocal.CHAT + ":" + "[" + cmd + "]님이 접속자 아닙니다.");
				}
			}
		}

		public void sendMessage(String msg) {
			out.println(msg);
		}
	}

	public static void main(String[] args) {
		new TalkServer();
		/*
		 * String str = "CHATALL:오늘은 목요일 입니다."; int idx = str.indexOf(':'); String cmd =
		 * str.substring(0, idx); String data = str.substring(idx+1);
		 * System.out.println(cmd); System.out.println(data);
		 */
	}

}