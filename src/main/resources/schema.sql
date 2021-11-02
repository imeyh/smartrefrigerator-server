DROP TABLE IF EXISTS User_info;
DROP TABLE IF EXISTS Food;
DROP TABLE IF EXISTS Shelf;


CREATE TABLE Shelf
(
    `shelf_id`  VARCHAR(45)    NOT NULL,
    `ble_uuid`  VARCHAR(45)    DEFAULT '',
    `shelf_row`       INT            NULL, 
    `shelf_col`       INT            NULL,
    `ice`		Boolean		DEFAULT	FALSE,
    CONSTRAINT PK_Shelf PRIMARY KEY (shelf_id)
);

CREATE TABLE Food
(
    `food_id`      VARCHAR(45)    NOT NULL, 
    `food_name`    VARCHAR(45)    NULL, 
    `food_weight` Float    NULL, 
    `max_weight` Float DEFAULT 0.0,
    `shelf_id`     VARCHAR(45)    NOT NULL, 
    `food_row`          INT            NULL, 
    `food_col`          INT            NULL,
    `registered_date`		VARCHAR(20)		NOT NULL,
    CONSTRAINT PK_Food PRIMARY KEY (food_id)
);

ALTER TABLE Food
    ADD CONSTRAINT FK_Food_shelf_id_Shelf_shelf_id FOREIGN KEY (shelf_id)
        REFERENCES Shelf (shelf_id) ON DELETE CASCADE ON UPDATE CASCADE;


CREATE TABLE User_info
(
    `user_id`   VARCHAR(45)    NOT NULL, 
    `shelf_id`  VARCHAR(45)    NULL, 
    `name`      VARCHAR(45)    NOT NULL, 
    `password`  VARCHAR(45)    NOT NULL, 
    CONSTRAINT PK_User_info PRIMARY KEY (user_id)
);

ALTER TABLE User_info
    ADD CONSTRAINT FK_UserInfo_shelf_id_Shelf_shelf_id FOREIGN KEY (shelf_id)
        REFERENCES Shelf (shelf_id) ON DELETE SET NULL ON UPDATE CASCADE;