var input = document.getElementById("input");
var output = document.getElementById("output");
var chooseURL = document.getElementById("chooseURL");
var parameterName = "item";

chooseURL.onchange = function(){
    
    switch(chooseURL.value){
        case "AddBuy":
            parameterName = "buy";
            input.placeholder = 
                    "{\n"
                  + "    id: 0, \n"
                  + "    storage_id: 1, \n"
                  + "    items:\"[{id: 1, number: 3, price: 150 }]\"\n"
                  + "}";
            break;
        case "AddItem":
            parameterName = "item";
            input.placeholder = 
                    "{\n"
                  + "    id: 0, \n"
                  + "    name: \"New item\", \n"
                  + "    price_buy: 140, \n"
                  + "    price_sale: 160 \n"
                  + "}";
            break;
        case "AddMove":
            parameterName = "move";
            input.placeholder = 
                    "{\n"
                  + "    id: 0, \n"
                  + "    storage_from: 3, \n"
                  + "    storage_to: 4, \n"
                  + "    items: \"[{id: 1, number: 3}]\" \n"
                  + "}";
            break;
        case "AddSale":
            parameterName = "sale";
            input.placeholder = 
                    "{\n"
                  + "    id: 0, \n"
                  + "    storage_id: 3, \n"
                  + "    items: \"[{id: 1, number: 3, price: 160}]\" \n"
                  + "}";
            break;
        case "AddStorage":
            parameterName = "storage";
            input.placeholder = 
                    "{\n"
                  + "    id: 0, \n"
                  + "    name: \"New storage\" \n"
                  + "}";
            break;
        case "DeleteBuy":
            parameterName = "buy";
            input.placeholder = 
                    "{\n"
                  + "    id: 0, \n"
                  + "    storage_id: 1, \n"
                  + "    items:\"[{id: 1, number: 3, price: 150 }]\"\n"
                  + "}";
            break;
        case "DeleteItem":
            parameterName = "item";
            input.placeholder = 
                    "{\n"
                  + "    id: 0, \n"
                  + "    name: \"New item\", \n"
                  + "    price_buy: 140, \n"
                  + "    price_sale: 160 \n"
                  + "}";
            break;
        case "DeleteMove":
            parameterName = "move";
            input.placeholder = 
                    "{\n"
                  + "    id: 0, \n"
                  + "    storage_from: 3, \n"
                  + "    storage_to: 4, \n"
                  + "    items: \"[{id: 1, number: 3}]\" \n"
                  + "}";
            break;
        case "DeleteSale":
            parameterName = "sale";
            input.placeholder = 
                    "{\n"
                  + "    id: 0, \n"
                  + "    storage_id: 3, \n"
                  + "    items: \"[{id: 1, number: 3, price: 160}]\" \n"
                  + "}";
            break;
        case "DeleteStorage":
            parameterName = "storage";
            input.placeholder = 
                    "{\n"
                  + "    id: 0, \n"
                  + "    name: \"Deleted storage\" \n"
                  + "}";
            break;
        case "FindAllBuy":
            input.placeholder = "";
            break;
        case "FindAllItem":
            parameterName = "filter";
            input.placeholder = "['banana', 'orange']";
            break;
        case "FindAllItemInStorage":
            parameterName = "filter";
            input.placeholder = "[1, 2]";
            break;
        case "FindAllMove":
            input.placeholder = "";
            break;
        case "FindAllSale":
            input.placeholder = "";
            break;
        case "FindAllStorage":
            input.placeholder = "";
            break;
        case "UpdateItem":
            parameterName = "item";
            input.placeholder = 
                    "{\n"
                  + "    id: 0, \n"
                  + "    name: \"Updated item\", \n"
                  + "    price_buy: 140, \n"
                  + "    price_sale: 160 \n"
                  + "}";
            break;
        case "UpdateStorage":
            parameterName = "storage";
            input.placeholder = 
                    "{\n"
                  + "    id: 0, \n"
                  + "    name: \"Updated storage\" \n"
                  + "}";
            break;
        default:
            input.placeholder = "";
            break;
        
    }
    
}

function addTemplateToInput(){
    input.value = input.placeholder;
}

function post(){
    
    var data = {};
    data[parameterName] = input.value;
    
    $.ajax({
        url: chooseURL.value,
        data: data,
        method: "POST",
        success: function(data){
            output.value = data;
        }
    });
}


