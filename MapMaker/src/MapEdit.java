import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.ScrollPane;
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
import java.io.FilenameFilter;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.filechooser.FileFilter;

public class MapEdit extends JFrame {
	boolean defaultMap;
	public static final Color MED_GRAY = new Color(150, 150, 160);
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

	public static final Color DARK_GREEN = new Color(30, 72, 30);
	public static final Color DRAB_BROWN = new Color(68, 21, 15);
	public static final Color LIGHT_SAND_BLUE = new Color(63, 85, 116);
	public static final Color SAND_BLUE = new Color(48, 65, 88);
	public static final Color DARK_SAND_BLUE = new Color(32, 44, 60);
	public static final Color SAND_RED = new Color(155, 36, 36);
	String name;
	MapEdit mapEdit = this;
	JPanel drawPan;
	int x = 0;
	int y = 0;
	String project;
	String[] strings;
	int xd = 0;
	int yd = 0;
	char selChar = 'W';// Blocks

	String oType = "Normal";
	String oImageString = "images/icon.png";
	String oCollectible = "normal";
	String pType = "normal";
	String pType2 = "portal";
	String eType = "Standing";
	String eImageString = "images/enemies/unique/blob.png";
	String eFlying;
	int eBaseHealth = 100;// enemies
	String nString = "kepler";// NPCs
	ArrayList<String> portals = new ArrayList<String>();
	int selNum = 2;
	int blocks = 0;
	boolean enPre = false;
	ArrayList<String> enemies = new ArrayList<String>();
	ArrayList<String> npcs = new ArrayList<String>();
	boolean zoom = false;
	boolean choosing = false;
	final int MAXBLOCKS = 4;
	int selEn;
	int theSpawn;
	ArrayList<String> objects = new ArrayList<String>();
	String oValue;
	int weatherT;
	String[] charNameList = new String[] { "shovel", "club", "diamond", "heart", "sirCobalt", "wizard", "macaroni" };
	JList<String> charNames;

