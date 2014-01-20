package ips;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Mat
 */
public class Plan extends JFrame{
     
     private IPS ips;
     private Afficheur affich;
     private int LONGUEUR=2000;
     private int LARGEUR=2000;
     
     public Plan(IPS ips)
     {
          this.ips=ips;
          setupFrame();
          initComponent();
     }
     
     private void setupFrame() 
     {
          this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
          this.setExtendedState(JFrame.MAXIMIZED_BOTH);
          this.setTitle("IPS - Indoor Positionning System");
          this.getContentPane().setLayout(new FlowLayout());
          this.setVisible(true);
     }
     
     private void initComponent() 
     {
          affich=new Afficheur();
          ReaderListener rl = new ReaderListener();
          affich.addMouseListener(rl);
          affich.addMouseMotionListener(rl);
          JScrollPane scroll = new JScrollPane(affich);
          Dimension screenSize = java.awt.Toolkit.getDefaultToolkit ().getScreenSize ();
          scroll.setMaximumSize(new Dimension(screenSize.height,screenSize.width));
          scroll.getVerticalScrollBar().setUnitIncrement(10);
          scroll.getHorizontalScrollBar().setUnitIncrement(10);
          this.setContentPane(scroll);
          this.getContentPane().validate();
          affich.repaint();
          //this.pack();
          this.repaint();
     }
     
     private class Afficheur extends JPanel{
          
          Afficheur (){
               this.setSize(new Dimension(LONGUEUR,LARGEUR));
               this.setPreferredSize(new Dimension(LONGUEUR,LARGEUR));               
          }
          
          @Override
          public void paintComponent(Graphics g)
          {
              super.paintComponent(g);
              ArrayList<Double> distances = ips.readers.get(0).getDistance(ips.tags);
              for (int i=0;i<ips.tags.size();++i)
              { 
                   g.setColor((distances.get(i)==-1)?Color.YELLOW:Color.ORANGE);
                   g.fillOval(ips.tags.get(i).getX()-4, ips.tags.get(i).getY()-4, 8, 8);
              }

              for (int i=0;i<ips.readers.size();++i)
              { 
                   g.setColor(Color.RED);
                   g.fillOval(ips.readers.get(i).getX()-4, ips.readers.get(i).getY()-4, 8, 8);
                   
                   g.setColor(Color.BLUE);
                   g.fillOval(ips.readers.get(i).getEstimatedX()-4, ips.readers.get(i).getEstimatedY()-4, 8, 8);
              }
           }
     }
     
     private class ReaderListener implements MouseListener, MouseMotionListener{
          private int selected = -1;
          @Override
          public void mouseClicked(MouseEvent me) {}

          @Override
          public void mousePressed(MouseEvent me) {
               for(int i = 0; i < ips.readers.size(); ++i)
               {
                    if(ips.readers.get(i).contains(me.getX(),me.getY()))
                    {
                         selected=i;
                         break;
                    }
               }
          }

          @Override
          public void mouseReleased(MouseEvent me) {
               selected = -1;
          }

          @Override
          public void mouseEntered(MouseEvent me) {}

          @Override
          public void mouseExited(MouseEvent me) {}

          @Override
          public void mouseDragged(MouseEvent me) {
               if(selected != -1)
               {
                    ips.readers.get(selected).setX(me.getX());
                    ips.readers.get(selected).setY(me.getY());
                    affich.repaint();
               }
          }

          @Override
          public void mouseMoved(MouseEvent me) {
          }
          
     }
}
