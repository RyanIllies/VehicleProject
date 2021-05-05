package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.xml.transform.Result;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;

public class Controller implements Initializable {

    public static ArrayList<Vehicle> inventory = new ArrayList<Vehicle>();
    public static ObservableList<Vehicle> obslistInventory = FXCollections.observableArrayList();
    String fueltype = "";
    String idToDelete = "";
    String idToChange = "";
    //these FXMLS are all for the first accordian pane (View Inventory)

    @FXML Label lblIDgrid;
    @FXML Label lblMakegrid;
    @FXML Label lblClassgrid;
    @FXML Label lblFuelgrid;
    @FXML Label lblDategrid;
    @FXML Label lblColorgrid;
    @FXML Label lblMilesgrid;
    @FXML Label lblUsagegrid;
    @FXML ComboBox comboBoxGridpane;

    //these FXMLS are all for the second accordian pane (Add Vehicle)

    @FXML TextField txtMake;
    @FXML Slider sliderMiles;
    @FXML RadioButton rbCar;
    @FXML RadioButton rbTruck;
    @FXML RadioButton rbSUV;
    @FXML RadioButton rbHybrid;
    @FXML Button btnSubmit;
    @FXML ColorPicker carColor;
    @FXML ToggleButton tNew;
    @FXML ToggleButton tUsed;
    @FXML CheckBox chkGas;
    @FXML CheckBox chkDes;
    @FXML CheckBox chkEle;
    @FXML DatePicker dateAqu;
    @FXML ToggleGroup toggleGroup;
    @FXML ToggleGroup toggleGroup2;
    @FXML Label lblSlider;

    //this is a placeholder for the FXMLS needed to complete accordian pane #3 (Change Vehicle)

    @FXML Spinner<Integer> spinnerChange;
    @FXML TableView<Vehicle> tableChange;
    @FXML TableColumn<Vehicle, String> colID;
    @FXML TableColumn<Vehicle, String> colMake;
    @FXML TableColumn<Vehicle, String> colClass;
    @FXML TableColumn<Vehicle, String> colUsage;
    @FXML TableColumn<Vehicle, String> colMiles;
    @FXML TableColumn<Vehicle, String> colColor;
    @FXML TableColumn<Vehicle, String> colFuel;
    @FXML TableColumn<Vehicle, String> colDate;
    @FXML ColorPicker colorChange;
    @FXML CheckBox btnUsed;
    @FXML ComboBox changeCombo;
    @FXML Button btnChange;

    //this is a placeholder for the FXMLS needed to complete accordian pane #4 (Delere Vehicle)

    @FXML TextArea textAreaDelete;
    @FXML ComboBox comboBoxDelete;
    @FXML Button btnDelete;

    // new
    public Button gridPrev;
    public Button gridNext;
    @FXML TableView<Vehicle> viewTable;
    @FXML TableColumn<Vehicle, String> viewID;
    @FXML TableColumn<Vehicle, String> viewMake;
    @FXML TableColumn<Vehicle, String> viewClass;
    @FXML TableColumn<Vehicle, String> viewUsage;
    @FXML TableColumn<Vehicle, String> viewMiles;
    @FXML TableColumn<Vehicle, String> viewColor;
    @FXML TableColumn<Vehicle, String> viewFuel;
    @FXML TableColumn<Vehicle, String> viewDate;

    @FXML TextArea textAreaMake;
    @FXML Button viewMakeButton;
    @FXML TextField viewtxtMake;
    @FXML PieChart pieChart;
    @FXML CheckMenuItem showMe;
    @FXML CheckMenuItem dontMe;

    public static ResultSet rs;


    ConnectionClass connectionClass = new ConnectionClass();
    Connection connection = connectionClass.getConnection();

