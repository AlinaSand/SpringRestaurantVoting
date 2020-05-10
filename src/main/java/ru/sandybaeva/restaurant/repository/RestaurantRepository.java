package ru.sandybaeva.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sandybaeva.restaurant.model.Restaurant;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Query("SELECT DISTINCT r FROM Restaurant r JOIN FETCH r.dishes d WHERE d.date =:date ORDER BY r.name ASC")
    List<Restaurant> getAllCurrent(@Param("date") LocalDate date);

    @Query("SELECT DISTINCT r FROM Restaurant r JOIN FETCH r.dishes d WHERE d.date =:date AND r.id =:id")
    Restaurant getByIdCurrent(@Param("date") LocalDate date, @Param("id") int id);

    @Override
    Optional<Restaurant> findById(Integer id);

    Optional<Restaurant> findByName(String name);

    @Override
    @Transactional
    void deleteById(Integer id);
}
