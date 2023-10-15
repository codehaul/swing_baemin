import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ToolTipManager;
import javax.swing.table.DefaultTableModel;

public class SellerMenuMain extends JFrame implements ActionListener {

	private static String id;
	private static String storeName;

	MenuDao dao;
	Container contentPane;
	ArrayList<MenuBean> lists;
	ToolTipManager m = ToolTipManager.sharedInstance();
	MenuInsertMain mi;
	MenuUpdateMain mu;

	private String[] columnNames = { "사진", "메뉴이름", "단가", "반찬갯수", "맛", "메인재료"};
	private Object[][] rowData;
	private static JTable table;
	private JScrollPane scrollPane;

	private JButton[] btn = new JButton[4];

	public SellerMenuMain(String id, String storeName) {
		this.id = id;
		this.storeName = storeName;
		setTitle("판매자 메뉴 페이지");

		compose();
		setLocation(500, 100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 600);
		setVisible(true);
		
		System.out.println(id);
		System.out.println(storeName);
	}

	private void compose() {
		dao = new MenuDao();
		lists = dao.getAllMenu(id, storeName);
		rowData = new Object[lists.size()][columnNames.length];
		fillData();
		
		table = new JTable(rowData, columnNames);
		scrollPane = new JScrollPane(table);
		contentPane = getContentPane();
		contentPane.setLayout(null);

		scrollPane.setBounds(20, 150, 540, 400);
		contentPane.add(scrollPane);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 4, 20, 10));
		panel.setBounds(40, 60, 490, 40);
		contentPane.add(panel);

		String[] btnTitle = { "메뉴등록", "메뉴수정", "메뉴삭제", "뒤로가기" };
		for (int i = 0; i < btn.length; i++) {
			btn[i] = new JButton(btnTitle[i]);
			btn[i].setFont(new Font("나눔고딕", Font.PLAIN, 17));
			panel.add(btn[i]);
			btn[i].addActionListener(this);
		}
		btn[1].setToolTipText("수정하실 메뉴를 목록에서 선택 후 눌러주세요.");
		btn[2].setToolTipText("삭제하실 메뉴를 목록에서 선택 후 눌러주세요.");
		m.setInitialDelay(0);

		table.addMouseListener(new MouseHandler());

	}

	private void fillData() {
		Object[] arr = lists.toArray();
		int j = 1;

		for (int i = 0; i < arr.length; i++) {
			MenuBean meb = (MenuBean) arr[i];
			rowData[i][j++] = meb.getMenuName();
			rowData[i][j++] = meb.getPrice();
			rowData[i][j++] = meb.getSidedishCnt();
			rowData[i][j++] = meb.getTaste();
			rowData[i][j++] = meb.getMainIngredient();
			j = 1;
		}
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btn[0]) {
			mi = new MenuInsertMain(id, storeName);
			dispose();
		} else if (obj == btn[1]) {
			if (MouseHandler.meb == null)
				JOptionPane.showMessageDialog(null, "가게를 선택해주세요", "에러발생", JOptionPane.WARNING_MESSAGE);
			else {
				mu = new MenuUpdateMain(MouseHandler.meb);
				dispose();
			}
		} else if (obj == btn[2]) {
			if (MouseHandler.meb == null)
				JOptionPane.showMessageDialog(null, "가게를 선택해주세요", "에러발생", JOptionPane.WARNING_MESSAGE);
			else {
				int cnt = dao.deleteData(MouseHandler.meb);
				if (cnt == 1) {
					JOptionPane.showMessageDialog(null, "삭제성공", "알림창", JOptionPane.PLAIN_MESSAGE);
					getAllMenu();
				}
			}
		} else if (obj == btn[3]) {
			dispose();
		}
	}

	private void getAllMenu() {
		dao = new MenuDao();
		lists = dao.getAllMenu(id, storeName);
		rowData = new Object[lists.size()][columnNames.length];
		fillData();

		table = new JTable(rowData, columnNames);
		table.addMouseListener(new MouseHandler());
		scrollPane.setViewportView(table);
	}

	static class MouseHandler extends MouseAdapter {

		static MenuBean meb;

		public void mouseClicked(MouseEvent e) {
			int row = table.getSelectedRow();

			meb = new MenuBean(id, storeName, String.valueOf(table.getValueAt(row, 1)), Integer.parseInt(String.valueOf(table.getValueAt(row, 2))),
					Integer.parseInt(String.valueOf(table.getValueAt(row, 3))),
					String.valueOf(table.getValueAt(row, 4)),
					String.valueOf(table.getValueAt(row, 5)));
		}

		public void mouseEntered(MouseEvent e) {
			System.out.println("mouseEntered");
		}

		public void mouseExited(MouseEvent e) {
			System.out.println("mouseExited");
		}

	}

}

