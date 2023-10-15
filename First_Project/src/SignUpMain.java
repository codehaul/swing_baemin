import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class SignUpMain extends JFrame implements FocusListener, ActionListener {

	MemberDao dao;
	Container contentPane;
	ArrayList<MemberDao> lists;

	private JLabel[] lb = { new JLabel("아이디"), new JLabel("비밀번호"), new JLabel("성함"), new JLabel("생년월일"),
			new JLabel("성별"), new JLabel("이메일"), new JLabel("휴대전화"), new JLabel("이용목적") };
	private JTextField[] txt = new JTextField[6];
	private JButton[] btn = new JButton[2];
	private JPanel panel = new JPanel();
	private ButtonGroup group_gen = new ButtonGroup();
	private ButtonGroup group_role = new ButtonGroup();
	private JRadioButton[] radio_gen = new JRadioButton[2];
	private JRadioButton[] radio_role = new JRadioButton[2];
	private JPanel panelTitle = new JPanel();
	private JLabel lbTitle;

	public SignUpMain() {
		setTitle("회원가입");

		compose();
		
		setLocation(500, 100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 600);
		setVisible(true);
	}

	private void compose() {
		dao = new MemberDao();
		contentPane = getContentPane();
		contentPane.setLayout(null);
		
		lbTitle = new JLabel("회원가입");
		lbTitle.setFont(new Font("나눔고딕", Font.BOLD, 20));
		panelTitle.setBounds(40, 25, 400, 50);
		panelTitle.add(lbTitle);
		contentPane.add(panelTitle);

		for (int i = 0; i < lb.length; i++) {
			lb[i].setBounds(15, 90 + (i * 50), 100, 40);
			lb[i].setFont(new Font("나눔고딕", Font.PLAIN, 15));
			lb[i].setHorizontalAlignment(JLabel.RIGHT);
			contentPane.add(lb[i]);
		}

		for (int i = 0; i < txt.length; i++) {
			txt[i] = new JTextField();
			txt[i].setBounds(180, 95 + (i * 50), 170, 30);
			if (i > 3)
				txt[i].setBounds(180, 145 + (i * 50), 170, 30);
			contentPane.add(txt[i]);
		}
		txt[3].setText("yyyy-mm-dd 형식으로 입력");
		txt[3].setFont(new Font("나눔고딕", Font.ITALIC, 13));
		txt[3].setForeground(Color.gray);
		txt[3].addFocusListener(this);
		txt[5].setText("휴대전화는 - 제외");
		txt[5].setFont(new Font("나눔고딕", Font.ITALIC, 13));
		txt[5].setForeground(Color.gray);
		txt[5].addFocusListener(this);

		panel.setLayout(new GridLayout(1, 2, 10, 10));
		panel.setBounds(140, 500, 200, 30);
		contentPane.add(panel);
		String[] btnTitle = { "회원가입", "취소" };
		for (int i = 0; i < btn.length; i++) {
			btn[i] = new JButton(btnTitle[i]);
			btn[i].setFont(lb[i].getFont());
			panel.add(btn[i]);
			btn[i].addActionListener(this);
		}

		String[] radio_genName = { "남자", "여자" };
		for (int i = 0; i < radio_gen.length; i++) {
			radio_gen[i] = new JRadioButton(radio_genName[i]);
			radio_gen[i].setFont(txt[0].getFont());
			radio_gen[i].setBounds(200 + (i * 70), 280, 60, 60);
			group_gen.add(radio_gen[i]);
			radio_gen[i].addActionListener(this);
			contentPane.add(radio_gen[i]);
		}
		radio_gen[0].setSelected(true);

		String[] radio_roleName = { "구매자", "판매자" };
		for (int i = 0; i < radio_role.length; i++) {
			radio_role[i] = new JRadioButton(radio_roleName[i]);
			radio_role[i].setFont(txt[0].getFont());
			radio_role[i].setBounds(190 + (i * 90), 430, 80, 60);
			group_role.add(radio_role[i]);
			radio_role[i].addActionListener(this);
			contentPane.add(radio_role[i]);
		}
		radio_role[0].setSelected(true);
	}

	public void focusGained(FocusEvent e) {
		if (e.getSource() == txt[3]) {
			txt[3].setText("");
			txt[3].setFont(txt[0].getFont());
			txt[3].setForeground(txt[0].getForeground());
		}
		if (e.getSource() == txt[5]) {
			txt[5].setText("");
			txt[5].setFont(txt[0].getFont());
			txt[5].setForeground(txt[0].getForeground());
		}
	}

	public void focusLost(FocusEvent e) {
		if (e.getSource() == txt[3] && txt[3].getText().equals("")) {
			txt[3].setText("yyyy-mm-dd 형식으로 입력");
			txt[3].setFont(new Font("나눔고딕", Font.ITALIC, 13));
			txt[3].setForeground(Color.GRAY);
		}
		if (e.getSource() == txt[5] && txt[5].getText().equals("")) {
			txt[5].setText("휴대전화는 - 제외");
			txt[5].setFont(new Font("나눔고딕", Font.ITALIC, 13));
			txt[5].setForeground(Color.GRAY);
		}
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		String gender = null;
		String mem_role = null;

		if (radio_gen[0].isSelected())
			gender = radio_gen[0].getText();
		else if (radio_gen[0].isSelected())
			gender = radio_gen[1].getText();

		if (radio_role[0].isSelected())
			mem_role = radio_role[0].getText();
		else if (radio_role[1].isSelected())
			mem_role = radio_role[1].getText();

		if (obj == btn[0]) {
			boolean check = insertData(gender, mem_role);
			if (check) {
				dispose();
				new LoginMain("Login");
			}
		}

		else if (obj == btn[1]) {
			dispose();
			new LoginMain("Login");
		}
	}

	private boolean insertData(String gender, String mem_role) {

		boolean check = checkData();

		if (check) {
			String id = txt[0].getText();
			String pw = txt[1].getText();
			String name = txt[2].getText();
			String birth = txt[3].getText();
			String email = txt[4].getText();
			String phone = txt[5].getText();

			MemberBean mb = new MemberBean(id, pw, name, birth, gender, email, phone, mem_role);

			int cnt = dao.insertData(mb);
			if (cnt == -1)
				System.out.println("삽입 실패");
			else {
				return true;
			}
		}

		return false;
	} // insertData

	private boolean checkData() {

		if (txt[0].getText().equals("")) {
			JOptionPane.showMessageDialog(this, "아이디를 입력해주세요", "에러발생", JOptionPane.WARNING_MESSAGE);
			txt[0].requestFocus();
			return false;
		}
		if (txt[1].getText().length() == 0) {
			JOptionPane.showMessageDialog(this, "비밀번호를 입력해주세요", "에러발생", JOptionPane.WARNING_MESSAGE);
			txt[1].requestFocus();
			return false;
		}
		if (txt[2].getText().equals("")) {
			JOptionPane.showMessageDialog(this, "성함을 입력해주세요", "에러발생", JOptionPane.WARNING_MESSAGE);
			txt[2].requestFocus();
			return false;
		}
		if (txt[3].getText().equals("yyyy-mm-dd 형식으로 입력")) {
			JOptionPane.showMessageDialog(this, "생년월일을 입력해주세요", "에러발생", JOptionPane.WARNING_MESSAGE);
			txt[3].requestFocus();
			return false;
		}
		if (txt[4].getText().equals("")) {
			JOptionPane.showMessageDialog(this, "이메일을 입력해주세요", "에러발생", JOptionPane.WARNING_MESSAGE);
			txt[4].requestFocus();
			return false;
		}
		if (txt[5].getText().equals("휴대전화는 - 제외")) {
			JOptionPane.showMessageDialog(this, "휴대전화 입력해주세요", "에러발생", JOptionPane.WARNING_MESSAGE);
			txt[5].requestFocus();
			return false;
		}

		return true;
	} // checkData
}
// 라디오 버튼 성별 선택