


import java.util.HashMap;
import java.util.List;

public class Word_Counter implements WordCounter {

    @Override
    public HashMap<String, Integer> wordCount(List<String> arg) {

        HashMap<String, Integer> hm = new HashMap<>();
        for (String w : arg){
            if (hm.get(w) != null){
                int num = hm.get(w);
                num++;
                hm.put(w, num);
            }else {
                hm.put(w, 1);
            }
        }
        return hm;
    }
}
