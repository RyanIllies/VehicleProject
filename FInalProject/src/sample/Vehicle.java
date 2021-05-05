package sample;

import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class Vehicle {

    
    private int vehicleID;
    private static int nextVehicleID = 1000;
    private int miles;
    private String make;
    private String vClass;
    private String fuelType;
    private String vUsage;
    private LocalDate dateAquired;
    private String vColor;

    private SimpleStringProperty simpleID;
    private SimpleStringProperty simpleMiles;
    private SimpleStringProperty simpleMake;
    private SimpleStringProperty simpleClass;
    private SimpleStringProperty simpleFuelType;
    private SimpleStringProperty simpleUsage;
    private SimpleStringProperty simpleDate;
    private SimpleStringProperty simpleColor;

    public Vehicle(String tmpClass,String tmpmake,int tmpmiles,String tmpfuel, String usage,LocalDate aquired,String color){
        miles = tmpmiles;
        make = tmpmake;
        vClass = tmpClass;
        fuelType = tmpfuel;
        vUsage = usage;
        dateAquired = aquired;
        vColor = color;
        setVehicleID(nextVehicleID);
        nextVehicleID = nextVehicleID+79;

        setSimpleID(vehicleID);
        setSimpleMiles(miles);
        setSimpleMake(make);
        setSimpleClass(vClass);
        setSimpleFuelType(fuelType);
        setSimpleUsage(vUsage);
        setSimpleColor(vColor);
        setSimpleDate(dateAquired);
    }

    public Vehicle(int tmpid,String tmpClass,String tmpmake,int tmpmiles,String tmpfuel, String usage,LocalDate aquired,String color){
        miles = tmpmiles;
        make = tmpmake;
        vClass = tmpClass;
        fuelType = tmpfuel;
        vUsage = usage;
        dateAquired = aquired;
        vColor = color;
        vehicleID = tmpid;

        setSimpleID(vehicleID);
        setSimpleMiles(miles);
        setSimpleMake(make);
        setSimpleClass(vClass);
        setSimpleFuelType(fuelType);
        setSimpleUsage(vUsage);
        setSimpleColor(vColor);
        setSimpleDate(dateAquired);
    }



    @Override
    public String toString(){
        String tmp = getVehicleID() +" ";
        tmp +=  getMake() +" ";
        tmp +=  getvClass() +" ";
        tmp +=  getvUsage() +" ";
        tmp +=  getvColor() +" ";
        tmp +=  getFuelType() +" ";
        tmp +=  getMiles() +" ";
        tmp +=  getDateAquired();
        tmp += "\n";
        return tmp;
    }

    public String toString(int i){
        String tmp = "ID: " + getVehicleID() +"\n";
        tmp +=  "Make: " + getMake() +"\n";
        tmp +=  "Class: " + getvClass() +"\n";
        tmp +=  "Usage: " + getvUsage() +"\n";
        tmp +=  "Color: " + getvColor() +"\n";
        tmp +=  "FuelType: " + getFuelType() +"\n";
        tmp +=  "Miles: " + getMiles() +"\n";
        tmp +=  "Date Acquired: " + getDateAquired()+"\n";
        tmp += "-------------------------------------------\n";
        return tmp;
    }



    public int getMiles() {
        return miles;
    }

    public void setMiles(int miles) {
        this.miles = miles;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getvClass() {
        return vClass;
    }

    public void setvClass(String vClass) {
        this.vClass = vClass;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getvUsage() {
        return vUsage;
    }

    public void setvUsage(String vUsage) {
        this.vUsage = vUsage;
    }

    public LocalDate getDateAquired() {
        return dateAquired;
    }

    public void setDateAquired(LocalDate dateAquired) {
        this.dateAquired = dateAquired;
    }

    public String getvColor() {
        return vColor;
    }

    public void setvColor(String vColor) {
        this.vColor = vColor;
    }


    public int getVehicleID() {
        return vehicleID;
    }

    public String getIDString(){
        String tmp = Integer.toString(vehicleID);
        return tmp;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }

    //these are the getters/setters for the simle string properties things


    public String getSimpleID() {
        return simpleID.get();
    }

    public void setSimpleID(int tmpid) {
        String tmp = Integer.toString(tmpid);
        this.simpleID = new SimpleStringProperty(tmp);
    }

    public String getSimpleMiles() {
        return simpleMiles.get();
    }

    public void setSimpleMiles(int tmpmil) {
        String tmp = Integer.toString(tmpmil);
        this.simpleMiles = new SimpleStringProperty(tmp);
    }

    public String getSimpleMake() {
        return simpleMake.get();
    }

    public void setSimpleMake(String tmpmake) {
        this.simpleMake = new SimpleStringProperty(tmpmake);
    }

    public String getSimpleClass() {
        return simpleClass.get();
    }

    public void setSimpleClass(String tmpclass) {
        this.simpleClass = new SimpleStringProperty(tmpclass);
    }

    public String getSimpleFuelType() {
        return simpleFuelType.get();
    }

    public void setSimpleFuelType(String tmpfuel) {
        this.simpleFuelType = new SimpleStringProperty(tmpfuel);
    }

    public String getSimpleUsage() {
        return simpleUsage.get();
    }

    public void setSimpleUsage(String tmpuse) {
        this.simpleUsage = new SimpleStringProperty(tmpuse);
    }

    public String getSimpleDate() {
        return simpleDate.get();
    }

    public void setSimpleDate(LocalDate tmpDate) {
        String tmpd = tmpDate.toString();
        this.simpleDate = new SimpleStringProperty(tmpd);
    }

    public String getSimpleColor() {
        return simpleColor.get();
    }

    public void setSimpleColor(String tmpc) {
        this.simpleColor = new SimpleStringProperty(tmpc);
    }




}
