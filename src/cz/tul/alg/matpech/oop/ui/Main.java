package cz.tul.alg.matpech.oop.ui;

import cz.tul.alg.matpech.oop.model.Car;
import cz.tul.alg.matpech.oop.model.RideLog;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static RideLog rideLog = new RideLog();
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
	// write your code here

        boolean cond = true;
        String choice;


        while(cond) {
            viewMenu();
            choice = sc.next();
            if(choice.equals("1"))
            {
                dataLoad();

            }
            else if(choice.equals("2"))
            {
                addCar();
            }
            else if(choice.equals("3"))
            {
                printCars();
            }
            else if(choice.equals("4"))
            {
                printPrices();
            }
            else if(choice.equals("5"))
            {
                saveToFile();
                try {
                    rideLog.saveCalcToBin("./data/testbin.bin");
                    System.out.println(rideLog.readCalcFromBin("./data/testbin.bin"));
                }
                catch(FileNotFoundException ex)
                {
                    System.out.println("Soubor nenalezen");
                }
                catch(IOException ex)
                {
                    System.out.println("Nastala chyba");
                }
            }
            else if(choice.equals("6"))
            {
                changePrices();
            }
            else if(choice.equals("7"))
            {
                mailSend();

            }
            else if(choice.equals("8"))
            {
                sortBy();
            }
            else if(choice.equals("9"))
            {
                System.out.println("Konec programu");
                cond = false;
                System.out.println("---------------------");
            }
            else
            {
                System.out.println("Neplatn?? volba");
                System.out.println("---------------------");
            }
        }
    }


    public static void viewMenu()
    {
        System.out.println("V??po??et n??klad?? j??zdy");
        System.out.println("---------------------");
        System.out.println("1. na??ten?? aut a cen paliv ze soubor??");
        System.out.println("2. p??id??n?? nov??ho auta");
        System.out.println("3. vyps??n?? aktu??ln??ch aut");
        System.out.println("4. vyps??n?? ceny paliv");
        System.out.println("5. v??po??et ceny n??klad?? na??ten??ch aut a ulo??en?? do souboru");
        System.out.println("6. zm??na ceny paliva");
        System.out.println("7. individu??ln?? v??po??et zaslan?? na email");
        System.out.println("8. Porovn??v??n?? zadan??ch aut");
        System.out.println("9. konec");
        System.out.println("---------------------");
    }
    public static void dataLoad()
    {
        System.out.println("Zadej .csv soubor s auty :");
        try {
            rideLog.loadCars("./data/" + sc.next()+".csv");
        }
        catch(FileNotFoundException ex)
        {
            System.out.println("Zadan?? soubor neexistuje");
        }
        catch(IOException ex)
        {
            System.out.println("Nastala chyba");
        }
        System.out.println("Zadej .bin soubor s cenou paliv");
        try {
            //rideLog.loadPrices("./data/" + sc.next()+".csv");
            rideLog.readPricesFromBin("./data/" + sc.next() +".bin");
        }
        catch(FileNotFoundException ex)
        {
            System.out.println("Zadan?? soubor neexistuje");
        }
        catch (IOException ex)
        {
            System.out.println("Nastala chyba");
        }
        System.out.println("---------------------");
    }
    public static void addCar()
    {
        String manufacturer;
        String model;
        Double consumption;
        Double fuelTankSize;
        Double kmCosts;
        int fuelType;
        String spz;
        Car car;
        try {
            System.out.println("Zadej v??robce vozu");
            manufacturer = sc.next();
            System.out.println("Zadej model vozu");
            model = sc.next();
            System.out.println("Zadej spot??ebu paliva na 100km");
            consumption = sc.nextDouble();
            System.out.println("Zadej velikost n??dr??e v litrech");
            fuelTankSize = sc.nextDouble();
            System.out.println("Zadej n??klady na jeden kilometr vyjma PHM(amortizace, servis,...)");
            kmCosts = sc.nextDouble();
            System.out.println("Zadej palivo");
            System.out.println("(0 - benzin | 1 - nafta | 2 - ethanol | 3 - benzin100 | 4 - LPG)");
            fuelType = sc.nextInt();
            System.out.println("Zadej SPZ");
            spz = sc.next();
            car = new Car(manufacturer, model, consumption, fuelTankSize, kmCosts, fuelType, spz);
            rideLog.addCar(car);
        }
        catch(InputMismatchException ex)
        {
            sc.nextLine();
            System.out.println("??patn?? vstup");
        }
        System.out.println("---------------------");
    }
    public static void printCars()
    {
        if(rideLog.carsToString().length() == 0)
        {
            System.out.println("Je??t?? nejsou na??ten?? ????dn?? auta");
        }
        else {
            System.out.println(rideLog.carsToString());
            System.out.println("---------------------");
        }
    }
    public static void printPrices()
    {
        StringBuilder str = new StringBuilder();
        if(rideLog.pricesToString().equals("[0.0, 0.0, 0.0, 0.0, 0.0]"))
        {
            System.out.println("Je??t?? nejsou zad??ny ceny paliv");
        }
        else {
            for (int i = 0; i < 5; i++) {
                str.append(rideLog.getFuelType(i));
                str.append(" " + rideLog.getPricesIndex(i) + "K??/L");
                str.append("\n");
            }
            System.out.println(str.toString());
            str.setLength(0);
        }
        System.out.println("---------------------");
    }
    public static void saveToFile() throws IOException {
        sc.nextLine();
        String resultFile;
        String fileChoice;
        if(rideLog.carsToString().equals("") || rideLog.pricesToString().equals("[0.0, 0.0, 0.0, 0.0, 0.0]"))
        {
            System.out.println("Nekompletn?? data k v??po??tu");
        }
        else {
            System.out.println("0 - .txt soubor | 1 - .bin soubor");
            fileChoice = sc.nextLine();
            if(fileChoice.equals("0")) {
                System.out.println("Zadej soubor pro z??pis, bez koncovky");
                resultFile = sc.next();
                try {
                    rideLog.calculateAll();
                    rideLog.saveCalculations("./data/" + resultFile + ".txt");
                }
                catch (IOException ex)
                {
                    System.out.println("Nastala chyba");
                }
            }
            else if(fileChoice.equals("1"))
            {
                System.out.println("Zadej soubor pro z??pis, bez koncovky");
                resultFile = sc.next();
                rideLog.calculateAll();
                rideLog.saveCalcToBin("./data/" + resultFile + ".bin");
            }
            else
            {
                System.out.println("Neplatn?? volba");
            }
        }
        System.out.println("---------------------");
    }
    public static void changePrices()
    {
        int fuelChoice;
        double fuelPrice;
        if(rideLog.pricesToString().equals("[0.0, 0.0, 0.0, 0.0, 0.0]"))
        {
            System.out.println("Je??t?? nejsou zad??ny ceny paliv");
        }
        else {
            System.out.println("Zadej palivo jeho?? cenu chce?? zm??nit");
            System.out.println("(0 - benzin | 1 - nafta | 2 - ethanol | 3 - benzin100 | 4 - LPG)");
            fuelChoice = sc.nextInt();
            if (fuelChoice < 0 || fuelChoice > 4) {
                System.out.println("Neplatn?? index paliva");
            } else {
                System.out.println("Aktu??ln?? cena zvolen??ho paliva");
                System.out.println(rideLog.getPricesIndex(fuelChoice) + " K??/L");
                System.out.println("Zadej novou cenu");
                try {
                    fuelPrice = sc.nextDouble();
                    rideLog.changePrices(fuelChoice, fuelPrice);
                } catch (InputMismatchException ex) {
                    sc.nextLine();
                    System.out.println("??patn?? vstup");
                }
            }
        }
        System.out.println("---------------------");
    }
    public static void mailSend()
    {
        String manufacturer;
        String model;
        Double consumption;
        Double fuelTankSize;
        Double kmCosts;
        int fuelType;
        String spz;
        Car car;
        String email;
        try {
            System.out.println("Zadej v??robce vozu");
            manufacturer = sc.next();
            System.out.println("Zadej model vozu");
            model = sc.next();
            System.out.println("Zadej spot??ebu paliva na 100km");
            consumption = sc.nextDouble();
            System.out.println("Zadej velikost n??dr??e v litrech");
            fuelTankSize = sc.nextDouble();
            System.out.println("Zadej n??klady na jeden kilometr vyjma PHM(amortizace, servis,...)");
            kmCosts = sc.nextDouble();
            System.out.println("Zadej palivo");
            System.out.println("(0 - benzin | 1 - nafta | 2 - ethanol | 3 - benzin100 | 4 - LPG)");
            fuelType = sc.nextInt();
            System.out.println("Zadej SPZ");
            spz = sc.next();
            car = new Car(manufacturer, model, consumption, fuelTankSize, kmCosts, fuelType, spz);
            System.out.println("Zadej email pro zasl??n?? reportu");
            email = sc.next();
            rideLog.sendMail(email, car);
        }
        catch(InputMismatchException ex)
        {
            sc.nextLine();
            System.out.println("??patn?? vstup");
        }
        System.out.println("---------------------");
    }
    public static void sortBy()
    {
        String compChoice;
        if(rideLog.carsToString().equals(""))
        {
            System.out.println("Nekompletn?? data k v??po??tu");
        }
        else
        {
            System.out.println("0 - Dle ceny na 1km | 1 - dle dojezdu na plnou n??dr??");
            compChoice = sc.next();
            rideLog.calculateAll();

            if(compChoice.equals("0")) {
                System.out.println(rideLog.sortByPrice());
            }
            else if(compChoice.equals("1"))
            {
                System.out.println(rideLog.sortByRange());
            }
            else
            {
                System.out.println("??patn?? vstup");
            }
        }
    }
}
