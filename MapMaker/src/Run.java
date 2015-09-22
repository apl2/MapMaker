import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class Run extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		new Run().run();
	}

	JButton newPack;
	JButton loadPack;
	JButton clearPacks;
	JButton newP;
	JButton load;
	JButton delete;
	JPanel bPanel;
	Run run = this;
	// File file;
	String folder;
	JPanel drawPan;
	DefaultListModel<String> lm = new DefaultListModel<String>();
	JList<String> list = new JList<String>(lm);
	JTextField jf;

	public void listModReFresh() {
		for (int c = 0; c < lm.size(); c++) {
			if (lm.get(c).equals("")) {
				lm.remove(c);
				c--;
			}
		}
	}

	public static String removeExtension(String s) {

		String separator = System.getProperty("file.separator");
		String filename;

		// Remove the path upto the filename.
		int lastSeparatorIndex = s.lastIndexOf(separator);
		if (lastSeparatorIndex == -1) {
			filename = s;
		} else {
			filename = s.substring(lastSeparatorIndex + 1);
		}

		// Remove the extension.
		// int extensionIndex = filename.lastIndexOf(".");
		// if (extensionIndex == -1)
		// return filename;

		// return filename.substring(0, extensionIndex);
		return filename;
	}

	public void load(String s) {
		setVisible(false);
		new MapMaker(s).run();
	}

	public void run() {
		if (!new File("projects").exists()) {
			new File("projects").mkdir();
		}
		if (!new File("bin/projects").exists()) {
			new File("bin/projects").mkdir();
		}
		this.addKeyListener(new MyKeyListener());
		this.setTitle("MapMaker");
		// Load stuff
		jf = new JTextField();
		jf.setFont(new Font("sans", Font.PLAIN, 40));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		load = new JButton("Load");
		load.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (list.getSelectedIndex() != -1) {

					load(list.getSelectedValue());
				}
			}

		});
		delete = new JButton("Delete");
		delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (list.getSelectedIndex() != -1
						&& 0 == JOptionPane.showConfirmDialog(
								run,
								"Are you sure you want to delete "
										+ list.getSelectedValue() + "?")) {
					try {
						MapMaker.deleteDir(new File("bin/projects/"
								+ list.getSelectedValue()).toPath());

						MapMaker.updateAllNor();
						JOptionPane
								.showMessageDialog(run,
										"Hover the mouse over the buttons below, they will become visible again.");
						lm.removeAllElements();
						run.revalidate();
						loadPack.doClick();
					} catch (Exception ex) {

					}
				}
			}
		});
		newP = new JButton("new");
		newP.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (!jf.getText().equals("")
						&& !jf.getText().equals(".DS_Store")) {

					load(jf.getText());
				} else {
					jf.setText("");
				}
			}

		});
		newPack = new JButton("New Pack");
		newPack.addActionListener(new newPC());
		loadPack = new JButton("Load Pack");
		loadPack.addActionListener(new loadPC());

		drawPan = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				g2.clearRect(0, 0, this.getWidth(), this.getHeight());
				// Check bin for view
				g2.drawImage(
						new ImageIcon(getClass().getResource("images/view.png"))
								.getImage(), 0, 0, this.getWidth(), this
								.getHeight(), this);
				g2.setColor(Color.BLACK);
				int sizeForm = this.getWidth() / 25;
				g2.setFont(new Font("sans", Font.PLAIN, sizeForm));
				g2.drawString("Map Maker",
						this.getWidth() / 2 - this.getWidth() / 10,
						this.getHeight() / 2);
			}
		};
		drawPan.setFocusable(true);
		clearPacks = new JButton("Clear Packs");
		clearPacks.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if (JOptionPane.showConfirmDialog(run,
						"Are you sure you want to delete ALL your packs?") == 0) {
					try {
						MapMaker.deleteDir(FileSystems.getDefault().getPath(
								"bin/projects"));
						MapMaker.deleteDir(FileSystems.getDefault().getPath(
								"projects"));
						File f = new File("bin/projects");
						f.mkdir();
						File f1 = new File("projects");
						f1.mkdir();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				run.requestFocus();
			}

		});
		// set frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setUndecorated(true);

		// start
		bPanel = new JPanel();
		bPanel.setBackground(Color.BLACK);
		bPanel.add(newPack);
		bPanel.add(loadPack);
		bPanel.add(clearPacks);
		add(bPanel, BorderLayout.SOUTH);
		add(drawPan, BorderLayout.CENTER);

		setFocusable(true);
		setVisible(true);
	}

	public class newPC implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			run.remove(bPanel);
			// run.remove(drawPan);
			run.revalidate();
			run.add(jf, BorderLayout.NORTH);
			run.add(newP, BorderLayout.SOUTH);
			run.revalidate();
		}

	}

	public class loadPC implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			// JFileChooser jc=new JFileChooser();
			// jc.showOpenDialog(run);
			// file=jc.getSelectedFile();
			File dir = null;
			try {
				dir = new File(".//.//bin/projects").getCanonicalFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(run, e1.getMessage());
				e1.printStackTrace();

			}
			File[] listen = dir.listFiles();
			for (int c = 0; c < listen.length; c++) {

				if (!removeExtension(listen[c].getPath()).equals(".DS_Store")) {
					lm.addElement(removeExtension(listen[c].getPath()));
				}
			}

			listModReFresh();
			run.remove(bPanel);
			// run.remove(drawPan);
			run.revalidate();
			if (lm.getSize() > 0) {
				run.add(list, BorderLayout.NORTH);
			} else {
				run.add(new JLabel("No Files To Load"), BorderLayout.NORTH);
			}
			JPanel loadPan = new JPanel();
			loadPan.add(load);
			loadPan.add(delete);

			run.add(loadPan, BorderLayout.SOUTH);
			run.revalidate();
			loadPan.validate();
			load.validate();
			delete.validate();
		}

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
}
