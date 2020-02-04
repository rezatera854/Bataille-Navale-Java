package Model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import View.JeuView;
import View.PlateauView;

public class AnimArtillerie {
	private JButton bouton;
	private int tempo = 0;
	private boolean firstClick = false;
	private tt t1;
	private Thread thread;
	private int y, x = 0;
	private ArrayList<Case> listeCase;
	private ImageIcon cible = new ImageIcon(this.getClass().getResource("/cible.png"));
	private ImageIcon splash = new ImageIcon(this.getClass().getResource("/splash-finale.png"));
	private ImageIcon fond = new ImageIcon(this.getClass().getResource("/artillerie.png"));
	public Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public int width = (int)screenSize.getWidth();
	public int height = (int)screenSize.getHeight();
	private int mode;
	
	public AnimArtillerie(ArrayList<Case> listeCase, int mode) {
		this.listeCase = listeCase;
		this.mode = mode;
		
		bouton = new JButton();
		bouton.setBounds(500 , 100, 200, 50);

		t1 = new tt();
		
		bouton.addActionListener(event -> cible());
		}
	
	public JButton getButton() {
		return bouton;
	}
	
	public void cible() {
		if(tempo == 0) {
			tempo = 1;
			thread = new Thread(t1);
			thread.start();

		}
		else if(tempo == 1) {
			tempo = 2;
		}
		else {
			tempo = 0;
			thread.stop();
			if(mode == 3) {
				AnimCase anim = new AnimCase(listeCase.get((y - 1) * 10 + x), JeuModel.getGridDroite());
				listeCase.get((y - 1) * 10 + x).setValue(3);
			}
			else if(mode == 4) {
				AnimRadar radar = new AnimRadar(listeCase.get((y - 1) * 10 + x), PlateauView.getListeDroite(), JeuView.gridDroite);
			}
			for(int i = 0; i < 10; i++) {
				/*System.out.println(listeCase);
				System.out.println(" y = " + y);
				System.out.println(" i = " + i);*/
				if(listeCase.get(i + (y - 1) *10).getButton().getIcon() != null) {
					if(listeCase.get(i + (y - 1) *10).getButton().getIcon().equals(fond)) {
						listeCase.get(i + (y - 1) *10).getButton().setIcon(null);
					}
				}
			}
			
		}	
	}
	
	public class tt implements Runnable{

		@Override
		public void run() {
			  while(tempo == 1) {
				  try {
						for(int j = 0; j < 100; j++) {
							
							if(listeCase.get(j).getButton().getIcon() != null) {
								if(listeCase.get(j).getButton().getIcon().equals(fond)){
									listeCase.get(j).getButton().setIcon(null);
								}
								
							}
							
						}
					  
					for(int i = 0; i < 10; i++) {
						if (listeCase.get(y * 10 + i).getValue() == 3) {
					  		listeCase.get((y - 1)*10 + 10+ i).getButton().setIcon(splash);
					  	}
						else {
							listeCase.get(y * 10 + i).getButton().setIcon(fond);
						}			
					}
					
					y = (y + 1) % 10;
					
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }	
			  
			  while(tempo == 2) {
				  
				  try {
					  
					  for(int i = 0; i < 10; i++) {
									
						  	x = i;
						  	
						  	listeCase.get((y - 1)*10 + i).getButton().setIcon(cible);

							Thread.sleep(300);
							listeCase.get((y - 1)*10 + i).getButton().setBackground(new Color(22, 26, 63));
							listeCase.get((y - 1)*10 + i).getButton().setIcon(fond);
						}
					 
				
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }	
		}
		
	}
}
