package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

public class Game extends JPanel implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1031086611605790475L;

	public static final int STATE_SINGLE_PLAYER = 1;
	public static final int STATE_MULTIPLAYER = 2;

	private int state = -1;

	private boolean host;
	private boolean initted = false;

	private Hero heroes[];
	private int points[];
	private Updater upd;
	private UniqueArrayList<Update> updates;

	private ArrayList<Bullet> bullets;

	private static final int stepTime = 10;
	private static final int JUMP_TIME = 40;
	private static final int PLAYER_SPEED = 4;
	private static final int JUMP_SPEED = 4;
	private static final int SCROLLING_BOUNDARY = 225;
	private static final int ENEMY_KILL_POINTS = 100;

	private int bWall;

	private static final int GRAVITY = 4;

	private SockWrap[] sockets;

	private ArrayList<Checkpoint> checkpoints;
	private int lastCheckpoint;

	protected GameState gamestate;

	protected boolean exit;

	private int numP;
	private int clientID;

	private boolean hasScrolled;

	private Level level;
	private File level_file;

	private boolean pause;

	public Game(int type, File level) {
		this(type, level, false, null, 1);
	}

	public Game(int type, File level, boolean hosting, String ip_address,
			int gNumP) {
		state = type;
		// ip = ip_address;
		host = hosting;
		this.level_file = level;
		upd = new Updater();
		updates = new UniqueArrayList<Update>();
		numP = gNumP;
		if (state == STATE_MULTIPLAYER) {
			initNetwork(ip_address);
		}
		init();
		initted = true;
	}

	public Game(File f) {
		this(Game.STATE_SINGLE_PLAYER, null, false, null, 1);
		loadGame(f);
	}

	public Thread start() {
		Thread run = new Thread(this);
		run.start();

		return run;
	}

	private void initNetwork(String ip_address) {
		// if host
		if (host) {
			System.out.println("Host");
			sockets = new SockWrap[numP - 1];
			SockWrap h = new SockWrap();
			try {
				h.initHost();
			} catch (IOException e) {
				e.printStackTrace();
			}
			for (int i = 0; i < sockets.length; i++) {
				try {
					sockets[i] = h.hostConnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			clientID = 0;
		} else {
			System.out.println("Join");
			sockets = new SockWrap[1];
			sockets[0] = new SockWrap();
			try {
				sockets[0].initClient(ip_address);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			while (true) {
				try {
					level = (Level) sockets[0].getIn().readObject();
					break;
				} catch (IOException e) {
					continue;
				} catch (ClassNotFoundException e) {
					continue;
				}
			}

			// System.out.println("Socket made");
			while (true) {
				try {
					numP = (Integer) sockets[0].getIn().readObject();
					break;
				} catch (IOException e) {
					continue;
				} catch (ClassNotFoundException e) {
					continue;
				}
			}
			while (true) {
				try {
					clientID = (Integer) sockets[0].getIn().readObject();
					break;
				} catch (IOException e) {
					continue;
				} catch (ClassNotFoundException e) {
					continue;
				}
			}
			
			loadLevel(level);
		}
	}

	private void loadGame(File f) {
		GameRecord record = new GameRecord();
		record.readerRecord(f);
	}

	private void saveGame() {
		pause = true;
		File f = null;
		JFileChooser fc = new JFileChooser(".");
		FileFilter filter1 = new FileFilter() {
			public boolean accept(File f) {
				return f.isDirectory()
						|| f.getName().toLowerCase().endsWith(".msav");
			}

			public String getDescription() {
				return "Mustachio Saves";
			}

		};
		fc.setFileFilter(filter1);
		int returnVal = fc.showOpenDialog(fc);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			f = fc.getSelectedFile();

			GameRecord record = new GameRecord();
			record.writeRecord(f);
		}

		pause = false;
	}

	public void init() {
		setSize(500, 400);
		exit = false;
		setFocusable(true);
		this.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				switch (keyCode) {

				case KeyEvent.VK_UP:
					if (!inAir(heroes[clientID])) {
						if (heroes[clientID].getJumpcount() == 0)
							heroes[clientID].setJumpcount(1);
					}
					break;
				case KeyEvent.VK_DOWN:
					heroes[clientID].setDirection(Hero.DOWN);
					break;
				case KeyEvent.VK_LEFT:
					heroes[clientID].setDirection(Hero.LEFT);
					break;
				case KeyEvent.VK_RIGHT:
					heroes[clientID].setDirection(Hero.RIGHT);
					break;
				case KeyEvent.VK_SPACE:
					break;
				case KeyEvent.VK_K:
					if (state == Game.STATE_SINGLE_PLAYER) {
						saveGame();
					}
					break;
				}

			}

			@Override
			public void keyReleased(KeyEvent e) {
				int keyCode = e.getKeyCode();
				switch (keyCode) {
				case KeyEvent.VK_UP:
					heroes[clientID].setJumpcount(0);
					break;
				case KeyEvent.VK_DOWN:
					break;
				case KeyEvent.VK_LEFT:
					if (heroes[clientID].getDirection() == Hero.LEFT) {
						heroes[clientID].setDirection(Hero.STILL);
					}
					break;
				case KeyEvent.VK_RIGHT:
					if (heroes[clientID].getDirection() == Hero.RIGHT) {
						heroes[clientID].setDirection(Hero.STILL);
					}
					break;
				case KeyEvent.VK_SPACE:

					heroes[clientID].setHasShot(true);
					break;

				case KeyEvent.VK_Q:
					exit = true;
					break;
				}

			}

			@Override
			public void keyTyped(KeyEvent arg0) {
			}

		});

		requestFocusInWindow();
		gamestate = new GameState();
		gamestate.index = 0;

		bWall = (int) (getBounds().getHeight());

		// set bullets
		bullets = new ArrayList<Bullet>();
		
		points = new int[numP];
		for (int i = 0; i < points.length; i++) {
			points[i] = 0;
		}

		initGraphics();

		lastCheckpoint = -1;

		if (this.level_file != null) {
			loadLevel();
		}

		pause = false;
		//setBackground(new Color(244, 178, 99));
	}

	private void loadLevel() {
		level = new Level();
		
		Reader read = new Reader(level_file.getAbsolutePath());
		ArrayList<Platform> plats = read.getPlats();
		ArrayList<Spikes> spikes = read.getSpikes();
		checkpoints = read.getCheckpoints();
		level.setSpawn(read.getHeroSpawn());
		level.setEnemies(read.getEnemies());
		for (Platform p : plats) {
			level.addEntity(p);
		}
		for (Spikes s : spikes) {
			level.addEntity(s);
		}
		initHero();
	}
	
	private void loadLevel(Level l) {
		this.level = l;
		initHero();
	}
	
	private void initHero() {
		heroes = new Hero[numP];
		MustachioGraphics.mustaches = new BufferedImage[numP];
		for (int i = 0; i < numP; i++) {
			if (level.getSpawn() == null) level.setSpawn(new Coordinate2D(100, 100));
			heroes[i] = new Hero(level.getSpawn().getX(), level.getSpawn().getY());
			heroes[i].index = i;
		
			File file = new File("Images/mustachio" + (i + 1) + ".gif");
			try {

				BufferedImage img = ImageIO.read(file);
				MustachioGraphics.mustaches[i] = img;

			} catch (IOException e) {
				e.printStackTrace();
			}

			heroes[i].index = i;
		}
	}

	private void initGraphics() {
		File image = new File("Images/spike.gif");
		File image2 = new File("Images/spikedown.gif");
		File image3 = new File("Images/spikeleft.gif");
		File image4 = new File("Images/spikeright.gif");

		File image5 = new File("Images/platformlong.gif");

		File image6 = new File("Images/angrysquare.gif");
		
		File image7 = new File("Images/MustachioBackground.jpg");

		try {
			MustachioGraphics.spikeUp = ImageIO.read(image);
			MustachioGraphics.spikeDown = ImageIO.read(image2);
			MustachioGraphics.spikeLeft = ImageIO.read(image3);
			MustachioGraphics.spikeRight = ImageIO.read(image4);
			MustachioGraphics.platform = ImageIO.read(image5);
			MustachioGraphics.baseEnemy = ImageIO.read(image6);
			MustachioGraphics.background = ImageIO.read(image7);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (initted == false) {
		}
		if (state == STATE_MULTIPLAYER) {
			if (host) {
				for (int i = 0; i < sockets.length; i++) {
					try {
						sockets[i].getOut().writeObject(level);
						sockets[i].getOut().writeObject(numP);
						sockets[i].getOut().writeObject(i + 1);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		while (exit == false) {
			try {
				Thread.sleep(stepTime);
			} catch (InterruptedException e) {
			}
			if (!pause)
				step();
		}
	}

	public Hero[] getHeroes() {
		return heroes;
	}

	private void step() {
		getLocalUpdates();
		if (state == STATE_MULTIPLAYER) {
			if (host) {
				getRemoteUpdates(); // blocking call to get all updates from
									// clients
				compute();
				updateClients(); // sends updates down to clients relinquishing
									// them from waiting.
			} else {
				retrieveUpdates(); // blocking call to retrieve applied updates
									// from host
				apply();
			}
		} else {
			checkCheckpoint();
			apply();
		}
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		g.drawImage(MustachioGraphics.background, 0, 0, null);
		
		drawScorebox(g);

		g.drawLine(0, 400, 10000, 400);

		for (Hero h : heroes) {
			if (h != null)
				h.drawHero(g, gamestate.index);
		}

		// draw bullets
		for (int i = 0; i < bullets.size(); i++) {
			Bullet b = bullets.get(i);
			b.drawBullet(g);
		}

		for (int i = 0; i < level.getEnemies().size(); i++) {
			Enemy e = level.getEnemies().get(i);
			e.setOffset(gamestate.index);
			e.drawEnemy(g);
		}

		for (Entity e : level.getEntities().getSection(heroes[clientID].getX())) {
			e.setOffset(gamestate.index);
			e.draw(g);
		}
	}

	private void drawScorebox(Graphics g) {
		int section = 45;
		int vspace = 25;
		int wspace = 10;

		int width = 200;
		int height = numP * section;

		g.drawLine(0, 0, 0, height);
		g.drawLine(0, 0, width, 0);
		g.drawLine(width, 0, width, height);
		g.drawLine(0, height, width, height);

		for (int i = 0; i < numP; i++) {
			g.drawString("Player " + (i + 1) + ": " + points[i], wspace,
					(section * i) + vspace);
		}
	}

	private void checkCheckpoint() {
		int last = -1;
		Hero h = heroes[clientID];
		for (int i = 0; i < checkpoints.size(); i++) {
			Checkpoint c = checkpoints.get(i);
			if (h.getX() >= c.getThreshold()) {
				if (last == -1)
					last = i;
				else if (c.getThreshold() > checkpoints.get(last)
						.getThreshold())
					last = i;
			}
		}
		lastCheckpoint = last;
	}

	private UniqueArrayList<Update> getLocalUpdates() {
		Hero h = heroes[clientID];

		Update u;
		if (h.getJumpcount() > 0) {
			u = new Update();
			u.doer = h;
			u.action = "JUMP";
			u.reciever = null;
			updates.add(u);
		} else {
			if (inAir(h)) {
				u = new Update();
				u.doer = h;
				u.action = "FALL";
				u.reciever = null;
				updates.add(u);
			}
		}

		if (h.getDirection() != Hero.STILL) {
			switch (h.getDirection()) {
			case Hero.LEFT:
				u = new Update();
				u.doer = h;
				u.action = "MOVE LEFT";
				u.reciever = null;
				updates.add(u);
				break;
			case Hero.RIGHT:
				u = new Update();
				u.doer = h;
				u.action = "MOVE RIGHT";
				u.reciever = null;
				updates.add(u);
				break;
			case Hero.DOWN:
				u = new Update();
				u.doer = h;
				u.action = "MOVE DOWN";
				u.reciever = null;
				updates.add(u);
				break;
			}
		}

		if (h.hasShot()) {
			u = new Update();
			u.doer = h;
			u.action = "SHOOT";
			u.reciever = null;
			updates.add(u);
			h.setHasShot(false);
		}

		checkBullets();
		checkEnemies();
		return updates;
	}

	// blocking call to get all updates from clients
	private UniqueArrayList<Update> getRemoteUpdates() {
		for (int i = 0; i < sockets.length; i++) {
			SockWrap s = sockets[i];
			Object obj = null;
			while (!(obj instanceof ArrayList)) {
				try {
					obj = s.getIn().readObject();
				} catch (ClassNotFoundException e) {
					continue;
				} catch (IOException e) {
					continue;
				}
			}
			@SuppressWarnings("rawtypes")
			UniqueArrayList list = (UniqueArrayList) obj;
			if (list.size() > 0) {
				Object tmp = list.get(0);
				if (tmp instanceof Update) {
					@SuppressWarnings("unchecked")
					UniqueArrayList<Update> u = (UniqueArrayList<Update>) obj;
					updates.addAll(u);
				}
			}
		}
		return updates;
	}

	private UniqueArrayList<Update> compute() {

		boolean[] dead = new boolean[numP];
		for (int i = 0; i < dead.length; i++) {
			dead[i] = false;
		}

		for (int i = 0; i < updates.size(); i++) {
			Update u = updates.get(i);
			Hero h = upd.doesHeroDie(u);
			if (h != null) {
				if (h.index >= 0 && h.index < numP) {
					dead[h.index] = true;
				}
			}
		}

		for (int i = 0; i < updates.size(); i++) {
			Update u = updates.remove(i);
			if (u.doer instanceof Hero) {
				Hero h = (Hero) u.doer;
				if (h.index >= 0 && h.index < numP) {
					if (dead[h.index]) {
						if (u.action != "DIE")
							continue;
					}
				}
			}
			Update tmp = upd.executeUpdate(u);
			if (tmp != null) { // u CANNOT happen
				updates.add(i, tmp);
			}
		}
		hasScrolled = false;
		return updates;
	}

	// sends final updates down to clients
	private void updateClients() {
		// go through all sockets, write updates to each and yeah
		for (int i = 0; i < sockets.length; i++) {
			SockWrap s = sockets[i];
			try {
				s.getOut().reset();
				s.getOut().writeObject(updates);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		updates.clear();
	}

	// blocking call to send raw updates to and retrieve applied updates from
	// host
	private UniqueArrayList<Update> retrieveUpdates() {

		SockWrap s = sockets[0];

		try {
			s.getOut().reset();
			s.getOut().writeObject(updates);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Object obj = null;
		while (!(obj instanceof UniqueArrayList)) {
			try {
				obj = s.getIn().readObject();
			} catch (ClassNotFoundException e) {
				continue;
			} catch (IOException e) {
				continue;
			}
		}

		@SuppressWarnings("rawtypes")
		UniqueArrayList list = (UniqueArrayList) obj;
		if (list.size() > 0) {
			Object tmp = list.get(0);
			if (tmp instanceof Update) {
				// System.out.println("join got stuff");
				@SuppressWarnings("unchecked")
				UniqueArrayList<Update> u = (UniqueArrayList<Update>) obj;
				updates = u;
			}
		}
		return updates;
	}

	private void apply() {

		for (int i = 0; i < updates.size(); i++) {
			Update u = updates.get(i);
			upd.executeUpdate(u);
		}
		updates.clear();
		hasScrolled = false;
	}

	private boolean inAir(Hero h) {

		for (Entity e : level.getEntities().getSection(h.getX())) {
			if (e instanceof Platform) {
				Platform p = (Platform) e;
				if (h.getX() >= p.getX()
						&& h.getX() <= (p.getX() + (p.getLength() * p.getXinc()))) {
					if (((h.getY() + h.getHeight()) - GRAVITY) <= p.getYstart()
							&& (h.getY() + h.getHeight()) >= p.getYstart()) {
						h.setY(p.getYstart() - h.getHeight());
						return false;
					}
				}

				if ((h.getX() + h.getWidth()) >= p.getX()
						&& (h.getX() + h.getWidth()) <= (p.getX() + (p
								.getLength() * p.getXinc()))) {
					if (((h.getY() + h.getHeight()) - GRAVITY) <= p.getYstart()
							&& (h.getY() + h.getHeight()) >= p.getYstart()) {
						h.setY(p.getYstart() - h.getHeight());
						return false;
					}
				}
			}
		}

		if (h.getY() + h.getHeight() >= bWall) {
			h.setY(bWall - h.getHeight());
			return false;
		}

		return true;
	}

	private void checkBullets() {
		for (int i = 0; i < bullets.size(); i++) {
			Bullet b = bullets.get(i);

			if (b.getX() + gamestate.index >= getWidth()) {
				bullets.remove(i);
				continue;
			}

			for (int j = 0; j < level.getEnemies().size(); j++) {
				Enemy e = level.getEnemies().get(j);
				if ((e.getX() <= b.getX() + b.getWidth())
						&& (e.getX() + b.getWidth() >= b.getX())) {
					if ((e.getY() <= b.getY() + b.getHeight())
							&& (e.getY() + e.getHeight() >= b.getY())) {
						level.getEnemies().remove(j);
						bullets.remove(i);
						points[b.getHeroId().index] += ENEMY_KILL_POINTS;
						break;
					}
				}
			}
			b.move();
		}
	}

	private void checkEnemies() {
		for (int i = 0; i < level.getEnemies().size(); i++) {
			Enemy e = level.getEnemies().get(i);

			for (int j = 0; j < heroes.length; j++) {
				Hero h = heroes[j];
				if ((e.getX() <= h.getX() + h.getWidth())
						&& (e.getX() + h.getWidth() >= h.getX())) {
					if ((e.getY() <= h.getY() + h.getHeight())
							&& (e.getY() + e.getHeight() >= h.getY())) {
						level.getEnemies().remove(i);
						die(h, e);
						break;
					}
				}
			}
			e.move();
		}
	}

	private Hero checkBounds(Hero h) {

		// check obstacles
		for (Entity e : level.getEntities().getSection(h.getX())) {
			if (e instanceof Spikes) {
				Spikes p = (Spikes) e;
				if ((h.getX() <= p.getX() + p.getWidth())
						&& (h.getX() + h.getWidth() >= p.getX())) {
					if ((h.getY() <= p.getY() + p.getHeight())
							&& (h.getY() + h.getHeight() >= p.getY())) {
						die(h, null);
					}
				}
			} else if (e instanceof Platform) {
				Platform p = (Platform) e;
				if (h.getX() >= p.getX()
						&& h.getX() <= (p.getX() + (p.getLength() * p.getXinc()))) {
					if (((h.getY() + h.getHeight()) - GRAVITY) <= p.getYstart()
							&& (h.getY() + h.getHeight()) >= p.getYstart()) {
						h.setY(p.getYstart() - h.getHeight());
					}
				}

				if ((h.getX() + h.getWidth()) >= p.getX()
						&& (h.getX() + h.getWidth()) <= (p.getX() + (p
								.getLength() * p.getXinc()))) {
					if (((h.getY() + h.getHeight()) - GRAVITY) <= p.getYstart()
							&& (h.getY() + h.getHeight()) >= p.getYstart()) {
						h.setY(p.getYstart() - h.getHeight());
					}
				}
			}
		}

		if (h.getY() + h.getHeight() >= bWall) {
			h.setY(bWall - h.getHeight());
		}

		// check scroll

		if (gamestate.index == 0) {
			if (h.getX() <= 0) {
				h.setX(0);
			}
		} else {
			boolean scroll = true;
			// scrolling left
			if (h.getX() + gamestate.index <= SCROLLING_BOUNDARY) {
				for (int i = 0; i < heroes.length; i++) {
					if (i == h.index)
						continue;
					if (heroes[i].getX() + gamestate.index >= this.getWidth()
							- SCROLLING_BOUNDARY) {
						scroll = false;
						h.move(Hero.RIGHT, PLAYER_SPEED);
						break;
					}
				}
				if (scroll) {
					if (hasScrolled == false) {
						hasScrolled = true;
						gamestate.index += PLAYER_SPEED;
					}
					// h.setX(SCROLLING_BOUNDARY);
				}
			}
		}

		// scrolling right
		if (h.getX() + gamestate.index >= this.getWidth() - SCROLLING_BOUNDARY) {
			boolean scroll = true;
			for (int i = 0; i < heroes.length; i++) {
				if (i == h.index)
					continue;
				if (heroes[i].getX() + gamestate.index <= SCROLLING_BOUNDARY) {
					scroll = false;
					h.move(Hero.LEFT, PLAYER_SPEED);
					break;
				}
			}
			if (scroll) {
				if (hasScrolled == false) {
					hasScrolled = true;
					gamestate.index -= PLAYER_SPEED;
				}

				// h.setX(this.getWidth() - SCROLLING_BOUNDARY);
			}
		}
		return heroes[h.index];
	}

	private void shoot(Hero h) {
		Bullet b = new Bullet(h, h.getX() + gamestate.index + h.getWidth(),
				h.getY() + (h.getWidth() / 2));
		bullets.add(b);
	}

	private void die(Object murderee, Object murderer) {

		Update u = new Update();
		u.doer = murderee;
		u.action = "DIE";
		u.reciever = murderer;

		updates.add(u);
	}

	private void startAtCheckpoint(int chkpnt) {
		if (chkpnt == -1) {
			heroes[clientID].setX(level.getSpawn().getX());
			heroes[clientID].setY(level.getSpawn().getY());
		} else {
			Checkpoint c = checkpoints.get(chkpnt);
			heroes[clientID].setX(c.getX());
			heroes[clientID].setY(c.getY());

			gamestate.index = -(c.getX() - SCROLLING_BOUNDARY);
		}
	}

	public int getLastCheckpoint() {
		return lastCheckpoint;
	}

	private class Updater {

		public Update executeUpdate(Update u) {
			if (u.doer instanceof Hero) {
				Hero h = (Hero) u.doer;
				if (h.index < 0 || h.index > numP) {
					return null;
				}
				h = heroes[h.index];

				if (u.action.toUpperCase().equals("MOVE LEFT")) {
					h.move(Hero.LEFT, PLAYER_SPEED);
					heroes[h.index] = checkBounds(h);
					return u;
				} else if (u.action.toUpperCase().equals("MOVE RIGHT")) {
					h.move(Hero.RIGHT, PLAYER_SPEED);
					heroes[h.index] = checkBounds(h);
					return u;
				}	else if (u.action.toUpperCase().equals("FALL")) {
					h.move(Hero.DOWN, GRAVITY);
					heroes[h.index] = checkBounds(h);
					return u;

				} else if (u.action.toUpperCase().equals("JUMP")) {
					h.move(Hero.UP, JUMP_SPEED);
					// h.setJumpcount(h.getJumpcount() - 1);
					h.setJumpcount(h.getJumpcount() + 1);
					if (h.getJumpcount() > JUMP_TIME)
						h.setJumpcount(0);
					heroes[h.index] = checkBounds(h);
					return u;
				} else if (u.action.toUpperCase().equals("SHOOT")) {
					shoot(h);
					heroes[h.index] = checkBounds(h);
					return u;
				} else if (u.action.toUpperCase().equals("DIE")) {
					if (state == STATE_SINGLE_PLAYER) {
						h.setX(0);
						gamestate.index = 0;
					} else if (state == STATE_MULTIPLAYER) {
						h.setX(((gamestate.index == 0) ? 0 : SCROLLING_BOUNDARY
								- gamestate.index));
					}

					h.setY(bWall - h.getHeight());
					h.setJumpcount(0);

					return u;
				}
			}
			return null;
		}

		public Hero doesHeroDie(Update u) {
			if (u.doer instanceof Hero) {
				Hero h = (Hero) u.doer;
				if (u.action.equals("DIE")) {
					return h;
				}
			}
			return null;
		}
	}

	private class GameRecord {
		// function that recording the game
		public void writeRecord(File f) {
			FileWriter fw = null;
			BufferedWriter bw = null;

			// if we hit the check point
			try {
				if (!f.exists()) {
					f.createNewFile();
				}

				fw = new FileWriter(f);
				bw = new BufferedWriter(fw);

				String record = "";

				int checkpoint = getLastCheckpoint();
				record = "" + checkpoint;

				bw.write(record + "\r\n");
				bw.write(level_file.getPath() + "\r\n");
				bw.flush();
				bw.close();
				fw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// function that loading the game
		public void readerRecord(File f) {
			FileReader fr = null;
			BufferedReader br = null;
			try {
				fr = new FileReader(f);
				br = new BufferedReader(fr);

				String n = "";

				// get the place of the player
				n = br.readLine();
				int chkpnt = Integer.parseInt(n);

				n = br.readLine();
				level_file = new File(n);
				loadLevel();

				startAtCheckpoint(chkpnt);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					br.close();
					fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}