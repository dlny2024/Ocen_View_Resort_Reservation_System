-- Schema for Ocen_View_Resort_Reservation_System v1.0
CREATE DATABASE IF NOT EXISTS ocean_view_resort DEFAULT CHARACTER SET utf8mb4;
USE ocean_view_resort;

CREATE TABLE IF NOT EXISTS user (
  user_id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) UNIQUE NOT NULL,
  password VARCHAR(200) NOT NULL,
  role ENUM('admin','customer') NOT NULL DEFAULT 'customer'
);

CREATE TABLE IF NOT EXISTS guest (
  guest_id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  address VARCHAR(250),
  contact VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS room (
  room_id INT AUTO_INCREMENT PRIMARY KEY,
  room_type VARCHAR(50) NOT NULL,
  price_per_night DECIMAL(10,2) NOT NULL,
  room_status VARCHAR(20) NOT NULL DEFAULT 'Available'
);

CREATE TABLE IF NOT EXISTS reservation (
  reservation_id INT AUTO_INCREMENT PRIMARY KEY,
  guest_id INT NOT NULL,
  room_id INT NOT NULL,
  check_in_date DATE NOT NULL,
  check_out_date DATE NOT NULL,
  total_price DECIMAL(10,2),
  CONSTRAINT fk_res_guest FOREIGN KEY (guest_id) REFERENCES guest(guest_id),
  CONSTRAINT fk_res_room  FOREIGN KEY (room_id)  REFERENCES room(room_id)
);

INSERT INTO user(username, password, role) VALUES ('admin','admin123','admin')
ON DUPLICATE KEY UPDATE username = username;
