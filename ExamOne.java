package picturePanel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*; //저장

class Draw implements Serializable {
	int x, y; // 위치를 저장할 정수형 필드
	int dist; // 도형 타입을 결정할 정수형 필드(line의 경우 나중 좌표값)
	int x1, y1; // 도형의 나중 위치를 저장할 정수형 필드
	boolean fill; // true값 반환시 채우기
	Color color; // 도형의 선 색깔을 나타내는 Color형 필드

	// 각각 필드들을 get, set하는 메소드
	public int getDist() {
		return dist;
	}

	public void setDist(int dist) {
		this.dist = dist;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX1() {
		return x1;
	}

	public Color getColor() {
		return color;
	}

	public int getY1() {
		return y1;
	}

	public boolean isFill() {
		return fill;
	}

	public Draw() {
	}

	// 도형 하나하나를 객체로 벡터에 저장, 객체마다 고유한 값(좌표, 타입, 색 등)을 가짐
	public Draw(int x, int y, int x1, int y1, int dist, boolean fill, Color color) {
		this.x = x;
		this.y = y;
		this.x1 = x1;
		this.y1 = y1;
		this.dist = dist;
		this.fill = fill;
		this.color = color;
	}
}

class PicturePanel extends Frame implements ItemListener, MouseListener, MouseMotionListener, ActionListener {

	Color color = new Color(0, 0, 0); // 색깔을 나타내는 필드

	boolean fill = false; // 채우기를 나타낼 필드

	// 화면구성부
	private MenuBar mb = new MenuBar(); // 메뉴바
	private Menu draw = new Menu("DRAW"); // 메뉴 DRAW

	// 체크박스 서브메뉴
	private CheckboxMenuItem pen = new CheckboxMenuItem("PEN", true); // 기본체크
	private CheckboxMenuItem line = new CheckboxMenuItem("LINE"); // 직선
	private CheckboxMenuItem oval = new CheckboxMenuItem("OVAL"); // 타원
	private CheckboxMenuItem rect = new CheckboxMenuItem("RECT"); // 사각형
	private CheckboxMenuItem triangle = new CheckboxMenuItem("TRIANGLE"); // 삼각형
	private CheckboxMenuItem pentagon = new CheckboxMenuItem("PENTAGON"); // 오각형
	private CheckboxMenuItem eraser = new CheckboxMenuItem("ERASER"); // 지우개
	private CheckboxMenuItem textBox = new CheckboxMenuItem("TEXT"); // 텍스트 입력
	private CheckboxMenuItem hexagon = new CheckboxMenuItem("hexagon"); // 육각형

	// 색깔 버튼
	JButton btn_Black = new JButton();
	JButton btn_Maroon = new JButton();
	JButton btn_Purple = new JButton();
	JButton btn_Olive = new JButton();
	JButton btn_Green = new JButton();
	JButton btn_Teal = new JButton();
	JButton btn_Navy = new JButton();
	JButton btn_Fuchsia = new JButton();
	JButton btn_Red = new JButton();
	JButton btn_Yellow = new JButton();
	JButton btn_Lime = new JButton();
	JButton btn_Aqua = new JButton();
	JButton btn_Blue = new JButton();
	JButton btn_Gray = new JButton();
	JButton btn_Silver = new JButton();
	JButton btn_etc = new JButton("..."); // 기타버튼, 컬러 다이얼로그 사용
	JButton clearAll = new JButton("CLEARALL"); // 화면 지우기 버튼
	JCheckBox fillBox = new JCheckBox("Fill"); // 채우기 체크박스

	JColorChooser chooser = new JColorChooser(); // 컬러 다이얼로그

	private int x, y, x1, y1; // 마우스를 눌렀을 때와 떼었을 때 각 좌표값
	private Vector<Draw> vc = new Vector<Draw>(); // 벡터를 저장할 필드

	Image a; // 더블 버퍼링 구현을 위한 객체
	Graphics g; // 더블 버퍼링 구현을 위한 객체

	// 화면분할
	private Panel p = new Panel(); // 버튼 및 체크박스를 등록할 패널

