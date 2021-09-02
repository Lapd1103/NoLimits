package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;

import data.Enemie;
import data.Level;
import data.PDFSources;
import data.Player;
import data.Question;
import logic.GameEngine;
import logic.Load;
import logic.ManagePDF;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.SystemColor;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import java.awt.Toolkit;

public class window extends JFrame {

	private JPanel contentPane;
	private int widht = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	private int height = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;

	/**
	 * Create the frame.
	 */
	public window() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(window.class.getResource("/sources/icons/game.png")));
		setTitle("NoLimits");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1366, 728);
		initialize();
	}

	/**
	 * Inicializa los paneles de la ventana
	 */
	private void initialize() {
		JPanel PStart = new JPanel();
		PStart.setBackground(SystemColor.controlShadow);
		PStart.setBounds(0, 0, widht, height);
		PStart.setLayout(null);
		PStart.setVisible(true);

		JPanel PLvls = new JPanel();
		PLvls.setBackground(Color.DARK_GRAY);
		PLvls.setBounds(0, 0, widht, height);
		PLvls.setLayout(null);
		PLvls.setVisible(false);

		JPanel PLvl = new JPanel();
		PLvl.setBackground(Color.DARK_GRAY);
		PLvl.setBounds(0, 0, widht, height);
		PLvl.setLayout(null);
		PLvl.setVisible(false);

		getContentPane().add(PStart);
		getContentPane().add(PLvls);
		getContentPane().add(PLvl);

		/**
		 * Botones para cambiar de pestañas
		 */
		
		//Font for buttons
		Font labelFont = new Font(Font.MONOSPACED, Font.BOLD, 30);
		// Inicio --> Aventura
		JLabel btnGLvls = new JLabel("> Modo Aventura");
		btnGLvls.setFont(labelFont);
		btnGLvls.setForeground(Color.white);
		btnGLvls.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PStart.setVisible(false);
				PLvls.setVisible(true);
			}
		});
		btnGLvls.setBounds(870, 290, 400, 125);
		PStart.add(btnGLvls);

		// Aventura --> Inicio
		JLabel lblBackA = new JLabel();
		lblBackA.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PLvls.setVisible(false);
				PStart.setVisible(true);
			}
		});
		lblBackA.setIcon(new ImageIcon(window.class.getResource("/sources/icons/arrow.png")));
		lblBackA.setBounds(30, 35, 61, 64);
		PLvls.add(lblBackA);

		// initializePLvl(PLvl);

		// Setting up of PDFViewer instance

		ButtonNBPDF nextBtn = new ButtonNBPDF("up.png");
		ButtonNBPDF beforeBtn = new ButtonNBPDF("down.png");
		PDFViewer generalPDFView = new PDFViewer(this, nextBtn, beforeBtn, this.widht, this.height);
		PDFSources sourcesPDF = new PDFSources();
		JLabel homeLabel = generalPDFView.getHomeLabel();
		ManagePDF.addDataEventsNext(nextBtn, sourcesPDF, generalPDFView.getController());
		ManagePDF.addDataEventsBefore(beforeBtn, sourcesPDF, generalPDFView.getController());
		ManagePDF.addEventHomeLabel(PStart, generalPDFView, homeLabel);
		// Principal Screen -> PDFViewer Screen
		JLabel btnPDFViewer = new JLabel("> Modo Repaso");
		btnPDFViewer.setFont(labelFont);
		btnPDFViewer.setForeground(Color.white);
		btnPDFViewer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PStart.setVisible(false);
				generalPDFView.setVisible(true);
				generalPDFView.getController().openDocument("src/sources/PDFs/1.Principios de POO.pdf");
				generalPDFView.getController().setZoom(2.24f);
				ButtonNBPDF.pointerPDF = 0;
				System.out.println("Inside ActionListener of btnPDFViewer");
			};

		});
		btnPDFViewer.setBounds(870, 375, 400, 125);
		PStart.add(btnPDFViewer);
		
		//JOptionPane(credits)
		JLabel btnCredits = new JLabel("> Créditos");
		btnCredits.setFont(labelFont);
		btnCredits.setForeground(Color.white);
		btnCredits.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "Proyecto para aprender las bases de Programación Orientada"
						+ " a Objetos\nCreado por: Laura Paez, David Rativa, Ivan Rojas\n"
						+ "Universidad Nacional de Colombia @ 2021", 
						"NoLimits", JOptionPane.INFORMATION_MESSAGE,
						new ImageIcon("src/sources/icons/game.png"));
			};

		});
		btnCredits.setBounds(870, 460, 400, 125);
		PStart.add(btnCredits);

		/**
		 * Inicializacion de las pestañas
		 */
		initializePStart(PStart);
		initializePLvls(PLvls, PLvl);
	}

	private void initializePStart(JPanel PStart) {
		String lvl = GameEngine.loadLevelPlayer();

		JLabel lblFondo = new JLabel("");
		lblFondo.setIcon(Load.loadImg("/backgrounds/levels/" + lvl + ".jpg"));
		lblFondo.setBounds(0, -20, widht, height);
		PStart.add(lblFondo);
	}

	private void initializePLvls(JPanel PLvls, JPanel PLvl) {

		ArrayList<Level> levels = Load.initLvls();

		Load.loadIconsLvls(PLvls, PLvl, levels);

		JLabel lblFondo = new JLabel("");
		lblFondo.setIcon(Load.loadImg("/backgrounds/w_levels.jpg"));
		lblFondo.setBounds(0, -20, widht, height);
		PLvls.add(lblFondo);

	}

	public static void initializePlvl(JPanel PLvls, JPanel PLvl, ImageIcon bg, Enemie enemie, Player player,
			ArrayList<Question> questions) {
		PLvl.removeAll();
		PLvl.repaint();

		/**
		 * Boton de regreso
		 */
		JLabel lblBackN = new JLabel();
		lblBackN.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PLvl.setVisible(false);
				PLvls.setVisible(true);
			}
		});
		lblBackN.setIcon(new ImageIcon(window.class.getResource("/sources/icons/arrow.png")));
		lblBackN.setBounds(30, 35, 61, 64);
		PLvl.add(lblBackN);

		/**
		 * Preguntas
		 */
		JLabel boxQuestion = new JLabel();
		boxQuestion.setBackground(Color.WHITE);
		boxQuestion.setBounds(0, 528, 1366, 200);
		boxQuestion.setOpaque(true);
		PLvl.add(boxQuestion);

		questions.get(4).showQuestion(boxQuestion);

		/**
		 * Jugador
		 */
		JLabel lblPlayer = new JLabel("");
		lblPlayer.setIcon(player.getImg());
		lblPlayer.setBounds(300, 220, player.getImg().getIconWidth(), player.getImg().getIconHeight());
		PLvl.add(lblPlayer);
		showLife(PLvl, player.getLife(), 400, 220);

		/**
		 * Enemigo
		 */
		JLabel lblEnemie = new JLabel("");
		lblEnemie.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int liveA = enemie.getLife();
				enemie.setLife(liveA - 20);
				initializePlvl(PLvls, PLvl, bg, enemie, player, questions);

			}
		});
		lblEnemie.setIcon(enemie.getImg());
		lblEnemie.setBounds(enemie.getPosX(), enemie.getPosY(), enemie.getImg().getIconWidth(),
				enemie.getImg().getIconHeight());
		PLvl.add(lblEnemie);
		showLife(PLvl, enemie.getLife(), enemie.getPosX() + (enemie.getImg().getIconWidth() / 2), enemie.getPosY());

		/**
		 * Fondo
		 */
		JLabel lblFondo = new JLabel("");
		lblFondo.setIcon(bg);
		lblFondo.setBounds(0, -20, 1366, 728);
		PLvl.add(lblFondo);
	}

	private static void showLife(JPanel panel, int life, int posX, int posY) {

		JLabel lblLife = new JLabel();
		lblLife.setBackground(Color.GRAY);
		lblLife.setBounds(posX - 140, posY - 60, life, 30);
		lblLife.setOpaque(true);
		panel.add(lblLife);

		JLabel cLife = new JLabel();
		cLife.setIcon(Load.loadImg("/icons/life.png"));
		cLife.setBounds(posX - 150, posY - 90, 330, 70);
		panel.add(cLife);
	}

}
