const db = require("../teste.json");
const fs = require('fs');

// String padrao para ser madada para o arquivo CSV

let str = "id./;steamID./;name./;price./;short_descritiption./;genres./;publishers./;roda_linux./;release_date\n";

// Trata a data do Json
function NewData (data){
    let month = "", day = "", year = "";

    for(let i = 0; i < 3; i++){
        month+= data[i];
    }
    month = returnMonth(month);

    for(let i = 4; i < 6; i++){
        if(data[i+1] === ","){
            day+= data[i];
            break
        }else{
            day+= data[i];
        }
    }

    for(let i = 7; i < data.length; i++){
        if(data[i] === " "){
        } else {
            year+= data[i];
        }
    }

    return day + "/" + month + "/" + year;
}

// Função para retornar o mês correto
function returnMonth (data){
    const months = {
        "Jan": 1,
        "Feb": 2,
        "Mar": 3,
        "Apr": 4,
        "May": 5,
        "Jun": 6,
        "Jul": 7,
        "Aug": 8,
        "Sep": 9,
        "Oct": 10,
        "Nov": 11,
        "Dec": 12
    };

    if(months[data]){
        return months[data];
    } else{
        return null;
    }
}

// Função para retornar os itens que apresentam mais de dois elementos (vetor)
function NewDivision (data){
    let resp = "";
    for(let i = 0; i < data.length; i++){
        resp+= i === data.length - 1 ? `${data[i]}` : `${data[i]}/`;
    }
    return resp
}

// Função que recebe um boolean e transforma em string Sim ou Nao
function booleanReturn (data){
    return data ? "Sim" : "Nao";
}

// Contador para ser usado como ID no BD
let contador = 0;

for (let i = 0; i < 10000000; i++) {
    // Faz o tratamento para apenas utilizar os dados que não são nulos e coloca todos em uma string str para ser inserido no csv
    if (db[i] && db[i].short_description.length > 1 && db[i].publishers.length > 0 && db[i].genres.length > 0) {
        str += `${contador++}./;${i}./;${db[i].name}./;${db[i].price}./;${db[i].short_description}./;${NewDivision(db[i].publishers)}./;${NewDivision(db[i].genres)}./;${booleanReturn(db[i].linux)}./;${NewData(db[i].release_date)}\n`
    }
}

// Inserção no CSV
fs.writeFile('./Bagunca_tupac/transformar_byte/games_tobyte.csv', str, (err) => {
    if (err) {
        console.error('Erro ao escrever o arquivo:', err);
    } else {
        console.log('Arquivo CSV criado com sucesso!');
    }
});