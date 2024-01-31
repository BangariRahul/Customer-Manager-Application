console.log("hell0");

const search = () => {
    //console.log("searching....");
    let type = $("#search-type").val();
    let query = $("#search-input").val();

    if(query == ""){
        $(".search-result").hide();
    }
    else{
        //search
        console.log(query);

        //sending request to the server

        let url=`http://localhost:8080/search/${type}/${query}`;

        fetch(url)
        .then((response) => {
            return response.json();
        })
        .then((data) => {
            console.log(data);

            let text = `<div class='list-group'>`;

            data.forEach((Customer) => {
            console.log("---------");
                text+= `<a href='/user/showCustomer/${Customer.cid}' class='list-group-item list-group-action'> ${Customer.firstName + " " +Customer.lastName} </a>`;
            });

            text+= `</div>`;

            $(".search-result").html(text);
            $(".search-result").show();
        });

    }
};