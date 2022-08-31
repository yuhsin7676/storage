# storage

## API

### AddBuy
##### Параметр
buy
##### Значение 
{
	id: 0,
	storage_id: 1,
	items:"[{id: 1, number: 3, price: 150 }]"
}
##### Возвращает
"OK" или сообщение об ошибке

### AddItem
Параметр: item
Значение: 
{
	id: 0,
	name:"New item",
	price_buy: 140,
	price_sale: 160
}
Возвращает: "OK" или сообщение об ошибке

### AddMove
Параметр: move
Значение: 
{
	id: 0,
	storage_from: 3,
	storage_to: 4,
	items: "[{id: 1, number: 3}]"
}
Возвращает: "OK" или сообщение об ошибке

### AddSale
Параметр: sale
Значение: 
{
	id: 0,
	storage_id: 3,
	items: "[{id: 1, number: 3, price: 160}]"
}
Возвращает: "OK" или сообщение об ошибке

### AddStorage
Параметр: storage
Значение: 
{
	id: 0,
	name:"New storage"
}
Возвращает: "OK" или сообщение об ошибке

### DeleteBuy
Параметр: buy
Значение: 
{
	id: 0,
	storage_id: 1,
	items: "[{id: 1, number: 3, price: 150 }]"
}
Возвращает: "OK" или сообщение об ошибке

### DeleteItem
Параметр: item
Значение: 
{
	id: 0,
	name: "New item",
	price_buy: 140,
	price_sale: 160
}
Возвращает: "OK" или сообщение об ошибке

### DeleteMove
Параметр: move
Значение: 
{
	id: 0,
	storage_from: 3,
	storage_to: 4,
	items: "[{id: 1, number: 3}]"
}
Возвращает: "OK" или сообщение об ошибке

### DeleteSale
Параметр: sale
Значение: 
{
	id: 0,
	storage_id: 3,
	items: "[{id: 1, number: 3, price: 160}]"
}
Возвращает: "OK" или сообщение об ошибке

### DeleteStorage
Параметр: storage
Значение: 
{
	id: 0,
	name: "Deleted storage"
}
Возвращает: "OK" или сообщение об ошибке

### FindAllBuy
Возвращает: список документов о покупках

### FindAllItem
Параметр: filter
Значение: "['banana', 'orange']"
Возвращает: список товаров в номенклатуре

### FindAllItemInStorage
Параметр: filter
Значение: "[1, 2]"
Возвращает: список товаров на складах

### FindAllMove
Возвращает: список документов о перемещениях

### FindAllSale
Возвращает: список документов о продажах

### FindAllStorage
Возвращает: список складов

### UpdateItem
Параметр: item
Значение: 
{
	id: 0,
	name: "Updated item",
	price_buy: 140,
	price_sale: 160
}
Возвращает: "OK" или сообщение об ошибке

### UpdateStorage
Параметр: storage
Значение: 
{
	id: 0,
	name: "Updated storage"
}
Возвращает: "OK" или сообщение об ошибке
