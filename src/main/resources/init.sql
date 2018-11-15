-- 权限表 --
CREATE TABLE permission(
  id INT(11) AUTO_INCREMENT,
  pname VARCHAR(20) NOT NULL,
  url VARCHAR(255) NOT NULL,
  PRIMARY KEY(id)
)ENGINE = InnoDB DEFAULT CHARSET = utf8;

INSERT INTO permission VALUES
(1,"add",""),
(2,"delete",""),
(3,"update",""),
(4,"select","");

-- 用户表 --
CREATE TABLE user(
  id INT(11) AUTO_INCREMENT,
  username VARCHAR(10) NOT NULL,
  password VARCHAR(50) NOT NULL ,
  PRIMARY KEY(id)
)ENGINE = InnoDB DEFAULT CHARSET = utf8;

INSERT INTO user VALUES
(1,"admin","admin"),
(2,"test","test");

-- 角色表 --
CREATE TABLE role(
  id INT(11) AUTO_INCREMENT,
  rname VARCHAR(20) NOT NULL,
  PRIMARY KEY(id)
)ENGINE=InnoDB DEFAULT CHARSET = utf8;

INSERT INTO role VALUES
(1,'ADMIN'),
(2,'NORMAL');

-- 用户角色关系表 --
CREATE TABLE user_role(
  uid INT(11) NOT NULL,
  rid INT(11) NOT NULL
)ENGINE = InnoDB DEFAULT CHARSET = utf8;

INSERT INTO user_role VALUES
(1,1),(1,2),(2,2);

-- 角色权限关系表 --
CREATE TABLE role_permission(
  rid INT(11) NOT NULL,
  pid INT(11) NOT NULL
)ENGINE = InnoDB DEFAULT CHARSET = utf8;

INSERT INTO role_permission VALUES
(1,1),
(1,2),
(1,3),
(1,4),
(2,4);