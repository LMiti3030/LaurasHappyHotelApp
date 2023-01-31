package mititelu.laura.mockitoapp.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

class Test07VerifyingBehaviour {

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
    void should_InvokePayment_When_Prepaid(){
        // given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2023, 1, 31),
                LocalDate.of(2023,2,4), 2, true);

        // when
        bookingService.makeBooking(bookingRequest);

        // then
        verify(paymentServiceMock, times(1)).pay(bookingRequest, 400.0);
        verifyNoMoreInteractions(paymentServiceMock);

        //verify(paymentServiceMock).pay(bookingRequest, 500.0); //fails because price calculated for the room is 400
    }


    @Test
    void should_NotInvokePayment_When_NotPrepaid(){
        // given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2023, 1, 31),
                LocalDate.of(2023,2,4), 2, false);
        // when
        bookingService.makeBooking(bookingRequest);

        // then
        verify(paymentServiceMock, never()).pay(any(), anyDouble());
        //verifyNoInteractions(paymentServiceMock);

    }

}