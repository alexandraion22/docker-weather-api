# Tema 2 SPRC - Microservicii & Docker

## Realizarea Temei

- Nume : `Ion Alexandra Ana-Maria`
- Grupa : `341C1`
- Timp de lucru :`~ 40 de ore`

## Tehnologiile Utilizate

- REST API - `Java Spring Boot`
- Baza de date - `MongoDB`
- Utilitar de gestiune al bazelor de date - `Mongo Express`

## Rularea Temei

### Testare

Pentru a putea testa tema este necesara doar rularea comenzii `docker compose up`. Aceasta va porni
toate cele 3 servicii necesare.

Am testat tema cu ajutorul utilitarului `Postman`, atat cu testele ordonate de pe forum, cat si cu teste
realizate personal (ex. testarea stergerii in cascada, deoarece nu era testata in cele date).

### Porturi si acces API si utilitar BD

- Portul 8080 - `REST API` - toate cererile catre API sunt de forma localhost:8080/[...]
- Portul 8081 - `Mongo Express` - pentru a putea accesa utilitarul de baze de date, acesta se gaseste la adresa
  localhost:8081/. Autentificarea cu rolul de admin poate fi realizata utilizand utilizatorul `student` si parola `student`
- Portul 27017 - `MongoDB` - baza de date este accesata de API prin portul 27017

Am impartit seviciile in doua retele diferite de Docker: `api_database` (REST API si BD) si `database_dbadmin`(BD si Utilitar BD),
conform cerintei si a ceea ce am mai intrebat la laborator.

## Implementare REST API

Implementarea mai exacta a fiecarei functii este detaliata in comentarii.

### Colectii

Colectiile au fost create conform cerintei, contin exact campurile din cerinta si constangerile
de unicitate specificate.

**IMPORTANT** - Colectiile `countries`, `cities` si `temperatures` se pot gasi in `weather_db`. Restul
colectiilor au fost create automat de MongoDB (ex. colectii in care se salveaza automat detalii despre
utilizatori sau sesiune) si pot fi ignorate.

## Observatii

### Stergerea in Cascada

Dupa cum a fost precizat in [aceasta postare de pe forum](https://curs.upb.ro/2023/mod/forum/discuss.php?d=1799),
odata cu stergerea tarilor/oraselor din colectii, se realizeaza stergerea in cascada: daca stergem o tara, mai intai
stergem toate orasele corespunzatoare id-ului tarii si temperaturile corespunzatoare oraselor tarii, iar dupa aceea este
stearsa tara din baza de date, de asemenea, daca stergem un oras, se sterg si toate temperaturile corespunzatoare.

### Update Temperature

Pentru metoda de `PUT/TEMPERATURE` am optat pentru a nu modifica timestamp-ul, deoarece
m-am gandit ca asa ar fi cel mai logic. M-am gandit de exemplu ca am dori sa corectam anumite
intrari prin aceasta metoda (ex. a fost inregistrata temperatura gresita). Daca nu am include aceasta
posibilitate, nu am putea modifica datele anterioare in niciun fel si acestea sa ramana cu timestamp-ul initial.

### Eroare 400 DELETE

In cererile de delete, sau orice alta cerere care contine
PathVaribile, se verifica automat daca este bad request.

### Precizie timestamp

Asa cum a fost precizat in [aceasta postare de pe forum](https://curs.upb.ro/2023/mod/forum/discuss.php?d=1777),
am crescut precizia timestamp-ului pana la ordinul miimilor de secunda.

### Multi-Stage Build

Am intrebat in cadrul laboratorului legat de practici bune pentru build-ul unui proiect in Java
si am inteles ca cel mai ok din punct de vedere al securitatii si corectitudinii, asa ca am studiat
diferite moduri de a face acest lucru si am implementat.

### Generate Sequence

Dat fiind faptul ca id-ul generat automat de catre MongoDB este de forma unui String, am optat
pentru metoda generateSequence (prin care practic retin in alta colectie utimul id intreg alocat in cele
3 colectii : `countries`, `cities`, `temperatures`, care va persista atata timp cat nu este sters volumul),
cu care se asigura atomicitatea setarii urmatorului id. Astfel, prin utilizarea acestei metode sunt generate
id-uri noi numere intregi, incepand de la 1, in ordine crescatoare.

### Volum Baza de Date

Pentru a putea persista datele intre sesiuni, pentru baza de date este creat un volum, astfel,
chiar daca rulaam `docker compose down`, datele din sesiunea finalizata sunt inca salvate pentru
sesiunea urmatoare.

## Referinte

- [Mongo-express Docker](https://hub.docker.com/_/mongo-express)
- [Why Multi-Stage Build - Spring Boot](https://spring.io/guides/topicals/spring-boot-docker/)
