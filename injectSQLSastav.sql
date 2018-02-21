insert into User(Username, Firstname, Lastname, Email, Phone, Password, Type) values('Admin', 'Adminko','Adminovic','admin@gmail.com','0645896857','MIlan123!','Admin' );
insert into User(Username, Firstname, Lastname, Email, Phone, Password, Type) values('Seller', 'Seler','Selenkovic','seler@gmail.com','0649872182','MIlan123!','Seller' );
insert into User(Username, Firstname, Lastname, Email, Phone, Password, Type) values('User', 'Userko','Userkovic','user@gmail.com','0645896857','MIlan123!','User' );
insert into User(Username, Firstname, Lastname, Email, Phone, Password, Type) values('User2', 'Pera','Perovic','pera@gmail.com','0645896857','MIlan123!','NoType' );

insert into Festival(DateFrom, DateTo, Name, Info, TicketNum) values('2018-2-15', '2018-2-20', 'FESTone','This is first fest. Its very first such fest.',15);  				/* 1 */
insert into Festival(DateFrom, DateTo, Name, Info, TicketNum) values('2018-2-22', '2018-2-26', 'FESTtwo','This is second fest, Its very second such fest', 10);		/* 2 */
insert into Festival(DateFrom, DateTo, Name, Info, TicketNum) values('2018-2-28', '2018-3-5', 'FESTthree', 'This is third fest, its very third such fest', 5);				/* 3 */

insert into Movie(Name, Year, About, Director, Length, Country, Picture, imdb, tomato, youtube) values('Rockenrolla', 2008, 'In London, a real-estate scam puts millions of pounds up for grabs, attracting some of the citys scrappiest tough guys', 'Guy Ritchie', 114, 'England','rockenrollaArt.jpg', 'http://www.imdb.com/title/tt1032755/?ref_=nv_sr_1', 'https://www.rottentomatoes.com/m/rocknrolla/', 'https://www.youtube.com/watch?v=TdpR8VuvbCM');  /* 1 */ 
insert into Galery(Picture, IdMovie) values('rockenrollaImg1.jpg',1);

insert into Actor(Name, IdMovie) values('Gerard Butler', 1);
insert into Actor(Name, IdMovie) values('Mark Strong', 1);
insert into Actor(Name, IdMovie) values('Toby Kebbell', 1);

insert into Location(Building, Adress) values('Slavija theater', 'Belgrade'); /* 1 */
insert into Hall(IdLok) values(1);

insert into Location(Building, Adress) values('Cineplexx usce', 'Belgrade'); /* 2 */
insert into Hall(IdLok) values(2);

insert into OnLocation(IdFest, IdLok) values(1,1);
insert into OnLocation(IdFest, IdLok) values(1,2);
insert into OnLocation(IdFest, IdLok) values(2,1);
insert into OnLocation(IdFest, IdLok) values(2,2);
insert into OnLocation(IdFest, IdLok) values(3,1);
insert into OnLocation(IdFest, IdLok) values(3,2);

insert into Projection(IdMovie, IdHall, Date, Status, VrCount, Price) values(1, 1, '2018-2-18 19:00', 'on',  0, 550); /* 1 */
insert into OnFest(IdFest, IdProjection) values(1,1);
insert into Projection(IdMovie, IdHall, Date, Status, VrCount, Price) values(1, 1, '2018-2-19 10:00', 'Canceled',  1, 550); /* 2 */
insert into OnFest(IdFest, IdProjection) values(1,2);
insert into Projection(IdMovie, IdHall, Date, Status, VrCount, Price) values(1, 1, '2018-2-19 19:00', 'on',  0, 550); /* 3 */
insert into OnFest(IdFest, IdProjection) values(1,3);
insert into Projection(IdMovie, IdHall, Date, Status, VrCount, Price) values(1, 1, '2018-2-25 19:00', 'on',  0, 480); /* 4 */
insert into OnFest(IdFest, IdProjection) values(2,4);
insert into Projection(IdMovie, IdHall, Date, Status, VrCount, Price) values(1, 1, '2018-3-2 14:00', 'on',  0, 480); /* 5 */
insert into OnFest(IdFest, IdProjection) values(3,5);

insert into Reservation(Code, Date, TicketNum, Username, IdProjection, Type, Status, VrCount) values('ASDFGHJKLP', '2018-2-15', 2, 'User', 1, 'Reserved', 'on', 0); /* 1 */
insert into Reservation(Code, Date, TicketNum, Username, IdProjection, Type, Status, VrCount) values('BSDFGHJKLP', '2018-2-15', 2, 'User', 2, 'Reserved', 'on', 0); /* 2 */
insert into Reservation(Code, Date, TicketNum, Username, IdProjection, Type, Status, VrCount) values('CSDFGHJKLP', '2018-2-15', 2, 'User', 3, 'Bought', 'on', 0); /* 3 */
insert into Reservation(Code, Date, TicketNum, Username, IdProjection, Type, Status, VrCount) values('DSDFGHJKLP', '2018-2-15', 2, 'User', 3, 'Bought', 'on', 0); /* 4 */
insert into Reservation(Code, Date, TicketNum, Username, IdProjection, Type, Status, VrCount) values('KKDFGHJKLP', '2018-2-20', 2, 'User', 4, 'Bought', 'on', 0); /* 5 */
insert into Reservation(Code, Date, TicketNum, Username, IdProjection, Type, Status, VrCount) values('KTDFGHJKLP', '2018-2-20', 2, 'User', 5, 'Reserved', 'on', 0); /* 6 */

insert into Feedback(IdRes, Rate, Comment, IdMovie) values(3,9, 'Awesome movie, one of my fav!!!', 1);