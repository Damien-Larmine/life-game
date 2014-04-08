/* Projet de fin de Semestre 6 : Le jeu de la vie
 * Par Damien Larminé et Marine Megherbi
 * 
 * Jeu de la Vie : Crée par John Conway en 1970
 * 
 * Classe DrawPanel qui dessine les cases et teste leur voisinage pour déterminer si elle vivent ou meurent
 * */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;
  
  
@SuppressWarnings("serial")
public class DrawPanel extends JPanel{
  
    private int cellSize; 
	private int cellRows; 
	private int cellCols;
    private boolean cells[][]; // tableau des cellules
    private int cellsBuffer[][]; //tableau des voisins
    private boolean cells2[][];
    private int cellsBuffer2[][];
    private boolean cells3[][];
    private int cellsBuffer3[][];
    public int generations;
    public int population = 0;
     
    /**
     * Constructeur
     */
    public DrawPanel(final int cellSize, int cellCols, int cellRows ){
    	
    	cells = new boolean[cellCols][cellRows]; 
		cellsBuffer = new int[cellCols][cellRows];
		cells2 = new boolean[cellCols][cellRows]; 
		cellsBuffer2 = new int[cellCols][cellRows];
		cells3 = new boolean[cellCols][cellRows]; 
		cellsBuffer3 = new int[cellCols][cellRows];
		
		this.cellSize = cellSize; 
		this.cellCols = cellCols; 
		this.cellRows = cellRows; 
    	
		if(population == 0){
			
			this.addMouseListener(new MouseAdapter(){ //listener qui affiche une case si on clique sur le drawPanel
				public void mousePressed(MouseEvent e){
					try { 
						cells[e.getX()/cellSize][e.getY()/cellSize] = true; 
					} catch ( java.lang.ArrayIndexOutOfBoundsException f ) {}
					repaint();
				}
			});
  
			this.addMouseMotionListener(new MouseMotionListener(){ //listener qui dessine les cases lorsque l'on garde le bouton gauche cliqué
				public void mouseDragged(MouseEvent e) {
					try { 
						cells[e.getX()/cellSize][e.getY()/cellSize] = true; 
					} catch ( java.lang.ArrayIndexOutOfBoundsException f ) {}
					repaint();
            }
            
				public void mouseMoved(MouseEvent e) {}
			}); 
    	}
		else if(population == 1){
			this.addMouseListener(new MouseAdapter(){ 
				public void mousePressed(MouseEvent e){
					try { 
						cells2[e.getX()/cellSize][e.getY()/cellSize] = true; 
					} catch ( java.lang.ArrayIndexOutOfBoundsException f ) {}
					repaint();
				}
			});
  
			this.addMouseMotionListener(new MouseMotionListener(){ 
				public void mouseDragged(MouseEvent e) {
					try { 
						cells2[e.getX()/cellSize][e.getY()/cellSize] = true; 
					} catch ( java.lang.ArrayIndexOutOfBoundsException f ) {}
					repaint();
            }
            
				public void mouseMoved(MouseEvent e) {}
			}); 
		}
		else{
			this.addMouseListener(new MouseAdapter(){ 
				public void mousePressed(MouseEvent e){
					try { 
						cells3[e.getX()/cellSize][e.getY()/cellSize] = true; 
					} catch ( java.lang.ArrayIndexOutOfBoundsException f ) {}
					repaint();
				}
			});
  
			this.addMouseMotionListener(new MouseMotionListener(){ 
				public void mouseDragged(MouseEvent e) {
					try { 
						cells3[e.getX()/cellSize][e.getY()/cellSize] = true; 
					} catch ( java.lang.ArrayIndexOutOfBoundsException f ) {}
					repaint();
            }
            
				public void mouseMoved(MouseEvent e) {}
			}); 
		}
    }
     
    //Méthode qui dessine la grille et les cellules
    