    public ObservableList<Vehicle> populateList() throws Exception{
        //this block is dedicated to the connection class

        System.out.println("In Initialize");

        String qry = "SELECT * FROM Vehicles";
        Statement st = null;
        rs = null;
        try{
            st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

        }catch(SQLException e){e.printStackTrace();
        }
        try {
            rs = st.executeQuery(qry);
            rs.first();
            while(!rs.isAfterLast()){//this does not include the toyota car because th last one is now being updated into the list
                int idtmp = Integer.parseInt(rs.getString(1));
                int milestmp = Integer.parseInt(rs.getString(5));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-d-yyyy");
                DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-d");
                LocalDate datetmp = LocalDate.parse((rs.getString(8)),formatter1);
                inventory.add(new Vehicle(idtmp,rs.getString(3),rs.getString(2)
                        ,milestmp,rs.getString(7),rs.getString(4),datetmp,rs.getString(6)));
                System.out.println(rs.getString(1));//id
                System.out.println(rs.getString(2));//make
                System.out.println(rs.getString(3));//class
                System.out.println(rs.getString(4));//usage
                System.out.println(rs.getString(5));//miles
                System.out.println(rs.getString(6));//color
                System.out.println(rs.getString(7));//fuel
                System.out.println(rs.getString(8));//date
                System.out.println("----------db-----------");
                //go here
                rs.next();
            }



        }catch (SQLException e) {e.printStackTrace(); }

        //this block is dedicated to the connection class


        inventory.forEach((Vehicle v)->{obslistInventory.add(v);});
        obslistInventory.sort(Comparator.comparing(Vehicle::getVehicleID));
        return obslistInventory;
    }

    public void makeVeiwButton (ActionEvent actionEvent) throws SQLException {
        String txtFeildfin = viewtxtMake.getText();

        String user = "%"+txtFeildfin+"%";
        String qrt = ("SELECT * FROM Vehicles WHERE VMAKE LIKE ?");
        PreparedStatement preparedStatement = connection.prepareStatement(qrt);
        preparedStatement.setString(1,user);
        rs = preparedStatement.executeQuery();

        String longone = "";

        while(rs.next()){
            longone += rs.getString(1)+"\n";
            longone += rs.getString(2)+"\n";
            longone += rs.getString(3)+"\n";
            longone += rs.getString(4)+"\n";
            longone += rs.getString(5)+"\n";
            longone += rs.getString(6)+"\n";
            longone += rs.getString(7)+"\n";
            longone += rs.getString(8)+"\n";
            longone += "---------------------\n";


        }
        textAreaMake.setText(longone);
    }

    public void prevAuthorClicked (ActionEvent actionEvent) throws SQLException {
        if(!(rs.isFirst())){
            rs.previous();
        }
        lblIDgrid.setText(rs.getString("VID"));
        lblMakegrid.setText(rs.getString("VMAKE"));
        lblClassgrid.setText(rs.getString("VCLASS"));
        lblUsagegrid.setText(rs.getString("VCOND"));
        lblMilesgrid.setText(rs.getString("VMILES"));
        lblFuelgrid.setText(rs.getString("VFUEL"));
        lblColorgrid.setText(rs.getString("VCOLOR"));
        lblDategrid.setText(rs.getString("VDATE"));

    }

    public void nextAuthorClicked (ActionEvent actionEvent) throws SQLException{
        if(!rs.isLast()){
            rs.next();
        }
        lblIDgrid.setText(rs.getString("VID"));
        lblMakegrid.setText(rs.getString("VMAKE"));
        lblClassgrid.setText(rs.getString("VCLASS"));
        lblUsagegrid.setText(rs.getString("VCOND"));
        lblMilesgrid.setText(rs.getString("VMILES"));
        lblFuelgrid.setText(rs.getString("VFUEL"));
        lblColorgrid.setText(rs.getString("VCOLOR"));
        lblDategrid.setText(rs.getString("VDATE"));
    }

    public void updatechart(){
        double nchart = 0;
        double uchart = 0;
        int tot = 0;
        for (int i = 0;i<inventory.size();i++){
            Vehicle tmpv = inventory.get(i);
            if (tmpv.getSimpleUsage().equals("Used")){
                uchart += 1;
                tot+=1;
            }
            if (tmpv.getSimpleUsage().equals("New")){
                nchart += 1;
                tot+=1;
            }
        }
        ObservableList<PieChart.Data> listy = FXCollections.observableArrayList(
                new PieChart.Data("New",nchart/tot),
                new PieChart.Data("Used",uchart/tot)
        );
        pieChart.setData(listy);

    }





    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            obslistInventory = populateList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            updateViewTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
        updatechart();
        initSpinner();
        toggleGroup.setUserData("");
        toggleGroup2.setUserData("");
        LocalDate loc = LocalDate.now();
        dateAqu.setValue(loc);
        comboBoxGridpane.setValue(obslistInventory.get(0));
        comboBoxGridpane.setItems(obslistInventory);

