package ips;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mat
 */
public class AlgoPosition implements Runnable{
     
     private IPS ips;
     
     double alphaX =0.0002;
     double alphaY =0.0002;
     
     double[] direction;
     int[] count;
     
     ArrayList<Double> differencesPosition=new ArrayList<Double>();
     
     public AlgoPosition(IPS ips)
     {
          this.ips=ips;
          count=new int[ips.readers.size()];
          direction=new double[ips.readers.size()];
          for (int i = 0; i < ips.readers.size(); ++i)
          {
              count[i]=0;
               ips.readers.get(i).setEstimatedX(Constantes.LARGEUR/2);
               ips.readers.get(i).setEstimatedY(Constantes.HAUTEUR/2);
          }
     }

     @Override
     public void run() {
          while (true)
          {
               for (int i = 0; i < ips.readers.size(); ++i) // Pour chaque reader
               {
                    ArrayList<Double> distances = ips.readers.get(i).getDistance(ips.tags); // calcul des distances du reader aux tags
                    ArrayList<Double> estimatedDistances = ips.readers.get(i).getEstimatedDistance(ips.tags); // calcul des distances du reader à l'estimation de la position du reader
                    
                    ArrayList<Double> erreurs = calculErreurs(distances, estimatedDistances); // calcul des erreurs entre l'estimation et la position
                    
                    int k = findDirection(erreurs); // trouver la direction d'ajustement de l'estimation
                    
                    int adjustementx = (int) (alphaX * erreurs.get(k) * (ips.readers.get(i).getEstimatedX()-ips.tags.get(k).getX()));
                    int adjustementy = (int) (alphaY * erreurs.get(k) * (ips.readers.get(i).getEstimatedY()-ips.tags.get(k).getY()));
                    ips.readers.get(i).setEstimatedX(ips.readers.get(i).getEstimatedX()+adjustementx);
                    ips.readers.get(i).setEstimatedY(ips.readers.get(i).getEstimatedY()+adjustementy);
                    
                    if(count[i] % 20==0)
                    {
                        direction[i] = direction[i]+Math.random()*Math.PI/6-Math.random()*Math.PI/6;
                        ips.readers.get(i).setX(Math.max((int) (ips.readers.get(i).getX()+(Math.cos(direction[i])*Constantes.VITESSEDEPLACEMENT*10)),0));
                        ips.readers.get(i).setY(Math.max((int) (ips.readers.get(i).getY()+(Math.sin(direction[i])*Constantes.VITESSEDEPLACEMENT*10)),0));
                    }
                    count[i]+=1;
                    ips.repaint();
                    differencesPosition.add(Math.sqrt(Math.pow(ips.readers.get(i).getX()-ips.readers.get(i).getEstimatedX(), 2)+Math.pow(ips.readers.get(i).getY()-ips.readers.get(i).getEstimatedY(), 2)));
               }
               try {
                    Thread.sleep(5);
                    } catch (InterruptedException ex) {
                         
                    }
               if(count[0]==4000)
                   try {
                       double tmp=0;
                       for(int i =0;i<differencesPosition.size();++i)
                           tmp+=differencesPosition.get(i);
                       System.out.println(tmp/differencesPosition.size());
                        this.wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(AlgoPosition.class.getName()).log(Level.SEVERE, null, ex);
                    }
          }
     } 
     
     private ArrayList<Double> calculErreurs(ArrayList<Double> distances, ArrayList<Double> estimatedDistances)
     {
          ArrayList<Double> erreurs=new ArrayList<Double>();
          for (int i = 0; i < distances.size(); ++i)
          {
               if(distances.get(i)==-1)
                    erreurs.add((double)0);
               else
                    erreurs.add(distances.get(i)-estimatedDistances.get(i));
          }
          return erreurs;
     }

     private int findDirection(ArrayList<Double> erreurs) {
          int k = 0;
          double max = 0;
          for(int i = 0; i < erreurs.size(); ++i)
          {
               if(Math.pow(erreurs.get(i),2)>max)
               {
                    k=i;
                    max=Math.pow(erreurs.get(i),2);
               }
          }
          return k;
     }
}
