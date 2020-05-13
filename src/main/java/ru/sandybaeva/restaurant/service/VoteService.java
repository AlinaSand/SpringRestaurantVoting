package ru.sandybaeva.restaurant.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sandybaeva.restaurant.model.Vote;
import ru.sandybaeva.restaurant.repository.VoteRepository;
import ru.sandybaeva.restaurant.util.exception.DeadlineVoteException;
import ru.sandybaeva.restaurant.util.exception.DuplicateDataException;
import ru.sandybaeva.restaurant.util.exception.IllegalRequestDataException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class VoteService {

    private final static LocalTime DEADLINE_TIME = LocalTime.of(11,00);

    private final VoteRepository voteRepository;
    private final RestaurantService restaurantService;

    public VoteService(VoteRepository voteRepository, RestaurantService restaurantService) {
        this.voteRepository = voteRepository;
        this.restaurantService = restaurantService;
    }

    @Transactional
    public Vote create(int restaurantId, int userId) {
        LocalDate today = LocalDate.now();
        restaurantService.getByIdCurrent(today, restaurantId);
        if (voteRepository.getAllBetweenDateWithUserId(userId, today, today.plusDays(1)).isEmpty()) {
            Vote vote = new Vote(userId, restaurantId, today);
            return voteRepository.save(vote);
        } else throw new DuplicateDataException("You have been already voted!");
    }

    @Transactional
    public void update(int userId, LocalDate date, int restaurantId) {
        restaurantService.getByIdCurrent(date, restaurantId);
        List<Vote> votesToday = getBetweenWithUser(userId, date, date.plusDays(1));
        if(votesToday.isEmpty()) {
            throw new IllegalRequestDataException("You have no votes today");
        }
        if (votesToday.size() != 1) {
            throw new IllegalRequestDataException("You have more than one vote today");
        }
        if (!LocalTime.now().isBefore(DEADLINE_TIME)) {
            throw new DeadlineVoteException("It is after 11:00 then it is too late, vote can't be changed");
        }
        Vote vote = votesToday.get(0);
        vote.setRestaurant(restaurantId);
        voteRepository.save(vote);
    }

    public List<Vote> getBetween(LocalDate startDate, LocalDate endDate) {
        startDate = startDate != null ? startDate : endDate != null ? LocalDate.of(1, 1, 1) : LocalDate.now();
        endDate = endDate != null ? endDate : LocalDate.now();
        return voteRepository.getAllBetweenDate(startDate, endDate.plusDays(1));
    }

    public List<Vote> getBetweenWithUser(int userId, LocalDate startDate, LocalDate endDate) {
        startDate = startDate != null ? startDate : LocalDate.of(1, 1, 1);
        endDate = endDate != null ? endDate : LocalDate.now();
        return voteRepository.getAllBetweenDateWithUserId(userId, startDate, endDate.plusDays(1));
    }

    public List<Vote> getByUser(int userId) {
        return voteRepository.findByUserIdOrderByDateDesc(userId);
    }

    @Transactional
    public List<Vote> getByRestaurant(int restaurantId) {
        restaurantService.getById(restaurantId);
        return voteRepository.getAllByRestaurantId(restaurantId);
    }

}
