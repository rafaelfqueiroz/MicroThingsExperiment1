# MicroThingsExperiment1

Este respositório faz parte de um experimento, o qual tem como objetivo analisar os impactos de padrões de Microserviços 
aplicados em sistemas de Internet das Coisas (IoT). 

A princípio, o padrão estudado é o Circuit Breaker, que funciona como um disjuntor de energia, ou seja, 
uma vez que ele detecta uma falha no fluxo, ele abre (ou corta) o cirtuito, a fim de evitar uma falha em 
cascata nas demais instâncias do sistema.

Neste repositório, é possível enctontrar quatro projetos - desenvolvidos em Java - destinados representarem os Microserviços
envolvidos em um ecossistema IoT.

## Caller

O caller é um serviço que realizada requisições pelos dados dos dispositivos IoT. Através dele, é possível iniciar o fluxo do ecossistema, recebendo uma requisição e, em seguida, delegando a chamada para o Gateway, ou diretamente para os dispositivos.

O Caller tem dependência do projeto **Circuit Breaker**, pois ele pode utilizar deste artifício para evitar uma falha em cascata.
  
  #### Argumentos
   * ` spring.profiles.active `: define os profiles de configuração do Caller. 
      * O profile `average` configura o Caller para computar os dados oriundos dos dispositivos, realizando uma média deles.
      * O profile `gatewayRequest` configura o Caller deve executar as requisições para o Gateway, em vez de requisitar diretamente para os microserviços de IoT Device.
      * `deviceCBRequest` configura o Caller de modo a utilizar o CB para realizar requisições diretamenten aos dispositivos.
      * `deviceRequest` configura o Caller para realizar requisições diretamenten aos dispositivos, sem CB e Gateway.
      * `EnableCircuitBreaker` permite a criação da classe CircuitBreakerConfig, que contem a anotação @EnableCircuitBreaker, habilitando o comportamento do CB.
   
   * `server.port`: define a porta em que o Caller estará executando.
   * `request.host`: define o endereço de host do Gateway para o qual o Caller realizará as requisições.
   * `request.port`: define a porta do Gateway para o qual o Caller realizará as requisições.
   * `request.timeout`: define o tempo de timeout para as requisições.

   ##### Exemplo
    --spring.profiles.active=average,gatewayRequest --server.port=8081 --request.host=localhost --request.port=8086

## Circuit Breaker

  O Circuit Breaker é uma biblioteca, que por si só não é executável. Porém, os projetos que utilizam esta biblioteca precisam definir as configurações dela, através dos seguintes argumentos:
   #### Argumentos
  * `spring.profiles.active `
    * `cacheStrategy` define que o Circuit Breaker deve utilizar a estragégia de fallback de Cache, caso ele identifique uma falha.
    * `exceptionStrategy` define que o Circuit Breaker deve utilizar a estratégia de fallback de Exceção, caso ele identifique uma falha.
  * `cache.expirationTime`: se utilizar o profile de **cacheStrategy**, deve-se definir o tempo em que o dado armazenado em cache irá expirar.
  * `request.timeout`: define o tempo que o Circuit Breaker deve aguardar, até disparar um TimeoutException.
  
## IoT Gateway

  O IoT Gateway é o microserviço que recebe as requisições dos Callers, e as redireciona para os microserviços dos dispositivos de destino. Por este motivo, o Gateway possui a dependência do Circuit Breaker, pois ele pode conter uma possível cascata de falhas aos Callers.
  #### Argumentos
  Por utilizar o Circuit Breaker, ele deve definir os argumentos apresentados no tópico referente à biblioteca. Além disso, 
  deve ser definido o argumento de `host`, que define o endereço destino dos dispositivos.

## IoT Device Service

  O IoT Device Service é o microserviço que é executado diretamente nos dispositivos, permitindo a exposição dos dados coletados pelo dispositivo.
  #### Argumentos
  * `serie.file.path`: define o caminho do arquivo, que contém as séries de dados dos dispositivos.
  * `failure.file.path`: define o caminho do arquivo, que contém as informações (momento e duração) das falhas do dispositivo.
  * `timeout.sleepTime`: define o tempo de duração da pausa de processamento do dispositivo, simulando uma falha.
  
## Execução
  Todos os microserviços foram desenvolvidos utilizando o Spring Boot e possuem o Gradle como gerenciador de dependencias e build. Sendo assim, para inicializar um destes microserviços, é necessário executar o build do gradle, já presente em cada projeto. Para isso, basta executar o comando **./gradlew build**, que iniciará o download das dependências definidas no build.gradle. Uma vez que o build finalizou com sucesso, um arquivo **.jar** foi gerado no sub-diretório target, dentro do próprio projeto. Por ultimo, basta executar o **.jar** gerado, informando os argumentos referentes ao microserviço.
  
Para execução adequada dos microserviços, é necessário realizar uma requisição GET para `/setup` do Caller, pois isso forçará a incialização dos beans dos microserviços.
