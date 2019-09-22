drop table if exists items;
create table items(
  id   integer primary key autoincrement,
  body text not null,
  done bool default false,
  created_at datetime default (datetime('now', 'localtime')),
  updated_at datetime default (datetime('now', 'localtime'))
);

insert into items(body) values('hello');
