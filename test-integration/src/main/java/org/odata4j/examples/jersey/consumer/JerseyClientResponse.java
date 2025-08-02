package org.odata4j.examples.jersey.consumer;

import jakarta.ws.rs.core.MultivaluedMap;

import org.odata4j.consumer.ODataClientResponse;

import org.glassfish.jersey.client.ClientResponse;

public class JerseyClientResponse implements ODataClientResponse {

  private ClientResponse clientResponse;

  public JerseyClientResponse(ClientResponse clientResponse) {
    this.clientResponse = clientResponse;
  }

  public ClientResponse getClientResponse() {
    return clientResponse;
  }

  @Override
  public MultivaluedMap<String, String> getHeaders() {
    return clientResponse.getHeaders();
  }

  @Override
  public void close() {
  }

}
