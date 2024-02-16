const db = require("./teste.json");
const fs = require('fs');

let correto = [{}];
let str = "id,name,price,short_descrtiption,genres,release_date\n";

// correto[] = {
//     id,
//     appID,
//     price,
//     name,
//     publisher,
//     genres,
//     release_date,
// }

function NewData (data){
    let resp = ""
    let i = 7
    for(; i < data.length; i++){
        if(data[i] === " "){
        } else {
            resp+= data[i];
        }
    }
    return resp
}

function NewCat (data){
    let resp = "";
    for(let i = 0; i < data.length; i++){
        resp+= i === data.length - 1 ? `${data[i]}` : `${data[i]}/`;
    }
    return resp
}

let contador = 0;

for (let i = 0; i < 10000000; i++) {
    if (db[i]) {
        str += `${contador++},${db[i].name},${db[i].price},${db[i].short_description},[${NewCat(db[i].genres)}],${NewData(db[i].release_date)}\n`
    }
}

fs.writeFile('dados.csv', str, (err) => {
    if (err) {
        console.error('Erro ao escrever o arquivo:', err);
    } else {
        console.log('Arquivo CSV criado com sucesso!');
    }
});