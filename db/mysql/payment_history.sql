CREATE TABLE payment_history (
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     subscriptionId BIGINT,
     userId BIGINT,
     amount BIGINT NOT NULL DEFAULT 0,
     paymentStatus ENUM('PENDING', 'SUCCESS', 'FAILED', 'CANCELLED') NOT NULL DEFAULT 'PENDING',
     paymentDate DATETIME NOT NULL,
     createdAt DATETIME NOT NULL,
);

INSERT INTO payment_history (userId, productId, amount, paymentDate)
VALUES
    (1, 1, 1500, NOW()),
    (2, 2, 2500, NOW()),
    (3, 3, 3000, NOW()),
    (4, 4, 1200, NOW()),
    (5, 5, 1800, NOW());
