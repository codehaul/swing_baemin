import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginMain extends JFrame implements ActionListener {

	MemberDao dao;
	Container contentPane;

	private JLabel[] lb = { new JLabel("Id"), new JLabel("Password") };
	private JTextField[] txt = { new JTextField(), new JTextField() };
	private JButton[] btn = new JButton[2];
	private JPanel panel = new JPanel();
	ImageIcon img = new ImageIcon("images/baemin.jpg");

	public LoginMain(String title) {
		super(title);

		compose();
		
		setLocation(500, 100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 450);
		setVisible(true);
	}

	private void compose() {
		dao = new MemberDao();
		contentPane = getContentPane();
		contentPane.setLayout(null);
		
		JLabel imgLable = new JLabel();
		contentPane.add(imgLable);
		imgLable.setIcon(img);
		imgLable.setBounds(120, 50, img.getIconWidth(), img.getIconHeight());

		for (int i = 0; i < lb.length; i++) {
			lb[i].setBounds(15, 230 + (i * 30), 100, 40);
			lb[i].setFont(new Font("나눔고딕", Font.PLAIN, 15));
			lb[i].setHorizontalAlignment(JLabel.RIGHT);
			contentPane.add(lb[i]);
		}

		for (int i = 0; i < txt.length; i++) {
			txt[i].setBounds(150, 240 + (i * 30), 120, 25);
			contentPane.add(txt[i]);
		}

		panel.setLayout(new GridLayout(1, 2, 10, 10));
		panel.setBounds(90, 320, 200, 30);
		contentPane.add(panel);
		String[] btnTitle = { "로그인", "회원가입" };
		for (int i = 0; i < btn.length; i++) {
			btn[i] = new JButton(btnTitle[i]);
			btn[i].setFont(lb[i].getFont());
			panel.add(btn[i]);
			btn[i].addActionListener(this);
		}
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btn[0]) {
			login();
		} else if (obj == btn[1]) {
			new SignUpMain();
			dispose();
		}
	}

	private void login() {
		boolean check = checkData();

		if (check) {
			String id = txt[0].getText();
			String pw = txt[1].getText();

			MemberBean mb = dao.checkLogin(id, pw);
			if (mb == null)
				JOptionPane.showMessageDialog(this, "일치하지 않습니다.", "에러발생", JOptionPane.WARNING_MESSAGE);
			else {
				System.out.println("로그인 성공");
				if (mb.getMem_role().equals("판매자")){
					new SellerStoreMain(mb.getId());
					dispose();
				}
				else if (mb.getMem_role().equals("구매자")) {
					new BuyerStoreMain(mb.getId());
					dispose();
				}
			}
		}

	} // login

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
		return true;
	} // checkData

	public static void main(String[] args) {
		new LoginMain("Login");
	} // main

} // LoginMain
