CREATE DATABASE IF NOT EXISTS `property-rental-db`;
USE `property-rental-db`;

CREATE TABLE IF NOT EXISTS accounts (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(120) NOT NULL,
  email VARCHAR(190) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,
  role VARCHAR(20) NOT NULL,
  created_at DATETIME
);
CREATE TABLE IF NOT EXISTS chat_messages (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  sender_email VARCHAR(190) NOT NULL,
  recipient_email VARCHAR(190) NOT NULL,
  property_title VARCHAR(255) NOT NULL,
  body VARCHAR(2000) NOT NULL,
  created_at DATETIME NOT NULL
);

-- Tables are created automatically by Spring Boot JPA when the backend starts.
-- Optional reference schema:
CREATE TABLE IF NOT EXISTS properties (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(255) NOT NULL,
  location VARCHAR(255) NOT NULL,
  city VARCHAR(255) NOT NULL,
  property_type VARCHAR(255) NOT NULL,
  monthly_rent DECIMAL(12,2) NOT NULL,
  bedrooms INT NOT NULL DEFAULT 0,
  bathrooms INT NOT NULL DEFAULT 0,
  description VARCHAR(2000),
  image_url VARCHAR(1000),
  available BOOLEAN NOT NULL DEFAULT TRUE,
  owner_name VARCHAR(255),
  created_at DATETIME
);
CREATE TABLE IF NOT EXISTS rental_applications (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  property_id BIGINT NOT NULL,
  applicant_name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  phone VARCHAR(50),
  message VARCHAR(1000),
  status VARCHAR(30) NOT NULL DEFAULT 'PENDING',
  applied_at DATETIME
);
CREATE TABLE IF NOT EXISTS payment_records (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  property_id BIGINT,
  payer_name VARCHAR(255),
  amount DECIMAL(12,2),
  status VARCHAR(40),
  created_at DATETIME
);

INSERT INTO properties (title,location,city,property_type,monthly_rent,bedrooms,bathrooms,description,image_url,available,owner_name,created_at)
SELECT 'Modern 2 BHK Apartment','Gachibowli','Hyderabad','Apartment',22000,2,2,'Bright apartment near offices, supermarkets and public transport.','https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?auto=format&fit=crop&w=1200&q=80',TRUE,'Ravi Kumar',NOW()
WHERE NOT EXISTS (SELECT 1 FROM properties WHERE title='Modern 2 BHK Apartment');
INSERT INTO properties (title,location,city,property_type,monthly_rent,bedrooms,bathrooms,description,image_url,available,owner_name,created_at)
SELECT 'Independent Family House','Miyapur','Hyderabad','House',28000,3,2,'Spacious family home with balcony and nearby schools.','https://images.unsplash.com/photo-1564013799919-ab600027ffc6?auto=format&fit=crop&w=1200&q=80',TRUE,'Anita Rao',NOW()
WHERE NOT EXISTS (SELECT 1 FROM properties WHERE title='Independent Family House');
INSERT INTO properties (title,location,city,property_type,monthly_rent,bedrooms,bathrooms,description,image_url,available,owner_name,created_at)
SELECT 'Compact Studio Flat','Kondapur','Hyderabad','Studio',12000,1,1,'Affordable furnished studio suited for working professionals.','https://images.unsplash.com/photo-1502672260266-1c1ef2d93688?auto=format&fit=crop&w=1200&q=80',TRUE,'Sandeep Reddy',NOW()
WHERE NOT EXISTS (SELECT 1 FROM properties WHERE title='Compact Studio Flat');

