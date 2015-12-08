import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.AbstractButton;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MapMaker extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String project;
	MapMaker mapMaker = this;
	Files files;
	JButton addMap;
	JButton editMap;
	JPanel drawPan;
	JPanel bPanel;
	JButton makeNMap;
	JTextField nMSize = new JTextField("Size", 4);
	DefaultListModel<String> edMM = new DefaultListModel<String>();
	JList<String> edML = new JList<String>(edMM);
	JButton edMEd = new JButton("Edit");
	JButton edMCn = new JButton("Cancel");

	JPanel tilspan = new JPanel();
	JTextField nMheight = new JTextField("Height", 5);

	DefaultListModel<String> mLoadM = new DefaultListModel<String>();
	JList<String> mLoadL = new JList<String>(mLoadM);
	JButton mEdit;

	public MapMaker(String s) {
		project = s;
		
		this.setTitle("MapMaker");

	}

	public void updateNor() {
		try {
			// Files.copy("projects/"+project,"bin/projects",REPLACE_EXISTING);

			// Path
			// source=FileSystems.getDefault().getPath("projects/"+project);
			// Path
			// target=FileSystems.getDefault().getPath("bin/projects/"+project);
			// works for file
			// Files.copy(source, target, REPLACE_EXISTING);
			copyDir(FileSystems.getDefault().getPath("bin/projects/" + project),
					FileSystems.getDefault().getPath("projects/" + project));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateAllNor() {
		try {
			// Files.copy("projects/"+project,"bin/projects",REPLACE_EXISTING);

			// Path
			// source=FileSystems.getDefault().getPath("projects/"+project);
			// Path
			// target=FileSystems.getDefault().getPath("bin/projects/"+project);
			// works for file
			// Files.copy(source, target, REPLACE_EXISTING);
			copyDir(FileSystems.getDefault().getPath("bin/projects/"),
					FileSystems.getDefault().getPath("projects/"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void deleteDir(Path target) throws IOException {
		File[] ft = new File(target.toString()).listFiles();
		for (int c = 0; c < ft.length; c++) {
			if (ft[c].isDirectory()) {
				deleteDir(ft[c].toPath());
			} else {

				Files.delete(ft[c].toPath());
			}
		}
		Files.delete(target);
	}

	public static void copyDir(Path source, Path target) throws IOException {

		if (target.toFile().exists()) {
			// delete all files
			deleteDir(target);

		}

		File[] f = new File(source.toString()).listFiles();
		Files.copy(source, target, REPLACE_EXISTING);

		// if(!target.toFile().exists()){

		// //target.toFile().delete();
		// }
		//
		//

		for (int c = 0; c < f.length; c++) {
			if (f[c].isDirectory()) {
				copyDir(FileSystems.getDefault().getPath(f[c].getPath()),
						FileSystems.getDefault().getPath(
								target + "/"
										+ Run.removeExtension(f[c].getPath())));
			} else {
				// if(new File(target+ "/" +
				// Run.removeExtension(f[c].getPath())).exists()){
				// new File(target+ "/" +
				// Run.removeExtension(f[c].getPath())).delete();
				// }

				Files.copy(
						FileSystems.getDefault().getPath(f[c].getPath()),
						FileSystems.getDefault().getPath(
								target + "/"
										+ Run.removeExtension(f[c].getPath())),
						REPLACE_EXISTING);
			}
		}
	}

	public void run() {
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setUndecorated(true);
		File f = new File("bin/projects/" + project);
		if (!f.exists()) {

			f.mkdir();

			updateNor();

		} else {

		}

		mEdit = new JButton("Edit");
		mEdit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (mLoadL.getSelectedIndex() != -1) {
					bPanel.removeAll();
					bPanel.revalidate();
					mapMaker.remove(bPanel);
					mapMaker.revalidate();
					mapMaker.remove(mLoadL);
					mapMaker.remove(drawPan);
					mapMaker.add(drawPan, BorderLayout.NORTH);
					mapMaker.add(drawPan, BorderLayout.SOUTH);
					mapMaker.add(drawPan, BorderLayout.CENTER);

					bPanel.add(editMap);
					bPanel.add(addMap);
					mapMaker.add(bPanel, BorderLayout.SOUTH);
					// ArrayList<URL>imURL=new ArrayList<URL>();
					// for(int c=0;c<tileM.size();c++){
					// imURL.add(mapMaker.getClass().getResource("bin/projects/"+project+"/tiles/"+tileM.get(c)));
					// }
					new MapEdit(mLoadL.getSelectedValue(), project).run();
				} else {
					JOptionPane.showMessageDialog(mapMaker,
							"You must select a value");
				}
			}
		});

		setFocusable(true);
		this.setLayout(new BorderLayout());
		this.addKeyListener(new MyKeyListener());
		// TODO Auto-generated method stub
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		makeNMap = new JButton("Create");
		makeNMap.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					boolean b = false;
					String[] s = new File("bin/projects/" + project).list();
					for (int c = 0; c < s.length; c++) {
						if (s[c].equalsIgnoreCase(project + ".txt")) {
							b = true;
							break;
						}
					}
					if (b == false
							|| JOptionPane.showConfirmDialog(
									mapMaker,
									"The file "
											+ project
											+ ".txt already exists. Do you want to override it? You may only have one map per pack.") == 0) {
						if (Integer.parseInt(nMSize.getText()) <= 500) {
							if (Integer.parseInt(nMheight.getText()) <= 500) {
								// String[]arr=new
								// String[Integer.parseInt(nMSize.getText())*Integer.parseInt(nMSize.getText())];
								// for(int c=0;c<arr.length;c++){
								// arr[c]="clear.jpg";
								// }

								String[] arrB = new String[Integer
										.parseInt(nMheight.getText())+3];
								int width = Integer.parseInt(nMSize.getText())+2;
								arrB[0]="Grassy,none,1,0";
							
									arrB[1]="";
								for(int c=0;c<width;c++){
									arrB[1]+='I';
								}
								for (int c = 2; c < arrB.length-1; c++) {
									arrB[c] = "";
									arrB[c]+="I";
									for (int c2 = 1; c2 < width-1; c2++) {
										arrB[c] += "1";
									}
									arrB[c]+="I";
								}
								arrB[arrB.length-1]="";
								for(int c=0;c<width;c++){
									arrB[arrB.length-1]+='I';
								}
								try {
									arrB[0]="Grassy,none,1";
									saveStrings(arrB, project + ".txt",
											project);
									saveStrings(new String[0], project
											+ "E.txt", project);
									saveStrings(new String[0], project
											+ "P.txt", project);
									saveStrings(new String[0], project
											+ "O.txt", project);
									saveStrings(new String[0], project
											+ "N.txt", project);
								} catch (Exception ex) {
									JOptionPane.showMessageDialog(mapMaker,
											ex.getMessage());
								}
								// CHANGE TO SER LATER
							}
						} else {
							JOptionPane
									.showMessageDialog(mapMaker,
											"Height size must be equal or less than 500");
						}
					} else {
						JOptionPane.showMessageDialog(mapMaker,
								"Side size must be equal or less than 500");
					}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(mapMaker,
							"Side size must be a number");
				}catch(ArrayIndexOutOfBoundsException e2){
					JOptionPane.showMessageDialog(mapMaker, "Size must be greater than 0");
				}
				updateNor();
			
				mapMaker.revalidate();
				bPanel.removeAll();

				bPanel.validate();
				bPanel.revalidate();

				bPanel.add(editMap);
				bPanel.add(addMap);

				bPanel.revalidate();
				bPanel.validate();

				mapMaker.remove(bPanel);
				// mapMaker.remove(nMSize);
				mapMaker.revalidate();
				mapMaker.remove(drawPan);
				mapMaker.revalidate();
				mapMaker.add(drawPan, BorderLayout.SOUTH);
				mapMaker.revalidate();
				mapMaker.add(drawPan, BorderLayout.EAST);
				mapMaker.revalidate();
				mapMaker.add(drawPan, BorderLayout.CENTER);
				mapMaker.revalidate();
				mapMaker.add(bPanel, BorderLayout.SOUTH);
				mapMaker.revalidate();
				// mapMaker.getContentPane().removeAll();
				// mapMaker.repaint();
				// mapMaker.add(edTiL,BorderLayout.CENTER);

				// mapMaker.remove(edTiAdd);
				// mapMaker.revalidate();

				// mapMaker.add(edTiAdd,BorderLayout.NORTH);

				// mapMaker.add(new JPanel(),BorderLayout.NORTH);
				// mapMaker.add(bPanel,BorderLayout.SOUTH);
				mapMaker.add(drawPan, BorderLayout.NORTH);
				mapMaker.revalidate();
				mapMaker.add(drawPan, BorderLayout.EAST);
				mapMaker.revalidate();
				mapMaker.add(drawPan, BorderLayout.CENTER);
				mapMaker.revalidate();
			}
		});
		
		drawPan = new JPanel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				g2.clearRect(0, 0, this.getWidth(), this.getHeight());
				// Check bin for view
				g2.drawImage(
						new ImageIcon(getClass().getResource("images/view.png"))
								.getImage(), 0, 0-mLoadL.getHeight(), mapMaker.getWidth(),mapMaker
								.getHeight(), this);
				g2.setColor(Color.BLACK);
				int sizeForm = this.getWidth() / 25;
				g2.setFont(new Font("sans", Font.PLAIN, sizeForm));
				g2.drawString("Map Maker",
						this.getWidth() / 2 - this.getWidth() / 10,
						(mapMaker.getHeight() / 2)-mLoadL.getHeight());
			}
		};
		this.add(drawPan, BorderLayout.CENTER);
		bPanel = new JPanel();
		bPanel.setBackground(Color.BLACK);

		editMap = new JButton("Edit Map");
		editMap.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				bPanel.removeAll();
				bPanel.revalidate();
				mapMaker.remove(bPanel);
				JButton cancel = new JButton("Cancel");
				cancel.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						bPanel.removeAll();
						bPanel.revalidate();
						mapMaker.remove(bPanel);
						mapMaker.revalidate();
						mapMaker.remove(mLoadL);
						mapMaker.remove(drawPan);

						mapMaker.add(drawPan, BorderLayout.NORTH);
						mapMaker.add(drawPan, BorderLayout.SOUTH);
						mapMaker.add(drawPan, BorderLayout.CENTER);

						bPanel.add(editMap);
						bPanel.add(addMap);
						mapMaker.add(bPanel, BorderLayout.SOUTH);
					}
				});
				JButton export = new JButton("Export");
				export.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						if (mLoadM.size() != 0) {
							JFileChooser chooser = new JFileChooser();
							chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

							int returnVal = chooser.showOpenDialog(mapMaker);
							if (returnVal == JFileChooser.APPROVE_OPTION) {
								if (chooser.getSelectedFile().exists()) {
									String[] options = { "yes", "no" };
									boolean newFile = true;
//											JOptionPane
//											.showOptionDialog(
//													mapMaker,
//													"Do you want to create a folder for your map(s)?",
//													"Create Folder?",
//													JOptionPane.DEFAULT_OPTION,
//													JOptionPane.INFORMATION_MESSAGE,
//													null, options, "no") == 0;

									if (newFile) {
										File nFile = new File(chooser
												.getSelectedFile().toString()
												+ "/" + project);

										// if(nFile.exists()){
										// try {
										// deleteDir(nFile.toPath());
										// } catch (IOException e1) {
										// // TODO Auto-generated catch block
										// e1.printStackTrace();
										// }
										// }

										nFile.mkdir();
										System.out.println(nFile.exists());
									}

									try {
										File[] files = new File("bin/projects/"
												+ project).listFiles();

										for (int c = 0; c < files.length; c++) {
											if (new File(
													chooser.getSelectedFile()
															.toString()
															+ "/"
															+ (newFile ? project
																	+ "/"
																	: "")
															+ Run.removeExtension(files[c]
																	.toString()))
													.exists()) {
												Files.delete(new File(
														chooser.getSelectedFile()
																.toString()
																+ "/"
																+ (newFile ? project
																		+ "/"
																		: "")
																+ Run.removeExtension(files[c]
																		.toString()))
														.toPath());
											}
											Files.copy(
													files[c].toPath(),
													new File(
															chooser.getSelectedFile()
																	.toString()
																	+ "/"
																	+ (newFile ? project
																			+ "/"
																			: "")
																	+ Run.removeExtension(files[c]
																			.toString()))
															.toPath());
										}
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								}
							}
						}
					}

				});
				JButton delete = new JButton("Delete");
				delete.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						if (mLoadL.getSelectedIndex() != -1
								&& 0 == JOptionPane.showConfirmDialog(mapMaker,
										"Are you sure you want to delete "
												+ mLoadL.getSelectedValue()
												+ "?")) {
							try {
								Files.delete(new File("bin/projects/" + project
										+ "/" + mLoadL.getSelectedValue())
										.toPath());
								if (new File("bin/projects/" + project + "/"
										+ nameWithE(mLoadL.getSelectedValue()))
										.exists()) {
									Files.delete(new File("bin/projects/"
											+ project
											+ "/"
											+ nameWithE(mLoadL
													.getSelectedValue()))
											.toPath());

								}
								mLoadM.removeAllElements();
								String[] s = new File("bin/projects/" + project)
										.list(new FilenameFilter() {

											@Override
											public boolean accept(File dir,
													String name) {
												// TODO Auto-generated method
												// stub
												return name.toLowerCase()
														.endsWith(".txt")
														&& !name.endsWith("E.txt");
											}
										});
								for (int c = 0; c < s.length; c++) {
									mLoadM.addElement(s[c]);
								}
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							updateNor();
						}
					}
				});
				String[] s = new File("bin/projects/" + project)
						.list(new FilenameFilter() {

							@Override
							public boolean accept(File dir, String name) {
								// TODO Auto-generated method stub
								return name.toLowerCase().endsWith(".txt")
										&& !name.endsWith("E.txt")&& !name.endsWith("P.txt")&& !name.endsWith("O.txt")&& !name.endsWith("N.txt");
							}
						});
				mLoadM.removeAllElements();
				for (int c = 0; c < s.length; c++) {
					mLoadM.addElement(s[c]);
				}
				bPanel.add(cancel);
				bPanel.add(delete);
				bPanel.add(export);
				bPanel.add(mEdit);
				mapMaker.remove(drawPan);
				mapMaker.revalidate();
				mapMaker.add(drawPan, BorderLayout.SOUTH);
				mapMaker.revalidate();
				mapMaker.add(drawPan, BorderLayout.CENTER);
				mapMaker.revalidate();
				mapMaker.add(bPanel, BorderLayout.SOUTH);
				mapMaker.revalidate();
				mapMaker.add(mLoadL, BorderLayout.NORTH);
				mapMaker.revalidate();
			}

		});
		addMap = new JButton("Add Map");
		addMap.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// mapMaker.remove(drawPan);
				//nMapN.setText("Enter Map Name");
				nMSize.setText("Side");

				nMheight.setText("Height");
		

				bPanel.removeAll();
				JButton cancel = new JButton("Cancel");
				cancel.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
						// mapMaker.remove(nMSize);
						mapMaker.revalidate();
						bPanel.removeAll();

						bPanel.validate();
						bPanel.revalidate();

						bPanel.add(editMap);
						bPanel.add(addMap);

						bPanel.revalidate();
						bPanel.validate();

						mapMaker.remove(bPanel);
						mapMaker.revalidate();
						mapMaker.remove(drawPan);
						mapMaker.revalidate();
						mapMaker.add(drawPan, BorderLayout.SOUTH);
						mapMaker.revalidate();
						mapMaker.add(drawPan, BorderLayout.EAST);
						mapMaker.revalidate();
						mapMaker.add(drawPan, BorderLayout.CENTER);
						mapMaker.revalidate();
						mapMaker.add(bPanel, BorderLayout.SOUTH);
						mapMaker.revalidate();

						// mapMaker.getContentPane().removeAll();
						// mapMaker.repaint();
						// mapMaker.add(edTiL,BorderLayout.CENTER);

						// mapMaker.remove(edTiAdd);
						// mapMaker.revalidate();

						// mapMaker.add(edTiAdd,BorderLayout.NORTH);

						// mapMaker.add(new JPanel(),BorderLayout.NORTH);
						// mapMaker.add(bPanel,BorderLayout.SOUTH);
						mapMaker.add(drawPan, BorderLayout.NORTH);
						mapMaker.revalidate();
						mapMaker.add(drawPan, BorderLayout.CENTER);
						mapMaker.revalidate();
					}
				});

				bPanel.add(nMSize);
				bPanel.add(nMheight);
				bPanel.add(cancel);
				bPanel.add(makeNMap);
				mapMaker.revalidate();
			}

		});

		bPanel.add(editMap);
		bPanel.add(addMap);
		mapMaker.revalidate();
		this.add(bPanel, BorderLayout.SOUTH);
		this.setVisible(true);
		requestFocus();
		mapMaker.revalidate();
	}

	public class MyKeyListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				System.exit(0);
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}

	}

	public static void enObj(Object obj, String file, String project) {

		try {
			ObjectOutputStream os = new ObjectOutputStream(
					new FileOutputStream("bin/projects/" + project + "/" + file));
			os.writeObject(obj);
			os.close();

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
			ex.printStackTrace();
		}
	}

	public static Object loadObj(String file, String project) {
		try {// "bin/projects/"+project+"/tiles/Tiles.ser"
			ObjectInputStream is = new ObjectInputStream(new FileInputStream(
					"bin/projects/" + project + "/" + file));
			Object obj = is.readObject();
			is.close();
			return obj;
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
			ex.printStackTrace();
			return new Object();

		}

	}

	public static void saveStrings(String[] strings, String file, String project) {
		try {
			if(new File("bin/projects/" + project + "/" + file).exists()){
				new File("bin/projects/" + project + "/" + file).delete();
				System.out.println("delete");
				System.out.println(new File("bin/projects/" + project + "/" + file).exists());
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(
					"bin/projects/" + project + "/" + file));
			for (int c = 0; c < strings.length; c++) {
				writer.write(strings[c].trim());
				writer.newLine();
			}
			writer.close();

		} catch (IOException ex) {
			System.out.println("Got io exeption:" + ex.getMessage());
		}
		try {
			if(new File("projects/" + project + "/" + file).exists()){
				new File("projects/" + project + "/" + file).delete();
				System.out.println("delete");
				System.out.println(new File("projects/" + project + "/" + file).exists());
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(
					"projects/" + project + "/" + file));
			for (int c = 0; c < strings.length; c++) {

				writer.write(strings[c]);
				writer.newLine();
			}
			writer.close();

		} catch (IOException ex) {
			System.out.println("Got io exeption:" + ex.getMessage());
		}
	}

	public static void saveStrings(ArrayList<String> enemies, String file,
			String project) {
		String[] strings = new String[enemies.size()];
		for (int c = 0; c < enemies.size(); c++) {
			strings[c] = enemies.get(c);
		}
		saveStrings(strings, file, project);
		// TODO Auto-generated method stub

	}

	public static String nameWithE(String name) {
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
}
