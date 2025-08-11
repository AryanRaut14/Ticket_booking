package ticket.booking.entities;

import java.util.Date;

public class Ticket {
    private String ticketId;
    private String userId;
    private String source;
    private String destination;
    private String dateOfTravel;
    private Train train;
    public Ticket(){}

    public Ticket(String ticketId, String userId, String source, String destination, String dateOfTravel, Train train){
        this.ticketId = ticketId;
        this.userId = userId;
        this.source = source;
        this.destination = destination;
        this.dateOfTravel = dateOfTravel;
        this.train = train;
    }

    public Ticket(Object trainId, String source, String destination, String dateOfTravel) {
        this.ticketId = "TICKET-" + System.currentTimeMillis();
        this.userId = "USER-" + System.currentTimeMillis();
        this.source = source;
        this.destination = destination;
        this.dateOfTravel = dateOfTravel;
        this.train = new Train(trainId, source, destination, dateOfTravel);
    }

    public Ticket(String string, String trainId, int row, int col, Date date, Object source, Object destination, Object dateOfTravel) {
        this.ticketId = string;
        this.userId = trainId;
        this.source = (String) source;
        this.destination = (String) destination;
        this.dateOfTravel = (String) dateOfTravel;
        this.train = new Train(trainId, source.toString(), destination.toString(), dateOfTravel.toString());
    }

    public String getTicketInfo(){
        return String.format("Ticket ID: %s belongs to User %s from %s to %s on %s", ticketId, userId, source, destination, dateOfTravel);
    }

    public String getTicketId(){
        return ticketId;
    }

    public void setTicketId(String ticketId){
        this.ticketId = ticketId;
    }

    public String getSource(){
        return source;
    }

    public void setSource(String source){
        this.source = source;
    }

    public String getUserId(){
        return userId;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getDestination(){
        return destination;
    }

    public void setDestination(String destination){
        this.destination = destination;
    }

    public String getDateOfTravel(){
        return dateOfTravel;
    }

    public void setDateOfTravel(String dateOfTravel){
        this.dateOfTravel = dateOfTravel;
    }

    public Train getTrain(){
        return train;
    }

    public void setTrain(Train train){
        this.train = train;
    }

    public Object getTrainId() {
        if (train != null) {
            return train.getTrainId();
        }
        return null;
    }

    public String getRow() {
        if (train != null && train.getSeats() != null && !train.getSeats().isEmpty()) {
            return String.valueOf(train.getSeats().get(0).get(0)); // Assuming the first seat's row is representative
        }
        return null;
    }

    public String getCol() {
        if (train != null && train.getSeats() != null && !train.getSeats().isEmpty()) {
            return String.valueOf(train.getSeats().get(0).get(0)); // Assuming the first seat's column is representative
        }
        return null;
    }
}
