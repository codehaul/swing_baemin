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

public class MenuUpdateMain extends JFrame implements ActionListener {

	MenuDao dao;
	Container contentPane;
	ArrayList<MenuDao> lists;
	MenuBean meb;
	String menuName;

	private JLabel[] lb = {new JLabel("메뉴이름"), new JLabel("단가"), new JLabel("반찬갯수"), new JLabel("맛"),
			new JLabel("메인재료") };
	private JButton[] btn = new JButton[2];
	private JTextField[] txt = new JTextField[5];
	private JPanel panel = new JPanel();

	public MenuUpdateMain(MenuBean meb) {
		this.meb = meb;
		this.menuName = meb.getMenuName();
		setTitle("메뉴수정");

		compose();

		setLocation(900, 150);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 600);
		setVisible(true);
	}

	private void compose() {
		dao = new MenuDao();
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
		String[] btnTitle = { "수정", "취소" };
		for (int i = 0; i < btn.length; i++) {
			btn[i] = new JButton(btnTitle[i]);
			btn[i].setFont(lb[i].getFont());
			panel.add(btn[i]);
			btn[i].addActionListener(this);
		}

		txt[1].addKeyListener(new KeyHandler());
		txt[2].addKeyListener(new KeyHandler());

		textCopy();
	}

	private void textCopy() {
		txt[0].setText(meb.getMenuName());
		txt[1].setText(String.valueOf(meb.getPrice()));
		txt[2].setText(String.valueOf(meb.getSidedishCnt()));
		txt[3].setText(meb.getTaste());
		txt[4].setText(meb.getMainIngredient());
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btn[0]) {
			boolean check = updateData();
			if (check) {
				new SellerMenuMain(meb.getId(), meb.getStoreName());
				dispose();
			}
		}

		else if (obj == btn[1]) {
			dispose();
		}
	}

	private boolean updateData() {
		boolean check = checkData();

		if (check) {
			meb = new MenuBean(meb.getId(), meb.getStoreName(), txt[0].getText(), Integer.parseInt(txt[1].getText()), Integer.parseInt(txt[2].getText()),
					txt[3].getText(), txt[4].getText());

			int cnt = dao.updateData(meb, menuName);
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
			JOptionPane.showMessageDialog(this, "메뉴이름을 입력해주세요", "에러발생", JOptionPane.WARNING_MESSAGE);
			txt[0].requestFocus();
			return false;
		}
		if (txt[1].getText().length() == 0) {
			JOptionPane.showMessageDialog(this, "단가를 입력해주세요", "에러발생", JOptionPane.WARNING_MESSAGE);
			txt[1].requestFocus();
			return false;
		}
		if (txt[2].getText().equals("")) {
			JOptionPane.showMessageDialog(this, "반찬갯수를 입력해주세요", "에러발생", JOptionPane.WARNING_MESSAGE);
			txt[2].requestFocus();
			return false;
		}
		if (txt[3].getText().equals("")) {
			JOptionPane.showMessageDialog(this, "맛을 입력해주세요", "에러발생", JOptionPane.WARNING_MESSAGE);
			txt[3].requestFocus();
			return false;
		}
		if (txt[4].getText().equals("")) {
			JOptionPane.showMessageDialog(this, "메인재료를 입력해주세요", "에러발생", JOptionPane.WARNING_MESSAGE);
			txt[4].requestFocus();
			return false;
		}
		return true;
	}

	class KeyHandler extends KeyAdapter {

		public void keyReleased(KeyEvent e) {
			if (e.getSource() == txt[1]) { // 유효성 검사
				if (!txt[1].getText().equals("")) {
					try {
						Integer.parseInt(txt[1].getText());
					} catch (NumberFormatException nfe) {
						JOptionPane.showMessageDialog(null, "숫자로 입력하세요", "에러발생", JOptionPane.WARNING_MESSAGE);
						txt[1].setText("");
					}
				}
			} else if (e.getSource() == txt[2]) {
				if (!txt[2].getText().equals("")) {
					try {
						Integer.parseInt(txt[2].getText());
					} catch (NumberFormatException nfe) {
						JOptionPane.showMessageDialog(null, "숫자로 입력하세요", "에러발생", JOptionPane.WARNING_MESSAGE);
						txt[2].setText("");
					}
				}
			}

		} // keyReleased
	}
}
