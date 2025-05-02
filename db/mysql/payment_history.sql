CREATE TABLE payment_history (
     payment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
     user_id BIGINT NOT NULL,
     subscription_id BIGINT,
     amount DECIMAL(10, 2) NOT NULL,
     payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     status ENUM('SUCCESS', 'FAILED') DEFAULT 'FAILED'
);