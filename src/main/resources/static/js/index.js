let player;
let timer;
let board;
const host = (window.location.hostname === "localhost") ? "http://localhost:8080" : "";

$(document).ready(function () {
    $("#new_game").click(async function () {

        const response = await fetch(host + "/new", {});
        board = await response.json();
        window.location.hash = board.id;
        console.log(board);

        drawGame(board);
        player = "X";
        localStorage.setItem("player", player);
        if (timer){
            clearInterval(timer);
        }

        timer = setInterval(loadGame, 1000);
    });
});

function drawGame(board) {
    $("#current_move").text(board.fieldChar);
    $("#player").text(player);
    $("#winner").text(board.winner === "EMPTY" ? "No winner yet" : board.winner);
    if (board.winner !== "EMPTY"){
        clearInterval(timer);
    }


    $("#board").html("");
    let isCompleted = true;
    let requestCompleted = true;

    for (let i = 0; i < board.fieldGrid.length; i++) {
        for (let j = 0; j < board.fieldGrid[i].length; j++) {
            const sign = board.fieldGrid[i][j] === "EMPTY" ? "#" : board.fieldGrid[i][j];
            $("#board").append(`<button id="${i}-${j}">${sign}</button>`);
        }
        $("#board").append("<br/>");
    }

    $("#board button").click(async function (event) {
        // $("#board").html("")
        const position = event.target.id.split("-");

        if (isCompleted && requestCompleted && board.fieldGrid[position[0]][position[1]] === "EMPTY"){
            isCompleted = false;
            requestCompleted = false;

            console.log(event);

            const response = await fetch(  `${host}/set?x=${position[0]}&y=${position[1]}&gameId=${board.id}`);
            const gameStatus = await response.json();

            const id = `${position[0]}-${position[1]}`;
            $(`#board button#${id}`).text(gameStatus.board.fieldGrid[position[0]][position[1]] === "EMPTY" ? "#" : gameStatus.board.fieldGrid[position[0]][position[1]]);

            isCompleted = true;
            requestCompleted = true;
            board = gameStatus.board;
        }
    })
}

async function loadGame() {
    const response = await fetch(`${host}/load?gameId=${window.location.hash.substring(1)}`, {});
    const board = await response.json();

    drawGame(board);
}