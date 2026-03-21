-- -----------------------------------------------------
-- Schema full-stack-ecommerce
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `full-stack-ecommerce`;

CREATE SCHEMA `full-stack-ecommerce`;
USE `full-stack-ecommerce` ;

-- -----------------------------------------------------
-- Table `full-stack-ecommerce`.`product_category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `full-stack-ecommerce`.`product_category` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `category_name` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE=InnoDB
AUTO_INCREMENT = 1;

-- -----------------------------------------------------
-- Table `full-stack-ecommerce`.`product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `full-stack-ecommerce`.`product` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `sku` VARCHAR(255) DEFAULT NULL,
  `name` VARCHAR(255) DEFAULT NULL,
  `description` VARCHAR(255) DEFAULT NULL,
  `unit_price` DECIMAL(13,2) DEFAULT NULL,
  `image_url` VARCHAR(255) DEFAULT NULL,
  `active` BIT DEFAULT 1,
  `units_in_stock` INT(11) DEFAULT NULL,
  `date_created` DATETIME(6) DEFAULT NULL,
  `last_updated` DATETIME(6) DEFAULT NULL,
  `category_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_category` (`category_id`),
  CONSTRAINT `fk_category` FOREIGN KEY (`category_id`) REFERENCES `product_category` (`id`)
) 
ENGINE=InnoDB
AUTO_INCREMENT = 1;

-- -----------------------------------------------------
-- Categories
-- -----------------------------------------------------
INSERT INTO product_category(category_name) VALUES ('Mens');
INSERT INTO product_category(category_name) VALUES ('Womens');
INSERT INTO product_category(category_name) VALUES ('Headwears');
INSERT INTO product_category(category_name) VALUES ('Accessories');

-- -----------------------------------------------------
-- Mens
-- -----------------------------------------------------
INSERT INTO product (sku, name, description, image_url, active, units_in_stock, unit_price, category_id,date_created) VALUES ('MENS-1000', 'Daopao (道袍) - Large Sleeve', 'Product and photo from 淮边筱竹.', 'assets/images/products/mens/p1000.png', 1, 100, 109.99, 1, NOW());
INSERT INTO product (sku, name, description, image_url, active, units_in_stock, unit_price, category_id,date_created) VALUES ('MENS-1001', 'Daopao (道袍) - Small Sleeve', 'Product and photo from 淮边筱竹.', 'assets/images/products/mens/p1001.png', 1, 100, 109.99, 1, NOW());
INSERT INTO product (sku, name, description, image_url, active, units_in_stock, unit_price, category_id,date_created) VALUES ('MENS-1002', 'Zhiduo (直裰)', 'Product and photo from 淮边筱竹.', 'assets/images/products/mens/p1002.png', 1, 100, 36.99, 1, NOW());
INSERT INTO product (sku, name, description, image_url, active, units_in_stock, unit_price, category_id,date_created) VALUES ('MENS-1003', 'Zhiduo (直裰) - Pleated', 'Product and photo from 惜物国风馆.', 'assets/images/products/mens/p1003.png', 1, 100, 41.99, 1, NOW());
INSERT INTO product (sku, name, description, image_url, active, units_in_stock, unit_price, category_id,date_created) VALUES ('MENS-1004', 'Tieli (貼裡)', 'Product and photo from 淮边筱竹.', 'assets/images/products/mens/p1004.png', 1, 100, 119.99, 1, NOW());
INSERT INTO product (sku, name, description, image_url, active, units_in_stock, unit_price, category_id,date_created) VALUES ('MENS-1005', 'Daofu (道服)', 'Product and photo from 淮边筱竹.', 'assets/images/products/mens/p1005.png', 1, 100, 119.99, 1, NOW());
INSERT INTO product (sku, name, description, image_url, active, units_in_stock, unit_price, category_id,date_created) VALUES ('MENS-1006', 'Changyi (氅衣)', 'Product and photo from 淮边筱竹.', 'assets/images/products/mens/p1006.png', 1, 100, 67.99, 1, NOW());
INSERT INTO product (sku, name, description, image_url, active, units_in_stock, unit_price, category_id,date_created) VALUES ('MENS-1007', 'Pifeng (披風)', 'Product and photo from 淮边筱竹.', 'assets/images/products/mens/p1007.png', 1, 100, 67.99, 1, NOW());
INSERT INTO product (sku, name, description, image_url, active, units_in_stock, unit_price, category_id,date_created) VALUES ('MENS-1008', 'Zhishen (直身)', 'Product and photo from 天汉骄阳.', 'assets/images/products/mens/p1008.png', 1, 100, 119.99, 1, NOW());
INSERT INTO product (sku, name, description, image_url, active, units_in_stock, unit_price, category_id,date_created) VALUES ('MENS-1009', 'Yuanlingpao (圓領袍)', 'Product and photo from 乘鲤汉服.', 'assets/images/products/mens/p1009.png', 1, 100, 139.99, 1, NOW());
INSERT INTO product (sku, name, description, image_url, active, units_in_stock, unit_price, category_id,date_created) VALUES ('MENS-1011', 'Duanshan (短衫)', 'Product and photo from 淮边筱竹.', 'assets/images/products/mens/p1011.png', 1, 100, 28.99, 1, NOW());
INSERT INTO product (sku, name, description, image_url, active, units_in_stock, unit_price, category_id,date_created) VALUES ('MENS-1012', 'Duanshan (短衫) - Half Sleeve', 'Product and photo from 淮边筱竹.', 'assets/images/products/mens/p1012.png', 1, 100, 22.99, 1, NOW());
INSERT INTO product (sku, name, description, image_url, active, units_in_stock, unit_price, category_id,date_created) VALUES ('MENS-1014', 'Xuanzi (旋子)', 'Product and photo from 淮边筱竹.', 'assets/images/products/mens/p1014.png', 1, 100, 22.99, 1, NOW());
INSERT INTO product (sku, name, description, image_url, active, units_in_stock, unit_price, category_id,date_created) VALUES ('MENS-1015', 'Ku (褲)', 'Product and photo from 淮边筱竹.', 'assets/images/products/mens/p1015.png', 1, 100, 19.99, 1, NOW());


