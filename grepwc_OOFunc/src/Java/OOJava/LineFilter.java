

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;

public class LineFilter implements Line_Filterer {
    private StringBuilder str;
    File file;
    String word;
    public LineFilter(String string) {
        word = string;
    }

    @Override
    public String lineFilter(String fileName) {
        file = new File(fileName);
        str = new StringBuilder();
        try {

            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains(word)) {
                    str.append(line + "\n");
                }


            }
            scanner.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        return str.toString();
    }
}