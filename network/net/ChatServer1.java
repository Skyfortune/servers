package net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ChatServer1 {

	Vector<ClientThread1> vc;
	ServerSocket server;
	int port = 8001;

	public ChatServer1() {
		try {
			vc = new Vector<ClientThread1>();
			server = new ServerSocket(port);

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error in Server");
			System.exit(1);// 비정상적인 종료
			// 큰 의미는 없다. 정상적인 종료가 아닐때 표기
		}
		System.out.println("*****************************");
		System.out.println("CharServer ver1.0 시작되었습니다");
		System.out.println("*****************************");
		try {
			while (true) {
				Socket sock = server.accept();
				ClientThread1 ct = new ClientThread1(sock);
				ct.start(); // 쓰레드 스케줄러에게 이 쓰레드 객체 시작할 준비 등록
				// Client 접속후에 생성된 객체(ClientThread1)를 Vector에 저장
				vc.addElement(ct); // 이건 뭘까?
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error in Socket");
		}
	}

	// 전체 접속자에게 메세지 전달 -Vector
	public void sendAllMessage(String msg) {
		// Vector 및 배열은 항상 for문을 쓴다
		// foreach가 더 일반적, for를 써도 무방하다.
		for (int i = 0; i < vc.size(); i++) {
			ClientThread1 ct = vc.get(i); // vec에서 ct를 하나씩 가져옴
			ct.sendMessage(msg); // ct에 연결된 client에게 메세지 전송
			// 각각 들어가는 애들이 a,b,c라면 클라이언트로 들어가서 vecter로 묶인다.
			// 벡터에서 가져와서 위의 샌드메세지로 계속 메세지를 던지고 던진다.
		} // --for
	}// --sendAllMessage

	// 클라이언트가 접속을 하고, 로그아웃을 하던 강제종료를 하던간에 서버는 벡터에서 remove 시켜야한다.
	// 접속되지 않은사람 -> 벡터에서 remove,

	// Vector에서 ClientThread1 제거
	public void removeClient(ClientThread1 ct) {
		vc.remove(ct);
	}

	class ClientThread1 extends Thread {

		// 4개의 필드 생성
		Socket sock;
		BufferedReader in;
		PrintWriter out;
		String id;

		// 내부클래스 3개 선언

		public ClientThread1(Socket sock) {
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

		@Override
		// 실질적인 일을 하는 애
		
		public void run() {
			try {
				// Client에게 최초 보내지는 메세지
				out.println("아이디를 입력 해주세요");
				// Client 입력한 id저장
				id = in.readLine();
				// 접속한 모든 Client에게 welcome 메세지 전달 
				sendAllMessage("[" + id + "]님이 입장하였습니다. ");
				String data = "";
				while(true) {
					data = in.readLine();
					if(data==null)
						break;
					sendAllMessage("["+id+"]"+data);
				}
				//로그아웃 기능이 없으니 지금은 기동하지 않을것
				in.close();
				out.close();
				sock.close();
				
			} catch (Exception e) {
				// 자신의 객체를 Vector에서 제거
				removeClient(this);
				System.err.println(sock + "끊어짐...");
				// 처음에는 디버깅 위해서 넣지만, 나중에는 주석처리하게 만든다.
				// 서버에 부담이 되니까.
				// e.printStackTrace();
			}
		}

		// Client에게 메세지를 보내는 메소드
		public void sendMessage(String msg) {
			out.println(msg);
		}

	}

	public static void main(String[] args) {
		new ChatServer1();

	}

}
