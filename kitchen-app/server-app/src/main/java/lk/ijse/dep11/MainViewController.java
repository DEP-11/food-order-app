package lk.ijse.dep11;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MainViewController {
    public AnchorPane root;
    public TableView<OrderDetails> tblOrder;

    public void initialize(){
        tblOrder.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblOrder.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("burger"));
        tblOrder.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("submarine"));
        tblOrder.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("coke"));
        tblOrder.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("pepsi"));


        try {
            ServerSocket serverSocket = new ServerSocket(5050);

            while(true){
                System.out.println("Waiting for a connection");
                Socket localSocket = serverSocket.accept();
                System.out.println("Client connected "+localSocket);
                new Thread(()->{
                    try {
                        InputStream is = localSocket.getInputStream();
                        BufferedInputStream bis = new BufferedInputStream(is);
                        ObjectInputStream ois = new ObjectInputStream(bis);

                        while(true){
                            OrderDetails newOrder = (OrderDetails)ois.readObject();
                            Platform.runLater(()->tblOrder.getItems().add(newOrder));

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    public void btnCloseOnAction(ActionEvent actionEvent) {
    }
}
