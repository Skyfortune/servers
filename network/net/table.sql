create table tblRegister(
id varchar2(20) primary key,
pwd char(4) not null,
name char(20) not null,
regdate date default sysdate
)

insert into tblregister
VALUES('aaa', '1234', '홍길동', sysdate); 

insert into tblregister
VALUES('bbb', '1234', '강도창', sysdate); 

insert into tblregister
VALUES('ccc', '1234', '오지혁', sysdate); 

select * FROM tblregister

create table tblMessage(
no number PRIMARY key
fid varchar2(20) not null,
tid varchar2(20) not null,
msg varchar2(50) not null,
mdata timestamp default sysdate 
)

create sequence msg_seq;

