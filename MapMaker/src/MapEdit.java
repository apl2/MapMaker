import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MapEdit extends JFrame {
	public static final Color NIGHT_SKY = // new Color(24, 24, 61);
	Color.black;
	public static final Color DAY_SKY = new Color(67, 67, 103);
	public static final Color TWILIGHT_SKY = new Color(40, 40, 140);
	public static final Color PURPLE = new Color(128, 0, 128);
	public static final Color ORANGE = new Color(254, 83, 1);
	public static final Color BROWN = new Color(128, 41, 0);
	public static final Color TAN = new Color(221, 182, 108);
	public static final Color LIGHT_GREEN = new Color(120, 255, 0);
	public static final Color OFF_GREEN = new Color(148, 190, 50);
	public static final Color LIGHT_OFF_GREEN = new Color(167, 207, 73);
	public static final Color LIGHT_BLUE = new Color(132, 255, 255);
	public static final Font ACHANGE = new Font("Algerian", Font.PLAIN, 60);
	String name;
	MapEdit mapEdit = this;
	JPanel drawPan;
	int x = 0;
	int y = 0;
	String project;
	String[] strings;
	int xd = 0;
	int yd = 0;
	char selChar = 'W';
	int selNum = 2;
	boolean blocks = true;
	boolean enPre = false;
	ArrayList<String> enemies = new ArrayList<String>();
boolean zoom=false;
boolean choosing =false;
	public MapEdit(String name, String project) {
		// TODO Auto-generated constructor stub
		
		this.name = name;
		this.project = project;
		// this.imUrl=imUrl;
	}

	public void run() {
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setUndecorated(true);
		JOptionPane.showMessageDialog(mapEdit, "Space/Click: place\n"
				+ "Up/Down/Scroll Wheel: change selected block/enemy\n"
				+ "E/Right Click: swap between enemies and blocks\n"
				+ "Q/Middle Mouse Button: change enemy placement precision\n"
				+ "R: delete enemy\n"
				+ "Z: zoom out/back\n"
				+ "X: remove (Opens Menu)\n"
				+ "C: add (Opens Menu)\n"
				+ "I: info");
		loadS();

		// TODO Auto-generated method stub
		// this.setBackground(Color.white);
		addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ESCAPE) {

					MapMaker.saveStrings(strings, name, project);
					MapMaker.saveStrings(enemies, nameWithE(), project);
					mapEdit.dispose();
				}
				else if(key==KeyEvent.VK_I){
					JOptionPane.showMessageDialog(mapEdit, "Space/Click: place\n"
							+ "Up/Down/Scroll Wheel: change selected block/enemy\n"
							+ "E/Right Click: swap between enemies and blocks\n"
							+ "Q/Middle Mouse Button: change enemy placement precision\n"
							+ "R: delete enemy\n"
							+ "Z: zoom out/back\n"
							+ "X: remove (Opens Menu)\n"
							+ "C: add (Opens Menu)\n"
							+ "I: info");
				}
				else if(key==KeyEvent.VK_C&&zoom){
					
					String[]options={"Cancel","Add row at the end","Add column at the end"};
					choosing=true;
					int sel=JOptionPane.showOptionDialog(mapEdit, "What and where would you like to add?\n If you want to add things in the middle, get out of zooming out.", "Add", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, 0);
				choosing=false;
					switch(sel){
				
				case 1:
					addRowEnd();
					break;
				
				case 2:
					addColEnd();
					break;
				default:	
				}
				
				}else if(key==KeyEvent.VK_X&&zoom){
					String[]options={"Cancel","Remove row at the end","Remove column at the end"};
					choosing=true;
					JFrame f=new JFrame();
					f.setLocation(mapEdit.getX()+mapEdit.getWidth()/2, 0);
					//f.setSize(0,0);
					f.setVisible(true);
					int sel=JOptionPane.showOptionDialog(f, "What and where would you like to add?", "Add", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, 0);
			f.dispose();
					choosing=false;
					switch(sel){
				
				case 1:
					remRowEnd();
					break;
				
				case 2:
					remColEnd();
					break;
				default:	
				}
				
				}
				else if (key == KeyEvent.VK_Z) {
					zoom=!zoom;

				}else if(!zoom){
				if (key == KeyEvent.VK_S) {
					yd = 25;
				} else if (key == KeyEvent.VK_W) {
					yd = -25;
				} else if (key == KeyEvent.VK_A) {
					xd = -25;
				} else if (key == KeyEvent.VK_D) {
					xd = 25;
				}
				else if(key==KeyEvent.VK_X){
					String[]options={"Cancel","Remove row here","Remove row at the end","Remove column here","Remove column at the end"};
					choosing=true;
					JFrame f=new JFrame();
					f.setLocation(mapEdit.getX()+mapEdit.getWidth()/2, 0);
					//f.setSize(0,0);
					f.setVisible(true);
					int sel=JOptionPane.showOptionDialog(f, "What and where would you like to add?", "Add", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, 0);
			f.dispose();
					choosing=false;
					switch(sel){
				case 1:
					remRowMid();
					break;
				case 2:
					remRowEnd();
					break;
				case 3:
					remColMid();
					break;
				case 4:
					remColEnd();
					break;
				default:	
				}
				
				}
				else if(key==KeyEvent.VK_C){
					String[]options={"Cancel","Add row here","Add row at the end","Add column here","Add column at the end"};
					choosing=true;
					JFrame f=new JFrame();
					f.setLocation(mapEdit.getX()+mapEdit.getWidth()/2, 0);
					//f.setSize(0,0);
					f.setVisible(true);
					int sel=JOptionPane.showOptionDialog(f, "What and where would you like to add?", "Add", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, 0);
			f.dispose();
					choosing=false;
					switch(sel){
				case 1:
					addRowMid();
					break;
				case 2:
					addRowEnd();
					break;
				case 3:
					addColMid();
					break;
				case 4:
					addColEnd();
					break;
				default:	
				}
				
				}
				else if (key == KeyEvent.VK_SPACE) {
					
					if (blocks) {
						Point m = MouseInfo.getPointerInfo().getLocation();
						String s = strings[(int) ((y + m.getY()-10) / 100)];
						s = s.substring(0, (int) ((x + m.getX()+10) / 100))
								+ selChar
								+ s.substring((int) ((x + m.getX()+10) / 100) + 1);

						strings[(int) ((y -10+ m.getY()) / 100)] = s;
					} else {

						Point m = MouseInfo.getPointerInfo().getLocation();
						int sen = 100;
						if (!enPre) {
							sen = 50;
						}
						int theX = ((int) ((m.x + x) / sen) * sen);
						int theY = ((int) ((m.y + y) / sen) * sen);
						if (!enPre) {
							theX -= 50;
							theY -= 50;
						}
						enemies.add((getSelChar() + "," + theX + "," + theY
								+ "," + getSelString() + "," + selFlying() +","+ selHealth()+(selDelay() != 0 ? ","
								+ selDelay()
								: "")));
					}
				}   else if (key == KeyEvent.VK_DOWN) {
					selNum++;
				} else if (key == KeyEvent.VK_UP) {
					selNum--;
				} else if (key == KeyEvent.VK_E) {
					blocks = !blocks;
					selNum = 0;
					selChar = 'S';
				} else if (key == KeyEvent.VK_Q) {
					enPre = !enPre;

				}
				
				else if (key == KeyEvent.VK_R) {
					Point m = MouseInfo.getPointerInfo().getLocation();
					for (int c = 0; c < enemies.size(); c++) {
						ArrayList<String> stuff = new ArrayList<String>();// should
																			// have
																			// 5
						String currentS = "";
						for (int c2 = 0; c2 < enemies.get(c).length(); c2++) {

							if (enemies.get(c).charAt(c2) == ',') {
								stuff.add(currentS);
								currentS = "";

							} else {
								currentS += enemies.get(c).charAt(c2);
							}
						}
						Image enImg = new ImageIcon(getClass().getResource(
								stuff.get(3))).getImage();
						if (new Rectangle(Integer.parseInt(stuff.get(1)),
								Integer.parseInt(stuff.get(2)),
								enImg.getWidth(null), enImg.getHeight(null))
								.contains(new Point(x + m.x, y + m.y))) {
							enemies.remove(c);
							c--;
						}
					}

				}
			}}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_S) {
					yd = 0;
				}
				if (e.getKeyCode() == KeyEvent.VK_W) {
					yd = 0;
				}
				if (e.getKeyCode() == KeyEvent.VK_A) {
					xd = 0;
				}
				if (e.getKeyCode() == KeyEvent.VK_D) {
					xd = 0;
				}
			}

		});
		this.addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				// TODO Auto-generated method stub
				int notches = e.getWheelRotation();

				try {
					Robot robot = new Robot();
					if (notches > 0) {
						robot.keyPress(KeyEvent.VK_DOWN);
					} else {
						robot.keyPress(KeyEvent.VK_UP);
					}
				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		this.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				MapMaker.saveStrings(strings, name, project);
				MapMaker.saveStrings(enemies, nameWithE(), project);
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}
		});
		drawPan = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				
