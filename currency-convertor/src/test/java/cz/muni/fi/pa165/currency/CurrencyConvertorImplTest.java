package cz.muni.fi.pa165.currency;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Currency;

@RunWith(MockitoJUnitRunner.class)
public class CurrencyConvertorImplTest {

    private CurrencyConvertor converter;
    private ExchangeRateTable exchangeRateTable;
    private static final Currency EUR = Currency.getInstance("EUR");
    private static final Currency CZK = Currency.getInstance("CZK");

    @Before
    public void setUp() throws ExternalServiceFailureException {
        this.exchangeRateTable = mock(ExchangeRateTable.class);
        BigDecimal value = new BigDecimal("25");
        when(this.exchangeRateTable.getExchangeRate(EUR, CZK)).thenReturn(value);
        this.converter = new CurrencyConvertorImpl(exchangeRateTable);
    }

    @Test
    public void testConvert() {
        // Don't forget to test border values and proper rounding
        BigDecimal amount;
        amount = new BigDecimal("1");
        assertEquals( new BigDecimal("25.00"), this.converter.convert(EUR, CZK, amount));
        amount = new BigDecimal("0.001");
        assertEquals(new BigDecimal("0.02"), this.converter.convert(EUR, CZK, amount));
        amount = new BigDecimal("0.003");
        assertEquals(new BigDecimal("0.08"), this.converter.convert(EUR, CZK, amount));
        amount = new BigDecimal("0");
        assertEquals(new BigDecimal("0.00"), this.converter.convert(EUR, CZK, amount));
        amount = new BigDecimal("-1");
        assertEquals(new BigDecimal("-25.00"), this.converter.convert(EUR, CZK, amount));
    }

    @Test
    public void testConvertWithNullSourceCurrency() {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            this.converter.convert(null, CZK, new BigDecimal("1"));
        });
    }

    @Test
    public void testConvertWithNullTargetCurrency() {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            this.converter.convert(EUR, null, new BigDecimal("1"));
        });
    }

    @Test
    public void testConvertWithNullSourceAmount() {
        assertThatIllegalArgumentException().isThrownBy(() -> {
            this.converter.convert(EUR, CZK, null);
        });
    }

    @Test
    public void testConvertWithUnknownCurrency() {
        assertThatExceptionOfType(UnknownExchangeRateException.class).isThrownBy(() -> {
            when(this.exchangeRateTable.getExchangeRate(any(), any())).thenReturn(null);
            this.converter.convert(EUR, CZK, new BigDecimal("1"));
        });
    }

    @Test
    public void testConvertWithExternalServiceFailure() {
        assertThatExceptionOfType(UnknownExchangeRateException.class).isThrownBy(() -> {
            when(this.exchangeRateTable.getExchangeRate(any(), any()))
                    .thenThrow(new ExternalServiceFailureException("message"));
            this.converter.convert(EUR, CZK, new BigDecimal("1"));
        });
    }

}
