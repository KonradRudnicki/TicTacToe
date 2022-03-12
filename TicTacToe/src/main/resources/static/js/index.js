let player;

$(document).ready(function () {
    $("#new_game").click(async function () {
        if (window.location.hash) {
            await loadGame();
        } else {
            const response = await fetch("http://localhost:8080/new", {});
            const board = await response.json();
            window.location.hash = board.id;
            console.log(board);

            drawGame(board);
            player = "X";
            localStorage.setItem("player", player);
        }

        setInterval(loadGame, 1000)
    });
});

function drawGame(board) {
    $("#current_move").text(board.fieldChar);
    $("#player").text(player);

    $("#board").html("")
    let isCompleted = true;

    for (let i = 0; i < board.fieldGrid.length; i++) {
        for (let j = 0; j < board.fieldGrid[i].length; j++) {
            const sign = board.fieldGrid[i][j] === "EMPTY" ? "#" : board.fieldGrid[i][j];
            $("#board").append(`<button id="${i}-${j}">${sign}</button>`);
        }
        $("#board").append("<br/>");
    }

    $("#board button").click(async function (event) {
        // $("#board").html("")
        if (isCompleted) {
            isCompleted = false
            console.log(event);

            const position = event.target.id.split("-");
            const response = await fetch(`http://localhost:8080/set?x=${position[0]}&y=${position[1]}&gameId=${board.id}`);
            const gameStatus = await response.json();

            const id = `${position[0]}-${position[1]}`;
            $(`#board button#${id}`).text(gameStatus.board.fieldGrid[position[0]][position[1]]);

            isCompleted = true
        }
    })
}

async function loadGame() {
    const response = await fetch(`http://localhost:8080/load?gameId=${window.location.hash.substring(1)}`, {});
    const board = await response.json();

    drawGame(board);
}