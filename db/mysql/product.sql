CREATE TABLE product (
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     name VARCHAR(255) NOT NULL,
     description TEXT,
     price BIGINT NOT NULL DEFAULT 0,
     imageUrl VARCHAR(255),
     stockCount INT NOT NULL DEFAULT 0,
     createdAt DATETIME NOT NULL,
     updatedAt DATETIME NOT NULL
);

INSERT INTO product (name, description, price, imageUrl, stockCount, createdAt, updatedAt)
VALUES
    ('Product A', 'Description of Product A', 15000, 'http://example.com/imageA.jpg', 100, NOW(), NOW()),
    ('Product B', 'Description of Product B', 25000, 'http://example.com/imageB.jpg', 50, NOW(), NOW()),
    ('Product C', 'Description of Product C', 30000, 'http://example.com/imageC.jpg', 200, NOW(), NOW()),
    ('Product D', 'Description of Product D', 12000, 'http://example.com/imageD.jpg', 75, NOW(), NOW()),
    ('Product E', 'Description of Product E', 18000, 'http://example.com/imageE.jpg', 120, NOW(), NOW());
