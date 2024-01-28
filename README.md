# Customer Onboarding Portal API

Welcome to the Customer Onboarding Portal API project! This API is designed for managing customer subscriptions to cloud software services.


## Getting Started

### Prerequisites

Before running the project, make sure you have the following installed:
	
- Java 17 or later
- Maven
- IDE  : Eclipse (optional)

### Installation

- Clone the repository:

		https://github.com/Badmavathi/Agilysys-Project.git
	
- Build the project:

		mvn clean install

- Run the application:
		
		mvn spring-boot:run
	
The API should now be accessible at http://localhost:8080.

### Project Structure
		src/main/java: Java source code
		com.customer.onboardpotal  : Main package for the application
						controller : REST controllers
							 model : Data models
						repository : Data access
						validation : Validation
						
		src/test/java: Test source code
			controller: Test for REST controllers
			
		src/main/resources: Application properties


### Endpoints
The API supports the following endpoints:

- Customer API

			GET /onboardingPortal/customers/{id}: Retrieve customer details for customer id as input
			PUT /onboardingPortal/customers/{id}: Update customer details for customer id
			DELETE /onboardingPortal/customers/{id}: Delete a customer id
			GET /onboardingPortal/customers: List all customers
			POST /onboardingPortal/customers: Create a new customer
			DELETE /onboardingPortal/customers: Deletes all customers


- Subscription API

			PUT /customerSubscription/subscribe : Customer subscribes to a service plan with SubscriptionRequestBody as input
			PUT /customerSubscription/resumeSubscription : Customer resumes subscribed service with SubscriptionRequestBody as input
			PUT /customerSubscription/pauseSubscription : Customer pauses subscribed service with SubscriptionRequestBody as input
			GET /customerSubscription/subscription-plans : Lists all the plans available for subscription.
			GET /customerSubscription/service-types : Lists all the Services available for subscription.



### Database

	The application uses Spring Boot, and you can configure it through application.properties or environment variables. application.properties is configured to support H2 in-memory database. 
	H2 database can be connected using the below credentials when application is running : 
	
		 http://localhost:8080/h2-ui/ 
	
			JDBC URL = jdbc:h2:file:./testdb
			Driver class =org.h2.Driver
			Username=sa

### Testing
	
- To Test the application :
		
		mvn test

	


### Documentation
		API documentation can be generated using Swagger2. Access the Swagger UI at 
			http://localhost:8080/swagger-ui/index.html#/ 
			when the application is running.
		
### Scope for improvements : 

			1. API Security : You can secure the API using Spring Security, JWT, or OAuth.
			2. Subscription plans and types can be extended into providing specific services supporting different attributes.
			3. Enhanced validations and error handling could be added appropriate to the functionalities.
			4. Logging should be added throughout the application for better debugging and monitoring. (Ex : Log4j)
			5. Test coverage could be expanded, including edge cases and negative scenarios.
			6. Implement monitoring and metrics to track the health and performance of the application. (Ex : Springboot actuator)
			7. API versioning to manage changes over time.
			8. Adjust configurations, logging levels, and other settings for production deployment.
			9. Implement strategies for handling sensitive information in production.
			10.Consider implementing internationalization and localization for supporting multiple languages and regions(time zone). 

### Postman : 
		Endpoints can be tested from Postman. Postman_collection.json is available in the project, which can be imported directly to Postman to run all the endpoints.
