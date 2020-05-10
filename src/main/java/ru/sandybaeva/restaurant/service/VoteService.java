package ru.sandybaeva.restaurant.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sandybaeva.restaurant.model.Restaurant;
import ru.sandybaeva.restaurant.model.User;
import ru.sandybaeva.restaurant.model.Vote;
import ru.sandybaeva.restaurant.repository.RestaurantRepository;
import ru.sandybaeva.restaurant.repository.UserRepository;
import ru.sandybaeva.restaurant.repository.VoteRepository;
import ru.sandybaeva.restaurant.util.exception.DeadlineVoteException;
import ru.sandybaeva.restaurant.util.exception.DuplicateDataException;
import ru.sandybaeva.restaurant.util.exception.IllegalRequestDataException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.sandybaeva.restaurant.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public VoteService(VoteRepository voteRepository, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Vote create(int restaurantId, int userId) {
        Restaurant restaurant = restaurantRepository.getByIdCurrent(LocalDate.now(), restaurantId);
        checkNotFoundWithId(restaurant, restaurantId, Restaurant.class);
        if (voteRepository.getAllBetweenDateWithUserId(userId, LocalDate.now(), LocalDate.now().plusDays(1)).isEmpty()) {
            Vote vote = new Vote(userId, restaurantId, LocalDate.now());
            return voteRepository.save(vote);
        } else throw new DuplicateDataException("You have been already voted!");
    }

    @Transactional
    public void update(int userId, LocalDate date, int restaurantId) {
        Restaurant restaurant = restaurantRepository.getByIdCurrent(date, restaurantId);
        checkNotFoundWithId(restaurant, restaurantId, Restaurant.class);
        List<Vote> votesToday = getBetweenWithUser(userId, date, date.plusDays(1));
        if(votesToday.isEmpty()) {
            throw new IllegalRequestDataException("You have no votes today");
        }
        if (!LocalTime.now().isBefore(LocalTime.of(11,00))) {
            throw new DeadlineVoteException("It is after 11:00 then it is too late, vote can't be changed");
        }
        Vote vote = votesToday.get(0);
        vote.setRestaurant(restaurantId);
        voteRepository.save(vote);
    }

    public List<Vote> getBetween(LocalDate startDate, LocalDate endDate) {
        startDate = startDate != null ? startDate : LocalDate.of(1, 1, 1);
        endDate = endDate != null ? endDate : LocalDate.of(3000, 1, 1);
        return voteRepository.getAllBetweenDate(startDate, endDate);
    }

    @Transactional
    public List<Vote> getBetweenWithUser(int userId, LocalDate startDate, LocalDate endDate) {
        User user = userRepository.findById(userId);
        checkNotFoundWithId(user, userId, User.class);
        startDate = startDate != null ? startDate : LocalDate.of(1, 1, 1);
        endDate = endDate != null ? endDate : LocalDate.of(3000, 1, 1);
        return voteRepository.getAllBetweenDateWithUserId(userId, startDate, endDate);
    }

    @Transactional
    public List<Vote> getByUser(int userId) {
        User user = userRepository.findById(userId);
        checkNotFoundWithId(user, userId, User.class);
        return voteRepository.findByUserIdOrderByDateDesc(userId);
    }

    @Transactional
    public List<Vote> getByRestaurant(int restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
        checkNotFoundWithId(restaurant, restaurantId, Restaurant.class);
        return voteRepository.getAllByRestaurantId(restaurantId);
    }

}
