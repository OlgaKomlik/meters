create procedure change_owner_person(id_object bigint, new_owner_persons_id bigint,
                                     time_now timestamp without time zone)
    language plpgsql
as
$$
begin
update real_estates
set owner_person_id = new_owner_persons_id,
    changed         = time_now
where real_estate_id = id_object;

commit;
end;
$$;

alter procedure change_owner_person(bigint, bigint, timestamp) owner to postgres;