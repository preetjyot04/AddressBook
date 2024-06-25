  use addressbook;

  CREATE TABLE `contact` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ;


CREATE TABLE `address_book` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);


CREATE TABLE `address_book_contacts` (
  `address_book_id` bigint NOT NULL,
  `contacts_id` bigint NOT NULL,
  PRIMARY KEY (`address_book_id`,`contacts_id`),
  UNIQUE KEY `UK73regql2qacbytm0limyyyu1a` (`contacts_id`),
  CONSTRAINT `FKmm4butuma1cybq35nabcixbvy` FOREIGN KEY (`address_book_id`) REFERENCES `address_book` (`id`),
  CONSTRAINT `FKouws6y9lrubtsoqi6d3vktsfr` FOREIGN KEY (`contacts_id`) REFERENCES `contact` (`id`)
) ;

