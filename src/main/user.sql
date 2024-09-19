
CREATE TABLE user (
                      userID	varchar(30)	NOT NULL primary Key,
                      pw		varchar(30)	NULL,
                      name	    varchar(10)	NULL,
                      email	    varchar(30)	NULL,
                      birth	    varchar(30)	NULL,
                      nickName	varchar(30)	NULL,
                      point 	int	        NULL,
                      grade	    varchar(10)	NULL,
                      access	boolean	    NULL
);

CREATE TABLE userData (
                          userNo	    int	                NOT NULL    primary Key    auto_Increment,
                          userID	    varchar(30)	        NOT NULL,
                          salary	    int	                NULL,
                          cash	    int	                NULL,
                          stock	    int	                NULL,
                          property    int	                NULL,
                          deposit	    int	                NULL,
                          consume	    varchar(30)	        NULL,
                          job	        varchar(30)	        NULL,
                          address	    varchar(30)	        NULL,
                          foreign key(userID) references user(userID)
);

-- user
insert into user(userID, pw, name, email, birth, nickName, point, grade, access)
values
    ('user0', '1234','user0','user0@mail', '2000-10-10', 'u', 0,  '초보솔로', 0),
    ('user1', '1234','user1','user1@mail', '2010-10-10', 'u1', 0,  '중급솔로', 0);
