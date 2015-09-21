package iloveyouboss;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

import util.Http;

public class AddressRetrieverTest {
    @Test
    public void answersAppropriateAddressForValidCoodinates() throws Exception {
        Http http = mock(Http.class);
        when(http.get(contains("lat=38.000000&lon=-104.000000"))).thenReturn(
                "{\"address\":{"
                + "\"house_number\":\"324\","
                + "\"road\":\"North Tejon Street\","
                + "\"city\":\"Colorado Springs\","
                + "\"state\":\"Colorado\","
                + "\"postcode\":\"80903\","
                + "\"country_code\":\"us\"}"
                + "}");
        AddressRetriever retriever = new AddressRetriever(http);
        Address address = retriever.retrieve(38.0, -104.0);

        assertThat(address.houseNumber, is("324"));
        assertThat(address.road, is("North Tejon Street"));
        assertThat(address.city, is("Colorado Springs"));
        assertThat(address.state, is("Colorado"));
        assertThat(address.zip, is("80903"));
    }
}
