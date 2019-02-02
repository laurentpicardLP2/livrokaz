CREATE PROCEDURE `ps_modifAuthorithies` ()
BEGIN
ALTER TABLE `db_livrokaz`.`authorities` 
CHANGE COLUMN `authority` `authority` VARCHAR(30) NOT NULL ,
CHANGE COLUMN `username` `username` VARCHAR(30) NOT NULL ,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`authority`, `username`);

END
