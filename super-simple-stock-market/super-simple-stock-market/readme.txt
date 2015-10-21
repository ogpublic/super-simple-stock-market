Project name:	Super Simple Stock Market

GIT repository:	https://github.com/ogpublic/super-simple-stock-market.git

Main class:		com.acme.SuperSimpleStockMarket

Description:	Spring project implemented following a multi-layered approach: 
					- presentation layer implemented as a console base client for the Spring application (see com.acme.SuperSimpleStockMarket)
					- businesslogic layer - implements businesslogic related operations (see package com.acme.services.businesslogic)
					- persistence layer - implements persistence related operations (see package com.acme.services.persistence)
					- storage layer - implements in-memory data storage for the entire application (see see package com.acme.storage)
					
Technologies:	- Maven
				- Spring 4.0.1
				- Apache Log4J 2.4.1
				- Appache commons Logging
				- JUnit 4.12
				
Package description:
				- com.acme.domain - contains the domain objects definitions, used across all application layers
				- com.acme.services.businesslogic - contains the businesslogic services definitions (interfaces) and implementations
				- com.acme.services.persistence - contains the persistence services definitions (interfaces) and implementations
				- com.acme.storage - contains the Datastore definitions and implementations
				
Testing:		There are several tests defined under src/test/java. The entire application can be tested itself by running the main class
				defined in com.acme.SuperSimpleStockMarket.
				
Future development: The application can be enhanced further to publish a web service and run into the embedded Java Virtual Machine web server,
					meaning that will not require deployment into a specialized container or application server.