package ips;

import java.util.ArrayList;

/**
 *
 * @author Mat
 */
public class IPS {

     public Plan plan;
     public ArrayList<Tag> tags;
     public ArrayList<Reader> readers;
     /**
      * @param args the command line arguments
      */
     public static void main(String[] args) {
          new IPS();
     }
     
     public IPS()
     {
          initTags();
          initReaders();
    
          plan = new Plan(this);
          
          this.repaint();
          
          AlgoPosition tmp = new AlgoPosition(this);
          tmp.run();
     }
     
     private void initTags()
     {
          tags=new ArrayList<Tag>();
          int x = 0;
          int y;
          while (x<Constantes.LARGEUR)
          {
               y=0;
               while(y<Constantes.HAUTEUR)
               {
                    tags.add(new Tag(x,y));
                    y+=Constantes.ESPACEMENTY;
               }  
               x+=Constantes.ESPACEMENTX;
          }
     }
     
     private void initReaders()
     {
          readers=new ArrayList<Reader>();
          for(int i =0; i<Constantes.NOMBREUTILISATEUR;++i)
          {
            readers.add(new Reader(Constantes.LARGEUR/2,Constantes.HAUTEUR/2));
          }
     }

     void repaint() {
          plan.repaint();
     }
}
