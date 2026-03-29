CREATE TABLE movie (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100)
);

CREATE TABLE theatre (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100),
  city VARCHAR(100)
);

CREATE TABLE show_details (
  id INT AUTO_INCREMENT PRIMARY KEY,
  movie_id INT,
  theatre_id INT,
  show_time VARCHAR(20),
  show_date DATE,
  total_seats INT DEFAULT 100,
  available_seats INT DEFAULT 100
);

CREATE TABLE seat (
  id INT AUTO_INCREMENT PRIMARY KEY,
  show_id INT,
  seat_number VARCHAR(10),
  is_booked BOOLEAN DEFAULT FALSE,
  FOREIGN KEY (show_id) REFERENCES show_details(id)
);

CREATE TABLE booking (
  id INT AUTO_INCREMENT PRIMARY KEY,
  show_id INT,
  user_id VARCHAR(100),
  base_price DECIMAL(10,2),
  discount_amount DECIMAL(10,2) DEFAULT 0.00,
  final_price DECIMAL(10,2),
  status VARCHAR(20) DEFAULT 'CONFIRMED',
  booked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (show_id) REFERENCES show_details(id)
);

CREATE TABLE booking_seat (
  id INT AUTO_INCREMENT PRIMARY KEY,
  booking_id INT,
  seat_id INT,
  FOREIGN KEY (booking_id) REFERENCES booking(id),
  FOREIGN KEY (seat_id) REFERENCES seat(id)
);
