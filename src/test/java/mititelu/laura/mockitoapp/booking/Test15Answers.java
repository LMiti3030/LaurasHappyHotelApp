package mititelu.laura.mockitoapp.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyDouble;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class Test15Answers {

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private PaymentService paymentServiceMock;
    @Mock
    private RoomService roomServiceMock;
    @Mock
    private BookingDAO bookingDAOMock;
    @Mock
    private MailSender mailSenderMock;
    @Captor
    private ArgumentCaptor<Double> doubleCaptor;

    @Test
    void should_CalculateCorrectPrice(){

        try(MockedStatic<CurrencyConverter> mockedConvertor = mockStatic(CurrencyConverter.class)){
            // given
            BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2023, 1, 31),
                    LocalDate.of(2023,2,4), 2, false);
            double expectedPrice = 400.0 * 0.8;
//            mockedConvertor.when(() -> CurrencyConverter.toEuro(anyDouble())).thenReturn(400.0); //will always return 400.0
            mockedConvertor.when(() -> CurrencyConverter.toEuro(anyDouble()))
                    .thenAnswer( invocationOnMock -> (double) (invocationOnMock.getArgument(0)) * 0.8);

            // answers work with any mockito test, not just static methods

            // when
            double actualPrice = bookingService.calculatePriceEuro(bookingRequest);

            // then
            //no exception is thrown
            assertEquals(expectedPrice, actualPrice);

        }
   }



}