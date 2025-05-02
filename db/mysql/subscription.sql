CREATE TABLE subscriptions (
       subscription_id BIGINT AUTO_INCREMENT PRIMARY KEY,
       user_id BIGINT NOT NULL,
       product_id BIGINT NOT NULL,
       next_payment_date DATE NOT NULL,
       status ENUM('ACTIVE', 'PAUSED', 'CANCELLED') DEFAULT 'ACTIVE',
       quantity INT DEFAULT 1,
       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
