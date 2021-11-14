package service;

import exception.InvalidGameException;
import exception.InvalidParamException;
import exception.NotFoundException;
import lombok.AllArgsConstructor;
import model.Game;
import model.GameStatus;
import model.Player;
import org.springframework.stereotype.Service;
import storage.GameStorage;

import java.util.UUID;

import static model.GameStatus.IN_PROGRESS;
import static model.GameStatus.NEW;

@Service
@AllArgsConstructor
public class GameService {

    public Game createGame(Player player) {
        Game game = new Game();
        game.setBoard(new int[3][3]);
        game.setGameId(UUID.randomUUID().toString());
        game.setPlayer1(player);
        game.setStatus(NEW);
        GameStorage.getInstance().setGame(game);
        return game;
    }
    public Game connectToGame(Player player2, String gameId) throws InvalidParamException, InvalidGameException {
        if(!GameStorage.getInstance().getGames().containsKey(gameId)) {
            throw new InvalidParamException("Game with provided id doesn't exist.");
        }
        Game game = GameStorage.getInstance().getGames().get(gameId);

        if (game.getPlayer2() != null) {
            throw new InvalidGameException("Game is not valid anymore.");
        }

        game.setPlayer2(player2);
        game.setStatus(IN_PROGRESS);
        GameStorage.getInstance().setGame(game);
        return game;
    }

    public Game connectToRandomGame(Player player2) throws NotFoundException {
        Game game = GameStorage.getInstance().getGames().values().stream()
                .filter(it->it.getStatus().equals(NEW))
                .findFirst().orElseThrow(() -> new NotFoundException("Game not found"));
        game.setPlayer2(player2);
        game.setStatus(IN_PROGRESS);
        GameStorage.getInstance().setGame(game);
        return game;
    }

    public Game gamePlay(){
        return null;
    }
}
