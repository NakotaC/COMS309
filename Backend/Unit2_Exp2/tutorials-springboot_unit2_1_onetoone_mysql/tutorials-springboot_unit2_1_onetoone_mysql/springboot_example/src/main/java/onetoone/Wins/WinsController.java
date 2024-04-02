package onetoone.Wins;

import onetoone.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class WinsController {
    @Autowired
    WinsRepository winsRepository;

    @Autowired
    UserRepository userRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    //return all cosmetics in the game (in the DB)
    @GetMapping(path = "/wins")
    List<Wins> getWins() {
        List<Wins> standard = winsRepository.findAll();
//        standard.sort(Comparator.comparingInt(Wins::getWins).reversed());


        standard = winsRepository.findAll(Sort.by(Sort.Direction.DESC, "wins"));


        Comparator winComp = new Comparator<Wins>() {
            @Override
            public int compare(Wins o1, Wins o2) {
                return (o1.getWins() > o2.getWins() ? -1 : (o1.getWins() < o2.getWins()) ? 1 : 0);
            }
        };

        standard = mergeSort(standard, winComp);
        for (int i = 0; i < standard.size(); i++) {
            System.out.println(standard.get(i).getWins());
        }
        return standard;
    }

    public List<Wins> mergeSort(List<Wins> list, Comparator<Wins> comparator) {
        if (list.size() <= 1) {
            return list;
        }

        int mid = list.size() / 2;
        List<Wins> left = mergeSort(list.subList(0, mid), comparator);
        List<Wins> right = mergeSort(list.subList(mid, list.size()), comparator);

        return merge(left, right, comparator);
    }

    private List<Wins> merge(List<Wins> left, List<Wins> right, Comparator<Wins> comparator) {
        List<Wins> result = new ArrayList<>();
        int leftIndex = 0;
        int rightIndex = 0;

        while (leftIndex < left.size() && rightIndex < right.size()) {
            if (comparator.compare(left.get(leftIndex), right.get(rightIndex)) <= 0) {
                result.add(left.get(leftIndex));
                leftIndex++;
            } else {
                result.add(right.get(rightIndex));
                rightIndex++;
            }
        }

        // Add remaining elements from left and right sublists
        result.addAll(left.subList(leftIndex, left.size()));
        result.addAll(right.subList(rightIndex, right.size()));

        return result;
    }



        @PostMapping(path = "/wins/{user_id}/{amount_to_add}")
        void postWins ( @PathVariable int user_id, @PathVariable int amount_to_add){
            Wins winner = winsRepository.findById(user_id);
            winner.setWins(winner.getWins() + amount_to_add);
            winsRepository.save(winner);
        }

//          start of "quest controller" which is part of wins since they work very close together.


//          Get method by USER_ID
//          returns [wins remaining to quest, reward scalar]

        @GetMapping(path = "/quest/{user_id}")
        int[] getQuest(@PathVariable int user_id) {
        Wins winner = winsRepository.findById(user_id);
        int fort[] = new int[2];
        fort[0] = winner.getQuest(); fort[1] = winner.getScalar();
        return fort;
    }


//          handles initial case (called when: ACCOUNT CREATED)
//          can be used as PUT, but should not be
//          gives special quest the first day
        @PostMapping(path = "/quest/{user_id}")
        void postQuest(@PathVariable int user_id) {
            Wins winner = winsRepository.findById(user_id);
            if(winner.getQDate() != -1) {
                if (new GregorianCalendar(TimeZone.getTimeZone("UTC+5:00")).getTime().getDate() != winner.getQDate()) {
                    Random rand = new Random();
                    int a = rand.nextInt();
                    if (a % 2 == 0) {
                        winner.setQuest(3);
                        winner.setScalar(5);
                    } else if (a % 2 == 1) {
                        winner.setQuest(5);
                        winner.setScalar(8);
                    }
                    winner.setQDate(new GregorianCalendar(TimeZone.getTimeZone("UTC+5:00")).getTime().getDate());
                }
            }
            else {
                winner.setQuest(1);
                winner.setScalar(10);
                winner.setQDate(new GregorianCalendar(TimeZone.getTimeZone("UTC+5:00")).getTime().getDate());
            }
            winsRepository.save(winner);
        }
//      does very similar work as above, but does not initialize.
//      (called when: USER LOGS ON)
        @PutMapping(path = "/quest/{user_id}")
        void putQuest(@PathVariable int user_id) {
            Wins winner = winsRepository.findById(user_id);
            if (new GregorianCalendar(TimeZone.getTimeZone("UTC+5:00")).getTime().getDate() != winner.getQDate()) {
                Random rand = new Random();
                int a = rand.nextInt();
                if (a % 2 == 0) {
                    winner.setQuest(3);
                    winner.setScalar(5);
                } else if (a % 2 == 1) {
                    winner.setQuest(5);
                    winner.setScalar(8);
                }
                winner.setQDate(new GregorianCalendar(TimeZone.getTimeZone("UTC+5:00")).getTime().getDate());
            }
            winsRepository.save(winner);
        }

        // Put method to run after every game
        // Basically will need to be used like the following:
        // if(game won) { put user_id.wins_needed PUT(user) if (wins_needed == 0, make currency requests}





    //NEEDS FIXED



//NEEDS FIXED



    //NEEDS FIXED






        //NEEDS FIXED




    //NEEDS FIXED

    //SIDE EFFECT DELETING WINS
    @PutMapping(path = "/quest/{user_id}/{wins_needed}")
    void putWinQuest(@PathVariable int user_id, @PathVariable Integer wins_needed) {
        Wins winner = winsRepository.findById(user_id);
        winner.setQuest(wins_needed);
        winsRepository.save(winner);
    }

//different variation of the POST request to allow for very specific quests with rewards to be given

    //THESE ARE BOTH COMING IN AS NULL TOO.
    @PostMapping(path = "/quest/{user_id}/{win}/{reward_scalar}")
    String postUserQuest(@PathVariable int user_id, @PathVariable Integer win, @PathVariable Integer reward_scalar) {
        Wins winner = winsRepository.findById(user_id);
        if(win == null || reward_scalar == null) {
            return failure;
        }
        winner.setQuest(win);
        winner.setScalar(reward_scalar);
        winner.setQDate(new GregorianCalendar(TimeZone.getTimeZone("UTC+5:00")).getTime().getDate());
        winsRepository.save(winner);
        return success;
    }

    }
