



import java.util.Arrays;
import java.util.List;

public class Word_Finder implements WordFinder {

    @Override
    public List<String> wordFinder(String arg) {
        String[] ma = arg.split("\\s+");
        List<String> ml = Arrays.asList(ma);
        return ml;
    }
}
