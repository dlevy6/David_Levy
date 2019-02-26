//
// Created by David Levy on 10/28/18.
//
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "dataStore.h"



struct Car* maxMem;
struct Car mostRecentCar;
int amtOfCarsInArray = 0;
int carLengthInMemArray;
FILE *fp;
int numberOfCarsOnDisk;


struct Car findCarByIDOnDisk(int id);

/**
 * adds car to maxMem or disc
 */
int addCar(char* make, char* model, short year, long price, int uniqueID){
    struct Car myCar = {.year = year, .price = price,.uniqueID = uniqueID, .make = make, .model = model};
    strcpy(myCar.make, make);
    strcpy(myCar.model, model);
    if(maxMem == NULL){
        FILE *oldFile;
        oldFile = fopen("extraCars.txt", "r");

        FILE *newFile;
        newFile = fopen("tmp.txt", "w");
        int len;
        char line[4096];

        if (oldFile != NULL) {
            fprintf(newFile,  "%d %s %s %hu %ld\n", myCar.uniqueID, myCar.make, myCar.model, myCar.year, myCar.price);
            while (fgets(line, sizeof line, oldFile)) {
                len = strlen(line);
                if (len && (line[len - 1] != '\n')) {} else {
                        fputs(line, newFile);
                }

            }

        } else {
            fprintf(newFile,  "%d %s %s %hu %ld\n", myCar.uniqueID, myCar.make, myCar.model, myCar.year, myCar.price);
        }
        numberOfCarsOnDisk++;
        remove("extraCars.txt");
        rename("tmp.txt", "extraCars.txt");
        fclose(oldFile);
        fclose(newFile);
    }
    else if (amtOfCarsInArray == carLengthInMemArray){
        struct Car* mc = &maxMem[carLengthInMemArray-1];

        FILE *oldFile;
        oldFile = fopen("extraCars.txt", "r");

        FILE *newFile;
        newFile = fopen("tmp.txt", "w");
        int len;
        char line[4096];
        fprintf(newFile,  "%d %s %s %hu %ld\n", mc->uniqueID, mc->make, mc->model, mc->year, mc->price);
        if (oldFile != NULL) {
            while (fgets(line, sizeof line, oldFile)) {
                len = strlen(line);
                if (len && (line[len - 1] != '\n')) {}
                else{
                    fputs(line, newFile);
                }

            }
        } else {

        }
        remove("extraCars.txt");
        rename("tmp.txt", "extraCars.txt");
        fclose(oldFile);
        fclose(newFile);
        for(int i =carLengthInMemArray-1; i>0; i--){
            maxMem[i]= maxMem[i-1];
        }
        maxMem[0] = myCar;
        numberOfCarsOnDisk++;
        
    }else{
        for(int i =carLengthInMemArray-1; i>0; i--){
            maxMem[i]= maxMem[i-1];
        }
        maxMem[0] = myCar;
        amtOfCarsInArray++;
    }
    mostRecentCar = myCar;


}

/**
 *
 */
int setMaxMemory(size_t bytes){
    int oldCarlen = carLengthInMemArray;

    carLengthInMemArray = bytes / sizeof(struct Car);
    if (oldCarlen < carLengthInMemArray){ // move from disk to mem
        maxMem = (struct Car*) realloc(maxMem, bytes);
        for (int i =amtOfCarsInArray; i< carLengthInMemArray; i++){
            if (numberOfCarsOnDisk > 0){
                numberOfCarsOnDisk--;
                FILE *oldFile;
                oldFile = fopen("extraCars.txt", "r");

                FILE *newFile;
                newFile = fopen("tmp.txt", "w");
                int lineNumber = 0;
                int len;
                char line[4096];

                if (oldFile != NULL) {
                    int k = 0;
                    while (fgets(line, sizeof line, oldFile)) {
                        len = strlen(line);
                        if (len && (line[len - 1] != '\n')) {} else {
                            lineNumber++;
                            if (k == 0) {
                                struct Car mc ={};
                                sscanf(line, "%d %s %s %hu %ld", &mc.uniqueID, mc.make, mc.model, &mc.year, &mc.price);
                                maxMem[carLengthInMemArray-1] = mc;
                                amtOfCarsInArray++;


                            } else {
                                fputs(line, newFile);
                            }
                        }
                        k++;
                    }
                } else {
                    printf("ERROR");
                }
                remove("extraCars.txt");
                rename("tmp.txt", "extraCars.txt");
                fclose(oldFile);
                fclose(newFile);
            }
        }

    }
    else if(carLengthInMemArray < oldCarlen) { //delet from mem pust to disk//////////////////////////////
        int k = oldCarlen - carLengthInMemArray; //amt of cars need to be deleted



        FILE *oldFile;
        oldFile = fopen("extraCars.txt", "r");

        FILE *newFile;
        newFile = fopen("tmp.txt", "w");
        int lineNumber = 0;
        int len;
        char line[4096];
        for (int i= carLengthInMemArray; i< oldCarlen; i++) {

            struct Car *mc = &maxMem[i];
            fprintf(newFile, "%d %s %s %hu %ld\n", mc->uniqueID, mc->make, mc->model, mc->year, mc->price);
            amtOfCarsInArray--;
            numberOfCarsOnDisk++;
        }
        if (oldFile != NULL) {

            while (fgets(line, sizeof line, oldFile)) {
                len = strlen(line);
                if (len && (line[len - 1] != '\n')) {}
                else {
                    lineNumber++;
                    fputs(line, newFile);

                }

            }
        } else {
            printf("ERROR");
        }

        remove("extraCars.txt");
        rename("tmp.txt", "extraCars.txt");
        fclose(oldFile);
        fclose(newFile);
        maxMem = (struct Car *) realloc(maxMem, bytes);
    }




}