	public PicturePanel(String title) {
		super(title);
		setLayout(new FlowLayout(FlowLayout.RIGHT));// FlowLayout으로 패널을 오른쪽으로 배치

		this.init(); // 화면구성용 메소드
		this.start(); // 이벤트용 메소드

		// window의 크기, 위치조정
		super.setSize(700, 700);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frm = super.getSize();
		int xpos = (int) (screen.getWidth() / 2 - frm.getWidth() / 2);
		int ypos = (int) (screen.getHeight() / 2 - frm.getHeight() / 2);
		super.setLocation(xpos, ypos);
		super.setResizable(false);
		super.setVisible(true);
	}

	// 레이아웃 화면구성
	public void init() {
		// 메뉴바와 메뉴 등록
		draw.add(pen); // pen
		draw.add(line); // line
		draw.add(oval); // oval
		draw.add(rect); // rect
		draw.add(triangle); // triangle
		draw.add(pentagon); // pentagon
		draw.add(eraser); // eraser
		draw.add(textBox); // text
		draw.add(hexagon); // hexagon
		mb.add(draw); // draw

		// 색깔, 버튼, 체크메뉴를 등록할 새로운 패널
		add(p);
		// 레이아웃 설정 및 등록
		this.setLayout(new BorderLayout());
		this.add(p, BorderLayout.NORTH);

		// 색깔 버튼의 배경색과 사이즈 지정
		btn_Black.setBackground(Color.BLACK);
		p.add(btn_Black);
		btn_Black.setPreferredSize(new Dimension(20, 20));
		btn_Maroon.setBackground(new Color(128, 0, 0));
		p.add(btn_Maroon);
		btn_Maroon.setPreferredSize(new Dimension(20, 20));
		btn_Purple.setBackground(new Color(128, 0, 128));
		p.add(btn_Purple);
		btn_Purple.setPreferredSize(new Dimension(20, 20));
		btn_Olive.setBackground(new Color(128, 128, 0));
		p.add(btn_Olive);
		btn_Olive.setPreferredSize(new Dimension(20, 20));
		btn_Green.setBackground(new Color(0, 128, 0));
		p.add(btn_Green);
		btn_Green.setPreferredSize(new Dimension(20, 20));
		btn_Teal.setBackground(new Color(0, 128, 128));
		p.add(btn_Teal);
		btn_Teal.setPreferredSize(new Dimension(20, 20));
		btn_Navy.setBackground(new Color(0, 0, 128));
		p.add(btn_Navy);
		btn_Navy.setPreferredSize(new Dimension(20, 20));
		btn_Fuchsia.setBackground(new Color(255, 0, 255));
		p.add(btn_Fuchsia);
		btn_Fuchsia.setPreferredSize(new Dimension(20, 20));
		btn_Red.setBackground(new Color(255, 0, 0));
		p.add(btn_Red);
		btn_Red.setPreferredSize(new Dimension(20, 20));
		btn_Yellow.setBackground(new Color(255, 255, 0));
		p.add(btn_Yellow);
		btn_Yellow.setPreferredSize(new Dimension(20, 20));
		btn_Lime.setBackground(new Color(0, 255, 0));
		p.add(btn_Lime);
		btn_Lime.setPreferredSize(new Dimension(20, 20));
		btn_Aqua.setBackground(new Color(0, 255, 255));
		p.add(btn_Aqua);
		btn_Aqua.setPreferredSize(new Dimension(20, 20));
		btn_Blue.setBackground(new Color(0, 0, 255));
		p.add(btn_Blue);
		btn_Blue.setPreferredSize(new Dimension(20, 20));
		btn_Gray.setBackground(new Color(128, 128, 128));
		p.add(btn_Gray);
		btn_Gray.setPreferredSize(new Dimension(20, 20));
		btn_Silver.setBackground(new Color(192, 192, 192));
		p.add(btn_Silver);
		btn_Silver.setPreferredSize(new Dimension(20, 20));
		p.add(btn_etc);
		btn_etc.setPreferredSize(new Dimension(20, 20));

		p.setCursor(new Cursor(Cursor.HAND_CURSOR)); // 패널에 커서 부착 패널 위에 커서가 올라오면
														// 손 모양으로 커서 모양을 바꾸어 준다.
		p.setBackground(Color.WHITE);// 패널의 배경 색을 WHITE로 설정
		// 화면 지우기 버튼, 채우기 체크박스 등록
		p.add(clearAll);
		p.add(fillBox);
		this.setMenuBar(mb); // 메뉴바

		// 버튼의 액션 리스너 구현
		btn_Black.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				color = new Color(0, 0, 0);
			}
		});
		btn_Maroon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				color = new Color(128, 0, 0);
			}
		});
		btn_Purple.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				color = new Color(128, 0, 128);
			}
		});
		btn_Olive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				color = new Color(128, 128, 0);
			}
		});
		btn_Green.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				color = new Color(0, 128, 0);
			}
		});
		btn_Teal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				color = new Color(0, 128, 128);
			}
		});
		btn_Navy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				color = new Color(0, 0, 128);
			}
		});
		btn_Fuchsia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				color = new Color(255, 0, 255);
			}
		});
		btn_Red.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				color = new Color(255, 0, 0);
			}
		});
		btn_Yellow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				color = new Color(255, 255, 0);
			}
		});
		btn_Lime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				color = new Color(0, 255, 0);
			}
		});
		btn_Aqua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				color = new Color(0, 255, 255);
			}
		});
		btn_Blue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				color = new Color(0, 0, 255);
			}
		});
		btn_Gray.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				color = new Color(128, 128, 128);
			}
		});
		btn_Silver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				color = new Color(192, 192, 192);
			}
		});
		btn_etc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				color = chooser.showDialog(null, "Color", color);
			}
		});
	}

	// 이벤트 구성
	public void start() {
		// window의 X버튼을 누르면 window를 종료하라
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		// 선택된 메뉴만 체크되도록 하는 이벤트
		pen.addItemListener(this);
		line.addItemListener(this);
		oval.addItemListener(this);
		rect.addItemListener(this);
		triangle.addItemListener(this);
		pentagon.addItemListener(this);
		eraser.addItemListener(this);
		textBox.addItemListener(this);
		clearAll.addActionListener(this);
		fillBox.addActionListener(this);
		hexagon.addItemListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);// 마우스가 움직이는 동안 그려지는 모양을 위한 이벤트
	}

	// 그림을 위한 메소드
	public void paint(Graphics g2) {
		if (a == null)
			a = createImage(getWidth(), getHeight());// 백버퍼 생성
		if (g == null)
			g = a.getGraphics();// 백버퍼의 Graphics 객체를 get

		g.setColor(Color.WHITE); // 배경을 WHITE로 지정
		g.fillRect(0, 0, getWidth(), getHeight()); // 시작점부터 프레임의 너비, 높이만큼 채움

		int x2 = x > x1 ? x1 : x; // oval, rect를 그릴 때 시작 x의 좌표값
		int y2 = y > y1 ? y1 : y; // oval, rect를 그릴 때 시작 y의 좌표값
		int x3 = x > x1 ? x : x1; // oval, rect를 그릴 때 나중 x의 좌표값
		int y3 = y > y1 ? y : y1; // oval, rect를 그릴 때 나중 y의 좌표값

		// 그림그리기
		g.setColor(color);

		// 마우스를 눌렀다 떼었을때
		if (line.getState() == true) {// 라인을 체크하면
			g.drawLine(x, y, x1, y1);// x,y좌표에서 x1,y1좌표에 라인을 그려라
		} else if (oval.getState() == true) { // oval을 체크하면
			if (!fill) // fill이 false일 때
				g.drawOval(x2, y2, x3 - x2, y3 - y2);// oval을 그려라
			else
				g.fillOval(x2, y2, x3 - x2, y3 - y2);// oval을 채워라
		} else if (rect.getState() == true) { // rect를 체크하면
			if (!fill) // fill이 false일 때
				g.drawRect(x2, y2, x3 - x2, y3 - y2);// rect를 그려라
			else
				g.fillRect(x2, y2, x3 - x2, y3 - y2);// rect를 채워라
		} else if (triangle.getState() == true) { // triangle을 체크하면
			int[] xx = { (x + x1) / 2, x, x1 }; // 좌표 설정
			int[] yy = { y, y1, y1 }; // 좌표 설정
			if (!fill) // fill이 false일 때
				g.drawPolygon(xx, yy, 3); // triangle을 그려라
			else
				g.fillPolygon(xx, yy, 3); // triangle을 채워라
		} else if (pentagon.getState() == true) {
			int[] xx = { (x2 + x3) / 2, x2, (x2 + x3) / 4, (x2 + x3) * 3 / 4, x3 }; // 좌표
																					// 설정
			int[] yy = { y2, (y2 + y3) / 2, y3, y3, (y2 + y3) / 2 }; // 좌표 설정
			if (!fill) // fill이 false일 때
				g.drawPolygon(xx, yy, 5); // pentagon을 그려라
			else
				g.fillPolygon(xx, yy, 5); // pentagon을 그려라
		} else if (eraser.getState() == true) { // eraser을 체크하면
			g.clearRect(x - 15, y - 15, 30, 30); // 30픽셀만큼 지워라
		} else if (textBox.getState() == true) { // textBox를 체크하면
			g.drawString("Java", x2, y2); // 텍스트를 그려라
		} else if (hexagon.getState() == true) {
			int[] xx = { (x2 + x3) / 4, x2, (x2 + x3) / 4, (x2 + x3) / 4 * 3, x3, (x2 + x3) / 4 * 3 };
			int[] yy = { y2, (y2+y3) / 2, y3, y3, (y2+y3) / 2, y2 };
			if (!fill) // fill이 false일 때
				g.drawPolygon(xx, yy, 6); // hexagon을 그려라
			else
				g.fillPolygon(xx, yy, 6); // hexagon을 그려라
		}

		// 위의 경우와 동일 각각이 체크되었을 때 그려주는 메소드 호출
		for (int i = 0; i < vc.size(); ++i) {
			Draw d = (Draw) vc.elementAt(i);

			g.setColor(d.getColor()); // 색깔 설정

			if (d.getDist() == 1) {
				g.drawLine(d.getX(), d.getY(), d.getX1(), d.getY1());
			} else if (d.getDist() == 2) {
				if (!d.isFill())
					g.drawOval(d.getX(), d.getY(), d.getX1() - d.getX(), d.getY1() - d.getY());
				else
					g.fillOval(d.getX(), d.getY(), d.getX1() - d.getX(), d.getY1() - d.getY());
			} else if (d.getDist() == 3) {
				if (!d.isFill())
					g.drawRect(d.getX(), d.getY(), d.getX1() - d.getX(), d.getY1() - d.getY());
				else
					g.fillRect(d.getX(), d.getY(), d.getX1() - d.getX(), d.getY1() - d.getY());
			} else if (d.getDist() == 4) {
				int[] xx = { (d.getX() + d.getX1()) / 2, d.getX(), d.getX1() };
				int[] yy = { d.getY(), d.getY1(), d.getY1() };
				if (!d.isFill())
					g.drawPolygon(xx, yy, 3);
				else
					g.fillPolygon(xx, yy, 3);
			} else if (d.getDist() == 5) {
				int[] xx = { (d.getX() + d.getX1()) / 2, d.getX(), (d.getX() + d.getX1()) / 4,
						(d.getX() + d.getX1()) * 3 / 4, d.getX1() };
				int[] yy = { d.getY(), (d.getY() + d.getY1()) / 2, d.getY1(), d.getY1(), (d.getY() + d.getY1()) / 2 };
				if (!d.isFill())
					g.drawPolygon(xx, yy, 5);
				else
					g.fillPolygon(xx, yy, 5);
			} else if (d.getDist() == 6) {
				g.clearRect(d.getX() - 15, d.getY() - 15, 30, 30);
			} else if (d.getDist() == 7) {
				g.drawString("Java", d.getX(), d.getY());
			} else if (d.getDist() == 8) {
				int[] xx = { (d.getX() + d.getX1()) / 4, d.getX(), (d.getX() + d.getX1()) / 4,
						(d.getX() + d.getX1()) / 4 * 3, d.getX1(), (d.getX() + d.getX1()) / 4 * 3 };
				int[] yy = { d.getY(), (d.getY()+d.getY1()) / 2, d.getY1(), d.getY1(), (d.getY()+d.getY1()) / 2, d.getY() };
				if (!d.isFill())
					g.drawPolygon(xx, yy, 6);
				else
					g.fillPolygon(xx, yy, 6);
			}
		}

		// 원래 Graphics에 그려줌
		g2.drawImage(a, 0, 0, this);

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == clearAll) { // clearAll 버튼에 액션 발생하면
			vc.clear(); // vc에 저장한 벡터들을 clear

			x = y = x1 = y1 = 0; // 좌표를 0으로 설정

			this.repaint(); // 다시 그린다
		}

		if (e.getSource() == fillBox) { // fillBox 체크메뉴에 액션이 발생하면
			if (fillBox.isSelected() == true)
				fill = true;
			else
				fill = false;
		}
	}

	public void update(Graphics g) { // 더블버퍼링 구현을 위한 메소드
		paint(g);
	}

	// 선택된 메뉴만 체크되도록 하는 이벤트
	public void itemStateChanged(ItemEvent e) {
		pen.setState(false);
		line.setState(false);
		oval.setState(false);
		rect.setState(false);
		triangle.setState(false);
		pentagon.setState(false);
		eraser.setState(false);
		textBox.setState(false);
		hexagon.setState(false);
		CheckboxMenuItem cb = (CheckboxMenuItem) e.getSource();
		cb.setState(true);
	}

	// 마우스 사용을 위한 메소드
	public void mouseClicked(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) { // 눌렀을때
		x = e.getX(); // x의 좌표값을 얻어내어
		y = e.getY(); // y의 좌표값을 얻어내어
	}

	public void mouseReleased(MouseEvent e) { // 떼었을때
		x1 = e.getX(); // x1의 좌표값
		y1 = e.getY(); // y1의 좌표값

		int x2 = x > x1 ? x1 : x; // oval, rect를 그릴 때 시작 x의 좌표값
		int y2 = y > y1 ? y1 : y; // oval, rect를 그릴 때 시작 y의 좌표값
		int x3 = x > x1 ? x : x1; // oval, rect를 그릴 때 폭
		int y3 = y > y1 ? y : y1; // oval, rect를 그릴 때 높이

		this.repaint(); // 그림을 다시 그린다

		if (pen.getState() != true) { // pen이 true가 아닐때에만 아래를 실행하라
			int dist = 0;
			if (line.getState() == true)
				dist = 1; // line가 체크되면 1값을 대입
			else if (oval.getState() == true)
				dist = 2; // oval가 체크되면 2값을 대입
			else if (rect.getState() == true)
				dist = 3; // rect가 체크되면 3값을 대입
			else if (triangle.getState() == true)
				dist = 4; // triangle이 체크되면 4값을 대입
			else if (pentagon.getState() == true)
				dist = 5; // pentagon이 체크되면 5값을 대입
			else if (eraser.getState() == true)
				dist = 6; // eraser가 체크되면 6값을 대입
			else if (textBox.getState() == true)
				dist = 7; // textBox가 체크되면 7값을 대입
			else if (hexagon.getState() == true)
				dist = 8;

			Draw d;

			if (dist == 2 || dist == 3 || dist == 5 || dist == 8) { // oval,
																	// rect,
																	// pentagon,
																	// hexagon이
				// 체크되어있으면
				d = new Draw(x2, y2, x3, y3, dist, fillBox.isSelected(), color);
			} else {
				d = new Draw(x, y, x1, y1, dist, fillBox.isSelected(), color);
			}

			d.setDist(dist); // dist 값 대입
			vc.add(d);
		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	// 마우스가 움직이는 동안 그려지는 모양 보이기
	public void mouseDragged(MouseEvent e) {
		x1 = e.getX();
		y1 = e.getY();

		// pen 그려지는 이벤트
		if (pen.getState()) {
			Draw d = new Draw(x, y, x1, y1, 1, fillBox.isSelected(), color);
			d.setX(x);
			d.setY(y);
			vc.add(d);
			x = x1;
			y = y1;
		} else if (eraser.getState()) { // eraser 이벤트
			Draw d = new Draw(x, y, x1, y1, 6, fillBox.isSelected(), Color.WHITE);
			d.setX(x);
			d.setY(y);
			vc.add(d);
			x = x1;
			y = y1;
		}
		this.repaint();
	}

	public void mouseMoved(MouseEvent e) {
	}
} // class end

public class ExamOne {
	public static void main(String[] args) {
		PicturePanel pp = new PicturePanel("그림판");
	}
}