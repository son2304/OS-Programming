// 2021 Son Moo Kyung

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class MainFrame extends JFrame{

	static File path = new File(".");				// ����� ���� �ε��� ���� ������Ʈ ���

	boolean startPageV = true;						// ȭ�� ��ȯ�� ���� boolean ����
	boolean explainPageV = false;
	boolean playPageV = false;
	boolean infoPageV = false;

	boolean overPageV = false;

	boolean KeyRight = false;						// Ű���� �� �� ����Ű�� �������� �Ǵ��ϴ� boolean ����
	boolean KeyLeft = false;

	boolean dir0 = false;							// ���� ������ �Ǵ��ϴ� boolean ����
	boolean dir1 = false;
	boolean dir2 = false;
	boolean dir3 = false;

	boolean delay = false;							// �������� Ŭ���� �� delay�� �ֱ� ���� boolean ����

	int limit = 0;									// RecordWrite 1ȸ ���� ������ ���� ����
	
	int stageDelay = 0;								// Ÿ�̸� ������ ���� delay �ð��� ��� ����

	int ENTER_COUNT = 0;							// ����ڰ� enterŰ�� ���� Ƚ���� �����ϱ� ���� ����

	int STARTPAGEINDEX = 0;							// gif ���� �� ���� ������ �̹��� �迭�� �ǹ��ϴ� ����
	int EXPLAINPAGEINDEX = 0;						// gif ���� �� ���� ������ �̹��� �迭�� �ǹ��ϴ� ����

	int STAGE_ENDCOUNT = 0;							// ����ڰ� �� ���� ������ ���� ����
	int STAGEEND=0;									// ����ڰ� ���� �� ���� ������ �����ϴ� ����

	int STAGE = 1;									// STAGE ���� 1���� ����
	int SCORE = 0;									// ������� ����, 0������ ����, ���� 1���� 1��
	int highScore = 0;								// ������ �÷����� ������� ���� �� ���� ���� ����

	int LIFECOUNT = 3;								// ������� �����, �⺻ 3���� ����

	ArrayList<Ball> listB = new ArrayList<>();		// �� ��ü ������ ���� ArrayList
	ArrayList<Ball> removeB = new ArrayList<>();	// �� ��ü ������ ���� ArrayList

	ArrayList<Block> listBK = new ArrayList<>();	// ��� ��ü ������ ���� ArrayList
	ArrayList<Block> removeBK = new ArrayList<>();	// ��� ��ü ������ ���� ArrayList

	ArrayList<Life> listLF = new ArrayList<>();		// ����� ��ü ������ ���� ArrayList
	ArrayList<Life> removeLF = new ArrayList<>();	// ����� ��ü ������ ���� ArrayList

	ArrayList<Item> listI = new ArrayList<>();		// ������ ��ü ������ ���� ArrayList
	ArrayList<Item> removeI = new ArrayList<>();	// ������ ��ü ������ ���� ArrayList

	ArrayList<PlayerInfo> highScoreList = new ArrayList<>();		// ������ �÷����� ������� �̸��� ������ ���Ͽ� �б�, ���⿡ Ȱ���ϱ� ���� �÷��̾� ���� ��ü ArrayList

	PlayerInfo highestPlayer;										// ���� ���� ������ �޼��� ���

	Bar bar;														// ���� ƨ�ܳ��� ���� ��ü

	Image enterImage = new ImageIcon(getClass().getClassLoader().getResource("ENTER.png")).getImage();

	Image gameOver = new ImageIcon(getClass().getClassLoader().getResource("GAMEOVER.png")).getImage(); // GAME OVER �̹���

	Image BAR = new ImageIcon(getClass().getClassLoader().getResource("BAR.png")).getImage();			// BAR �̹���
	Image BALL = new ImageIcon(getClass().getClassLoader().getResource("BALL.png")).getImage();			// BALL �̹���

	Image HEART = new ImageIcon(getClass().getClassLoader().getResource("LIFE.png")).getImage();		// ����� ��Ʈ �̹���

	Image[] ITEM = {
			new ImageIcon(getClass().getClassLoader().getResource("HPPLUS_ITEM.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("ADDBALL_ITEM.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("DAMAGE_ITEM.png")).getImage()};		// 3 ������ ITEM �̹��� �迭
	// 8 ������ BLOCK �̹��� �迭
	Image[] BLOCK = {
			new ImageIcon(getClass().getClassLoader().getResource("WHITE_BLOCK.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("CYAN_BLOCK.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("GREEN_BLOCK.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("YELLOW_BLOCK.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("ORANGE_BLOCK.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("MAGENTA_BLOCK.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("PURPLE_BLOCK.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("RED_BLOCK.png")).getImage()
	};
	// ���� ������ gif�� ���� �̹��� �迭
	Image[] startPage = {
			new ImageIcon(getClass().getClassLoader().getResource("1.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("2.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("3.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("4.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("5.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("6.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("7.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("8.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("9.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("10.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("11.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("12.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("13.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("14.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("15.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("16.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("17.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("18.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("19.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("20.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("21.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("22.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("23.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("24.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("25.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("26.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("27.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("28.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("29.png")).getImage()
	};
	// ���� ������ gif�� ���� �̹��� �迭
	Image[] explainPage = {
			new ImageIcon(getClass().getClassLoader().getResource("1EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("2EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("3EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("4EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("5EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("6EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("7EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("8EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("9EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("10EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("11EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("12EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("13EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("14EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("15EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("16EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("17EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("18EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("19EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("20EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("21EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("22EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("23EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("24EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("25EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("26EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("27EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("28EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("29EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("30EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("31EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("32EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("33EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("34EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("35EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("36EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("37EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("38EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("39EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("40EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("41EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("42EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("43EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("44EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("45EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("46EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("47EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("48EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("49EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("50EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("51EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("52EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("53EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("54EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("55EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("56EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("57EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("58EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("59EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("60EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("61EP.png")).getImage(),
			new ImageIcon(getClass().getClassLoader().getResource("62EP.png")).getImage()
	};

	DrawPanel mainPanel;																				// �׸��� �׷��ִ� mainPanel

	JLabel SCOREINFO;																					// ����, HIGHSCORE, �̸�, �������� ���� JLabel
	JLabel HIGHSCORE;
	JLabel USERNAME;
	JLabel STAGEINFO;

	JLabel REDBLOCKHP;																					// 8 ���� ����� HP ������ ���� JLabel
	JLabel PURPLEBLOCKHP;
	JLabel MAGENTABLOCKHP;
	JLabel ORANGEBLOCKHP;
	JLabel YELLOWBLOCKHP;
	JLabel GREENBLOCKHP;
	JLabel CYANBLOCKHP;
	JLabel WHITEBLOCKHP;

	JLabel highestScore;																				// ���� ���� ����

	JLabel lastScore;																					// ���� ���ھ�

	JLabel LIFE;																						// ����� ����

	JLabel READY;																						// �������� Ŭ���� �� ��� �̹���

	JLabel spaceInfo;																					// �����̽��ٸ� ������ �� ������ �˸��� JLabel

	static String userName;																				// ���� ���� �� �Է¹޴� ����� �̸�

	static int MAIN_PANEL_WIDTH = 1080;																	// �����Ӱ� �г�, BAR, BALL �� �������� ũ�⸦ ���� ����
	static int MAIN_PANEL_HEIGHT = 940;

	static int EXPLAIN_PANEL_WIDTH = 1080;
	static int EXPLAIN_PANEL_HEIGHT = 940;

	static int GAME_PANEL_WIDTH = 800;
	static int GAME_PANEL_HEIGHT = 940;
	static int INFO_PANEL_WIDTH = 280;
	static int INFO_PANEL_HEIGHT = 940;

	static int FRAME_WIDTH = MAIN_PANEL_WIDTH +  16;
	static int FRAME_HEIGHT = MAIN_PANEL_HEIGHT + 39;

	static int BAR_WIDTH = 80;
	static int BAR_HEIGHT = 20;

	static int BLOCK_WIDTH = 50;
	static int BLOCK_HEIGHT = 20;

	static int BALL_SIZE = 20;

	Timer draw;					// �׸��� �׸��� Ÿ�̸�
	Timer process;				// ������ȯ�� �������� ����ϴ� Ÿ�̸�

	public static void effect() {		// ���� ��Ͽ� ������ �Ҹ��� 1ȸ ���� �޼ҵ�
		try {
			File beep = new File(path + "/SOUND/BBeep.wav");
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(beep));
			clip.loop(0);
			clip.start();
		} catch(Exception e) {
			System.out.println("Sound Error BEEP");
		}
	}

	public static void BGM() {			// ���� ���۽� ��������� �����ϴ� �޼ҵ� (���ѹݺ� ���)
		try {
			File bgm = new File(path + "/SOUND/BGM.wav");
			Clip clip2 = AudioSystem.getClip();
			clip2.open(AudioSystem.getAudioInputStream(bgm));
			if(true) {
				clip2.loop(0);
			}
			clip2.start();
		} catch (Exception e2) {
			System.out.println("Sound Error BGM");
		}
	}

	public void RecordRead() throws IOException{		// ������� ���� ����ҿ� �����ϴ� ���� ���� ��� ������ �о�� �޼ҵ�, ����� �о ���� ����Ʈ�� ����
		File record = new File("record.txt");

		Scanner s = null;

		String fileName;
		int fileScore;

		if(!record.exists()) {
			record.createNewFile();
		}
		else {
			try {
				s = new Scanner(new BufferedReader(new FileReader(record)));

				while (s.hasNext()) {
					fileName = s.next();
					fileScore = s.nextInt();

					highScoreList.add(new PlayerInfo(fileName, fileScore));
					System.out.println("Add List");
				}
			} finally {
				if(s != null)
					s.close();
			}
		}
	} 
	public void RecordWrite() throws IOException {		// ������ ����� �� ���� ����Ʈ�� �ִ� ����ڵ��� ������ ����

		highScoreList.add(new PlayerInfo(userName, SCORE));

		FileWriter filewriter;
		try {
			filewriter = new FileWriter(path + "/record.txt");

			for(PlayerInfo pi : highScoreList) {
				filewriter.write(pi.getName() + "\r\n");
				filewriter.write(pi.getScore() + "\r\n");
			}

			filewriter.close();

		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	MainFrame() {			// ������ ������, ������ �⺻ ����, JLabel ���� �� ���� ����, �г� �߰�, Ÿ�̸� ����, ��ҵ� �����ӿ� �߰�

		try {				// ������� �б� ����
			RecordRead();
		} catch (IOException e) {
			e.printStackTrace();
		}

		nameInput();						// ���� ���� �� ����ڸ� �Է¹���

		if(!highScoreList.isEmpty()) {		// ������ �о�� ����Ʈ�� �����ϸ� ���� ������ ��������� ����

			highestPlayer = highScoreList.get(0);

			if(highScoreList.size() >= 2) {	// ����Ʈ�� ������ 2�� �̻��� ��� ���� ����
				for(int i = 1; i < highScoreList.size(); i++) {
					if(highestPlayer.compareTo(highScoreList.get(i)) > 0)
						highestPlayer = highScoreList.get(i);
				}
			}
		}

		this.setLayout(null);
		this.setBackground(Color.black);

		mainPanel = new DrawPanel();
		mainPanel.setBounds(0,0,MAIN_PANEL_WIDTH, MAIN_PANEL_HEIGHT);
		mainPanel.setLayout(null);

		if(!highScoreList.isEmpty()) {		// ����Ʈ�� ������� ���� ��� �ְ����� ����� ���� ���
			highestScore = new JLabel(highestPlayer.getName() + "  " + highestPlayer.getScore());
			highestScore.setFont(new Font("Tmon��Ҹ� Black", Font.PLAIN, 20));
			highestScore.setHorizontalAlignment(JLabel.CENTER);
			highestScore.setForeground(Color.white);
			highestScore.setBounds(820,300,240,50);
			highestScore.setVisible(false);
		}
		else {								// ����Ʈ�� ����ִ� ��� NO RECORD ���
			highestScore = new JLabel("NO RECORD");
			highestScore.setFont(new Font("Tmon��Ҹ� Black", Font.PLAIN, 20));
			highestScore.setHorizontalAlignment(JLabel.CENTER);
			highestScore.setForeground(Color.white);
			highestScore.setBounds(820,300,240,50);
			highestScore.setVisible(false);
		}
		// JLabel ����, ��Ʈ, ����, ��ġ���� ���� ���� �׸��� ���� �����Ҷ� ���̰� ���� ���� ����
		lastScore = new JLabel();
		lastScore.setFont(new Font("Tmon��Ҹ� Black", Font.PLAIN, 45));
		lastScore.setHorizontalAlignment(JLabel.CENTER);
		lastScore.setForeground(Color.white);
		lastScore.setBounds(340,500,400,100);
		lastScore.setVisible(false);

		spaceInfo = new JLabel("<html><body style='text-align:center;'>SPACEBAR �� ������<br>�� ���� �����մϴ�</body></html>");
		spaceInfo.setFont(new Font("Tmon��Ҹ� Black", Font.PLAIN, 17));
		spaceInfo.setHorizontalAlignment(JLabel.CENTER);
		spaceInfo.setForeground(Color.white);
		spaceInfo.setBounds(800,710,280,50);
		spaceInfo.setVisible(false);

		LIFE = new JLabel("LIFE  : ");
		LIFE.setFont(new Font("Tmon��Ҹ� Black", Font.PLAIN, 20));
		LIFE.setHorizontalAlignment(JLabel.CENTER);
		LIFE.setForeground(Color.white);
		LIFE.setBounds(865,780,70,20);
		LIFE.setVisible(false);

		READY = new JLabel("READY");
		READY.setFont(new Font("Tmon��Ҹ� Black", Font.PLAIN, 45));
		READY.setHorizontalAlignment(JLabel.CENTER);
		READY.setForeground(Color.white);
		READY.setBounds(300, 440, 200, 100);

		STAGEINFO = new JLabel("STAGE  " + STAGE);
		SCOREINFO = new JLabel("SCORE : " + SCORE);
		HIGHSCORE = new JLabel("HIGHSCORE");
		USERNAME = new JLabel("����ڸ� : " + userName);

		STAGEINFO.setFont(new Font("Tmon��Ҹ� Black", Font.PLAIN, 45));
		SCOREINFO.setFont(new Font("Tmon��Ҹ� Black", Font.PLAIN, 20));
		HIGHSCORE.setFont(new Font("Tmon��Ҹ� Black", Font.PLAIN, 20));
		USERNAME.setFont(new Font("Tmon��Ҹ� Black", Font.PLAIN, 25));

		STAGEINFO.setHorizontalAlignment(JLabel.CENTER);
		SCOREINFO.setHorizontalAlignment(JLabel.CENTER);
		HIGHSCORE.setHorizontalAlignment(JLabel.CENTER);
		USERNAME.setHorizontalAlignment(JLabel.CENTER);

		STAGEINFO.setForeground(Color.white);
		SCOREINFO.setForeground(Color.white);
		HIGHSCORE.setForeground(Color.white);
		USERNAME.setForeground(Color.white);

		STAGEINFO.setBounds(820,50,240,100);
		SCOREINFO.setBounds(820,150,240,50);
		HIGHSCORE.setBounds(820,250,240,50);
		USERNAME.setBounds(820,850,240,50);

		REDBLOCKHP = new JLabel("HP 8");
		PURPLEBLOCKHP = new JLabel("HP 7");
		MAGENTABLOCKHP = new JLabel("HP 6");
		ORANGEBLOCKHP = new JLabel("HP 5");
		YELLOWBLOCKHP = new JLabel("HP 4");
		GREENBLOCKHP = new JLabel("HP 3");
		CYANBLOCKHP = new JLabel("HP 2");
		WHITEBLOCKHP = new JLabel("HP 1");

		REDBLOCKHP.setFont(new Font("Tmon��Ҹ� Black", Font.PLAIN, 15));
		PURPLEBLOCKHP.setFont(new Font("Tmon��Ҹ� Black", Font.PLAIN, 15));
		MAGENTABLOCKHP.setFont(new Font("Tmon��Ҹ� Black", Font.PLAIN, 15));
		ORANGEBLOCKHP.setFont(new Font("Tmon��Ҹ� Black", Font.PLAIN, 15));
		YELLOWBLOCKHP.setFont(new Font("Tmon��Ҹ� Black", Font.PLAIN, 15));
		GREENBLOCKHP.setFont(new Font("Tmon��Ҹ� Black", Font.PLAIN, 15));
		CYANBLOCKHP.setFont(new Font("Tmon��Ҹ� Black", Font.PLAIN, 15));
		WHITEBLOCKHP.setFont(new Font("Tmon��Ҹ� Black", Font.PLAIN, 15));

		REDBLOCKHP.setHorizontalAlignment(JLabel.CENTER);
		PURPLEBLOCKHP.setHorizontalAlignment(JLabel.CENTER);
		MAGENTABLOCKHP.setHorizontalAlignment(JLabel.CENTER);
		ORANGEBLOCKHP.setHorizontalAlignment(JLabel.CENTER);
		YELLOWBLOCKHP.setHorizontalAlignment(JLabel.CENTER);
		GREENBLOCKHP.setHorizontalAlignment(JLabel.CENTER);
		CYANBLOCKHP.setHorizontalAlignment(JLabel.CENTER);
		WHITEBLOCKHP.setHorizontalAlignment(JLabel.CENTER);

		REDBLOCKHP.setForeground(Color.white);
		PURPLEBLOCKHP.setForeground(Color.white);
		MAGENTABLOCKHP.setForeground(Color.white);
		ORANGEBLOCKHP.setForeground(Color.white);
		YELLOWBLOCKHP.setForeground(Color.white);
		GREENBLOCKHP.setForeground(Color.white);
		CYANBLOCKHP.setForeground(Color.white);
		WHITEBLOCKHP.setForeground(Color.white);

		REDBLOCKHP.setBounds(930,400,100,20);
		PURPLEBLOCKHP.setBounds(930,430,100,20);
		MAGENTABLOCKHP.setBounds(930,460,100,20);
		ORANGEBLOCKHP.setBounds(930,490,100,20);
		YELLOWBLOCKHP.setBounds(930,520,100,20);
		GREENBLOCKHP.setBounds(930,550,100,20);
		CYANBLOCKHP.setBounds(930,580,100,20);
		WHITEBLOCKHP.setBounds(930,610,100,20);
		// mainPanel�� Label ��ҵ� �߰�
		mainPanel.add(READY);

		mainPanel.add(STAGEINFO);
		mainPanel.add(SCOREINFO);
		mainPanel.add(HIGHSCORE);
		mainPanel.add(USERNAME);

		mainPanel.add(REDBLOCKHP);
		mainPanel.add(PURPLEBLOCKHP);
		mainPanel.add(MAGENTABLOCKHP);
		mainPanel.add(ORANGEBLOCKHP);
		mainPanel.add(YELLOWBLOCKHP);
		mainPanel.add(GREENBLOCKHP);
		mainPanel.add(CYANBLOCKHP);
		mainPanel.add(WHITEBLOCKHP);

		mainPanel.add(highestScore);

		mainPanel.add(lastScore);
		mainPanel.add(LIFE);
		mainPanel.add(spaceInfo);

		READY.setVisible(false);

		REDBLOCKHP.setVisible(false);
		PURPLEBLOCKHP.setVisible(false);
		MAGENTABLOCKHP.setVisible(false);
		ORANGEBLOCKHP.setVisible(false);
		YELLOWBLOCKHP.setVisible(false);
		GREENBLOCKHP.setVisible(false);
		CYANBLOCKHP.setVisible(false);
		WHITEBLOCKHP.setVisible(false);

		STAGEINFO.setVisible(false);
		SCOREINFO.setVisible(false);
		HIGHSCORE.setVisible(false);
		USERNAME.setVisible(false);

		draw = new Timer(5, new CallMainPanel());			// �׸� �׷��ִ� Ÿ�̸�
		process = new Timer(3 , new Process());				// ����, ������ Ÿ�̸�

		draw.start();										// �׸� �׸��� Ÿ�̸� ����

		Dimension dim = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());

		this.add(mainPanel);

		this.setLocation((int)dim.getWidth()/2-FRAME_WIDTH/2, (int)dim.getHeight()/2-FRAME_HEIGHT/2);
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setTitle("BLOCK GAME");

		mainPanel.addKeyListener(mainPanel);				// mainPanel�� Ű������ ����
	}

	public static void main(String[] args) {
		new MainFrame();									// ���α׷� ����
	}

	public void nameInput() {								// ����ڿ��� �̸��� �Է¹޴� �޼ҵ�, �̸��� 5�ڰ� �Ѿ�� �ٽ� �Է¹���
		for(;;) {
			userName = (String) JOptionPane.showInputDialog(this, "�̸��� �Է��ϼ���(5�� �̳�)", "BLOCK GAME",JOptionPane.PLAIN_MESSAGE);
			if(userName == null)
				System.exit(0);
			if(userName.length() > 5)
				userName = (String) JOptionPane.showInputDialog(this, "�̸��� �Է��ϼ���(5�� �̳�)", "BLOCK GAME",JOptionPane.PLAIN_MESSAGE);
			else if(userName.length() <= 5) {
				BGM();
				break;
			}
		}
	}

	class CallMainPanel implements ActionListener{			// Ÿ�̸ӿ� ���� ������ �ð����� repaint()�� paintComponent�޼ҵ带 ����
		public void actionPerformed(ActionEvent e) {
			repaint();
		}
	}

	class DrawPanel extends JPanel implements KeyListener{	// �׸��� �׷��ִ� JPanel�� ����� DrawPanel, CallMainPanel�� ���� ���� �ð����� �ݺ������� ȣ��, Ű������ ����
		public void paintComponent(Graphics g) {
			int w = this.getWidth();
			int h = this.getHeight();

			if(listB.isEmpty() && LIFECOUNT == 0) {			// ���� ��� ������� ������� 0�� ��� overPage�� ���̰� ��
				overPageV = true;
				playPageV = false;
				infoPageV = false;
				lastScore.setText("YOUR SCORE : " + SCORE);
			}

			if(startPageV) {								// ���� ȭ���� ���̰� �� ���, Ÿ�̸Ӱ� ������ �ð����� �̹��� �迭�� ������� ������
				g.drawImage(startPage[STARTPAGEINDEX],0,0,w,h,null);

				STARTPAGEINDEX++;
				if(STARTPAGEINDEX == 28)
					STARTPAGEINDEX = 0;
			}

			else if(explainPageV) {							// ���� ȭ���� ���̰� �� ���, Ÿ�̸Ӱ� ������ �ð����� �̹��� �迭�� ������� ������
				g.drawImage(explainPage[EXPLAINPAGEINDEX],0,0,w,h,null);
				g.drawImage(enterImage, 909, 875, 170, 65, null);

				EXPLAINPAGEINDEX++;
				if(EXPLAINPAGEINDEX == 61)
					EXPLAINPAGEINDEX = 0;
			}

			else if(playPageV && infoPageV) {				// ����ȭ��� ����ȭ���� ���̰� �� ���, ���� ���࿡ �ʿ��� ��ҵ�, ���� ĭ�� �ִ� ������� �׷���
				g.setColor(Color.black);
				g.fillRect(0, 0, GAME_PANEL_WIDTH, GAME_PANEL_HEIGHT);
				g.setColor(Color.white);

				g.drawImage(BLOCK[7], 890, 400, 50, 20, null);
				g.drawImage(BLOCK[6], 890, 430, 50, 20, null);
				g.drawImage(BLOCK[5], 890, 460, 50, 20, null);
				g.drawImage(BLOCK[4], 890, 490, 50, 20, null);
				g.drawImage(BLOCK[3], 890, 520, 50, 20, null);
				g.drawImage(BLOCK[2], 890, 550, 50, 20, null);
				g.drawImage(BLOCK[1], 890, 580, 50, 20, null);
				g.drawImage(BLOCK[0], 890, 610, 50, 20, null);

				for(int i = 0; i < 5; i++) {
					g.drawRect(GAME_PANEL_WIDTH + i, 0+i, INFO_PANEL_WIDTH-2*i, INFO_PANEL_HEIGHT-2*i);
				}

				bar.drawBar(g);

				for(Ball drawB : listB) {
					drawB.drawBall(g);
				}

				for(Life lf : listLF)
					lf.drawLife(g);

				for(Item item : listI) {
					item.drawItem(g);
				}

				SCOREINFO.setText("SCORE : " + SCORE);

				STAGEINFO.setText("STAGE  " + STAGE);

				if(listBK.isEmpty() && draw.isRunning() && ENTER_COUNT > 1) {		// ���������� ���� �������� Ŭ��� �ʿ��� ������ ����� �׷���, 1~5 ���� ������ �ٸ��� ����
					if(STAGE == 1) {
						for(int i = 0; i < 16; i++) 
							listBK.add(new Block(i*50, 100, 1, BLOCK[0]));
						STAGEEND = listBK.size();
					}
					else if (STAGE == 2) {
						for(int i = 0; i < 3; i++) 
							for(int j = 0; j < 16; j++)
								listBK.add(new Block(j*50, 100+i*20,3-i,BLOCK[2-i]));
						STAGEEND = listBK.size();
					}
					else if (STAGE == 3) {
						for(int i = 0; i < 5; i++)
							for(int j = 0; j < 16; j++)
								listBK.add(new Block(j*50,100+i*20,5-i,BLOCK[4-i]));
						STAGEEND = listBK.size();
					}
					else if (STAGE == 4) {
						for(int i = 0; i < 8; i++)
							for(int j = 0; j < 16; j++)
								listBK.add(new Block(j*50,100+i*20,8-i, BLOCK[7-i]));
						STAGEEND = listBK.size();
					}
					else if (STAGE == 5) {
						for(int i = 0; i < 15; i++)
							for(int j = 0; j < 16; j++) {
								int randNum = (int)(Math.random()*5)+3;
								listBK.add(new Block(j*50,100+i*20, randNum, BLOCK[randNum]));
							}
						STAGEEND = listBK.size(); 
					}
				}

				for(Block BK : listBK) {
					BK.drawBlock(g);
				}

			}

			else if (overPageV) {						// ������ ����� ��� Ÿ�̸Ӹ� ���߰� ������ �������
				g.drawImage(gameOver, 0, 0, null);
				process.stop();

				STAGEINFO.setVisible(false);
				SCOREINFO.setVisible(false);
				HIGHSCORE.setVisible(false);
				USERNAME.setVisible(false);

				REDBLOCKHP.setVisible(false);
				PURPLEBLOCKHP.setVisible(false);
				MAGENTABLOCKHP.setVisible(false);
				ORANGEBLOCKHP.setVisible(false);
				YELLOWBLOCKHP.setVisible(false);
				GREENBLOCKHP.setVisible(false);
				CYANBLOCKHP.setVisible(false);
				WHITEBLOCKHP.setVisible(false);

				highestScore.setVisible(false);

				LIFE.setVisible(false);
				spaceInfo.setVisible(false);
				lastScore.setVisible(true);

				draw.stop();
				
				if(limit == 0) {
					limit++;
					try {
						RecordWrite();
						System.out.println("Write");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			if(delay) {							// �������� Ŭ����� delay �߻�
				if(STAGE < 6)	
					READY.setVisible(true);
				stageDelay++;
				if(stageDelay == 200) {
					delay = false;
					stageDelay = 0;
					process.restart();
					READY.setVisible(false);
				}
			}

			setFocusable(true);
			requestFocus();
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}
		@Override
		public void keyPressed(KeyEvent e) {		// Ű�� ��������, ����Ű, ESC, �¿� ����Ű, �����̽���
			int keycode = e.getKeyCode();

			if(keycode == KeyEvent.VK_ENTER) {

				if(ENTER_COUNT == 0) {
					startPageV = false;
					explainPageV = true;
					ENTER_COUNT++;
				}

				else if(ENTER_COUNT == 1) {
					explainPageV = false;
					playPageV = true;
					infoPageV = true;
					bar = new Bar();

					if(listB.isEmpty())
						listB.add(new Ball());

					STAGEINFO.setVisible(true);
					SCOREINFO.setVisible(true);
					HIGHSCORE.setVisible(true);
					USERNAME.setVisible(true);

					REDBLOCKHP.setVisible(true);
					PURPLEBLOCKHP.setVisible(true);
					MAGENTABLOCKHP.setVisible(true);
					ORANGEBLOCKHP.setVisible(true);
					YELLOWBLOCKHP.setVisible(true);
					GREENBLOCKHP.setVisible(true);
					CYANBLOCKHP.setVisible(true);
					WHITEBLOCKHP.setVisible(true);

					highestScore.setVisible(true);

					LIFE.setVisible(true);
					spaceInfo.setVisible(true);

					process.start();

					for(int i = 0; i < LIFECOUNT; i++) {
						listLF.add(new Life(i));
					}
					ENTER_COUNT++;
				}

			}

			if(keycode == KeyEvent.VK_RIGHT) {
				KeyRight = true;
			}

			if(keycode == KeyEvent.VK_LEFT) {
				KeyLeft = true;
			}

			if(keycode == KeyEvent.VK_ESCAPE)
				System.exit(0);

			if(keycode == KeyEvent.VK_SPACE && process.isRunning() && LIFECOUNT > 0) {
				listB.add(new Ball(bar.getBARX()+BAR_WIDTH/2-BALL_SIZE, bar.getBARY()-BALL_SIZE));
				removeLF.add(listLF.get(LIFECOUNT-1));
				LIFECOUNT--;
			}
		}
		@Override
		public void keyReleased(KeyEvent e) {		// Ű�� ��������, �¿� ����Ű

			int keycode = e.getKeyCode();

			if(keycode == KeyEvent.VK_RIGHT)
				KeyRight = false;

			if(keycode == KeyEvent.VK_LEFT)
				KeyLeft = false;
		}
	}
	class Process implements ActionListener {					// ���� ����, ���� ������, BAR�� ������, ���� ��, ���, BAR�� �浹���� �� ���� ����� ���� �޼ҵ�
		public void actionPerformed(ActionEvent e) {
			int BAR_LIMIT_R = GAME_PANEL_WIDTH - BAR_WIDTH-5;
			int BAR_LIMIT_L = 0;

			int PANEL_LIMITL = 0;
			int PANEL_LIMITR = GAME_PANEL_WIDTH - BALL_SIZE;
			int PANEL_LIMITU = 0;
			int PANEL_LIMITD = GAME_PANEL_HEIGHT - BALL_SIZE;

			int BAR_LIMITU = bar.getBARY();
			int BAR_LIMITD = bar.getBARY() + BAR_HEIGHT;
			int BAR_LIMITR = bar.getBARX() + BAR_WIDTH;
			int BAR_LIMITL = bar.getBARX();

			for(Item item2 : listI) {
				item2.moveItem();
			}

			for(Life lf : removeLF)
				listLF.remove(lf);

			if(KeyRight && bar.getBARX() <= BAR_LIMIT_R) {
				bar.moveR();
			}
			else if (KeyLeft &&  bar.getBARX() > BAR_LIMIT_L) {
				bar.moveL();
			}

			for(Ball ball1 : listB) {
				int bx = ball1.getBALLX();
				int by = ball1.getBALLY();
				int bDir = ball1.getDir();
				if(bx <= PANEL_LIMITL || bx >= PANEL_LIMITR) {
					if(bDir == 0)
						ball1.setDir(3);
					else if(bDir == 1)
						ball1.setDir(2);
					else if(bDir == 2)
						ball1.setDir(1);
					else if(bDir == 3)
						ball1.setDir(0);
				}
				else if(by <= PANEL_LIMITU) {
					if(bDir == 0)
						ball1.setDir(1);
					else if(bDir == 3)
						ball1.setDir(2);
				}
				else if(by >= PANEL_LIMITD)
					removeB.add(ball1);
			}
			for(Ball removeBall : removeB)
				listB.remove(removeBall);

			for(Ball ball2 : listB) {
				int bxW = ball2.getBALLX();
				int bxN = ball2.getBALLX() + BALL_SIZE/2;
				int bxS = ball2.getBALLX() + BALL_SIZE/2;
				int bxE = ball2.getBALLX() + BALL_SIZE;

				int byN = ball2.getBALLY();
				int byS = ball2.getBALLY() + BALL_SIZE;
				int byW = ball2.getBALLY() + BALL_SIZE/2;
				int byE = ball2.getBALLY() + BALL_SIZE/2;

				int bDamage = ball2.getDamage();

				int bDir = ball2.getDir();

				if(byS > BAR_LIMITU && bxS >= BAR_LIMITL && bxS <= BAR_LIMITR && byS <= BAR_LIMITD) {
					if(bDir == 1)
						ball2.setDir(0);
					else if(bDir == 2)
						ball2.setDir(3);
				}
				else if(byN < BAR_LIMITD && bxN >= BAR_LIMITL && bxN <= BAR_LIMITR && byN >= BAR_LIMITU) {
					if(bDir == 0)
						ball2.setDir(1);
					else if (bDir == 3)
						ball2.setDir(2);
				}
				else if(bxE > BAR_LIMITL && byE >= BAR_LIMITU && byE <= BAR_LIMITD && bxE <= BAR_LIMITR) {
					if(bDir == 1)
						ball2.setDir(2);
					else if(bDir == 0)
						ball2.setDir(3);
				}
				else if(bxW < BAR_LIMITR && byW >= BAR_LIMITU && byW <= BAR_LIMITD && bxW >= BAR_LIMITL) {
					if(bDir == 3)
						ball2.setDir(0);
					else if(bDir == 2)
						ball2.setDir(1);
				}

				for(Block BK1 : listBK) {
					int BLOCK_LIMITU = BK1.getBLOCKY();
					int BLOCK_LIMITD = BK1.getBLOCKY() + BLOCK_HEIGHT;
					int BLOCK_LIMITR = BK1.getBLOCKX() + BLOCK_WIDTH;
					int BLOCK_LIMITL = BK1.getBLOCKX();

					if(byS > BLOCK_LIMITU && bxS >= BLOCK_LIMITL && bxS <= BLOCK_LIMITR && byS <= BLOCK_LIMITD) {
						effect();
						BK1.hit(bDamage);
						if(bDir == 1)
							ball2.setDir(0);
						else if(bDir == 2)
							ball2.setDir(3);
					}
					else if(byN < BLOCK_LIMITD && bxN >= BLOCK_LIMITL && bxN <= BLOCK_LIMITR && byN >= BLOCK_LIMITU) {
						effect();
						BK1.hit(bDamage);
						if(bDir == 0)
							ball2.setDir(1);
						else if (bDir == 3)
							ball2.setDir(2);
					}
					else if(bxE > BLOCK_LIMITL && byE >= BLOCK_LIMITU && byE <= BLOCK_LIMITD && bxE <= BLOCK_LIMITR) {
						effect();
						BK1.hit(bDamage);
						if(bDir == 1)
							ball2.setDir(2);
						else if(bDir == 0)
							ball2.setDir(3);
					}
					else if(bxW < BLOCK_LIMITR && byW >= BLOCK_LIMITU && byW <= BLOCK_LIMITD && bxW >= BLOCK_LIMITL) {
						effect();
						BK1.hit(bDamage);
						if(bDir == 3)
							ball2.setDir(0);
						else if(bDir == 2)
							ball2.setDir(1);
					}
					if(BK1.isCrash()) {
						removeBK.add(BK1);
						STAGE_ENDCOUNT++;
						SCORE++;

						int prob = (int)(Math.random()*5);
						if(prob == 2) {
							int randType = (int)(Math.random()*3);
							listI.add(new Item(BK1.getBLOCKX() + BLOCK_WIDTH/2 - 15, BK1.getBLOCKY() + 30, randType));
						}
					}
				}
				for(Block BK2 : removeBK) {
					listBK.remove(BK2);
				}
			}

			for(Ball ball5 : listB) {
				int bDir = ball5.getDir();
				if(bDir == 0) {
					dir0 = true;
					dir1 = false;
					dir2 = false;
					dir3 = false;
				}
				else if(bDir == 1) {
					dir0 = false;
					dir1 = true;
					dir2 = false;
					dir3 = false;
				}
				else if(bDir == 2) {
					dir0 = false;
					dir1 = false;
					dir2 = true; 
					dir3 = false;
				}
				else if(bDir == 3) {
					dir0 = false;
					dir1 = false;
					dir2 = false;
					dir3 = true;
				}
				if(dir0) 
					ball5.move0();
				else if(dir1) 
					ball5.move1();
				else if(dir2) 
					ball5.move2();
				else if(dir3) 
					ball5.move3();
			}
			for(Item item1 : listI) {
				int itemX = item1.getX();
				int itemY = item1.getY();
				if(itemY >= GAME_PANEL_HEIGHT - 30) {
					removeI.add(item1);
				}
				else if(itemY >= bar.getBARY() && (itemX >= bar.getBARX() && itemX <= bar.getBARX() + BAR_WIDTH && itemY <= bar.getBARY()+BAR_HEIGHT)) {
					item1.eat();
					removeI.add(item1);
				}
			}
			for(Item removeItem : removeI) {
				listI.remove(removeItem);
			}

			if(STAGE_ENDCOUNT == STAGEEND) {
				listBK.clear();
				process.stop();
				STAGE++;
				bar.resetBar();
				for(Ball ball4 : listB)
					ball4.resetBall();
				delay = true;
				STAGE_ENDCOUNT = 0;

				if(STAGE == 6) {
					overPageV = true;
					playPageV = false;
					infoPageV = false;
					lastScore.setText("YOUR SCORE : " + SCORE);
				}
			}

		}
	}

	class Ball {				// �� ��ü, XY ��ǥ�� �����̴� �ȼ�ũ��, ���⿡ ���� ������ ���� , ��ǥ�� ��ȯ�ϴ� �޼ҵ�, �׷��ִ� �޼ҵ�, ��ġ�� �ʱ�ȭ�ϴ� �޼ҵ�, ������ �޼ҵ� ����
		int BALLX;
		int BALLY;

		int moveB = 5;

		int DAMAGE = 1;

		int dir = 0;

		Ball(int x, int y){
			BALLX = x;
			BALLY = y;
		}
		Ball() {
			BALLX = GAME_PANEL_WIDTH/2 - BALL_SIZE/2;
			BALLY = 650;
		}
		public void drawBall(Graphics gBall) {
			gBall.drawImage(BALL, BALLX, BALLY, BALL_SIZE, BALL_SIZE, null);
		}
		public void resetBall() {
			BALLX = GAME_PANEL_WIDTH/2 - BALL_SIZE/2;
			BALLY = 650;
			dir = 0;
		}
		public void move0() {
			BALLX += moveB;
			BALLY -= moveB;
		}
		public void move1() {
			BALLX += moveB;
			BALLY += moveB;
		}
		public void move2() {
			BALLX -= moveB;
			BALLY += moveB;
		}
		public void move3() {
			BALLX -= moveB;
			BALLY -= moveB;
		}
		public int getBALLX() {
			return BALLX;
		}
		public int getBALLY() {
			return BALLY;
		}
		public void setDir(int inputDir) {
			dir = inputDir;
		}
		public int getDir() {
			return dir;
		}
		public int getDamage() {
			return DAMAGE;
		}
		public void damageUp() {
			if(DAMAGE < 3)
				DAMAGE++;
		}
	}

	class Bar{						// BAR��ü XY��ǥ�� ����, �����̴� �ȼ� ũ��, �׷��ִ� �޼ҵ�, ��ġ�� �ʱ�ȭ �����ִ� �޼ҵ�, ��ǥ ��ȯ �޼ҵ�
		int BARX;
		int BARY;

		int moveBar = 10;

		Bar() {
			BARX = GAME_PANEL_WIDTH/2 - BAR_WIDTH/2;
			BARY = 700;
		}
		public void drawBar(Graphics gBar) {
			gBar.drawImage(BAR, BARX, BARY, BAR_WIDTH, BAR_HEIGHT, null);
		}
		public void resetBar() {
			BARX = GAME_PANEL_WIDTH/2 - BAR_WIDTH/2;
			BARY = 700;
		}
		public void moveR() {
			BARX += moveBar;
		}
		public void moveL() {
			BARX -= moveBar;
		}
		public int getBARX() {
			return BARX;
		}
		public int getBARY() {
			return BARY;
		}

	}

	class Block{					// ��� ��ü, XY��ǥ�� ����, HP���� ����, �׷��ִ� �޼ҵ�, ��ǥ��ȯ �޼ҵ�, ����� �������� �Ǵ��ϴ� �޼ҵ� ����
		int BLOCKX;
		int BLOCKY;
		int HP;

		Image blockImage;

		Block(int x, int y, int hp, Image inputImage){
			BLOCKX = x;
			BLOCKY = y;
			HP = hp;

			blockImage = inputImage;
		}
		public void drawBlock(Graphics gBlock) {
			gBlock.drawImage(blockImage,BLOCKX,BLOCKY,BLOCK_WIDTH,BLOCK_HEIGHT,null);
		}
		public int getBLOCKX() {
			return BLOCKX;
		}
		public int getBLOCKY() {
			return BLOCKY;
		}
		public void hit(int h) {
			HP -= h;
		}
		public boolean isCrash() {
			if(HP <= 0)
				return true;
			else
				return false;
		}
	}

	class Item{					// ������ ��ü, XY��ǥ�� ����, ������ Ÿ���� �����ϴ� ���� ����, �׷��ִ� �޼ҵ�, �������� �Ծ��� �� �޼ҵ�, ��ǥ��ȯ �޼ҵ� ����
		int ITEMX;
		int ITEMY;

		int TYPE;

		Item(int x, int y, int type) {
			ITEMX = x;
			ITEMY = y;
			TYPE = type;
		}
		public void drawItem(Graphics gItem) {
			gItem.drawImage(ITEM[TYPE], ITEMX, ITEMY, 30, 30, null);
		}
		public void eat() {
			if(TYPE == 0) {
				if(LIFECOUNT < 3) {
					listLF.add(new Life(LIFECOUNT));
					LIFECOUNT++;
				}
			}
			else if(TYPE == 1) {
				listB.add(new Ball(bar.getBARX() + BAR_WIDTH/2-BALL_SIZE/2, bar.getBARY() - BALL_SIZE));

			}
			else if(TYPE == 2) {
				for(Ball dmgUp : listB)
					dmgUp.damageUp();
			}
		}
		public void moveItem() {
			ITEMY++;
		}
		public int getX() {
			return ITEMX+15;
		}
		public int getY() {
			return ITEMY+30;
		}

	}

	class Life {			// ����� ��ü, �� ������� INDEX�� ����, INDEX�� ���� ��Ʈ �׸��� ��ġ�� ����

		int index;

		Life(int INDEX) {
			index = INDEX;
		}

		public void drawLife(Graphics gLife) {
			gLife.drawImage(HEART, 945+index*30, 780, 20, 20, null);
		}
	}

	class PlayerInfo {		// ����ڿ� ���� ������ �����ϴ� ��ü, ArrayList�� �߰��Ͽ� �ְ����� ������
		String playerName;
		int playerScore;

		PlayerInfo(String inputName, int inputScore) {
			playerName = inputName;
			playerScore = inputScore;
		}

		public int compareTo(PlayerInfo playerinfo) {
			if(playerinfo.playerScore > this.playerScore)
				return 1;
			else
				return 0;
		}
		public void printInfo() {
			System.out.println("NAME : " + playerName + ", SCORE :" + playerScore);
		}
		public String getName() {
			return playerName;
		}
		public int getScore() {
			return playerScore;
		}
	}
}