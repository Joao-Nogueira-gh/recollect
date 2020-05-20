


function teste(){
    var uri = "https://discover.search.hereapi.com/v1/discover?apikey=xoiul1sZIn_MtE4TuQ00zVjDdzRc7vhUs4-Hq_7zzDE&q=all&in=circle:39.92354,-8.35584;r=50000";

    $.ajax({
        url: uri,
        async: false,

    }).then(function(data) {

        console.log(JSON.stringify(data));

        if(data.length === 0){
            alert("Não existem dados.");
        }

        const itens = data.items;
        const item1 = itens[0];

        const title = item1.title;
        const address = item1.address.label;
        const site  = item1.contacts[0].www[0].value;

        const texto = "Nome: " + title + "\nMorada: " + address + "\nSite: " + site;

        console.log("texto: ", texto);

        $("#API_result").html(texto);

    });
}


$(document).ready(function(){
    teste();

    const tagline = document.getElementById('teste_div');
    tagline.innerHTML = 'BOM DIA com JavaScript!';

});