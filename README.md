# Shopping Cart 🛒

Shopping Cart is a sample code used to validate coding best practices using the Java language.

## Installation

Import the project in your favorite IDE with gradle or run it with the gradle wrapper using:

```bash
./gradlew run
```

## Usage

The exercise is used to:

* Validate the correct usage of keywords like `final`, `synchronized`, among others as well as object-oriented programming best practices by applying OOP, SOLID, GRASP and design principles and patterns.

* Validate coding best practices by promoting a refactor of the code taking advantage that the exercise has some code smells, on purpose, in order to challenge the candidate to find them and refactor the code with the objective to improve it.

* Understand the unit testing practices of the candidate by making use of unit tests that should be completed based on unit testing best practices.

* Understand that the candidate knows how to build java applications using modern application frameworks like Spring, Micronaut, Quarkus, Helidon, among others by applying coding best practices, design principles, and patterns.

## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

Please make sure to update tests as appropriate.

## Homework

Create the application shopping-cart using the application framework of your preference. It could be Spring Boot, Quarkus, Micronaut, Helidon, or similar. Through a Rest API, the system should be able to manage shopping carts. That means that we can add and remove items to and from shopping carts as well as getting the state of them. This is an example of the shopping cart rendered through a UI (UI is out of scope).

|Quantity  | Product |Unit Price |Subtotal|
|--|--|--|--|
| 2 | Banana     | 2,000.00 | 4,000.00  |
| 3 | Orange     | 1,000.00 | 3,000.00  |
| 3 | Strawberry | 2,000.00 |  6,000.00 |

Total shopping cart: 13,000.00

Total payment: 14,060.00

The logic for the total and the total payment is given by the class com.perficient.shoppingcart.domain.ShoppingCart.
The application should:

1. Expose the operations through a Rest API that follows the Rest API best practices.
1. Have persistence including database scripts and configurations required for it.
1. Contain unit tests as well as integration tests that follow the testing best practices.
1. Have a README file that explains how to start the application and consume the services. If the API has a live specification like Swagger, it must specify the endpoint.
1. Allow to get the total payment based on the payment method specified through the API. Remember that different payment methods derive in different values.
1. Allow to add new payment methods by following design best practices and patterns.
1. Implement security for the API and OWASP best practices.
1. In general, have design and architecture best practices.
1. Make assumptions and explain them in the README.
1. Send the code compressed in a zip file to <evaluator’s email>. Source code only.
