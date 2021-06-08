package cz.tul.alg.matpech.oop.utils;

import cz.tul.alg.matpech.oop.model.Car;

import java.io.IOException;

public interface RideLogInterface {
    void addCar(Car car);

    void changePrices(int index, double price);

    void loadCars(String carsFile) throws IOException;

    void loadPrices(String priceFile) throws IOException;

    void calculateAll();

    void sendMail(String to, Car car);

    void saveCalculations(String calculationsFile) throws IOException;

    String pricesToString();

    Car findCar(String spz);

    double getPricesIndex(int index);

    String getFuelType(int index);

    String fuelTypeToString(Car car);

    double kmCosts(Car car);

    double fullTankPrice(Car car);

    String carsToString();

    String sortByPrice();

    String sortByRange();

    String sortByDryPrice();

    void savePricesToBin(String binFile);

    void readPricesFromBin(String binFile) throws IOException;

    void saveCalcToBin(String binFile);
}
