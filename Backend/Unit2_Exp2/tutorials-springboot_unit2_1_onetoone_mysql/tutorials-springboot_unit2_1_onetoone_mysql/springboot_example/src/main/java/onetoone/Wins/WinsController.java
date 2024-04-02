package onetoone.Wins;

import io.swagger.annotations.Api;
import onetoone.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
@Api(value = "WinsController", description = "REST APIs related to the Wins Entity")
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



//            public static void main(String[] args) {
//                // Example usage
//                List<Win> wins = new ArrayList<>();
//                // Populate your wins list here...
//
//                // Define your comparator for sorting Wins objects
//                Comparator<Win> winComparator = Comparator.comparingInt(Win::getWins).reversed();
//
//                // Perform merge sort
//                MergeSort<Win> mergeSort = new MergeSort<>();
//                List<Win> sortedWins = mergeSort.mergeSort(wins, winComparator);
//
//                // Display sorted wins
//                for (Win win : sortedWins) {
//                    System.out.println(win);
//                }
//

        //System.out.println(standard.get(0));
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

//    private List<Wins> sortInt(List<Wins> a) {
//        List<Wins> sortA = null;
//
//        return sortA;
//    }

//    private Sort sortByWinsDesc() {
//        return new Sort(Sort.Direction.DESC, "wins");
//    }


        @PostMapping(path = "/wins/{user_id}/{amount_to_add}")
        void postWins ( @PathVariable int user_id, @PathVariable int amount_to_add){
            Wins winner = winsRepository.findById(user_id);
            winner.setWins(winner.getWins() + amount_to_add);
            winsRepository.save(winner);
        }
    }
