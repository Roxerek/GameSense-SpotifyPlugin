package pl.roxerek.splugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileUtil {

    public static String readAll(File f){
        StringBuilder output = new StringBuilder();
        try {
            Scanner scanner = new Scanner(f);
            while(scanner.hasNext()){
                output.append(scanner.next());
            }
            return output.toString();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

}
