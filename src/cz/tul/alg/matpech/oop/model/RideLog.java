package cz.tul.alg.matpech.oop.model;


import cz.tul.alg.matpech.oop.utils.RideLogInterface;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

public class RideLog implements RideLogInterface {
    private List<Car> cars;

    public RideLog(){
        cars = new ArrayList<>();
    }
    private String[] fuelTypes = new String[]{"Benzín", "Nafta", "Ethanol", "Benzín 100", "LPG"};
    private double[] prices = new double[5];

    /**
     * Adds new object type Car to arraylist of others object type Car
     * @param car New Car type object
     */
    @Override
    public void addCar(Car car)
    {
        cars.add(car);
    }

    /**
     * Allows to change prices of fuel
     * @param index Index where is fuel indexed in array of all fuel prices
     * @param price New price of fuel
     */
    @Override
    public void changePrices(int index, double price)
    {
        prices[index] = price;
    }

    /**
     * Goes through all lines of given file and transfers them to Car type object
     * then adds created Car object to arraylist of others Car objects
     * @param carsFile Path to .csv file where are cars stored
     * @throws IOException Compulsory exception
     */
    @Override
    public void loadCars(String carsFile) throws FileNotFoundException, IOException {
        cars = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(new File(carsFile))))
        {
            String line;
            Car c;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String manufacturer = parts[0];
                String model = parts[1];
                double consumption = Double.parseDouble(parts[2]);
                double fuelTankSize = Double.parseDouble(parts[3]);
                double kmCosts = Double.parseDouble(parts[4]);
                int fuelType = Integer.parseInt(parts[5]);
                if(parts[6].matches("^[\\d][ASULKHEPCJBMTZ][^GOQW](4*[\\d])"))
                {
                    String spz = parts[6]; //přidat regex
                    c = new Car(manufacturer, model, consumption, fuelTankSize, kmCosts, fuelType, spz);
                    cars.add(c);
                }
            }
        }
    }

    /**
     * Loads .csv file, reads its only line with prices of fuel, then adds them to array of prices
     * @param priceFile Path to .csv file where are prices stored
     * @throws IOException Compulsory exception
     */
    @Override
    public void loadPrices(String priceFile) throws FileNotFoundException, IOException {
        try(BufferedReader br = new BufferedReader(new FileReader(new File(priceFile))))
        {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                for(int i = 0; i < 5; i++)
                {
                    prices[i] = Integer.parseInt(parts[i]);
                }
            }
        }
    }

    /**
     * Calls methods from Logic to calculate price of full tank and price of one km for every car in arraylist
     */
    @Override
    public void calculateAll()
    {
        for (Car car : cars)
        {
            car.setFinalKmPrice(kmCosts(car));
            car.setFullTankPrice(fullTankPrice(car));
        }
    }

    /**
     * Sends email with calculations for one car
     * @param to Email address of recipient
     * @param car Car type object used for calculations
     */
    @Override
    public void sendMail(String to, Car car)
    {
        car.setFinalKmPrice(kmCosts(car));
        car.setFullTankPrice(fullTankPrice(car));
        final String username = "pechoutprgtest@gmail.com";
        final String password = "Heslo123";
        String fromEmail = "pechoutprgtest@gmail.com";
        String toEmail = to;

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(username,password);
            }
        });
        StringBuilder text = new StringBuilder();
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(fromEmail));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

            // Set Subject: header field
            message.setSubject("Report nákladů jízdy");

            text.append("Náklady jízdy vozem " + car.getManufacturer() + " " + car.getModel() + " s RZ - " + car.getSpz());
            text.append("\n");
            text.append("Palivo - " + fuelTypeToString(car));
            text.append("\n");
            text.append("Cena paliva - " + prices[car.getFuelType()] + "Kč/L");
            text.append("\n");
            text.append("Cena 1 km - " + car.getFinalKmPrice() + " Kč");
            text.append("\n");
            text.append("Cena plné nádrže - " + car.getFullTankPrice() + " Kč");
            LocalDateTime dateTime = LocalDateTime.now();

            text.append("\n");
            text.append("----------------");
            text.append("\n");
            text.append("Report vytvořen " + (dateTime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))) + " " + (dateTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM))));
            message.setText(text.toString());
            // Send message
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    /**
     * Saves calculated prices with every car in arraylist to .txt file
     * @param calculationsFile Path to save file
     * @throws IOException Compulsory exception
     */
    @Override
    public void saveCalculations(String calculationsFile) throws IOException
    {
        StringBuilder str = new StringBuilder();
        try(PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(calculationsFile, false)))){ //true (append) - zapise data na konec souboru
            pw.println(String.format("%s %s %s %s %s %s","Značka", "Model", "SPZ", "Cena na Km", "Cena plné nádrže", "Typ paliva")); //zahlavi
            String s;
            for (Car car : cars) {
                s = String.format("%s %s %s %s %s %s", car.getManufacturer(), car.getModel(), car.getSpz(), car.getFinalKmPrice(), car.getFullTankPrice(), fuelTypeToString(car));
                pw.println(s);
            }
        }
    }

    /**
     * Method returning price array in String type
     * @return Price array in String type
     */
    @Override
    public String pricesToString()
    {
        return Arrays.toString(prices);
    }

    /**
     * Returns car from arraylist based on spz (registration plate)
     * @param spz String used to search for exact car
     * @return return found car
     */
    @Override
    public Car findCar(String spz)
    {
        for(Car car : cars)
        {
            if(car.getSpz().equals(spz))
            {
                return car;
            }
        }
        return null;
    }

    /**
     * Returns price of fuel at given index
     * @param index index of fuel
     * @return returns price in double type
     */
    @Override
    public double getPricesIndex(int index)
    {
        return prices[index];
    }

    /**
     * Returns name of fuel based on given index
     * @param index index used to find the fuel
     * @return fuel in String type
     */
    @Override
    public String getFuelType(int index)
    {
        return fuelTypes[index];
    }

    /**
     * Returns fuel type for given Car object
     * @param car Car which fuel will be returned
     * @return Fuell of given car in String type
     */
    @Override
    public String fuelTypeToString(Car car)
    {
        int type = car.getFuelType();
        switch(type)
        {
            case 0:
                return "Benzín";
            case 1:
                return "Nafta";
            case 2:
                return "Ethanol";
            case 3:
                return "Benzín 100oct";
            case 4:
                return "LPG";
            default:
                return null;
        }


    }

    /**
     * Calculates cost of one Km for given car
     * @param car Car which price of Km we want to calculate
     * @return Price of one Km calculated in double type
     */
    @Override
    public double kmCosts(Car car)
    {
        double kmCosts = 0;
        kmCosts = (car.getFuelConsumption()*prices[car.getFuelType()]) + (100*car.getKmCosts());
        return kmCosts/100;
    }

    /**
     * Calculates price of full tank for given car
     * @param car Car which price of full tank we want to calculate
     * @return price of full tank in double type
     */
    @Override
    public double fullTankPrice(Car car)
    {
        double fullTankPrice = 0;
        fullTankPrice = car.getFuelTankSize()*prices[car.getFuelType()];
        return fullTankPrice;
    }

    /**
     * Transforms arraylist of cars to String form using StringBuilder
     * @return Every car toString method appended to one StringBuilder converted to String in the end
     */
    @Override
    public String carsToString()
    {
        StringBuilder str = new StringBuilder();
        for (Car car : cars) {
            str = str.append(car.toString());
            str = str.append("\n");
        }
        return str.toString();
    }

    /**
     * Sorts arraylist of cars by price of one Km from lowest to highest
     * @return Sorted arraylist in String
     */
    @Override
    public String sortByPrice()
    {
        Collections.sort(cars, Car.COMP_PRICE);
        String str = carsToString();
        return str;
    }

    /**
     * Sorts arraylist of cars by it's range from lowest to highest
     * @return Sorted arraylist in String
     */
    @Override
    public String sortByRange()
    {
        Collections.sort(cars, Car.COMP_RANGE);
        return carsToString();
    }

    /**
     * Sorts arraylist of cars by price of one Km excluding fuel from lowest to highest
     * @return Sorted arraylist in String
     */
    @Override
    public String sortByDryPrice()
    {
        Collections.sort(cars);
        return carsToString();
    }

    /**
     * Method used to create .bin file with prices of fuel
     * @param binFile savefile name
     */
    @Override
    public void savePricesToBin(String binFile) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(binFile))) {
            for (Double price : prices) {
                dos.writeDouble(price);
            }
        }
    }

    /**
     * Reading prices from binary file
     * @param binFile name of file
     * @throws IOException compulsory
     */
    @Override
    public void readPricesFromBin(String binFile) throws FileNotFoundException, IOException {
        int i = 0;
        try(DataInputStream din = (new DataInputStream(new FileInputStream(new File(binFile)))))
        {
            boolean end = false;
            while(!end){
                try {
                    double price = din.readDouble();
                    prices[i] = price;
                    i++;
                } catch(EOFException e){
                    end = true;
                }
            }
        }
    }

    /**
     * Saveing calculations to binary file
     * @param binFile filename
     */
    @Override
    public void saveCalcToBin(String binFile) throws IOException
    {
        calculateAll();
        int length = 0;
        try(DataOutputStream dos = new DataOutputStream(new FileOutputStream(binFile)))
        {
            for(Car car : cars)
            {
                length = car.getManufacturer().length();
                dos.writeInt(length);
                for(int i = 0; i<length; i++)
                {
                    dos.writeChar(car.getManufacturer().charAt(i));
                }
                length = car.getModel().length();
                dos.writeInt(length);
                for(int i = 0; i<length; i++)
                {
                    dos.writeChar(car.getModel().charAt(i));
                }
                dos.writeDouble(car.getRange());
            }
        }
    }

    public String readCalcFromBin(String binFile) throws FileNotFoundException, IOException {
        int length = 0;
        StringBuilder manufacturer = new StringBuilder();
        StringBuilder model = new StringBuilder();
        double range;
        StringBuilder result = new StringBuilder();
        try(DataInputStream din = (new DataInputStream(new FileInputStream(new File(binFile)))))
        {
            boolean end = false;
            while(!end){
                try {
                    length = din.readInt();
                    manufacturer.setLength(0);
                    for(int i = 0; i < length; i++)
                    {
                        manufacturer.append(din.readChar());
                    }
                    length = din.readInt();
                    model.setLength(0);
                    for(int i = 0; i < length; i++)
                    {
                        model.append(din.readChar());
                    }
                    range = din.readDouble();
                    result.append(manufacturer);
                    result.append(" ");
                    result.append(model);
                    result.append(" ");
                    result.append(range);
                    result.append("\n");
                } catch(EOFException e){
                    end = true;
                }
            }
        }
        return result.toString();
    }

}
