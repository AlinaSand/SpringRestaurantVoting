package ru.sandybaeva.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.sandybaeva.restaurant.model.Dish;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface DishRepository extends JpaRepository<Dish, Integer> {

    @Override
    @Transactional
    Dish save(Dish dish);

    @Transactional
    @Modifying
    @Query("DELETE FROM Dish d WHERE d.id=:id AND d.restaurant.id=:restaurantId")
    int delete(@Param("id") int id, @Param("restaurantId") int restaurantId);

    Dish findById(int id);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restaurantId")
    List<Dish> getAll(@Param("restaurantId") int restaurantId);
}
