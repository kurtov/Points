package points;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import org.json.simple.JSONArray;

public class Generator {
    private static final int DEFAULT_COUNT = 1000;
    
    private static int getArg(String[] args, int i) {
        int arg = 0;

        try {
            arg = Integer.parseInt(args[i]);
        } catch (NumberFormatException e) {
            System.err.println("Argument: " + args[i] + " must be an int.");
            System.exit(1);
        }

        return arg;
    }
    
    public static void main(String[] args) {
        Random rnd = new Random();
        JSONArray list = new JSONArray();
        PrintWriter out = null;
        
        int count;
        String fileName;
        
        if(args.length == 2) {
            count = getArg(args, 0);
            fileName = args[1];
        } else {
            count = DEFAULT_COUNT;
            fileName = Main.DEFAULT_INPUT_FILE;
        }
        
        for(int i=0; i < count; i++) {
            JSONArray point = new JSONArray();
            point.add(rnd.nextInt());
            point.add(rnd.nextInt());
        
            list.add(point);
        }
        
        try {
            out = new PrintWriter(fileName);
            out.println(list);
        }
        catch (IOException e) {
            System.err.println("IO Exception");
        }
        finally {
            if(out != null) {
                out.close();
            }
        }
    }
}
