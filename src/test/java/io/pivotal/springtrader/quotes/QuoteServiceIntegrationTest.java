package io.pivotal.springtrader.quotes;

import io.pivotal.springtrader.quotes.domain.Stock;
import io.pivotal.springtrader.quotes.exceptions.SymbolNotFoundException;
import io.pivotal.springtrader.quotes.repositories.StockRepository;
import io.pivotal.springtrader.quotes.services.QuoteService;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static io.pivotal.springtrader.quotes.TestConfig.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

/**
 * Tests the QuoteService.
 *
 * @author David Ferreira Pinto
 * @author cq
 */
@Category(IntegrationTests.class)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {QuotesApplication.class})
@ActiveProfiles("test")
public class QuoteServiceIntegrationTest {

    @Autowired
    QuoteService quoteService;

    @Autowired
    StockRepository stockRepository;



//    @After
//    public void tearDown() throws Exception {
//
//        stockRepository.deleteAll();
//
//    }

    /**
     * Tests retrieving a stock from the external quoteService.
     *
     * @throws Exception
     */
    @Test
    public void getQuote() throws Exception {
        Stock quote = quoteService.getQuote(QUOTE_SYMBOL);
        assertEquals(QUOTE_SYMBOL, quote.getSymbol());
        assertEquals(QUOTE_NAME, quote.getName());
    }

    /**
     * Tests retrieving a stock with an unknown/null symbol from the external quoteService.
     *
     * @throws Exception
     */
    @Test(expected = SymbolNotFoundException.class)
    public void getNullQuote() throws Exception {
        quoteService.getQuote(NULL_QUOTE_SYMBOL);
    }

    /**
     * tests retrieving company information from external quoteService.
     *
     * @throws Exception
     */
    @Test
    public void getCompanyInfo() throws Exception {
        List<Stock> comps = quoteService.companiesByNameOrSymbol(QUOTE_SYMBOL);
        assertFalse(comps.isEmpty());
        boolean pass = false;
        for (Stock info : comps) {
            if (info.getSymbol().equals(QUOTE_SYMBOL)) {
                pass = true;
            }
        }
        assertTrue(pass);
    }

    /**
     * tests retrieving company information from external quoteService with unkown company.
     *
     * @throws Exception
     */
    @Test
    public void getNullCompanyInfo() throws Exception {
        List<Stock> comps = quoteService.companiesByNameOrSymbol(NULL_QUOTE_SYMBOL);
        assertTrue(comps.isEmpty());
    }

    @Test
    public void searchForCompanies() throws Exception {
        List<Stock> comps = quoteService.companiesByNameOrSymbol("Microsoft");
        comps.stream().forEach(System.out::println);
        assertThat(comps.size(), equalTo(1));
        assertThat(comps.stream().anyMatch(c -> c.getSymbol().equalsIgnoreCase("MSFT")), equalTo(true));

    }

}
