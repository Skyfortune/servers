package net;

public class ChatProtocol3 {
	//ID, CHAT, CHATALL , MESSAGE, CHATLIST
	
	//(C->S): ID :aaa;1234
	//(S->C)  ID :T(성공), ID:F(실패), ID:C(이중접속)
	//(S->C) CHATLIST:aaa,bbb,ccc,ddd,
	public static final String ID = "ID";
	
	//(C->S) CHAT:받는 아이디; 메세지 ex) CHAT:bbb; 밥먹자
	//(S->C) CHAT:받는 아이디; 메세지 ex) CHAT:bbb; 밥먹자
	public static final String CHAT = "CHAT";
	
	//(C->S) CHATALL:메세지
	//(S->C) CHATALL:[보낸아이디] 메세지 
	public static final String CHATALL = "CHATALL";
	
	//(C->S) MESSAGE:받는아이디;쪽지내용 ex) MESSAGE:bbb; 밥먹자
	//(S->C) MESSAGE:보낸아이디:쪽지내용 ex) MESSAGE:aaa; 밥먹자
	public static final String MESSAGE = "MESSAGE";
	
	//(S->C): CHATLIST:aaa,bbb,ccc,ddd,
	public static final String CHATLIST = "CHATLIST";
	
	
}