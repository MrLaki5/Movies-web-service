update User set Type='User' where Username='mimar';





insert into Festival(DateFrom, DateTo, Name) values('2018-2-17', '2018-2-22', 'FESTone');


insert into Festival(DateFrom, DateTo, Name) values('2018-2-13', '2018-2-19', 'nestoKao2');
insert into Festival(DateFrom, DateTo, Name) values('2018-2-13', '2018-2-19', 'nestoKao3');
insert into Festival(DateFrom, DateTo, Name) values('2018-2-13', '2018-2-19', 'nestoKao4');
insert into Festival(DateFrom, DateTo, Name) values('2018-2-13', '2018-2-19', 'nestoKao5');
insert into Festival(DateFrom, DateTo, Name) values('2018-2-13', '2018-2-19', 'nestoKao6');
insert into Festival(DateFrom, DateTo, Name) values('2018-2-13', '2018-2-19', 'nestoKao7');
insert into Festival(DateFrom, DateTo, Name) values('2018-2-13', '2018-2-19', 'nestoKao8');
insert into Festival(DateFrom, DateTo, Name) values('2018-2-10', '2018-2-19', 'prvenaac');

insert into Movie(Name, Year, About, Director, Length, Country) values('Asov', 2014, 'horor film u sumi kod save', 'Milan Lazarevic', 20, 'Serbia'); 
insert into Movie(Name, Year, About, Director, Length, Country) values('4 dana', 2010, 'trke bicikala', 'Milan Lazarevic', 15, 'Serbia'); 

insert into Location(Building, Adress) values('ZKS', 'kralj petra 23');
insert into Location(Building, Adress) values('Hogar', 'zmaj jovina 31');

insert into Hall(IdLok) values(2); /*3*/
insert into Hall(IdLok) values(1); /*2*/

insert into Projection(IdMovie, IdHall, Hour, Date) values(1, 2, 12, '2018-2-19'); /*2*/
insert into Projection(IdMovie, IdHall, Hour, Date) values(2, 3, 12, '2018-2-20'); /*3*/
insert into Projection(IdMovie, IdHall, Hour, Date) values(2, 3, 14, '2018-2-20'); /*4*/
insert into Projection(IdMovie, IdHall, Hour, Date) values(2, 3, 14, '2018-1-20'); /*5*/

insert into OnFest(IdFest, IdProjection) values(2,2);
insert into OnFest(IdFest, IdProjection) values(7,4);

insert into Reservation(Code, Date, TicketNum, Username, IdProjection, Type) values('ASVHBESJDK', '2018-2-16', 3, 'mimar', 3, 'Bought');
insert into Reservation(Code, Date, TicketNum, Username, IdProjection, Type) values('KOVHBESJDK', '2018-2-16', 3, 'mimar', 5, 'Bought');


insert into Reservation(Code, Date, TicketNum, Username, IdProjection, Type) values('PKVHBESJDK', '2018-2-16', 1, 'mimar', 5, 'Reserved');
insert into Reservation(Code, Date, TicketNum, Username, IdProjection, Type) values('TKVHBESJDK', '2018-2-16', 3, 'mimar', 2, 'Reserved');
