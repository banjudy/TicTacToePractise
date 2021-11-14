package controller.dto;

import lombok.Data;
import model.Player;

@Data
public class ConnectRequest {

    private Player player;
    private String gameId;
}