	public MapEdit(String name, String project) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.project = project;
		// this.imUrl=imUrl;
	}

	public void run() {
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setUndecorated(true);
		JOptionPane.showMessageDialog(mapEdit,
				"Space/Click: place\n" + "Up/Down/Scroll Wheel: change selected block/enemy\n"
						+ "E/Right Click: swap between enemies and blocks\n"
						+ "Q/Middle Mouse Button: change enemy placement precision\n" + "R: delete enemy\n"
						+ "Z: zoom out/back\n" + "X: remove (Opens Menu)\n" + "C: add (Opens Menu)\n" + "I: info\n"
						+ "T: change texture pack\n" + "P: Build/close paths");
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
					System.out.println(strings[0]);
					MapMaker.saveStrings(strings, name, project);
					MapMaker.saveStrings(enemies, nameWithE(), project);
					MapMaker.saveStrings(portals, nameWithP(), project);
					MapMaker.saveStrings(objects, nameWithO(), project);
					MapMaker.saveStrings(npcs, nameWithN(), project);
					mapEdit.dispose();
				} else if (key == KeyEvent.VK_O) {
					if (JOptionPane.showConfirmDialog(mapEdit,
							"Do you want to set this map as the default?") == JOptionPane.YES_OPTION) {
						charNames = new JList<String>(charNameList);
						for (String s : charNameList)
							System.out.println(s);
						JFrame jf = new JFrame();
						jf.setAlwaysOnTop(true);
						// charNames.setSelectionMode(JList.);
						jf.setSize(500, 300);
						// jf.setLocation(WIDTH/2-250, HEIGHT/2-250);
						jf.setLayout(new BorderLayout());
						jf.setTitle("What characters will you start with? Ctrl click for multiple.");
						jf.add(charNames, BorderLayout.CENTER);

						jf.add(new JLabel("Exit this window when done."), BorderLayout.SOUTH);
						jf.setVisible(true);
						jf.addWindowListener(new WindowListener() {

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
								String[] names = new String[charNames.getSelectedIndices().length + 1];
								names[0] = name.split("/")[0];
								for (int c = 0; c < charNames.getSelectedIndices().length; c++)
									names[c + 1] = charNames.getSelectedValuesList().get(c);
								MapMaker.saveInfo(project, names);
								defaultMap = true;
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

					}
				} else if (key == KeyEvent.VK_V) {
					if (blocks == 1) {
						new EnemyChooser();
					} else if (blocks == 2) {
						new PortalChooser();
					} else if (blocks == 3) {
						new ObjectChooser();
					} else if (blocks == 4) {
						new NPCChooser();
					}
				} else if (key == KeyEvent.VK_I) {
					JOptionPane.showMessageDialog(mapEdit,
							"Space/Click: place\n" + "Up/Down/Scroll Wheel: change selected block/enemy\n"
									+ "E/Right Click: swap between enemies and blocks\n"
									+ "Q/Middle Mouse Button: change enemy placement precision\n" + "R: delete enemy\n"
									+ "Z: zoom out/back\n" + "X: remove (Opens Menu)\n" + "C: add (Opens Menu)\n"
									+ "I: info\n" + "T: change texture pack\n" + "P: Build/close paths");
				} else if (key == KeyEvent.VK_T) {

					String[] options = { "cancel", "Grassy", "Desert", "Snowy", "Island", "Volcano", "Haunted", "Lab" };
					int sel = JOptionPane.showOptionDialog(mapEdit, "Which texture pack do you want?", "Texture Pack",
							JOptionPane.CANCEL_OPTION, JOptionPane.DEFAULT_OPTION, null, options, 0);
					if (sel > 0) {
						String[] options2 = { "cancel", "none","normal", "rain", "obscure", "fog" };
						int sel2 = JOptionPane.showOptionDialog(mapEdit, "What weather do you want?", "Weather",
								JOptionPane.CANCEL_OPTION, JOptionPane.DEFAULT_OPTION, null, options2, 0);
						if (sel2 > 0) {
							try {
								weatherT = 0;
								strings[0] = options[sel]
										+ "," + options2[sel2] + "," + Integer.parseInt(JOptionPane
												.showInputDialog(mapEdit, "What level do you want the Map to be?", "1"))
										+ "," + theSpawn;
							} catch (Exception ex) {
								JOptionPane.showMessageDialog(mapEdit, "Level must be a number.");
							}
						}
					}
				} else if (key == KeyEvent.VK_Y) {
					theSpawn = Integer
							.parseInt(JOptionPane.showInputDialog("What spawn number should the default spawn be?"));
					strings[0] = strings[0].substring(strings[0].lastIndexOf(",")) + theSpawn;
				} else if (key == KeyEvent.VK_C && zoom) {

					String[] options = { "Cancel", "Add row at the end", "Add column at the end" };
					choosing = true;
					int sel = JOptionPane.showOptionDialog(mapEdit,
							"What and where would you like to add?\n If you want to add things in the middle, get out of zooming out.",
							"Add", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, 0);
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
					String[] options = { "Cancel", "Remove row at the end", "Remove column at the end" };
					choosing = true;
					JFrame f = new JFrame();
					f.setLocation(mapEdit.getX() + mapEdit.getWidth() / 2, 0);
					// f.setSize(0,0);
					f.setVisible(true);
					int sel = JOptionPane.showOptionDialog(f, "What and where would you like to add?", "Add",
							JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, 0);
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
						String[] options = { "Cancel", "Remove row here", "Remove row at the end", "Remove column here",
								"Remove column at the end" };
						choosing = true;
						JFrame f = new JFrame();
						f.setLocation(mapEdit.getX() + mapEdit.getWidth() / 2, 0);
						// f.setSize(0,0);
						f.setVisible(true);
						int sel = JOptionPane.showOptionDialog(f, "What and where would you like to add?", "Add",
								JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, 0);
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
						String[] options = { "Cancel", "Add row here", "Add row at the end", "Add column here",
								"Add column at the end" };
						choosing = true;
						JFrame f = new JFrame();
						f.setLocation(mapEdit.getX() + mapEdit.getWidth() / 2, 0);
						// f.setSize(0,0);
						f.setVisible(true);
						int sel = JOptionPane.showOptionDialog(f, "What and where would you like to add?", "Add",
								JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, 0);
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
							int maxWidth = 0;
							for (int c = 0; c < strings.length; c++) {
								if (strings[c].length() > maxWidth) {
									maxWidth = strings[c].length();
								}
							}
							Point m = MouseInfo.getPointerInfo().getLocation();
							if ((int) ((y + m.getY() - 10) / 100) + 1 > 0
									&& (int) ((y + m.getY() - 10) / 100) + 2 < strings.length - 1
									&& (int) ((x + m.getX() + 10) / 100) > 0
									&& (int) ((x + m.getX() + 10) / 100) + 1 < maxWidth) {
								String s = strings[(int) ((y + m.getY() - 10) / 100) + 2];
								s = s.substring(0, (int) ((x + m.getX() + 10) / 100)) + selChar
										+ s.substring((int) ((x + m.getX() + 10) / 100) + 1);
								if ((int) ((y - 10 + m.getY()) / 100) + 2 > 0)
									strings[(int) ((y - 10 + m.getY()) / 100) + 2] = s;
							}
						} else if (blocks == 1) {

							Point m = MouseInfo.getPointerInfo().getLocation();
							int sen = 100;
							if (!enPre) {
								sen = 50;
							}
							int theX = ((int) ((m.x + x) / sen) * sen);
							int theY = ((int) ((m.y + y) / sen) * sen) + 200;
							if (!enPre) {
								theX -= 50;
								theY -= 50;
							}

							String extraEn = extraEn(eType);
							String extraEn2 = extraEn2(eType);
							enemies.add((eType + "," + theX + "," + theY + "," + eImageString + "," + eFlying + ","
									+ eBaseHealth + (extraEn != null ? "," + extraEn : "")
									+ (extraEn2 != null ? "," + extraEn2 : "")));
						} else if (blocks == 2) {
							Point m = MouseInfo.getPointerInfo().getLocation();
							int sen = 100;
							if (!enPre) {
								sen = 50;
							}
							int theX = ((int) ((m.x + x) / sen) * sen);
							int theY = ((int) ((m.y + y) / sen) * sen) + 200;

							String returnVal = JOptionPane.showInputDialog(mapEdit, "What is the name of the map");
							if (returnVal != null) {
								String returnVal2 = JOptionPane.showInputDialog(mapEdit, "What spawn number?");
								int spawnNum = -1;
								try {
									spawnNum = Integer.parseInt(returnVal2);
									if (spawnNum < -1) {
										JOptionPane.showMessageDialog(mapEdit,
												"The spawnNum needs to 0 or higher.\nOr use -1 as the default spawn number.\nSetting spawn number to default spawn number.");
										spawnNum = -1;
									}
								} catch (Exception ex) {
									spawnNum = -1;
								}

								portals.add(theX + "," + theY + "," + returnVal + "," + 0 + "," + pType + "," + spawnNum
										+ (!pType2.equals("portal") ? "," + pType2 : "")// Run.removeExtension(chooser.getSelectedFile().toString()+","+Integer.parseInt(JOptionPane.showInputDialog(mapEdit,
								// "How many collectibles"))
								);
							}
						} else if (blocks == 3) {
							Point m = MouseInfo.getPointerInfo().getLocation();
							int sen = 25;
							if (!enPre) {
								sen = 50;
							}
							int theX = ((int) ((m.x + x) / sen) * sen) + objectsAddX();
							int theY = ((int) ((m.y + y) / sen) * sen) + 200 + objectsAddY();
							String s = getObjectsVal();//TODO HERE
							String amount = "0";
							if (s == null) {
								amount = oValue;
							} else {
								amount = s;
							}

							objects.add(theX + "," + theY + "," + oImageString + "," + getObjectCollide() + "," + amount
									+ (!oCollectible.equals("normal") ? "," + oCollectible : ""));
						} else if (blocks == 4) {
							Point m = MouseInfo.getPointerInfo().getLocation();
							int sen = 100;
							if (enPre) {
								sen = 50;
							}
							int theX = ((int) ((m.x + x) / sen) * sen) + npcX();
							int theY = ((int) ((m.y + y) / sen) * sen) + 200 + npcY();
							String answer = null;
							if (nString.equals("sirCobalt"))
								answer = JOptionPane.showInputDialog("Option to join player? y/n");
							else if (nString.equals("gatekeeper"))
								answer = JOptionPane.showInputDialog("How many collectibles");

							String s = getNPC3(answer);
							npcs.add(theX + "," + theY + "," + nString + (s != null ? "," + s : ""));
						} else if (blocks == MAXBLOCKS + 1) {
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
								// Image pImg = new ImageIcon(getClass()
								// .getResource(stuff.get(3))).getImage();
								int stuffG = 6;
								if (hasPath(stuff.get(0)))
									stuffG = 7;

								if (stuff.size() > stuffG - 1) {
									int[][] points = new int[0][0];
									if (stuff.size() > stuffG)
										points = createArray(stuff.get(stuffG));

									int theX = ((int) ((m.x + x) / 100));
									int theY = ((int) ((m.y + y) / 100)) + 2;

									int newX = ((int) ((px) / 100));
									int newY = ((int) ((py) / 100));

									for (int c = 0; c < points.length; c++) {
										newX += points[c][0];
										newY += points[c][1];
									}
									if (// (newX!=theX&&newY!=theY)
										// ||
									(newX == theX && newY == theY)) {
										// System.out.println("no");
										// System.out.println(newX-theX);
										// System.out.println(newY-theY);
									} else {
										theX -= newX;
										theY -= newY;
										if ((theX < 2 && theX > -2) && (theY < 2 && theY > -2)) {
											if (stuff.size() <= stuffG)
												stuff.add("");
											stuff.set(stuffG,
													(!stuff.get(stuffG).equals("") ? stuff.get(stuffG) + "'" : "")
															+ theX + "'" + theY);
											String s = "";
											for (int c = 0; c < stuff.size(); c++) {
												if (c != 0)
													s += ",";
												s += stuff.get(c);
											}
											enemies.set(selEn, s);
											// System.out.println("yes");
										}
									}

								} else {
									throw new Exception();
								}
							} catch (Exception e2) {
								e2.printStackTrace();
							}

						}
					} else if (key == KeyEvent.VK_DOWN) {
						selNum++;
					} else if (key == KeyEvent.VK_UP) {
						selNum--;
					} else if (key == KeyEvent.VK_E) {
						if (blocks != MAXBLOCKS + 1) {
							blocks++;
							if (blocks > MAXBLOCKS) {
								blocks = 0;
							}
							selNum = 0;
							selChar = 'S';

						}
					} else if (key == KeyEvent.VK_Q) {
						enPre = !enPre;

					}

					else if (key == KeyEvent.VK_R) {
						if (blocks == MAXBLOCKS + 1) {
							blocks = 1;
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
							String ch = stuff.get(0);
							Image enImg;
							String sB;

							if (ch.equals("Head Boss")) {
								sB = "images/enemies/bosses/Head.png";
								enImg = new ImageIcon(getClass().getResource(sB)).getImage();
							} else if (ch.equals("Lizard Man")) {
								sB = "images/enemies/bosses/LizardMan/front/n.png";
								enImg = new ImageIcon(getClass().getResource(sB)).getImage();
							} else if (ch.equals("Pod")) {
								sB = "images/podAll.png";
								enImg = new ImageIcon(getClass().getResource(sB)).getImage();
							}
							else if(ch.equals("Stone")){
								sB="images/enemies/bosses/Stone.png";
								enImg=new ImageIcon(getClass().getResource(sB)).getImage();
							}
							else {
								enImg = new ImageIcon(getClass().getResource(stuff.get(3))).getImage();
							}
							if (new Rectangle(Integer.parseInt(stuff.get(1)), Integer.parseInt(stuff.get(2)),
									enImg.getWidth(null), enImg.getHeight(null))
											.contains(new Point(x + m.x, y + m.y + 200))) {
								enemies.remove(c);
								c--;
							}
						}

						for (int c = 0; c < npcs.size(); c++) {
							ArrayList<String> stuff = new ArrayList<String>();// should
																				// have
																				// 5
							String currentS = "";
							for (int c2 = 0; c2 < npcs.get(c).length(); c2++) {

								if (npcs.get(c).charAt(c2) == ',') {
									stuff.add(currentS);
									currentS = "";

								} else {
									currentS += npcs.get(c).charAt(c2);
								}
							}
							if (currentS != "")
								stuff.add(currentS);
							System.out.println(stuff.get(2));
							Image enImg;
							enImg = new ImageIcon(getClass().getResource(getImageChar(stuff.get(2)))).getImage();
							if (new Rectangle(Integer.parseInt(stuff.get(0)), Integer.parseInt(stuff.get(1)),
									enImg.getWidth(null), enImg.getHeight(null))
											.contains(new Point(x + m.x, y + m.y + 200))) {
								npcs.remove(c);
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
							}
							if (currentS != null) {
								stuff.add(currentS);
							}
							Image enImg;
							if (stuff.get(4).equals("normal")) {
								enImg = new ImageIcon(getClass().getResource("images/portals/normal/0.png")).getImage();
							}

							else if (stuff.get(4).equals("boss")) {
								enImg = new ImageIcon(getClass().getResource("images/portals/boss/open/0.png")).getImage();
							} else {
								enImg = new ImageIcon(getClass()
										.getResource("images/portals/" + stuff.get(4) + "/" + stuff.get(6) + "/c.png"))
												.getImage();
							}
							if (new Rectangle(Integer.parseInt(stuff.get(0)), Integer.parseInt(stuff.get(1)),
									enImg.getWidth(null), enImg.getHeight(null))
											.contains(new Point(x + m.x, y + m.y + 200))) {
								portals.remove(c);
								c--;
							}
						}
						for (int c = 0; c < objects.size(); c++) {
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
							stuff.add(currentS);
							Image enImg;
							if (stuff.size() > 4) {
								if(stuff.get(4).equals("-2"))
								enImg = new ImageIcon(getClass().getResource(getAllWanted(stuff.get(2)))).getImage();
								else if(stuff.get(4).equals("-14"))
									enImg = new ImageIcon(getClass().getResource("images/objects/householdObjects/tableBook.png")).getImage();
								else if(stuff.get(4).equals("-16"))
									enImg = new ImageIcon(getClass().getResource("images/icon.png")).getImage();
								else if(stuff.get(4).equals("-1"))
									enImg = new ImageIcon(getClass().getResource("images/icon.png")).getImage();
								else{
									try{
									int o=Integer.parseInt(stuff.get(4));
									if(o<-15&&o>-20)
										enImg = new ImageIcon(getClass().getResource("images/icon.png")).getImage();
									else
										enImg = new ImageIcon(getClass().getResource(stuff.get(2))).getImage();	
									}catch(Exception ex){
										//System.out.println(stuff.get(2));
										enImg = new ImageIcon(getClass().getResource(stuff.get(2))).getImage();
									}
									
									}
							}else
								enImg = new ImageIcon(getClass().getResource(stuff.get(2))).getImage();
							// if(stuff.size()<4){
							// System.out.println("Thingy");
							//
							// }else
							// enImg = new ImageIcon(getClass().getResource(
							// stuff.get(2))).getImage();

							if (new Rectangle(Integer.parseInt(stuff.get(0)), Integer.parseInt(stuff.get(1)),
									enImg.getWidth(null), enImg.getHeight(null))
											.contains(new Point(x + m.x, y + m.y + 200))) {
								objects.remove(c);
								c--;
							}
						}
					} else if (key == KeyEvent.VK_P) {
						if (blocks == MAXBLOCKS + 1) {
							blocks = 0;
						} else {
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
								String sB="images/icon.png";
								Image enImg;
								String type=stuff.get(0);
								if (type.equals("Head Boss")) {
									sB = "images/enemies/bosses/Head.png";
									enImg = new ImageIcon(getClass().getResource(sB)).getImage();
								} else if (type.equals("Lizard Man")) {
									sB = "images/enemies/bosses/LizardMan/front/n.png";
									enImg = new ImageIcon(getClass().getResource(sB)).getImage();
								} else if (type.equals("Pod")) {
									sB = "images/podAll.png";
									enImg = new ImageIcon(getClass().getResource(sB)).getImage();
								}
								else if (type.equals("Stone")) {
									sB = "images/enemies/bosses/Stone.png";
									enImg = new ImageIcon(getClass().getResource(sB)).getImage();
								}
								else
								enImg = new ImageIcon(getClass().getResource(stuff.get(3))).getImage();
								if (new Rectangle(Integer.parseInt(stuff.get(1)), Integer.parseInt(stuff.get(2)),
										enImg.getWidth(null), enImg.getHeight(null)).contains(
												new Point(x + m.x, y + m.y + 200))
										&& (isPath(stuff.get(0)))) {
									selEn = c;
									blocks = MAXBLOCKS + 1;
									break;

								}
							}

						}
					}
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
				MapMaker.saveStrings(npcs, nameWithN(), project);
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
					g2d.scale((double) this.getWidth() / (double) scale, (double) this.getHeight() / (double) scale);
				}

				for (int cA = 1; cA < strings.length; cA++) {
					for (int c = 0; c < strings[cA].length(); c++) {

						int nx = c * 100 - x;
						int ny = (cA - 1) * 100 - y - 100;
						if (zoom || (nx > -100 && nx < this.getWidth() && ny > -100 && ny < this.getHeight())) {

							char theChar = strings[cA].charAt(c);
							if (theChar == 'I') {
								// g2d.setColor(Color.white);
								// g2d.fill(new Rectangle(nx, ny, 100, 100));
							} else
								switch (getTexturePack()) {
								case 'D':// Start desert
									switch (theChar) {
									case 'O':
										g2d.setColor(getColor(theChar));
										g2d.fill(new Rectangle(nx, ny, 100, 100));
										g2d.setColor(LIGHT_OFF_TAN);
										g2d.fill(new Rectangle(nx, ny + 30, 60, 4));
										g2d.fill(new Rectangle(nx + 60, ny + 26, 40, 4));
										g2d.fill(new Rectangle(nx, ny + 80, 30, 4));
										g2d.fill(new Rectangle(nx + 30, ny + 76, 70, 4));
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
										g2d.fill(new Rectangle(nx + 60, ny + 26, 40, 4));
										g2d.fill(new Rectangle(nx, ny + 80, 30, 4));
										g2d.fill(new Rectangle(nx + 30, ny + 76, 70, 4));
										g2d.draw(new Rectangle(nx, ny, 100, 100));
										break;
									case 'L':
										g2d.setColor(getColor(theChar));
										g2d.fill(new Rectangle(nx, ny, 100, 100));
										g2d.setColor(LIGHT_BLUE);
										g2d.fill(new Rectangle(nx + 60, ny + 67, 40, 3));
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
										g2d.drawLine(nx, ny + 100 / 2, nx + 100, ny + 100 / 2);
										g2d.drawLine(nx + 100 / 2, ny, nx + 100 / 2, ny + 100);
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
										g2d.fill(new Rectangle(nx + 60, ny + 40, 4, 4));
										g2d.fill(new Rectangle(nx + 30, ny + 80, 3, 3));
										g2d.draw(new Rectangle(nx, ny, 100, 100));
										g2d.setFont(MOUSE);
										g2d.setColor(Color.BLACK);
										g2d.drawString("spawn", nx + 30, ny + 40);
										break;
									case '1':
										g2d.setColor(getColor(theChar));
										g2d.fill(new Rectangle(nx, ny, 100, 100));
										g2d.setColor(Color.LIGHT_GRAY);
										g2d.fill(new Rectangle(nx + 60, ny + 40, 4, 4));
										g2d.fill(new Rectangle(nx + 30, ny + 80, 3, 3));
										g2d.draw(new Rectangle(nx, ny, 100, 100));
										break;
									case 'L':
										g2d.setColor(getColor(theChar));
										g2d.fill(new Rectangle(nx, ny, 100, 100));
										g2d.setColor(LIGHT_BLUE);
										g2d.fill(new Rectangle(nx + 70, ny + 65, 10, 10));
										g2d.fill(new Rectangle(nx + 30, ny + 30, 15, 15));
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
										g2d.fill(new Rectangle(nx + 70, ny + 30, 5, 5));
										g2d.fill(new Rectangle(nx + 40, ny + 70, 5, 5));
										g2d.fill(new Rectangle(nx + 10, ny + 15, 3, 3));
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
										g2d.fill(new Rectangle(nx + 70, ny + 30, 5, 5));
										g2d.fill(new Rectangle(nx + 40, ny + 70, 5, 5));
										g2d.fill(new Rectangle(nx + 10, ny + 15, 3, 3));
										g2d.setColor(LIGHT_OFF_GREEN);
										g2d.fill(new Rectangle(nx + 30, ny + 15, 4, 10));
										g2d.fill(new Rectangle(nx + 80, ny + 20, 4, 10));
										g2d.fill(new Rectangle(nx + 20, ny + 80, 4, 10));
										g2d.draw(new Rectangle(nx, ny, 100, 100));
										g2d.setFont(MOUSE);
										g2d.setColor(Color.BLACK);
										g2d.drawString("spawn", nx + 30, ny + 40);
										break;
									case '1':
										g2d.setColor(getColor(theChar));
										g2d.fill(new Rectangle(nx, ny, 100, 100));
										g2d.setColor(OFF_TAN);
										g2d.fill(new Rectangle(nx + 70, ny + 30, 5, 5));
										g2d.fill(new Rectangle(nx + 40, ny + 70, 5, 5));
										g2d.fill(new Rectangle(nx + 10, ny + 15, 3, 3));
										g2d.setColor(LIGHT_OFF_GREEN);
										g2d.fill(new Rectangle(nx + 30, ny + 15, 4, 10));
										g2d.fill(new Rectangle(nx + 80, ny + 20, 4, 10));
										g2d.fill(new Rectangle(nx + 20, ny + 80, 4, 10));
										g2d.draw(new Rectangle(nx, ny, 100, 100));
										break;
									case 'L':
										g2d.setColor(getColor(theChar));
										g2d.fill(new Rectangle(nx, ny, 100, 100));
										g2d.setColor(BLUE);
										g2d.fill(new Rectangle(nx + 70, ny + 65, 30, 5));
										g2d.fill(new Rectangle(nx, ny + 70, 70, 5));
										g2d.fill(new Rectangle(nx + 40, ny + 25, 60, 5));
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
										g2d.fill(new Rectangle(nx + 60, ny + 26, 40, 4));
										g2d.fill(new Rectangle(nx, ny + 80, 30, 4));
										g2d.fill(new Rectangle(nx + 30, ny + 76, 70, 4));
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
										g2d.fill(new Rectangle(nx + 30, ny + 15, 4, 10));
										g2d.fill(new Rectangle(nx + 80, ny + 20, 4, 10));
										g2d.fill(new Rectangle(nx + 20, ny + 80, 4, 10));
										g2d.setColor(Color.GRAY);
										g2d.fill(new Rectangle(nx + 70, ny + 30, 5, 5));
										g2d.fill(new Rectangle(nx + 40, ny + 70, 5, 5));
										g2d.fill(new Rectangle(nx + 10, ny + 15, 3, 3));
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
										g2d.fill(new Rectangle(nx + 30, ny + 15, 4, 10));
										g2d.fill(new Rectangle(nx + 80, ny + 20, 4, 10));
										g2d.fill(new Rectangle(nx + 20, ny + 80, 4, 10));
										g2d.setColor(Color.GRAY);
										g2d.fill(new Rectangle(nx + 70, ny + 30, 5, 5));
										g2d.fill(new Rectangle(nx + 40, ny + 70, 5, 5));
										g2d.fill(new Rectangle(nx + 10, ny + 15, 3, 3));
										g2d.setColor(Color.DARK_GRAY);
										g2d.draw(new Rectangle(nx, ny, 100, 100));
										break;
									case 'L':
										g2d.setColor(getColor(theChar));
										g2d.fill(new Rectangle(nx, ny, 100, 100));
										g2d.setColor(Color.ORANGE);
										g2d.fill(new Rectangle(nx + 70, ny + 65, 10, 10));
										g2d.fill(new Rectangle(nx + 30, ny + 30, 15, 15));
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
										g2d.setColor(Color.LIGHT_GRAY);
										g2d.drawLine(nx, ny + 15, nx + 100, ny + 15);
										g2d.drawLine(nx, ny + 100 - 14, nx + 100, ny + 100 - 14);
										g2d.drawLine(nx, ny + 100 / 2, nx + 100, ny + 100 / 2);
										g2d.drawLine(nx + 100 / 2, ny, nx + 100 / 2, ny + 100);
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
										g2d.fill(new Rectangle(nx + 60, ny + 26, 4, 4));
										g2d.fill(new Rectangle(nx, ny + 80, 4, 4));
										g2d.setColor(Color.DARK_GRAY);
										g2d.draw(new Rectangle(nx, ny, 100, 100));
										break;

									case 'R':
										g2d.setColor(getColor(theChar));
										g2d.fill(new Rectangle(nx, ny, 100, 100));
										g2d.setColor(MED_GRAY);
										g2d.fill(new Rectangle(nx, ny + 30, 4, 4));
										g2d.fill(new Rectangle(nx + 60, ny + 26, 4, 4));
										g2d.fill(new Rectangle(nx, ny + 80, 4, 4));
										break;
									default:
										g2d.setColor(getColor(theChar));
										g2d.fill(new Rectangle(nx, ny, 100, 100));
										break;
									}
									break;
								// TODO Haunted Draw
								case 'H':
									switch (theChar) {
									case 'O':
										g2d.setColor(getColor(theChar));
										g2d.fill(new Rectangle(nx, ny, 100, 100));
										g2d.setColor(LIGHT_SAND_BLUE);
										g2d.fill(new Rectangle(nx + 30, ny + 15, 4, 10));
										g2d.fill(new Rectangle(nx + 80, ny + 20, 4, 10));
										g2d.fill(new Rectangle(nx + 20, ny + 80, 4, 10));
										g2d.setColor(DARK_SAND_BLUE);
										g2d.fill(new Rectangle(nx + 70, ny + 30, 5, 5));
										g2d.fill(new Rectangle(nx + 40, ny + 70, 5, 5));
										g2d.fill(new Rectangle(nx + 10, ny + 15, 3, 3));
										g2d.draw(new Rectangle(nx, ny, 100, 100));
										g2d.setFont(MOUSE);
										g2d.setColor(Color.BLACK);
										g2d.drawString("spawn", nx + 30, ny + 40);
										break;
									case '1':
										g2d.setColor(getColor(theChar));
										g2d.fill(new Rectangle(nx, ny, 100, 100));
										g2d.setColor(LIGHT_SAND_BLUE);
										g2d.fill(new Rectangle(nx + 30, ny + 15, 4, 10));
										g2d.fill(new Rectangle(nx + 80, ny + 20, 4, 10));
										g2d.fill(new Rectangle(nx + 20, ny + 80, 4, 10));
										g2d.setColor(DARK_SAND_BLUE);
										g2d.fill(new Rectangle(nx + 70, ny + 30, 5, 5));
										g2d.fill(new Rectangle(nx + 40, ny + 70, 5, 5));
										g2d.fill(new Rectangle(nx + 10, ny + 15, 3, 3));
										g2d.draw(new Rectangle(nx, ny, 100, 100));
										break;
									case 'L':
										g2d.setColor(getColor(theChar));
										g2d.fill(new Rectangle(nx, ny, 100, 100));
										g2d.setColor(LIGHT_SAND_BLUE);
										g2d.fill(new Rectangle(nx, ny + 30, 40, 4));
										g2d.fill(new Rectangle(nx + 80, ny + 76, 20, 4));
										g2d.fill(new Rectangle(nx, ny + 80, 80, 4));
										g2d.fill(new Rectangle(nx + 40, ny + 26, 60, 4));
										break;
									case 'W':
										g2d.setColor(getColor(theChar));
										g2d.fill(new Rectangle(nx, ny, 100, 100));
										g2d.setColor(LIGHT_SAND_BLUE);
										g2d.draw(new Rectangle(nx, ny, 100, 100));
										break;

									case 'C':
										g2d.setColor(getColor(theChar));
										g2d.fill(new Rectangle(nx, ny, 100, 100));
										g2d.setColor(Color.LIGHT_GRAY);
										g2d.drawLine(nx, ny, nx + 100, ny + 100);
										g2d.drawLine(nx + 100, ny, nx, ny + 100);
										g2d.drawLine(nx, ny + 100 / 2, nx, ny + 100 / 2);
										g2d.drawLine(nx + 100 / 2, ny, nx + 100 / 2, ny + 100);
										break;

									case '>':

										g2d.setFont(BLOCK);
										g2d.setColor(getColor(theChar));
										g2d.fill(new Rectangle(nx, ny, 100, 100));
										g2d.setColor(Color.BLUE);
										g2d.drawString("<->", nx, ny + 70);
										g2d.draw(getBounds());
										break;
									case '2':
										g2d.setColor(getColor(theChar));
										g2d.fill(new Rectangle(nx, ny, 100, 100));
										g2d.setColor(SAND_BLUE);
										g2d.fill(new Rectangle(nx + 70, ny + 65, 10, 10));
										g2d.fill(new Rectangle(nx + 30, ny + 30, 15, 15));
										g2d.fill(new Rectangle(nx + 65, ny + 20, 15, 15));
										g2d.fill(new Rectangle(nx + 50, ny + 70, 20, 20));
										g2d.draw(new Rectangle(nx, ny, 100, 100));
										break;

									case 'R':
										g2d.setColor(getColor(theChar));
										g2d.fill(new Rectangle(nx, ny, 100, 100));
										g2d.setColor(Color.BLACK);

										// Brick pattern
										int i;
										for (i = 0; i < 10; i++)
											g2d.fill(new Rectangle(nx, ny + (10 * i) - (i != 0 ? 2 : 0), 100,
													i != 0 ? 5 : 3));

										g2d.fill(new Rectangle(nx, ny + 98, 100, 2));

										for (i = 0; i < 6; i++)
											g2d.fill(new Rectangle(nx + (20 * i) - (i != 0 ? 2 : 0), ny, i != 0 ? 5 : 3,
													100));
										break;

									default:
										g2d.setColor(getColor(theChar));
										g2d.fill(new Rectangle(nx, ny, 100, 100));
										break;
									}
									break;
								// TODO Lab Draw
								case 'L':
									switch (theChar) {
									case 'O':
										g2d.setColor(getColor(theChar));
										g2d.fill(new Rectangle(nx, ny, 100, 100));
										g2d.setColor(DRAB_BROWN);
										g2d.fillRect(nx + 10, ny + 70, 5, 5);
										g2d.fillRect(nx + 50, ny + 30, 10, 10);
										g2d.fillRect(nx + 80, ny + 80, 10, 10);

										g2d.setColor(Color.WHITE);
										g2d.fillRect(nx + 85, ny + 85, 5, 10);
										g2d.setColor(Color.RED);
										g2d.fillOval(nx + 80, ny + 80, 15, 10);

										g2d.setColor(Color.WHITE);
										g2d.fillRect(nx + 75, ny + 35, 5, 10);
										g2d.setColor(Color.RED);
										g2d.fillOval(nx + 70, ny + 30, 15, 10);

										g2d.setColor(Color.WHITE);
										g2d.fillRect(nx + 45, ny + 45, 5, 10);
										g2d.setColor(Color.RED);
										g2d.fillOval(nx + 40, ny + 40, 15, 10);
										g2d.setFont(MOUSE);
										g2d.setColor(Color.BLACK);
										g2d.drawString("spawn", nx + 30, ny + 40);
										break;
									case '1':
										g2d.setColor(getColor(theChar));
										g2d.fill(new Rectangle(nx, ny, 100, 100));
										g2d.setColor(DRAB_BROWN);
										g2d.fillRect(nx + 10, ny + 70, 5, 5);
										g2d.fillRect(nx + 50, ny + 30, 10, 10);
										g2d.fillRect(nx + 80, ny + 80, 10, 10);

										g2d.setColor(Color.WHITE);
										g2d.fillRect(nx + 85, ny + 85, 5, 10);
										g2d.setColor(Color.RED);
										g2d.fillOval(nx + 80, ny + 80, 15, 10);

										g2d.setColor(Color.WHITE);
										g2d.fillRect(nx + 75, ny + 35, 5, 10);
										g2d.setColor(Color.RED);
										g2d.fillOval(nx + 70, ny + 30, 15, 10);

										g2d.setColor(Color.WHITE);
										g2d.fillRect(nx + 45, ny + 45, 5, 10);
										g2d.setColor(Color.RED);
										g2d.fillOval(nx + 40, ny + 40, 15, 10);
										break;

									case 'L':
										g2d.setColor(getColor(theChar));
										g2d.fill((new Rectangle(nx, ny, 100, 100)));

										g2d.setColor(Color.GREEN);
										g2d.fillOval(nx + 70, ny + 10, 5, 5);
										g2d.fillOval(nx + 30, ny + 50, 10, 10);
										g2d.fillOval(nx + 80, ny + 80, 10, 10);

										break;

									case 'W':
										g2d.setColor(getColor(theChar));
										g2d.fill((new Rectangle(nx, ny, 100, 100)));
										g2d.setColor(Color.BLACK);
										g2d.draw((new Rectangle(nx, ny, 100, 100)));
										break;

									case 'R':
										g2d.setColor(getColor(theChar));
										g2d.fill((new Rectangle(nx, ny, 100, 100)));
										g2d.setColor(Color.BLACK);

										int i;
										for (i = 0; i < 5; i++)
											g2d.drawLine(nx, ny + (20 * i), nx + 100 - 1, ny + (20 * i));

										for (i = 0; i < 5; i++)
											for (int cB = 0; cB < 5; cB++)
												g2d.fillRect(nx + (20 * cB) + 8, ny + (20 * i) + 2, 2, 2);

										break;

									case '2':
										g2d.setColor(getColor(theChar));
										g2d.fill((new Rectangle(nx, ny, 100, 100)));
										g2d.setColor(DRAB_BROWN);
										g2d.fillRect(nx + 10, ny + 70, 5, 5);
										g2d.fillRect(nx + 50, ny + 30, 10, 10);
										g2d.fillRect(nx + 80, ny + 80, 10, 10);
										break;

									case 'C':
										g2d.setColor(getColor(theChar));
										g2d.fill((new Rectangle(nx, ny, 100, 100)));
										g2d.setColor(Color.GRAY);
										g2d.drawLine(nx, ny, nx + 100, ny + 100);
										g2d.drawLine(nx + 100, ny, nx, ny + 100);
										g2d.drawLine(nx, ny + 100 / 2, nx + 100, ny + 100 / 2);
										g2d.drawLine(nx + 100 / 2, ny, nx + 100 / 2, ny + 100);
										break;

									default:
										g2d.setColor(getColor(theChar));
										g2d.fill((new Rectangle(nx, ny, 100, 100)));
										break;
									}
									break;// End Grassy

								case 'G':
								default:// Start grassy
									switch (theChar) {
									case 'O':
										g2d.setColor(getColor(theChar));
										g2d.fill(new Rectangle(nx, ny, 100, 100));
										g2d.setColor(LIGHT_OFF_GREEN);
										g2d.fill(new Rectangle(nx + 30, ny + 15, 4, 10));
										g2d.fill(new Rectangle(nx + 80, ny + 20, 4, 10));
										g2d.fill(new Rectangle(nx + 20, ny + 80, 4, 10));
										g2d.fill(new Rectangle(nx + 60, ny + 60, 4, 10));
										g2d.draw(new Rectangle(nx, ny, 100, 100));
										g2d.setFont(MOUSE);
										g2d.setColor(Color.BLACK);
										g2d.drawString("spawn", nx + 30, ny + 40);
										break;
									case '1':
										g2d.setColor(getColor(theChar));
										g2d.fill(new Rectangle(nx, ny, 100, 100));
										g2d.setColor(LIGHT_OFF_GREEN);
										g2d.fill(new Rectangle(nx + 30, ny + 15, 4, 10));
										g2d.fill(new Rectangle(nx + 80, ny + 20, 4, 10));
										g2d.fill(new Rectangle(nx + 20, ny + 80, 4, 10));
										g2d.fill(new Rectangle(nx + 60, ny + 60, 4, 10));
										g2d.draw(new Rectangle(nx, ny, 100, 100));
										break;
									case 'L':
										g2d.setColor(getColor(theChar));
										g2d.fill(new Rectangle(nx, ny, 100, 100));
										g2d.setColor(LIGHT_BLUE);
										g2d.fill(new Rectangle(nx, ny + 30, 40, 4));
										g2d.fill(new Rectangle(nx + 80, ny + 76, 20, 4));
										g2d.fill(new Rectangle(nx, ny + 80, 80, 4));
										g2d.fill(new Rectangle(nx + 40, ny + 26, 60, 4));
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
										g2d.drawLine(nx, ny + 100 / 2, nx + 100, ny + 100 / 2);
										g2d.drawLine(nx + 100 / 2, ny, nx + 100 / 2, ny + 100);
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
										g2d.setColor(DRAB_BROWN);
										// g2d.fillRect(x + 70, y + 10, 20, 20);
										g2d.fillRect(nx + 10, ny + 70, 5, 5);
										g2d.fillRect(nx + 50, ny + 30, 10, 10);
										g2d.fillRect(nx + 80, ny + 80, 10, 10);
										// g2d.draw(getBounds());
										break;

									case 'R':
										g2d.setColor(getColor(theChar));
										g2d.fill(new Rectangle(nx, ny, 100, 100));
										g2d.setColor(MED_GRAY);
										g2d.fill(new Rectangle(nx, ny + 30, 4, 4));
										g2d.fill(new Rectangle(nx + 60, ny + 26, 4, 4));
										g2d.fill(new Rectangle(nx, ny + 80, 4, 4));
										// g2d.draw(getBounds());
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
						String type = stuff.get(0);
						Image enImg;

						if (zoom || (enX - x > -100 && enX - x < this.getWidth() && enY - y > -100
								&& enY - y < this.getHeight() + 200)) {
							String sB = "images/icon.png";
							if (type.equals("Head Boss")) {
								sB = "images/enemies/bosses/Head.png";
								enImg = new ImageIcon(getClass().getResource(sB)).getImage();
							} else if (type.equals("Lizard Man")) {
								sB = "images/enemies/bosses/LizardMan/front/n.png";
								enImg = new ImageIcon(getClass().getResource(sB)).getImage();
							} else if (type.equals("Pod")) {
								sB = "images/podAll.png";
								enImg = new ImageIcon(getClass().getResource(sB)).getImage();
							}
							else if (type.equals("Stone")) {
								sB = "images/enemies/bosses/Stone.png";
								enImg = new ImageIcon(getClass().getResource(sB)).getImage();
							}
							else {

								enImg = new ImageIcon(getClass().getResource(stuff.get(3))).getImage();
							}
							g2d.drawImage(enImg, enX - x, enY - y - 200, mapEdit);
						}
						int stuffG = 6;
						if (hasPath(stuff.get(0))) {
							stuffG = 7;
						}
						if (isPath(stuff.get(0)) && stuff.size() > stuffG) {
							int addX = enX - x + 50;
							int addY = enY - y - 150;
							// System.out.println(stuff.get(stuffG));
							int[][] points = createArray(stuff.get(stuffG));
							// System.out.println(points.length);
							g2d.setColor(Color.black);
							for (int c3 = 0; c3 < points.length; c3++) {
								g2d.drawLine(addX, addY, addX + (points[c3][0] * 100), addY + (points[c3][1] * 100));
								addX += (points[c3][0] * 100);
								addY += (points[c3][1] * 100);
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
				int spawnNum = 0;
				for (int c = 0; c < objects.size(); c++) {
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
						int val =0;
						try{
						val=stuff.size() > 4 ? Integer.parseInt(stuff.get(4)) : 0;}
						catch(Exception ex){
							val=0;
						}
						if (zoom || (px - x > -100 && px - x < this.getWidth() && py - y > -100
								&& py - y < this.getHeight() + 200)) {
							if (val == -3) {
								g2d.setColor(Color.BLACK);
								if (theSpawn == spawnNum)
									g2d.setColor(Color.RED);
								g2d.setFont(MOUSE);
								g2d.drawString("Spawn" + spawnNum, px - x + 20, py - y - 150);
								spawnNum++;
							} else if (val == -2) {
								try {
									Image pImg = new ImageIcon(getClass().getResource(getAllWanted(stuff.get(2))))
											.getImage();
									g2d.drawImage(pImg, px - x, py - y - 200, mapEdit);
								} catch (Exception ex) {
									System.out.println(getAllWanted(stuff.get(2)));
									ex.printStackTrace();
									System.exit(0);
								}
							}else if(val==-14){
								
								try {
									Image pImg = new ImageIcon(getClass().getResource("images/objects/householdObjects/tableBook.png"))
											.getImage();
									g2d.drawImage(pImg, px - x, py - y - 200, mapEdit);
									if(stuff.get(2).equals("0")){
										pImg = new ImageIcon(getClass().getResource("images/objects/householdObjects/dangerousFire.gif"))
												.getImage();
										g2d.drawImage(pImg, px - x-250, py - y - 200+50, mapEdit);
									}
								} catch (Exception ex) {
									System.out.println(getAllWanted(stuff.get(2)));
									ex.printStackTrace();
									System.exit(0);
								}
							}else if(val<-15&&val>-20){
								
								try {
									Image pImg = new ImageIcon(getClass().getResource("images/icon.png"))
											.getImage();
									g2d.drawImage(pImg, px - x, py - y - 200, mapEdit);
								} catch (Exception ex) {
									System.out.println(getAllWanted(stuff.get(2)));
									ex.printStackTrace();
									System.exit(0);
								}
							}
							else if(val==-1){
								Image pImg = new ImageIcon(getClass().getResource("images/objects/collectibles/keyCrystal/"+stuff.get(2)+".png"))
										.getImage();
								g2d.drawImage(pImg, px - x, py - y - 200, mapEdit);
							}
							else {
								//System.out.println(stuff.get(2)+","+val);
								Image pImg = new ImageIcon(getClass().getResource(stuff.get(2))).getImage();
								g2d.drawImage(pImg, px - x, py - y - 200, mapEdit);
							}
						} else {
							if (val == -3)
								spawnNum++;
						}

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
						if (zoom || (px - x > -100 && px - x < this.getWidth() && py - y > -100
								&& py - y < this.getHeight() + 200)) {
							if (stuff.get(4).equals("normal")) {
								pImg = new ImageIcon(getClass().getResource("images/portals/normal/0.png")).getImage();
							} else if (stuff.get(4).equals("boss")) {
								pImg = new ImageIcon(getClass().getResource("images/portals/boss/open/0.png")).getImage();
							} else {
								pImg = new ImageIcon(getClass()
										.getResource("images/portals/" + stuff.get(4) + "/" + stuff.get(6) + "/c.png"))
												.getImage();
							}
							String to = "to " + stuff.get(2);
							g2d.setFont(MOUSE);
							g2d.setColor(PURPLE);
							g2d.drawImage(pImg, px - x, py - y - 200, mapEdit);
							g2d.drawString(to, px - x, py - y - 200);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

				for (int c = 0; c < npcs.size(); c++) {
					ArrayList<String> stuff = new ArrayList<String>();// should
					// have
					// 5
					String currentS = "";
					for (int c2 = 0; c2 < npcs.get(c).length(); c2++) {

						if (npcs.get(c).charAt(c2) == ',') {
							stuff.add(currentS);
							currentS = "";

						} else {
							currentS += npcs.get(c).charAt(c2);
						}
					}
					if (currentS != "") {
						stuff.add(currentS);
					}
					try {
						int px = Integer.parseInt(stuff.get(0));
						int py = Integer.parseInt(stuff.get(1));
						if (zoom || (px - x > -100 && px - x < this.getWidth() && py - y > -100
								&& py - y < this.getHeight() + 200)) {
							Image pImg = new ImageIcon(getClass().getResource(getImageChar(stuff.get(2))))
									.getImage();
							g2d.drawImage(pImg, px - x, py - y - 200, mapEdit);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
				Point m = MouseInfo.getPointerInfo().getLocation();
				String weather = strings[0].split(",")[1];

				if (weather.equals("rain")) {
					if (weatherT > 0)
						weatherT = -1;
					weatherT++;
					g2d.drawImage(new ImageIcon(getClass().getResource("images/weather/rain/" + weatherT + ".png"))
							.getImage(), 0, 0, this);

				} else if (weather.equals("obscure")) {
					if (!strings[0].split(",")[0].equals("Desert")) {
						if (weatherT > 1)
							weatherT = -1;
						weatherT++;

						g2d.drawImage(new ImageIcon(getClass().getResource("images/weather/snow/" + weatherT + ".png"))
								.getImage(), 0, 0, this);

					} else {
						if (weatherT > 0)
							weatherT = -1;
						weatherT++;
						g2d.drawImage(new ImageIcon(getClass().getResource("images/weather/sand/" + weatherT + ".png"))
								.getImage(), 0, 0, this);

					}
				} else if (weather.equals("fog")) {
					g2d.drawImage(new ImageIcon(getClass().getResource("images/weather/fog/0.png")).getImage(), 0, 0,
							this);
				}
				g2d.setFont(MOUSE);
				g2d.setColor(Color.RED);
				if (defaultMap) {
					g2d.drawString("Default Map", 125, 50);
				} else {
					g2d.drawString("Not Default", 125, 50);
				}

				if (choosing && !zoom) {

					int theX = ((int) (this.getWidth() / 100) * 100) / 2 + (x % 100);
					int theY = ((int) (this.getHeight() / 100) * 100) / 2 + 50 + (y % 100);
					g2d.setColor(Color.red);
					g2d.fillRect(theX - 6, theY - 6, 12, 12);
					g2d.setColor(Color.black);
					g2d.fillOval(theX - 4, theY - 4, 8, 8);
				} else if (!zoom) {

					g2d.setFont(MOUSE);

					if (blocks == 0) {
						g2d.setColor(Color.red);
						g2d.drawString("" + getSelShowStringBlocks(), m.x + 10, m.y - 10);
					} else if (blocks == 1) {
						g2d.setColor(Color.black);
						if (enPre) {

							g2d.drawString(getEnemyShowString(), ((int) ((m.x + x) / 100) * 100) + 50 - x,
									((int) ((m.y + y) / 100) * 100) + 50 - y);
						} else {

							g2d.drawString(getEnemyShowString(), ((int) ((m.x + x) / 50) * 50 - x),
									((int) ((m.y + y) / 50) * 50 - y));
						}
					} else if (blocks == 2) {
						g2d.setColor(PURPLE);
						String portalS = getPortalShowString();
						if (enPre) {

							g2d.drawString(portalS, ((int) ((m.x + x) / 100) * 100) + 25 - x,
									((int) ((m.y + y) / 100) * 100) + 50 - y);
						} else {

							g2d.drawString(portalS, ((int) ((m.x + x) / 50) * 50 + 25 - x),
									((int) ((m.y + y) / 50) * 50 + 50 - y));
						}

					} else if (blocks == 3) {
						g2d.setColor(Color.MAGENTA);

						if (enPre) {

							g2d.drawString(getObjectShowString(), ((int) ((m.x + x) / 25) * 25) + 25 - x,
									((int) ((m.y + y) / 25) * 25) + 50 - y);
						} else {

							g2d.drawString(getObjectShowString(), ((int) ((m.x + x) / 50) * 50 + 25 - x),
									((int) ((m.y + y) / 50) * 50 + 50 - y));
						}

					} else if (blocks == 4) {
						g2d.setColor(Color.ORANGE);

						if (enPre) {

							g2d.drawString(nString, ((int) ((m.x + x) / 50) * 50) + 25 - x,
									((int) ((m.y + y) / 50) * 50) + 50 - y);
						} else {

							g2d.drawString(nString, ((int) ((m.x + x) / 100) * 100 + 25 - x),
									((int) ((m.y + y) / 100) * 100 + 50 - y));
						}

					} else if (blocks == MAXBLOCKS + 1) {
						g2d.setColor(Color.black);

						g2d.drawString("Path", ((int) ((m.x + x) / 100) * 100) + 25 - x,
								((int) ((m.y + y) / 100) * 100) + 50 - y);

					}

				}
			}
		};

		Timer timer = new Timer(100, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (blocks == 0) {
					if (selNum < 1) {
						selNum = 10;
					}
					if (selNum > 10) {
						selNum = 1;
					}
					switch (selNum) {

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
						selChar = '2';
						break;
					case 8:
						selChar = 'L';
						break;
					case 9:
						selChar = 'I';
						break;
					case 10:
						selChar ='0';
						break;
					}

				} else if (blocks == 1) {
					selNum = 0;
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

	public class ObjectChooser extends JFrame {
		String[] typesOfCollectibles = { "Invisible Cloak", "Banana", "Donut", "????", "Gem", "Video Game",
				"Sinister Black Orb of Ultimate Agony and Suffering", "Wizard Hat", "Cobalt Hat", "Goggles", "Cape" };
		String[] typesOfObjects = { "Normal", "Collectible", "SpecialCollectible", "RandSkinObject", "Spawn",
				"BossBlock", "HookObject", "DropPoint", "Money", "ActivatedBossWallActivator", "ActivatedBossWall",
				"PushCube", "BombCube" };
		String[] typesOfCoins = { "1", "3", "5", "10", "20", "50", "100", "-100" };
		ObjectChooser l = this;
		int stage = 0;
		JButton okButton;
		JList<String> list;

		public ObjectChooser() {
			this.setUndecorated(true);
			this.setFocusable(true);
			mapEdit.setFocusable(false);
			this.setResizable(false);

			this.setTitle("ObjectChooser");
			this.setSize(400, 200);
			this.setLocation(drawPan.getWidth() / 2 - this.getWidth() / 2,
					drawPan.getHeight() / 2 - this.getHeight() / 2);
			this.setAlwaysOnTop(true);
			this.setLayout(new BorderLayout());

			okButton = new JButton("OK");
			okButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if (stage == 0) {
						oType = list.getSelectedValue();
						if (oType.equals("SpecialCollectible"))
							oImageString = JOptionPane.showInputDialog(ObjectChooser.this,"What is the value of this Key Crystal?\n1, 3, or 5");
						else if (oType.equals("Spawn"))
							oImageString = "images/icon.png";
						else if (oType.equals("BossBlock"))
							oImageString = "images/portals/bossWall.png";
						else if (oType.equals("HookObject"))
							oImageString = "images/objects/HookObject.png";
						else if (oType.equals("DropPoint"))
							oImageString = "images/objects/chestC.png";
						else if (oType.equals("HookObject"))
							oImageString = "images/objects/HookObject.png";
						else if (oType.equals("DropPoint"))
							oImageString = "images/objects/chestC.png";

						else if (oType.equals("Money")) {
							list.setListData(typesOfCoins);
							list.setSelectedIndex(-1);
							stage = 1;
						} else if (oType.equals("Collectible")) {
							list.setListData(typesOfCollectibles);
							list.setSelectedIndex(-1);
							stage = 1;
						} else if (oType.equals("RandSkinObject")) {
							JFileChooser chooser = new JFileChooser(
									MapEdit.class.getProtectionDomain().getCodeSource().getLocation().getPath()
											+ "/images");
							chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

							chooser.setSelectedFile(
									new File(MapEdit.class.getProtectionDomain().getCodeSource().getLocation().getPath()
											+ "/images"));
							chooser.showOpenDialog(l);

							String[] splits = chooser.getSelectedFile().getPath().split("images");
							oImageString = "images" + splits[splits.length - 1].replace("\\", "/") + "/";
							l.exit();
						} else if (oType.equals("ActivatedBossWall"))
							oImageString = "images/portals/acBossWall.png";
						else if (oType.equals("ActivatedBossWallActivator"))
							oImageString = "images/dummy.png";
						else if (oType.equals("PushCube"))
							oImageString = "images/objects/pushCube.png";
						else if (oType.equals("BombCube"))
							oImageString = "images/objects/bombCube.png";
						else {
							JFileChooser chooser = new JFileChooser(
									MapEdit.class.getProtectionDomain().getCodeSource().getLocation().getPath()
											+ "/images");
							chooser.setFileFilter(new FileFilter() {

								@Override
								public String getDescription() {
									// TODO Auto-generated method stub
									return "Images only. Stay in the game's images.";
								}

								@Override
								public boolean accept(File f) {
									// TODO Auto-generated method stub
									String fileName = f.getName();
									return (fileName.endsWith(".png") || fileName.endsWith(".jpg")
											|| fileName.endsWith(".gif") || !fileName.contains("."));
								}
							});
							int returnVal = chooser.showOpenDialog(l);

							while (!(returnVal == JFileChooser.APPROVE_OPTION && chooser.getSelectedFile().exists()
									&& !chooser.getSelectedFile().isDirectory())) {
								chooser.setSelectedFile(new File(
										MapEdit.class.getProtectionDomain().getCodeSource().getLocation().getPath()
												+ "/images"));
								returnVal = chooser.showOpenDialog(l);
							}
							String[] splits = chooser.getSelectedFile().getPath().split("images");
							oImageString = "images" + splits[splits.length - 1].replace("\\", "/");
							oValue=JOptionPane.showInputDialog(ObjectChooser.this,"Description key? 0=none");
						}
						if (!oType.equals("Money") && !oType.equals("Collectible")) {
							oCollectible = "normal";
							l.exit();
						}
					} else {
						if (oType.equals("Money")) {
							oCollectible = "normal";
							oValue = list.getSelectedValue();
							oImageString = "images/objects/collectibles/coin" + list.getSelectedValue() + ".png";
							exit();
						} else {
							oCollectible = list.getSelectedValue();
							oImageString = getCollectiblePath(oCollectible);
							exit();
						}
					}
					// end stage 0
				}
			});
			this.add(okButton, BorderLayout.SOUTH);
			list = new JList<String>();
			list.setListData(typesOfObjects);
			ScrollPane sc = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);

			sc.add(list);

			this.add(sc, BorderLayout.CENTER);

			this.addWindowListener(new WindowListener() {

				@Override
				public void windowOpened(WindowEvent e) {

				}

				@Override
				public void windowIconified(WindowEvent e) {

				}

				@Override
				public void windowDeiconified(WindowEvent e) {

				}

				@Override
				public void windowDeactivated(WindowEvent e) {

				}

				@Override
				public void windowClosing(WindowEvent e) {

					mapEdit.setFocusable(true);
					mapEdit.requestFocus();
				}

				@Override
				public void windowClosed(WindowEvent e) {

				}

				@Override
				public void windowActivated(WindowEvent e) {

				}
			});
			this.setVisible(true);
			this.requestFocus();
		}

		public void exit() {
			mapEdit.setFocusable(true);
			mapEdit.requestFocus();
			this.dispose();
		}
	}

	public static String[] typesOfNPCs = { "kepler", "sirCobalt", "gatekeeper", "plato", "reyzu", "macaroni",
			"shopkeep", "wizard", "TutorialSirCobalt" };

	public class NPCChooser extends JFrame {
		// JLabel levelLabel;
		// JButton mHealth;
		// JButton mEn;
		// JButton melee;
		// JButton ranged;
		// JButton special;

		NPCChooser l = this;
		int stage = 0;
		JButton okButton;
		JList<String> list;

		public NPCChooser() {

			this.setFocusable(true);
			mapEdit.setFocusable(false);
			this.setResizable(false);

			this.setTitle("NPCChooser");
			this.setSize(400, 200);
			this.setLocation(drawPan.getWidth() / 2 - this.getWidth() / 2,
					drawPan.getHeight() / 2 - this.getHeight() / 2);
			this.setAlwaysOnTop(true);
			this.setLayout(new BorderLayout());

			okButton = new JButton("OK");
			okButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					nString = list.getSelectedValue();
					exit();

					// end stage 0
				}
			});
			this.add(okButton, BorderLayout.SOUTH);
			list = new JList<String>();
			list.setListData(typesOfNPCs);
			ScrollPane sc = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);

			sc.add(list);

			this.add(sc, BorderLayout.CENTER);

			this.addWindowListener(new WindowListener() {

				@Override
				public void windowOpened(WindowEvent e) {

				}

				@Override
				public void windowIconified(WindowEvent e) {

				}

				@Override
				public void windowDeiconified(WindowEvent e) {

				}

				@Override
				public void windowDeactivated(WindowEvent e) {

				}

				@Override
				public void windowClosing(WindowEvent e) {

					mapEdit.setFocusable(true);
					mapEdit.requestFocus();
				}

				@Override
				public void windowClosed(WindowEvent e) {

				}

				@Override
				public void windowActivated(WindowEvent e) {

				}
			});
			this.setVisible(true);
			this.requestFocus();
		}

		public void exit() {
			mapEdit.setFocusable(true);
			mapEdit.requestFocus();
			this.dispose();
		}
	}

	public class PortalChooser extends JFrame {
		// JLabel levelLabel;
		// JButton mHealth;
		// JButton mEn;
		// JButton melee;
		// JButton ranged;
		// JButton special;
		String[] typesOfPortals = { "normal", "boss", "doors" };
		String[] typesOfDoors = { "brown" };
		PortalChooser l = this;
		int stage = 0;
		JButton okButton;
		JList<String> list;

		public PortalChooser() {

			this.setFocusable(true);
			mapEdit.setFocusable(false);
			this.setResizable(false);

			this.setTitle("PortalChooser");
			this.setSize(400, 200);
			this.setLocation(drawPan.getWidth() / 2 - this.getWidth() / 2,
					drawPan.getHeight() / 2 - this.getHeight() / 2);
			this.setAlwaysOnTop(true);
			this.setLayout(new BorderLayout());

			okButton = new JButton("OK");
			okButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if (stage == 0) {
						pType = list.getSelectedValue();
						stage++;
						if (pType.equals("doors"))
							list.setListData(typesOfDoors);
						else {
							pType2 = "portal";
							exit();
						}
						list.setSelectedIndex(-1);
					} else if (stage == 1) {
						pType2 = list.getSelectedValue();
						exit();
					}
					// end stage 0
				}
			});
			this.add(okButton, BorderLayout.SOUTH);
			list = new JList<String>();
			list.setListData(typesOfPortals);
			ScrollPane sc = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);

			sc.add(list);

			this.add(sc, BorderLayout.CENTER);

			this.addWindowListener(new WindowListener() {

				@Override
				public void windowOpened(WindowEvent e) {

				}

				@Override
				public void windowIconified(WindowEvent e) {

				}

				@Override
				public void windowDeiconified(WindowEvent e) {

				}

				@Override
				public void windowDeactivated(WindowEvent e) {

				}

				@Override
				public void windowClosing(WindowEvent e) {

					mapEdit.setFocusable(true);
					mapEdit.requestFocus();
				}

				@Override
				public void windowClosed(WindowEvent e) {

				}

				@Override
				public void windowActivated(WindowEvent e) {

				}
			});
			this.setVisible(true);
			this.requestFocus();
		}

		public void exit() {
			mapEdit.setFocusable(true);
			mapEdit.requestFocus();
			this.dispose();
		}
	}

	public class EnemyChooser extends JFrame {
		// JLabel levelLabel;
		// JButton mHealth;
		// JButton mEn;
		// JButton melee;
		// JButton ranged;
		// JButton special;
		String[] typesOfEnemies = { "Standing", "Tracking", "Head Boss", "Path", "Path Security", "Launch",
				"Pursuing Launch", "Charge", "Walking", "Slime", "Explosive Spawning", "Chain", "Tail", "See Chase",
				"See Shoot", "Security", "Look Chase", "Side To Player", "Lizard Man", "Pod","Stone" };
		EnemyChooser l = this;
		int stage = 0;
		JButton okButton;
		JList<String> list;

		public EnemyChooser() {

			this.setFocusable(true);
			mapEdit.setFocusable(false);
			this.setResizable(false);

			this.setTitle("EnemyChooser");
			this.setSize(400, 200);
			this.setLocation(drawPan.getWidth() / 2 - this.getWidth() / 2,
					drawPan.getHeight() / 2 - this.getHeight() / 2);
			this.setAlwaysOnTop(true);
			this.setLayout(new BorderLayout());

			okButton = new JButton("OK");
			okButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if (stage == 0) {
						eType = list.getSelectedValue();
						stage++;
						if (eType.equals("Head Boss")) {
							eImageString = "Head Boss";
							eBaseHealth = 1000;
							l.exit();
						} else if (eType.equals("Lizard-Man")) {
							eImageString = "Lizard-Man";
							eBaseHealth = 1000;
							l.exit();
						} else if (eType.equals("Pod")) {
							eImageString = "Pod";
							eBaseHealth = 1000;
							l.exit();
						}else if (eType.equals("Stone")) {
							eImageString = "Stone";
							eBaseHealth = 750;
							l.exit();
						} else if (stage == 1) {
							JFileChooser chooser = new JFileChooser(
									MapEdit.class.getProtectionDomain().getCodeSource().getLocation().getPath()
											+ "/images");
							chooser.setFileFilter(new FileFilter() {

								@Override
								public String getDescription() {
									// TODO Auto-generated method stub
									return "Images only. Stay in the game's images.";
								}

								@Override
								public boolean accept(File f) {
									// TODO Auto-generated method stub
									String fileName = f.getName();
									return (fileName.endsWith(".png") || fileName.endsWith(".jpg")
											|| fileName.endsWith(".gif") || !fileName.contains("."));
								}
							});
							int returnVal = chooser.showOpenDialog(l);

							while (!(returnVal == JFileChooser.APPROVE_OPTION && chooser.getSelectedFile().exists()
									&& !chooser.getSelectedFile().isDirectory())) {
								chooser.setSelectedFile(new File(
										MapEdit.class.getProtectionDomain().getCodeSource().getLocation().getPath()
												+ "/images"));
								returnVal = chooser.showOpenDialog(l);
							}
							String[] splits = chooser.getSelectedFile().getPath().split("images");
							eImageString = "images" + splits[splits.length - 1].replace("\\", "/");
							eBaseHealth = 100;
							stage++;

							eFlying = "false";
							String[] options = new String[] { "true", "false" };
							String s = "Is this a flying enemy?";
							if (eType.equals("Side To Player"))
								s = "Does it follow on the X axis?";
							int chosen = JOptionPane.showOptionDialog(l, s, "Map Maker", JOptionPane.DEFAULT_OPTION,
									JOptionPane.INFORMATION_MESSAGE, null, options, "false");
							if (chosen == 0)
								eFlying = "true";
							l.exit();
						}
					} // end stage 0
				}
			});
			this.add(okButton, BorderLayout.SOUTH);
			list = new JList<String>();
			list.setListData(typesOfEnemies);
			ScrollPane sc = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);

			sc.add(list);

			this.add(sc, BorderLayout.CENTER);

			this.addWindowListener(new WindowListener() {

				@Override
				public void windowOpened(WindowEvent e) {

				}

				@Override
				public void windowIconified(WindowEvent e) {

				}

				@Override
				public void windowDeiconified(WindowEvent e) {

				}

				@Override
				public void windowDeactivated(WindowEvent e) {

				}

				@Override
				public void windowClosing(WindowEvent e) {

					mapEdit.setFocusable(true);
					mapEdit.requestFocus();
				}

				@Override
				public void windowClosed(WindowEvent e) {

				}

				@Override
				public void windowActivated(WindowEvent e) {

				}
			});
			this.setVisible(true);
			this.requestFocus();
		}

		public void exit() {
			mapEdit.setFocusable(true);
			mapEdit.requestFocus();
			this.dispose();
		}
	}

