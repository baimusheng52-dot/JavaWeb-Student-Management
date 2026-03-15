select * from students where age in (18, 19);

select *
from students
where
    gender = '男'
    and age between 17 and 20
    and name like '___';

update students set age = floor(16 + rand() * 4);

select gender, count(*) from students group by gender;

select name as '姓名', age as '年龄', score as '成绩'
from students
where
    age <= 35
order by score desc, age asc;

select *
from students
where
    gender = '男'
    and age between 18 and 20
limit 0, 5;

select gender, avg(score) from students group by gender;

select gender, avg(score)
from students
group by
    gender
having
    avg(score) > 80;

alter table students add column class_id tinyint;

update students set class_id = floor(1 + rand() * 3);

truncate table students;

CREATE TABLE classes (
    class_id INT PRIMARY KEY AUTO_INCREMENT, -- 对应学生表的 class_id
    class_name VARCHAR(50) NOT NULL, -- 班级名称（如：一班、青龙班）
    teacher VARCHAR(20), -- 班主任名字
    room_number VARCHAR(10) -- 教室编号（如：A302）
);

insert into
    classes (
        class_id,
        class_name,
        teacher,
        room_number
    )
values (1, '计科一班', '张大胡子', '教A-201'),
    (2, '软工二班', '李美眉', '教B-305'),
    (3, '大数据三班', '王老五', '教A-102');

SELECT s.name AS '学生姓名', c.class_name AS '班级名称', c.teacher AS '班主任'
FROM students s
    INNER JOIN classes c ON s.class_id = c.class_id;

select c.class_name '班级名称', c.teacher '班主任', c.room_number '教室编号', s.name '学生姓名'
from classes c
    inner join students s on c.class_id = s.class_id;

SELECT s.name, s.score, c.class_name
FROM students s
    INNER JOIN classes c ON s.class_id = c.class_id
WHERE
    c.class_name = '计科一班'
    AND s.gender = '女'
    AND s.score > 80;

select s.name, s.score, c.class_name
from students s
    inner join classes c on s.class_id = c.class_id
where
    s.score > 60
order by s.class_id asc, s.score desc;