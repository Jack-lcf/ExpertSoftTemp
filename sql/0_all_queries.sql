CREATE DATABASE `expert` DEFAULT CHARACTER SET utf8;

USE `expert`;

CREATE TABLE `contacts` (
	`id` INTEGER NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(20),
	`surname` VARCHAR(20),
	`login` VARCHAR(255) NOT NULL UNIQUE,
	`email` VARCHAR(255) NOT NULL,
	`phone` VARCHAR(255),	
	PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARACTER SET `utf8`;

USE `expert`;
SET NAMES `utf8` COLLATE `utf8_unicode_ci`;
INSERT INTO `contacts` (`name`,`surname`,`login`,`email`,`phone`)
				VALUES ('John','Black','johnblack','johnblack@tut.by','+375796542132');
INSERT INTO `contacts` (`name`,`surname`,`login`,`email`,`phone`)
				VALUES ('Alex','Harding','alex515','alex515@gmail.com','+375321789654');
INSERT INTO `contacts` (`name`,`surname`,`login`,`email`,`phone`)
				VALUES ('Bill','Collins','billcol','bill17@yandex.ru','+749456789123');
INSERT INTO `contacts` (`name`,`surname`,`login`,`email`,`phone`)
				VALUES ('Frank','Logan','frlog','frlog@gmail.com','+375555111222');
INSERT INTO `contacts` (`name`,`surname`,`login`,`email`,`phone`)
				VALUES ('Петр','Власлов','petrvlas','petr1985@mail.ru','+375777888999');
INSERT INTO `contacts` (`name`,`surname`,`login`,`email`,`phone`)
				VALUES ('Евгений','Смольский','eugen','gensmal@ya.com','+375331258798');
INSERT INTO `contacts` (`name`,`surname`,`login`,`email`,`phone`)
				VALUES ('Maureen','Wills','maur','maurwills21@gmail.com','+759846351426');
INSERT INTO `contacts` (`name`,`surname`,`login`,`email`,`phone`)
				VALUES ('Leila','Mains','Leila','mains123@gmail.com','+759333222666');
INSERT INTO `contacts` (`name`,`surname`,`login`,`email`,`phone`)
				VALUES ('Miguel','Escobar','escobar','escobar@mail.ru','+798444666555');
INSERT INTO `contacts` (`name`,`surname`,`login`,`email`,`phone`)
				VALUES ('Gloria','Light','glor987','glor987@mail.ru','+375336459977');			
