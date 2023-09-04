drop table if exists TBL_ROVERS;

create table if not exists TBL_ROVERS (
    rover_id uuid default random_uuid(),
    plateau varchar(10) not null,
    position varchar(256) not null,
    constraint rovers_pk primary key (rover_id)
);
