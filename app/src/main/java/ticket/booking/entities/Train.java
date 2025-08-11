package ticket.booking.entities;

import java.sql.Time;
import java.util.*;

public class Train {
    private String trainId;
    private String trainNo;
    private List<List<Integer>> seats;
    private Map<String, String> stationTimes;
    private List<String> stations;
    public Train(){}

    public Train(String trainId, String trainNo, List<List<Integer>> seats, Map<String, String> stationTimes, List<String> stations){
        this.trainId = trainId;
        this.trainNo = trainNo;
        this.seats = seats;
        this.stationTimes = stationTimes;
        this.stations = stations;
    }

    public Train(Object trainId, String source, String destination, String dateOfTravel) {
        this.trainId = (String) trainId;
        this.trainNo = "Train No: " + UUID.randomUUID().toString();
        this.seats = new ArrayList<>();
        this.stationTimes = new HashMap<>();
        this.stations = new ArrayList<>();
        this.stations.add(source);
        this.stations.add(destination);
        this.stationTimes.put(source, dateOfTravel);
        this.stationTimes.put(destination, dateOfTravel);
    }

    public List<String> getStations(){
        return stations;
    }

    public List<List<Integer>> getSeats() {
        return seats;
    }

    public void setSeats(List<List<Integer>> seats){
        this.seats = seats;
    }

    public String getTrainId(){
        return trainId;
    }

    public Map<String, String> getStationTimes(){
        return stationTimes;
    }

    public String getTrainNo(){
        return trainNo;
    }

    public void setTrainNo(String trainNo){
        this.trainNo = trainNo;
    }

    public void setTrainId(String trainId){
        this.trainId = trainId;
    }

    public void setStationTimes(Map<String, String> stationTimes){
        this.stationTimes = stationTimes;
    }

    public void setStations(List<String> stations){
        this.stations = stations;
    }

    public String getTrainInfo(){
        return String.format("Train ID: %s Train No: %s", trainId, trainNo);
    }

    public Object getSource() {
        if (stations != null && !stations.isEmpty()) {
            return stations.get(0);
        }
        return null;
    }

    public Object getDestination() {
        if (stations != null && !stations.isEmpty()) {
            return stations.get(stations.size() - 1);
        }
        return null;
    }

    public Object getDateOfTravel() {
        if (stationTimes != null && !stationTimes.isEmpty()) {
            // Assuming the first station's time is the date of travel
            return stationTimes.values().iterator().next();
        }
        return null;
    }
}
