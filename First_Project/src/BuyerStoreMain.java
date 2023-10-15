import java.awt.Container;
import java.awt.FileDialog;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ToolTipManager;

public class BuyerStoreMain extends JFrame{

	private static String id;

	StoreDao dao;
	Container contentPane;
	ArrayList<StoreBean> lists;

	private String[] columnNames = { "로고", "상호명", "업종", "평균가격", "위치", "전화번호", "리뷰수" };
	private Object[][] rowData;
	private static JTable table;
	private JScrollPane scrollPane;
	private JLabel lb;
	private JPanel panel = new JPanel();
	ImageIcon img;
	
	public BuyerStoreMain(String id) {
		this.id = id;
		setTitle("구매자 가게 페이지");

		compose();

		setLocation(500, 100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 600);
		setVisible(true);
	}

	private void compose() {
		dao = new StoreDao();
		lists = dao.getAllStore();
		rowData = new Object[lists.size()][columnNames.length];
		fillData();

		table = new JTable(rowData, columnNames) {
			public Class<?> getColumnClass(int column)  {
                return getValueAt(0,  column).getClass();
           }
		};
		scrollPane = new JScrollPane(table);
		contentPane = getContentPane();
		contentPane.setLayout(null);
		table.getColumnModel().getColumn(0).setPreferredWidth(200);
		table.setRowHeight(200);

		scrollPane.setBounds(20, 150, 540, 400);
		contentPane.add(scrollPane);

		lb = new JLabel("주문하실 가게를 더블클릭 해주세요");
		lb.setFont(new Font("나눔고딕", Font.PLAIN, 19));
		panel.setBounds(90, 60, 400, 50);
		panel.add(lb);
		contentPane.add(panel);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 5, 10, 10));
		panel.setBounds(40, 80, 490, 40);
		contentPane.add(panel);

		table.addMouseListener(new MouseHandler());

	}

	private void fillData() {
		Object[] arr = lists.toArray();
		int j = 0;

		for (int i = 0; i < arr.length; i++) {
			StoreBean sb = (StoreBean) arr[i];
			rowData[i][j++] = new ImageIcon(sb.getImg());
			rowData[i][j++] = sb.getStoreName();
			rowData[i][j++] = sb.getFoodType();
			rowData[i][j++] = sb.getPriceAvg();
			rowData[i][j++] = sb.getStoreLocation();
			rowData[i][j++] = sb.getCallNum();
			rowData[i][j++] = sb.getReviewCnt();
			j = 0;
		}
	}

	private void exitProgram() {
		int answer = JOptionPane.showConfirmDialog(this, "종료하시겠습니까?", "confirm", JOptionPane.OK_CANCEL_OPTION); // JOptionPane.YES_NO_OPTION
		if (answer == JOptionPane.OK_OPTION) {
			System.out.println("프로그램을 종료합니다.");
			System.exit(0);
		} else {
			System.out.println("종료를 취소합니다.");
		}

	}

	static class MouseHandler extends MouseAdapter {

		static StoreBean sb;

		public void mousePressed(MouseEvent e) {
			System.out.println("mouseClicked");
			int row = table.getSelectedRow();
			System.out.println("row:" + row);
			if (e.getClickCount() == 1) {
				sb = new StoreBean(id, String.valueOf(table.getValueAt(row, 1)),
						String.valueOf(table.getValueAt(row, 2)),
						Integer.parseInt(String.valueOf(table.getValueAt(row, 3))),
						String.valueOf(table.getValueAt(row, 4)),
						String.valueOf(table.getValueAt(row, 5)),
						Integer.parseInt(String.valueOf(table.getValueAt(row, 6))),
						String.valueOf(table.getValueAt(row, 0)));
			}

			if (e.getClickCount() > 1) {
				new BuyerMenuMain(id, String.valueOf(table.getValueAt(row, 1)));
			}
		} // mousePressed
	}
	
}
