$(document).ready(function(){
    $("#new_game").click(async function () {
        $("#board").html("")
        const response = await fetch("http://localhost:8080/new", {});
        const board = await response.json();

        console.log(board)

        for (let i = 0; i < board.fieldGrid.length; i++){
            for (let j = 0; j < board.fieldGrid[i].length; j++){
                const sign = board.fieldGrid[i][j] === "EMPTY"?"#":board.fieldGrid[i][j]
                $("#board").append(`<button id="${i}-${j}">${sign}</button>`)
            }
            $("#board").append("<br/>")
        }

        $("#board button").click(async function (event) {
            // $("#board").html("")

            console.log(event)

            const position =  event.target.id.split("-")
            const response = await fetch(`http://localhost:8080/set?x=${position[0]}&y=${position[1]}`);
            const gameStatus = await response.json();

            const id =`${position[0]}-${position[1]}`
            $(`#board button#${id}`).text(gameStatus.board.fieldGrid[position[0]][position[1]])
        });
    });
});