package org.odata4j.test.integration;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.After;
import org.junit.Before;
import org.odata4j.producer.server.ODataServer;
import org.odata4j.test.integration.AbstractRuntimeTest.RuntimeFacadeType;

/**
 * Base integration test class that:
 * <ol>
 * <li>starts an ODataServer,</li>
 * <li>registers an ODataProducer,</li>
 * <li>and starts a client</li>
 * </ol>
 */
public abstract class AbstractIntegrationTest extends AbstractRuntimeTest {

  private static final AtomicInteger portCounter = new AtomicInteger(9000);

  private static int getNextAvailablePort() {
    int basePort = portCounter.getAndIncrement();
    // Try the base port first, then increment until we find an available port
    for (int port = basePort; port < basePort + 100; port++) {
      try (ServerSocket socket = new ServerSocket(port)) {
        return port;
      } catch (IOException e) {
        // Port is in use, try the next one
      }
    }
    throw new RuntimeException("Could not find an available port starting from " + basePort);
  }

  protected final String BASE_URI;

  /**
   * The ODataServer instance.
   */
  protected ODataServer server;

  public AbstractIntegrationTest(RuntimeFacadeType type) {
    super(type);
    // Each test instance gets its own port to avoid conflicts
    int port = getNextAvailablePort();
    this.BASE_URI = "http://localhost:" + port + "/test.svc/";
  }

  @Before
  public void setup() throws Exception {
    startODataServer();
    registerODataProducer();
    startClient();
  }

  @After
  public void teardown() throws Exception {
    stopClient();
    stopODataServer();
  }

  protected void startODataServer() throws Exception {
    server = rtFacade.startODataServer(BASE_URI);
  }

  protected abstract void registerODataProducer() throws Exception;

  protected abstract void startClient() throws Exception;

  protected abstract void stopClient() throws Exception;

  protected void stopODataServer() throws Exception {
    if (server != null) {
      server.stop();
    }
  }
}
