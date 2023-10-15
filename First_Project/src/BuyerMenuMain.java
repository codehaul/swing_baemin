import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ToolTipManager;
import javax.swing.table.DefaultTableModel;

public class BuyerMenuMain extends JFrame implements ActionListener {

	private static String id;
	private static String storeName;

	MenuDao dao;
	Container contentPane;
	ArrayList<MenuBean> lists;
	ToolTipManager m = ToolTipManager.sharedInstance();
	DefaultTableModel model;
	private Vector<String> vector;

	private String[] columnNames = { "사진", "메뉴이름", "단가", "반찬갯수", "맛", "메인재료" };
	private Object[][] rowData;
	private static JTable table, tableBasket;
	private JScrollPane scrollPane, scrollBasket;

	private JButton[] btn = new JButton[4];

	public BuyerMenuMain(String id, String storeName) {
		this.id = id;
		this.storeName = storeName;
		setTitle("구매자 메뉴 페이지");

		compose();
		setLocation(500, 50);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 900);
		setVisible(true);
	}

	private void compose() {
		dao = new MenuDao();
		lists = dao.getAllMenu(storeName);
		rowData = new Object[lists.size()][columnNames.length];
		fillData();

		vector = new Vector<String>();
		vector.add("사진");
		vector.add("메뉴이름");
		vector.add("단가");
		vector.add("반찬갯수");
		vector.add("맛");
		vector.add("메인재료");
		model = new DefaultTableModel(vector, 0);
		tableBasket = new JTable(model);
			
		table = new JTable(rowData, columnNames);
		scrollPane = new JScrollPane(table);
		scrollBasket = new JScrollPane(tableBasket);
		contentPane = getContentPane();
		contentPane.setLayout(null);

		scrollPane.setBounds(20, 150, 540, 300);
		scrollBasket.setBounds(20, 480, 540, 300);
		contentPane.add(scrollPane);
		contentPane.add(scrollBasket);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 4, 15, 10));
		panel.setBounds(40, 60, 490, 40);
		contentPane.add(panel);

		String[] btnTitle = { "장바구니 추가", "장바구니 빼기", "결제", "뒤로가기" };
		for (int i = 0; i < btn.length; i++) {
			btn[i] = new JButton(btnTitle[i]);
			btn[i].setFont(new Font("나눔고딕", Font.PLAIN, 13));
			panel.add(btn[i]);
			btn[i].addActionListener(this);
		}
		btn[0].setToolTipText("추가하실 메뉴를 목록에서 선택 후 눌러주세요.");
		btn[1].setToolTipText("삭제하실 메뉴를 목록에서 선택 후 눌러주세요.");
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
			String[] str = { null, MouseHandler.meb.getMenuName(), String.valueOf(MouseHandler.meb.getPrice()),
					String.valueOf(MouseHandler.meb.getSidedishCnt()), MouseHandler.meb.getTaste(),
					MouseHandler.meb.getMainIngredient() };

			model.addRow(str);

		} else if (obj == btn[1]) {
			int row = tableBasket.getSelectedRow();
			model.removeRow(row);
		} else if (obj == btn[2]) {
			int answer = JOptionPane.showConfirmDialog(this, "결제하시겠습니까?", "confirm", JOptionPane.YES_NO_OPTION);
			if (answer == JOptionPane.OK_OPTION) {
				JOptionPane.showMessageDialog(null, "결제 성공!", "결제창", JOptionPane.DEFAULT_OPTION);
				dispose();
			}
		} else if (obj == btn[3]) {
			dispose();
		}
	}

	static class MouseHandler extends MouseAdapter {

		static MenuBean meb;

		public void mouseClicked(MouseEvent e) {
			int row = table.getSelectedRow();

			meb = new MenuBean(id, storeName, String.valueOf(table.getValueAt(row, 1)),
					Integer.parseInt(String.valueOf(table.getValueAt(row, 2))),
					Integer.parseInt(String.valueOf(table.getValueAt(row, 3))),
					String.valueOf(table.getValueAt(row, 4)), String.valueOf(table.getValueAt(row, 5)));
		}
	}

}
