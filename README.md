# AddressBook
Address book  holds name and phone numbers of
contact entries

### Features

* User is able to add new contact entries.
* User is  able to remove existing contact
  entries.
* Users can print all contacts in an
  address book.
* Users can maintain multiple address
  books.
* Users can print a unique set of all
  contacts across multiple address books
* Unit test cases are written with Junit and mockito and jacoco is used for checking code coverage. 


### API Spec.

All apis are present in Swagger which can be accessed with below url 

``
<Base_URL>/swagger-ui.html
``
![image](https://github.com/preetjyot04/AddressBook/assets/161794560/ef429116-c3fa-4af6-8f5f-b685148c3f53)


### Setup.
* Docker image has been pushed under public account with name 
``
jyotpreet000/addressbook-app
``
* All deployment and service files are present under src/main/resources, follow below commands to deploy Addressbook on kubernetes


``
kubectl apply -f mysql-deployment.yml
``

``
kubectl apply -f deployment.yml
``

* Once the pods are up, access database with below commands and execute the queries under resources folder. 

`` 
kubectl exec -it <MySQL-POd-NAME> bash
``

``
  mysql -h mysql -u root -p 
``
* Enter password as root( you can update the same in mysql-deployment.yml file if required)
* show databases
* Database addressbook will automatically be created, now you can run the qureries mentioned under resource folder. 

### Now Application can be accessible on browser.






