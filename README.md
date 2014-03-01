# Java AMQP Service

A Java library to support the AMQP communication as a service. Initial support Qpid, however easy to adjust to another AMQP broker using interfaces and factories. It aims to simulate the behavior of a service request/response over AMQP broker, thus getting similar to a web service over HTTP.

> Just set interfaces as services and annotate it to configure messages and connection, then relax and let the library do everything else.

Steps involved in a service:

* Mapping broker configuration from defined config annotations;
* Mapping message to be sent from message annotations; 
* Conversion message in to JSON format;
* Creating a tempory queue to receive the response;
* Sending the message with the created temporary queue through [Qpid JMS API](http://qpid.apache.org/components/qpid-jms/); 
* Listening and receiving the response message from created temporary queue; 
* Conversion of the text message in JSON format to the specified response object.
    
### Available Annotations
#### Service:
* @ServiceAddress
* @ServiceQueue

#### Message:
* @Param

#### Configuration:
* @QueueConfig
* @AddressConfig
* @ServiceConfig

### Message

The created and sent message will follow the above style:

    {
	  "service" : "<service method name>",
	  "args" : {
	    "<param 1>" : "<data type>",
	    "<param 2>" : "<data type>" 
	  }
	}

### Exemple

##### Defining a service interface
	
	@ServiceAddress("amqp://192.168.1.185/default?brokerlist='tcp://192.168.1.185:5672?sasl_mechs='ANONYMOUS''")
	public interface MyService {
		
		@ServiceQueue("umr{ip}.manual_trigger")
		public ResponseMessage manual_trigger(
				@Param("createShortRegister") Boolean shortRegister,
				@Param("createLongRegister") Boolean longRegister,
				@QueueConfig("ip") String umrIp);
	
		@ServiceQueue("umr{ip}.config")
		public ResponseMessage config(
				@Param("config") String configuration,
				@QueueConfig("ip") String umrIp);
	}
	
##### Defining a response message
	
	public class ResponseMessage {
	    public String status;
    }

##### Using the service

    MyService uis = new AMQPService(new QpidConnectionFactory(),
            new QpidDestinationFactory(), new ConverterFactoryImpl())
            .create(MyService.class);
    
    ResponseMessage message = uis.manual_trigger(true, true, "192.168.1.185");
    
    System.out.println(message.status); //print the received message

#### Using with a container support:

##### Spring:
	@Bean
	public MyService integrationService() throws Exception{
		MyService uis = new AMQPService(new QpidConnectionFactory(),
				new QpidDestinationFactory(), new ConverterFactoryImpl())
				.create(MyService.class);
		return uis;
	}
---
	@Autowired
	private MyService uis;

### Notes

* Broker address specification should follow [Qpid-JMS format](http://people.apache.org/~jonathan/High-Level-API.html#id3068796).
* The message to be sent will follow the style specified on section "messages".
* The response message will be converted from JSON format to Object according with specified class in method return. Therefore, the response message should be a JSON-like message and match with the specified return Object.

### Changelogs

#### Version 0.1 (SNAPSHOT)
* Connecting to the broker via Qpid JMS;
* Dynamic and static services and queue configurations through annotations;
* Support for message composition via annotations;
* Conversion of the text message to send in JSON format; 
* Conversion of the text response to object based on specified class by the invoked method return. 

## Created By
* Anderson Rodrigo Davi
* Jhonny Marcos Acordi Mertz
