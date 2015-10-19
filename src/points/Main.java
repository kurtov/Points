package points;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import vptree.VpTreeNode;

public class Main {
    public static final String DEFAULT_INPUT_FILE = "data/default.json";
    private static final String DEFAULT_OUTPUT_FILE = "data/result.json";

    public static void main(String[] args) {
        Plane plane = new Plane();
        BufferedReader in = null;
        PrintWriter out = null;
        String inputFileName;
        String outputFileName;
        JSONArray array = null;
        
       
        if(args.length == 2) {
            inputFileName = args[0];
            outputFileName = args[1];
        } else {
            inputFileName = DEFAULT_INPUT_FILE;
            outputFileName = DEFAULT_OUTPUT_FILE;
        }
        
        //Считать из входного файла данные в массив array
        try {
            in = new BufferedReader(new FileReader(inputFileName));
            array = (JSONArray)JSONValue.parse(new BufferedReader(new FileReader(inputFileName)));
            in.close();
        }
        catch (IOException e) {
            try {
                if(in != null) {
                    in.close();
                }
            }
            catch (IOException e2) {}
            finally {
                System.err.println("IO Exception with input file");
                System.exit(1);
            }
        }
        
        //На основе считанного файла создать точки и добавить их на плоскость
        Iterator<JSONArray> arrIter = array.iterator();
        while(arrIter.hasNext()) {
            JSONArray pointArray = arrIter.next();
           
            plane.add( new Point(
                (int)(long)pointArray.get(0),
                (int)(long)pointArray.get(1)
            ));
        }

        long start = System.nanoTime();
        String s1 = linear(plane);
        System.out.println("Linear search completed, took " + (System.nanoTime() - start) + " ns");
        writeFile(s1, outputFileName);


        start = System.nanoTime();
        String s2 = vptree(plane.getPoints());
        System.out.println("VP-tree search completed, took " + (System.nanoTime() - start) + " ns");

        System.out.println("Result is same: " + s1.equals(s2));

    }
    
    static void writeFile(String str, String fileName) {
        //Записать результаты работы в выходной файл
        try (PrintWriter out = new PrintWriter(fileName)) {
            out.println(str);
        }
        catch (IOException e) {
            System.err.println("IO Exception with output file");
        }
    }
    
    static String linear(Plane plane) {
        return plane.toJSONString();
    }
    
    static String vptree(ArrayList<Point> points) {
        JSONArray array = new JSONArray();
        VpTreeNode<Point> node = VpTreeNode.buildVpTree(points);

        Iterator<Point> iter = points.iterator();
        while(iter.hasNext()) {
            Point p = iter.next();
            
            JSONObject obj = new JSONObject();

            obj.put("point", p);
            obj.put("radius", p.getRadius());
            obj.put("neighbours", node.findNearbyPoints(p, p.getRadius() * 2).size() - 1);

            array.add(obj);   
        }
        
        return array.toJSONString();
    }
}