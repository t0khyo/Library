-- Insert ROLE_USER if it doesn't exist
INSERT INTO role (name)
SELECT 'ROLE_USER'
WHERE NOT EXISTS (SELECT 1 FROM role WHERE name = 'ROLE_USER');

-- Insert ROLE_ADMIN if it doesn't exist
INSERT INTO role (name)
SELECT 'ROLE_ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM role WHERE name = 'ROLE_ADMIN');

-- Insert admin user if it doesn't exist
INSERT INTO users (username, email, password)
SELECT 'admin', 'admin@library.io', 'secret1234'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'admin');

-- Assign ROLE_ADMIN to the admin user
INSERT INTO user_role (user_id, role_id)
SELECT
    (SELECT id FROM users WHERE username = 'admin'),
    (SELECT id FROM role WHERE name = 'ROLE_ADMIN')
WHERE NOT EXISTS (
    SELECT 1
    FROM user_role
    WHERE user_id = (SELECT id FROM users WHERE username = 'admin')
      AND role_id = (SELECT id FROM role WHERE name = 'ROLE_ADMIN')
);
