package picturePanel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*; //����

class Draw implements Serializable {
	int x, y; // ��ġ�� ������ ������ �ʵ�
	int dist; // ���� Ÿ���� ������ ������ �ʵ�(line�� ��� ���� ��ǥ��)
	int x1, y1; // ������ ���� ��ġ�� ������ ������ �ʵ�
	boolean fill; // true�� ��ȯ�� ä���
	Color color; // ������ �� ������ ��Ÿ���� Color�� �ʵ�

	// ���� �ʵ���� get, set�ϴ� �޼ҵ�
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

	// ���� �ϳ��ϳ��� ��ü�� ���Ϳ� ����, ��ü���� ������ ��(��ǥ, Ÿ��, �� ��)�� ����
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

	Color color = new Color(0, 0, 0); // ������ ��Ÿ���� �ʵ�

	boolean fill = false; // ä��⸦ ��Ÿ�� �ʵ�

	// ȭ�鱸����
	private MenuBar mb = new MenuBar(); // �޴���
	private Menu draw = new Menu("DRAW"); // �޴� DRAW

	// üũ�ڽ� ����޴�
	private CheckboxMenuItem pen = new CheckboxMenuItem("PEN", true); // �⺻üũ
	private CheckboxMenuItem line = new CheckboxMenuItem("LINE"); // ����
	private CheckboxMenuItem oval = new CheckboxMenuItem("OVAL"); // Ÿ��
	private CheckboxMenuItem rect = new CheckboxMenuItem("RECT"); // �簢��
	private CheckboxMenuItem triangle = new CheckboxMenuItem("TRIANGLE"); // �ﰢ��
	private CheckboxMenuItem pentagon = new CheckboxMenuItem("PENTAGON"); // ������
	private CheckboxMenuItem eraser = new CheckboxMenuItem("ERASER"); // ���찳
	private CheckboxMenuItem textBox = new CheckboxMenuItem("TEXT"); // �ؽ�Ʈ �Է�
	private CheckboxMenuItem hexagon = new CheckboxMenuItem("hexagon"); // ������

	// ���� ��ư
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
	JButton btn_etc = new JButton("..."); // ��Ÿ��ư, �÷� ���̾�α� ���
	JButton clearAll = new JButton("CLEARALL"); // ȭ�� ����� ��ư
	JCheckBox fillBox = new JCheckBox("Fill"); // ä��� üũ�ڽ�

	JColorChooser chooser = new JColorChooser(); // �÷� ���̾�α�

	private int x, y, x1, y1; // ���콺�� ������ ���� ������ �� �� ��ǥ��
	private Vector<Draw> vc = new Vector<Draw>(); // ���͸� ������ �ʵ�

	Image a; // ���� ���۸� ������ ���� ��ü
	Graphics g; // ���� ���۸� ������ ���� ��ü

	// ȭ�����
	private Panel p = new Panel(); // ��ư �� üũ�ڽ��� ����� �г�

	public PicturePanel(String title) {
		super(title);
		setLayout(new FlowLayout(FlowLayout.RIGHT));// FlowLayout���� �г��� ���������� ��ġ

		this.init(); // ȭ�鱸���� �޼ҵ�
		this.start(); // �̺�Ʈ�� �޼ҵ�

		// window�� ũ��, ��ġ����
		super.setSize(700, 700);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frm = super.getSize();
		int xpos = (int) (screen.getWidth() / 2 - frm.getWidth() / 2);
		int ypos = (int) (screen.getHeight() / 2 - frm.getHeight() / 2);
		super.setLocation(xpos, ypos);
		super.setResizable(false);
		super.setVisible(true);
	}

