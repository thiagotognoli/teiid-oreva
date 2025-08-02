package org.odata4j.test.integration;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;

/**
 * Base integration test class that uses a Jetty HTTP client.
 */
public abstract class AbstractJettyHttpClientTest extends AbstractIntegrationTest {

  /**
   * The HttpClient instance.
   */
  protected HttpClient client;

  public AbstractJettyHttpClientTest(RuntimeFacadeType type) {
    super(type);
  }

  @Override
  protected void startClient() throws Exception {
    client = new HttpClient();
    client.start();
  }

  @Override
  protected void stopClient() throws Exception {
    client.stop();
  }

  /**
   * Helper method to send an HTTP request.
   */
  protected ContentResponse sendRequest(String url) throws Exception {
    ContentResponse response = client.GET(url);
    return response;
  }
}
