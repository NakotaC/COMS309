package onetoone.Wins;

import onetoone.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
    }
