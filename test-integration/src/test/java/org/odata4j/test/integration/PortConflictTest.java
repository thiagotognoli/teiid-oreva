package org.odata4j.test.integration;

import org.junit.Test;
import static org.junit.Assert.*;

public class PortConflictTest extends AbstractIntegrationTest {

    public PortConflictTest(RuntimeFacadeType type) {
        super(type);
    }

    @Override
    protected void registerODataProducer() throws Exception {
        // No producer needed for this test
    }

    @Override
    protected void startClient() throws Exception {
        // No client needed for this test
    }

    @Override
    protected void stopClient() throws Exception {
        // No client needed for this test
    }

    @Test
    public void testUniquePortPerInstance() throws Exception {
        // Create another instance to verify different ports
        PortConflictTest otherTest = new PortConflictTest(RuntimeFacadeType.JERSEY);

        // Verify that instances get different BASE_URIs (and thus different ports)
        assertNotEquals("Different test instances should have different BASE_URIs",
                this.BASE_URI, otherTest.BASE_URI);

        System.out.println("This instance BASE_URI: " + this.BASE_URI);
        System.out.println("Other instance BASE_URI: " + otherTest.BASE_URI);
    }
}
