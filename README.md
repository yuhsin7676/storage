# storage

## API

### AddBuy
__Параметр:__ buy <br>
__Значение:__ <br>
`{
	id: 0,
	storage_id: 1,
	items:"[{id: 1, number: 3, price: 150 }]"
}`<br>`
__Возвращает:__ "OK" или сообщение об ошибке <br>

***
### AddItem
__Параметр:__ item <br>
__Значение:__ <br>
`{
	id: 0,
	name:"New item",
	price_buy: 140,
	price_sale: 160
}`<br>
__Возвращает:__ "OK" или сообщение об ошибке <br>

***
### AddMove
__Параметр:__ move <br>
__Значение:__ <br>
`{
	id: 0,
	storage_from: 3,
	storage_to: 4,
	items: "[{id: 1, number: 3}]"
}`<br>
__Возвращает:__ "OK" или сообщение об ошибке <br>

***
### AddSale
__Параметр:__ sale <br>
__Значение:__ <br>
`{
	id: 0,
	storage_id: 3,
	items: "[{id: 1, number: 3, price: 160}]"
}`<br>
__Возвращает:__ "OK" или сообщение об ошибке <br>

***
### AddStorage
__Параметр:__ storage <br>
__Значение:__ <br>
`{
	id: 0,
	name:"New storage"
}`<br>
__Возвращает:__ "OK" или сообщение об ошибке <br>

***
### DeleteBuy
__Параметр:__ buy <br>
__Значение:__ <br>
`{
	id: 0,
	storage_id: 1,
	items: "[{id: 1, number: 3, price: 150 }]"
}`<br>
__Возвращает:__ "OK" или сообщение об ошибке <br>

***
### DeleteItem
__Параметр:__ item <br>
__Значение:__ <br>
`{
	id: 0,
	name: "New item",
	price_buy: 140,
	price_sale: 160
}`<br>
__Возвращает:__ "OK" или сообщение об ошибке <br>

***
### DeleteMove
__Параметр:__ move <br>
__Значение:__ <br>
`{
	id: 0,
	storage_from: 3,
	storage_to: 4,
	items: "[{id: 1, number: 3}]"
}`<br>
__Возвращает:__ "OK" или сообщение об ошибке <br>

***
### DeleteSale
__Параметр:__ sale <br>
__Значение:__ <br>
`{
	id: 0,
	storage_id: 3,
	items: "[{id: 1, number: 3, price: 160}]"
}`<br>
__Возвращает:__ "OK" или сообщение об ошибке <br>

***
### DeleteStorage
__Параметр:__ storage <br>
__Значение:__ <br>
`{
	id: 0,
	name: "Deleted storage"
}`<br>
__Возвращает:__ "OK" или сообщение об ошибке <br>

***
### FindAllBuy
__Возвращает:__ список документов о покупках <br>

***
### FindAllItem
__Параметр:__ filter <br>
__Значение:__ <br>
`['banana', 'orange']` <br>
__Возвращает:__ список товаров в номенклатуре <br>

***
### FindAllItemInStorage
__Параметр:__ filter <br>
__Значение:__ <br>
`[1, 2]` <br>
__Возвращает:__ список товаров на складах <br>

***
### FindAllMove
__Возвращает:__ список документов о перемещениях <br>

***
### FindAllSale
__Возвращает:__ список документов о продажах <br>

***
### FindAllStorage
__Возвращает:__ список складов <br>

***
### UpdateItem
__Параметр:__ item <br>
__Значение:__ <br>
`{
	id: 0,
	name: "Updated item",
	price_buy: 140,
	price_sale: 160
}`<br>
__Возвращает:__ "OK" или сообщение об ошибке <br>

***
### UpdateStorage
__Параметр:__ storage <br>
__Значение:__ <br>
`{
	id: 0,
	name: "Updated storage"
}`<br>
__Возвращает:__ "OK" или сообщение об ошибке <br>
