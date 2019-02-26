



import java.util.HashMap;
import java.util.List;

public class DocumentProcessorBuilder {
    private LineFilter filterdLines;
    private Case_Converter CaseConvert;
    private Word_Finder wordFind;
    private NonABCFilter nonABC;
    private Word_Counter wordCount;

    private String fileName;

    private DocumentProcessorBuilder(Builder builder) {
        this.filterdLines = builder.filterdLines;
        this.CaseConvert = builder.CaseConvert;
        this.wordFind = builder.wordFind;
        this.nonABC = builder.nonABC;
        this.wordCount = builder.wordCount;

    }

    public HashMap<String, Integer> process(String fileName){
        //grep
        if(this.CaseConvert == null){
            String fn = filterdLines.lineFilter(fileName);
            HashMap<String, Integer> myh = new HashMap<>();
            int i = 0;
            myh.put(fn, i);
            return myh;
        }//wc
        else if(this.filterdLines == null){
            String first=  CaseConvert.convertCase(fileName);
            List<String> sec = wordFind.wordFinder(first);
            List<String> thir = nonABC.nonABCFilter(sec);
            return wordCount.wordCount(thir);
        }
        else {
            String fileN = filterdLines.lineFilter(fileName);
            String first=  CaseConvert.convertCase(fileN);
            List<String> sec = wordFind.wordFinder(first);
            List<String> thir = nonABC.nonABCFilter(sec);
            return wordCount.wordCount(thir);
        }

    }





    public static class Builder {
        private LineFilter filterdLines;
        private Case_Converter CaseConvert;
        private Word_Finder wordFind;
        private NonABCFilter nonABC;
        private Word_Counter wordCount;


        public Builder setLineFilter(LineFilter lf) {
            this.filterdLines = lf;
            return this;
        }

        public Builder setCaseConvert(Case_Converter CC) {
            this.CaseConvert = CC;
            return this;

        }
        public Builder setWordFinder(Word_Finder wf){
            this.wordFind = wf;
            return this;
        }
        public Builder setNonABCFilter(NonABCFilter nonABC){
            this.nonABC = nonABC;
            return this;
        }
        public Builder setWordCounter(Word_Counter wc){
            this.wordCount = wc;
            return this;
        }
        public DocumentProcessorBuilder build(){
            return new DocumentProcessorBuilder(this);
        }

    }

}
