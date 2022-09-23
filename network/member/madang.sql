create table tblMember(
id number primary key,
name varchar2(20) not null,
phone varchar(20) not null,
address varchar(50) not null,
team varchar(20) not null
);

create sequence mem_seq;

create table tblzipcode(
zipcode VARCHAR2(5) not null,
areal varchar2(10),
area2 varchar2(10),
area3 varchar2(50)
)

TBLMEMBER
insert into tblmember
values(mem_seq.nextval, '홍길동', '010-8888-9999', '서울', '산적');

insert into tblmember
values(mem_seq.nextval, '강호동', '010-8888-9999', '서울', 'MC');

select * from tblMember;


낭낭하게 에리어3는 50주자