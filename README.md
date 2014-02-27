#TODO - Revisar e terminar
---

# AMQP Service API
---

API para auxílio ao desenvolvimento de serviços via AMQP com Qpid e Java.

Tem por objetivo simular o comportamento de um serviço request/response sobre o broker AMQP, obtendo assim, comportamento similar a um serviço web.

Definição de serviços e mensagens é realizada de forma orientada a anotações e interfaces tornando transparente a implementação do serviço.

Passos da invocação de um serviço:

* Mapeamento da identificação do broker e fila a partir das anotações de serviço;
* Mapeamento da mensagem a ser enviada a partir das anotações de mensagem em formato JSON; 
* Envio da mensagem, juntamente com uma fila temporária para resposta por meio da api JMS do Qpid;
* Escuta e recebimento da mensagem de resposta a partir da fila temporária criada para este fim;
* Conversão da mensagem em texto no formato JSON para o objeto especificado de resposta.

### Maven

	<dependency>
      <groupId>br.org.itai.amqp</groupId>
      <artifactId>amqp-service-api</artifactId>
      <version>0.1-SNAPSHOT</version>
    </dependency>
    
    <repositories>
		<repository>
			<id>br.org.itai</id>
			<name>ITAI Internal Repository</name>
			<url>http://repository.itai.org.br/repository/internal/</url>
			<snapshots>
				<updatePolicy>always</updatePolicy>
			</snapshots>
		</repository>
	</repositories>
	
	<pluginRepositories>
		<pluginRepository>
			<id>br.org.itai</id>
			<name>ITAI Internal Repository</name>
			<url>http://repository.itai.org.br/repository/internal/</url>
			<snapshots>
				<updatePolicy>always</updatePolicy>
			</snapshots>
		</pluginRepository>
	</pluginRepositories>
    
## Anotações disponiveis
---

### Serviço

@ServiceAddress
@ServiceQueue

### Mensagem

@Param

### Configuração

@QueueConfig
@AddressConfig
@ServiceConfig

## Mensagem

	{
	  "service" : "<nome serviço>",
	  "args" : {
	    "<parametro especificado 1>" : "<tipo de dado>",
	    "<parametro especificado 2>" : "<tipo de dado>" 
	  }
	}

## Exemplo de Utilização
---

#### UmrIntegrationService.java

	import br.org.itai.amqpservice.proxyservice.annotations.param.Param;
	import br.org.itai.amqpservice.proxyservice.annotations.param.config.QueueConfig;
	import br.org.itai.amqpservice.proxyservice.annotations.service.ServiceAddress;
	import br.org.itai.amqpservice.proxyservice.annotations.service.ServiceQueue;
	
	@ServiceAddress("amqp://192.168.1.185/default?brokerlist='tcp://192.168.1.185:5672?sasl_mechs='ANONYMOUS''")
	public interface UmrIntegrationService {
		
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
	
#### ResponseMessage.java
	
	public class ResponseMessage {

	    public String status;

    }


#### Uso com CDI:

	@Bean
	public UmrIntegrationService integrationService() throws Exception{
		UmrIntegrationService uis = new AMQPService(new QpidConnectionFactory(),
				new QpidDestinationFactory(), new ConverterFactoryImpl())
				.create(UmrIntegrationService.class);
		return uis;
	}
---
	@Inject
	private UmrIntegrationService uis;
---

## Notas

* A especificação do endereço do broker deve seguir a nomenclatura definida pelo [Qpid-JMS](http://people.apache.org/~jonathan/High-Level-API.html#id3068796).
* A estrutura da mensagem a ser enviada segue o padrão especificado na seção de mensagens, apenas a mensagem de resposta é dinâmica, sendo especificada como o retorno do serviço invocado.
* A mensagem de resposta será convertida de JSON para um objeto da classe especificada de retorno. Assim, a mensagem de resposta em texto do broker deve estar no formato JSON e ser equivalente a Classe Java especificada. 

# Changelogs
---

#### Versão 0.1 (SNAPSHOT)
* Suporte a definição estática e dinâmica dos serviços e filas do broker AMQP via notações;
* Suporte a composição da mensagem via anotações;
* Conversão da mensagem de resposta para objeto por meio da especificação da classe de retorno do método invocado. 
* Conversão da mensagem para envio no formato JSON;
* Conexão com o broker Qpid via JMS;

## Criado por

Anderson Rodrigo Davi
Jhonny Marcos Acordi Mertz