/**
 *
 */
struct Car* getCarById(int id){
//travers memory
    for(int i = 0; i< carLengthInMemArray; i++){
        if( maxMem[i].uniqueID == id){
            struct Car myCar = maxMem[i];
            deleteCarById(id);
            addCar(myCar.make, myCar.model, myCar.year, myCar.price, myCar.uniqueID);
            return &myCar;
        }
    }


//traverse disc
    FILE * fp;
    char * line = NULL;
    size_t len = 0;
    ssize_t read;

    fp = fopen("extraCars.txt", "r");
    if (fp == NULL)
        exit(EXIT_FAILURE);

    while ((read = getline(&line, &len, fp)) != -1) {
        struct Car mc ={};
        sscanf(line, "%d %s %s %hu %ld", &mc.uniqueID, mc.make, mc.model, &mc.year, &mc.price);
        if (mc.uniqueID == id){
            deleteCarById(id);
            short year = mc.year;
            long price;                                         //problem
            addCar(mc.make, mc.model, mc.year, mc.price, mc.uniqueID);
            return &mc;
        }
    }

    fclose(fp);
    if (line)
        free(line);
    exit(EXIT_SUCCESS);


    return NULL;

}
/**
 *
 */
int deleteCarById(int id){
    //travers memory
    if(numberOfCarsOnDisk >0){
        numberOfCarsOnDisk--;
    }
    for(int i = 0; i< carLengthInMemArray; i++){
        if( maxMem[i].uniqueID == id){
            for (int j =i; j< carLengthInMemArray-1; j++){
                maxMem[i] = maxMem[i+1];
            }
            amtOfCarsInArray--;
            // delete first item from disc and put it in last spot in memory
            FILE *oldFile;
            oldFile = fopen("extraCars.txt", "r");

            FILE *newFile;
            newFile = fopen("tmp.txt", "w");
            int lineNumber = 0;
            int len;
            char line[4096];

            if (oldFile != NULL) {
                int k = 0;
                while (fgets(line, sizeof line, oldFile)) {
                    len = strlen(line);
                    if (len && (line[len - 1] != '\n')) {} else {
                        lineNumber++;
                        if (k == 0) {
                            struct Car mc ={};
                            sscanf(line, "%d %s %s %hu %ld", &mc.uniqueID, mc.make, mc.model, &mc.year, &mc.price);
                            maxMem[carLengthInMemArray-1] = mc;
                            amtOfCarsInArray++;


                        } else {
                            fputs(line, newFile);
                        }
                    }
                    k++;
                }
            } else {
                printf("ERROR");
            }
            remove("extraCars.txt");
            rename("tmp.txt", "extraCars.txt");
            fclose(oldFile);
            fclose(newFile);
            return 1; //true

        }
    }

//traverse disc
    FILE *oldFile;
    oldFile = fopen("extraCars.txt", "r");

    FILE *newFile;
    newFile = fopen("tmp.txt", "w");
    int lineNumber = 0;
    int len;
    char line[4096];

    if (oldFile != NULL) {
        while (fgets(line, sizeof line, oldFile)) {
            len = strlen(line);
            if (len && (line[len - 1] != '\n')) {} else {
                lineNumber++;
                struct Car mc ={};
                sscanf(line, "%d %s %s %hu %ld", &mc.uniqueID, mc.make, mc.model, &mc.year, &mc.price);
                if (mc.uniqueID == id) {
                    // Do nothing
                } else {
                    fputs(line, newFile);
                }
            }
        }
        remove("extraCars.txt");
        rename("tmp.txt", "extraCars.txt");
        fclose(oldFile);
        fclose(newFile);
        return 1; //true
    } else {
        printf("ERROR");
    }

    return 0; //false

}
int modifyCarById(int id, struct Car* myCar){
   int num = deleteCarById(id);
   struct Car car = *myCar;
   //struct Car car = {.make = *myCar->make, .model = *myCar->model, .year = myCar->year, .uniqueID = myCar->uniqueID};
    addCar(car.make, car.model, car.year, car.price, car.uniqueID);
    return num;
}
int getNumberOfCarsInMemory(){
    return amtOfCarsInArray;
}
int getAmountOfUsedMemory(){
    return (amtOfCarsInArray) * (sizeof(struct Car));
}
/**
 * done
 */
int getNumberOfCarsOnDisk(){
    return numberOfCarsOnDisk;
}
struct Car* getAllCarsInMemory(){
    return maxMem;
}
/* this function does NOT cause the cars on disk to displace those
* that were already in memory. It uses separate memory to load them
* and return them to the caller. THE CALLER MUST FREE THIS MEMORY
* WHEN FINISHED WITH THESE CARS.
*/
struct Car* getAllCarsOnDisk(){
    struct Car* sideMem = malloc(numberOfCarsOnDisk * sizeof(struct Car));
    FILE * fp;
    char * line = NULL;
    size_t len = 0;
    ssize_t read;
    int i =0;
    fp = fopen("extraCars.txt", "r");
    if (fp == NULL)
        exit(EXIT_FAILURE);

    while ((read = getline(&line, &len, fp)) != -1) {
        struct Car mc ={};
        sscanf(line, "%d %s %s %hu %ld", &mc.uniqueID, mc.make, mc.model, &mc.year, &mc.price);
        sideMem[i] = mc;
        i++;
    }

    fclose(fp);

    if (line)
        free(line);
    return sideMem;
    free(sideMem);
    exit(EXIT_SUCCESS);

}




