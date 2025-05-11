CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    createdAt DATETIME NOT NULL,
    updatedAt DATETIME NOT NULL
);

INSERT INTO user (name, email, password, createdAt, updatedAt)
VALUES
    ('John Doe', 'johndoe@example.com', 'password123', NOW(), NOW()),
    ('Jane Smith', 'janesmith@example.com', 'mypassword456', NOW(), NOW()),
    ('Alice Johnson', 'alice.johnson@example.com', 'alicepassword789', NOW(), NOW()),
    ('Bob Brown', 'bobbrown@example.com', 'bobpassword000', NOW(), NOW()),
    ('Charlie Davis', 'charliedavis@example.com', 'charliepass2025', NOW(), NOW()),
    ('David Wilson', 'davidwilson@example.com', 'davidpass999', NOW(), NOW()),
    ('Emily Taylor', 'emilytaylor@example.com', 'emilypass1234', NOW(), NOW()),
    ('Frank Miller', 'frankmiller@example.com', 'frankpass5678', NOW(), NOW()),
    ('Grace Lee', 'gracelee@example.com', 'gracepass7890', NOW(), NOW()),
    ('Hannah Moore', 'hannahmoore@example.com', 'hannahpassword1', NOW(), NOW());
