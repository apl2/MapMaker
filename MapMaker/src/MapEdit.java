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
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
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
	public static final Font BLOCK = new Font("Calibri", Font.PLAIN, 80);
	public static final Font MOUSE = new Font("Calibri", Font.PLAIN, 20);
	public static final Color LIGHT_OFF_TAN = new Color(225, 180, 0);
	public static final Color OFF_TAN = new Color(220, 185, 60);
	public static final Color SAND_STONE = new Color(110, 90, 30);
	public static final Color DESERT_BLUE = new Color(100, 200, 255);
	public static final Color BLUE = new Color(50, 100, 255);
	public static final Color LIGHT_BROWN = new Color(175, 75, 0);
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
	ArrayList<String> portals = new ArrayList<String>();
	int selNum = 2;
	int blocks = 0;
	boolean enPre = false;
	ArrayList<String> enemies = new ArrayList<String>();
	boolean zoom = false;
	boolean choosing = false;
	final int MAXBLOCKS = 3;
int selEn;
ArrayList<String>objects=new ArrayList<String>();
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
				+ "R: delete enemy\n" + "Z: zoom out/back\n"
				+ "X: remove (Opens Menu)\n" + "C: add (Opens Menu)\n"
				+ "I: info\n" 
				+ "T: change texture pack\n"
				+ "P: Build/close paths");
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
					MapMaker.saveStrings(portals, nameWithP(), project);
					MapMaker.saveStrings(objects, nameWithO(), project);
					mapEdit.dispose();
				} else if (key == KeyEvent.VK_I) {
					JOptionPane
							.showMessageDialog(
									mapEdit,
									"Space/Click: place\n"
											+ "Up/Down/Scroll Wheel: change selected block/enemy\n"
											+ "E/Right Click: swap between enemies and blocks\n"
											+ "Q/Middle Mouse Button: change enemy placement precision\n"
											+ "R: delete enemy\n"
											+ "Z: zoom out/back\n"
											+ "X: remove (Opens Menu)\n"
											+ "C: add (Opens Menu)\n"
											+ "I: info\n"
											+ "T: change texture pack\n"
											+ "P: Build/close paths");
				} else if (key == KeyEvent.VK_T) {
					String[] options = { "cancel", "Grassy", "Desert", "Snowy",
							"Island", "Volcano" };
					int sel = JOptionPane.showOptionDialog(mapEdit,
							"Which texture pack do you want?", "Texture Pack",
							JOptionPane.CANCEL_OPTION,
							JOptionPane.DEFAULT_OPTION, null, options, 0);
					if (sel != 0) {
						strings[0] = options[sel];
					}
				} else if (key == KeyEvent.VK_C && zoom) {

					String[] options = { "Cancel", "Add row at the end",
							"Add column at the end" };
					choosing = true;
					int sel = JOptionPane
							.showOptionDialog(
									mapEdit,
									"What and where would you like to add?\n If you want to add things in the middle, get out of zooming out.",
									"Add", JOptionPane.DEFAULT_OPTION,
									JOptionPane.PLAIN_MESSAGE, null, options, 0);
					choosing = false;
					switch (sel) {

					case 1:
						addRowEnd();
						break;

					case 2:
						addColEnd();
						break;
					default:
					}

				} else if (key == KeyEvent.VK_X && zoom) {
					String[] options = { "Cancel", "Remove row at the end",
							"Remove column at the end" };
					choosing = true;
					JFrame f = new JFrame();
					f.setLocation(mapEdit.getX() + mapEdit.getWidth() / 2, 0);
					// f.setSize(0,0);
					f.setVisible(true);
					int sel = JOptionPane.showOptionDialog(f,
							"What and where would you like to add?", "Add",
							JOptionPane.DEFAULT_OPTION,
							JOptionPane.PLAIN_MESSAGE, null, options, 0);
					f.dispose();
					choosing = false;
					switch (sel) {

					case 1:
						remRowEnd();
						break;

					case 2:
						remColEnd();
						break;
					default:
					}

				} else if (key == KeyEvent.VK_Z) {
					zoom = !zoom;

				} else if (!zoom) {
					if (key == KeyEvent.VK_S) {
						yd = 25;
					} else if (key == KeyEvent.VK_W) {
						yd = -25;
					} else if (key == KeyEvent.VK_A) {
						xd = -25;
					} else if (key == KeyEvent.VK_D) {
						xd = 25;
					} else if (key == KeyEvent.VK_X) {
						String[] options = { "Cancel", "Remove row here",
								"Remove row at the end", "Remove column here",
								"Remove column at the end" };
						choosing = true;
						JFrame f = new JFrame();
						f.setLocation(mapEdit.getX() + mapEdit.getWidth() / 2,
								0);
						// f.setSize(0,0);
						f.setVisible(true);
						int sel = JOptionPane.showOptionDialog(f,
								"What and where would you like to add?", "Add",
								JOptionPane.DEFAULT_OPTION,
								JOptionPane.PLAIN_MESSAGE, null, options, 0);
						f.dispose();
						choosing = false;
						switch (sel) {
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

					} else if (key == KeyEvent.VK_C) {
						String[] options = { "Cancel", "Add row here",
								"Add row at the end", "Add column here",
								"Add column at the end" };
						choosing = true;
						JFrame f = new JFrame();
						f.setLocation(mapEdit.getX() + mapEdit.getWidth() / 2,
								0);
						// f.setSize(0,0);
						f.setVisible(true);
						int sel = JOptionPane.showOptionDialog(f,
								"What and where would you like to add?", "Add",
								JOptionPane.DEFAULT_OPTION,
								JOptionPane.PLAIN_MESSAGE, null, options, 0);
						f.dispose();
						choosing = false;
						switch (sel) {
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

					} else if (key == KeyEvent.VK_SPACE) {

						if (blocks == 0) {
							int maxWidth=0;
							for(int c=0;c<strings.length;c++){
								if(strings[c].length()>maxWidth){
									maxWidth=strings[c].length();
								}
							}
							Point m = MouseInfo.getPointerInfo().getLocation();
							if((int) ((y + m.getY() - 10) / 100)+1 >0&&(int) ((y + m.getY() - 10) / 100) + 2<strings.length-1&&(int) ((x + m.getX() + 10) / 100)>0&&(int) ((x + m.getX() + 10) / 100)+1<maxWidth)
							{
							String s = strings[(int) ((y + m.getY() - 10) / 100) + 2];
							s = s.substring(0,
									(int) ((x + m.getX() + 10) / 100))
									+ selChar
									+ s.substring((int) ((x + m.getX() + 10) / 100) + 1);
						strings[(int) ((y - 10 + m.getY()) / 100) + 2] = s;
						}} else if (blocks == 1) {

							Point m = MouseInfo.getPointerInfo().getLocation();
							int sen = 100;
							if (!enPre) {
								sen = 50;
							}
							int theX = ((int) ((m.x + x) / sen) * sen);
							int theY = ((int) ((m.y + y) / sen) * sen)+200;
							if (!enPre) {
								theX -= 50;
								theY -= 50;
							}
							enemies.add((getSelChar() + "," + theX + "," + theY
									+ "," + getSelString() + "," + selFlying()
									+ "," + selHealth() + (selDelay() != null ? ","
									+ selDelay()
									: "")
									+
									  (selDelay2() != null ? ","
									+ selDelay2()
									: "")+
									(selDelay3() != null ? ","
											+ selDelay3()
											: "")));
						} else if (blocks == 2) {
							Point m = MouseInfo.getPointerInfo().getLocation();
							int sen = 100;
							if (!enPre) {
								sen = 50;
							}
							int theX = ((int) ((m.x + x) / sen) * sen);
							int theY = ((int) ((m.y + y) / sen) * sen)+200;

							String returnVal = JOptionPane.showInputDialog(
									mapEdit, "What is the name of the map");
if(returnVal!=null){
							portals.add(theX + "," + theY + ","
 + returnVal + ","
									+ 0
									+","+"normal"// Run.removeExtension(chooser.getSelectedFile().toString()+","+Integer.parseInt(JOptionPane.showInputDialog(mapEdit,
										// "How many collectibles"))
							);
						}}
						else if (blocks == 3) {
							Point m = MouseInfo.getPointerInfo().getLocation();
							int sen = 25;
							if (!enPre) {
								sen = 50;
							}
							int theX = ((int) ((m.x + x) / sen) * sen)+objectsAddX();
							int theY = ((int) ((m.y + y) / sen) * sen)+200+objectsAddY();

						
							objects.add(theX + "," + theY + ","+getSelStringO()+","+getObjectCollide()// Run.removeExtension(chooser.getSelectedFile().toString()+","+Integer.parseInt(JOptionPane.showInputDialog(mapEdit,
										// "How many collectibles"))
							);
						}
						 else if (blocks == MAXBLOCKS+1) {
								Point m = MouseInfo.getPointerInfo().getLocation();
								
								ArrayList<String> stuff = new ArrayList<String>();// should
								// have
								// 5
								String currentS = "";
								for (int c2 = 0; c2 < enemies.get(selEn).length(); c2++) {

									if (enemies.get(selEn).charAt(c2) == ',') {
										stuff.add(currentS);
										currentS = "";

									} else {
										currentS += enemies.get(selEn).charAt(c2);
									}
								}
								if (currentS != "") {
									stuff.add(currentS);
								}
								try {
									int px = Integer.parseInt(stuff.get(1));
									int py = Integer.parseInt(stuff.get(2));
									Image pImg = new ImageIcon(getClass().getResource(
											stuff.get(3))).getImage();
									int stuffG=6;
									if(cop(enemies.get(selEn).charAt(0))==true)
										stuffG=7;
									
									//System.out.println(stuffG);
									if(stuff.size()>stuffG){
									int[][]points=createArray(stuff.get(stuffG));
									if(points.length>0){
									int theX = ((int) ((m.x + x) / 100));
								int theY = ((int) ((m.y + y) / 100) )+2;
							
								int newX=((int) ((px) / 100) );
								int newY=((int) ((py) / 100) );
								
								for(int c=0;c<points.length;c++){
									newX+=points[c][0];
									newY+=points[c][1];
								}
									if(//(newX!=theX&&newY!=theY)
											//||
											(newX==theX&&newY==theY)
											){
										//System.out.println("no");
										//System.out.println(newX-theX);
										//System.out.println(newY-theY);
									}else{
										theX-=newX;
										theY-=newY;
										if((theX<2&&theX>-2)&&(theY<2&&theY>-2)){
										stuff.set(stuffG, stuff.get(stuffG)+"'"+theX+"'"+theY);
										
										String s="";
										for(int c=0;c<stuff.size();c++){
											if(c!=0)
												s+=",";
											s+=stuff.get(c);
										}
										enemies.set(selEn, s);
									//System.out.println("yes");
									}}
									}}
								} catch (Exception e2) {
									e2.printStackTrace();
								}
								
	
								
							}
					} else if (key == KeyEvent.VK_DOWN) {
						selNum++;
					} else if (key == KeyEvent.VK_UP) {
						selNum--;
					} else if (key == KeyEvent.VK_E) {
						if(blocks!=MAXBLOCKS+1){
						blocks++;
						if (blocks > MAXBLOCKS) {
							blocks = 0;
						}
						selNum = 0;
						selChar = 'S';}
					} else if (key == KeyEvent.VK_Q) {
						enPre = !enPre;

					}

					else if (key == KeyEvent.VK_R) {
						if(blocks==MAXBLOCKS+1){
							blocks=1;
						}
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
									.contains(new Point(x + m.x, y + m.y+200))) {
								enemies.remove(c);
								c--;
							}
						}

						
						for (int c = 0; c < portals.size(); c++) {
							ArrayList<String> stuff = new ArrayList<String>();// should
																				// have
																				// 5
							String currentS = "";
							for (int c2 = 0; c2 < portals.get(c).length(); c2++) {

								if (portals.get(c).charAt(c2) == ',') {
									stuff.add(currentS);
									currentS = "";

								} else {
									currentS += portals.get(c).charAt(c2);
								}
							}Image enImg;
							if(stuff.get(3).equals("normal")){
						 enImg = new ImageIcon(getClass().getResource(
									"images/portal1.png")).getImage();}
							else{
								enImg = new ImageIcon(getClass().getResource(
										"images/icon.png")).getImage();
							}
							if (new Rectangle(Integer.parseInt(stuff.get(0)),
									Integer.parseInt(stuff.get(1)),
									enImg.getWidth(null), enImg.getHeight(null))
									.contains(new Point(x + m.x, y + m.y+200))) {
								portals.remove(c);
								c--;
							}
						}
						for (int c = 0; c < objects.size(); c++) {
							ArrayList<String> stuff = new ArrayList<String>();// should
																				// have
																				// 5
							String currentS = "";
							for (int c2 = 0; c2 <objects.get(c).length(); c2++) {

								if (objects.get(c).charAt(c2) == ',') {
									stuff.add(currentS);
									currentS = "";

								} else {
									currentS += objects.get(c).charAt(c2);
								}
							}
							stuff.add(currentS);
							Image enImg=new ImageIcon(getClass().getResource(stuff.get(2))).getImage();
							
							if (new Rectangle(Integer.parseInt(stuff.get(0)),
									Integer.parseInt(stuff.get(1)),
									enImg.getWidth(null), enImg.getHeight(null))
									.contains(new Point(x + m.x, y + m.y+200))) {
								objects.remove(c);
								c--;
							}
						}
					}
					else if (key == KeyEvent.VK_P) {
						if(blocks==3){
							blocks=0;
						}else{
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
									.contains(new Point(x + m.x, y + m.y+200))) {
								selEn=c;blocks=MAXBLOCKS+1;
								break;
								
							}
						}

						
						
					}}
				}
			}

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
				MapMaker.saveStrings(portals, nameWithP(), project);
				MapMaker.saveStrings(objects, nameWithO(), project);
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

				int x = mapEdit.x;
				int y = mapEdit.y;
				g2d.clearRect(0, 0, this.getWidth(), this.getHeight());
				g2d.setColor(getTextureBack());
				g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
				if (zoom) {
					x = 0;
					y = 0;
					int mWidth = 0;
					for (int c = 1; c < strings.length; c++) {
						if (strings[c].length() > mWidth) {
							mWidth = strings[c].length();
						}
					}

					mWidth *= 100;
					int mHeight = (strings.length - 1) * 100;
					int scale = Math.max(mWidth, mHeight);
					g2d.scale((double) this.getWidth() / (double) scale,
							(double) this.getHeight() / (double) scale);
				}

				for (int cA = 1; cA < strings.length; cA++) {
					for (int c = 0; c < strings[cA].length(); c++) {

						int nx = c * 100 - x;
						int ny = (cA - 1) * 100 - y-100;
						if (zoom
								|| (nx > -100 && nx < this.getWidth()
										&& ny > -100 && ny < this.getHeight())) {

							char theChar = strings[cA].charAt(c);
							if(theChar=='I'){
//								g2d.setColor(Color.white);
//								g2d.fill(new Rectangle(nx, ny, 100, 100));
							}else
							switch (getTexturePack()) {
							case 'D':// Start desert
								switch (theChar) {
								case 'O':
									g2d.setColor(getColor(theChar));
									g2d.fill(new Rectangle(nx, ny, 100, 100));
									g2d.setColor(LIGHT_OFF_TAN);
									g2d.fill(new Rectangle(nx, ny + 30, 60, 4));
									g2d.fill(new Rectangle(nx + 60, ny + 26,
											40, 4));
									g2d.fill(new Rectangle(nx, ny + 80, 30, 4));
									g2d.fill(new Rectangle(nx + 30, ny + 76,
											70, 4));
									g2d.draw(new Rectangle(nx, ny, 100, 100));
									g2d.setFont(MOUSE);
									g2d.setColor(Color.BLACK);
									g2d.drawString("spawn", nx + 30, ny + 40);
									break;
								case '1':
									g2d.setColor(getColor(theChar));
									g2d.fill(new Rectangle(nx, ny, 100, 100));
									g2d.setColor(LIGHT_OFF_TAN);
									g2d.fill(new Rectangle(nx, ny + 30, 60, 4));
									g2d.fill(new Rectangle(nx + 60, ny + 26,
											40, 4));
									g2d.fill(new Rectangle(nx, ny + 80, 30, 4));
									g2d.fill(new Rectangle(nx + 30, ny + 76,
											70, 4));
									g2d.draw(new Rectangle(nx, ny, 100, 100));
									break;
								case 'L':
									g2d.setColor(getColor(theChar));
									g2d.fill(new Rectangle(nx, ny, 100, 100));
									g2d.setColor(LIGHT_BLUE);
									g2d.fill(new Rectangle(nx + 60, ny + 67,
											40, 3));
									g2d.fill(new Rectangle(nx, ny + 70, 60, 3));
									break;
								case 'W':
									g2d.setColor(getColor(theChar));
									g2d.fill(new Rectangle(nx, ny, 100, 100));
									g2d.setColor(Color.BLACK);
									g2d.draw(new Rectangle(nx, ny, 100, 100));
									break;

								case 'C':
									g2d.setColor(getColor(theChar));
									g2d.fill(new Rectangle(nx, ny, 100, 100));
									g2d.setColor(LIGHT_OFF_TAN);
									g2d.drawLine(nx, ny, nx + 100, ny + 100);
									g2d.drawLine(nx + 100, ny, nx, ny + 100);
									g2d.drawLine(nx, ny + 100 / 2, nx + 100,
											ny + 100 / 2);
									g2d.drawLine(nx + 100 / 2, ny,
											nx + 100 / 2, ny + 100);
									break;

								case '>':

									g2d.setFont(BLOCK);
									g2d.setColor(getColor(theChar));
									g2d.fill(new Rectangle(nx, ny, 100, 100));
									g2d.setColor(Color.BLUE);
									g2d.drawString("<->", nx, ny + 70);
									g2d.draw(new Rectangle(nx, ny, 100, 100));
									break;

								default:
									g2d.setColor(getColor(theChar));
									g2d.fill(new Rectangle(nx, ny, 100, 100));
									break;
								}
								break;
							case 'S':// Start snowy
								switch (theChar) {
								case 'O':
									g2d.setColor(getColor(theChar));
									g2d.fill(new Rectangle(nx, ny, 100, 100));
									g2d.setColor(Color.LIGHT_GRAY);
									g2d.fill(new Rectangle(nx + 60, ny + 40, 4,
											4));
									g2d.fill(new Rectangle(nx + 30, ny + 80, 3,
											3));
									g2d.draw(new Rectangle(nx, ny, 100, 100));
									g2d.setFont(MOUSE);
									g2d.setColor(Color.BLACK);
									g2d.drawString("spawn", nx + 30, ny + 40);
									break;
								case '1':
									g2d.setColor(getColor(theChar));
									g2d.fill(new Rectangle(nx, ny, 100, 100));
									g2d.setColor(Color.LIGHT_GRAY);
									g2d.fill(new Rectangle(nx + 60, ny + 40, 4,
											4));
									g2d.fill(new Rectangle(nx + 30, ny + 80, 3,
											3));
									g2d.draw(new Rectangle(nx, ny, 100, 100));
									break;
								case 'L':
									g2d.setColor(getColor(theChar));
									g2d.fill(new Rectangle(nx, ny, 100, 100));
									g2d.setColor(LIGHT_BLUE);
									g2d.fill(new Rectangle(nx + 70, ny + 65,
											10, 10));
									g2d.fill(new Rectangle(nx + 30, ny + 30,
											15, 15));
									break;
								case 'W':
									g2d.setColor(getColor(theChar));
									g2d.fill(new Rectangle(nx, ny, 100, 100));
									g2d.setColor(BROWN);
									g2d.fill(new Rectangle(nx, ny + 20, 100, 10));
									g2d.fill(new Rectangle(nx, ny + 70, 100, 10));
									g2d.setColor(Color.BLACK);
									g2d.draw(new Rectangle(nx, ny, 100, 100));
									break;

								case 'C':
									g2d.setColor(getColor(theChar));
									g2d.fill(new Rectangle(nx, ny, 100, 100));
									g2d.setColor(LIGHT_BROWN);
									g2d.fill(new Rectangle(nx, ny + 20, 100, 10));
									g2d.fill(new Rectangle(nx, ny + 70, 100, 10));
									break;

								case '>':

									g2d.setFont(BLOCK);
									g2d.setColor(getColor(theChar));
									g2d.fill(new Rectangle(nx, ny, 100, 100));
									g2d.setColor(Color.BLUE);
									g2d.drawString("<->", nx, ny + 70);
									g2d.draw(new Rectangle(nx, ny, 100, 100));
									break;
								case '2':
									g2d.setColor(getColor(theChar));
									g2d.fill(new Rectangle(nx, ny, 100, 100));
									g2d.setColor(Color.WHITE);
									g2d.fill(new Rectangle(nx + 70, ny + 30, 5,
											5));
									g2d.fill(new Rectangle(nx + 40, ny + 70, 5,
											5));
									g2d.fill(new Rectangle(nx + 10, ny + 15, 3,
											3));
									g2d.setColor(Color.LIGHT_GRAY);
									g2d.draw(new Rectangle(nx, ny, 100, 100));
									break;
								default:
									g2d.setColor(getColor(theChar));
									g2d.fill(new Rectangle(nx, ny, 100, 100));
									break;
								}
								break;
							case 'I':// Start tropic
								switch (theChar) {
								case 'O':
									g2d.setColor(getColor(theChar));
									g2d.fill(new Rectangle(nx, ny, 100, 100));
									g2d.setColor(OFF_TAN);
									g2d.fill(new Rectangle(nx + 70, ny + 30, 5,
											5));
									g2d.fill(new Rectangle(nx + 40, ny + 70, 5,
											5));
									g2d.fill(new Rectangle(nx + 10, ny + 15, 3,
											3));
									g2d.setColor(LIGHT_OFF_GREEN);
									g2d.fill(new Rectangle(nx + 30, ny + 15, 4,
											10));
									g2d.fill(new Rectangle(nx + 80, ny + 20, 4,
											10));
									g2d.fill(new Rectangle(nx + 20, ny + 80, 4,
											10));
									g2d.draw(new Rectangle(nx, ny, 100, 100));
									g2d.setFont(MOUSE);
									g2d.setColor(Color.BLACK);
									g2d.drawString("spawn", nx + 30, ny + 40);
									break;
								case '1':
									g2d.setColor(getColor(theChar));
									g2d.fill(new Rectangle(nx, ny, 100, 100));
									g2d.setColor(OFF_TAN);
									g2d.fill(new Rectangle(nx + 70, ny + 30, 5,
											5));
									g2d.fill(new Rectangle(nx + 40, ny + 70, 5,
											5));
									g2d.fill(new Rectangle(nx + 10, ny + 15, 3,
											3));
									g2d.setColor(LIGHT_OFF_GREEN);
									g2d.fill(new Rectangle(nx + 30, ny + 15, 4,
											10));
									g2d.fill(new Rectangle(nx + 80, ny + 20, 4,
											10));
									g2d.fill(new Rectangle(nx + 20, ny + 80, 4,
											10));
									g2d.draw(new Rectangle(nx, ny, 100, 100));
									break;
								case 'L':
									g2d.setColor(getColor(theChar));
									g2d.fill(new Rectangle(nx, ny, 100, 100));
									g2d.setColor(BLUE);
									g2d.fill(new Rectangle(nx + 70, ny + 65,
											30, 5));
									g2d.fill(new Rectangle(nx, ny + 70, 70, 5));
									g2d.fill(new Rectangle(nx + 40, ny + 25,
											60, 5));
									g2d.fill(new Rectangle(nx, ny + 30, 40, 5));
									break;
								case 'W':
									g2d.setColor(getColor(theChar));
									g2d.fill(new Rectangle(nx, ny, 100, 100));
									g2d.setColor(BROWN);
									g2d.fill(new Rectangle(nx, ny + 20, 100, 10));
									g2d.fill(new Rectangle(nx, ny + 70, 100, 10));
									g2d.setColor(Color.BLACK);
									g2d.draw(new Rectangle(nx, ny, 100, 100));
									break;

								case 'C':
									g2d.setColor(getColor(theChar));
									g2d.fill(new Rectangle(nx, ny, 100, 100));
									g2d.setColor(LIGHT_BROWN);
									g2d.fill(new Rectangle(nx, ny + 20, 100, 10));
									g2d.fill(new Rectangle(nx, ny + 70, 100, 10));
									break;

								case '>':

									g2d.setFont(BLOCK);
									g2d.setColor(getColor(theChar));
									g2d.fill(new Rectangle(nx, ny, 100, 100));
									g2d.setColor(Color.BLUE);
									g2d.drawString("<->", nx, ny + 70);
									g2d.draw(new Rectangle(nx, ny, 100, 100));
									break;
								case '2':
									g2d.setColor(getColor(theChar));
									g2d.fill(new Rectangle(nx, ny, 100, 100));
									g2d.setColor(LIGHT_OFF_TAN);
									g2d.fill(new Rectangle(nx, ny + 30, 60, 4));
									g2d.fill(new Rectangle(nx + 60, ny + 26,
											40, 4));
									g2d.fill(new Rectangle(nx, ny + 80, 30, 4));
									g2d.fill(new Rectangle(nx + 30, ny + 76,
											70, 4));
									g2d.draw(new Rectangle(nx, ny, 100, 100));
									break;
								default:
									g2d.setColor(getColor(theChar));
									g2d.fill(new Rectangle(nx, ny, 100, 100));
									break;
								}
								break;

							case 'V':// Start volcano
								switch (theChar) {
								case 'O':
									g2d.setColor(getColor(theChar));
									g2d.fill(new Rectangle(nx, ny, 100, 100));
									g2d.setColor(OFF_GREEN);
									g2d.fill(new Rectangle(nx + 30, ny + 15, 4,
											10));
									g2d.fill(new Rectangle(nx + 80, ny + 20, 4,
											10));
									g2d.fill(new Rectangle(nx + 20, ny + 80, 4,
											10));
									g2d.setColor(Color.GRAY);
									g2d.fill(new Rectangle(nx + 70, ny + 30, 5,
											5));
									g2d.fill(new Rectangle(nx + 40, ny + 70, 5,
											5));
									g2d.fill(new Rectangle(nx + 10, ny + 15, 3,
											3));
									g2d.setColor(Color.DARK_GRAY);
									g2d.draw(new Rectangle(nx, ny, 100, 100));
									g2d.setFont(MOUSE);
									g2d.setColor(Color.BLACK);
									g2d.drawString("spawn", nx + 30, ny + 40);
									break;
								case '1':
									g2d.setColor(getColor(theChar));
									g2d.fill(new Rectangle(nx, ny, 100, 100));
									g2d.setColor(OFF_GREEN);
									g2d.fill(new Rectangle(nx + 30, ny + 15, 4,
											10));
									g2d.fill(new Rectangle(nx + 80, ny + 20, 4,
											10));
									g2d.fill(new Rectangle(nx + 20, ny + 80, 4,
											10));
									g2d.setColor(Color.GRAY);
									g2d.fill(new Rectangle(nx + 70, ny + 30, 5,
											5));
									g2d.fill(new Rectangle(nx + 40, ny + 70, 5,
											5));
									g2d.fill(new Rectangle(nx + 10, ny + 15, 3,
											3));
									g2d.setColor(Color.DARK_GRAY);
									g2d.draw(new Rectangle(nx, ny, 100, 100));
									break;
								case 'L':
									g2d.setColor(getColor(theChar));
									g2d.fill(new Rectangle(nx, ny, 100, 100));
									g2d.setColor(Color.ORANGE);
									g2d.fill(new Rectangle(nx + 70, ny + 65,
											10, 10));
									g2d.fill(new Rectangle(nx + 30, ny + 30,
											15, 15));
									break;
								case 'W':
									g2d.setColor(getColor(theChar));
									g2d.fill(new Rectangle(nx, ny, 100, 100));
									g2d.setColor(Color.BLACK);
									g2d.draw(new Rectangle(nx, ny, 100, 100));
									break;

								case 'C':
									g2d.setColor(getColor(theChar));
									g2d.fill(new Rectangle(nx, ny, 100, 100));
									g2d.setColor(Color.GRAY);
									g2d.drawLine(nx, ny, nx + 100, ny + 100);
									g2d.drawLine(nx + 100, ny, nx, ny + 100);
									g2d.drawLine(nx, ny + 100 / 2, nx + 100,
											ny + 100 / 2);
									g2d.drawLine(nx + 100 / 2, ny,
											nx + 100 / 2, ny + 100);
									break;

								case '>':

									g2d.setFont(BLOCK);
									g2d.setColor(getColor(theChar));
									g2d.fill(new Rectangle(nx, ny, 100, 100));
									g2d.setColor(Color.BLUE);
									g2d.drawString("<->", nx, ny + 70);
									g2d.draw(new Rectangle(nx, ny, 100, 100));
									break;
								case '2':
									g2d.setColor(getColor(theChar));
									g2d.fill(new Rectangle(nx, ny, 100, 100));
									g2d.setColor(Color.GRAY);
									g2d.fill(new Rectangle(nx, ny + 30, 4, 4));
									g2d.fill(new Rectangle(nx + 60, ny + 26, 4,
											4));
									g2d.fill(new Rectangle(nx, ny + 80, 4, 4));
									g2d.setColor(Color.DARK_GRAY);
									g2d.draw(new Rectangle(nx, ny, 100, 100));
									break;
								default:
									g2d.setColor(getColor(theChar));
									g2d.fill(new Rectangle(nx, ny, 100, 100));
									break;
								}
								break;

							case 'G':
							default:// Start grassy
								switch (theChar) {
								case 'O':
									g2d.setColor(getColor(theChar));
									g2d.fill(new Rectangle(nx, ny, 100, 100));
									g2d.setColor(LIGHT_OFF_GREEN);
									g2d.fill(new Rectangle(nx + 30, ny + 15, 4,
											10));
									g2d.fill(new Rectangle(nx + 80, ny + 20, 4,
											10));
									g2d.fill(new Rectangle(nx + 20, ny + 80, 4,
											10));
									g2d.fill(new Rectangle(nx + 60, ny + 60, 4,
											10));
									g2d.draw(new Rectangle(nx, ny, 100, 100));
									g2d.setFont(MOUSE);
									g2d.setColor(Color.BLACK);
									g2d.drawString("spawn", nx + 30, ny + 40);
									break;
								case '1':
									g2d.setColor(getColor(theChar));
									g2d.fill(new Rectangle(nx, ny, 100, 100));
									g2d.setColor(LIGHT_OFF_GREEN);
									g2d.fill(new Rectangle(nx + 30, ny + 15, 4,
											10));
									g2d.fill(new Rectangle(nx + 80, ny + 20, 4,
											10));
									g2d.fill(new Rectangle(nx + 20, ny + 80, 4,
											10));
									g2d.fill(new Rectangle(nx + 60, ny + 60, 4,
											10));
									g2d.draw(new Rectangle(nx, ny, 100, 100));
									break;
								case 'L':
									g2d.setColor(getColor(theChar));
									g2d.fill(new Rectangle(nx, ny, 100, 100));
									g2d.setColor(LIGHT_BLUE);
									g2d.fill(new Rectangle(nx, ny + 30, 40, 4));
									g2d.fill(new Rectangle(nx + 80, ny + 76,
											20, 4));
									g2d.fill(new Rectangle(nx, ny + 80, 80, 4));
									g2d.fill(new Rectangle(nx + 40, ny + 26,
											60, 4));
									break;
								case 'W':
									g2d.setColor(getColor(theChar));
									g2d.fill(new Rectangle(nx, ny, 100, 100));
									g2d.setColor(Color.BLACK);
									g2d.draw(new Rectangle(nx, ny, 100, 100));
									break;

								case 'C':
									g2d.setColor(getColor(theChar));
									g2d.fill(new Rectangle(nx, ny, 100, 100));
									g2d.setColor(Color.RED);
									g2d.drawLine(nx, ny, nx + 100, ny + 100);
									g2d.drawLine(nx + 100, ny, nx, ny + 100);
									g2d.drawLine(nx, ny + 100 / 2, nx + 100,
											ny + 100 / 2);
									g2d.drawLine(nx + 100 / 2, ny,
											nx + 100 / 2, ny + 100);
									break;

								case '>':

									g2d.setFont(BLOCK);
									g2d.setColor(getColor(theChar));
									g2d.fill(new Rectangle(nx, ny, 100, 100));
									g2d.setColor(Color.BLUE);
									g2d.drawString("<->", nx, ny + 70);
									g2d.draw(new Rectangle(nx, ny, 100, 100));
									break;

								default:
									g2d.setColor(getColor(theChar));
									g2d.fill(new Rectangle(nx, ny, 100, 100));
									break;
								}
								break;// End Grassy

							}
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
						g2d.drawImage(enImg, enX - x, enY - y-200, mapEdit);
						int stuffG=6;
						if(cop(enemies.get(c).charAt(0))==true){
							stuffG=7;
							}
					if((ch=='b'||ch=='p')&&stuff.size()>stuffG){
						int addX=enX-x+50;
						int addY=enY-y-150;
						//System.out.println(stuff.get(stuffG));
						int[][]points=createArray(stuff.get(stuffG));
						//System.out.println(points.length);
						g2d.setColor(Color.black);
						for(int c3=0;c3<points.length;c3++){
							g2d.drawLine(addX, addY, addX+(points[c3][0]*100), addY+(points[c3][1]*100));
							addX+=(points[c3][0]*100);
							addY+=(points[c3][1]*100);
						}
					}
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
				for(int c=0;c<objects.size();c++){
					ArrayList<String> stuff = new ArrayList<String>();// should
					// have
					// 5
					String currentS = "";
					for (int c2 = 0; c2 < objects.get(c).length(); c2++) {

						if (objects.get(c).charAt(c2) == ',') {
							stuff.add(currentS);
							currentS = "";

						} else {
							currentS += objects.get(c).charAt(c2);
						}
					}
					if (currentS != "") {
						stuff.add(currentS);
					}
					try {
						int px = Integer.parseInt(stuff.get(0));
						int py = Integer.parseInt(stuff.get(1));
						Image pImg= new ImageIcon(getClass().getResource(
								stuff.get(2))).getImage();
g2d.drawImage(pImg, px-x, py-y-200, mapEdit);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				for (int c = 0; c < portals.size(); c++) {
					ArrayList<String> stuff = new ArrayList<String>();// should
					// have
					// 5
					String currentS = "";
					for (int c2 = 0; c2 < portals.get(c).length(); c2++) {

						if (portals.get(c).charAt(c2) == ',') {
							stuff.add(currentS);
							currentS = "";

						} else {
							currentS += portals.get(c).charAt(c2);
						}
					}
					if (currentS != "") {
						stuff.add(currentS);
					}
					try {
						int px = Integer.parseInt(stuff.get(0));
						int py = Integer.parseInt(stuff.get(1));
						Image pImg;
						if(stuff.get(4).equals("normal")){
						pImg = new ImageIcon(getClass().getResource(
								"images/portal1.png")).getImage();}
						else{
							pImg = new ImageIcon(getClass().getResource(
									"images/icon.png")).getImage();
						}
						String to = "to " + stuff.get(2);
						g2d.setFont(MOUSE);
						g2d.setColor(PURPLE);
g2d.drawImage(pImg, px-x, py-y-200, mapEdit);
g2d.drawString(to, px-x, py-y-200);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
				Point m = MouseInfo.getPointerInfo().getLocation();
				if (choosing && !zoom) {

					int theX = ((int) (this.getWidth() / 100) * 100) / 2
							+ (x % 100);
					int theY = ((int) (this.getHeight() / 100) * 100) / 2 + 50
							+ (y % 100);
					g2d.setColor(Color.red);
					g2d.fillRect(theX - 6, theY - 6, 12, 12);
					g2d.setColor(Color.black);
					g2d.fillOval(theX - 4, theY - 4, 8, 8);
				} else if (!zoom) {
					g2d.setFont(MOUSE);
					if (blocks == 0) {
						g2d.setColor(Color.red);
						g2d.drawString("" + getSelShowStringBlocks(), m.x + 10,
								m.y - 10);
					} else if (blocks == 1) {
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
					else if(blocks==2){
						g2d.setColor(PURPLE);
						
						if (enPre) {
							g2d.drawString("Portal",
									((int) ((m.x + x) / 100) * 100) + 25 - x,
									((int) ((m.y + y) / 100) * 100) + 50 - y);
						} else {
							g2d.drawString("Portal",
									((int) ((m.x + x) / 50) * 50+25 - x),
									((int) ((m.y + y) / 50) * 50+50 - y));
						}
						
					}
					else if(blocks==3){
						g2d.setColor(Color.MAGENTA);
						
						if (enPre) {
							g2d.drawString(getSelShowStringObjects(),
									((int) ((m.x + x) / 25) * 25) + 25 - x,
									((int) ((m.y + y) / 25) * 25) + 50 - y);
						} else {
							g2d.drawString(getSelShowStringObjects(),
									((int) ((m.x + x) / 50) * 50+25 - x),
									((int) ((m.y + y) / 50) * 50+50 - y));
						}
						
					}
					else if(blocks==MAXBLOCKS+1){
						g2d.setColor(Color.black);
						
						
							g2d.drawString("Path",
									((int) ((m.x + x) / 100) * 100) + 25 - x,
									((int) ((m.y + y) / 100) * 100) +50 - y);
						
						
					}
				}
			}
		};

		Timer timer = new Timer(100, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (blocks == 0) {
					if (selNum < 0) {
						selNum = 10;
					}
					if (selNum > 10) {
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
					case 8:
						selChar = '2';
						break;
					case 9:
						selChar = 'L';
						break;
					case 10:
						selChar = 'I';
						break;
					}

				} else if (blocks == 1) {
					if (selNum > 17) {
						selNum = 0;
					}
					if (selNum < 0) {
						selNum = 17;
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
					case 11:
						selChar = 'S';
						break;
					case 12:
						selChar = 'D';
						break;
					case 13:
						selChar = 'I';
						break;
					case 14://backPath
						selChar='R';
						break;
					case 15://security
						selChar='r';
						break;
					case 16://Path
						selChar='d';
						break;
					case 17://backSecurity
						selChar='p';
						break;
					}
				}
				else if (blocks == 3) {
					if (selNum > 3) {
						selNum = 0;
					}
					if (selNum < 0) {
						selNum = 3;
					}
					switch (selNum) {
					case 0:
						selChar='L';
						break;
					case 1:
						selChar='l';
						break;
					case 2:
						selChar='P';
						break;
					case 3:
						selChar='W';
						break;
					}
				}
				if (zoom) {
					xd = 0;
					yd = 0;
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

	public Color getColor(char type) {
		// TODO Auto-generated method stub
		switch (getTexturePack()) {
		case 'D':
			switch (type) {
			case '2':
			case '1':
			case 'O':
				return OFF_TAN;
			case 'L':
				return DESERT_BLUE;
			case 'W':
				return SAND_STONE;
			case 'P':
				return LIGHT_OFF_TAN;
			case 'R':
				return TAN;
			case '>':
				return Color.LIGHT_GRAY;
			case 'C':
				return TAN;

			case '*':
				return LIGHT_BLUE;

			default:
				System.err.println("Type " + type
						+ " does not have a color case");
				return Color.RED;
			}

		case 'S':
			switch (type) {
			case '1':
			case 'O':
				return Color.WHITE;
			case '2':
				return BROWN;
			case 'L':
				return BLUE;
			case 'W':
				return LIGHT_BROWN;
			case 'P':
				return Color.BLACK;
			case 'R':
				return Color.GRAY;
			case '>':
				return Color.LIGHT_GRAY;
			case 'C':
				return BROWN;

			case '*':
				return LIGHT_BLUE;

			default:
				System.err.println("Type " + type
						+ " does not have a color case");
				return Color.RED;
			}
		case 'I':
			switch (type) {
			case '2':
				return OFF_TAN;
			case '1':
			case 'O':
				return OFF_GREEN;
			case 'L':
				return Color.BLUE;
			case 'W':
				return LIGHT_BROWN;
			case 'P':
				return Color.BLACK;
			case 'R':
				return Color.GRAY;
			case '>':
				return Color.LIGHT_GRAY;
			case 'C':
				return BROWN;

			case '*':
				return LIGHT_BLUE;

			default:
				System.err.println("Type " + type
						+ " does not have a color case");
				return Color.RED;
			}
		case 'V':
			switch (type) {
			case '2':
			case 'O':
			case '1':
				return BROWN;
			case 'L':
				return ORANGE;
			case 'W':
				return Color.DARK_GRAY;
			case 'P':
				return Color.BLACK;
			case 'R':
				return Color.GRAY;
			case '>':
			case 'C':
				return Color.LIGHT_GRAY;

			case '*':
				return LIGHT_BLUE;

			default:
				System.err.println("Type " + type
						+ " does not have a color case");
				return Color.RED;
			}
		case 'G':
		default:
			switch (type) {
			case '2':
				return BROWN;
			case '1':
			case 'O':
				return OFF_GREEN;
			case 'L':
				return BLUE;
			case 'W':
				return Color.DARK_GRAY;
			case 'P':
				return Color.BLACK;
			case 'R':
			case '>':
				return Color.LIGHT_GRAY;
			case 'C':
				return TAN;

			case '*':
				return LIGHT_BLUE;

			default:
				System.err.println("Type " + type
						+ " does not have a color case");
				return Color.RED;
			}

		}

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
				for (int c = 1; c < lines.size(); c++) {
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
		try {

			File saveFile = new File("bin/projects/" + project + "/"
					+ nameWithP());

			if (saveFile.exists()) {

				BufferedReader reader = new BufferedReader(new FileReader(
						saveFile));
				String line;

				while ((line = reader.readLine()) != null) {
					portals.add(line);

				}
				reader.close();

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {

			File saveFile = new File("bin/projects/" + project + "/"
					+ nameWithO());

			if (saveFile.exists()) {

				BufferedReader reader = new BufferedReader(new FileReader(
						saveFile));
				String line;

				while ((line = reader.readLine()) != null) {
					objects.add(line);

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

	public String nameWithP() {
		// TODO Auto-generated method stub
		String fName = "";
		String extension = "";
		boolean ext = false;
		for (int c = 0; c < name.length(); c++) {
			if (ext) {
				extension += name.charAt(c);
			} else if (name.charAt(c) == '.') {
				ext = true;
				fName += "P.";
			} else {
				fName += name.charAt(c);
			}
		}

		fName += extension;
		return fName;
	}
	public String nameWithO() {
		// TODO Auto-generated method stub
		String fName = "";
		String extension = "";
		boolean ext = false;
		for (int c = 0; c < name.length(); c++) {
			if (ext) {
				extension += name.charAt(c);
			} else if (name.charAt(c) == '.') {
				ext = true;
				fName += "O.";
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
		case 'S':
			return "images/enemies/unique/security.png";
		case 'D':
			return "images/enemies/unique/explosiveBox.png";
		case 'I':
			return "images/enemies/unique/invGhost.png";
		case 'R'://backPath
			return "images/enemies/unique/car1Right.png";
		case 'r'://security
			return "images/enemies/unique/paintRoller.png";
		case 'd'://Path
			return "images/enemies/unique/ducky.png";
		case 'p'://backSecurity
			return "images/enemies/unique/potato.png";
		case 'B':
		default:
			return "images/enemies/unique/blob.png";
		}

	}
	private String getSelStringO() {

		switch (selChar) {
		case 'L':
			return"images/objects/Leaves.png";
		case 'l':
			return"images/objects/LeavesSc.png";
		case 'P':
			return "images/objects/PTree0.png";
		default:
		case 'W':
			return "images/objects/Wood.png";
		}

	}
	private char getSelChar() {

		switch (selChar) {
		case 'I':
			return 'c';// LookChaseEnemy
		case '0':
			return 'L';// Launch

		case 'P':
			return 'P';// PursuingLaunch
		case 'M':
		case 'C':
		case 'F':
		case 'T':
			return 'S';// StandEnemy

		case 'G':
			return 'T';// TrackingEnemy

		case 't':
			return 'C';// ChargeEnemy

		case 'B':
			return 'W';// WalkEnemy

		case 'D':
			return 'E';// ExplosiveSpawner

			// Lowercase denotes an enemy that must see you before attacking.

		case 'c':
			return 'w';// SeeChaseEnemy

		case '1':
			return 'l';// SeeShootEnemy
		case 'S':
			return 's';// SecurityEnemy
			
		case 'R'://backPath
			return'p';
		case 'r'://security
			return 'b';
		case 'd'://Path
			return 'p';
		case 'p'://backSecurity
			return 'b';
		default:
			return 'S';// StandEnemy
		}

	}
	public boolean cop(char selChar) {

		switch (selChar) {
		
case 'r'://security		
case 'b':
			return true;
		
		
		

		default:
			return false;
		}

	}
	public String selDelay() {

		switch (selChar) {
		case 'M':
			return "20";
		case '0':
		case '1':
		case 'P':
			return "75";
case 'r'://security		
		case 'p'://backSecurity
		case 'S':
			return "cop";
		case 'D':
			return "100";
		
		

		default:
			return null;
		}

	}
	public String selDelay2(){
		switch (selChar) {
		
		
		
		case 'r'://security
		
		case 'd'://Path
			
		case 'R'://backPath
		case 'p'://backSecurity
			return "0'0";
		default:
			return null;
		}
	}
	public String selDelay3() {

		switch (selChar) {
		
		
			
		case 'r'://security
		
		case 'd'://Path
			return "F";
		case 'R'://backPath
		case 'p'://backSecurity
			return "B";
		default:
			return null;
		}

	}
	public char selFlying() {

		switch (selChar) {
		case 'G':
		case 'I':
			return 't';

		default:
			return 'f';
		}

	}

	public int selHealth() {
		switch (selChar) {

		case 'P':
		case 'F':
		case 'D':
			return 5;

		case 't':
		case '0':
			return 6;

		case 'T':
		case 'C':
		case 'c':
			case 'R'://backPath
		
		case 'r'://security
		
		case 'd'://Path
		
		case 'p'://backSecurity
		
			return 8;

		case '1':
		case 'B':
		case 'G':
		case 'M':
		case 'S':
		case 'I':
			
	
		default:
			return 10;
		}
	}

	public void addRowEnd() {
		String[] nStrings = new String[strings.length + 1];
		int ml = 0;
		for (int c = 0; c < strings.length-1; c++) {
			nStrings[c] = strings[c];
			if (strings[c].length() > ml) {
				ml = strings[c].length();
			}
		}
		String addString = "";
		addString += 'I';
		for (int c = 1; c < ml-1; c++) {
			addString += '1';
		}
		addString += 'I';
		nStrings[nStrings.length-2] = addString;
		nStrings[nStrings.length-1]=strings[strings.length-1];
		strings = nStrings;
	}

	public void addRowMid() {
		String[] nStrings = new String[strings.length + 1];
		int ml = 0;
		int cA = 0;

		int theRow = (int) ((y + (this.getHeight() / 2)) / 100)+2;
		for (int c = 0; c < strings.length-0; c++, cA++) {

			if (strings[c].length() > ml) {
				ml = strings[c].length();
			}
			if (theRow == c) {
				nStrings[cA] = "";
				cA++;
			}
			nStrings[cA] = strings[c];

		}
		String addString = "";
		addString += 'I';
		for (int c = 1; c < ml-1; c++) {
			addString += '1';
		}
		addString += 'I';
		nStrings[theRow] += addString;

		strings = nStrings;
	}

	public void addColEnd() {
		strings[1] += 'I';
		
		for (int c = 2; c < strings.length-1; c++) {
			strings[c] =strings[c].substring(0, strings[c].length()-1)+'1'+strings[c].substring(strings[c].length()-1);
		}
		strings[strings.length-1] +="I";
				}

	public void addColMid() {

		int block = (int) ((x + (this.getWidth() / 2)) / 100);
		strings[1]+="I";
		for (int c = 2; c < strings.length-1; c++) {
			strings[c] = strings[c].substring(0, block) + "1"
					+ strings[c].substring(block);
		}
		strings[strings.length-1]+="I";
	}

	public void remRowEnd() {
		String[] nStrings = new String[strings.length - 1];
		int ml = 0;
		for (int c = 0; c <nStrings.length-1; c++) {
			
			nStrings[c] = strings[c];
			if (strings[c].length() > ml) {
				ml = strings[c].length();
			}
		}
nStrings[nStrings.length-1]="";
for(int c=0;c<ml;c++){
	nStrings[nStrings.length-1]+="I";
}
		strings = nStrings;
	}

	public void remRowMid() {
		String[] nStrings = new String[strings.length - 1];

		int cA = 0;
		int theRow = (int) ((y - 1 + (this.getHeight() / 2)) / 100)+2;
		for (int c = 0; c < strings.length; c++) {

			if (theRow != c) {
				nStrings[cA] = strings[c];
				cA++;
			}

		}

		strings = nStrings;
	}

	public void remColEnd() {
		for (int c = 1; c < strings.length; c++) {
			strings[c] = strings[c].substring(0, strings[c].length() - 2)+"I";
		}
	}

	public void remColMid() {

		int block = (int) ((x + (this.getWidth() / 2)) / 100);
		for (int c = 1; c < strings.length; c++) {
			strings[c] = strings[c].substring(0, block - 1)
					+ strings[c].substring(block);
		}
	}

	private String getSelShowString() {

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
		case 'S':
			return "security";
		case 'D':
			return "explosiveBox";
		case 'I':
			return "invertedGhost";
		case 'R'://backPath
			return "car1Right";
		case 'r'://security
			return "paintRoller";
		case 'd'://Path
			return "ducky";
		case 'p'://backSecurity
			return "potato";
		default:
			return "?";
		}

	}

	private String getSelShowStringBlocks() {

		switch (selChar) {

		case '1':
			return "grass";
		case '2':
			return "dirt";
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
		case 'L':
			return "liquid";
		case 'I':
			return "InvisibleWall";
		default:
			return "?";
		}

	}
	private String getSelShowStringObjects() {

		switch (selChar) {
		case 'L':
			return "Leaves";
		case 'l':
			return "LeavesScattered";
		case 'P':
			return "PalmTree";
		case 'W':
			return "Wood";
		default:
			return "?";
		}

	}
	public Color getTextureBack() {
		switch (strings[0].charAt(0)) {
		case 'D':
			return OFF_TAN;
		case 'I':
			return Color.BLUE;
		case 'S':
			return Color.WHITE;
		case 'V':
			return Color.DARK_GRAY;
		case 'G':
		default:
			return OFF_GREEN;
		}
	}

	public char getTexturePack() {
		return strings[0].charAt(0);

	}
	private int[][] createArray(String string) {
		// TODO Auto-generated method stub

		//System.out.println(string);
		String[] split = string.split("\'");
		int splitLength = split.length / 2;

		int splitCounter = 0;
		int p2;
		int[][] toReturn = new int[splitLength][2];

		for (int p1 = 0; p1 < splitLength; p1++) {

			//System.out.println("In loop");
			for (p2 = 0; p2 < toReturn[p1].length; p2++) {
				toReturn[p1][p2] = Integer.parseInt(split[splitCounter]);
				splitCounter++;
			}
		}

//		for (int k = 0; k < toReturn.length; k++) {
//			for (int c = 0; c < toReturn[k].length; c++)
//				//System.out.print(toReturn[k][c] + " ");
//			//System.out.println();
//		}

		return toReturn;
	}
	
	public int objectsAddX(){
		switch(selChar){
		case 'L':
			return 35;
		case 'l':
			return 10;
		case 'W':
			return 40;
		
		case 'P':
		default:
			return 0;
		}
	}
	public int objectsAddY(){
		switch(selChar){
		case 'L':
			return 35;
		case 'l':
			return 10;
		case 'W':
			return 40;
		
		case 'P':
		default:
			return 0;
		}
	}
	public boolean getObjectCollide(){
		switch(selChar){
		case 'P':
			return true;
		case 'L':
		case 'l':
		case 'W':
		default:
			return false;
		}
	}
}
