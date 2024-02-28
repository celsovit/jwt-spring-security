INSERT INTO permission (id, code, description) VALUES
    (1, 'CAN_VIEW_BALANCE', 'Allows viewing account balances'),
    (2, 'CAN_MODIFY_CONFIG', 'Allows changing settings'),
    (3, 'CAN_READ', 'Allows viewing data only');

INSERT INTO users_group (id, code, description) VALUES
    (1, 'ADMIN', 'Administrators group'),
    (2, 'MANAGED', 'Managers group'),
    (3, 'DEVELOPERS', 'Common users group'),
    (4, 'MATHEMATICIAN', 'Mathematicians group'),
    (5, 'PHYSICIST', 'Physicists group');

-- All with password 'admin123'
INSERT INTO users (id, username, password) VALUES
    (1, 'turin', '{pbkdf2}d3e26711d3071178ce9606d92e037fb876ba8188ecf359a70a477b252b10369cfe27c5c803d8c949'),
    (2, 'lovelace', '{pbkdf2}d3e26711d3071178ce9606d92e037fb876ba8188ecf359a70a477b252b10369cfe27c5c803d8c949'),
    (3, 'babbage', '{pbkdf2}d3e26711d3071178ce9606d92e037fb876ba8188ecf359a70a477b252b10369cfe27c5c803d8c949'),
    (4, 'hopper', '{pbkdf2}d3e26711d3071178ce9606d92e037fb876ba8188ecf359a70a477b252b10369cfe27c5c803d8c949'),
    (5, 'knuth', '{pbkdf2}d3e26711d3071178ce9606d92e037fb876ba8188ecf359a70a477b252b10369cfe27c5c803d8c949');

INSERT INTO users_group_permission (users_group_id, permission_id) VALUES
    (1, 1),
    (1, 2),
    (2, 2),
    (2, 3),
    (3, 3),
    (4, 3),
    (5, 1);

INSERT INTO user_users_group (user_id, users_group_id) VALUES
    (1, 1),
    (1, 4),
    (2, 4),
    (3, 3),
    (3, 4),
    (4, 4),
    (4, 5),
    (5, 4),
    (5, 5);

