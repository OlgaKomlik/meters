create function find_bigest_square(rooms_criteria integer, object_type bigint) returns integer
    language plpgsql
as
$$
declare
itemSquare integer;
begin
select max(square)
into itemSquare
from real_estates
where object_type_id = object_type and rooms = rooms_criteria;
return itemSquare;
end
$$;

alter function find_bigest_square(integer, bigint) owner to postgres;