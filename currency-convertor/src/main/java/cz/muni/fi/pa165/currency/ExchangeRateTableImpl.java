package cz.muni.fi.pa165.currency;

import javax.inject.Named;
import java.math.BigDecimal;
import java.util.Currency;

@Named("exchangeRateTableImpl")
public class ExchangeRateTableImpl implements ExchangeRateTable {

    private final Currency EUR = Currency.getInstance("EUR");
    private final Currency CZK = Currency.getInstance("CZK");

    @Override
    public BigDecimal getExchangeRate(Currency sourceCurrency, Currency targetCurrency) throws ExternalServiceFailureException {
        if (sourceCurrency.equals(EUR) && targetCurrency.equals(CZK)) {
            return new BigDecimal("27");
        }
        return null;
    }
}
