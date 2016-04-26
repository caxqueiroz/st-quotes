package io.pivotal.springtrader.quotes;

import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;
import io.pivotal.springtrader.quotes.domain.Stock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by cq on 4/12/15.
 */
@Configuration
@Profile("test")
@EnableMongoAuditing
@EnableMongoRepositories(basePackages = {"io.pivotal.springtrader.quotes"})
public class TestConfig extends AbstractMongoConfiguration {

    public static final String QUOTE_SYMBOL = "AAPL";
    public static final String QUOTE_NAME = "Apple Inc";
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss zzzXXX yyyy", Locale.ENGLISH);
    public static final String QUOTE_DATE_STRING = "Wed May 6 00:00:00 UTC-04:00 2015";
    public static final double QUOTE_LAST_PRICE = 26.135;
    public static final double QUOTE_CHANGE = 0.00500000000000256d;
    public static final double QUOTE_CHANGE_PERCENT = 0.0191350937619692;
    public static final double QUOTE_MSDATE = 42130d;

    public static final String COMPANY_EXCHANGE = "NASDAQ";
    public static final String NULL_QUOTE_SYMBOL = "LALALALA";

    @Bean
    public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory) {
        return new MongoTemplate(mongoDbFactory);
    }

    @Override
    protected String getDatabaseName() {
        return "Quotes";
    }

    @Override
    public Mongo mongo() throws Exception {
        Fongo fongo = new Fongo("mongo server local");
        return fongo.getMongo();

    }

    @Override
    protected String getMappingBasePackage() {
        return "io.pivotal.springtrader.quotes.domain";
    }



    /*
     * {"Status":"SUCCESS","Name":"EMC Corp","Symbol":"EMC","LastPrice":26.135,
     * "Change":0.00500000000000256,"ChangePercent":0.0191350937619692,
     * "Timestamp":"Wed May 6 00:00:00 UTC-04:00 2015","MSDate":42130,
     * "MarketCap":50755764235,"Volume":15159291,"ChangeYTD":29.74,
     * "ChangePercentYTD":-12.1217215870881,"High":0,"Low":0,"Open":26.52}
     */
    public static Stock stock() {
        Stock quote = new Stock();
        quote.setStatus("SUCCESS");
        quote.setSymbol(QUOTE_SYMBOL);
        quote.setLastPrice(QUOTE_LAST_PRICE);
        quote.setChange(QUOTE_CHANGE);
        quote.setChangePercent(QUOTE_CHANGE_PERCENT);
        try {
            quote.setTimestamp(dateFormat.parse(QUOTE_DATE_STRING));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        quote.setmSDate(QUOTE_MSDATE);
        quote.setMarketCap(50755764235.00);
        quote.setVolume(15159291);
        quote.setChangeYTD(29.74);
        quote.setChangePercentYTD(-12.1217215870881);
        quote.setHigh(0.0);
        quote.setLow(0.0);
        quote.setOpen(26.52);


        quote.setExchange(COMPANY_EXCHANGE);
        quote.setName(QUOTE_NAME);
        quote.setSymbol(QUOTE_SYMBOL);
        return quote;

    }
}