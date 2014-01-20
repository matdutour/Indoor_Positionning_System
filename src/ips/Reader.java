package ips;

import java.util.ArrayList;

/**
 *
 * @author Mat
 */
public class Reader {
     private int x=0;
     private int y=0;
     
     private int estimatedx=0;
     private int estimatedy=0;
     
     public Reader(int x, int y)
     {
          this.x=x;
          this.y=y;
     }
     
     public int getX()
     {
          return x;
     }
     
     public int getY()
     {
          return y;
     }
     
     public void setX(int x)
     {
          this.x=x;
     }
     
     public void setY(int y)
     {
          this.y=y;
     }    
          
     public int getEstimatedX()
     {
          return estimatedx;
     }
     
     public int getEstimatedY()
     {
          return estimatedy;
     }
     
     public void setEstimatedX(int x)
     {
          this.estimatedx=x;
     }
     
     public void setEstimatedY(int y)
     {
          this.estimatedy=y;
     }
     
     public ArrayList<Double> getDistance(ArrayList<Tag> tags)
     {
          ArrayList<Double> distances = new ArrayList<Double>();
          for (int i = 0; i < tags.size(); ++i)
          {
               Double tmp = Math.sqrt(Math.pow(x-tags.get(i).getX(),2)+Math.pow(y-tags.get(i).getY()+(Math.random()-Math.random())*Constantes.AMPLITUDEBRUIT/2,2));
               distances.add((tmp<800)?tmp:-1);
          }
          return distances;          
     }
     
     public ArrayList<Double> getEstimatedDistance(ArrayList<Tag> tags)
     {
          ArrayList<Double> distances = new ArrayList<Double>();
          for (int i = 0; i < tags.size(); ++i)
          {
               distances.add(Math.sqrt(Math.pow(estimatedx-tags.get(i).getX(),2)+Math.pow(estimatedy-tags.get(i).getY(),2)));
          }
          return distances;          
     }

     boolean contains(int x, int y) {
          return x < this.x + 4 && x > this.x - 4 && y < this.y + 4 && y > this.y - 4;
     }
}
