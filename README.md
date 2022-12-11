# project-jdp-2212-01

Project known as "project-jdp-2212-01" is a part of Java Developer Plus course by kodilla.com, also known as "Team Project". 

A group of five trainees form a team, whose task is to create REST API service implementing backend functionality of the shopping cart, according to the given endpoint specifications.

Working under the supervision of an experienced project manager, the team completes tasks using the Jira platform and Kanban board, while learning teamwork in the Git version control system. 

## API documentation

### Demo

if you want to check how the project works, clone the repository.

### Requirements

* JVM
* MySQL database

### Running the project

You need to create a user in the MySql database. Make settings in the application properties file. The project is compiling with gradle.

### Endpoints' description

#### Group

* /v1/groups - Get method, shows all groups. No parameters.
* /v1/groups - Post method, adds a new group. to add a group we use dto class json(String groupName).
* /v1/groups/{groupId} - Get method, retrieves the group with the indicated id.
* /v1/groups - Put method, using dto updates the group.

#### Product

* /v1/products - Get method, show all products. No parameters.
* /v1/products/{productId} - Get method, retrieves the product with the indicated id. Parameters: productId.
* /v1/products/{productId} - Delete method, deleting products using the indicated id. Parameters: productId.
* /v1/products - Post method, adds a new product. to add a product we use dto class json(String groupName, String description, BigDecimal price, long groupId).
* /v1/products - Put method, using dto updates the product.

#### Cart

* /v1/carts/emptyCart - Post method, creates an empty cart using dto class json(long userId).
* /v1/carts/getCart/{cardId} - Get method, retrieves the cart with the indicated id. Parameters: cartId.
* /v1/carts/addProducts/{cartId}/{productId} - Put method, adds the indicated product to the indicated cart. Parameters: cartId, productId.
* /v1/carts/{cardId}/{productId} - Delete method, removes the given product from the cart. Parameters: cartId, productId.
* /v1/carts/createOrder/{cardId} - Creates an order from the indicated cart. Parameters: cartId.

#### Order

* /v1/orders  - Get method, fetches all orders. No parameters.
* /v1/orders/{orderId} - Get method, retrieves the order with the indicated id. Parameters: orderId.
* /v1/orders/{orderId} - Delete method, deleting order using the indicated id. Parameters: orderId.
* /v1/orders - Post method, creates a new order. We pass dto as a parameter(long userId, LocalDateTime dateTime, CartStatus cartStatus, Set cartItems).
* /v1/orders - Put method, changes the current order. We pass dto as a parameter(long userId, LocalDateTime dateTime, CartStatus cartStatus, Set cartItems).

#### User

* /v1/users/GetUsers - Get method, retrieves all users from the database. No parameters.
* /v1/users/BlockUser - Put method, blocks the selected user. Parameters: userId.
* /v1/users/CreateUser - Post method, creates a new user. We pass dto as a parameter(String username, String firstName, String lastName, String password).
* /v1/users/login - Post method, creates a token for the user, thanks to which he can use endpoints. Parameters: String username, String password.

### How can I use it?

This project can be used as an online shopping cart.

### Troubleshooting
 
We have not discovered any problem.