-- -----------------------------------------------------
-- Womens
-- -----------------------------------------------------
INSERT INTO product (sku, name, description, image_url, active, units_in_stock, unit_price, category_id,date_created) VALUES ('WOMENS-2000', 'Changshan (長衫) - Cross Collar & Large Sleeve', 'Product and photo from 令熹堂.', 'assets/images/products/womens/p2000.png', 1, 100, 89.99, 2, NOW());
INSERT INTO product (sku, name, description, image_url, active, units_in_stock, unit_price, category_id,date_created) VALUES ('WOMENS-2001', 'Changshan (長衫) - Stand-up Collar & Large Sleeve', 'Product and photo from 尘余馆.', 'assets/images/products/womens/p2001.png', 1, 100, 89.99, 2, NOW());
INSERT INTO product (sku, name, description, image_url, active, units_in_stock, unit_price, category_id,date_created) VALUES ('WOMENS-2002', 'Changshan (長衫) - Stand-up Collar & Small Sleeve', 'Product and photo from 凤翥斋.', 'assets/images/products/womens/p2002.png', 1, 100, 69.99, 2, NOW());
INSERT INTO product (sku, name, description, image_url, active, units_in_stock, unit_price, category_id,date_created) VALUES ('WOMENS-2003', 'Ao (襖) - Cross Collar', 'Product and photo from ', 'assets/images/products/womens/p2003.png', 1, 100, 44.99, 2, NOW());
INSERT INTO product (sku, name, description, image_url, active, units_in_stock, unit_price, category_id,date_created) VALUES ('WOMENS-2004', 'Ao (襖) - Stand-up Collar', 'Product and photo from ', 'assets/images/products/womens/p2004.png', 1, 100, 44.99, 2, NOW());
INSERT INTO product (sku, name, description, image_url, active, units_in_stock, unit_price, category_id,date_created) VALUES ('WOMENS-2005', 'Pifeng (披風)', 'Product and photo from ', 'assets/images/products/womens/p2005.png', 1, 100, 67.99, 2, NOW());
INSERT INTO product (sku, name, description, image_url, active, units_in_stock, unit_price, category_id,date_created) VALUES ('WOMENS-2006', 'Pifeng (披風) - Stand-up Collar', 'Product and photo from ', 'assets/images/products/womens/p2006.png', 1, 100, 76.99, 2, NOW());
INSERT INTO product (sku, name, description, image_url, active, units_in_stock, unit_price, category_id,date_created) VALUES ('WOMENS-2007', 'Bijia (比甲)', 'Product and photo from ', 'assets/images/products/womens/p2007.png', 1, 100, 24.99, 2, NOW());
INSERT INTO product (sku, name, description, image_url, active, units_in_stock, unit_price, category_id,date_created) VALUES ('WOMENS-2008', 'Bijia (比甲) - Short & Round Collar', 'Product and photo from 惜物国风馆.', 'assets/images/products/womens/p2008.png', 1, 100, 24.99, 2, NOW());
INSERT INTO product (sku, name, description, image_url, active, units_in_stock, unit_price, category_id,date_created) VALUES ('WOMENS-2009', 'Changshan (長衫) - Stand-up Collar & Brocaded', 'Product and photo from 控弦司.', 'assets/images/products/womens/p2009.png', 1, 100, 998.99, 2, NOW());
INSERT INTO product (sku, name, description, image_url, active, units_in_stock, unit_price, category_id,date_created) VALUES ('WOMENS-2010', 'Yuanlingpao (圓領袍) - Brocaded', 'Product and photo from 控弦司.', 'assets/images/products/womens/p2010.png', 1, 100, 1199.99, 2, NOW());
INSERT INTO product (sku, name, description, image_url, active, units_in_stock, unit_price, category_id,date_created) VALUES ('WOMENS-2011', 'Mamianqun (馬面裙) - Gold Brocaded', 'Product and photo from 褍正记', 'assets/images/products/womens/p2011.png', 1, 100, 78.99, 2, NOW());
INSERT INTO product (sku, name, description, image_url, active, units_in_stock, unit_price, category_id,date_created) VALUES ('WOMENS-2012', 'Mamianqun (馬面裙) - Plain', 'Product and photo from ', 'assets/images/products/womens/p2012.png', 1, 100, 26.99, 2, NOW());
INSERT INTO product (sku, name, description, image_url, active, units_in_stock, unit_price, category_id,date_created) VALUES ('WOMENS-2013', 'Mamianqun (馬面裙) - Embroidered', 'Product and photo from ', 'assets/images/products/womens/p2013.png', 1, 100, 38.99, 2, NOW());
INSERT INTO product (sku, name, description, image_url, active, units_in_stock, unit_price, category_id,date_created) VALUES ('WOMENS-2014', 'Mamianqun (馬面裙)- One Brocade', 'Product and photo from .', 'assets/images/products/womens/p2014.png', 1, 100, 32.99, 2, NOW());
INSERT INTO product (sku, name, description, image_url, active, units_in_stock, unit_price, category_id,date_created) VALUES ('WOMENS-2015', 'Mamianqun (馬面裙) - Two Brocades', 'Product and photo from 吉元裳织.', 'assets/images/products/womens/p2015.png', 1, 100, 38.99, 2, NOW());


