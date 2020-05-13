package ru.sandybaeva.restaurant.web.vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.sandybaeva.restaurant.model.Vote;
import ru.sandybaeva.restaurant.service.VoteService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = AdminVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminVoteController {

    static final String REST_URL = "/rest/admin";
    private static final Logger log = LoggerFactory.getLogger(AdminVoteController.class);

    private final VoteService voteService;

    private AdminVoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @GetMapping(value = "/restaurant/{id}/votes")
    public List<Vote> getByRestaurant(@PathVariable("id") int id) {
        log.info("get votes by restaurant with id={}", id);
        return voteService.getByRestaurant(id);
    }

    @GetMapping(value = "/votes/history")
    public List<Vote> getBetween(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        log.info("get votes between dates {}-{}", startDate, endDate);
        return voteService.getBetween(startDate, endDate);
    }
}