public String getImageChar(String type){
	String s="/images/npcs/map/stationary/";
	switch(type){
	case "kepler":
	case"sirCobalt": 
	case"gatekeeper":
	case"plato":
	case"reyzu":
	case"macaroni":
	case"shopkeep":
	case"wizard":
	return s+type+".png";	
	case	"TutorialSirCobalt":
			return s+"sirCobalt.png";
	}
	return "/images/icon.png";
}

	public Color getColor(char type) {
		// TODO Auto-generated method stub
		if(type=='0')
			return getBackground();
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
				return Color.BLACK;
			case 'R':
				return TAN;
			case '>':
				return Color.LIGHT_GRAY;
			case 'C':
				return TAN;

			case '*':
				return LIGHT_BLUE;

			default:
				System.err.println("Type " + type + " does not have a color case");
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
				System.err.println("Type " + type + " does not have a color case");
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
				System.err.println("Type " + type + " does not have a color case");
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
			case 'C':
				return Color.GRAY;
			case '>':

				return Color.LIGHT_GRAY;

			case '*':
				return LIGHT_BLUE;

			default:
				System.err.println("Type " + type + " does not have a color case");
				return Color.RED;
			}
		case 'H':
			switch (type) {
			case '2':
				return DRAB_BROWN;
			case '1':
			case 'O':
				return SAND_BLUE;
			case 'L':
				return BLUE;
			case 'W':
				return DARK_SAND_BLUE;
			case 'P':
				return Color.BLACK;
			case 'R':
				return SAND_RED;
			case '>':
				return Color.LIGHT_GRAY;
			case 'C':
				return DARK_GREEN;

			case '*':
				return LIGHT_BLUE;

			default:
				System.err.println("Type " + type + " does not have a color case");
				return Color.RED;
			}
		case 'L':
			switch (type) {
			case '1':
			case '2':
			case 'O':
				return BROWN;
			case 'L':
				return BLUE;
			case 'W':
				return Color.DARK_GRAY;
			case 'P':
				return Color.BLACK;
			case 'R':
				return Color.GRAY;
			case 'C':
				return Color.WHITE;
			case '*':
				return LIGHT_BLUE;
			default:
				System.err.println("Type " + type + " does not have a color case");
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
				System.err.println("Type " + type + " does not have a color case");
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
				BufferedReader reader = new BufferedReader(new FileReader(saveFile));
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
				strings[0] = lines.get(0);
				for (int c = 1; c < strings.length; c++) {
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

			File saveFile = new File("bin/projects/" + project + "/" + nameWithE());

			if (saveFile.exists()) {

				BufferedReader reader = new BufferedReader(new FileReader(saveFile));
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

			File saveFile = new File("bin/projects/" + project + "/" + nameWithP());

			if (saveFile.exists()) {

				BufferedReader reader = new BufferedReader(new FileReader(saveFile));
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

			File saveFile = new File("bin/projects/" + project + "/" + nameWithN());

			if (saveFile.exists()) {

				BufferedReader reader = new BufferedReader(new FileReader(saveFile));
				String line;

				while ((line = reader.readLine()) != null) {
					npcs.add(line);

				}
				reader.close();

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {

			File saveFile = new File("bin/projects/" + project + "/" + nameWithO());

			if (saveFile.exists()) {

				BufferedReader reader = new BufferedReader(new FileReader(saveFile));
				String line;

				while ((line = reader.readLine()) != null) {
					objects.add(line);

				}
				reader.close();

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		try {
			lines.clear();
			File saveFile = new File("bin/projects/" + project + "/info.txt");
			if (saveFile.exists()) {
				BufferedReader reader = new BufferedReader(new FileReader(saveFile));
				String line;

				while ((line = reader.readLine()) != null) {

					lines.add(line);
				}
				reader.close();

				if (lines.size() > 0 && name.split("/")[1].startsWith(lines.get(0)))
					defaultMap = true;
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

	public String nameWithN() {
		// TODO Auto-generated method stub
		String fName = "";
		String extension = "";
		boolean ext = false;
		for (int c = 0; c < name.length(); c++) {
			if (ext) {
				extension += name.charAt(c);
			} else if (name.charAt(c) == '.') {
				ext = true;
				fName += "N.";
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

	// public boolean cop(char selChar) {
	//
	// switch (selChar) {
	//
	// case 'r'://security
	// case 'b':
	// return true;
	//
	//
	//
	//
	// default:
	// return false;
	// }
	//
	// }
	// public String selDelay() {
	//
	// switch (selChar) {
	// case 'M':
	// return "20";
	// case '0':
	// case '1':
	// case 'P':
	// return "75";
	// case 'r'://security
	// case 'p'://backSecurity
	// case 'S':
	// return "cop";
	// case '~':
	// return ""+3;
	// case 'D':
	// return "100";
	//
	//
	//
	// default:
	// return null;
	// }
	//
	// }
	// public String selDelay2(){
	// switch (selChar) {
	//
	//
	//
	// case 'r'://security
	//
	// case 'd'://Path
	//
	// case 'R'://backPath
	// case 'p'://backSecurity
	// return "0'0";
	// default:
	// return null;
	// }
	// }
	// public String selDelay3() {
	//
	// switch (selChar) {
	//
	//
	//
	// case 'r'://security
	//
	// case 'd'://Path
	// return "F";
	// case 'R'://backPath
	// case 'p'://backSecurity
	// return "B";
	// default:
	// return null;
	// }
	//
	// }
	// public char selFlying() {
	//
	// switch (selChar) {
	// case 'G':
	// case 'I':
	// case 'H':
	// case 'X':
	// return 't';
	// case 'x':
	// default:
	// return 'f';
	//
	// }
	//
	// }
	//
	// public int selHealth() {
	// switch (selChar) {
	//
	// case 'P':
	// case 'F':
	// case 'D':
	// return 50;
	//
	// case 't':
	// case '0':
	// return 60;
	//
	// case 'T':
	//
	// case 'c':
	// //backPath
	//
	// case 'r'://security
	//
	// case 'd'://Path
	//
	// case 'p'://backSecurity
	//
	// return 800;
	// case 'H':
	// case 'L':
	// return 1000;
	//
	// case 'R':
	// case 'C':
	// case 'X':
	// case 'x':
	// return -10;
	//
	// case '1':
	// case 'B':
	// case 'G':
	// case 'M':
	// case 'S':
	// case 'I':
	//
	//
	// default:
	// return 100;
	//
	// }
	// }

	public void addRowEnd() {
		String[] nStrings = new String[strings.length + 1];
		int ml = 0;
		for (int c = 0; c < strings.length - 1; c++) {
			nStrings[c] = strings[c];
			if (strings[c].length() > ml) {
				ml = strings[c].length();
			}
		}
		String addString = "";
		addString += 'I';
		for (int c = 1; c < ml - 1; c++) {
			addString += '1';
		}
		addString += 'I';
		nStrings[nStrings.length - 2] = addString;
		nStrings[nStrings.length - 1] = strings[strings.length - 1];
		strings = nStrings;
	}

	public void addRowMid() {
		String[] nStrings = new String[strings.length + 1];
		int ml = 0;
		int cA = 0;

		int theRow = (int) ((y + (this.getHeight() / 2)) / 100) + 2;
		for (int c = 0; c < strings.length - 0; c++, cA++) {

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
		for (int c = 1; c < ml - 1; c++) {
			addString += '1';
		}
		addString += 'I';
		nStrings[theRow] += addString;

		strings = nStrings;
	}

	public void addColEnd() {
		strings[1] += 'I';

		for (int c = 2; c < strings.length - 1; c++) {
			strings[c] = strings[c].substring(0, strings[c].length() - 1) + '1'
					+ strings[c].substring(strings[c].length() - 1);
		}
		strings[strings.length - 1] += "I";
	}

	public void addColMid() {

		int block = (int) ((x + (this.getWidth() / 2)) / 100);
		strings[1] += "I";
		for (int c = 2; c < strings.length - 1; c++) {
			strings[c] = strings[c].substring(0, block) + "1" + strings[c].substring(block);
		}
		strings[strings.length - 1] += "I";
	}

	public void remRowEnd() {
		String[] nStrings = new String[strings.length - 1];
		int ml = 0;
		for (int c = 0; c < nStrings.length - 1; c++) {

			nStrings[c] = strings[c];
			if (strings[c].length() > ml) {
				ml = strings[c].length();
			}
		}
		nStrings[nStrings.length - 1] = "";
		for (int c = 0; c < ml; c++) {
			nStrings[nStrings.length - 1] += "I";
		}
		strings = nStrings;
	}

	public void remRowMid() {
		String[] nStrings = new String[strings.length - 1];

		int cA = 0;
		int theRow = (int) ((y - 1 + (this.getHeight() / 2)) / 100) + 2;
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
			strings[c] = strings[c].substring(0, strings[c].length() - 2) + "I";
		}
	}

	public void remColMid() {

		int block = (int) ((x + (this.getWidth() / 2)) / 100);
		for (int c = 1; c < strings.length; c++) {
			strings[c] = strings[c].substring(0, block - 1) + strings[c].substring(block);
		}
	}

	private String getSelShowStringBlocks() {

		switch (selChar) {

		case '1':
			return "grass";
		case '2':
			return "dirt";
		case '0':
			return "nothing";

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
		case 'H':
			return SAND_BLUE;
		case 'L':
			return Color.WHITE;
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

		if (!string.equals("")) {
			String[] split = string.split("\'");
			int splitLength = split.length / 2;

			int splitCounter = 0;
			int p2;
			int[][] toReturn = new int[splitLength][2];
			for (int p1 = 0; p1 < splitLength; p1++) {
				for (p2 = 0; p2 < toReturn[p1].length; p2++) {
					toReturn[p1][p2] = Integer.parseInt(split[splitCounter]);
					splitCounter++;
				}
			}

			// for (int k = 0; k < toReturn.length; k++) {
			// for (int c = 0; c < toReturn[k].length; c++)
			// //System.out.print(toReturn[k][c] + " ");
			// //System.out.println();
			// }

			return toReturn;
		} else
			return new int[0][0];
	}

	public int objectsAddX() {
		switch (oImageString) {
		case "images/objects/leaves.png":
			return 35;
		case "images/objects/leavesSc.png":
			return 10;
		case "images/objects/Wood.png":
			return 40;
		case "images/objects/collectibles/coin0.png":
		case "images/objects/collectibles/coin1.png":

		default:
			return 0;
		}
	}

	public int objectsAddY() {
		switch (oImageString) {
		case "images/objects/leaves.png":
			return 35;
		case "images/objects/leavesSc.png":
			return 10;
		case "images/objects/Wood.png":
			return 40;
		case "images/objects/collectibles/coin0.png":
		case "images/objects/collectibles/coin1.png":

		default:
			return 0;
		}
	}

	public boolean getObjectCollide() {
		String[] options = new String[] { "Yes", "No" };
		;
		switch (oType) {
		case "Normal":
		case "Collectible":
		case "RandSkinObject":
			return JOptionPane.showOptionDialog(mapEdit, "Is this a wall object?", "Map Maker",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, "No") == 0;
		case "Spawn":
			return JOptionPane.showOptionDialog(mapEdit, "Is this also a checkpoint?", "Map Maker",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, "No") == 0;
		// case"PushCube":
		// case"BombCube":
		// return JOptionPane.showOptionDialog(mapEdit, "Is this also a
		// checkpoint?", "Map Maker",JOptionPane.DEFAULT_OPTION,
		// JOptionPane.INFORMATION_MESSAGE, null, options, "No")==0;
		default:
			return false;
		}
	}

	public String extraEn(String type) {
		switch (type) {
		case "Launch":
		case "Pursuing Launch":
		case "Explosive Spawning":
		case "See Shoot":
			return JOptionPane.showInputDialog("What delay time?");
		case "Chain":
		case "Tail":
			return JOptionPane.showInputDialog("How many Links?");

		case "Path Security":
		case "Security":
			String string = "images/enemies/unique/cop.png";
			JFileChooser chooser = new JFileChooser(
					MapEdit.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "/images");
			chooser.setFileFilter(new FileFilter() {

				@Override
				public String getDescription() {
					// TODO Auto-generated method stub
					return "Images only. Stay in the game's images.";
				}

				@Override
				public boolean accept(File f) {
					// TODO Auto-generated method stub
					String fileName = f.getName();
					return (fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".gif")
							|| !fileName.contains("."));
				}
			});
			int returnVal = chooser.showOpenDialog(mapEdit);
			if (returnVal == JOptionPane.YES_OPTION) {
				String[] splits = chooser.getSelectedFile().getPath().split("images");
				string = "images" + splits[splits.length - 1].replace("\\", "/");
			}
			return string;
		default:
			return null;
		}
	}

	public String extraEn2(String type) {
		switch (type) {
		case "Tail":
			return JOptionPane.showInputDialog("Distance?");
		case "Path":
		case "PatrolChase":
			return JOptionPane.showOptionDialog(mapEdit, "Is this a backwards path enemy?", "Map Maker",
					JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[] { "Yes", "No" },
					"Yes") == 0 ? ",B" : ",L";
		case "Path Security":
			return JOptionPane.showOptionDialog(mapEdit, "Is this a backwards path security enemy?", "Map Maker",
					JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[] { "Yes", "No" },
					"Yes") == 0 ? ",B" : ",L";

		default:
			return null;
		}
	}

	public String getNPC3(String answer) {
		switch (nString) {
		case "Ham-fisted-dude":
			int number = 3;
			try {
				number = Integer.parseInt(answer);
			} catch (Exception ex) {

			}
			return "" + number;
		case "Sir Cobalt":
			return answer.equalsIgnoreCase("y") ? "t" : "f";
		default:
			return null;
		}
	}

	public String getImageChar() {
		return this.getImageChar(nString);
	}

//	public String getImageChar(String nString) {
//		switch (nString) {
//		case "kepler":
//			return "images/npcs/map/stationary/kepler.png";
//		case "sirCobalt":
//			return "images/npcs/map/stationary/sirCobalt.png";
//		case "TutorialSirCobalt":
//			return "images/npcs/map/stationary/sirCobalt.png";
//		case "wizard":
//			return "images/npcs/map/stationary/wizard.png";
//		case "shopkeep":
//			return "images/npcs/map/stationary/shopkeep.png";
//		case "plato":
//			return "images/npcs/map/stationary/plato.png";
//		case "Police man":
//			return "images/npcs/map/stationary/policeman.png";
//		case "gatekeeper":
//			return "images/npcs/map/stationary/gatekeeper.png";
//		case "macaroni":
//			return "images/npcs/map/stationary/macaroni.png";
//		case "reyzu":
//			return "images/npcs/map/stationary/reyzu.png";
//		default:
//			return null;
//		}
//	}

	public int npcX() {
		switch (nString) {

		default:
			return 0;
		}
	}

	public int npcY() {
		switch (nString) {
		case "sirCobalt":
			return -9;
		default:
			return 0;
		}
	}

	public String getObjectShowString() {
		String returnS = oType;

		if (returnS.equals("Money"))
			returnS = oValue + " " + oType;

		return returnS;
	}

	public String getPortalShowString() {
		String returnS = pType;

		switch (pType2) {
		case "portal":
			returnS += " portal";
			break;
		default:
			returnS = pType2 + " door";
			break;
		}
		return returnS;
	}

	public String getEnemyShowString() {
		String returnS = eType;

		switch (eImageString) {
		case "Head Boss":
		case "Lizard Man":
		case "Pod":
			break;
		case "images/enemies/unique/pizza.png":
			returnS += " pizza";
			break;
		default:
			returnS += " enemy";
		}
		return returnS;
	}

	public boolean hasPath(String s) {
		switch (s) {

		case "Path Security":
			return true;
		case "Path":
		default:
			return false;
		}
	}

	public boolean isPath(String s) {
		switch (s) {

		case "Path Security":
		case "Path":
			return true;
		default:
			return false;
		}
	}

	public String getObjectsVal() {
		switch (oType) {

		case "SpecialCollectible":
			return "-1";
		case "RandSkinObject":
			return "-2";
		case "Spawn":
			return "-3";
		case "BossBlock":
			return "-4";
		case "HookObject":
			return "-5";
		case "DropPoint":
			return "-6";
		case "ActivatedBossWallActivator":
			return "-7";
		case "ActivatedBossWall":
			return "-8";
		case "PushCube":
			return "-9";
		case "BombCube":
			return "-10";
		case "Money":
			return null;
		case "Normal":
		case "Collectible":
		default:
			return null;
		}
	}

	public static String getAllWanted(String folderLoc) {
		switch (folderLoc) {
		// case "images/objects/table/":
		// return "images/objects/table/table.png";//first anyways
		default:
			String s;
			try {
				s = folderLoc + new File(folderLoc).list()[0];
			} catch (Exception ex) {
				s = "images/icon.png";
			}
			return s;
		}
	}
	String[] typesOfCollectibles = { "Cape" };

	public String getCollectiblePath(String name) {
		switch (name) {
		case "Invisible Cloak":
			return "images/objects/inventoryObjects/notSoInvisibleCloak.png";
		case "Banana":
			return "images/objects/food/banana.png";
		case "Donut":
			return "images/objects/food/DonutUnhealthyAmountOfFoodColoring.png";
		case "????":
			return "images/objects/InventoryObjects/whatIsThis.png";
		case "Gem":
			return "images/objects/InventoryObjects/gem.png";
		case "Video Game":
			return "images/objects/InventoryObjects/videoGame0.png";
		case "Sinister Black Orb of Ultimate Agony and Suffering":
			return "images/objects/InventoryObjects/blackOrb.png";
		case "Wizard Hat":
			return "images/objects/InventoryObjects/wizardHat.png";
		case "Cobalt Hat":
			return "images/objects/InventoryObjects/cobaltHat.png";
		case "Goggles":
			return "images/objects/InventoryObjects/keplerGoggles.png";
		case "Cape":
			return "images/objects/InventoryObjects/cheesyCape.png";
		default:
			return "images/icon.png";
		}
	}
}
