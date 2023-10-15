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

public class SellerStoreMain extends JFrame implements ActionListener {

	private static String id;

	StoreDao dao;
	Container contentPane;
	ArrayList<StoreBean> lists;
	StoreInsertMain si;
	StoreUpdateMain su;
	ToolTipManager m = ToolTipManager.sharedInstance();
	String path;
	String name;
	String dir;

	private String[] columnNames = { "로고", "상호명", "업종", "평균가격", "위치", "전화번호", "리뷰수" };
	private Object[][] rowData;
	private static JTable table;
	private JScrollPane scrollPane;
	private JLabel lb;
	private JPanel panel = new JPanel();
	ImageIcon img;
	

	private JButton[] btn = new JButton[5];


	public SellerStoreMain(String id) {
		this.id = id;
		setTitle("판매자 가게 페이지");

		compose();

		setLocation(500, 100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 600);
		setVisible(true);
	}

	private void compose() {
		dao = new StoreDao();
		lists = dao.getAllStore(id);
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

		lb = new JLabel("메뉴확인은 자신의 가게를 더블클릭 해주세요");
		lb.setFont(new Font("나눔고딕", Font.PLAIN, 17));
		panel.setBounds(90, 25, 400, 50);
		panel.add(lb);
		contentPane.add(panel);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 5, 10, 10));
		panel.setBounds(40, 80, 490, 40);
		contentPane.add(panel);

		String[] btnTitle = { "가게등록", "가게수정", "가게삭제", "사진등록", "종료" };
		for (int i = 0; i < btn.length; i++) {
			btn[i] = new JButton(btnTitle[i]);
			btn[i].setFont(new Font("나눔고딕", Font.PLAIN, 14));
			panel.add(btn[i]);
			btn[i].addActionListener(this);
		}

		btn[1].setToolTipText("수정하실 가게를 목록에서 선택 후 눌러주세요.");
		btn[2].setToolTipText("삭제하실 가게를 목록에서 선택 후 눌러주세요.");
		m.setInitialDelay(0);

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

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btn[0]) {
			si = new StoreInsertMain(id);
			dispose();
		} else if (obj == btn[1]) {
			if (MouseHandler.sb == null)
				JOptionPane.showMessageDialog(null, "가게를 선택해주세요", "에러발생", JOptionPane.WARNING_MESSAGE);
			else {
				su = new StoreUpdateMain(MouseHandler.sb);
				dispose();
			}
		} else if (obj == btn[2]) {
			if (MouseHandler.sb == null)
				JOptionPane.showMessageDialog(null, "가게를 선택해주세요", "에러발생", JOptionPane.WARNING_MESSAGE);
			else {
				int cnt = dao.deleteData(MouseHandler.sb);
				if (cnt == 1) {
					JOptionPane.showMessageDialog(null, "삭제성공", "알림창", JOptionPane.PLAIN_MESSAGE);
					getAllStore(id);
				}
			}
		} else if (obj == btn[3]){
			FileDialog fdOpen = new FileDialog(this, "파일열기",FileDialog.LOAD);
			fdOpen.setVisible(true);
			path = fdOpen.getDirectory();
			name = fdOpen.getFile();
			dir = path + name;
			MouseHandler.sb.setImg(dir);
			dao.updateStoreImage(MouseHandler.sb);
			getAllStore(id);
			System.out.println(path+name);
		}
		else if (obj == btn[4]) {
			System.out.println("종료버튼 클릭");
			dao.exit();
			exitProgram();
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

	private void getAllStore(String id) {
		dao = new StoreDao();
		lists = dao.getAllStore(id);
		rowData = new Object[lists.size()][columnNames.length];
		fillData();

		table = new JTable(rowData, columnNames) {
			public Class<?> getColumnClass(int column)  {
                return getValueAt(0,  column).getClass();
           }
		};
		table.addMouseListener(new MouseHandler());
		scrollPane.setViewportView(table);
		table.getColumnModel().getColumn(0).setPreferredWidth(200);
		table.setRowHeight(200);
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
				new SellerMenuMain(id, String.valueOf(table.getValueAt(row, 1)));
			}
		} // mousePressed
	}
	
}
