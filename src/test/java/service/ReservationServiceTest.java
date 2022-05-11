package service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.*;
import model.*;
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
        assertEquals(((IRoom)testRoom),ReservationService.getInstance().getARoom("105"));

    }

    @Test
    void reserveARoom() {
    }

    @Test
    void findRooms() {
    }

    @Test
    void getCustomersReservation() {
    }

    @Test
    void printAllReservation() {
    }

    @Test
    void getAllRooms() {
    }


}