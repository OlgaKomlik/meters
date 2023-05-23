create function find_smallest_square(rooms_criteria integer, object_type bigint) returns integer
    language plpgsql
as
$$
declare
itemSquare integer;
begin
select min(square)
into itemSquare
from real_estates
where object_type_id = object_type and rooms = rooms_criteria;
return itemSquare;
end
$$;

alter function find_smallest_square(integer, bigint) owner to postgres;