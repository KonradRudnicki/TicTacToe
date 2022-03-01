package com.KonradRudnicki.TicTacToe;

import com.google.common.collect.Iterables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@SpringBootApplication
public class TicTacToeApplication {
    public static void main(String[] args) {
        SpringApplication.run(TicTacToeApplication.class);
    }

    @Autowired
    private BoardRepository boardRepository;

    @GetMapping("/new")
    public String index() {
        FieldEnum[][] defaultBoard = new FieldEnum[10][10];

        for (int i = 0; i < defaultBoard.length; i++) {
            for (int j = 0; j < defaultBoard[i].length; j++) {
                defaultBoard[i][j] = FieldEnum.EMPTY;
            }
        }

        boardRepository.save(new Board().setFieldGrid(defaultBoard));

        return "TicTacToe - new game";
    }

    @GetMapping("/set")
    public String set(@RequestParam int x, @RequestParam int y) {
        FieldEnum[][] newBoard = new FieldEnum[10][10];
        Board currentBoard = Iterables.getLast(boardRepository.findAll());

        for (int i = 0; i < newBoard.length; i++) {
            for (int j = 0; j < newBoard[i].length; j++) {
                newBoard[i][j] = currentBoard.getFieldGrid()[i][j];
            }
        }

        FieldEnum currentChar = currentBoard.getFieldChar();
        if (newBoard[x][y] == FieldEnum.EMPTY){
            newBoard[x][y] = currentChar;
        }else {
            return "The field is already set";
        }

        currentBoard.setFieldGrid(newBoard);
        FieldEnum result =  WinnerCheck.winnerCheck(currentBoard);
        currentBoard.setFieldChar(currentChar == FieldEnum.X ? FieldEnum.O : FieldEnum.X);
        boardRepository.save(currentBoard);

        switch (result){
            case X -> {return "Player X won";}
            case O -> {return "Player O won";}
            case DRAW ->{return "There is a draw";}
        }

        return "TicTacToe - set: " + "x: " + x + " " + "y: " + y + " " + "fV: " + currentChar;
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