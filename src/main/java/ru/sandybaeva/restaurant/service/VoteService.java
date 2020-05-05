package ru.sandybaeva.restaurant.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.sandybaeva.restaurant.model.Vote;
import ru.sandybaeva.restaurant.repository.VoteRepository;
import ru.sandybaeva.restaurant.util.exception.DeadlineVoteException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.sandybaeva.restaurant.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteService {

    private final VoteRepository voteRepository;

    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @Transactional
    public void create(Vote vote) {
        Assert.notNull(vote, "vote must not be null");
        voteRepository.save(vote);
    }

    @Transactional
    public void update(int userId, LocalDate date, int restaurantId) {
        if (!LocalTime.now().isBefore(LocalTime.of(11,00))) {
            throw new DeadlineVoteException("It is after 11:00 then it is too late, vote can't be changed");
        }
        Vote vote = voteRepository.get(date, userId);
        vote.setDate(date);
        vote.setRestaurant(restaurantId);
        voteRepository.save(vote);
    }

    public List<Vote> getBetween(LocalDate startDate, LocalDate endDate) {
        startDate = startDate != null ? startDate : LocalDate.of(1, 1, 1);
        endDate = endDate != null ? endDate : LocalDate.of(3000, 1, 1);
        return voteRepository.getAllBetweenDate(startDate, endDate);
    }

    public List<Vote> getBetweenWithUser(int userId, LocalDate startDate, LocalDate endDate) {
        startDate = startDate != null ? startDate : LocalDate.of(1, 1, 1);
        endDate = endDate != null ? endDate : LocalDate.of(3000, 1, 1);
        return voteRepository.getAllBetweenDateWithUserId(userId, startDate, endDate);
    }

    public List<Vote> getByUser(int userId) {
        return voteRepository.findByUserIdOrderByDateDesc(userId);
    }

    public List<Vote> getByRestaurant(int restaurantId) {
        return voteRepository.getAllByRestaurantId(restaurantId);
    }

}
