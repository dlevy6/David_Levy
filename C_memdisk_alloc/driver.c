#include <stdio.h>
#include "dataStore.h"

void printPointerCar(struct Car* car, int amt){

    for (int i =0 ; i< amt; i++){
        struct Car cr = car[i];
        printf("%d %s %s %d %ld \n", car[i].uniqueID, car[i].make, car[i].model, car[i].year, car[i].price);
    }

}

int main() {
    remove("extraCars.txt");



    setMaxMemory(100);


    addCar("toyota", "camry", 1995, 1000, 1812);
    addCar("farrari", "enzo", 1995, 3000, 1813);
    addCar("porsche", "911", 1995, 4000, 1814);
    addCar("honda", "911", 1995, 1000, 1815);
    addCar("porsche", "918", 1995, 4000, 1816);// 5 cars added

    printf("\nnumber of cars in memory = %d", getNumberOfCarsInMemory());
    printf("\nnumber of cars on disk = %d\n", getNumberOfCarsOnDisk());


    printf("\nall cars in memory: \n");
    printPointerCar(getAllCarsInMemory(), getNumberOfCarsInMemory());
    printf("\nall cars in disk: \n");
    printPointerCar(getAllCarsOnDisk(), getNumberOfCarsOnDisk());




    printf("\n\nchecking adding memory \n");
    setMaxMemory(150);
    printf("number of cars in memory = %d", getNumberOfCarsInMemory());
    printf("\nnumber of cars on disk = %d\n", getNumberOfCarsOnDisk());
    printf("\nall cars in memory: \n");
    printPointerCar(getAllCarsInMemory(), getNumberOfCarsInMemory());
    printf("\nall cars in disk: \n");
    printPointerCar(getAllCarsOnDisk(), getNumberOfCarsOnDisk());

    printf("\n\nchecking cutting memory \n");
    setMaxMemory(50);
    printf("number of cars in memory = %d", getNumberOfCarsInMemory());
    printf("\nnumber of cars on disk = %d\n", getNumberOfCarsOnDisk());
    printf("\nall cars in memory: \n");
    printPointerCar(getAllCarsInMemory(), getNumberOfCarsInMemory());
    printf("\nall cars in disk: \n");
    printPointerCar(getAllCarsOnDisk(), getNumberOfCarsOnDisk());

    printf("\nchecking methods\n");

    setMaxMemory(100);
    printf("\nnumber of cars in memory = %d", getNumberOfCarsInMemory());
    printf("\nnumber of cars on disk = %d\n", getNumberOfCarsOnDisk());

    printf("\nget car by id 1813  price = %ld\n", getCarById(1813)->price);

    printf("\nall cars in memory: \n");
    printPointerCar(getAllCarsInMemory(), getNumberOfCarsInMemory());
    printf("\nall cars in disk: \n");
    printPointerCar(getAllCarsOnDisk(), getNumberOfCarsOnDisk());


    printf("\namt of used memory: %d\n", getAmountOfUsedMemory());

    deleteCarById(1814);
    printf("\ndeleted 1814\n");
    printf("number of cars in memory = %d", getNumberOfCarsInMemory());
    printf("\nnumber of cars on disk = %d\n", getNumberOfCarsOnDisk());
    printf("\nall cars in memory: \n");
    printPointerCar(getAllCarsInMemory(), getNumberOfCarsInMemory());
    printf("\nall cars in disk: \n");
    printPointerCar(getAllCarsOnDisk(), getNumberOfCarsOnDisk());

    struct Car sam = {.uniqueID =14, .make = "bugy", .model = "hazzer", .year = 1712, .price = 12};
    printf("\nmodifying 1812 :%d\n", modifyCarById(1812, &sam));
    printf("\nall cars in memory: \n");
    printPointerCar(getAllCarsInMemory(), getNumberOfCarsInMemory());
    printf("\nall cars in disk: \n");
    printPointerCar(getAllCarsOnDisk(), getNumberOfCarsOnDisk());


}

//int addCar(char* make, char* model, short year, long price, int uniqueID);/
//int setMaxMemory(size_t bytes);/
//struct Car* getCarById(int id);/
//int deleteCarById(int id);/

//int getNumberOfCarsInMemory();/
//int getAmountOfUsedMemory();/
//int getNumberOfCarsOnDisk();/
//struct Car* getAllCarsInMemory();/
// this function does NOT cause the cars on disk to displace those
//* that were already in memory. It uses separate memory to load them
//* and return them to the caller. THE CALLER MUST FREE THIS MEMORY
//* WHEN FINISHED WITH THESE CARS.
//*/
//struct Car* getAllCarsOnDisk();/
//int modifyCarById(int id, struct Car* myCar);