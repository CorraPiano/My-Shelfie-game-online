package it.polimi.ingsw.client.view;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ClientGUI;
import it.polimi.ingsw.client.connection.*;
public interface View {
    public void setSender(Sender sender);
    public Sender getSender();
    public void setClient();
    public Client getClient();

}
