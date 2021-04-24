/*
 *  @author albua
 *  created on 17/04/2021
 */


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ProtoUtils {

    public static Protobufs.ChatRequest createLoginRequest(Admin admin){
        Protobufs.Admin adminDto = Protobufs.Admin.newBuilder()
                .setUsername(admin.getUsername())
                .setPasswd(admin.getPasswordString())
                .build();

        return Protobufs.ChatRequest.newBuilder()
                .setType(Protobufs.ChatRequest.Type.Login)
                .setAdmin(adminDto)
                .build();
    }

    public static Protobufs.ChatRequest createLogoutRequest(Admin admin){
        Protobufs.Admin adminDto = Protobufs.Admin.newBuilder()
                .setUsername(admin.getUsername())
                .setPasswd(admin.getPasswordString())
                .build();

        return Protobufs.ChatRequest.newBuilder()
                .setType(Protobufs.ChatRequest.Type.Logout)
                .setAdmin(adminDto)
                .build();
    }

    public static Protobufs.ChatRequest createAddBookingRequest(Booking booking){
        Protobufs.Booking book = Protobufs.Booking.newBuilder()
                .setFirstName(booking.getClientFirstName())
                .setLastName(booking.getClientLastName())
                .setNrSeats(booking.getNrSeats())
                .setTripId(booking.getTrip().getID())
                .build();

        return Protobufs.ChatRequest.newBuilder()
                .setType(Protobufs.ChatRequest.Type.Add_Booking)
                .setBooking(book)
                .build();
    }

    public static Protobufs.ChatRequest createFindAllTripsRequest(){
        return Protobufs.ChatRequest.newBuilder()
                .setType(Protobufs.ChatRequest.Type.Find_All_Trips)
                .build();
    }

    public static Protobufs.ChatRequest createFindBookingsTripRequest(Trip trip){
        Protobufs.Trip tr = Protobufs.Trip.newBuilder()
                .setDate(trip.getDateFormat())
                .setId(trip.getID())
                .build();

        return Protobufs.ChatRequest.newBuilder()
                .setType(Protobufs.ChatRequest.Type.Find_Bookings_Trip)
                .setTrip(tr)
                .build();
    }

    public static Protobufs.ChatRequest createFindTripFilterRequest(Trip trip){
        Protobufs.Trip tr = Protobufs.Trip.newBuilder()
                .setDate(trip.getDateFormat())
                .setId(trip.getID())
                .setSource(trip.getSource())
                .build();

        return Protobufs.ChatRequest.newBuilder()
                .setType(Protobufs.ChatRequest.Type.Find_Trip_Filter)
                .setTrip(tr)
                .build();
    }

    public static Protobufs.ChatResponse createOkResponse(){
        Protobufs.ChatResponse response=Protobufs.ChatResponse.newBuilder()
                .setType(Protobufs.ChatResponse.Type.Ok).build();
        return response;
    }

    public static Protobufs.ChatResponse createErrorResponse(String text){
        Protobufs.ChatResponse response=Protobufs.ChatResponse.newBuilder()
                .setType(Protobufs.ChatResponse.Type.Error)
                .setError(text).build();
        return response;
    }

    public static Protobufs.ChatResponse createRefreshResponse(Trip[] trips){
        Protobufs.ChatResponse.Builder response = Protobufs.ChatResponse.newBuilder()
                .setType(Protobufs.ChatResponse.Type.Refresh);

        for (var tr : trips){
            Protobufs.Trip tripDto = Protobufs.Trip.newBuilder()
                    .setId(tr.getID())
                    .setDate(tr.getDateFormat())
                    .setSource(tr.getSource())
                    .setDestination(tr.getDestination())
                    .setFreeSeats(tr.getFreeSeats())
                    .build();

            response.addTrip(tripDto);
        }

        return response.build();
    }

    public static Protobufs.ChatResponse createGetTripsResponse(Trip[] trips){
        Protobufs.ChatResponse.Builder response = Protobufs.ChatResponse.newBuilder()
                .setType(Protobufs.ChatResponse.Type.Get_Trips);

        for (var tr : trips){
            Protobufs.Trip tripDto = Protobufs.Trip.newBuilder()
                    .setId(tr.getID())
                    .setDate(tr.getDateFormat())
                    .setSource(tr.getSource())
                    .setDestination(tr.getDestination())
                    .setFreeSeats(tr.getFreeSeats())
                    .build();

            response.addTrip(tripDto);
        }

        return response.build();
    }

    public static Protobufs.ChatResponse createGetBookingsResponse(Booking[] bookings){
        Protobufs.ChatResponse.Builder response = Protobufs.ChatResponse.newBuilder()
                .setType(Protobufs.ChatResponse.Type.Get_Bookings);

        for(var book : bookings){
            Protobufs.Booking bookingDto = Protobufs.Booking.newBuilder()
                    .setTripId(book.getTrip().getID())
                    .setFirstName(book.getClientFirstName())
                    .setLastName(book.getClientLastName())
                    .setNrSeats(book.getNrSeats())
                    .build();

            response.addBookings(bookingDto);
        }

        return response.build();
    }

    public static Protobufs.ChatResponse createGetTripsFilterResponse(Trip[] trips){
        Protobufs.ChatResponse.Builder response = Protobufs.ChatResponse.newBuilder()
                .setType(Protobufs.ChatResponse.Type.Get_Trips_Filter);

        for (var tr : trips){
            Protobufs.Trip tripDto = Protobufs.Trip.newBuilder()
                    .setId(tr.getID())
                    .setDate(tr.getDateFormat())
                    .setSource(tr.getSource())
                    .setDestination(tr.getDestination())
                    .setFreeSeats(tr.getFreeSeats())
                    .build();

            response.addTrip(tripDto);
        }

        return response.build();
    }

    public static String getError(Protobufs.ChatResponse response){
        String errorMessage=response.getError();
        return errorMessage;
    }

    public static Admin getAdmin(Protobufs.ChatResponse response){
        byte[] a = new byte[1];

        Admin user=new Admin("a", a);
        user.setUsername(response.getAdmin().getUsername());
        user.setPasswordString(response.getAdmin().getPasswd());
        return user;
    }

    public static Admin getAdmin(Protobufs.ChatRequest request){
        byte[] a = new byte[1];

        Admin user=new Admin("a", a);
        user.setUsername(request.getAdmin().getUsername());
        user.setPasswordString(request.getAdmin().getPasswd());
        return user;
    }

    public static Booking getBooking(Protobufs.ChatRequest request){
        Trip tr = new Trip("a", "a", LocalDateTime.now(), 18);
        tr.setID(request.getBooking().getTripId());

        Booking booking = new Booking(tr, request.getBooking().getNrSeats(), request.getBooking().getFirstName(), request.getBooking().getLastName());

        return booking;
    }

    public static Booking[] getBookings(Protobufs.ChatResponse response){
        Booking[] bookings = new Booking[response.getBookingsCount()];

        for(int i=0;i<response.getBookingsCount();i++){
            Protobufs.Booking bookingDto = response.getBookings(i);

            Trip trip = new Trip("a", "a", LocalDateTime.now(), 18);
            trip.setID(bookingDto.getTripId());

            Booking booking = new Booking(trip, bookingDto.getNrSeats(), bookingDto.getFirstName(), bookingDto.getLastName());

            bookings[i] = booking;
        }
        return bookings;
    }
//JAVA
//    public static Trip[] getTrips(Protobufs.ChatResponse response){
//        Trip[] friends=new Trip[response.getTripCount()];
//
//        for(int i=0;i<response.getTripCount();i++){
//            Protobufs.Trip userDTO=response.getTrip(i);
//            Trip user=new Trip(userDTO.getSource(),
//                    userDTO.getDestination(),
//                    LocalDateTime.parse(userDTO.getDate(),
//                            DateTimeFormatter.ofPattern("yyyy-MM-dd  HH:mm")),
//                    userDTO.getFreeSeats());
//
//            user.setID(userDTO.getId());
//            friends[i]=user;
//        }
//        return friends;
//    }

//C#
    public static Trip[] getTrips(Protobufs.ChatResponse response){
        Trip[] friends=new Trip[response.getTripCount()];

        for(int i=0;i<response.getTripCount();i++){
            Protobufs.Trip userDTO=response.getTrip(i);
            Trip user=new Trip(userDTO.getSource(),
                    userDTO.getDestination(),
                    LocalDateTime.parse(userDTO.getDate(),
                            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:s")),
                    userDTO.getFreeSeats());

            user.setID(userDTO.getId());
            friends[i]=user;
        }
        return friends;
    }
//JAVA
//    public static Trip getTrip(Protobufs.ChatRequest request){
//        Protobufs.Trip userDTO = request.getTrip();
//
//        Trip user=new Trip(userDTO.getSource(),
//                userDTO.getDestination(),
//                LocalDateTime.parse(userDTO.getDate(),
//                        DateTimeFormatter.ofPattern("yyyy-MM-dd  HH:mm")),
//                userDTO.getFreeSeats());
//        user.setID(userDTO.getId());
//        return user;
//    }

    public static Trip getTrip(Protobufs.ChatRequest request){
        Protobufs.Trip userDTO = request.getTrip();

        Trip user=new Trip(userDTO.getSource(),
                userDTO.getDestination(),
                LocalDateTime.parse(userDTO.getDate(),
                        DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm:s")),
                userDTO.getFreeSeats());
        user.setID(userDTO.getId());
        return user;
    }
}
