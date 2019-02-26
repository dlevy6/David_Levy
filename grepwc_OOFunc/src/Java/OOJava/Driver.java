



import java.util.HashMap;

public class Driver {


    public Driver(String[] aug) {
        LineFilter lf = new LineFilter(aug[1]);
        Case_Converter caseConvert = new Case_Converter();
        Word_Finder wordFind = new Word_Finder();
        NonABCFilter nonABC = new NonABCFilter();
        Word_Counter wordCount = new Word_Counter();

        if(aug.length > 3){

            HashMap<String, Integer> hm = new DocumentProcessorBuilder.Builder().setLineFilter(lf).setCaseConvert(caseConvert).setWordFinder(wordFind)
                    .setNonABCFilter(nonABC).setWordCounter(wordCount).build().process(aug[2]);
            for ( String key : hm.keySet() ) {
                System.out.println( key +" "+ hm.get(key));
            }
        }
        else if(aug[0].equals("grep")){

            HashMap<String, Integer> hm = new DocumentProcessorBuilder.Builder().setLineFilter(lf).build().process(aug[2]);
            for ( String key : hm.keySet() ) {
                System.out.println( key );
            }

        }
        else if(aug[0].equals("wc")){

            HashMap<String, Integer> hm = new DocumentProcessorBuilder.Builder().setCaseConvert(caseConvert).setWordFinder(wordFind)
                    .setNonABCFilter(nonABC).setWordCounter(wordCount).build().process(aug[1]);
            for ( String key : hm.keySet() ) {
                System.out.println( key +" "+ hm.get(key));
            }
        }



    }

    public static void main(String[] args) {
        String[] grep= {"grep", "this", "/Users/davidlevy/yu_git/PL/FinalProject/out/production/FinalProject/Java/OOJava/Txt.txt"};
        String[] wc = {"wc", "/Users/davidlevy/yu_git/PL/FinalProject/out/production/FinalProject/Java/OOJava/Txt.txt"};
        String[] both = {"grep", "this", "/Users/davidlevy/yu_git/PL/FinalProject/out/production/FinalProject/Java/OOJava/Txt.txt", "|", "wc"};
        new Driver(args);
    }
}
