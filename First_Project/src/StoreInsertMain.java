import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StoreInsertMain extends JFrame implements ActionListener {

	StoreDao dao;
	Container contentPane;
	ArrayList<StoreDao> lists;
	private String id;

	private JLabel[] lb = { new JLabel("상호명"), new JLabel("업종"), new JLabel("평균가격"), new JLabel("위치"),
			new JLabel("전화번호"), new JLabel("리뷰수") };
	private JButton[] btn = new JButton[2];
	private JTextField[] txt = new JTextField[6];
	private JPanel panel = new JPanel();

	public StoreInsertMain(String id) {
		this.id = id;
		setTitle("가게등록");

		compose();

		setLocation(900, 150);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 600);
		setVisible(true);
	}

	private void compose() {
		dao = new StoreDao();
		contentPane = getContentPane();
		contentPane.setLayout(null);

		for (int i = 0; i < lb.length; i++) {
			lb[i].setBounds(15, 125 + (i * 50), 100, 40);
			lb[i].setFont(new Font("나눔고딕", Font.PLAIN, 15));
			lb[i].setHorizontalAlignment(JLabel.RIGHT);
			contentPane.add(lb[i]);
		}

		for (int i = 0; i < txt.length; i++) {
			txt[i] = new JTextField();
			txt[i].setBounds(180, 130 + (i * 50), 170, 30);
			contentPane.add(txt[i]);
		}

		panel.setLayout(new GridLayout(1, 2, 20, 10));
		panel.setBounds(140, 450, 200, 30);
		contentPane.add(panel);
		String[] btnTitle = { "등록", "취소" };
		for (int i = 0; i < btn.length; i++) {
			btn[i] = new JButton(btnTitle[i]);
			btn[i].setFont(lb[i].getFont());
			panel.add(btn[i]);
			btn[i].addActionListener(this);
		}

		txt[2].addKeyListener(new KeyHandler());
		txt[4].addKeyListener(new KeyHandler());
		txt[5].addKeyListener(new KeyHandler());
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btn[0]) {
			boolean check = insertData();
			if (check) {
				dispose();
				new SellerStoreMain(id);
			}
		}

		else if (obj == btn[1]) {
			dispose();
		}
	}

	private boolean insertData() {
		boolean check = checkData();

		if (check) {
			StoreBean sb = new StoreBean(id, txt[0].getText(), txt[1].getText(), Integer.parseInt(txt[2].getText()),
					txt[3].getText(), txt[4].getText(), Integer.parseInt(txt[5].getText()));

			int cnt = dao.insertData(sb);
			if (cnt == -1)
				System.out.println("삽입 실패");
			else {
				return true;
			}
		}
		return false;
	}

	private boolean checkData() {
		if (txt[0].getText().equals("")) {
			JOptionPane.showMessageDialog(this, "상호명을 입력해주세요", "에러발생", JOptionPane.WARNING_MESSAGE);
			txt[0].requestFocus();
			return false;
		}
		if (txt[1].getText().length() == 0) {
			JOptionPane.showMessageDialog(this, "업종을 입력해주세요", "에러발생", JOptionPane.WARNING_MESSAGE);
			txt[1].requestFocus();
			return false;
		}
		if (txt[2].getText().equals("")) {
			JOptionPane.showMessageDialog(this, "평균가격을 입력해주세요", "에러발생", JOptionPane.WARNING_MESSAGE);
			txt[2].requestFocus();
			return false;
		}
		if (txt[3].getText().equals("")) {
			JOptionPane.showMessageDialog(this, "위치를 입력해주세요", "에러발생", JOptionPane.WARNING_MESSAGE);
			txt[3].requestFocus();
			return false;
		}
		if (txt[4].getText().equals("")) {
			JOptionPane.showMessageDialog(this, "전화번호를 입력해주세요", "에러발생", JOptionPane.WARNING_MESSAGE);
			txt[4].requestFocus();
			return false;
		}
		if (txt[5].getText().equals("")) {
			JOptionPane.showMessageDialog(this, "리뷰수를 입력해주세요", "에러발생", JOptionPane.WARNING_MESSAGE);
			txt[5].requestFocus();
			return false;
		}
		return true;
	}

	class KeyHandler extends KeyAdapter {

		public void keyReleased(KeyEvent e) {
			if (e.getSource() == txt[2]) { // 유효성 검사
				if (!txt[2].getText().equals("")) {
					try {
						Integer.parseInt(txt[2].getText());
					} catch (NumberFormatException nfe) {
						JOptionPane.showMessageDialog(null, "숫자로 입력하세요", "에러발생", JOptionPane.WARNING_MESSAGE);
						txt[2].setText("");
					}
				}
			} else if (e.getSource() == txt[5]) {
				if (!txt[5].getText().equals("")) {
					try {
						Integer.parseInt(txt[5].getText());
					} catch (NumberFormatException nfe) {
						JOptionPane.showMessageDialog(null, "숫자로 입력하세요", "에러발생", JOptionPane.WARNING_MESSAGE);
						txt[5].setText("");
					}
				}
			}

		} // keyReleased
	}
}