-- -----------------------------------------------------
-- Headwears
-- -----------------------------------------------------
INSERT INTO product (sku, name, description, image_url, active, units_in_stock, unit_price, category_id,date_created) VALUES ('HEADWEARS-3000', 'Wangjin (網巾) - Regular', 'Product and photo from 淮边筱竹.', 'assets/images/products/headwears/p3000.png', 1, 100, 12.99, 3, NOW());
INSERT INTO product (sku, name, description, image_url, active, units_in_stock, unit_price, category_id,date_created) VALUES ('HEADWEARS-3001', 'Wangjin (網巾) - Simplified', 'Product and photo from 淮边筱竹.', 'assets/images/products/headwears/p3001.png', 1, 100, 10.99, 3, NOW());
INSERT INTO product (sku, name, description, image_url, active, units_in_stock, unit_price, category_id,date_created) VALUES ('HEADWEARS-3002', 'Fangjin (方巾)', 'Product and photo from ', 'assets/images/products/headwears/p3002.png', 1, 100, 27.99, 3, NOW());
INSERT INTO product (sku, name, description, image_url, active, units_in_stock, unit_price, category_id,date_created) VALUES ('HEADWEARS-3003', 'Piaopiaojin (飄飄巾)', 'Product and photo from 鱼汤传统服饰.', 'assets/images/products/headwears/p3003.png', 1, 100, 27.99, 3, NOW());
INSERT INTO product (sku, name, description, image_url, active, units_in_stock, unit_price, category_id,date_created) VALUES ('HEADWEARS-3004', 'Fujin (幅巾)', 'Product and photo from 执月传统服饰.', 'assets/images/products/headwears/p3004.png', 1, 100, 11.99, 3, NOW());
INSERT INTO product (sku, name, description, image_url, active, units_in_stock, unit_price, category_id,date_created) VALUES ('HEADWEARS-3005', 'Wushamao (烏紗帽)', 'Product and photo from 空青汉服.', 'assets/images/products/headwears/p3005.png', 1, 100, 37.99, 3, NOW());
INSERT INTO product (sku, name, description, image_url, active, units_in_stock, unit_price, category_id,date_created) VALUES ('HEADWEARS-3006', 'Futou (幞頭)', 'Product and photo from 彰汉堂.', 'assets/images/products/headwears/p3006.png', 1, 100, 12.99, 3, NOW());
INSERT INTO product (sku, name, description, image_url, active, units_in_stock, unit_price, category_id,date_created) VALUES ('HEADWEARS-3007', 'Xiaomao (小帽)', 'Product and photo from ', 'assets/images/products/headwears/p3007.png', 1, 100, 17.99, 3, NOW());


-- -----------------------------------------------------
-- Accessories
-- -----------------------------------------------------
INSERT INTO product (sku, name, description, image_url, active, units_in_stock, unit_price, category_id,date_created) VALUES ('ACCESSORIES-4000', 'Boots', 'Product and photo from 乌衣巷.', 'assets/images/products/accessories/p4000.png', 1, 100, 36.99, 4, NOW());
INSERT INTO product (sku, name, description, image_url, active, units_in_stock, unit_price, category_id,date_created) VALUES ('ACCESSORIES-4001', 'Belt', 'Product and photo from 吉元裳织.', 'assets/images/products/accessories/p4001.png', 1, 100, 36.99, 4, NOW());


