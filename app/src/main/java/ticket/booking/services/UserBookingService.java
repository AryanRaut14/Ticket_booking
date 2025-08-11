package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.Ticket;
import ticket.booking.entities.Train;
import ticket.booking.entities.User;
import ticket.booking.util.UserServiceUtil1;
import ticket.booking.services.TrainService;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class UserBookingService {

    private User user;
    private List<User> userList = new ArrayList<>();
    private ObjectMapper objectMapper = new ObjectMapper();

    private static final String USERS_PATH = "app/src/main/java/ticket/booking/localDB/users.json";

    public UserBookingService(User user1) throws IOException {
        this.user = user1;
        loadUsers();
    }

    public UserBookingService() throws IOException {
        loadUsers();
    }

    public void loadUsers() throws IOException {
        File users = new File(USERS_PATH);
        if (users.exists()) {
            this.userList = objectMapper.readValue(users, new TypeReference<List<User>>() {});
        } else {
            this.userList = new ArrayList<>();
        }
    }

    public Boolean loginUser() {
        Optional<User> foundUser = userList.stream().filter(user1 ->
                user1.getName().equalsIgnoreCase(user.getName())
                        && UserServiceUtil1.checkPassword(user.getPassword(), user1.getHashedPassword())
        ).findFirst();
        if (foundUser.isPresent()) {
            this.user = foundUser.get();
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public Boolean signUp(User user1) {
        try {
            userList.add(user1);
            saveUserListToFile();
            return Boolean.TRUE;
        } catch (IOException ex) {
            ex.printStackTrace();
            return Boolean.FALSE;
        }
    }

    private void saveUserListToFile() throws IOException {
        File usersFile = new File(USERS_PATH);
        objectMapper.writeValue(usersFile, userList);
    }

    public List<Ticket> fetchBookings() {
        Optional<User> userFetched = userList.stream().filter(user1 ->
                user1.getUserId().equals(user.getUserId())).findFirst();
        if (userFetched.isPresent()) {
            List<Ticket> bookedTickets = new ArrayList<>();
            for (Ticket ticket : userFetched.get().getTicketsBooked()) {
                Ticket train1 = new Ticket(ticket.getTrainId(), ticket.getSource(), ticket.getDestination(), ticket.getDateOfTravel());
                if (ticket.getTrainId().equals(train1.getTrainId())) {
                    bookedTickets.add(ticket);
                }
            }
            return bookedTickets;
        }
        return new ArrayList<>();
    }

    public Boolean cancelBooking(String ticketId) {
        Optional<User> userFetched = userList.stream().filter(user1 ->
                user1.getUserId().equals(user.getUserId())).findFirst();
        if (userFetched.isPresent()) {
            List<Ticket> tickets = userFetched.get().getTicketsBooked();
            boolean removed = tickets.removeIf(ticket -> ticket.getTicketId().equals(ticketId));
            if (removed) {
                user.setTicketsBooked(tickets);
                try {
                    saveUserListToFile();
                    return Boolean.TRUE;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return Boolean.FALSE;
    }

    public List<Train> getTrains(String source, String destination) {
        try {
            ticket.booking.services.TrainService trainService = new ticket.booking.services.TrainService();
            return trainService.searchTrains(source, destination);
        } catch (IOException ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Boolean bookTrainSeat(Train trainSelectedForBooking, int row, int col) {
        if (row < 0 || col < 0
                || row >= trainSelectedForBooking.getSeats().size()
                || col >= trainSelectedForBooking.getSeats().get(row).size()) {
            System.out.println("Invalid seat selection. Please try again.");
            return Boolean.FALSE;
        }

        // Check if seat already booked
        if (trainSelectedForBooking.getSeats().get(row).get(col) == 1) {
            System.out.println("Seat already booked!");
            return Boolean.FALSE;
        }

        // Mark seat as booked
        trainSelectedForBooking.getSeats().get(row).set(col, 1);

        // Create ticket
        Ticket newTicket = new Ticket(UUID.randomUUID().toString(), trainSelectedForBooking.getTrainId(), row, col, new Date(), trainSelectedForBooking.getSource(), trainSelectedForBooking.getDestination(), trainSelectedForBooking.getDateOfTravel());

        Optional<User> userFetched = userList.stream().filter(user1 ->
                user1.getUserId().equals(user.getUserId())).findFirst();
        if (userFetched.isPresent()) {
            userFetched.get().getTicketsBooked().add(newTicket);
            user.setTicketsBooked(userFetched.get().getTicketsBooked());
            try {
                saveUserListToFile();
                return Boolean.TRUE;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Boolean.FALSE;
    }

    public List<List<Integer>> fetchSeats(Train trainSelectedForBooking) {
        List<List<Integer>> seats = new ArrayList<>();
        for (List<Integer> row : trainSelectedForBooking.getSeats()) {
            List<Integer> seatRow = new ArrayList<>(row);
            seats.add(seatRow);
        }
        return seats;
    }
}

