package cz.tul.alg.matpech.oop.ui;

import cz.tul.alg.matpech.oop.model.Car;
import cz.tul.alg.matpech.oop.model.Logic;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here
        Scanner sc = new Scanner(System.in);
        Logic logic = new Logic();
        boolean cond = true;
        StringBuilder str = new StringBuilder();
        String email;
        String resultFile;
        String compChoice;
        //if vars
        int fuelChoice;
        double fuelPrice;
        //přídávání auta vars
        String choice;
        String manufacturer;
        String model;
        Double consumption;
        Double fuelTankSize;
        Double kmCosts;
        int fuelType;
        String spz;
        Car car;

        while(cond) {
            System.out.println("Výpočet nákladů jízdy");
            System.out.println("---------------------");
            System.out.println("1. načtení aut a cen paliv ze souborů");
            System.out.println("2. přidání nového auta");
            System.out.println("3. vypsání aktuálních aut");
            System.out.println("4. vypsání ceny paliv");
            System.out.println("5. výpočet ceny nákladů načtených aut a uložení do souboru");
            System.out.println("6. změna ceny paliva");
            System.out.println("7. individuální výpočet zaslaný na email");
            System.out.println("8. Porovnávání zadaných aut");
            System.out.println("9. konec");
            System.out.println("---------------------");
            choice = sc.next();
            if(choice.equals("1"))
            {
                System.out.println("Zadej .csv soubor s auty :");
                try {
                    logic.loadCars("./data/" + sc.next()+".csv");
                }
                catch(FileNotFoundException ex)
                {
                    System.out.println("Zadaný soubor neexistuje");
                }
                System.out.println("Zadej .csv soubor s cenou paliv");
                try {
                    logic.loadPrices("./data/" + sc.next()+".csv");
                }
                catch(FileNotFoundException ex)
                {
                    System.out.println("Zadaný soubor neexistuje");
                }
                System.out.println("---------------------");
            }
            else if(choice.equals("2"))
            {
                try {
                    System.out.println("Zadej výrobce vozu");
                    manufacturer = sc.next();
                    System.out.println("Zadej model vozu");
                    model = sc.next();
                    System.out.println("Zadej spotřebu paliva na 100km");
                    consumption = sc.nextDouble();
                    System.out.println("Zadej velikost nádrže v litrech");
                    fuelTankSize = sc.nextDouble();
                    System.out.println("Zadej náklady na jeden kilometr vyjma PHM(amortizace, servis,...)");
                    kmCosts = sc.nextDouble();
                    System.out.println("Zadej palivo");
                    System.out.println("(0 - benzin | 1 - nafta | 2 - ethanol | 3 - benzin100 | 4 - LPG)");
                    fuelType = sc.nextInt();
                    System.out.println("Zadej SPZ");
                    spz = sc.next();
                    car = new Car(manufacturer, model, consumption, fuelTankSize, kmCosts, fuelType, spz);
                    logic.addCar(car);
                }
                catch(InputMismatchException ex)
                {
                    sc.nextLine();
                    System.out.println("Špatný vstup");
                }
                System.out.println("---------------------");
            }
            else if(choice.equals("3"))
            {
                if(logic.carsToString().length() == 0)
                {
                    System.out.println("Ještě nejsou načtená žádná auta");
                }
                else {
                    System.out.println(logic.carsToString());
                    System.out.println("---------------------");
                }
            }
            else if(choice.equals("4"))
            {
                if(logic.pricesToString().equals("[0.0, 0.0, 0.0, 0.0, 0.0]"))
                {
                    System.out.println("Ještě nejsou zadány ceny paliv");
                }
                else {
                    for (int i = 0; i < 5; i++) {
                        str.append(logic.getFuelType(i));
                        str.append(" " + logic.getPricesIndex(i) + "Kč/L");
                        str.append("\n");
                    }
                    System.out.println(str.toString());
                    str.setLength(0);
                }
                System.out.println("---------------------");
            }
            else if(choice.equals("5"))
            {
                if(logic.carsToString().equals("") || logic.pricesToString().equals("[0.0, 0.0, 0.0, 0.0, 0.0]"))
                {
                    System.out.println("Nekompletní data k výpočtu");
                }
                else {
                    System.out.println("Zadej soubor pro zápis, bez koncovky");
                    resultFile = sc.next();
                    logic.calculateAll();
                    logic.saveCalculations("./data/" + resultFile + ".txt");
                }
                System.out.println("---------------------");
            }
            else if(choice.equals("6"))
            {
                if(logic.pricesToString().equals("[0.0, 0.0, 0.0, 0.0, 0.0]"))
                {
                    System.out.println("Ještě nejsou zadány ceny paliv");
                }
                else {
                    System.out.println("Zadej palivo jehož cenu chceš změnit");
                    System.out.println("(0 - benzin | 1 - nafta | 2 - ethanol | 3 - benzin100 | 4 - LPG)");
                    fuelChoice = sc.nextInt();
                    if (fuelChoice < 0 || fuelChoice > 4) {
                        System.out.println("Neplatný index paliva");
                    } else {
                        System.out.println("Aktuální cena zvoleného paliva");
                        System.out.println(logic.getPricesIndex(fuelChoice) + " Kč/L");
                        System.out.println("Zadej novou cenu");
                        try {
                            fuelPrice = sc.nextDouble();
                            logic.changePrices(fuelChoice, fuelPrice);
                        } catch (InputMismatchException ex) {
                            sc.nextLine();
                            System.out.println("Špatný vstup");
                        }
                    }
                }
                System.out.println("---------------------");
            }
            else if(choice.equals("7"))
            {
                try {
                    System.out.println("Zadej výrobce vozu");
                    manufacturer = sc.next();
                    System.out.println("Zadej model vozu");
                    model = sc.next();
                    System.out.println("Zadej spotřebu paliva na 100km");
                    consumption = sc.nextDouble();
                    System.out.println("Zadej velikost nádrže v litrech");
                    fuelTankSize = sc.nextDouble();
                    System.out.println("Zadej náklady na jeden kilometr vyjma PHM(amortizace, servis,...)");
                    kmCosts = sc.nextDouble();
                    System.out.println("Zadej palivo");
                    System.out.println("(0 - benzin | 1 - nafta | 2 - ethanol | 3 - benzin100 | 4 - LPG)");
                    fuelType = sc.nextInt();
                    System.out.println("Zadej SPZ");
                    spz = sc.next();
                    car = new Car(manufacturer, model, consumption, fuelTankSize, kmCosts, fuelType, spz);
                    System.out.println("Zadej email pro zaslání reportu");
                    email = sc.next();
                    logic.sendMail(email, car);
                }
                catch(InputMismatchException ex)
                {
                    sc.nextLine();
                    System.out.println("Špatný vstup");
                }
                System.out.println("---------------------");
            }
            else if(choice.equals("8"))
            {
                if(logic.carsToString().equals(""))
                {
                    System.out.println("Nekompletní data k výpočtu");
                }
                else
                {
                    System.out.println("0 - Dle ceny na 1km | 1 - dle dojezdu na plnou nádrž");
                    compChoice = sc.next();
                    logic.calculateAll();

                        if(compChoice.equals("0")) {
                            System.out.println(logic.sortByPrice());
                        }
                        else if(compChoice.equals("1"))
                        {
                            System.out.println(logic.sortByRange());
                        }
                        else
                        {
                            System.out.println("Špatný vstup");
                        }
                }



            }
            else if(choice.equals("9"))
            {
                System.out.println("Konec programu");
                cond = false;
                System.out.println("---------------------");
            }
            else
            {
                System.out.println("Neplatná volba");
                System.out.println("---------------------");
            }
        }
    }
}
