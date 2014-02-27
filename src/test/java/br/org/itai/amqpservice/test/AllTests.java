package br.org.itai.amqpservice.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AddressAnnotationsTest.class, CommunicationTest.class,
		MessageConverterTest.class })
public class AllTests {

}
