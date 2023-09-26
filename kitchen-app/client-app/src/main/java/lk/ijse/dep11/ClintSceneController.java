package lk.ijse.dep11;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

public class ClintSceneController {
    public Button btnNewCustomer;
    public Button btnPlaceOrder;
    public TextField txtID;
    public TextField txtName;
    public TextField txtContactNumber;
    public Spinner<Integer> spnBurger;
    public Spinner<Integer> spnSub;
    public Spinner<Integer> spnPepsi;
    public Spinner<Integer> spnCoke;
    public TableView<CustomerDetails> tblOrderDetails;
    public ObjectOutputStream oos;

    public void initialize(){
        spnBurger.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,20,1));
        spnBurger.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,20,1));
        spnBurger.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,20,1));
        spnBurger.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,20,1));


        tblOrderDetails.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblOrderDetails.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblOrderDetails.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("status"));


        try {
            Socket remoteSocket = new Socket("localhost", 5050);
            OutputStream os = remoteSocket.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);
            oos = new ObjectOutputStream(bos);
            System.out.println("Connected...!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }






    }

    public void btnNewCustomerOnAction(ActionEvent actionEvent) {
        txtID.setText(getOrderId()+"");
        txtName.requestFocus();

    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
        if(!isDataValid()) return;
        List<CustomerDetails> customerDetails = getOrderList();
        CustomerDetails newCustomer = new CustomerDetails(txtID.getText(), txtName.getText(), txtContactNumber.getText(), "Processing");
        customerDetails.add(newCustomer);


    }

    private boolean isDataValid(){
        if(!txtName.getText().strip().matches("[a-zA-Z]+")){
            txtName.requestFocus();
            txtName.selectAll();
            return false;
        }
        if(!txtContactNumber.getText().strip().matches("0\\d{2}-\\d{7}+")){
            txtContactNumber.requestFocus();
            txtContactNumber.selectAll();
            return false;
        }
        return true;
    }

    private int getOrderId(){
        return 1;
    }

    private List<CustomerDetails> getOrderList(){
        return tblOrderDetails.getItems();
    }


}
