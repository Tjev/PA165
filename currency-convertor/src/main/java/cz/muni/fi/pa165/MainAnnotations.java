package cz.muni.fi.pa165;

import cz.muni.fi.pa165.currency.CurrencyConvertor;
import cz.muni.fi.pa165.currency.CurrencyConvertorImpl;
import cz.muni.fi.pa165.currency.ExchangeRateTableImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.util.Currency;


public class MainAnnotations {

    private static final Currency EUR = Currency.getInstance("EUR");
    private static final Currency CZK = Currency.getInstance("CZK");

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(
                CurrencyConvertorImpl.class, ExchangeRateTableImpl.class);
        CurrencyConvertor currencyConvertor = context.getBean(CurrencyConvertor.class);
        BigDecimal result = currencyConvertor.convert(EUR, CZK, new BigDecimal("1"));
        System.out.printf("1 EUR is equal to %s CZK.", result);
    }
}
