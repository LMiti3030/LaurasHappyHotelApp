package mititelu.laura.mockitoapp.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class Test04MultipleThenReturnCalls {

    private BookingService bookingService;

    private PaymentService paymentServiceMock;
    private RoomService roomServiceMock;
    private BookingDAO bookingDAOMock;
    private MailSender mailSenderMock;

    @BeforeEach
    void setUp(){
        this.paymentServiceMock = mock(PaymentService.class);
        this.roomServiceMock = mock(RoomService.class);
        this.bookingDAOMock = mock(BookingDAO.class);
        this.mailSenderMock = mock(MailSender.class);

        this.bookingService = new BookingService(paymentServiceMock, roomServiceMock, bookingDAOMock, mailSenderMock);

    }

    @Test
    void should_CountAvailablePlaces_When_CalledMultipleTimes(){
        // given
        when(this.roomServiceMock.getAvailableRooms())
                .thenReturn(Collections.singletonList(new Room( "Room 1", 5 )))
                .thenReturn(Collections.emptyList());
        //first  time 5 places
        //second time empty list

        int expectedFirstCall = 5;
        int expectedSecondCall = 0;

        // when
        int actualFirstCall = bookingService.getAvailablePlaceCount();
        int actualSecondCall = bookingService.getAvailablePlaceCount();

        // then
        assertAll(
                () -> assertEquals(expectedFirstCall, actualFirstCall),
                () -> assertEquals(expectedSecondCall, actualSecondCall)
        );
    }



}