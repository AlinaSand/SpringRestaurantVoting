package ru.sandybaeva.restaurant.web.vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.sandybaeva.restaurant.model.Vote;
import ru.sandybaeva.restaurant.service.VoteService;
import ru.sandybaeva.restaurant.web.SecurityUtil;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = UserVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserVoteController {

    static final String REST_URL = "/rest/user";
    private static final Logger log = LoggerFactory.getLogger(UserVoteController.class);

    private final VoteService voteService;

    public UserVoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping(value = "/restaurants/{restaurantId}/votes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> create(@PathVariable("restaurantId") int restaurantId) {
        int userId = SecurityUtil.authUserId();
        log.info("create vote for user with id={}", userId);
        Vote created = voteService.create(restaurantId, userId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/restaurants/{restaurantId}/votes", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@PathVariable("restaurantId") int restaurantId) {
        int userId = SecurityUtil.authUserId();
        log.info("update vote for restaurant with id={} for user with id={}", restaurantId, userId);
        voteService.update(userId, LocalDate.now(), restaurantId);
    }

    @GetMapping(value = "/votes/history")
    public List<Vote> getBetween(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        int userId = SecurityUtil.authUserId();
        log.info("get history votes between {}-{}", startDate, endDate);
        return voteService.getBetweenWithUser(userId, startDate, endDate);
    }

}
