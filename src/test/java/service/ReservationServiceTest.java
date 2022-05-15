package service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.*;
import java.util.Date;
import model.Reservation;
import model.*;
import java.util.Collection;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {

    }



    @Test
    void getARoomTest() {
        Room testRoom = new Room("105", 100.00, RoomType.DOUBLE, true);
        ReservationService.getInstance().addRoom(testRoom);
        assertEquals(testRoom,ReservationService.getInstance().getARoom("105"));

    }

    @Test
    void testReserveARoom() {

    }

    @Test
    void findRooms() {
    }

    @Test
    void getCustomersReservationTest() {
        Customer myTestCustomer = new Customer("Anicet", "S", "a@email.com");
        Customer newCustomer = new Customer ("Jastin", "A", "j@email.com");
        Room testRoom = new Room("105", 100.00, RoomType.DOUBLE, true);
        Date checkInDate = new Date();
        Date checkOutDate = new Date();
        Reservation myTestReservation = new Reservation(myTestCustomer, testRoom, checkInDate, checkOutDate);

        Collection testReservationList = new LinkedList<Reservation>();
        //Need to add this reservation to the reservation list before checking this function.
        //getCustomerReservation returns a Collection<Reservation>
        testReservationList.add(myTestReservation);

        ReservationService.getInstance().addNewReservation(myTestCustomer, myTestReservation, testReservationList);
        assertEquals(testReservationList, ReservationService.getInstance().getCustomersReservation(myTestCustomer));

        testReservationList.remove(myTestReservation);
    }

    @Test
    void printAllReservation() {
    }

    @Test
    void getAllRooms() {
    }


}