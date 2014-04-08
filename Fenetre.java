/* Projet de fin de Semestre 6 : Le jeu de la vie
 * Par Damien Larminé et Marine Megherbi
 * 
 * Jeu de la Vie : Crée par John Conway en 1970
 * 
 * Classe Fenetre qui affiche le drawPanel, et qui crée le thread nécessaire à la boucle de jeu
 * */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
  
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
  
  
@SuppressWarnings({ "unused", "serial" })

public class Fenetre extends JFrame implements Runnable {
  
    //**************************************
    //               LE MENU
    //**************************************
    private JMenuBar menuBar = new JMenuBar();
    JMenu   fichier = new JMenu("Fichier"),
            simulation = new JMenu("Simulation"),
            controle = new JMenu("Contrôle"),
            population = new JMenu("Populations"),
            option = new JMenu("Options");
     
    JMenuItem   nouveau = new JMenuItem("Effacer"),
                quitter = new JMenuItem("Quitter"),
                pause = new JMenuItem("Pause"),
                stop = new JMenuItem("Stop"),
                lancer = new JMenuItem("Lancer"),
                n1pop = new JMenuItem("1 Groupe"),
                n2pop = new JMenuItem("2 Groupes"),
                n3pop = new JMenuItem("3 Groupes");
     
    //****************************************
    //          LA BARRE D'OUTILS
    //****************************************
     
    JToolBar toolBar = new JToolBar();
     
    JButton stopped = new JButton(new ImageIcon("images/rouge.jpg")),
            launched = new JButton(new ImageIcon("images/vert.jpg")),
            paused = new JButton(new ImageIcon("images/gris.png")),
            pop1 = new JButton(new ImageIcon("images/carre-noir.jpg")),
            pop2 = new JButton(new ImageIcon("images/carre-bleu.jpg")),
            pop3 = new JButton(new ImageIcon("images/carre-jaune.jpg"));
     
    //***************************************
    //          LES ÉCOUTEURS
    //***************************************
    private StopListener sListener = new StopListener();
    private LaunchListener lListener = new LaunchListener();
    private PauseListener pListener = new PauseListener();    
    private ColorListener cListener = new ColorListener();
    private GroupeListener gListener = new GroupeListener();
    
    int cellSize = 20; 
	int cellCols = 100;  
	int cellRows = 100;
    
    //Notre zone de dessin
    private DrawPanel drawPanel = new DrawPanel(cellSize,cellCols,cellRows);
    
    private Thread gameThread = null;
    private int genTime = 100;
    private Label genLabel; 
    
    
    public Fenetre(){
        this.setSize(1000, 1000);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
        //On initialise le menu
        this.initMenu();
        //Idem pour la barre d'outils
        this.initToolBar();
        //On positionne notre zone de dessin
        this.getContentPane().add(drawPanel, BorderLayout.CENTER);
        this.setVisible(true);     
    }
     
    /*
     * Initialise le menu
     */
    private void initMenu(){
        nouveau.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                drawPanel.clear();
            }          
        });
         
        nouveau.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK));
         
        quitter.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                System.exit(0);
            }          
        });
        quitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK));
         
        fichier.add(nouveau);
        fichier.addSeparator();
        fichier.add(quitter);
        fichier.setMnemonic('F');
        
        stop.setMnemonic('t');
        lancer.setMnemonic('L');
        pause.setMnemonic('P');
        stop.addActionListener(sListener);
        lancer.addActionListener(lListener);
        pause.addActionListener(pListener);
        controle.setMnemonic('C');
        controle.add(lancer);
        controle.add(stop);
        controle.add(pause);
         
        simulation.setMnemonic('S');
        simulation.add(controle);
        
        n1pop.addActionListener(gListener);
        n2pop.addActionListener(gListener);
        n3pop.addActionListener(gListener);
        
        population.add(n1pop);
        population.add(n2pop);
        population.add(n3pop);
        
        option.add(population);
        
        menuBar.add(fichier);
        menuBar.add(simulation);
        menuBar.add(option);
         
        this.setJMenuBar(menuBar);
    }
     
    /*
     * Initialise la barre d'outils
     */
    private void initToolBar(){
    	genLabel = new Label( "Generations: 0 " );
    	
        JPanel panneau = new JPanel();
        stopped.addActionListener(sListener);
        launched.addActionListener(lListener);
        paused.addActionListener(sListener);
        pop1.addActionListener(cListener);
        pop2.addActionListener(cListener);
        pop3.addActionListener(cListener);
        
        pop2.setVisible(false);
        pop3.setVisible(false);
        
        toolBar.add(launched);
        toolBar.add(paused);
        toolBar.add(stopped);
        toolBar.addSeparator();
        toolBar.add(pop1);
        toolBar.add(pop2);
        toolBar.add(pop3);
        toolBar.addSeparator();
        toolBar.add(genLabel);
        
        
                         
        this.getContentPane().add(toolBar, BorderLayout.NORTH);
    }     
     
    class LaunchListener implements ActionListener{ //la classe qui permet de lancer la simulation
        public void actionPerformed(ActionEvent e) {
        		start2();
        	
        }      
    }
    
    class PauseListener implements ActionListener{ //la classe qui permet de faire une pause dans la simulation
        public void actionPerformed(ActionEvent e) {
            stop();
        }      
    }
    
    class StopListener implements ActionListener{ // la classe qui permet de stopper la simulation
    	public void actionPerformed(ActionEvent e){
    		drawPanel.clear();
    	}
    }
    
    class ColorListener implements ActionListener{ //la classe qui permet de lancer la simulation

        public void actionPerformed(ActionEvent e) {
        		if(e.getSource() == pop1){
        			drawPanel.population = 0;
        		}
        		else if(e.getSource() == pop2){
        			drawPanel.population = 1;
        		}
        		
        		else if(e.getSource() == pop3){
        			drawPanel.population = 2;
        		}
        	
        }      
    }
    
    class GroupeListener implements ActionListener{ //la classe qui permet de lancer la simulation
        public void actionPerformed(ActionEvent e) {
        		if(e.getSource() == n1pop){
        			pop2.setVisible(false);
        			pop3.setVisible(false);
        			drawPanel.clear1();
        		}
        		else if(e.getSource() == n2pop){
        			pop2.setVisible(true);
        			pop3.setVisible(false);
        			drawPanel.clear2();
        		}
        		
        		else if(e.getSource() == n2pop){
        			pop2.setVisible(true);
        			pop3.setVisible(true);
        		}
        	
        }      
    }
    
    public void start2() { //pour parametrer le lancement de la boucle
		if(gameThread == null) { 
			gameThread = new Thread(this); 
			gameThread.start(); 
		} 
	} 

	@SuppressWarnings("deprecation")
	public void stop() { // pour parametrer le bouton stop 
		if(gameThread != null) { 
			gameThread.stop(); 
			gameThread = null; 
		} 
	} 

	@SuppressWarnings("static-access")
	public void run() { 
		while (gameThread != null) { 
				drawPanel.next(); 
				drawPanel.repaint();
				showGenerations();
				try { 
					gameThread.sleep( genTime ); 
				}	catch (InterruptedException e){} 
		} 
	}
    
    public void showGenerations() { 
		genLabel.setText( "Generations: "+drawPanel.generations ); 
	}
     
    public static void main(String[] args){
        Fenetre fen = new Fenetre(); 
		} 
    }