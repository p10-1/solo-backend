CREATE TABLE quizs (
                       quizNo	    int	            NOT NULL    primary Key     auto_Increment,
                       fnName	    varchar(200)	NULL,
                       fnContent    varchar(4000)	NULL
);

-- quiz
insert into quizs(fnName, fnContent)
values
    ('happy', '행복'),
    ('sad', '슬픔');