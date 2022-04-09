package com.KonradRudnicki.TicTacToe;

import com.google.common.collect.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@SpringBootApplication
public class TicTacToeApplication {

    @RequestMapping("/")
    public String viewHome() {
        return "home";
    }

    private final int boardSize = 10;

    public static void main(String[] args) {
        SpringApplication.run(TicTacToeApplication.class, args);
    }

    @Autowired
    private BoardRepository boardRepository;

    @GetMapping("/new")
//    @CrossOrigin(origins = "http://localhost:8000")
    public Board newGame() {
        FieldEnum[][] defaultBoard = new FieldEnum[boardSize][boardSize];

        for (int i = 0; i < defaultBoard.length; i++) {
            for (int j = 0; j < defaultBoard[i].length; j++) {
                defaultBoard[i][j] = FieldEnum.EMPTY;
            }
        }

        Board board = new Board().setFieldGrid(defaultBoard);
        board.setNewBoard(true);
        boardRepository.save(board);
        return board;
    }

    @GetMapping("/set")
//    @CrossOrigin(origins = "http://localhost:8000")
    public GameStatus set(@RequestParam int x, @RequestParam int y,@RequestParam long gameId) {

        FieldEnum[][] newBoard = new FieldEnum[boardSize][boardSize];
        Board currentBoard = boardRepository.findById(gameId).orElse(Iterables.getLast(boardRepository.findAll()));

        for (int i = 0; i < newBoard.length; i++) {
            for (int j = 0; j < newBoard[i].length; j++) {
                newBoard[i][j] = currentBoard.getFieldGrid()[i][j];
            }
        }

        FieldEnum result = currentBoard.getWinner();
        FieldEnum currentChar = currentBoard.getFieldChar();

        if (currentBoard.getWinner() == FieldEnum.EMPTY) {
            if (newBoard[x][y] == FieldEnum.EMPTY) {
                newBoard[x][y] = currentChar;
            }

            currentBoard.setFieldGrid(newBoard);
            result = WinnerCheck.winnerCheck(currentBoard);
            currentBoard.setFieldChar(currentChar == FieldEnum.X ? FieldEnum.O : FieldEnum.X);
            currentBoard.setNewBoard(false);
            currentBoard.setWinner(result);
            boardRepository.save(currentBoard);
        }

        return new GameStatus(currentBoard, result);
    }

    @GetMapping("/load")
//    @CrossOrigin(origins = "http://localhost:8000")
    public Board loadGame(@RequestParam long gameId){
        return boardRepository.findById(gameId).orElseGet(this::newGame);
    }

}

//	@Autowired
//	private CustomerRepository customerRepository;
//
//	@GetMapping("/")
//	public String index() {
//
//		customerRepository.save(new Customer("Adam","Małysz"));
//
//		return "Hello Poland!";
//	}
//
//	@GetMapping("/list")
//	public String showList() {
//		String names = " ";
//
//
//		for (Customer customer : customerRepository.findAll()) {
//			names += customer.getFirstName() + "\n";
//		}
//
//		return names;
//	}
//
//
//	private static final Logger log = LoggerFactory.getLogger(TicTacToeApplication.class);
//
//	public static void main(String[] args) {
//		SpringApplication.run(TicTacToeApplication.class);
//	}
//
//	@Bean
//	public CommandLineRunner demo(CustomerRepository repository) {
//		return (args) -> {
//			// save a few customers
//			repository.save(new Customer("Jack", "Bauer"));
//			repository.save(new Customer("Chloe", "O'Brian"));
//			repository.save(new Customer("Kim", "Bauer"));
//			repository.save(new Customer("David", "Palmer"));
//			repository.save(new Customer("Michelle", "Dessler"));
//
//			// fetch all customers
//			log.info("Customers found with findAll():");
//			log.info("-------------------------------");
//			for (Customer customer : repository.findAll()) {
//				log.info(customer.toString());
//			}
//			log.info("");
//
//			// fetch an individual customer by ID
//			Customer customer = repository.findById(1L);
//			log.info("Customer found with findById(1L):");
//			log.info("--------------------------------");
//			log.info(customer.toString());
//			log.info("");
//
//			// fetch customers by last name
//			log.info("Customer found with findByLastName('Bauer'):");
//			log.info("--------------------------------------------");
//			repository.findByLastName("Bauer").forEach(bauer -> {
//				log.info(bauer.toString());
//			});
//			// for (Customer bauer : repository.findByLastName("Bauer")) {
//			//  log.info(bauer.toString());
//			// }
//			log.info("");
//		};
//	}