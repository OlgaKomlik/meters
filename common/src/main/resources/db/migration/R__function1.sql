create function bigest_fee_of_the_month(month integer, year integer) returns bigint
    language plpgsql
as
$$
begin
select max(fee)
from deals
group by manager_id
having EXTRACT(month from deal_date) = month
   AND EXTRACT(year from deal_date) = year;
end
$$;

alter function bigest_fee_of_the_month(integer, integer) owner to postgres;