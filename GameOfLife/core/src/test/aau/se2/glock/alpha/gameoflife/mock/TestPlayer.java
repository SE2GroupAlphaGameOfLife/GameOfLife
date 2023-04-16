package aau.se2.glock.alpha.gameoflife.mock;

import aau.se2.glock.alpha.gameoflife.core.GameField;
import aau.se2.glock.alpha.gameoflife.core.Player;

public class TestPlayer extends Player {

    /*
            UGLY SOLUTION - may be fixed in future
     */

    public TestPlayer(String username, boolean isHost) {
        super(username, isHost);
    }

    @Override
    public boolean makeMove(){
        GameField currentField = TestBoard.getMockInstance().getGameFields().get(this.getPosition());

        while (this.moveCount > 0){
            this.moveCount--;

            if(currentField.getIndexOfNextGameFields().size() > 1){
                //we have to choose between multiple fields which one we want to choose so we return false
                return false;
            } else {
                this.position = currentField.getIndexOfNextGameFields().get(0);
                currentField = TestBoard.getMockInstance().getGameFields().get(this.position);
            }
        }

        //we finished moving return true
        return true;
    }

    @Override
    public void chooseDirection(int index) {
        GameField currentField = TestBoard.getMockInstance().getGameFields().get(this.position);
        this.moveCount--;
        this.position = currentField.getIndexOfNextGameFields().get(index);
    }

}
