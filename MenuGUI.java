package main;

import java.awt.*;

import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

//Cleaning up some stuff.

public class MenuGUI extends JPanel {

	private static final long serialVersionUID = -4797779277772067971L;

	// For main menu
	private JButton start;
	private JButton lvl;
	private JButton lvlcrt;
	private JButton opt;
	private JButton multi;

	private JLabel label;

	// For multiplayer
	private JButton host;
	private JButton join;
	private JButton mBack;

	private GridBagLayout layout;
	private static JPanel panel;
	
	ArrayList<String> enemies;
	ArrayList<String> plats;
	ArrayList<String> spikes;
	ArrayList<String> checkpoint;
	String spawns;
	String file;
	int radio;

	private static JFrame frame = new JFrame("Mustachio!");

	private Game g;
	private MenuGUI owner;

	public MenuGUI() {
		
		enemies = new ArrayList<String>();
		plats = new ArrayList<String>();
		spikes = new ArrayList<String>();
		checkpoint = new ArrayList<String>();
		radio = -1;
		file = "";
		spawns = "";
		
		panel = new JPanel(new CardLayout());

		layout = new GridBagLayout();
		JPanel panel2 = new JPanel();
		panel2.setLayout(layout);
		panel2.setBackground(Color.WHITE);
		JPanel MenuPanel = new JPanel();
		MenuPanel.setLayout(layout);
		MenuPanel.setBackground(Color.WHITE);
		GridBagConstraints c = new GridBagConstraints();
		GridBagConstraints c2 = new GridBagConstraints();

		frame.setSize(450, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Menu image.
//		java.net.URL menuIconURL = this.getClass().getResource("/mustachioMenu.jpg");
		ImageIcon menuIcon = new ImageIcon("Images/mustachioMenu.jpg");
		label = new JLabel(menuIcon);
		JPanel panel3 = new JPanel();
		panel3.add(label);

		c2.gridx = 0;
		c2.gridy = 0;
		MenuPanel.add(panel3, c2);

		// New game button.
//		java.net.URL ngURL = this.getClass().getResource("/newgamebutton.jpg");
		ImageIcon ng = new ImageIcon("Images/newgamebutton.jpg");
		start = new JButton(ng);
		c.insets = new Insets(10, 10, 10, 10);
		c.gridx = 0;
		c.gridy = 1;
		start.setPreferredSize(new Dimension(100, 30));
		start.setSelected(true);
		panel2.add(start, c);
		
		//Load Level button.
//		java.net.URL llvlURL = this.getClass().getResource("/loadgamebutton.jpg");
		ImageIcon llvl = new ImageIcon("Images/loadgamebutton.jpg");
		lvl = new JButton(llvl);
		c.gridx = 1;
		c.gridy = 1;
		lvl.setPreferredSize(new Dimension(100, 30));
		c.insets = new Insets(10, 10, 10, 10);
		panel2.add(lvl, c);
		
		//Multiplayer button.
//		java.net.URL multURL = this.getClass().getResource("/multiplayerbutton.jpg");
		ImageIcon mult = new ImageIcon("Images/multiplayerbutton.jpg");
		multi = new JButton(mult);
		c.gridx = 2;
		c.gridy = 1;
		multi.setPreferredSize(new Dimension(100, 30));
		c.insets = new Insets(10, 10, 10, 10);
		panel2.add(multi, c);
		
		//Level creator button.
//		java.net.URL lvlcURL = this.getClass().getResource("/levelcreatorbutton.jpg");
		ImageIcon lvlc = new ImageIcon("Images/levelcreatorbutton.jpg");
		lvlcrt = new JButton(lvlc);
		c.gridx = 0;
		c.gridy = 2;
		lvlcrt.setPreferredSize(new Dimension(100, 30));
		c.gridwidth = 2;
		c.insets = new Insets(10, 10, 10, 10);
		panel2.add(lvlcrt, c);
		
		//Options button.
//		java.net.URL opsURL = this.getClass().getResource("/optionsbutton.jpg");
		ImageIcon ops = new ImageIcon("Images/optionsbutton.jpg");
		opt = new JButton(ops);
		c.gridx = 1;
		c.gridy = 2;
		opt.setPreferredSize(new Dimension(100, 30));
		c.gridwidth = 2;
		c.insets = new Insets(10, 10, 10, 10);
		panel2.add(opt, c);
		
		c2.gridx = 0;
		c2.gridy = 1;
		MenuPanel.add(panel2, c2);
		
		panel.add(MenuPanel, "Menu");
		
		JPanel MultiPanel = new JPanel(layout);
		MultiPanel.setBackground(Color.WHITE);
		
		//Host game button
//		java.net.URL hostURL = this.getClass().getResource("/hostbutton.jpg");
		ImageIcon hostgame = new ImageIcon("Images/hostbutton.jpg");
		host = new JButton(hostgame);
		host.setPreferredSize(new Dimension(100, 30));
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(10, 10, 10, 10);
		MultiPanel.add(host, c);
		
		//Join game button
//		java.net.URL joinURL = this.getClass().getResource("/joinbutton.jpg");
		ImageIcon joingame = new ImageIcon("Images/joinbutton.jpg");
		join = new JButton(joingame);
		join.setPreferredSize(new Dimension(100, 30));
		c.gridx = 2;
		c.gridy = 0;
		c.insets = new Insets(10, 10, 10, 10);
		MultiPanel.add(join, c);
		
		//Back to menu from multiplayer screen.
//		java.net.URL backURL = this.getClass().getResource("/backbutton.jpg");
		ImageIcon back = new ImageIcon("Images/backbutton.jpg");
		mBack = new JButton(back);
		mBack.setPreferredSize(new Dimension(100, 30));
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(10, 10, 10, 10);
		c.gridwidth = 4;
		MultiPanel.add(mBack, c);
		
		panel.add(MultiPanel, "Multiplayer");
		
		JPanel lvlPanel = new JPanel(layout);
		lvlPanel.setBackground(Color.white);
		GridBagConstraints c3 = new GridBagConstraints();
		
		JPanel txtPanel = new JPanel(layout);
		txtPanel.setBackground(Color.white);
		c3.gridx = 0;
		c3.gridy = 0;
		lvlPanel.add(txtPanel, c3);
		
		final JTextArea text = new JTextArea(10, 15);
		text.setPreferredSize(new Dimension(500, 500));
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(10, 10, 10, 10);
		txtPanel.add(text, c);
		JScrollPane scrollPane = new JScrollPane(text);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		txtPanel.add(scrollPane);
		
		
		final JLabel label = new JLabel("Format:");
		label.setPreferredSize(new Dimension(150, 30));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(10, 10, 10, 10);
		txtPanel.add(label, c);
		
		
		JPanel bttnPanel = new JPanel(layout);
		bttnPanel.setBackground(Color.white);
		c3.gridx = 1;
		c3.gridy = 0;
		lvlPanel.add(bttnPanel, c3);
		
		final JRadioButton enemiesrb = new JRadioButton("Enemies");
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(10, 10, 10, 10);
		bttnPanel.add(enemiesrb, c);
		
		final JRadioButton platsrb = new JRadioButton("Plats");
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(10, 10, 10, 10);
		bttnPanel.add(platsrb, c);
		
		final JRadioButton spikesrb = new JRadioButton("Spikes");
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(10, 10, 10, 10);
		bttnPanel.add(spikesrb, c);
		
		final JRadioButton checkpoints = new JRadioButton("Checkpoints");
		c.gridx = 0;
		c.gridy = 3;
		c.insets = new Insets(10, 10, 10, 10);
		bttnPanel.add(checkpoints, c);
		
		final JRadioButton spawn = new JRadioButton("Spawn Points");
		c.gridx = 0;
		c.gridy = 4;
		c.insets = new Insets(10, 10, 10, 10);
		bttnPanel.add(spawn, c);
		
		JButton save = new JButton("Save");
		save.setPreferredSize(new Dimension(100, 30));
		c.gridx = 4;
		c.gridy = 0;
		c.insets = new Insets(10, 10, 10, 10);
		bttnPanel.add(save, c);
		
		JButton load = new JButton("Load");
		load.setPreferredSize(new Dimension(100, 30));
		c.gridx = 4;
		c.gridy = 1;
		c.insets = new Insets(10, 10, 10, 10);
		bttnPanel.add(load, c);
		
		JButton button3 = new JButton("Back");
		button3.setPreferredSize(new Dimension(100, 30));
		c.gridx = 4;
		c.gridy = 2;
		c.insets = new Insets(10, 10, 10, 10);
		bttnPanel.add(button3, c);
		
		button3.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				CardLayout card = (CardLayout) panel.getLayout();
				card.show(panel, "Menu");
				
			}
			
		});
		
		panel.add(lvlPanel, "Level Creator");
		
		owner = this;
		
		start.setEnabled(true);
		multi.setEnabled(true);
		lvl.setEnabled(true);
		lvlcrt.setEnabled(true);
		opt.setEnabled(false);
		
		load.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser(".");
				FileFilter filter1 = new FileFilter() {
					public boolean accept(File f) {
						return f.isDirectory()
								|| f.getName().toLowerCase().endsWith(".mm");
					}

					public String getDescription() {
						return "Level Select";
					}

				};
				fc.setFileFilter(filter1);
				int returnVal = fc.showOpenDialog(fc);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					file = fc.getSelectedFile().getAbsolutePath();
				}
				Reader read = new Reader(file);
				enemies = read.getEnemies1();
				spikes = read.getSpikes1();
				plats = read.getPlats1();
				spawns = read.getHeroSpawn1();
				checkpoint = read.getCheckpoints1();
			}

		});
		
		save.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if(file.equals("")){
					JFileChooser fc = new JFileChooser(".");
					FileFilter filter1 = new FileFilter() {
						public boolean accept(File f) {
							return f.isDirectory()
									|| f.getName().toLowerCase().endsWith(".mm");
						}

						public String getDescription() {
							return "Mustachio Maps";
						}

					};
					fc.setFileFilter(filter1);
					int returnVal = fc.showSaveDialog(fc);

					if (returnVal == JFileChooser.APPROVE_OPTION) {
						file = fc.getSelectedFile().getAbsolutePath();
					} else {

					}
				}
				Writer write = new Writer(file);
				write.setEnemies(enemies);
				write.setPlats(plats);
				write.setSpikes(spikes);
				write.setCheckpoints(checkpoint);
				write.setSpawn(spawns);
				write.Write();
			}

		});

		platsrb.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				enemiesrb.setSelected(false);
				spikesrb.setSelected(false);
				checkpoints.setSelected(false);
				spawn.setSelected(false);
				label.setText("X.Y.Length");
				if(radio != 0 && radio != -1){
					String[] x = text.getText().split("[\\x00-\\x10]");
					if(radio == 1){
						spikes.clear();
						for (String p : x) {
							spikes.add(p);
						}
					}else if(radio == 2){
						enemies.clear();
						for (String p : x) {
							enemies.add(p);
						}
					}else if(radio == 3){
						checkpoint.clear();
						for (String p : x) {
							checkpoint.add(p);
						}
					}else if(radio ==4){
						spawns = x[0];
					}
				}
				text.setText("");
				for (String p : plats) {
					text.append(p + "\n");
				}

				radio = 0;
			}

		});
		
		checkpoints.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				spawn.setSelected(false);
				enemiesrb.setSelected(false);
				platsrb.setSelected(false);
				spikesrb.setSelected(false);
				label.setText("X.Y.Checkpoint_X");
				if(radio != 3 && radio != -1){
					String[] x = text.getText().split("[\\x00-\\x10]");
					if(radio == 1){
						spikes.clear();
						for (String p : x) {
							spikes.add(p);
						}
					}else if(radio == 2){
						enemies.clear();
						for (String p : x) {
							enemies.add(p);
						}
					}else if(radio == 0){
						plats.clear();
						for (String p : x) {
							plats.add(p);
						}
					}else if(radio == 4){
						if(x.length > 0)
							spawns = x[0];
					}
				}
				text.setText("");
				for (String p : checkpoint) {
					text.append(p + "\n");
				}
				
				
				radio = 3;
			}

		});
		
		spawn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				checkpoints.setSelected(false);
				enemiesrb.setSelected(false);
				platsrb.setSelected(false);
				spikesrb.setSelected(false);
				label.setText("X.Y");
				if(radio != 4 && radio != -1){
					String[] x = text.getText().split("[\\x00-\\x10]");
					if(radio == 1){
						spikes.clear();
						for (String p : x) {
							spikes.add(p);
						}
					}else if(radio == 2){
						enemies.clear();
						for (String p : x) {
							enemies.add(p);
						}
					}else if(radio == 0){
						plats.clear();
						for (String p : x) {
							plats.add(p);
						}
					}else if(radio ==3){
						checkpoint.clear();
						for (String p : x) {
							checkpoint.add(p);
						}
					}
				}
				text.setText(spawns + "\n");
				
				
				radio = 4;
			}

		});

		spikesrb.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				enemiesrb.setSelected(false);
				platsrb.setSelected(false);
				checkpoints.setSelected(false);
				spawn.setSelected(false);
				label.setText("X.Y.Width.Height");
				if(radio != 1 && radio != -1){
					String[] x = text.getText().split("[\\x00-\\x10]");
					if(radio == 0){
						plats.clear();
						for (String p : x) {
							plats.add(p);
						}
					}else if(radio == 2){
						enemies.clear();
						for (String p : x) {
							enemies.add(p);
						}
					}else if(radio == 3){
						checkpoint.clear();
						for (String p : x) {
							checkpoint.add(p);
						}
					}else if(radio ==4){
						spawns = x[0];
					}
				}
				text.setText("");
				for (String p : spikes) {
					text.append(p + "\n");
				}

				radio = 1;
			}

		});

		enemiesrb.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				spikesrb.setSelected(false);
				platsrb.setSelected(false);
				checkpoints.setSelected(false);
				spawn.setSelected(false);
				label.setText("X.Y");
				if(radio != 2 && radio != -1){
					String[] x = text.getText().split("[\\x00-\\x10]");
					if(radio == 1){
						spikes.clear();
						for (String p : x) {
							spikes.add(p);
						}
					}else if(radio == 0){
						plats.clear();
						for (String p : x) {
							plats.add(p);
						}
					}else if(radio == 3){
						checkpoint.clear();
						for (String p : x) {
							checkpoint.add(p);
						}
					}else if(radio ==4){
						spawns = x[0];
					}
				}
				text.setText("");
				for (String p : enemies) {
					text.append(p + "\n");
				}

				radio = 2;
			}

		});


		
		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				File f = null;
				JFileChooser fc = new JFileChooser(".");
				FileFilter filter1 = new FileFilter() {
					public boolean accept(File f) {
						return f.isDirectory()
								|| f.getName().toLowerCase().endsWith(".mm");
					}

					public String getDescription() {
						return "Level Select";
					}

				};
				fc.setFileFilter(filter1);
				int returnVal = fc.showOpenDialog(fc);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					f = fc.getSelectedFile();

					g = new Game(Game.STATE_SINGLE_PLAYER, f);
					g.setFocusable(true);
					frame.setVisible(false);
					frame = new JFrame("Mustachio!");
					frame.setSize(1050, 450);
					frame.setResizable(false);
					frame.getContentPane().add(g);
					g.setBorder(BorderFactory.createRaisedBevelBorder());
					g.requestFocus();
					g.setSize(1000, 400);
					frame.invalidate();
					frame.validate();
					// frame.setUndecorated(true);
					frame.setVisible(true);
					// frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

					Thread th = g.start();

					WaitGameFinishThread wait = new WaitGameFinishThread(th, 2,
							owner);
					Thread t = new Thread(wait);
					t.start();
				}
			}
		});

		multi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				CardLayout card = (CardLayout) panel.getLayout();
				card.show(panel, "Multiplayer");
			}	
		});
		
		host.addActionListener(new ActionListener(){

			
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Object[] choices = {"2 players", "3 players", "4 players"};
				int n = JOptionPane.showOptionDialog(owner, "Select number of players", "Multiplayer", 
						JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
				
				
				if(n != JOptionPane.CLOSED_OPTION){
					
					JDialog dialog = new JDialog();
					JLabel label = new JLabel();
					label.setPreferredSize(new Dimension(200, 150));
					dialog.setLocationRelativeTo(owner);
					dialog.setTitle("Please Wait...");
					dialog.add(label);
					dialog.pack();
					dialog.setVisible(true);

					File f = null;
					JFileChooser fc = new JFileChooser(".");
					FileFilter filter1 = new FileFilter() {
						public boolean accept(File f) {
							return f.isDirectory()
									|| f.getName().toLowerCase()
											.endsWith(".mm");
						}

						public String getDescription() {
							return "Level Select";
						}

					};
					fc.setFileFilter(filter1);
					int returnVal = fc.showOpenDialog(fc);

					if (returnVal == JFileChooser.APPROVE_OPTION) {
						f = fc.getSelectedFile();

						g = new Game(Game.STATE_MULTIPLAYER, f, true, null,
								n + 2);
						g.setFocusable(true);
						frame.setVisible(false);
						frame = new JFrame("Mustachio!");
						frame.setSize(1050, 450);
						frame.getContentPane().removeAll();
						frame.getContentPane().add(g);
						// g.setBorder(BorderFactory.createRaisedSoftBevelBorder());
						g.requestFocus();
						g.setSize(1000, 400);
						frame.invalidate();
						frame.validate();
						frame.setVisible(true);
						Thread th = g.start();

						WaitGameFinishThread wait = new WaitGameFinishThread(
								th, 2, owner);
						Thread t = new Thread(wait);
						t.start();
					}
					dialog.setVisible(false);
				}
			}

		});

		join.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String ip = (String) JOptionPane.showInputDialog(
						"Enter ip address", null);

				if ((ip != null) && (ip.length() > 0)) {
					JDialog dialog = new JDialog();
					JLabel label = new JLabel();
					label.setPreferredSize(new Dimension(200, 150));
					dialog.setLocationRelativeTo(owner);
					dialog.setTitle("Please Wait...");
					dialog.add(label);
					dialog.pack();
					dialog.setVisible(true);
					
					g = new Game(Game.STATE_MULTIPLAYER, null, false, ip, 1);
					g.setFocusable(true);
					frame.setVisible(false);
					frame = new JFrame("Mustachio!");
					frame.setSize(1050, 450);
					frame.getContentPane().removeAll();
					frame.getContentPane().add(g);
					//g.setBorder(BorderFactory.createRaisedSoftBevelBorder());
					g.requestFocus();
					g.setSize(1000, 400);
					frame.invalidate();
					frame.validate();
					frame.setVisible(true);
					Thread th = g.start();
					
					WaitGameFinishThread wait = new WaitGameFinishThread(th, 2, owner);
					Thread t = new Thread(wait);
					t.start();
					
					dialog.setVisible(false);
				}
			}
			
		});
		
		lvl.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				File f = null;
				JFileChooser fc = new JFileChooser(".");
				FileFilter filter1 = new FileFilter() {
					public boolean accept(File f) {
						return f.isDirectory()
								|| f.getName().toLowerCase().endsWith(".msav");
					}

					public String getDescription() {
						return "Load Game";
					}

				};
				fc.setFileFilter(filter1);
				int returnVal = fc.showOpenDialog(fc);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					f = fc.getSelectedFile();

					g = new Game(f);
					g.setFocusable(true);
					frame.setVisible(false);
					frame = new JFrame("Mustachio!");
					frame.setSize(1050, 450);
					frame.setResizable(false);
					frame.getContentPane().add(g);
					g.setBorder(BorderFactory.createRaisedBevelBorder());
					g.requestFocus();
					g.setSize(1000, 400);
					frame.invalidate();
					frame.validate();
					// frame.setUndecorated(true);
					frame.setVisible(true);
					// frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

					Thread th = g.start();

					WaitGameFinishThread wait = new WaitGameFinishThread(th, 2,
							owner);
					Thread t = new Thread(wait);
					t.start();
				}

			}

		});
		
		mBack.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				CardLayout card = (CardLayout) panel.getLayout();
				card.show(panel, "Menu");
			}
			
		});
		
		lvlcrt.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				CardLayout card = (CardLayout) panel.getLayout();
				card.show(panel, "Level Creator");
				
			}
			
		});
		
	}
	
	
	public static void createGUI(){
		MenuGUI contentPane = new MenuGUI();
		contentPane.setOpaque(true);
		frame.setContentPane(contentPane);
		
		frame.getContentPane().add(panel);
		frame.setVisible(false);

		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	
	
	public static void run(){
		createGUI();
	}
	
	public static void main(String[] args){
		run();
	}
	
	public void performAction(int action) {
		switch (action) {
		case 2:
			frame.setVisible(false);
			createGUI();
			break;
		}
	}
	
	private class WaitGameFinishThread implements Runnable {

		Thread waitFor;
		int action;
		MenuGUI owner;
		
		public WaitGameFinishThread(Thread t, int action, MenuGUI o) {
			waitFor = t;
			this.action = action;
			owner = o;
		}
		
		@Override
		public void run() {
			try {
				waitFor.join();
			} catch (InterruptedException e) {
			}
			owner.performAction(action);
		}
	}
}
