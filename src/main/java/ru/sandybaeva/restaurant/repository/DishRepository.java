package ru.sandybaeva.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sandybaeva.restaurant.model.Dish;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface DishRepository extends JpaRepository<Dish, Integer> {

    @Override
    @Transactional
    Dish save(Dish dish);

    @Override
    @Transactional
    void deleteById(Integer id);

    Optional<Dish> findById(int id);

    @Query("SELECT d FROM Dish d WHERE d.name=:name AND d.restaurant.id=:restaurantId AND d.date=:date")
    Optional<Dish> getByNameAndRestaurantIdAndDate(@Param("name") String name, @Param("restaurantId") int restaurantId,
                                                   @Param("date") LocalDate date);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restaurantId")
    List<Dish> getAll(@Param("restaurantId") int restaurantId);
}
