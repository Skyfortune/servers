package demo;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

class MainFrame extends JFrame {

	public JTextField address;
	public JLabel resAddress, resX, resY, jibunAddress, imageLabel;

	public MainFrame(String title) {
		super(title);
		// 컴포넌트 시작
		JPanel pan = new JPanel();
		JLabel addressLbl = new JLabel("주소입력");
		address = new JTextField(50); // 입력창 객체 생성
		JButton btn = new JButton("클릭");
		pan.add(addressLbl);
		pan.add(address);
		pan.add(btn);

		btn.addActionListener(e -> {
			try {
				new NaverMap(this);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		});

		// 지도 입력 라벨
		imageLabel = new JLabel("지도보기");
		// 아래의 주소정보 표시 패널
		JPanel pan1 = new JPanel();
		pan1.setLayout(new GridLayout(4, 1));
		resAddress = new JLabel("도로명");
		jibunAddress = new JLabel("지번주소");
		resX = new JLabel("경도");
		resY = new JLabel("위도");
		pan1.add(resAddress);
		pan1.add(jibunAddress);
		pan1.add(resX);
		pan1.add(resY);

		// 레이아웃 설정
		setLayout(new BorderLayout());
		add(pan, BorderLayout.NORTH);
		add(imageLabel, BorderLayout.CENTER);
		add(pan1, BorderLayout.SOUTH);

		// 컴포넌트 끝
		setSize(730, 660);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}

public class Mapapp {
	public static void main(String[] args) {
		new MainFrame("네이버 Map 검색");
	}
}
