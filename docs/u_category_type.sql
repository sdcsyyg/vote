UPDATE `house` SET `type` = 'XINFANG' WHERE `cid` = 1;
UPDATE `house` SET `type` = 'ERSHOUFANG' WHERE `cid` = 2;
UPDATE `house` SET `type` = 'ZUFANG' WHERE `cid` = 3;
UPDATE `house` SET `type` = 'QITAFANG' WHERE `cid` = 4;
UPDATE `house` SET `type` = 'SHOULOUBU' WHERE `cid` = 5;

# cid=6的需要单独分开处理
UPDATE `company` SET `type` = 'JJJZ' WHERE `cid` = 6;
UPDATE `company` SET `type` = 'JZFW' WHERE `cid` = 6;
UPDATE `company` SET `type` = 'YXQY' WHERE `cid` = 7;
UPDATE `company` SET `type` = 'SLB' WHERE `cid` = 8;