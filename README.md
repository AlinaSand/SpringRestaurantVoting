# Restaurant voting

Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) without frontend.

The task is:

Build a voting system for deciding where to have lunch.

2 types of users: admin and regular users Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price) Menu changes each day (admins do the updates) Users can vote on which restaurant they want to have lunch at Only one vote counted per user If user votes again the same day: If it is before 11:00 we asume that he changed his mind. If it is after 11:00 then it is too late, vote can't be changed Each restaurant provides new menu each day.

As a result, provide a link to github repository.

It should contain the code and README.md with API documentation and curl commands to get data for voting and vote.

---

## Rest API
### User
#### get all restaurants with menu today
`curl -s http://localhost:8080/rest/user/restaurants --user user1@mail.com:password1`
#### get restaurant id=100003 with menu today
`curl -s http://localhost:8080/rest/user/restaurants/100003 --user user1@mail.com:password1`
#### create vote
`curl -s -X POST -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/user/votes/restaurants/100003 --user user1@mail.com:password1`
#### update vote
`curl -s -X PUT -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/user/votes/restaurants/100004 --user user1@mail.com:password1`
#### get vote history
`curl -s http://localhost:8080/rest/user/votes/history --user user1@mail.com:password1`
#### get vote history between dates
`curl -s 'http://localhost:8080/rest/user/votes/history?startDate=2020-01-01&endDate=2020-04-30' --user user1@mail.com:password1`
#### register Users
`curl -s -i -X POST -d '{"name":"New User","email":"test@mail.ru","password":"test-password"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/user/register`

### Admin 
#### get all restaurants
`curl -s http://localhost:8080/rest/admin/restaurants --user admin@mail.com:admin`
#### get restaurant with id=100003
`curl -s http://localhost:8080/rest/admin/restaurants/100003 --user admin@mail.com:admin`
#### create restaurant
`curl -s -X POST -d '{"name":"Restaurant"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/restaurants --user admin@mail.com:admin`
#### update restaurant with id=100018
`curl -s -X PUT -d '{"id":100018, "name":"Rest"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/restaurants/100018 --user admin@mail.com:admin`
#### delete restaurant with id=100003
`curl -s -X DELETE http://localhost:8080/rest/admin/restaurants/100003 --user admin@mail.com:admin`
#### get all dishes by restaurant id=100004
`curl -s http://localhost:8080/rest/admin/restaurants/100004/dishes --user admin@mail.com:admin`
#### get dish by id=100010
`curl -s http://localhost:8080/rest/admin/dishes/100010 --user admin@mail.com:admin`
#### create dish
`curl -s -X POST -d '{"name":"Kefir", "date":"2020-05-05", "price":250}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/restaurants/100003/dishes --user admin@mail.com:admin`
#### update dish with id=100007
`curl -s -X PUT -d '{"id":100007, "name":"Kefir", "date":"2020-05-05", "price":250}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/rest/admin/restaurants/100003/dishes/100007 --user admin@mail.com:admin`
#### delete dish with id=100007
`curl -s -X DELETE http://localhost:8080/rest/admin/dishes/100007 --user admin@mail.com:admin`
#### get votes by user
`curl -s http://localhost:8080/rest/admin/votes/user/100001 --user admin@mail.com:admin`
#### get votes by restaurant
`curl -s http://localhost:8080/rest/admin/votes/restaurant/100004 --user admin@mail.com:admin`
#### get votes today
`curl -s http://localhost:8080/rest/admin/votes/today --user admin@mail.com:admin`
#### get votes between dates
`curl -s 'http://localhost:8080/rest/admin/votes/between?startDate=2020-05-01&endDate=2020-05-05' --user admin@mail.com:admin`
#### get votes between dates by user id=100001
`curl -s 'http://localhost:8080/rest/admin/votes/user/100001/between?startDate=2020-05-01&endDate=2020-05-05' --user admin@mail.com:admin`






