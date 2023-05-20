package com.meters.repository;

import com.meters.entities.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



import java.util.List;
import java.util.Map;

public interface ManagerRepository extends JpaRepository<Manager, Long> {


    List<Manager> findByFullNameContainingIgnoreCase(String query);

    @Query("select m from Manager m where EXTRACT(day from m.birthDate) = :dayToday AND EXTRACT(month from m.birthDate) = :monthToday")
    List<Manager> findBirthDayManagers(Integer dayToday, Integer monthToday);

    @Query("select m.id, AVG(d.fee) as averageFee from Deal d  JOIN d.manager m group by m.id ORDER BY averageFee desc")
    List<Object []> getAverageFeeOfManagers();

    @Query("select m from Manager m where m.id = best_seller_of_the_month( :month, :year)")
    Manager getBestSellerOfTheMonth(int month, int year);

/*   @Query("select m, sum(d.fee) as sumFee from Deal d JOIN d.manager m group by m.id having EXTRACT(month from d.dealDate) = :month AND EXTRACT(year from d.dealDate) = :year  And sumFee =bigest_fee_of_the_month(:month, :year)")
   List<Object []> getBestSellersOfTheMonth(int month, int year);*/
}
