package aau.se2.glock.alpha.gameoflife.core.logic;

import com.badlogic.gdx.Gdx;

import org.graalvm.compiler.java.GraphBuilderPhase;

import java.util.List;

import aau.se2.glock.alpha.gameoflife.GameOfLife;
import aau.se2.glock.alpha.gameoflife.core.Board;
import aau.se2.glock.alpha.gameoflife.core.GameField;
import aau.se2.glock.alpha.gameoflife.core.Player;
import aau.se2.glock.alpha.gameoflife.screens.GameScreen;

//Should later be refactored to be a singleton
public class Game {

    public static Game INSTANCE;

    private Board board;
    private Player currentplayer;
    private List<Player> otherPlayers;

    private Game(Player player, List<Player> otherPlayers){
        this.currentplayer = player;
        this.otherPlayers = otherPlayers;
    }

    /**
     * Called when host presses the start button/the host is initialized
     * sets up all logic components
     */
    public void startGame(){
        board = Board.getInstance();
        //sendInfoToOtherPlayers();
    }


    public int rollDice(){
        int rolledNumber = 0;
        if(currentplayer.isHasTurn()){
            rolledNumber = currentplayer.rollTheDice();
        }else return -1;
        Gdx.app.log(currentplayer.getUsername()+ " rolled ",""+rolledNumber);
        if(!currentplayer.makeMove()){
            //player has to choose a path
            int currentPosition = currentplayer.getPosition();
            GameField currentField = board.getGameFields().get(currentPosition);
            GameOfLife.players.set(currentplayer.getId(),currentplayer);
            GameScreen.getINSTANCE().chooseNextStep(currentField);
        } else{
            //triggerEventOnField();
            //set status of currentplayer
            GameOfLife.players.set(currentplayer.getId(),currentplayer);
            endTurn();
        }

        return rolledNumber;
    }

    /**
     * called when the current player presses end turn
     */
    public void endTurn(){
        currentplayer.setHasTurn(false);
        if(currentplayer.getId()>=otherPlayers.size()-1){
            currentplayer = otherPlayers.get(0);
        }else{
            currentplayer = otherPlayers.get(currentplayer.getId()+1);
        }


    }

    public static Game getInstance(Player player, List<Player> otherPlayers){
        if(INSTANCE == null){
            INSTANCE = new Game(player,otherPlayers);
        }
        return INSTANCE;
    }

    public static Game getInstance() {
        return INSTANCE;
    }

    public Player getCurrentPlayer() {
        return currentplayer;
    }

    public List<Player> getOtherPlayers() {
        return otherPlayers;
    }
}
