CREATE DATABASE Todo_list;

use Todo_list;

CREATE TABLE todo (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
title VARCHAR(250) NOT NULL,
finish BOOLEAN NOT NULL
);