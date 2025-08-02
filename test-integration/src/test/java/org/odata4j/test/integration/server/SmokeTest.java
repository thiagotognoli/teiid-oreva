package org.odata4j.test.integration.server;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Test;
import org.odata4j.producer.resources.DefaultODataProducerProvider;
import org.odata4j.test.integration.AbstractJettyHttpClientTest;
import org.odata4j.test.integration.TestInMemoryProducers;

public class SmokeTest extends AbstractJettyHttpClientTest {

  public SmokeTest(RuntimeFacadeType type) {
    super(type);
  }

  @Override
  protected void registerODataProducer() throws Exception {
    DefaultODataProducerProvider.setInstance(TestInMemoryProducers.simple());
  }

  @Test
  public void serviceUrlReturnsOkStatus() throws Exception {
    ContentResponse response = sendRequest(BASE_URI);
    verifyOkStatusIsReturned(response);
  }

  @Test
  public void metaDataUrlReturnsOkStatus() throws Exception {
    ContentResponse response = sendRequest(BASE_URI + "$metadata");
    verifyOkStatusIsReturned(response);
  }

  @Test
  public void feedUrlReturnsOkStatus() throws Exception {
    ContentResponse response = sendRequest(BASE_URI + TestInMemoryProducers.SIMPLE_ENTITY_SET_NAME);
    verifyOkStatusIsReturned(response);
  }

  private void verifyOkStatusIsReturned(ContentResponse response) throws Exception {
    assertThat(response.getStatus(), is(HttpStatus.OK_200));
    assertThat(response.getContentAsString().length(), greaterThan(0));
  }
}
