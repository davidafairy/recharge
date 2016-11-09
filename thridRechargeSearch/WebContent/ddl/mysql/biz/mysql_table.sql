use videomgr;

CREATE TABLE cs_channel(
	 ID             INT(8) PRIMARY KEY AUTO_INCREMENT,
	 channel_name        VARCHAR(100)  UNIQUE,
	 chanel_desc     VARCHAR(225) ,
	 remark        VARCHAR(225) ,
	 CREATE_TIME    DATETIME
	)ENGINE=MyISAM  DEFAULT CHARSET=utf8;  

CREATE TABLE cs_program(
	 ID             INT(8) PRIMARY KEY AUTO_INCREMENT,
         channel_id     int(8),
	 program_name        VARCHAR(100),
	 program_desc     VARCHAR(225) ,
	 remark        VARCHAR(225) ,
	 CREATE_TIME    DATETIME
	)ENGINE=MyISAM  DEFAULT CHARSET=utf8;  

CREATE TABLE cs_video(
	 ID             INT(8) PRIMARY KEY AUTO_INCREMENT,
         program_id       int(8),
	 vido_name        VARCHAR(100),
	 video_url     VARCHAR(225) ,
         video_type    int(2),
         video_author  varchar(20),
         video_desc    text,
         pic_id       int(8),
         video_status int(2),
	 remark        VARCHAR(225) ,
	 CREATE_TIME    DATETIME
	)ENGINE=MyISAM  DEFAULT CHARSET=utf8;

CREATE TABLE cs_pic(
	 ID             INT(8) PRIMARY KEY AUTO_INCREMENT,
	 pic_name       VARCHAR(100),
         pic_addr       varchar(225),
	 pic_desc       VARCHAR(225) ,
	 remark         VARCHAR(225) ,
	 CREATE_TIME    DATETIME
	)ENGINE=MyISAM  DEFAULT CHARSET=utf8;  