	// ���̾ƿ� ȭ�鱸��
	public void init() {
		// �޴��ٿ� �޴� ���
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

		// ����, ��ư, üũ�޴��� ����� ���ο� �г�
		add(p);
		// ���̾ƿ� ���� �� ���
		this.setLayout(new BorderLayout());
		this.add(p, BorderLayout.NORTH);

		// ���� ��ư�� ������ ������ ����
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

		p.setCursor(new Cursor(Cursor.HAND_CURSOR)); // �гο� Ŀ�� ���� �г� ���� Ŀ���� �ö����
														// �� ������� Ŀ�� ����� �ٲپ� �ش�.
		p.setBackground(Color.WHITE);// �г��� ��� ���� WHITE�� ����
		// ȭ�� ����� ��ư, ä��� üũ�ڽ� ���
		p.add(clearAll);
		p.add(fillBox);
		this.setMenuBar(mb); // �޴���

		// ��ư�� �׼� ������ ����
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

	// �̺�Ʈ ����
	public void start() {
		// window�� X��ư�� ������ window�� �����϶�
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		// ���õ� �޴��� üũ�ǵ��� �ϴ� �̺�Ʈ
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
		this.addMouseMotionListener(this);// ���콺�� �����̴� ���� �׷����� ����� ���� �̺�Ʈ
	}

	// �׸��� ���� �޼ҵ�
	public void paint(Graphics g2) {
		if (a == null)
			a = createImage(getWidth(), getHeight());// ����� ����
		if (g == null)
			g = a.getGraphics();// ������� Graphics ��ü�� get

		g.setColor(Color.WHITE); // ����� WHITE�� ����
		g.fillRect(0, 0, getWidth(), getHeight()); // ���������� �������� �ʺ�, ���̸�ŭ ä��

		int x2 = x > x1 ? x1 : x; // oval, rect�� �׸� �� ���� x�� ��ǥ��
		int y2 = y > y1 ? y1 : y; // oval, rect�� �׸� �� ���� y�� ��ǥ��
		int x3 = x > x1 ? x : x1; // oval, rect�� �׸� �� ���� x�� ��ǥ��
		int y3 = y > y1 ? y : y1; // oval, rect�� �׸� �� ���� y�� ��ǥ��

		// �׸��׸���
		g.setColor(color);

		// ���콺�� ������ ��������
		if (line.getState() == true) {// ������ üũ�ϸ�
			g.drawLine(x, y, x1, y1);// x,y��ǥ���� x1,y1��ǥ�� ������ �׷���
		} else if (oval.getState() == true) { // oval�� üũ�ϸ�
			if (!fill) // fill�� false�� ��
				g.drawOval(x2, y2, x3 - x2, y3 - y2);// oval�� �׷���
			else
				g.fillOval(x2, y2, x3 - x2, y3 - y2);// oval�� ä����
		} else if (rect.getState() == true) { // rect�� üũ�ϸ�
			if (!fill) // fill�� false�� ��
				g.drawRect(x2, y2, x3 - x2, y3 - y2);// rect�� �׷���
			else
				g.fillRect(x2, y2, x3 - x2, y3 - y2);// rect�� ä����
		} else if (triangle.getState() == true) { // triangle�� üũ�ϸ�
			int[] xx = { (x + x1) / 2, x, x1 }; // ��ǥ ����
			int[] yy = { y, y1, y1 }; // ��ǥ ����
			if (!fill) // fill�� false�� ��
				g.drawPolygon(xx, yy, 3); // triangle�� �׷���
			else
				g.fillPolygon(xx, yy, 3); // triangle�� ä����
		} else if (pentagon.getState() == true) {
			int[] xx = { (x2 + x3) / 2, x2, (x2 + x3) / 4, (x2 + x3) * 3 / 4, x3 }; // ��ǥ
																					// ����
			int[] yy = { y2, (y2 + y3) / 2, y3, y3, (y2 + y3) / 2 }; // ��ǥ ����
			if (!fill) // fill�� false�� ��
				g.drawPolygon(xx, yy, 5); // pentagon�� �׷���
			else
				g.fillPolygon(xx, yy, 5); // pentagon�� �׷���
		} else if (eraser.getState() == true) { // eraser�� üũ�ϸ�
			g.clearRect(x - 15, y - 15, 30, 30); // 30�ȼ���ŭ ������
		} else if (textBox.getState() == true) { // textBox�� üũ�ϸ�
			g.drawString("Java", x2, y2); // �ؽ�Ʈ�� �׷���
		} else if (hexagon.getState() == true) {
			int[] xx = { (x2 + x3) / 4, x2, (x2 + x3) / 4, (x2 + x3) / 4 * 3, x3, (x2 + x3) / 4 * 3 };
			int[] yy = { y2, (y2+y3) / 2, y3, y3, (y2+y3) / 2, y2 };
			if (!fill) // fill�� false�� ��
				g.drawPolygon(xx, yy, 6); // hexagon�� �׷���
			else
				g.fillPolygon(xx, yy, 6); // hexagon�� �׷���
		}

		// ���� ���� ���� ������ üũ�Ǿ��� �� �׷��ִ� �޼ҵ� ȣ��
		for (int i = 0; i < vc.size(); ++i) {
			Draw d = (Draw) vc.elementAt(i);

			g.setColor(d.getColor()); // ���� ����

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

		// ���� Graphics�� �׷���
		g2.drawImage(a, 0, 0, this);

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == clearAll) { // clearAll ��ư�� �׼� �߻��ϸ�
			vc.clear(); // vc�� ������ ���͵��� clear

			x = y = x1 = y1 = 0; // ��ǥ�� 0���� ����

			this.repaint(); // �ٽ� �׸���
		}

		if (e.getSource() == fillBox) { // fillBox üũ�޴��� �׼��� �߻��ϸ�
			if (fillBox.isSelected() == true)
				fill = true;
			else
				fill = false;
		}
	}

	public void update(Graphics g) { // ������۸� ������ ���� �޼ҵ�
		paint(g);
	}

	// ���õ� �޴��� üũ�ǵ��� �ϴ� �̺�Ʈ
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

	// ���콺 ����� ���� �޼ҵ�
	public void mouseClicked(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) { // ��������
		x = e.getX(); // x�� ��ǥ���� ����
		y = e.getY(); // y�� ��ǥ���� ����
	}

	public void mouseReleased(MouseEvent e) { // ��������
		x1 = e.getX(); // x1�� ��ǥ��
		y1 = e.getY(); // y1�� ��ǥ��

		int x2 = x > x1 ? x1 : x; // oval, rect�� �׸� �� ���� x�� ��ǥ��
		int y2 = y > y1 ? y1 : y; // oval, rect�� �׸� �� ���� y�� ��ǥ��
		int x3 = x > x1 ? x : x1; // oval, rect�� �׸� �� ��
		int y3 = y > y1 ? y : y1; // oval, rect�� �׸� �� ����

		this.repaint(); // �׸��� �ٽ� �׸���

		if (pen.getState() != true) { // pen�� true�� �ƴҶ����� �Ʒ��� �����϶�
			int dist = 0;
			if (line.getState() == true)
				dist = 1; // line�� üũ�Ǹ� 1���� ����
			else if (oval.getState() == true)
				dist = 2; // oval�� üũ�Ǹ� 2���� ����
			else if (rect.getState() == true)
				dist = 3; // rect�� üũ�Ǹ� 3���� ����
			else if (triangle.getState() == true)
				dist = 4; // triangle�� üũ�Ǹ� 4���� ����
			else if (pentagon.getState() == true)
				dist = 5; // pentagon�� üũ�Ǹ� 5���� ����
			else if (eraser.getState() == true)
				dist = 6; // eraser�� üũ�Ǹ� 6���� ����
			else if (textBox.getState() == true)
				dist = 7; // textBox�� üũ�Ǹ� 7���� ����
			else if (hexagon.getState() == true)
				dist = 8;

			Draw d;

			if (dist == 2 || dist == 3 || dist == 5 || dist == 8) { // oval,
																	// rect,
																	// pentagon,
																	// hexagon��
				// üũ�Ǿ�������
				d = new Draw(x2, y2, x3, y3, dist, fillBox.isSelected(), color);
			} else {
				d = new Draw(x, y, x1, y1, dist, fillBox.isSelected(), color);
			}

			d.setDist(dist); // dist �� ����
			vc.add(d);
		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	// ���콺�� �����̴� ���� �׷����� ��� ���̱�
	public void mouseDragged(MouseEvent e) {
		x1 = e.getX();
		y1 = e.getY();

		// pen �׷����� �̺�Ʈ
		if (pen.getState()) {
			Draw d = new Draw(x, y, x1, y1, 1, fillBox.isSelected(), color);
			d.setX(x);
			d.setY(y);
			vc.add(d);
			x = x1;
			y = y1;
		} else if (eraser.getState()) { // eraser �̺�Ʈ
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
		PicturePanel pp = new PicturePanel("�׸���");
	}
}