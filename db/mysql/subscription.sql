CREATE TABLE subscription (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    userId BIGINT,
    productId BIGINT,
    subscriptionDate DATETIME NOT NULL,
    subscriptionExpirationDate DATETIME NOT NULL,
    subscriptionStatus ENUM('ACTIVE', 'INACTIVE') NOT NULL DEFAULT 'INACTIVE',
    subscriptionCycleType ENUM('YEARLY', 'MONTHLY', 'WEEKLY', 'BIWEEKLY', 'DAILY') NOT NULL,
    cycleDetails JSON,
);

INSERT INTO subscription (userId, productId, subscriptionDate, subscriptionExpirationDate, subscriptionStatus, subscriptionCycleType, cycleDetails)
VALUES
    (1, 1, NOW(), DATE_ADD(NOW(), INTERVAL 2 MONTH), 'ACTIVE', 'MONTHLY', '["1", "15"]'),
    (2, 2, NOW(), DATE_ADD(NOW(), INTERVAL 2 MONTH), 'ACTIVE', 'MONTHLY', '["5", "20"]'),
    (3, 3, NOW(), DATE_ADD(NOW(), INTERVAL 2 YEAR), 'INACTIVE', 'YEARLY', '["03-01", "12-25"]'),
    (4, 4, NOW(), DATE_ADD(NOW(), INTERVAL 3 WEEK), 'ACTIVE', 'BIWEEKLY', '["MONDAY", "THURSDAY"]'),
    (5, 5, NOW(), DATE_ADD(NOW(), INTERVAL 2 DAY), 'ACTIVE', 'DAILY', NULL);
