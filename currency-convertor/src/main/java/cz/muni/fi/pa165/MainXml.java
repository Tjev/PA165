package cz.muni.fi.pa165;

import cz.muni.fi.pa165.currency.CurrencyConvertor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.util.Currency;


public class MainXml {

    private static final Currency EUR = Currency.getInstance("EUR");
    private static final Currency CZK = Currency.getInstance("CZK");

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("convertorAppContext.xml");
        CurrencyConvertor currencyConvertor = context.getBean(CurrencyConvertor.class);
        BigDecimal result = currencyConvertor.convert(EUR, CZK, new BigDecimal("1"));
        System.out.printf("1 EUR is equal to %s CZK.", result);
    }
}
