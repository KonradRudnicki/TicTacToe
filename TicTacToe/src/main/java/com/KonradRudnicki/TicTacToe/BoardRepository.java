package com.KonradRudnicki.TicTacToe;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface BoardRepository extends CrudRepository<Board, Long> {

}