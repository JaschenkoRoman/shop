CREATE TABLE IF NOT EXISTS user
(
  id       INTEGER               NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name     VARCHAR(100)          NOT NULL,
  email    VARCHAR(100)          NOT NULL UNIQUE,
  password CHAR(100)             NOT NULL,
  role     ENUM('USER', 'ADMIN') NOT NULL
);

CREATE TABLE IF NOT EXISTS item
(
  id          INTEGER      NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name        VARCHAR(100) NOT NULL,
  description VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS tag
(
  id       INTEGER     NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name    VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS basket
(
  id       INTEGER     NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_id  INTEGER     NOT NULL UNIQUE,
  FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS basket_item
(
  id       INTEGER     NOT NULL AUTO_INCREMENT PRIMARY KEY,
  basket_id  INTEGER     NOT NULL,
  item_id  INTEGER     NOT NULL,
  FOREIGN KEY (basket_id) REFERENCES basket(id) ON DELETE CASCADE,
  FOREIGN KEY (item_id) REFERENCES item(id) ON DELETE CASCADE,
  UNIQUE(basket_id, item_id)
);

CREATE TABLE IF NOT EXISTS item_tag
(
  id       INTEGER     NOT NULL AUTO_INCREMENT PRIMARY KEY,
  item_id  INTEGER     NOT NULL,
  tag_id   INTEGER     NOT NULL,
  FOREIGN KEY (tag_id) REFERENCES tag(id),
  FOREIGN KEY (item_id) REFERENCES item(id) ON DELETE CASCADE
);

INSERT INTO user (name, email, password, role)
VALUES
('user', 'supershopuser@yopmail.com', '$2y$11$9I3ai.JwB4lfLcvIV7k.I.MdwxeLR6uXfT7k3cnmaQWPfxQBHJG96', 'USER'),
('admin', 'admin@mail.ru', '$2y$11$9I3ai.JwB4lfLcvIV7k.I.MdwxeLR6uXfT7k3cnmaQWPfxQBHJG96', 'ADMIN');

INSERT INTO basket (user_id)
VALUES
(1);


INSERT INTO tag (name)
VALUES
('computer'),
('notebook'),
('phone'),
('ssd'),
('usb'),
('mouse'),
('display'),
('power');

INSERT INTO item (name, description)
VALUES
('notebook charge','quick charge for notebook'),
('usb mouse', 'portable mouse');

INSERT INTO item_tag (item_id, tag_id)
VALUES
(1,2),
(1,8),
(2,1),
(2,2),
(2,5),
(2,6);