int x=mapEdit.x;
int y=mapEdit.y;
g2d.clearRect(0, 0, this.getWidth(), this.getHeight());
g2d.setColor(OFF_GREEN);
g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
				if(zoom){
	x=0;
	y=0;
	int mWidth=0;
	for(int c=0;c<strings.length;c++){
		if(strings[c].length()>mWidth){
			mWidth=strings[c].length();
		}
	}
	
	mWidth*=100;
	int mHeight=strings.length*100;
	int scale=Math.max(mWidth, mHeight);
	g2d.scale((double)this.getWidth()/(double)scale, (double)this.getHeight()/(double)scale);
}
				
			
				
				for (int cA = 0; cA < strings.length; cA++) {
					for (int c = 0; c < strings[cA].length(); c++) {
						int side = (int) strings[cA].length();

						int width = 100;
						int height = 100;

						switch (strings[cA].charAt(c)) {

						case '1':
							g2d.setColor(LIGHT_OFF_GREEN);
							g2d.fill(new Rectangle(((c) * 100) - x + 40,
									((int) (cA * 100)) - y + 40, width - 80,
									height - 80));
							g2d.drawRect(((c) * 100) - x, ((int) (cA * 100))
									- y, width, height);
							break;
						case 'O':
							g2d.setColor(LIGHT_OFF_GREEN);
							g2d.fill(new Rectangle(((c % side) * 100) - x + 40,
									((int) ((int) (cA * 100))) - y + 40,
									width - 80, height - 80));
							g2d.drawRect(((c % side) * 100) - x,
									((int) ((int) (cA * 100))) - y, width,
									height);
							g2d.setColor(Color.black);
							g2d.drawString("Spawn",
									((c % side) * 100) - x + 40,
									((int) (cA * 100)) - y + 40);
							break;

						case 'W':
							g2d.setColor(Color.DARK_GRAY);
							g2d.fillRect(((c % side) * 100) - x,
									((int) (cA * 100)) - y, width, height);
							g2d.setColor(Color.BLACK);
							g2d.drawRect(((c % side) * 100) - x,
									((int) (cA * 100)) - y, width, height);
							break;

						case 'P':
							g2d.setColor(Color.black);
							g2d.fillRect(((c % side) * 100) - x,
									((int) (cA * 100)) - y, width, height);
							break;

						case 'R':
							g2d.setColor(Color.LIGHT_GRAY);
							g2d.fillRect(((c % side) * 100) - x,
									((int) (cA * 100)) - y, width, height);
							break;

						case 'C':
							g2d.setColor(TAN);
							g2d.fillRect(((c % side) * 100) - x,
									((int) (cA * 100)) - y, width, height);
							g2d.setColor(Color.RED);
							g2d.drawLine(((c % side) * 100) - x,
									((int) (cA * 100)) - y, ((c % side) * 100)
											- x + width, ((int) (cA * 100)) - y
											+ height);
							g2d.drawLine(((c % side) * 100) - x + width,
									((int) (cA * 100)) - y, ((c % side) * 100)
											- x, ((int) (cA * 100)) - y
											+ height);
							g2d.drawLine(((c % side) * 100) - x,
									((int) (cA * 100)) - y + height / 2,
									((c % side) * 100) - x + width,
									((int) (cA * 100)) - y + height / 2);
							g2d.drawLine(((c % side) * 100) - x + width / 2,
									((int) (cA * 100)) - y, ((c % side) * 100)
											- x + width / 2, ((int) (cA * 100))
											- y + height);
							break;

						case '*':
							g2d.setColor(LIGHT_BLUE);
							g2d.fillRect(((c % side) * 100) - x,
									((int) (cA * 100)) - y, width, height);
							break;

						case '>':
							Font f = g2d.getFont();
							g2d.setFont(ACHANGE);
							g2d.setColor(Color.LIGHT_GRAY);
							g2d.drawRect(((c % side) * 100) - x,
									((int) (cA * 100)) - y, width, height);
							g2d.setColor(Color.BLUE);
							g2d.drawString("<->", ((c % side) * 100) - x,
									((int) (cA * 100)) - y + 70);
							g2d.drawRect(((c % side) * 100) - x,
									((int) (cA * 100)) - y, width, height);
							g2d.setFont(f);
							break;
						}
					}

				}
				for (int c = 0; c < enemies.size(); c++) {
					ArrayList<String> stuff = new ArrayList<String>();// should
																		// have
																		// 5
					String currentS = "";
					for (int c2 = 0; c2 < enemies.get(c).length(); c2++) {

						if (enemies.get(c).charAt(c2) == ',') {
							stuff.add(currentS);
							currentS = "";

						} else {
							currentS += enemies.get(c).charAt(c2);
						}
					}
					if (currentS != "") {
						stuff.add(currentS);
					}
					try {
						int enX = Integer.parseInt(stuff.get(1));
						int enY = Integer.parseInt(stuff.get(2));
						char ch = stuff.get(0).charAt(0);

						Image enImg = new ImageIcon(getClass().getResource(
								stuff.get(3))).getImage();
						g2d.drawImage(enImg, enX - x, enY - y, mapEdit);
					} catch (IndexOutOfBoundsException ex) {
						ex.printStackTrace();
						enemies.remove(c);
						try {
							new Robot().keyPress(KeyEvent.VK_ESCAPE);
						} catch (Exception ex2) {
							ex.printStackTrace();
							System.exit(0);
						}
					}
				}
				Point m = MouseInfo.getPointerInfo().getLocation();
				if(choosing&&!zoom){
				
					int theX=((int)(this.getWidth()/100)*100)/2+(x%100);
					int theY=((int)(this.getHeight()/100)*100)/2+50+(y%100);
					g2d.setColor(Color.red);
					g2d.fillRect(theX-6, theY-6, 12, 12);
					g2d.setColor(Color.black);
					g2d.fillOval(theX-4, theY-4, 8, 8);
				}
				else if(!zoom){if (blocks) {
					g2d.setColor(Color.red);
					g2d.drawString("" + getSelShowStringBlocks(), m.x + 10, m.y - 10);
				} else {
					g2d.setColor(Color.black);
					if (enPre) {
						g2d.drawString("" + getSelShowString(),
								((int) ((m.x + x) / 100) * 100) + 50 - x,
								((int) ((m.y + y) / 100) * 100) + 50 - y);
					} else {
						g2d.drawString("" + getSelShowString(),
								((int) ((m.x + x) / 50) * 50 - x),
								((int) ((m.y + y) / 50) * 50 - y));
					}
				}

			}
			}
		};

		Timer timer = new Timer(100, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (blocks) {
					if (selNum < 0) {
						selNum = 7;
					}
					if (selNum > 7) {
						selNum = 0;
					}
					switch (selNum) {
					case 0:
						selChar = 'O';
						break;
					case 1:
						selChar = '1';
						break;
					case 2:
						selChar = 'W';
						break;
					case 3:
						selChar = 'P';
						break;
					case 4:
						selChar = 'R';
						break;
					case 5:
						selChar = 'C';
						break;
					case 6:
						selChar = '*';
						break;
					case 7:
						selChar = '>';
						break;
					}

				} else {
					if (selNum > 10) {
						selNum = 0;
					}
					if (selNum < 0) {
						selNum = 10;
					}
					switch (selNum) {
					case 0:
						selChar = 'G';
						break;
					case 1:
						selChar = 'P';
						break;
					case 2:
						selChar = 'T';
						break;

					case 3:
						selChar = 'M';
						break;
					case 4:
						selChar = '0';
						break;
					case 5:
						selChar = '1';
						break;

					case 6:
						selChar = 'B';
						break;
					case 7:
						selChar = 'C';
						break;
					case 8:
						selChar = 'c';
						break;
					case 9:
						selChar = 'F';
						break;
					case 10:
						selChar = 't';
						break;
					}
				}
				if(zoom){
					xd=0;
					yd=0;
				}
				x += xd * 2;
				y += yd * 2;
				drawPan.repaint();
			}
		});
		timer.start();
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == 1) {
					try {
						new Robot().keyPress(KeyEvent.VK_SPACE);
					} catch (AWTException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if (e.getButton() == 2) {
					try {
						new Robot().keyPress(KeyEvent.VK_Q);
					} catch (AWTException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if (e.getButton() == 3) {
					try {
						new Robot().keyPress(KeyEvent.VK_E);
					} catch (AWTException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				// System.out.println((x+e.getXOnScreen())/100);
				// System.out.println((y+e.getYOnScreen())/100);

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});

		this.add(drawPan);
		drawPan.repaint();
		this.setFocusable(true);
		this.requestFocus();
		this.setVisible(true);
	}

	private void loadS() {
		String[] strings;
		ArrayList<String> lines = new ArrayList<String>();
		try {
			File saveFile = new File("bin/projects/" + project + "/" + name);
			if (saveFile.exists()) {
				BufferedReader reader = new BufferedReader(new FileReader(
						saveFile));
				String line;

				while ((line = reader.readLine()) != null) {
					lines.add(line);
				}
				reader.close();
				int mL = 0;
				for (int c = 0; c < lines.size(); c++) {
					if (mL < lines.get(c).length()) {
						mL = lines.get(c).length();
					}
				}
				strings = new String[lines.size()];

				for (int c = 0; c < strings.length; c++) {
					strings[c] = "";
					for (int c2 = 0; c2 < mL; c2++) {
						if (lines.get(c).length() > c2) {
							strings[c] += lines.get(c).charAt(c2);
						} else {
							strings[c] += " ";
						}
					}
				}

				this.strings = strings;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		try {

			File saveFile = new File("bin/projects/" + project + "/"
					+ nameWithE());

			if (saveFile.exists()) {

				BufferedReader reader = new BufferedReader(new FileReader(
						saveFile));
				String line;

				while ((line = reader.readLine()) != null) {
					enemies.add(line);

				}
				reader.close();

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public String nameWithE() {
		// TODO Auto-generated method stub
		String fName = "";
		String extension = "";
		boolean ext = false;
		for (int c = 0; c < name.length(); c++) {
			if (ext) {
				extension += name.charAt(c);
			} else if (name.charAt(c) == '.') {
				ext = true;
				fName += "E.";
			} else {
				fName += name.charAt(c);
			}
		}

		fName += extension;
		return fName;
	}

	public ImageIcon createImageIcon(String path, String description) {
		java.net.URL imgURL = getClass().getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	// public URL getCor(int c) {
	// // TODO Auto-generated method stub
	// String s=arr.get(0).get(c);
	// for(int c2=0;c2<imUrl.size();c2++){
	// System.out.println(imUrl.get(c2));
	// if(Run.removeExtension(imUrl.get(c2).getPath()).equals(s)){
	// System.out.println("Return good");
	// return imUrl.get(c2);
	//
	// }
	// }
	// return null;
	// }

	private String getSelString() {

		switch (selChar) {
		case 'C':
			return "images/enemies/unique/cactus.png";
		case 'c':
			return "images/enemies/unique/chair.png";
		case 'F':
			return "images/enemies/unique/fishbowl.png";
		case 'G':
			return "images/enemies/unique/ghost.png";
		case 'M':
			return "images/enemies/unique/machineLaunch.png";
		case 'P':
			return "images/enemies/unique/pizza.png";
		case 'T':
			return "images/enemies/unique/tires.png";
		case 't':
			return "images/enemies/unique/tv.png";
		case '0':
			return "images/enemies/turrets/0.png";
		case '1':
			return "images/enemies/turrets/1.png";
		case 'B':
		default:
			return "images/enemies/unique/blob.png";
		}

	}

	private char getSelChar() {

		switch (selChar) {
		case 'G':// I put flying as tracking because flying is a variable, not a
					// class
		case 'c':
			return 'T';

		case 'P':
		case 't':

			return 'W';

		case 'M':
		case '0':
		case '1':
			return 'L';

		case 'B':
		case 'C':
		case 'F':
		case 'T':
		default:
			return 'S';
		}

	}

	public int selDelay() {

		switch (selChar) {
		case 'M':
			return 20;
		case '0':
		case '1':
			return 75;

		default:
			return 0;
		}

	}

	public char selFlying() {

		switch (selChar) {
		case 'G':
			return 't';

		default:
			return 'f';
		}

	}
	public int selHealth(){
		switch(selChar){
		
		case 'P':
		case 'F':
			return 5;
			
		case 't':
		case '0':
			return 6;
			
		case 'T':
		case 'C':
		case 'c':
			return 8;
		
		case '1':
		case 'B':
		case 'G':
		case 'M':
		default:
			return 10;
		}
	}
	public void addRowEnd(){
		String[] nStrings=new String[strings.length+1];
		int ml=0;
		for(int c=0;c<strings.length;c++){
			nStrings[c]=strings[c];
			if(strings[c].length()>ml){
				ml=strings[c].length();
			}
		}
		String addString="";
		for(int c=0;c<ml;c++){
			addString+='1';
		}
		nStrings[strings.length]=addString;
		strings=nStrings;
	}

	public void addRowMid(){
		String[] nStrings=new String[strings.length+1];
		int ml=0;
		int cA=0;

		int theRow=(int) ((y + (this.getHeight()/2)) / 100);
		for(int c=0;c<strings.length;c++,cA++){
			
			if(strings[c].length()>ml){
				ml=strings[c].length();
			}
			if(theRow==c){
			nStrings[cA]="";
			cA++;
			}
				nStrings[cA]=strings[c];
			
		}
		String addString="";
		for(int c=0;c<ml;c++){
			addString+='1';
		}
		nStrings[theRow]+=addString;
	
		strings=nStrings;
	}
	public void addColEnd(){
		for(int c=0;c<strings.length;c++){
			strings[c]+='1';
		}
	}
	public void addColMid(){
		
		int block=(int) ((x + (this.getWidth()/2)) / 100)-1;
		for(int c=0;c<strings.length;c++){
			strings[c]=strings[c].substring(0, block)+"1"+strings[c].substring(block);
		}
	}
	public void remRowEnd(){
		String[] nStrings=new String[strings.length-1];
		int ml=0;
		for(int c=0;c<nStrings.length;c++){
			nStrings[c]=strings[c];
			if(strings[c].length()>ml){
				ml=strings[c].length();
			}
		}
	
		strings=nStrings;
	}
	public void remRowMid(){
		String[] nStrings=new String[strings.length-1];
		
		
int cA=0;
		int theRow=(int) ((y -1+ (this.getHeight()/2)) / 100);
		for(int c=0;c<strings.length;c++){
			
			
			if(theRow!=c){
			nStrings[cA]=strings[c];
			cA++;
			}
				
			
		}
		
	
	
		strings=nStrings;
	}
	public void remColEnd(){
		for(int c=0;c<strings.length;c++){
			strings[c]=strings[c].substring(0, strings[c].length()-1);
		}
	}
public void remColMid(){
		
		int block=(int) ((x + (this.getWidth()/2)) / 100);
		for(int c=0;c<strings.length;c++){
			strings[c]=strings[c].substring(0, block-1)+strings[c].substring(block);
		}
	}private String getSelShowString() {

		switch (selChar) {
		case 'C':
			return "cactus";
		case 'c':
			return "chair";
		case 'F':
			return "fishbowl";
		case 'G':
			return "ghost";
		case 'M':
			return "machineLaunch";
		case 'P':
			return "pizza";
		case 'T':
			return "tires";
		case 't':
			return "tv";
		case '0':
			return "tennisLauncher";
		case '1':
			return "cannon";
		case 'B':
			return "blob";
		default:
			return "?";
		}

	}
	private String getSelShowStringBlocks() {

		switch (selChar) {
		
		
		case '1':
			return "grass";
		case 'O':
			
			return "spawn";

		case 'W':
			return "wall";

		case 'P':
			return "pit";

		case 'R':
			return "rock";

		case 'C':
			return "carpet";

		case '*':
			return "crystal";

		case '>':
			return "switch";
			
		default:
			return "?";
		}

	}
}
