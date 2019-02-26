



import java.util.List;

public class NonABCFilter implements NonABCFilterer {
    @Override
    public List<String> nonABCFilter(List<String> arg) {
        for (int w=0; w< arg.size(); w++){
            arg.set(w, arg.get(w).replaceAll("[^a-zA-Z]", ""));
        }
        return arg;

    }
}
