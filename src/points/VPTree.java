package points;

import java.util.ArrayList;
import java.util.Iterator;
import vptree.VpTreeNode;

public class VPTree {
    
    private static VpTreeNode<Point> root;
    
    public static void buildVPTree(ArrayList<Point> points) {
        root = VpTreeNode.buildVpTree(points);
    }
    
    public static void calcRadius(ArrayList<Point> points) {
        Iterator<Point> iter = points.iterator();
        while(iter.hasNext()) {
            Point point = iter.next();
            
            
            double radius = Double.POSITIVE_INFINITY;
            ArrayList<VpTreeNode<Point>> nodesArray = new ArrayList<>(1);
            double distance;
            
            nodesArray.add(root);
            
            while(nodesArray.size() > 0) {
                VpTreeNode<Point> node = nodesArray.remove(0);
                

                
                //Если рассматриваемый узел - лист
                //То рассчитать расстояния от всех точек узла до point
                if (node.getLeft() == null) {
                    Iterator<Point> nodePointIter = node.getPoints().iterator();
                    while(nodePointIter.hasNext()) {
                        Point p = nodePointIter.next();
                        
                        if(point != p) {
                            double d = point.distance(p);
                            if(d < radius) {
                                radius = d;
                            }
                        }
                    }
                    continue;
                } else {
                    if(point != node.getVantagePoint()) {
                        distance = point.distance(node.getVantagePoint());
                        if(distance < radius) {
                            radius = distance;
                        }
                    } else {
                        distance = 0;
                        if(node.getLeftRadius() < radius) {
                            radius = node.getLeftRadius();
                        }                        
                    }
                }
            
                if(distance <= node.getLeftRadius() + radius) {
                    nodesArray.add(node.getLeft());
                }
                if(distance >= node.getLeftRadius() - radius) {
                    nodesArray.add(node.getRight());
                }
            }
            
            //Для случая одной точки. 
            if (radius == Double.POSITIVE_INFINITY) {
                radius = 0;
            }
                
            point.setRadius(radius);
        }
    }
    
    static void calcNeighboursCount(ArrayList<Point> points) {
        Iterator<Point> iter = points.iterator();
        while(iter.hasNext()) {
            Point p = iter.next();
            
            p.setNeighboursCount(root.findNearbyPoints(p, p.getRadius() * 2).size() - 1);
        }
    }
}