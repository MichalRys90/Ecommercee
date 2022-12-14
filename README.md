# project-jdp-2212-01

project-jdp-2212-01 is a part of Java Developer Plus course by kodilla.com, also known as "Team Project".

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

* `/v1/groups`

  GET method, shows all groups. No parameters.


* `/v1/groups`

  POST method, creates new group. Pass json in request body, i.e:

  ```
  {
        "id": 112,
        "name": "Maski na twarz"
  }
  ```

* `/v1/groups/{groupId}`

  GET method, retrieves the group with the indicated id.


* `/v1/groups`

  PUT method, updates existing group. Pass json in request body, i.e:

  ```
  {
        "id": 112,
        "name": "Maski na twarz"
  }
  ```

#### Product

* `/v1/products`

  GET method, show all products. No parameters.


* `v1/products/{productId}`

  GET method, retrieves the product with the indicated id. Parameters: productId.


* `/v1/products/{productId}`

  DELETE method, deleting products using the indicated id. Parameters: productId.


* `/v1/products`

  POST method, adds a new product. Pass json in request body, i.e:

  ```
  {
    "id": 2,
        "name": "płaszcz",
        "description": "Pellentesque tempus interdum quam ut rhoncus. Donec ullamcorper turpis dolor. Donec euismod pretium eros et eleifend. Aliquam vulputate faucibus lorem non auctor. Vivamus erat turpis, molestie a nisl non, scelerisque luctus enim. Nunc mi mi, laoreet ac mollis nec, pharetra sit amet tortor. Vivamus a bibendum purus.",
        "price": 150,
    "groupId": "321"
  }
  ```


* `/v1/products`

  PUT method, updates existing product. Pass json in request body, i.e:

  ```
  {
    "id": 2,
        "name": "płaszcz",
        "description": "Pellentesque tempus interdum quam ut rhoncus. Donec ullamcorper turpis dolor. Donec euismod pretium eros et eleifend. Aliquam vulputate faucibus lorem non auctor. Vivamus erat turpis, molestie a nisl non, scelerisque luctus enim. Nunc mi mi, laoreet ac mollis nec, pharetra sit amet tortor. Vivamus a bibendum purus.",
        "price": 150,
    "groupId": "321"
  }
  ```

#### Cart


* `/v1/carts/emptyCart`

  POST method, creates an empty cart. Pass json in request body, i.e:

  ```
  {
       "userId": "387"
  }
  ```



* `/v1/carts/getCart/{cardId}`

  GET method, retrieves the cart with the indicated id. Parameters: cartId.


* `/v1/carts/addProducts/{cartId}/{productId}`

  PUT method, adds the indicated product to the indicated cart. Parameters: cartId, productId.


* `/v1/carts/{cardId}/{productId}`

  DELETE method, removes the given product from the cart. Parameters: cartId, productId.


* `/v1/carts/createOrder/{cardId}`

  POST method, creates an order from the indicated cart. Parameters: cartId.

#### Order

* `/v1/orders`

  GET method, fetches all orders. No parameters.


* `/v1/orders/{orderId}`

  GET method, retrieves the order with the indicated id. Parameters: orderId.


* `/v1/orders/{orderId}`

  DELETE method, deleting order using the indicated id. Parameters: orderId.


* `/v1/orders`

  POST method, creates a new order. Pass json in request body, i.e:

  ```
  {
        "userId": 387,
        "dateTime": "2022-12-07T16:15:22",
        "cartStatus": "ORDER",
        "cartItems": [
            {
                "product_id": 322,
                "quantity": 3,
                "unitPrice": 100.00,
                "productName": "kurtka zimowa",
                "totalPrice": 300.00
            },
            {
                "product_id": 346,
                "quantity": 2,
                "unitPrice": 100.00,
                "productName": "płaszcz",
                "totalPrice": 200.00
            }
        ]
    }
  ```  

* `/v1/orders`

  PUT method, update given order. Pass json in request body, i.e:


  ```
  {
        "id": 272,
        "userId": 387,
        "dateTime": "2022-12-07T16:15:22",
        "cartStatus": "ORDER",
        "cartItems": [
            {
                "product_id": 322,
                "order_id": 387,
                "quantity": 3,
                "unitPrice": 100.00,
                "productName": "kurtka zimowa",
                "totalPrice": 300.00
            },
            {
                "product_id": 346,
                "orderId": 387,
                "quantity": 2,
                "unitPrice": 100.00,
                "productName": "płaszcz",
                "totalPrice": 200.00
            }
        ]
  }
  ```

#### User


* `/v1/users/GetUsers`

  GET method, retrieves all users from the database. No parameters.


* `/v1/users/BlockUser`

  PUT method, blocks the selected user. Parameters: userId.


* `/v1/users/CreateUser`

  POST method, creates a new user. Pass json in request body, i.e:

  ```
  {
        "username": "User", 
        "firstName": "Jan",
        "lastName": "Nowak",
        "password": "Password123"
  } 
  ```


* `/v1/users/login`

  POST method, parameters: String username, String password.

  Retrieves a token in response, valid for one hour.

  **All requests, except for create user and login,
  need to pass a token in header, to get access to the service.**


* `/v1/users/activities/{userId}`

  GET method, retrieves all activities by user with given userId.


### How can I use it?

This project can be used as an online shopping cart.

### Troubleshooting

We have not discovered any problem.
