package lk.ijse.dep11;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class OrderDetails implements Serializable {
    String id;
    int burger;
    int submarine;
    int coke;
    int pepsi;

}
