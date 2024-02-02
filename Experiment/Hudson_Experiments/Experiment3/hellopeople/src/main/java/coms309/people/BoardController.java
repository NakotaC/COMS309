package coms309.people;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class BoardController {
    char[][] gameboard = new char[3][3];

    void gb_init() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                gameboard[i][j] = ' ';
            }
        }
    }

    @PutMapping("/reset")
    public void resetboard() {
        gb_init();
    }
    @GetMapping("/showboard")
    public char[][] showBoard() {
        return(gameboard);
    }

    @GetMapping("/cfw")
    public String checkForWinner() {
        if((gameboard[0][0] == gameboard[0][1] && gameboard[0][0] == gameboard[0][2] && gameboard[0][2] == 'x') ||
                (gameboard[1][0] == gameboard[1][1] && gameboard[1][0] == gameboard[1][2] && gameboard[1][2] == 'x') ||
                (gameboard[2][0] == gameboard[2][1] && gameboard[2][0] == gameboard[2][2] && gameboard[2][2] == 'x') ||
                (gameboard[0][0] == gameboard[1][0] && gameboard[0][0] == gameboard[2][0] && gameboard[2][0] == 'x') ||
                (gameboard[0][1] == gameboard[1][1] && gameboard[0][1] == gameboard[2][1] && gameboard[2][1] == 'x') ||
                (gameboard[0][2] == gameboard[1][2] && gameboard[0][2] == gameboard[2][2] && gameboard[2][2] == 'x') ||
                (gameboard[0][0] == gameboard[1][1] && gameboard[0][0] == gameboard[2][2] && gameboard[2][2] == 'x') ||
                (gameboard[2][0] == gameboard[1][1] && gameboard[2][0] == gameboard[0][2] && gameboard[0][2] == 'x')) {
            return "X HAS WON!";
        }
        else if((gameboard[0][0] == gameboard[0][1] && gameboard[0][0] == gameboard[0][2] && gameboard[0][2] == 'o') ||
                (gameboard[1][0] == gameboard[1][1] && gameboard[1][0] == gameboard[1][2] && gameboard[1][2] == 'o') ||
                (gameboard[2][0] == gameboard[2][1] && gameboard[2][0] == gameboard[2][2] && gameboard[2][2] == 'o') ||
                (gameboard[0][0] == gameboard[1][0] && gameboard[0][0] == gameboard[2][0] && gameboard[2][0] == 'o') ||
                (gameboard[0][1] == gameboard[1][1] && gameboard[0][1] == gameboard[2][1] && gameboard[2][1] == 'o') ||
                (gameboard[0][2] == gameboard[1][2] && gameboard[0][2] == gameboard[2][2] && gameboard[2][2] == 'o') ||
                (gameboard[0][0] == gameboard[1][1] && gameboard[0][0] == gameboard[2][2] && gameboard[2][2] == 'o') ||
                (gameboard[2][0] == gameboard[1][1] && gameboard[2][0] == gameboard[0][2] && gameboard[0][2] == 'o')) {
            return "O HAS WON!";
        }
        return "no winner yet!";
    }

    //uses an top left is (0,0), [x, abs(y)] coordinate system to place "team" in that location
    //teams are 'x' and 'o'
    @PutMapping("/{x}/{y}/{team}")
    public int makeMove(@PathVariable int x, @PathVariable int y, @PathVariable char team){
        if(team != 'x' && team != 'o') {
            return -1;
        }
        if(gameboard[y][x] != ' ' && gameboard[y][x] != 'x' && gameboard[y][x] != 'o') {
            gameboard[y][x] = team;
        }
        return 1;
    }

}
