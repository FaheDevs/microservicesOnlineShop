# microservicesOnlineShop

This repository contains a set of microservices designed to provide various functionalities for an e-commerce platform. These services interact with each other to collectively create a seamless experience for both customers and administrators. The microservices included are as follows:

## 1. Product Service

The Product Service acts as a product catalog, allowing users to create and view products. This service maintains a repository of products and their details, making it an essential component of the e-commerce ecosystem.

## 2. Order Service

The Order Service handles the process of placing and managing orders. Customers can utilize this service to select products and initiate the order process. It facilitates the creation, tracking, and management of orders, ensuring a smooth and efficient checkout experience.

## 3. Inventory Service

The Inventory Service plays a critical role in ensuring product availability. Customers and the system can check the availability of products through this service. It keeps track of product stock levels and provides real-time information to prevent over-selling and improve order accuracy.

## 4. Notification Service

The Notification Service is responsible for sending notifications to relevant parties. After an order is successfully placed, this service communicates with users to provide updates on order status, payment confirmation, and delivery information. It enhances customer communication and keeps them informed throughout the order fulfillment process.


## Interactions and Communication

These microservices interact with each other in both synchronous and asynchronous ways:

- **Synchronous Communication:** Certain interactions require immediate responses. For instance, when a customer places an order, the Order Service might need to query the Inventory Service to ensure the products are available. Synchronous communication allows for real-time decision-making.

- <img width="648" alt="image" src="https://github.com/FaheDevs/microservicesOnlineShop/assets/71198261/c3d9051c-3f64-4897-89f0-78aecd0e6a11">


- **Asynchronous Communication:** Some interactions don't require immediate responses and can be handled in the background. For instance, once an order is successfully placed, the Notification Service can send out order confirmation emails or push notifications. This type of communication enhances system scalability and responsiveness.


