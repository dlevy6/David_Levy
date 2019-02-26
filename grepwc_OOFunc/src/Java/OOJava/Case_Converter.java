 

import java.io.File;
import java.util.Scanner;

public class Case_Converter implements CaseConverter {
    StringBuilder str;

    public Case_Converter() {
    }

    @Override
    public String convertCase(String file) {
        str = new StringBuilder();
        try {
            File file1 = new File(file);
            Scanner scanner = new Scanner(file1);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                str.append(line.toLowerCase() + "\n");

            }
            scanner.close();
            return str.toString();

        } catch (Exception e) {
            return file.toLowerCase();
        }
    }
}
