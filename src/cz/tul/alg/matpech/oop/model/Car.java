package cz.tul.alg.matpech.oop.model;

import java.util.Comparator;

public class Car implements Comparable<Car> {

    private String manufacturer;
    private String model;
    private Double fuelConsumption;
    private Double fuelTankSize;
    private Double range;
    private Double kmCosts;
    private int fuelType; //0 - benzin | 1 - nafta | 2 - ethanol | 3 - benzin100 | 4 - LPG
    private String spz;
    private Double finalKmPrice;
    private Double fullTankPrice;

    /**
     * Constructor of Car
     * @param manufacturer Manufacturer of car
     * @param model Model of car
     * @param fuelConsumption Fuel consumption of car in litres to 100Km
     * @param fuelTankSize Fuel tank size in litres
     * @param kmCosts Cost of one Km excluding fuel (service, amorization)
     * @param fuelType Fuel type of car (0 - gas, 1 - diesel, 2 - ethanol, 3 - high octane gas, 4 - LPG)
     * @param spz Registration plate of car
     */
    public Car(String manufacturer, String model, double fuelConsumption, double fuelTankSize, double kmCosts, int fuelType, String spz) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.fuelConsumption = fuelConsumption;
        this.fuelTankSize = fuelTankSize;
        this.kmCosts = kmCosts;
        this.fuelType = fuelType;
        this.spz = spz;
        this.range = (fuelTankSize/fuelConsumption)*100;
    }

    /**
     * ToString method
     * @return String
     */
    @Override
    public String toString() {
        return "Car{" +
                "manufacturer='" + manufacturer + '\'' +
                ", model='" + model + '\'' +
                ", fuelConsumption=" + fuelConsumption +
                ", fuelTankSize=" + fuelTankSize +
                ", range=" + range +
                ", kmCosts=" + kmCosts +
                ", fuelType=" + fuelType +
                ", spz='" + spz + '\'' +
                ", finalKmPrice=" + finalKmPrice +
                ", fullTankPrice=" + fullTankPrice +
                '}';
    }

    /**
     * Manufacturer getter
     * @return String manufacturer
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * Model getter
     * @return String model
     */
    public String getModel() {
        return model;
    }

    /**
     * FuelConsumption getter
     * @return Double fuelConsumption
     */
    public Double getFuelConsumption() {
        return fuelConsumption;
    }

    /**
     * FuelTankSize getter
     * @return Double FuelTankSize
     */
    public Double getFuelTankSize() {
        return fuelTankSize;
    }

    /**
     * KmCosts getter
     * @return double kmCosts
     */
    public Double getKmCosts() {
        return kmCosts;
    }

    /**
     * FuelType getter
     * @return int fuelType
     */
    public int getFuelType() {
        return fuelType;
    }

    /**
     * Spz getter
     * @return String spz
     */
    public String getSpz() {
        return spz;
    }

    /**
     * FinalKmPrice getter
     * @return Double finalKmPrice
     */
    public Double getFinalKmPrice() {
        return finalKmPrice;
    }

    /**
     * Range getter
     * @return Double range
     */
    public Double getRange() {
        return range;
    }

    /**
     * FullTankPrice getter
     * @return Double fullTankPrice
     */
    public Double getFullTankPrice() {
        return fullTankPrice;
    }

    /**
     * FinalKmPrice setter
     * @param finalKmPrice to be set
     */
    public void setFinalKmPrice(double finalKmPrice) {
        this.finalKmPrice = finalKmPrice;
    }

    /**
     * FullTankPrice setter
     * @param fullTankPrice to be set
     */
    public void setFullTankPrice(Double fullTankPrice) {
        this.fullTankPrice = fullTankPrice;
    }

    /**
     * Comparator, comparing by price of one km
     */
    public static final Comparator<Car> COMP_PRICE = (Car c1, Car c2) -> c1.finalKmPrice.compareTo(c2.finalKmPrice);

    /**
     * Comparator, comparing by range
     */
    public static final Comparator<Car> COMP_RANGE = (Car c1, Car c2) -> c1.range.compareTo(c2.range);

    /**
     * compareTo which implements iComparable, compares by price of one km with fuel excluded
     * @param o Car to be compared
     * @return int of which car is cheaper to drive
     */
    @Override
    public int compareTo(Car o) {
        return this.kmCosts.compareTo(o.kmCosts);
    }
}