    public void paintComponent(Graphics g) { 
         
        g.setColor(Color.white);        
        g.fillRect( 0, 0, cellSize*cellCols-1, cellSize*cellRows-1 ); 
		// dessiner la grille
		g.setColor(getBackground()); 
		for( int x=1; x<cellCols; x++ ) { 
			g.drawLine( x*cellSize-1, 0, x*cellSize-1, cellSize*cellRows-1 ); 
		} 
		for( int y=1; y<cellRows; y++ ) { 
			g.drawLine( 0, y*cellSize-1, cellSize*cellCols-1, y*cellSize-1 ); 
		} 
		//dessiner les cellules
		g.setColor(Color.black); 
		for( int y=0; y<cellRows; y++ ) { 
			for( int x=0; x<cellCols; x++ ) { 
				if ( cells[x][y] ) { 
					g.fillRect( x*cellSize, y*cellSize, cellSize-1, cellSize-1 ); 
				} 
			} 
		}
		
		g.setColor(Color.blue); 
		for( int y=0; y<cellRows; y++ ) { 
			for( int x=0; x<cellCols; x++ ) { 
				if ( cells2[x][y] ) { 
					g.fillRect( x*cellSize, y*cellSize, cellSize-1, cellSize-1 ); 
				} 
			} 
		} 
		
		g.setColor(Color.yellow); 
		for( int y=0; y<cellRows; y++ ) { 
			for( int x=0; x<cellCols; x++ ) { 
				if ( cells3[x][y] ) { 
					g.fillRect( x*cellSize, y*cellSize, cellSize-1, cellSize-1 ); 
				} 
			} 
		} 
    }
               
     
    /*
     * Efface le contenu
     */
    public synchronized void clear() { 
		generations = 0; 
		for( int x=0; x<cellCols; x++ ) { 
			for( int y=0; y<cellRows; y++ ) { 
				cells[x][y] = false;
				cells2[x][y] = false;
				cells3[x][y] = false;
			} 
		}
		repaint();
	}
    
    public synchronized void clear1() { //efface la 2eme et 3eme population
		generations = 0; 
		for( int x=0; x<cellCols; x++ ) { 
			for( int y=0; y<cellRows; y++ ) { 
				cells2[x][y] = false;
				cells3[x][y] = false;
			} 
		}
		repaint();
	}
    
    public synchronized void clear2() { //efface uniquement la 3eme population
		generations = 0; 
		for( int x=0; x<cellCols; x++ ) { 
			for( int y=0; y<cellRows; y++ ) { 
				cells3[x][y] = false;
			} 
		}
		repaint();
	}
    
    // Méthode qui calcule la génération suivante
    
    public synchronized void next() { 
		int x; 
		int y; 

		generations++; 
		// vide le tableau des voisins 
		for( x=0; x<cellCols; x++ ) { 
			for( y=0; y<cellRows; y++ ) { 
				cellsBuffer[x][y] = 0; 
			} 
		} 

		// teste les voisins
		for( x=1; x<cellCols-1; x++ ) { 
			for( y=1; y<cellRows-1; y++ ) { 
				if ( cells[x][y] ) { 
					cellsBuffer[x-1][y-1]++; 
					cellsBuffer[x][y-1]++; 
					cellsBuffer[x+1][y-1]++; 
					cellsBuffer[x-1][y]++; 
					cellsBuffer[x+1][y]++; 
					cellsBuffer[x-1][y+1]++; 
					cellsBuffer[x][y+1]++; 
					cellsBuffer[x+1][y+1]++; 
				} 
			} 
		} 

		// test les voisins des cellules sur les cotés de la grille 
		x=1; 
		y=0; 
		int dx=1; 
		int dy=0; 
		while( true ) { 
			if ( cells[x][y] ) { 
				if ( x > 0 ) { 
					if ( y > 0 ) 
						cellsBuffer[x-1][y-1]++; 
					if ( y < cellRows-1 ) 
						cellsBuffer[x-1][y+1]++; 
					cellsBuffer[x-1][y]++; 
				} 
				if ( x < cellCols-1 ) { 
					if ( y < cellRows-1 ) 
						cellsBuffer[x+1][y+1]++; 
					if ( y > 0 ) 
						cellsBuffer[x+1][y-1]++; 
					cellsBuffer[x+1][y]++; 
				} 
				if ( y > 0 ) 
					cellsBuffer[x][y-1]++;
		 
				if ( y < cellRows-1 ) 
					cellsBuffer[x][y+1]++; 
			} 

			// lorsque l'on atteint le coté de la grille on change de direction
			if ( x==cellCols-1 && y==0 ) { 
				dx = 0; 
				dy = 1; 
			} else if ( x==cellCols-1 && y==cellRows-1 ) { 
				dx = -1; 
				dy = 0; 
			} else if ( x==0 && y==cellRows-1 ) { 
				dx = 0; 
				dy = -1; 
			} else if ( x==0 && y==0 ) { 
			// se termine quand toutes les cellules ont été testées
				break; 
			} 
			x += dx; 
			y += dy; 
		} 
		
		//L'algorithme du jeu de la vie
		for( x=0; x<cellCols; x++ ) { 
			for( y=0; y<cellRows; y++ ) { 
				switch( cellsBuffer[x][y] ) { 
				case 2: 
					// pas de changement dans ce cas 
					break; 
				case 3: 
					cells[x][y] = true; 
					break; 
				default: 
					cells[x][y] = false; 
					break; 
				} 
			} 
		} 

	} 
    
 }
