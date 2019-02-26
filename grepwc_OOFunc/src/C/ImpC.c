#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

char **readLines(char *filename);

char **filterLines(char **text,char *wordSearch);

char **convertCase(char ** text);

char **findWords(char **base);

char ** nonABCFilter(char ** str);

void reformat_string(char *src, char *dst) ;

int numberOfLines = 0;
int structamt = 0;
int n;
struct pair *mypair;

struct pair *countwords(char **str);

struct pair{
    char *word;
    int amt;
};




struct pair *doesExist(char * word, int k);

void Driver(int argc, char ** argv);

int main(int argc, char ** argv) {

    char *test[4];
    test[1] = "grep";test[2] = "this";test[3] = "/Users/davidlevy/yu_git/PL/FinalProject/src/Java/OOJava/Txt.txt";
    char ** grep= test;
    char *testwc[4];
    testwc[1] = "wc";testwc[2] = "/Users/davidlevy/yu_git/PL/FinalProject/src/Java/OOJava/Txt.txt";
    char ** wc= testwc;
    char *testboth[6];
    testboth[1] = "grep";testboth[2] = "this";testboth[3] = "/Users/davidlevy/yu_git/PL/FinalProject/src/Java/OOJava/Txt.txt";
    testboth[4] ="|";testboth[5] ="wc";
    char ** both= testboth;

    Driver(argc, argv);

    return 0;
}

void Driver(int argc, char ** argv){


    if(argc > 4){
        char **text = readLines(argv[3]);
        char **res1= filterLines(text, argv[2]);
        char **res2 = convertCase(res1);
        char **res3 = findWords(res2);
        char **res4 = nonABCFilter(res3);
        struct pair *ms = countwords(res4);
        for( int i=0; i< structamt; i++){
            printf("%s -> %d \n", ms[i].word, ms[i].amt);
        }
    }
    else if(strcmp(argv[1], "grep") ==0){
        char **text = readLines(argv[3]);

        char **res1= filterLines(text, argv[2]);
        for( int i=0; i< numberOfLines; i++){
            printf("%s", res1[i]);
        }
    }
    else if(strcmp(argv[1], ("wc")) ==0){
        char **text = readLines(argv[2]);
        char **res2 = convertCase(text);
        char **res3 = findWords(res2);
        char **res4 = nonABCFilter(res3);
        struct pair *ms = countwords(res4);
        for( int i=0; i< structamt; i++){
            printf("%s -> %d \n", ms[i].word, ms[i].amt);
        }

    }
}

char **filterLines(char **text,char *wordSearch){
    size_t len = 0;
    ssize_t read;
    char **out = (char **)malloc(numberOfLines * sizeof(char*));
    int j = 0;
    for(int i =0 ; i < numberOfLines;i++) {
        if (strstr(text[i],wordSearch) != NULL){
            out[j] = text[i];
            j++;
        }

    }
    numberOfLines = j;
    return out;
}

char **convertCase(char ** text){
    for (int j =0; j< numberOfLines; j++){
        for(int i = 0; text[j][i] != '\0'; i++){
            text[j][i] = (char)tolower(text[j][i]);
        }
    }

    return text;
}

char ** findWords(char **p){
    char **after = (char **)malloc(numberOfLines * sizeof(char *));
    n=0;
    int i ,j=0;
    char wordlst[100 * numberOfLines][50];
    for(int d = 0 ; d < numberOfLines; d++) {
        char* inputString = p[d];


            for(i=0 ;1;i++)
            {
                if(inputString[i]!=' '){
                    wordlst[n][j++]=inputString[i];
                }
                else if(inputString[i] == '\n'){
                    break;

                }
                else{
                    wordlst[n][j++]='\0';//insert NULL
                    n++;
                    j=0;
                }
                if(inputString[i]=='\0'){
                    break;
                }
                    //break;
            }

        }
        for (int i =0; i< numberOfLines*n;i++){
            after[i] = wordlst[i];


        }
    return after;
        //after[i][j] = '\0';



}
char * wordedit(char * line){
    int i, j;


    for(i = 0; line[i] != '\0'; ++i)
    {
        while (!( (line[i] >= 'a' && line[i] <= 'z') || (line[i] >= 'A' && line[i] <= 'Z') || line[i] == '\0') )
        {
            for(j = i; line[j] != '\0'; ++j)
            {
                line[j] = line[j+1];
            }
            line[j] = '\0';
        }
    }
    
    return line;
}

char ** nonABCFilter(char ** p) {
    //char **after = (char **)malloc(numberOfLines * sizeof(char *));
    for(int i = 0 ; i < numberOfLines*n; i++) {
        p[i] = wordedit(p[i]);
    }
    return p;


}


struct pair * countwords(char **str){
    //TODO: set number of lines
    mypair = (struct pair*)malloc(n * sizeof(struct pair));
    int j =0 ;
    structamt =n;
    struct pair mp = {.amt =1, .word = str[0]};
    structamt-= 1;
    mypair[0] = mp;

    for (int i = 1; i < n; i++){
        struct pair *exist = doesExist(str[i], i-1);
        if(exist != NULL){
            exist->amt = exist->amt +1;
            structamt-= 1;

        }
        else{
            struct pair mp = {.amt =1, .word = str[i]};

            mypair[j++] = mp;
        }
    }
    return mypair;
}

struct pair *doesExist(char *word, int k){
    int i = 0, j =0;
    while (i <k){
        if (strcmp(word, mypair[i].word) ==0){
            return &mypair[i];
        }
        i++;
    }
    return NULL;
}




/**
 * Read from stream to array of lines.
 * @param filename
 * @return
 */
char **readLines(char *filename) {
    int i = 0;
    size_t  j = 0;
    FILE  *fp;
    size_t  dummy  = 0;
    char  **array  = NULL;
    size_t  asize  = 2;
    size_t lnctr  = 0;

    array = (char**) malloc(asize * sizeof(char*));
    for (i = 1 ; i < 2; i++ ){
        fp = fopen(filename, "r");
        if (!fp) continue;
        while ( fp && !ferror(fp) && !feof(fp)){
            if (lnctr > (asize-2)){
                asize += 1;
                array = (char**)realloc(array, asize*sizeof(char*));
            }
            dummy = 0;
            array[lnctr] = NULL;
            getline(&(array[lnctr]), &dummy, fp);
            ++lnctr;
        }
        numberOfLines = (int) lnctr;
        fclose(fp);
        fp = NULL;
    }
    return array;
}


