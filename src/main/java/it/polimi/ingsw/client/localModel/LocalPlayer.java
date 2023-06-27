package it.polimi.ingsw.client.localModel;

import it.polimi.ingsw.connection.MessageHeader;
import it.polimi.ingsw.connection.message.Sendable;
import it.polimi.ingsw.model.PlayerState;
import it.polimi.ingsw.model.Token;

import java.io.Serializable;

/**
 * The `LocalPlayer` class represents a player in the client's game model.
 * It stores information such as the player's name, seat position, tokens, points, personal card, and player state.
 */
public class LocalPlayer implements Serializable, Sendable {

    public final String name;
    public final boolean firstPlayerSeat;
    public final Token endGameToke;
    public final Token token1;
    public final Token token2;
    public final int points;
    public final int numPersonalCard;
    public final PlayerState playerState;

    /**
     * Constructs a `LocalPlayer` object with the specified player information, including the player's name,
     * seat position, tokens, points, personal card, and player state.
     *
     * @param name              The name of the player.
     * @param firstPlayerSeat   A boolean indicating if the player occupies the first player seat.
     * @param endGameToke       The end game token associated with the player represented by a `Token` object.
     * @param token1            The first token associated with the player represented by a `Token` object.
     * @param token2            The second token associated with the player represented by a `Token` object.
     * @param points            The number of points accumulated by the player.
     * @param numPersonalCard   The number of the player's personal card. Use -1 if the personal card is not available.
     * @param playerState       The state of the player represented by a `PlayerState` object.
     */
    public LocalPlayer(String name, boolean firstPlayerSeat, Token endGameToke, Token token1, Token token2, int points, int numPersonalCard, PlayerState playerState) {
        this.name = name;
        this.firstPlayerSeat = firstPlayerSeat;
        this.endGameToke = endGameToke;
        this.token1 = token1;
        this.token2 = token2;
        this.points = points;
        this.numPersonalCard = numPersonalCard;
        this.playerState = playerState;
    }

    /**
     * Constructs a `LocalPlayer` object with the specified player information, excluding the personal card.
     *
     * @param name              The name of the player.
     * @param firstPlayerSeat   A boolean indicating if the player occupies the first player seat.
     * @param endGameToke       The end game token associated with the player represented by a `Token` object.
     * @param token1            The first token associated with the player represented by a `Token` object.
     * @param token2            The second token associated with the player represented by a `Token` object.
     * @param points            The number of points accumulated by the player.
     * @param playerState       The state of the player represented by a `PlayerState` object.
     */
    public LocalPlayer(String name, boolean firstPlayerSeat, Token endGameToke, Token token1, Token token2, int points, PlayerState playerState) {
        this.name = name;
        this.firstPlayerSeat = firstPlayerSeat;
        this.endGameToke = endGameToke;
        this.token1 = token1;
        this.token2 = token2;
        this.points = points;
        this.numPersonalCard = -1;
        this.playerState = playerState;
    }

    /**
     * Returns the message header associated with the `LocalPlayer` object.
     *
     * @return The message header.
     */
    public MessageHeader getHeader() {
        return MessageHeader.PLAYER;
    }

    /**
     * Returns the number of points accumulated by the player.
     *
     * @return The number of points.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Returns the name of the player.
     *
     * @return The player's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the end game token associated with the player.
     *
     * @return The end game token represented by a `Token` object.
     */
    public Token getEndGameToke() {
        return endGameToke;
    }

    /**
     * Returns the first token associated with the player.
     *
     * @return The first token represented by a `Token` object.
     */
    public Token getToken1() {
        return token1;
    }

    /**
     * Returns the second token associated with the player.
     *
     * @return The second token represented by a `Token` object.
     */
    public Token getToken2() {
        return token2;
    }

    /**
     * Returns whether the player occupies the first player seat.
     *
     * @return `true` if the player occupies the first player seat, `false` otherwise.
     */
    public boolean isFirstPlayerSeat() {
        return firstPlayerSeat;
    }

    /**
     * Returns the number of the player's personal card.
     *
     * @return The number of the personal card. Returns -1 if the personal card is not available.
     */
    public int getNumPersonalCard() {
        return numPersonalCard;
    }

    /**
     * Returns the state of the player.
     *
     * @return The player's state represented by a `PlayerState` object.
     */
    public PlayerState getPlayerState() {
        return playerState;
    }
    public Boolean isRecurrentUpdate(){
        return false;
    }
}