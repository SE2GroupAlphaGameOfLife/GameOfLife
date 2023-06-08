package aau.se2.glock.alpha.gameoflife.networking.packages;

import java.io.Serializable;
import java.util.List;

import aau.se2.glock.alpha.gameoflife.core.Player;
import aau.se2.glock.alpha.gameoflife.core.logic.PlayerCheated;

public interface TcpMessage {
    void accept(TcpMessageVisitor visitor);
}

