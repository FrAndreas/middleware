package test;

import static org.junit.Assert.assertTrue;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Vector;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ChatTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAdresse() throws UnknownHostException {
		boolean test = false;
		InetAddress monIP = InetAddress.getLocalHost();
		String adServer = monIP.getHostAddress();
		String adresseLocale = "192.168.1.37";
		if (adServer == adresseLocale) {
			assertTrue(test);
		}
	}

	@Test
	public void testLogin() {
		String login = "monNom";
		boolean testLogin = false;
		Vector vector = null;
		vector.add(login);
		if (vector.size() > 1) {
			assertTrue(testLogin);
		}
	}
}