        comboBoxGridpane.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                String selected = comboBoxGridpane.getValue().toString();
                String tmplist[] = selected.split(" ");

                lblIDgrid.setText(tmplist[0]);
                lblMakegrid.setText(tmplist[1]);
                lblClassgrid.setText(tmplist[2]);
                lblUsagegrid.setText(tmplist[3]);
                lblMilesgrid.setText(tmplist[6]);
                lblFuelgrid.setText(tmplist[5]);
                lblColorgrid.setText(tmplist[4]);
                lblDategrid.setText(tmplist[7]);

            }
        });
        //combo delete pane 4
        comboBoxDelete.setValue(obslistInventory.get(0));
        comboBoxDelete.setItems(obslistInventory);
        comboBoxDelete.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                String selected = comboBoxDelete.getValue().toString();
                String tmplist[] = selected.split(" ");
                idToDelete = tmplist[0]; //this is the ID of the selected combobox to delete an item
            }
        });

        changeCombo.setValue(obslistInventory.get(0));
        changeCombo.setItems(obslistInventory);
        changeCombo.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {

                String selected = changeCombo.getValue().toString();
                String tmplist[] = selected.split(" ");
                idToChange = tmplist[0]; //this is the ID of the selected combobox to delete an item
                ObservableList<Vehicle> tableList = FXCollections.observableArrayList();
                for (int i = 0;i<inventory.size();i++){
                    if(idToChange.equals(inventory.get(i).getIDString())){
                        tableList.clear();
                        Vehicle tmphumv = inventory.get(i);
                        tableList.add(tmphumv);
                    }
                }

                colID.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("simpleID"));
                colMiles.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("simpleMiles"));
                colMake.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("simpleMake"));
                colClass.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("simpleClass"));
                colFuel.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("simpleFuelType"));
                colDate.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("simpleDate"));
                colColor.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("simpleColor"));
                colUsage.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("simpleUsage"));
                tableChange.setItems(tableList);
            }


        });

        //SpinnerValueFactory.IntegerSpinnerValueFactory intFactory = (SpinnerValueFactory.IntegerSpinnerValueFactory) spinnerChange.getValueFactory();
        //int intFactoryMin = intFactory.getMin();
        //int intFactoryMax = intFactory.getMax();
        //int intFactoryAmountToStepBy = intFactory.getAmountToStepBy();
        //int intFactoryValue = intFactory.getValue();

        sliderMiles.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                double wt = sliderMiles.getValue();
                int tmpww = (int)wt;
                String thisone = Integer.toString(tmpww);
                lblSlider.setText(thisone);
            }
        });

        textAreaDelete.setText(getstring());
        try {
            rs.first();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void updateViewTable() throws Exception {

        viewID.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("simpleID"));
        viewMiles.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("simpleMiles"));
        viewMake.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("simpleMake"));
        viewClass.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("simpleClass"));
        viewFuel.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("simpleFuelType"));
        viewDate.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("simpleDate"));
        viewColor.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("simpleColor"));
        viewUsage.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("simpleUsage"));
        viewTable.setItems(obslistInventory);
    }

    @FXML
    public void handleBtnChange(ActionEvent event) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Change Vehicle Info");
        alert.setHeaderText("Success");
        alert.setContentText("Successfully modified Vehicle!");
        alert.showAndWait();
        Statement st = null;
        try{
            st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

        }catch(SQLException e){e.printStackTrace();
        }
        for (int i = 0;i<inventory.size();i++){
            if(idToChange.equals(inventory.get(i).getIDString())){
                Vehicle tmpv = inventory.get(i);
                String tcolor = "#" + Integer.toHexString(colorChange.getValue().hashCode());
                //these are changing the variable and the simplestring variable
                inventory.get(i).setSimpleColor(tcolor);
                inventory.get(i).setvColor(tcolor);
                obslistInventory.get(i).setvColor(tcolor);
                obslistInventory.get(i).setSimpleColor(tcolor);
                PreparedStatement preparedStatement;

                String color = "UPDATE Vehicles SET VCOLOR = ? WHERE VID = ?";

                preparedStatement = connection.prepareStatement(color);
                preparedStatement.setString(1,tcolor);
                preparedStatement.setString(2, tmpv.getIDString());
                preparedStatement.executeUpdate();
                if(btnUsed.isSelected()){
                    inventory.get(i).setvUsage("Used");
                    inventory.get(i).setSimpleUsage("Used");
                    obslistInventory.get(i).setvUsage("Used");
                    obslistInventory.get(i).setSimpleUsage("Used");
                    String used = "UPDATE Vehicles SET VCOND = (?) WHERE VID = (?)";
                    preparedStatement = connection.prepareStatement(used);
                    preparedStatement.setString(1, tmpv.getSimpleUsage());
                    preparedStatement.setString(2, tmpv.getIDString());
                    preparedStatement.executeUpdate();

                }

                //going to take this out as I can not get the miles to update properly
                //inventory.get(i).setSimpleMiles(inventory.get(i).getMiles()+spinnerChange.getValue());
                //inventory.get(i).setMiles(inventory.get(i).getMiles()+spinnerChange.getValue());

                //obslistInventory.get(i).setSimpleMiles(inventory.get(i).getMiles()+ spinnerChange.getValue());
                //obslistInventory.get(i).setMiles(inventory.get(i).getMiles()+ spinnerChange.getValue());
                //these are updating the comboboxes and textfeild updates
                comboBoxGridpane.setItems(obslistInventory);
                changeCombo.setItems(obslistInventory);
                comboBoxDelete.setItems(obslistInventory);
                comboBoxGridpane.setValue(obslistInventory.get(0));
                changeCombo.setValue(obslistInventory.get(0));
                comboBoxDelete.setValue(obslistInventory.get(0));
                textAreaDelete.setText(getstring());

            }
        }

    }


    private void initSpinner() {
        spinnerChange.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(10000,100000,10000,10000));
    }

    @FXML
    public String getstring(){
        String tmps = "";
        for(int i = 0;i<inventory.size();i++) {
            tmps = tmps + inventory.get(i).toString(1)+"\n";
        }
        return tmps;
    }



    @FXML
    public void handlesave(ActionEvent event){
        FileChooser file = new FileChooser();
        FileChooser.ExtensionFilter myfilter = new FileChooser.ExtensionFilter("Text Files","*.txt");
        file.getExtensionFilters().add(myfilter);
        File myfile = file.getInitialDirectory();
        LocalDate date = LocalDate.now();
        String datest = date.toString();
        String tmp = "Vehicles saved on "+datest+"\n";
        for (int i = 0;i< obslistInventory.size();i++){
            tmp+=obslistInventory.get(i).toString(1);
        }
        if(myfile != null){
            try{
                FileWriter write;
                write = new FileWriter(myfile);
                write.write(tmp);
                write.close();
            }catch(IOException e){}
        }

    }

    @FXML
    public void handlechkShow(ActionEvent event){
        if(showMe.isSelected()){
            updatechart();
            pieChart.setVisible(false);
        }
        if(!showMe.isSelected()){
            updatechart();
            pieChart.setVisible(true);
        }
    }
    @FXML
    public void handlechkHide(ActionEvent event){
        if(dontMe.isSelected()){
            updatechart();
            dontMe.setSelected(false);
        }
    }

    @FXML
    public void handlemenucls(ActionEvent event){
        System.exit(0);
    }
    @FXML
    public void handleBtnDelete(ActionEvent event) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Vehicle");
        alert.setHeaderText("Are you sure you want to delete this Vehicle?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            for (int i = 0;i<inventory.size();i++){
                if(idToDelete.equals(inventory.get(i).getIDString())){
                    PreparedStatement preparedStatement;
                    //DELETE FROM `ryan-illies_vehicles`.`Vehicles` WHERE `VID`='70';
                    String delq = "DELETE FROM Vehicles WHERE VID = ?";
                    preparedStatement = connection.prepareStatement(delq);
                    preparedStatement.setString(1, idToDelete);
                    preparedStatement.executeUpdate();
                    inventory.remove(inventory.get(i));
                    obslistInventory.remove(i);
                    comboBoxGridpane.setItems(obslistInventory);
                    comboBoxDelete.setItems(obslistInventory);
                    comboBoxGridpane.setValue(obslistInventory.get(0));
                    comboBoxDelete.setValue(obslistInventory.get(0));


                }
            }
            textAreaDelete.setText(getstring());
        }
        else {
            System.out.println("Vehicle not deleted.");
        }

    }

    @FXML
    private void handlerbcar(ActionEvent event) {
        toggleGroup.setUserData("");
        toggleGroup.setUserData("Car");
    }
    @FXML
    private void handlerbtruck(ActionEvent event) {
        toggleGroup.setUserData("");
        toggleGroup.setUserData("Truck");
    }
    @FXML
    private void handlerbsuv(ActionEvent event) {
        toggleGroup.setUserData("");
        toggleGroup.setUserData("SUV");
    }
    @FXML
    private void handlerbhybrid(ActionEvent event) {
        toggleGroup.setUserData("");
        toggleGroup.setUserData("Hybrid");
    }
    @FXML
    private void handletbused(ActionEvent event) {
        toggleGroup2.setUserData("Used");
    }
    @FXML
    private void handletbnew(ActionEvent event) {
        toggleGroup2.setUserData("New");
    }
    @FXML
    private void handlechgas(ActionEvent event){
        fueltype = fueltype+"Gasoline";
    }
    @FXML
    private void handlechdes(ActionEvent event){
        fueltype = fueltype+"Diesel";
    }
    @FXML
    private void handlechelec(ActionEvent event){
        fueltype = fueltype+"/Electric";
    }

    //Adding works do not touch this
    @FXML
    private void handleBtnSubmit(ActionEvent event) throws SQLException {
        if(!txtMake.getText().equals("")&&!toggleGroup.getUserData().toString().equals("")&&!toggleGroup2.getUserData().toString().equals("")&&!lblSlider.getText().equals("Miles go here")&&!fueltype.equals("")) {

            String tmake;
            String tclass;
            String tmiles;
            tmiles = lblSlider.getText();
            int tmpm = Integer.parseInt(tmiles);
            String tfuel;
            String tusage;
            LocalDate tdate;
            tmake = txtMake.getText();
            tclass = toggleGroup.getUserData().toString();
            tdate = dateAqu.getValue();
            String tcolor = "#" + Integer.toHexString(carColor.getValue().hashCode());
            tusage = toggleGroup2.getUserData().toString();
            tfuel = fueltype;
            Statement st = null;
            try{
                st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

            }catch(SQLException e){e.printStackTrace();
            }
            String tqry = "SELECT * FROM Vehicles";
            rs = st.executeQuery(tqry);
            rs.last();
            int tmptmid = 10+Integer.parseInt((rs.getString(1)));
            Vehicle tmpv = new Vehicle(tmptmid,tclass, tmake, tmpm, tfuel, tusage, tdate, tcolor);
            String trythis = tdate.toString();
            PreparedStatement preparedStatement;
            //
            String qry = "INSERT INTO Vehicles (VID,VMAKE,VCLASS,VCOND,VMILES,VCOLOR,VFUEL,VDATE) VALUES (?,?,?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(qry);
            preparedStatement.setString(1,tmpv.getSimpleID());
            preparedStatement.setString(2,tmpv.getSimpleMake());
            preparedStatement.setString(3,tmpv.getSimpleClass());
            preparedStatement.setString(4,tmpv.getSimpleUsage());
            preparedStatement.setString(5,tmpv.getSimpleMiles());
            preparedStatement.setString(6,tmpv.getSimpleColor());
            preparedStatement.setString(7,tmpv.getSimpleFuelType());
            preparedStatement.setString(8,trythis);
            preparedStatement.executeUpdate();

            //

            inventory.add(tmpv);
            obslistInventory.add(tmpv);
            txtMake.clear();
            toggleGroup2.setUserData("");
            fueltype = "";
            chkDes.setSelected(false);
            chkGas.setSelected(false);
            chkEle.setSelected(false);
            rbCar.setSelected(false);
            rbHybrid.setSelected(false);
            rbSUV.setSelected(false);
            rbTruck.setSelected(false);
            txtMake.requestFocus();
            textAreaDelete.setText(getstring());

        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Fill out all Fields");
            alert.setContentText("Please fill out all required fields");
            alert.showAndWait();
        }
    }


}
