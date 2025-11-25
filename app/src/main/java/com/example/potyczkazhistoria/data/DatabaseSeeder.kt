package com.example.potyczkazhistoria.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object DatabaseSeeder {
    fun seed(database: AppDatabase) {
        val epochDao = database.epochDao()
        val chapterDao = database.chapterDao()
        val questionDao = database.questionDao()
        val answerDao = database.answerDao()

        CoroutineScope(Dispatchers.IO).launch {
            answerDao.deleteAll()
            questionDao.deleteAll()
            chapterDao.deleteAll()
            epochDao.deleteAll()

            val epochs = listOf(
                EpochEntity(id = 1, name = "Przez epoki o Polsce (Klasa IV)"),
                EpochEntity(id = 2, name = "Od pierwszych cywilizacji do Polski XIII - XV w. (Klasa V)"),
                EpochEntity(id = 3, name = "Od wielkich odkryć geograficznych przez epokę napoleońską. (Klasa VI)"),
                EpochEntity(id = 4, name = "Od Europy po kongresie wiedeńskim do wybuchu II wojny światowej. (Klasa VII)"),
                EpochEntity(id = 5, name = "Od Wojny obronnej Polski we Wrześniu 1939 r. do miejsca Polski w świecie współczesnym. (Klasa VIII)"),

            )
            epochDao.insertAll(epochs)

            val chapters = listOf(
                ChapterEntity(id = 1, title = "Z historią na Ty (rozdział I)", epochId = 1),
                ChapterEntity(id = 2, title = "Od Piastów do Jagiellonów (rozdział II)", epochId = 1),
                ChapterEntity(id = 3, title = "Wojny i upadek Rzeczypospolitej (Rozdział III)", epochId = 1),
                ChapterEntity(id = 4, title = "Ku współczesnej Polsce (Rozdział IV)", epochId = 1),

                ChapterEntity(id = 5, title = "Pierwsze cywilizacje (rozdział I)", epochId = 2),
                ChapterEntity(id = 6, title = "Starożytna Grecja (Rozdział II)", epochId = 2),
                ChapterEntity(id = 7, title = "Starożytny Rzym (Rozdział III)", epochId = 2),
                ChapterEntity(id = 8, title = "Początki średniowiecza (Rozdział IV)", epochId = 2),
                ChapterEntity(id = 9, title = "Społeczeństwo średniowiecza (Rozdział V)", epochId = 2),
                ChapterEntity(id = 10, title = "Polska pierwszych Piastów (Rozdział VI)", epochId = 2),
                ChapterEntity(id = 11, title = "Polska w XIII-XV wieku (Rozdział VII)", epochId = 2),

                ChapterEntity(id = 12, title = "Narodziny nwowożytnego świata (Rozdział I)", epochId = 3),
                ChapterEntity(id = 13, title = "W Rzeczypospolitej szlacheckiej (Rozdział II)", epochId = 3),
                ChapterEntity(id = 14, title = "W obronie granic Rzeczypospolitej (Rozdział III)", epochId = 3),
                ChapterEntity(id = 15, title = "Od absolutyzmu do republiki (Rozdział IV)", epochId = 3),
                ChapterEntity(id = 16, title = "Upadek Rzeczypospolitej (Rozdział V)", epochId = 3),
                ChapterEntity(id = 17, title = "Rewolucja francuska i okres napoleoński (Rozdział VI)", epochId = 3),

                ChapterEntity(id = 18, title = "Europa po kongresie wiedeńskim (Rozdział I)", epochId = 4),
                ChapterEntity(id = 19, title = "Ziemie polskie po kongresie wiedeńskim (Rozdział II)", epochId = 4),
                ChapterEntity(id = 20, title = "Europa i świat po Wiośnie Ludów (Rozdział III)", epochId = 4),
                ChapterEntity(id = 21, title = "Ziemie polskie po Wiośnie Ludów (Rozdział IV)", epochId = 4),
                ChapterEntity(id = 22, title = "I wojna światowa (Rozdział V)", epochId = 4),
                ChapterEntity(id = 23, title = "Świat w okresie międzywojennym (Rozdział V)", epochId = 4),
                ChapterEntity(id = 24, title = "Polska w okresie międzywojennym (Rozdział VI)", epochId = 4),

                ChapterEntity(id = 25, title = "II wojna światowa (Rozdział I)", epochId = 5),
                ChapterEntity(id = 26, title = "Polacy podczas II wojny światowej (Rozdział II)", epochId = 5),
                ChapterEntity(id = 27, title = "Świat po II wojnie światowej (Rozdział III)", epochId = 5),
                ChapterEntity(id = 28, title = "Polska po II wojnie światowej (Rozdział IV)", epochId = 5),
                ChapterEntity(id = 29, title = "Upadek komunizmu (Rozdział V)", epochId = 5),
                ChapterEntity(id = 30, title = "Polska i świat w nowej epoce (Rozdział VI)", epochId = 5)




            )
            chapterDao.insertAll(chapters)

            val questions = mutableListOf<QuestionEntity>()
            val answers = mutableListOf<AnswerEntity>()
            var qId = 1
            var aId = 1

            fun addQuestion(chapterId: Int, text: String, difficulty: String, options: List<Pair<String, Boolean>>) {
                questions.add(QuestionEntity(id = qId, text = text, difficulty = difficulty, chapterId = chapterId))
                options.forEach { (answerText, correct) ->
                    answers.add(AnswerEntity(id = aId++, text = answerText, isCorrect = correct, questionId = qId))
                }
                qId++
            }

            addQuestion(1, "Najważniejsze święta państwowe to:", "łatwy",
                listOf("Święto Narodowe 3 Maja, Narodowe Święto Niepodległości (11 listopada)." to true, "Dzień kobiet i Dzień dziecka." to false, "Sylwester i Nowy Rok." to false, "Narodowe Święto Trzech Króli." to false))
            addQuestion(1, "Czym zajmują się historycy?", "łatwy",
                listOf("Badaniem i opisywaniem przeszłości" to true, "Opowiadaniem legend" to false, "Badaniem znalezisk" to false, "Kopaniem w ziemi" to false))
            addQuestion(1, "Jak się nazywa epoka, w której żyjesz?", "łatwy",
                listOf("Współczesność" to true, "Dzisiejszość" to false, "Nowoczesność" to false, "Teraźniejszość" to false))
            addQuestion(1, "Co to jest historia?", "łatwy",
                listOf("Nauka o dziejach ludzkości" to true, "Nauka o literaturze" to false, "Nauka o materiałach" to false, "Nauka o opowiadaniach" to false))
            addQuestion(1, "Podaj poprawną nazwę państwa polskiego.", "łatwy",
                listOf("Rzeczpospolita Polska" to true, "Rzeczypospolita Polska" to false, "Polska Rzeczpospolita" to false, "Polska Ludowa" to false))
            addQuestion(1, "Co to jest naród?", "łatwy",
                listOf("To grupa ludzi zamieszkujących to samo terytorium i mających to samo pochodzenie." to true, "To cała rodzina wraz z krewnymi." to false, "To grupa ludzi o takich samych zainteresowaniach." to false, "To grupa ludzi wierząca w to samo." to false))
            addQuestion(1, "Co to jest region?", "łatwy",
                listOf("To część obszaru, która wyróżnia się zwyczajami, historią albo krajobrazem." to true, "To miasto." to false, "To wieś z polami." to false, "To kilka państw." to false))
            addQuestion(1, "Podaj dwa przykłady świąt rodzinnych.", "łatwy",
                listOf("Urodziny członka rodziny, rocznica ślubu." to true, "Spacer, wizyta w kinie." to false, "Wspólny obiad, odwiedziny krewnego." to false, "Praca w polu, wspólne gotowanie." to false))
            addQuestion(1, "Ile wieków obejmuje tysiąclecie?", "łatwy",
                listOf("10" to true, "100" to false, "1000" to false, "1" to false))
            addQuestion(1, "Która z postaci jest postacią historyczną?", "łatwy",
                listOf("Mieszko I." to true, "Popiel" to false, "Lech" to false, "Król Artur." to false))
            addQuestion(1, "Prehistoria to:?", "łatwy",
                listOf("Czasy od pojawienia się na ziemi pierwszego człowieka aż do wynalezienia pisma" to true, "Wszystko, co zdarzyło się po narodzinach Jezusa." to false, "Czas istnienia starożytnego Rzymu." to false, "Okres w dziejach ziemi, gdy jeszcze nie było ludzi." to false))
            addQuestion(1, "Ile czasu upłynęło od roku 44 p.n.e. do 476 roku?", "łatwy",
                listOf("519 lat." to true, "520 lat." to false, "432 lata." to false, "476 lat." to false))
            addQuestion(1, "Rok 1410 to który wiek?", "łatwy",
                listOf("XV" to true, "XIV" to false, "X" to false, "XI" to false))
            addQuestion(1, "Zapisz liczbę 509 cyframi rzymskimi", "łatwy",
                listOf("MDIX" to true, "MDCXI" to false, "CLIX" to false, "IXI" to false))

            addQuestion(1, "Co to jest mała ojczyzna??", "normalny",
                listOf("To region, wieś lub miasto, gdzie człowiek się urodził albo mieszka" to true, "To małe państwo." to false, "To dom rodzinny." to false, "To malutki kraj" to false))
            addQuestion(1, "Co nazywamy Polonią?", "normalny",
                listOf("Osoby polskiego pochodzenia, które mieszkają na stałę poza Polską." to true, "Nazwa sklepu z zabytkami." to false, "Stara polska moneta." to false, "Nauka o Polakach." to false))
            addQuestion(1, "Wymień polskie symbole narodowe.", "normalny",
                listOf("Godło, flaga, hymn." to true, "Pieśń bojowa, okrzyk." to false, "Pieczęć i rysunek" to false, "Skrót nazwy państwa" to false))
            addQuestion(1, "Co to są źródła historyczne?", "normalny",
                listOf("To wszystko, co stworzyli w przeszłości ludzie" to true, "To historyczne rzeki" to false, "To żródła, z których czerpiemy wodę" to false, "To dawniej strumienie" to false))
            addQuestion(1, "Co to jest tradycja?", "normalny",
                listOf("Obyczaje i zasady przekazywane z pokolenia na pokolenie" to true, "Coś, co jest nowe" to false, "Materiał na obrusy" to false, "Rodzina" to false))
            addQuestion(1, "Co to jest drzewo genealogiczne?", "normalny",
                listOf("To schemat, który przedstawia stopień pokrewieństwa między osobami należącymi do jednego rodu." to true, "Jest to drzewo zabytkowe." to false, "To drzewo wykopane przez geologów." to false, "Jest to budowla w postaci drzewa" to false))
            addQuestion(1, "Kiedy rozpoczęłą się epoka historyczna współczesność?", "normalny",
                listOf("W 1914 r." to true, "W 476 r." to false, "W 1492 r." to false, "Około 4000 lat p.n.e." to false))
            addQuestion(1, "Jakie wydarzenie świętujemy w dniu 11 listopada?", "normalny",
                listOf("Świętujemy rocznicę odzyskania przez Polskę niepodległości w 1918 r." to true, "Świętujemy uchwalenie konstytucji w 1791 r." to false, "Świętujemy zwycięstwo Jana Sobieskiego w bitwie pod Chocimiem w 1673 r." to false, "Świętujemy podpisanie w 1359 r. polsko-litewskiego traktatu granicznego" to false))
            addQuestion(1, "W jakiej epoce nastąpił koniec I wojny światowej?", "normalny",
                listOf("We współczesności" to true, "W nowożytności" to false, "W średniowieczu" to false, "W starożytności" to false))
            addQuestion(1, "Co to takiego kartografia?", "normalny",
                listOf("To nauka o mapach, ich tworzeniu i metodach odwzorowania różnych obiektów, zjawisk na mapie" to true, "To nauka o historii Ziemi." to false, "To nauka o budowie Ziemi i jej minerałach" to false, "To nauka o ruchach wpjsk w poszczególnych epokach historycznych" to false))
            addQuestion(1, "Podaj różnice między mapą historyczną a geograficzną.", "normalny",
                listOf("Historyczna przedstawia wydarzenia, które były w przeszłości, bitwy a geograficzna pokazuje, jak jakiś teren wygląda w teraźniejszości." to true, "Historyczna przedstawia tylko plany bitew, geograficzna przedstawia teren tej bitwy." to false, "Historyczne, to mapy stare, wyprodukowane w innej erze, geograficzne to mapy wyprodukowane w XXI wieku." to false, "Historyczne to zrobione przez znanych ludzi, geograficzne to mają południki i równoleżniki." to false))
            addQuestion(1, "Co to jest chronologia?", "normalny",
                listOf("To nauka o mierzeniu czasu w historii. Zajmuje się też kolejnością zdarzeń w historii" to true, "Nauka o zegarach." to false, "Nauka o czasie." to false, "Nauka o Chronosie, bogu starożytnych Greków." to false))
            addQuestion(1, "Epoka historyczna to:", "normalny",
                listOf("Etap w dziejach, którego początkiem jest ważne wydarzenie i kończy się też innym ważnym wydarzeniem" to true, "To ważne wydarzenie od jego początku, aż do końca" to false, "To ważna bitwa, o której piszą historycy" to false, "To długotrwałe rządy jednego króla" to false))
            addQuestion(1, "Co wskazywała róża wiatrów?", "normalny",
                listOf("Strony świata." to true, "Kierunek marszu wojsk." to false, "Miejsce, gdzie jest bitwa." to false, "Miejsce, gdzie rosną róże." to false))

            addQuestion(1, "Święto Narodowe 3 Maja utworzono:", "trudny",
                listOf("Na cześć uchwalenia w dniu 3 maja 1791 r. pierwszej polskiej Konstytucji" to true, "Na cześć utwprzenie Irlandii Północnej w tym dniu w 1920 r." to false, "Na cześć otwarcia w tym dniu w 1939 r. polskiego pawolonu na Wystawie Światowej w Nowym Jorku" to false, "Na cześć robotników" to false))
            addQuestion(1, "Region, w którym leży stolica Polski to:", "trudny",
                listOf("Mazowsze" to true, "Warmia" to false, "Mazury" to false, "Wielkopolska" to false))
            addQuestion(1, "W jakim regionie leży Kraków?", "trudny",
                listOf("Małopolska" to true, "Górny Śląsk" to false, "Mazowsze" to false, "Wielkopolska" to false))
            addQuestion(1, "Czy dzieło literackie może być źródłem historycznym?", "trudny",
                listOf("Tak, bo może pokazywać tło epoki, to jak żyli ludzie, nawet jeśli zdarzenia w tym dziele są fikcyjne." to true, "Nie, bo autor dzieła mógł opisywać epokę, której nie znał." to false, "Nie, bo zdarzenia w tym dziele mogą być fikcyjne." to false, "Tak, bo autor zawsze opisuje wydarzenia, które się odbyły." to false))
            addQuestion(1, "czego możemy się dowiedzieć dzięki pracy archeologów?", "trudny",
                listOf("Możemy się dowiedzieć kiedy powstał wykopany przedmiot, do czego służył, jak żyli ludzie w tym czasie." to true, "Możemy się dowiedzieć jakie dinozaury kiedyś żyły." to false, "Możemy się dowiedzieć jak powstały paliwa kopalne." to false, "Możemy się dowiedzieć jak powstało życie na ziemi." to false))
            addQuestion(1, "Wymień dwie instytucje dbające o lokalną historię i kulturę.", "trudny",
                listOf("Muzea, domy kultury." to true, "Galerie handlowe, kluby." to false, "Parki rozrywki, sklepy." to false, "Urząd miejski, przedszkole." to false))
            addQuestion(1, "W jakim regionie znajduje się pałąc Branickich?", "trudny",
                listOf("Na Podlasiu." to true, "Na Mazowszu." to false, "Na Górnym Śląsku." to false, "W Małopolsce" to false))
            addQuestion(1, "Gdzie jest największe skupisko Polonii?", "trudny",
                listOf("W USA." to true, "W Niemczech." to false, "W Hiszpanii." to false, "W Brazylii." to false))
            addQuestion(1, "Dawna siedziba królów w Małopolsce to:", "trudny",
                listOf("Wawel." to true, "Zamek Królewski w Warszawie." to false, "Gdański Żuraw." to false, "Zamek Branickich w Białymstoku." to false))
            addQuestion(1, "Podaj dwa inne sposoby mierzenia czasu w dawnych wiekach.", "trudny",
                listOf("Za pomocą zegara słonecznego oraz klepsydry." to true, "Za pomocą zegarka bez baterii oraz metronomu." to false, "Za pomocą długości cienia oraz ilości kroków." to false, "Za pomocą zmian w parowaniu wody i wypalonej świecy." to false))
            addQuestion(1, "W którym roku upadło Cesarstwo Zachodniorzymskie?", "trudny",
                listOf("476 n.e." to true, "410 n.e." to false, "330 n.e." to false, "395 n.e." to false))

            addQuestion(1, "Kto jest autorem Mazurka Dąbrowskiego?", "ekspert",
                listOf("Józef Wybicki" to true, "Jan Henryk Dąbrowski" to false, "Stanisław August Poniatowski" to false, "Stanisław Leszczyński" to false))
            addQuestion(1, "Podaj genezę (skąd pochodzi) godła Polski", "ekspert",
                listOf("Orzeł był znakiem rodu Piastów, dlatego jest symbolem państwa polskiego." to true, "Orzeł to ptak drapieżny, dlatego jest symbolem państwa polskiego." to false, "Orzeł to znak wolności, dlatego jest symbolem państwa polskiego." to false, "Orzeł lata wysoko i jest groźny, dlatego jest symbolem państwa polskiego." to false))
            addQuestion(1, "Dlaczego flaga Polski jest biało-czerwona?", "ekspert",
                listOf("Biały uważany jest za kolor szlachetny i czysty, czerwony oznacza waleczność i odwagę." to true, "Biały oznacza poddanie się, gdy Polska utraciła niepodległość, a czerwony to krew przelana za odzyskanie wolności" to false, "Żeby widać ją było z daleka, bo to kontrastowe, rzucające się w oczy kolory." to false, "Tak postanowił król Władysław Łokietek" to false))
            addQuestion(1, "Co to emigracja?", "ekspert",
                listOf("To wyjazd ze swojego kraju, żeby osiedlić się i mieszkać w innym" to true, "To wyjazd do innego miasta." to false, "To zbiór ludzi z tego samego kraju." to false, "To budowanie więzi między osobami pochodzącymi z tego samego kraju." to false))
            addQuestion(1, "Dokończ zdanie: Jako rok pierwszy uznano ten rok, w którym...", "ekspert",
                listOf("urodził się Jezus Chrystus." to true, "człowiek nauczył się krzesać ogień." to false, "wyginęły dinozaury" to false, "powstali ludzie pierwotni" to false))
            addQuestion(1, "Jaka jest rola źródeł historycznych?", "ekspert",
                listOf("Pomagają poznać i zrozumieć historię." to true, "Niewielka, bo mogą zawierać informacje fałszywe." to false, "Nie odgrywają żadnej roli, mogą wprowadzać w błąd." to false, "Nie dostarczają nam żadnych ważnych informacji." to false))
            addQuestion(1, "Kogo nazywamy uchodźcą?", "ekspert",
                listOf("Osobę, która uciekła ze swojego kraju w obawie przed prześladowaniem, albo wojną." to true, "Osobę, która chce się wzbogacić." to false, "Osobę, która podróżuje przez liczne państwa," to false, "Osobę, która wyjechałą ze swojego kraju." to false))
            addQuestion(1, "Podaj dwie inne rachuby mierzenia czasu. ", "ekspert",
                listOf("Za pomcą faz księżyca czy zegarów wodnych." to true, "Za pomocą kroków oraz długości snu." to false, "Za pomocą wzrostu drzewa czy ruchu chmur." to false, "Za pomocą obserwacji psucia się żywności." to false))



            addQuestion(2, "Pierwszy historyczny władca Polski to", "łatwy",
                listOf("książę Mieszko I." to true, "Piast." to false, "Lech." to false, "Popiel" to false))
            addQuestion(2, "Dokończ: plemię to...", "normalny",
                listOf("kilka lub kilkanaście rodów spokrewnionych z sobą, mieszkających na określonym obszarze." to true, "grupa ludzi mówiąca jednym językiem." to false, "jedna rodzina" to false, "praludzie" to false))
            addQuestion(2, "Żona Mieszka I to...?", "łatwy",
                listOf("Dobrawa." to true, "Dobrosława." to false, "Dobromiła." to false, "Dobruchna." to false))
            addQuestion(2, "Słowianie to...", "łatwy",
                listOf("to lud, który od V wieku osiedlił się w Europie (oprócz jej zachodniej części)." to true, "to lud, od któego pochodzi Popiel." to false, "lud, który sławił Boga." to false, "ludzie, którzy mieszkali na Słowacji." to false))
            addQuestion(2, "W którym roku Mieszko I przyjął chrzest?", "łatwy",
                listOf("966." to true, "699." to false, "996." to false, "969." to false))
            addQuestion(2, "Podaj dwie przyczyny chrztu Polski.", "łatwy",
                listOf("Chęć wzmocnienia kraju w Europie, zwiększenie w Europie pozycji Mieszka I jako władcy." to true, "Naciski biskupów i prośba Dobrawy." to false, "Religijność Mieszka I i chęć przypodobania się żonie." to false, "Naciski papieża oraz słabnąca pozycja Mieszka I." to false))
            addQuestion(2, "Jaki cesarz przybył na Zjazd Gnieźnieński?", "łatwy",
                listOf("Otton III." to true, "Karol XII." to false, "Dobromiła." to false, "Wojciech Święty." to false))

            addQuestion(2, "Kto był pierwszym królem Polski?", "łatwy",
                listOf("Bolesław Chrobry." to true, "Mieszko I." to false, "Piast." to false, "Popiel." to false))
            addQuestion(2, "Podaj dwie przyczyny zjazdu w Gnieźnie.", "łatwy",
                listOf("Ottona III chciał zjednoczyć Europę oraz chciał oddać hołd Św. Wojciechowi." to true, "Otton III chciał podbić Polskę oraz zamierzał obalić Chrobrego." to false, "Otton III miał nakaz  papieża, żeby pojechać na zjazd oraz chciał poznać Chrobrego." to false, "Otton III miał taką pokutę oraz chciał złożyć hołd Chrobremu." to false))
            addQuestion(2, "Wymień jeden skutek zjazdu w Gnieźnie.", "łatwy",
                listOf("Zostało utworzone arcybiskupstwo." to true, "Polska stała się państwem poddanym Ottonowi III." to false, "Polska straciła na znaczeniu." to false, "Chrobry przestał być królem." to false))
            addQuestion(2, "Kto był ostatnim królem dynastii Piastów?", "łatwy",
                listOf("Kazimierz Wielki." to true, "Bolesław Chrobry." to false, "Mieszko I." to false, "Popiel." to false))
            addQuestion(2, "Rok koronacji Kazimierza Wielkiego to:", "łatwy",
                listOf("1333" to true, "1364" to false, "966" to false, "1000" to false))
            addQuestion(2, "Co to uniwersytet?", "łatwy",
                listOf("Uczelnia, która kształci różnych specjalistów." to true, "To szkoła pomaturalna." to false, "To uczelnia założóna przez zakonników." to false, "To ogólne kształcenie." to false))
            addQuestion(2, "Kim byli rycerze?", "łatwy",
                listOf("To wojownicy, którmy za dobrą służbę król nadał ziemie." to true, "To wyżsi rangą wojskowi." to false, "To ci, którzy rządzili w imieniu króla." to false, "To szlachetnie urodzeni bogaci ludzie." to false))
            addQuestion(2, "dwie cechy rycerza?", "łatwy",
                listOf("Honor i uczciwość." to true, "Zamożność i usłużność." to false, "Posłuszeństwo i wysoki wzrost." to false, "Bogactwo i przepych." to false))
            addQuestion(2, "Najsławniejszy polski astronom?", "łatwy",
                listOf("Mikołaj Kopernik." to true, "Mieszko I." to false, "Galileusz." to false, "Kazimierz Wielki." to false))
            addQuestion(2, "Elementy stroju rycerza to:?", "łatwy",
                listOf("Rękawice, kopia, hełm." to true, "Maska, białą chusta, nóż." to false, "Buty, broń palna, naboje." to false, "Kabura, maczuga, młot." to false))
            addQuestion(2, "Kto to jest astronom??", "łatwy",
                listOf("To naukowiec, który bada kosmos." to true, "To osoba zajmująca się wróżbami." to false, "To osoba, która przewiduje przyszłość." to false, "To to osoba badająca daty urodzenia." to false))
            addQuestion(2, "Wyjaśnij termin Jagiellonowie.", "łatwy",
                listOf("To dynastia zapoczątkowana przez Władysława." to true, "To ród wywodzący się z Polski." to false, "To zamożna rodzina pochodząca z Czech." to false, "To rodzina Dobrawy." to false))
            addQuestion(2, "Kim był Władysław JAgiełło?", "łatwy",
                listOf("Był królem Polski." to true, "Był mężem Dobrawy." to false, "Był synem Bolesława Chrobrego." to false, "Był królem Litwy." to false))

            addQuestion(2, "Od czego pochodzi nazwa Polski?", "normalny",
                listOf("Od plemienia Polan." to true, "Od polany, na której zbódowano Gniezno." to false, "Od pola, które uprawiał Popiel." to false, "Od tego, że ten lud polewał wodą pole." to false))
            addQuestion(2, "Piastowie to...", "normalny",
                listOf("dynastia, władcy Polan." to true, "potomkowie Popiela." to false, "starsi wiekiem doradcy księcia." to false, "plemię, żyjące na południu Polski." to false))
            addQuestion(2, "Legenda o początkach państwa polskiego to:", "normalny",
                listOf("Legenda o złym księciu Popiele." to true, "Legenda o polanie." to false, "Legenda o królu Mieszku." to false, "Legenda o dzielnym Piaście." to false))
            addQuestion(2, "Co to są relikwie?", "normalny",
                listOf("To szczątki zmarłych, których uznano za świętych." to true, "To szczątki królów." to false, "To religijne pieśni." to false, "To pozostałości z majątku króla." to false))
            addQuestion(2, "Podaj rok zjazdu gnieźnieńskiego.", "normalny",
                listOf("1000" to true, "966." to false, "1025" to false, "1044" to false))
            addQuestion(2, "Kiedy Bolesław Chrobry został koronowany?", "normalny",
                listOf("1025" to true, "1000" to false, "966" to false, "1044" to false))
            addQuestion(2, "Kim był św. Wojciech?", "normalny",
                listOf("Biskupem czeskim, który chciał nawrócić pogan." to true, "Apostołem." to false, "Religijnym chłopem." to false, "Biskupem polskim." to false))
            addQuestion(2, "Elementy uzbrojenia woja książęcego to:", "normalny",
                listOf("Włócznia, miecz i drewniana tarcza." to true, "Młot i tarcza z żelaza." to false, "Miecz, zbroja i maczuga." to false, "Maczuga, łuk i pancerz." to false))
            addQuestion(2, "Co to jest skryptorium?", "normalny",
                listOf("Miejsce, gdzie mnisi przepisywali księgi." to true, "Miejsce, gdzie mnisi się modlili." to false, "Miejsce, gdzie mnisi spali." to false, "Miejsce, gdzie mnisi śpiewali." to false))
            addQuestion(2, "Reguła zakonna to...?", "normalny",
                listOf("Przepisy, które obowiązywały w zakonie." to true, "To zasada, że nei można się odzywać." to false, "To zasada, że nie można opuszczać zakonu." to false, "To pomieszczenie, gdzie mnisi medytowali." to false))
            addQuestion(2, "Czym zajmowali się mnisi?", "normalny",
                listOf("Rolnictwem, robieniem lekarstw i przepisywaniem ksiąg." to true, "Szkololi się do udziału w bitwie." to false, "Budowali domy dla ludzi." to false, "Handlem" to false))
            addQuestion(2, "Co to jest dynastia?", "normalny",
                listOf("To ród panujący, w którym władza po śmierci ojca przechodzi na syna." to true, "To grupa ludzi skupiona wokół króla," to false, "To osoby posiadające to samo nazwisko," to false, "To urzędnicy królewscy." to false))
            addQuestion(2, "W którym roku założono Akademię Krakowską?", "normalny",
                listOf("1364" to true, "1333" to false, "1000" to false, "1345." to false))
            addQuestion(2, "Co zdzrzyło się w 1370 r.?", "normalny",
                listOf("Koronacja Ludwika Węgierskiego." to true, "Koronacja Bolesława Chrobrego." to false, "Uczta u Wierzynka." to false, "Śmierć Jadwigi." to false))
            addQuestion(2, "Kiedy Bolesław Chrobry został koronowany?", "normalny",
                listOf("1025" to true, "1000" to false, "966" to false, "1044" to false))
            addQuestion(2, "Dlaczego król Kazimierz nazwany został Wielkim?", "normalny",
                listOf("Zakładał dużo miast i wsi." to true, "Bo był bardzo wysoki." to false, "Bo był bardzo waleczny." to false, "Bo nie poddał się wrogom." to false))
            addQuestion(2, "Z jakiej okazji zorganizowano ucztę u Wierzynka?", "normalny",
                listOf("Z okazji zjazdu władców europejskich w Krakowie." to true, "Z okazji koronacji Bolesława Chrobrego." to false, "Z okazji urodzin Kazimierza Wielkiego." to false, "Z okazji śmierci Kazimierza Wielkiego." to false))
            addQuestion(2, "Kto to był paź?", "normalny",
                listOf("To pochodzący z rodu rycerskiego chłopiec, który był pomocnikiem u innego rycerza, aby stać się giermkiem, a potem rycerzem." to true, "To służący króla." to false, "To doradca króla." to false, "To główny rycerz w wojsku królewskim." to false))
            addQuestion(2, "Fosa, baszta, most zwodzony to elementy...?", "normalny",
                listOf("Sredniowiecznego zamku." to true, "Klasztoru." to false, "Zbrojowni." to false, "Budowli wiejskich w średniowieczu." to false))
            addQuestion(2, "Kto walczył w turniejach średniowiecznych?", "normalny",
                listOf("Rycerze" to true, "Giermkowie" to false, "Paziowie" to false, "Służba króla." to false))
            addQuestion(2, "Rok podpisania w Krewie unii polsko-litewskiej?", "normalny",
                listOf("1385." to true, "1000" to false, "966" to false, "1333" to false))
            addQuestion(2, "Wyjaśnij termin unia.", "normalny",
                listOf("To związek dwóch państw." to true, "To rozejm w bitwie." to false, "To poddanie się jednego państwa drugiemu." to false, "To przegrana w bitwie." to false))
            addQuestion(2, "Jakie były skutki unii w Krewie?", "normalny",
                listOf("Chrzest Litwy, ślub Jagiełły, walka Litwy i Polski z Krzyżakami." to true, "Spadek znaczenia Polski, poddanie się Jagiełły Litwie, zahamowanie handlu." to false, "Rozkwit rolnictwa, wzbogacenie się ludności, powiększenie terenów Polski." to false, "Upadek handlu, zubożenie chłopstwa, wojny graniczne." to false))
            addQuestion(2, "Co znaczy, że Kopernik wstrzymał Słońce, ruszył Ziemię?", "normalny",
                listOf("Udowodnił, że to Ziemia krąży wokół nieruchomego Słońca a nie na odwrót." to true, "Bo z jego obserwacji wynikało, że Słońce czasem się zatrzymuje." to false, "Bo jego odkrycie było tak ważne, że poruszyło wszystkich." to false, "Bo wydawało się, że Słońce na chwilę stanęło." to false))
            addQuestion(2, "Jakie wydarzenie miało miejsce w roku 1410?", "normalny",
                listOf("Bitwa pod Grunwaldem." to true, "Unia w Krewie." to false, "Chrzest Polski." to false, "Sprowadzenie Krzyżaków do Polski." to false))

            addQuestion(2, "Dlaczego Mieszko zdecydował się wziąć ślub z Dobrawą?", "trudny",
                listOf("Chciał wzmocnić pozycję Polski i swoją poprzez przyjęcie chrztu." to true, "Bo chciał mieć żonę z Czech. " to false, "Bo chciał w przyszłości być królem Czech." to false, "Bo przyjaźnił się z ojcem Dobrawy." to false))
            addQuestion(2, "Legendarni protoplaści Mieszka to:", "trudny",
                listOf("Siemowit, Lestek, Siemiomysł." to true, "Ziemowit, Leszek, Popiel." to false, "Leszek, Siemowit, Ziemiomysł." to false, "Lech, Ziemowit, Siemiomysł." to false))
                        addQuestion(2, "Wymień dwa skutki Chrztu Polski", "trudny",
                listOf("Rozwój kulturalny Polski i zawarcie sojuszu z Czechami." to true, "Biskupi byli zadowoleni a Mieszko stracił na swym znaczeniu." to false, "Mieszko zaatakował czeskie ziemie i Polska straciła na znaczeniu." to false, "Mieszko zerwał sojusz z Czechami a wszedł w sojusz z papieżem." to false))
            addQuestion(2, "Podaj rok bitwy pod Cedynią i z kim ona była.", "trudny",
                listOf("972 między wojskami Mieszka I i margrabiego Hodona." to true, "966 między wojskami Mieszka I i Ottona III." to false, "1025 między wojskami Chrobrego i Hodona." to false, "1000 między wojskami Mieszka I i Krzyżakami" to false))
            addQuestion(2, "Rok przybycia biskupa Wojciecha na dwór Chrobrego.", "trudny",
                listOf("997" to true, "966" to false, "972" to false, "1025" to false))
            addQuestion(2, "Pierwsza wojna polsko-niemiecka Bolesława Chrobrego była w latach:", "trudny",
                listOf("1002-1018" to true, "997-1000" to false, "966-972" to false, "1025-1026" to false))
            addQuestion(2, "Dwie przyczyny zjazdu gnieźnieńskiego.", "trudny",
                listOf("Otton chciał zjednoczyć Europę oraz złożyć hołd św. Wojciechowi." to true, "Otton chciał podporządkować sobie Polskę oraz zmusić Chrobrego do sojuszu." to false, "Chrobry chciał podporządkować sobie Ottona oraz zawrzeć z nim sojusz." to false, "Chrobry chciał ugody z Ottonem i zmusić go do hołdu." to false))
            addQuestion(2, "Jakie terytoria podbił Chrobry?", "trudny",
                listOf("Łużyce i Milsko oraz Grody Czerwieńskie." to true, "Ziemie Wieletów." to false, "Czechy." to false, "Ruś." to false))
            addQuestion(2, "Dwa najstarsze zakony w Polsce to:", "trudny",
                listOf("benedyktyni i cystersi." to true, "bernardyni i dominikanie." to false, "jezuici i salezjanie." to false, "nazaretki i pallotyni." to false))
            addQuestion(2, "Główne reformy Kazimierza Wielkiego", "trudny",
                listOf("Reforma budowlana - budowle miały być z cegły, wprowadzenie jednolitej waluty" to true, "Reforma podatków, reforma drogowa." to false, "Reforma wojskowa, reforma skarbu." to false, "Reforma czasu pracy i reforma policji." to false))
            addQuestion(2, "Kiedy odbył się zjazd monarchów w Krakowie?", "trudny",
                listOf("1364" to true, "997" to false, "1025" to false, "1026" to false))
            addQuestion(2, "Cele utworzenia Akademii Krakowskiej?", "trudny",
                listOf("Zwiększenie ilości ludzi wykształconych oraz umocnienie pozycji Królestwa Polskiego." to true, "Dotrzymanie obietnicy złożonej Ottonowi III" to false, "Wykształcenie wojskowych do prowadzenia wojen." to false, "Wykształcenie duchownych." to false))
            addQuestion(2, "Ziemie przyłączone do Polski za Kazimierza Wielkiego:", "trudny",
                listOf("Podole, Ruś Halicka, Mazowsze." to true, "Łużyce i Milsko." to false, "Grody Czerwieńskie." to false, "Ziemie Wieletów" to false))
            addQuestion(2, "Postanowienia unii w Krewie", "trudny",
                listOf("Jagiełło po ślubie z Jadwigą miał być królem Polski, wprowadzić chrześcijaństwo na Litwie i walczyć wraz z Litwinami z krzyżakami." to true, "Sproadzenie wojsk litewskich do Polski, zniesienie granicy z Litwą." to false, "Wprowadzenie na Litwie polskiej waluty, budowanie wspólnych okrętów" to false, "Wspólne przecowstawienie się Czechom." to false))
            addQuestion(2, "Kto był królem Polski po śmierci Ludwika Węgierskiego?", "trudny",
                listOf("Jadwiga Andegaweńska." to true, "Kazimierz Jagiellończyk." to false, "Wielki książę litewski Jagiełło." to false, "Konrad Mazowiecki." to false))
            addQuestion(2, "Gdzie urodził się Kopernik?", "trudny",
                listOf("W Toruniu." to true, "W Olsztynie." to false, "W Krakowie." to false, "W Warszawie." to false))
            addQuestion(2, "Gdzie jest grobowiec Kopernika?", "trudny",
                listOf("We Fromorku." to true, "Na Wawelu" to false, "W Katedrze w Toruniu." to false, "W Warszawie." to false))


            addQuestion(2, "Co to jest Dagome iudex?", "ekspert",
                listOf("Dokument, w którym Mieszko I poddaje Polskę opiece papiestwa" to true, "Spisany zbiór praw za Mieszka I." to false, "To testament Mieszka I." to false, "To nazwa kroniki z X wieku." to false))
            addQuestion(2, "Gdzie znajdują się najstarsze budowle zakonne w Polsce?", "ekspert",
                listOf("W Tyńcu, w Brzeźnicy." to true, "Na Wawelu, w Henrykowie." to false, "W Wadowicach, w Katowicach" to false, "W Lubiążu, w Przemyślu." to false))
            addQuestion(2, "Przykład wzorca rycerskiego w literaturze to:", "ekspert",
                listOf("Roland" to true, "Popiel" to false, "Sancho Pansa" to false, "Św. Franciszek." to false))
            addQuestion(2, "Podaj przykład zamku średniowiecznego w Polsce.", "ekspert",
                listOf("Zamek w Bobolicach" to true, "Zamek Królewski w Warszawie." to false, "Zamek Książ w Wałbrzychu." to false, "Zamek w Malborku." to false))
            addQuestion(2, "Heraldyka zajmuje się badaniem..?", "ekspert",
                listOf("Herbów." to true, "Zachowanych zbiorów." to false, "Rękopisów." to false, "Hieroglifów." to false))
            addQuestion(2, "Dwa skutki bitwy pod Grunwaldem to:", "ekspert",
                listOf("Polska odzyskałą ziemię dobrzyńską, Litwa utrzymała Żmudź." to true, "Zdobycie Pomorza przez Polskę, zdobycie Podola przez Litwę." to false, "Polska popadła w ruinę, Litwa też." to false, "Polska zyskała drogę handlową, Litwa się wzbogaciła." to false))
            addQuestion(2, "Jak Kościół odniósł się do dzieła Kopernika?", "ekspert",
                listOf("Umieścił je na indeksie ksiąg zakazanych." to true, "Papież pochwalił Kopernika." to false, "Za to dzieło papież mianował Kopernika biskupem." to false, "Nie zareagował wogóle." to false))
            addQuestion(2, "Postanowienia pokoju w Toruniu:", "ekspert",
                listOf("Polska odzyskała ziemię dobrzyńską, Litwa Żmudź." to true, "Polska zyskała Pomorze." to false, "Litwa zyskałą ziemię dobrzyńską" to false, "Polska zyskała Pomorze, Litwa Żmudź." to false))


            addQuestion(3, "Szlachta to:", "łatwy",
                listOf("Ludność wywodząca się z rycerstwa w dawnej Polsce." to true, "Doradcy króla." to false, "Osoby spokrewnione z królem." to false, "Najniższa warstwa społeczna w dawnej Polsce." to false))
            addQuestion(3, "Epoka, w której żył Jan Zamojski to?", "łatwy",
                listOf("Nnowożytność." to true, "Średniowiecze." to false, "Starożytność." to false, "Współczesność." to false))
            addQuestion(3, "Miasto założone przez Zamojskiego to...?", "łatwy",
                listOf("Zamość." to true, "Lublin." to false, "Poznań" to false, "Zamojsków" to false))
            addQuestion(3, "Potop szwedzki to...?", "łatwy",
                listOf("najgroźniejszy najazd Szwedów na Polskę." to true, "zalanie przez Bałtyk wiosek w Szwecji." to false, "przyjazd kupów szwedzkich" to false, "bitwa morska" to false))
            addQuestion(3, "Rok koronacji Stanisława Augusta Poniatowskiego?", "łatwy",
                listOf("1764" to true, "1773" to false, "1791" to false, "1683" to false))
            addQuestion(3, "Reformy to...", "łatwy",
                listOf("zmiany, by usprawnić funkcjonowanie państwa. " to true, "Zbiór praw i obowiązków" to false, "Zebrania króla i poddanych" to false, "próby przekupstwa." to false))
            addQuestion(3, "Rok utworzenia Komisji Edukacji Narodowej?", "łatwy",
                listOf("1773" to true, "1764" to false, "1791" to false, "1683" to false))
            addQuestion(3, "Rok Powstania kościuszkowskiego?", "łatwy",
                listOf("1764" to true, "1773" to false, "1791" to false, "1683" to false))
            addQuestion(3, "Rozbiory to...?", "łatwy",
                listOf("Podział i zagarnięcie terytorium państwa bez użycia siły przez inne państwa." to true, "Zagarnięcie części państwa w wyniku wojny." to false, "Wojna kilku państw." to false, "wojna podjazdowa." to false))
            addQuestion(3, "Powstanie to:", "łatwy",
                listOf("Zbrojne wystąpienie ludności przeciw władzy lub obcym wojskom." to true, "wystąpienie przeciwko ludowi." to false, "dokument koronacyjny." to false, "List z propozycjami zawarcia pokoju." to false))

            addQuestion(3, "Hetman to...?", "normalny",
                listOf("najwyższy dowódca wojskowy." to true, "władca" to false, "zwykły żołnierz." to false, "skarbnik królewski." to false))
            addQuestion(3, "Kanclerz to...?", "normalny",
                listOf("najważniejszy urząd w dawnej Polsce." to true, "kronikarz." to false, "dowódca wojsk." to false, "duchowny." to false))
            addQuestion(3, "Akademia to...?", "normalny",
                listOf("uczelnia wyższa." to true, "uroczystość z okazji zwycięstwa." to false, "szkoła wojskowa." to false, "rodzaj teatru." to false))
            addQuestion(3, "Kto to wielki wezyr?", "normalny",
                listOf("naczelny wódz wojsk tureckich." to true, "turecki służący." to false, "żołnierz wojsk tureckich" to false, "niewola turecka" to false))
            addQuestion(3, "Kto założył Szkołę Rycerską?", "normalny",
                listOf("Król Stanisław August Poniatowski." to true, "Stanisław Konarski." to false, "Julian Ursyn Niemcewicz." to false, "Ignacy Krasicki." to false))
            addQuestion(3, "Kadet to...?", "normalny",
                listOf("uczeń Szkoły Rycerskiej." to true, "wojskowy najniższej rangi." to false, "kapłan." to false, "kandydat na króla." to false))
            addQuestion(3, "Cykliczne spotkania organizowane przez króla Poniatowskiego nosiły nazwę:", "normalny",
                listOf("obiady czwartkowe." to true, "kolacje przyjaciół." to false, "królewskie obiady." to false, "spotkania przy muzyce." to false))
            addQuestion(3, "Konstytucja to...?", "normalny",
                listOf("najważniejszy w państwie akt prawny, zbiór zasad i praw." to true, "zarządzenie króla." to false, "dokument zrzeczenia się władzy." to false, "Zebranie szlachty, aby wybrać króla." to false))


            addQuestion(3, "Stefan Batory to...?", "trudny",
                listOf("król Polski." to true, "dowódca w bitwie pod Grunwaldem." to false, "arcybiskup" to false, "kanclerz króla." to false))
            addQuestion(3, "Zygmunt II August to...?", "trudny",
                listOf("ostatni król Polski z rodu Jagiellonów" to true, "syn Stefana Batorego." to false, "dowódca wojskowy" to false, "twórca Wawelu." to false))
            addQuestion(3, "Kordecki to...?", "trudny",
                listOf("490 p.n.e." to true, "przeor klasztoru na Jasnej Górze" to false, "dowódca wojsk polskich" to false, "polski wysłannik do króla Szwecji." to false))
            addQuestion(3, "Gdzie leży Chocim?", "trudny",
                listOf("Koło Kamieńca Podolskiego." to true, "Koło Lublina." to false, "Koło Krakowa." to false, "Na zachodzie Polski." to false))
            addQuestion(3, "Data ogłoszenia pierwszej polskiej Konstytucji:", "trudny",
                listOf("3 maja 1791" to true, "4 kwietnia 1773" to false, "1 maja 1794" to false, "3 maja 1683" to false))
            addQuestion(3, "Rok koronacji Stanisława Augusta Poniatowskiego?", "trudny",
                listOf("1764" to true, "1773" to false, "1791" to false, "1683" to false))
            addQuestion(3, "W jakiej bitwie armia Kościuszki pokonała Rosjan?", "trudny",
                listOf("Pod Racławicami" to true, "Pod Maciejowicami" to false, "Pod Krakowem" to false, "Pod Warszawą" to false))
            addQuestion(3, "Dotkliwą klęskę ponieśli powstańcy kościuszkowscy w bitwie pod:", "trudny",
                listOf("Maciejowicami" to true, "Racławicami" to false, "Pod Krakowem" to false, "Pod Warszawą" to false))
            addQuestion(3, "Rok powstania kościuszkowskiego?", "trudny",
                listOf("1794" to true, "1795" to false, "1791" to false, "1797" to false))

            addQuestion(3, "Monarchia elekcyjna to ustrój, w którym...?", "ekspert",
                listOf("król jest wybierany" to true, "król rządzi wraz z ojcem." to false, "król pełni rolę tylko reprezentacyjną." to false, "rządzą możnowładcy." to false))
            addQuestion(3, "Monarchia dynastyczna to ustrój, w którym...?", "ekspert",
                listOf("król obejmuje władzę przez dziedziczenie.." to true, "królem jest kobieta." to false, "króla wybiera się." to false, "rządzi szlachta." to false))
            addQuestion(3, "Skutki dla Polski wojen prowadzonych w XVII w.??", "ekspert",
                listOf("Utrata części ziem, zrujnowanie wielu miast." to true, "Odzyskanie niektóych ziem, wzogacenie państwa ." to false, "Wzrost potęgi militarnej i znaczenia." to false, "Rozkwit handlu i rolnictwa." to false))
            addQuestion(3, "Rok zawarcia pokoju w Oliwie?", "ekspert",
                listOf("1660" to true, "1655" to false, "1683" to false, "1580" to false))
            addQuestion(3, "Kim był Stefan Czarniecki?", "ekspert",
                listOf("Hetmanem, jednym z najwybitniejszych wodzów." to true, "Ojcem Batorego." to false, "Kandydatem na króla Polski." to false, "Przeorem Jasnej Góry.." to false))

            addQuestion(4, "Z jakiej okazji obchodzimy święto w dniu 11 listopada?", "łatwy",
                listOf("Odzyskania niepodległości." to true, "Śmierci Piłsudskiego." to false, "Bitwy Warszawskiej" to false, "Bitwy pod Racławicami" to false))
            addQuestion(4, "II Rzeczpospolita to...?", "łatwy",
                listOf("Odrodzone w 1918 r. państwo polskie." to true, "Aktualna nazwa państwa polskiego." to false, "Polska pod II zaborem." to false, "Polska po I zaborze." to false))
            addQuestion(4, "Czy Wrocław był w granicach II Rzeczpospolitej?", "łatwy",
                listOf("Nie." to true, "Tak." to false, "Był, ale tylko w połowie." to false, "Tylko okolice Wrocławia należały do Polski." to false))
            addQuestion(4, "Z jakiej okazji obchodzimy święto w dniu 11 listopada?", "łatwy",
                listOf("Odzyskania niepodległości." to true, "Śmierci Piłsudskiego." to false, "Bitwy Warszawskiej" to false, "Bitwy pod Racławicami" to false))
            addQuestion(4, "Wybuch II wojny światowej...?", "łatwy",
                listOf("1 września 1939 r." to true, "1 sierpnia 1944 r." to false, "1 sierpnia 1980 r.." to false, "1 września 1943 r.." to false))
            addQuestion(4, "W którym roku zakończyła się II wojna światowa?", "łatwy",
                listOf("1945." to true, "1939." to false, "1943." to false, "1944." to false))
            addQuestion(4, "Pierwszym przywódcą Związku Zawodowego Solidarność był...?", "łatwy",
                listOf("Lech Wałęsa." to true, "Wojciech Jaruzelski." to false, "Witold Pilecki." to false, "Władysław Frasyniuk." to false))
            addQuestion(4, "Co t jest strajk?", "łatwy",
                listOf("To protest pracowników, w którym odmawiają pracy." to true, "To walka z okupantem." to false, "To porozumienie z pracodawcą." to false, "To podwyżka cen." to false))

            addQuestion(4, "Kiedy zakończyła się I wojna światowa?", "normalny",
                listOf("11 listopada 1918 r.." to true, "11 listopada 1914 r." to false, "11 listopada 1935 r." to false, "11 listopada 1920 r." to false))
            addQuestion(4, "Naczelnik państwa to...??", "normalny",
                listOf("Najwyższy urząd w Polsce w latach 1918-1922." to true, "Naczelny dowódca wojsk powstańczych." to false, "Główny dowódca Legionów Polskich. " to false, "Naczelnik więzienia." to false))
            addQuestion(4, "Z jakiej okazji obchodzimy święto w dniu 11 listopada?", "normalny",
                listOf("Odzyskania niepodległości." to true, "Śmierci Piłsudskiego." to false, "Bitwy Warszawskiej" to false, "Bitwy pod Racławicami" to false))
            addQuestion(4, "aką funkcję pełnił Józef Piłsudski?", "normalny",
                listOf("Naczelnika państwa." to true, "Marszałka Senatu." to false, "Marszałka Sejmu." to false, "Premiera" to false))
            addQuestion(4, "Z jakiej okazji obchodzimy święto w dniu 11 listopada?", "normalny",
                listOf("Odzyskania niepodległości." to true, "Śmierci Piłsudskiego." to false, "Bitwy Warszawskiej" to false, "Bitwy pod Racławicami" to false))
            addQuestion(4, "Dokończ: 1 sierpnia 1944 r...", "normalny",
                listOf("Wybuchło Powstanie Warszawskie." to true, "Rozpoczęłą się II wojna światowa." to false, "Podpisano zkończenie wojny." to false, "Rozpoczęła się II wojna światowa." to false))
            addQuestion(4, "Dokończ: Lech Wałęsa..?", "normalny",
                listOf("stanął na czele strajku stoczniowców." to true, "Ogłosił stan wojenny w Polsce." to false, "Wstrzymał tramwaj podczas strajku." to false, "Rozwiązał rząd." to false))

            addQuestion(4, "Treblinka to?", "normalny",
                listOf("hitlerowski obóz zagłady." to true, "miejsce, gdzie Niemcy produkowali pociski V-1." to false, "miejscowość, gdzie podpisano zkończenie wojny." to false, "miejscowość zlikwidowana przez Niemców." to false))
            addQuestion(4, "Kogo wspierały Francja i Wielka Brytania podczas I wojny światowej?", "trudny",
                listOf("Niemcy." to true, "Rosję." to false, "Austro-Węgry." to false, "Włochy." to false))
            addQuestion(4, "Przeciw jakiemu państwu walczyły Legiony?", "trudny",
                listOf("Rosji." to true, "Niemcom." to false, "Austro-Węgrom." to false, "Włochom" to false))
            addQuestion(4, "Co stało się w Rosji po zakończeniu I wojny światowej?", "trudny",
                listOf("W Rosji wybuchła wojna domowa." to true, "Rosja, jako zwycięzca zaczęłą się rozwijać." to false, "Rosja rozpoczęła wojnę z Japonią." to false, "Rosja nie miała pieniędzy i musiała się zadłużyć." to false))
            addQuestion(4, "W jakim roku była Bitwa Warszawska??", "normalny",
                listOf("1920." to true, "1919." to false, "1921" to false, "1922" to false))
            addQuestion(4, "Co to jest cud nad Wisłą??", "trudny",
                listOf("Bitwa Warszawska." to true, "Bitwa pod Racławicami." to false, "Obrona Jasnej Góry." to false, "Bitwa pod Oliwą." to false))
            addQuestion(4, "Z jakiej okazji obchodzimy święto w dniu 11 listopada?", "trudny",
                listOf("Odzyskania niepodległości." to true, "Śmierci Piłsudskiego." to false, "Bitwy Warszawskiej" to false, "Bitwy pod Racławicami" to false))
            addQuestion(4, "Solidarność to...?", "trudny",
                listOf("Związek zawodowy." to true, "Partia polityczna." to false, "Partia rządząca Polską od 1980 r.." to false, "Partia robotników." to false))
            addQuestion(4, "Akcja Szarych Szeregów?", "trudny",
                listOf("Akcja pod Arsenałem." to true, "Wyzwolenie obozu w Oświęcimiu." to false, "Wzniecenie Powstania Warszawskiego." to false, "Wysadzenie siedziby Gestapo." to false))
            addQuestion(4, "W którym roku bolszewicy w Rosji przejęli władzę?", "trudny",
                listOf("1917." to true, "1920." to false, "1914" to false, "1921" to false))
            addQuestion(4, "Kto stanął na czele strajkujących w stoczni gdańskiej...?", "trudny",
                listOf("Lech Wałęsa." to true, "Anna Walentynowicz." to false, "Wojciech Jaruzelski." to false, "Witold Pilecki." to false))

            addQuestion(4, "Jakie były trudności polityczne w odbudowie Polski? ", "trudny",
                listOf("Brak silnej władzy, napięcia społeczne." to true, "Silna władza, która nie słuchała ludu." to false, "Brak pomysłu na odbudowę, bunt rolników." to false, "Nie było żadnych trudności." to false))
            addQuestion(4, "Czy zwycięska Bitwa Warszawska ocaliła tylko Polskę ", "ekspert",
                listOf("Polskę i inne kraje Europy, w których bolszewicy mogliby wprowadzić komunizm." to true, "Polskę i Rosję." to false, "Tak, tylko Polskę." to false, "Polskę i Węgry." to false))
            addQuestion(4, "Dlaczego 15 sierpnia obchodzony jest Dzień Wojska Polskiego?", "ekspert",
                listOf("Bo 15 sierpnia 1920 r. był atak polskiego wojska, które odepchnęło Rosjan od Warszawy." to true, "Na pamiątkę bitwy pod Racławicami." to false, "Bo 15 sierpnia podpisano pokój po Bitwie Warszawskiej." to false, "Na pamiątkę odzyskania niepodległości." to false))
            addQuestion(4, "Najważniejsze ośrodki przemysłowe współczesnej Polski to...?", "ekspert",
                listOf("Katowice, Kraków, Zabrze." to true, "Kielce, Olecko, Skawina.." to false, "Nakło, Kalisz, Leszno." to false, "Biłgoraj, Kielce, Mrągowo." to false))
            addQuestion(4, "Harcerstwo działające z Armią Krajową nosiło nazwę...??", "ekspert",
                listOf("Szare Szeregi." to true, "Szary Parasol." to false, "Polska Walcząca." to false, "Konspiracyjna Armia." to false))
            addQuestion(4, "Co to Polskie Państwo Podziemne?", "ekspert",
                listOf("tajne struktury państwa polskiego, które w konspiracji działały na ziemiach okupowanych przez Niemcy." to true, "To rząd na uchodźctwie." to false, "To rząd polski, który wyemigrował." to false, "To Armia Krajowa." to false))
            addQuestion(4, "Data wybuchu Powstania Warszawskiego?", "ekspert",
                listOf("1 sierpnia 1944." to true, "1 września 1939." to false, "1 sierpnia 1943." to false, "9 maja 1945." to false))
            addQuestion(4, "Suwerenność to...?", "ekspert",
                listOf("niezależność w sprawach wewnętrznych i zewnętrznych oraz swoboda decydowania o państwie." to true, "zależność od innego państwa." to false, "walka z wrogiem." to false, "ucieczka z pola walki." to false))
            addQuestion(4, "Żelazna kurtyna to...?", "ekspert",
                listOf("potoczna nazwa granicy pomiędzy blokiem wschodnim a blokiem zachodnim." to true, "To granica między Polską a Związkiem Radzieckim po II wojnie światowej." to false, "granica między Polską a Czechami." to false, "granica między Polską a Niemcami." to false))
            addQuestion(4, "Auschwitz II-Birkenau to...?", "ekspert",
                listOf("hitlerowski obóz zagłady." to true, "miejsce, gdzie Niemcy produkowali pociski V-1." to false, "miejscowość, gdzie podpisano zkończenie wojny." to false, "miejscowość zlikwidowana przez Niemców." to false))
            addQuestion(4, "Podaj rok obrad Okrągłego Stołu.", "ekspert",
                listOf("1989." to true, "1980." to false, "1990." to false, "1956." to false))
            addQuestion(4, "Data wprowadzenia stanu wojennego?", "ekspert",
                listOf("13 grudnia 1981." to true, "15 sierpnia 1980." to false, "1 czerwca 1990." to false, "1 sierpnia 1989." to false))


            addQuestion(5, "Gdzie żyli praludzie około 4 milionów lat temu?", "łatwy",
                listOf("W Afryce" to true, "W Australii" to false, "W Europie" to false, "W Azji" to false))
            addQuestion(5, "Jakiego rodzaju domy budowali praludzie?", "łatwy",
                listOf("Szałasy z gałęzi" to true, "Domy z gliny" to false, "Domy ze słomy." to false, "Domy z pali." to false))
            addQuestion(5, "Gdzie powstawały pierwsze osady koczowników?", "łatwy",
                listOf("W pobliżu jezior i rzek" to true, "Na polanach" to false, "Daleko od rzek" to false, "W lasach" to false))
            addQuestion(5, "Z czym związany jest początek neolitu?", "łatwy",
                listOf("Z hodowlą zwierząt" to true, "Z wytopem żelaza" to false, "Z polowaniem" to false, "Z wytopem brązu" to false))
            addQuestion(5, "Kiedy pojawił się człowiek rozumny?", "łatwy",
                listOf("Około 200 tysięcy lat temu" to true, "Około 500 tysięcy lat temu" to false, "20 tysięcy lat temu" to false, "5 tysięcy lat temu" to false))
            addQuestion(5, "Kiedy były pczątki rolnictwa?", "łatwy",
                listOf("Około 10 tysięcy lat p.n.e." to true, "Około 500 tysięcy lat temu" to false, "20 tysięcy lat temu" to false, "5 tysięcy lat temu" to false))
            addQuestion(5, "Kiedy był początek epoki żelaza?", "łatwy",
                listOf("Około 1200 lat p.n.e." to true, "Około 500 tysięcy lat p.n.e" to false, "Około 2000 lat temu" to false, "Około 200 lat temu" to false))
            addQuestion(5, "Przodkowie dzisiejszego człowieka nazywają się:", "łatwy",
                listOf("Praludzie" to true, "Pradziadkowe" to false, "Potomkowie" to false, "Prawnuki" to false))
            addQuestion(5, "Podaj nazwę wędrownego trybu życia.", "łatwy",
                listOf("Koczowniczy" to true, "Przechodzący" to false, "Wędrowny" to false, "Spacerujący" to false))
            addQuestion(5, "Które słowa określają koczowniczy tryb życia?", "łatwy",
                listOf("Jaskinie i zbieractwo" to true, "Wytop żelaza i domy" to false, "Hodowla i rolnictwo" to false, "Uprawa zbóż i wędrówka" to false))
            addQuestion(5, "W jakim okresie w dziejach do wyrobu narzędzi używano kamieni?", "łatwy",
                listOf("Epoka kamienna" to true, "Epoka skórzana" to false, "Epoka stali" to false, "Epoka brązu" to false))
            addQuestion(5, "W jakiej epoce do wyrobu narzędzi zaczęto używać zelaza?", "łatwy",
                listOf("Epoka żelaza" to true, "Epoka narzędzi z żelaza" to false, "Epoka początkowa" to false, "Epoka zaawansowana" to false))
            addQuestion(5, "W jakiej epoce do wyrobu narzędzi zaczęto używać brązu?", "łatwy",
                listOf("Epoka brązu" to true, "Epoka kowalstwa" to false, "Epoka wyrobów rzemieślniczych" to false, "Epoka rozwinięta" to false))
            addQuestion(5, "Gdzie leżała Mezopotamia?", "łatwy",
                listOf("Na Bliskim Wshodzie" to true, "W Afryce" to false, "W Europie" to false, "Na Antarktydzie" to false))
            addQuestion(5, "Co to jest miasto-państwo?", "normalny",
                listOf("Niewielkie państwo składające się z miasta i jego okolicy" to true, "Duże miasto" to false, "Miasto posiadające granice" to false, "Państewko" to false))
            addQuestion(5, "Wyjaśnij termin POLIS", "łatwy",
                listOf("To samodzielne państwo-miasto" to true, "Wiara w wielu bogów" to false, "To miasto Sumerów" to false, "To świątynia Greków" to false))
            addQuestion(5, "Jak nazywa się kilkupiętrowa sumeryjska świątynia?", "łatwy",
                listOf("Zikkurat" to true, "Partenon" to false, "Akropol" to false, "Pakkurat" to false))
            addQuestion(5, "Nad jakimi rzekami powstała Babilonia?", "łatwy",
                listOf("Nad Trygrysem i Eufratem" to true, "Nad Tamizą i Olzą" to false, "Nad Gangesem i Brahmaputrą" to false, "Nad Obem i Jenisiejem" to false))
            addQuestion(5, "Kiedy powstało państwo Egipskie?", "łatwy",
                listOf("Około 3000 lat p.n.e." to true, "Około 20000 lat p.n.e." to false, "Około 2000 lat temu" to false, "Około 200 lat temu" to false))
            addQuestion(5, "Co to jest politeizm?", "normalny",
                listOf("Wiara w wielu bogów" to true, "Wiara w jednego boga" to false, "Małżeństwo faraonów" to false, "Mieszanina narodów" to false))


            addQuestion(5, "Kiedy pojawił się Australopitek?", "normalny",
                listOf("Około 4 mln lat temu." to true, "Około 300 tys. lat temu." to false, "1000 lat temu." to false, "3000 lat temu" to false))
            addQuestion(5, "Co to jest rewolucja neolityczna?", "normalny",
                listOf("Proces przejścia człowieka z koczowniczego na osiadły tryb życia" to true, "Rewolucja w wytopie żelaza" to false, "Rewolucja społeczna" to false, "Zmiana granic" to false))
            addQuestion(5, "Kto stworzył pierwszą cywilizację na terenie Mezopotamii?", "normalny",
                listOf("Sumerowie" to true, "Arabowie" to false, "Babilończycy" to false, "Izraelici" to false))
            addQuestion(5, "Jaki lud wymyślił   żagiel?", "normalny",
                listOf("Sumerowie" to true, "Polacy" to false, "Arabowe" to false, "Chińczycy" to false))
            addQuestion(5, "Kto wymyślił koło?", "normalny",
                listOf("Sumerowie" to true, "Grecy" to false, "Ateńczycy" to false, "Babilończycy" to false))
            addQuestion(5, "umeryjskie miasto-państwo:", "normalny",
                listOf("Ur" to true, "Ateny" to false, "Sparta" to false, "Gur" to false))
            addQuestion(5, "Jak nazywa się sumeryjskie pismo?", "normalny",
                listOf("Klinowe" to true, "Piktogramy" to false, "Pismo obrazkowe" to false, "Hieroglify" to false))
            addQuestion(5, "W dolinie jakiej rzeki powstało państwo egipskie?", "normalny",
                listOf("Nilu" to true, "Gangesu" to false, "Brahmaputry" to false, "Irtysza" to false))
            addQuestion(5, "Kto był najważniejszą osobą w starożytnym Egipcie?", "normalny",
                listOf("Faraon" to true, "Kapłan" to false, "Matka faraona" to false, "Król" to false))
            addQuestion(5, "Jak nazywał się bóg Słońce w starożytnym Egipcie?", "normalny",
                listOf("Ra" to true, "Wisznu" to false, "Sziwa" to false, "Budda" to false))
            addQuestion(5, "Piramidy to:", "normalny",
                listOf("Grobowce faraonów" to true, "Zwoje pism" to false, "Pomocnice faraona" to false, "Staregipskie rzeźby" to false))
            addQuestion(5, "Jak się nazywa pismo obrazkowe opracowane przez Egipcjan?", "normalny",
                listOf("Hieroglify" to true, "Papirusy" to false, "Pismo klinowe" to false, "Piktogramy" to false))
            addQuestion(5, "Co zawdzięczamy starożytnym Egipcjanom?", "normalny",
                listOf("Kalendarz z podziałem roku na 365 dni" to true, "Pismo klinowe" to false, "Proch strzelniczy" to false, "Zikkurat" to false))
            addQuestion(5, "Najstarszy babiloński zbiór praw to:", "normalny",
                listOf("Kodeks Hammurabiego" to true, "Księga tysiąclecia" to false, "Prawa obywatelskie" to false, "Tablice prawne" to false))
            addQuestion(5, "Kto podbił sumeryjskie miasta-państwa?", "normalny",
                listOf("Hammurabi" to true, "Maharadża" to false, "Sumerowie" to false, "Starożytni Grecy" to false))
            addQuestion(5, "Kim byli doradcy faraona?", "normalny",
                listOf("Kapłanami" to true, "Sekretnymi sługami" to false, "Starożytnymi Grekami" to false, "Bogatymi rzemieślnikami" to false))
            addQuestion(5, "Kto opracował system liczenia liczbą 60 jako podstawą?", "normalny",
                listOf("Sumerowie" to true, "Babilończycy" to false, "Spartanie" to false, "Grecy" to false))

            addQuestion(5, "Na którym kontynencie praludzie pojawili się najwcześniej?", "trudny",
                listOf("W Afryce" to true, "W Australii" to false, "W Azji" to false, "W Europie" to false))
            addQuestion(5, "Jak nazywały się dwa kamienie do rozcierania ziarna?", "trudny",
                listOf("Żarna" to true, "Cepy" to false, "Rozcieralnice" to false, "Kamieniarze" to false))
            addQuestion(5, "Co to są dymarki?", "trudny",
                listOf("Piece do wytopu żelaza" to true, "Kominy na chatach pierwotnych ludzi" to false, "Specjalne ogniska pierwotnych ludzi" to false, "Pradawne obrzędy" to false))
            addQuestion(5, "Wymień dwa miasta-państwa Mezopotamii", "trudny",
                listOf("Sumer i Babilonia" to true, "Egipt i Izrael" to false, "Indie i Chiny" to false, "Sumer i Indus" to false))
            addQuestion(5, "Jedna z głównych bram Babilonu to:", "trudny",
                listOf("Isztar" to true, "Brama Hammurabiego" to false, "Brama Główna" to false, "Brama Babilońska" to false))
            addQuestion(5, "Święta księga judaizmu to:", "trudny",
                listOf("Tora" to true, "Menora" to false, "Szabas" to false, "Dekalog" to false))
            addQuestion(5, "Wymień dwa miasta leżące w państwie Salomona", "trudny",
                listOf("Jerycho i Damaszek" to true, "Kadesz i Akka" to false, "Jerozolima i Gaza" to false, "Tyr i Akka" to false))
            addQuestion(5, "Co wspólnego mieli Ariowie z Indiami?", "trudny",
                listOf("Ariowie podbili Indie" to true, "Ariowie założyli Indie" to false, "Zostali sprowadzeni do Indii prze kapłanów" to false, "To najniższa kasta w Indiach" to false))
            addQuestion(5, "Podaj nazwy dwóch bogów hinduizmu", "trudny",
                listOf("Brahma i Wisznu" to true, "Budda i Hamisz" to false, "Mahomet i Szyita" to false, "Mohawe i Putra" to false))
            addQuestion(5, "Kiedy ziemie chińskie zostały zjednoczone?", "trudny",
                listOf("W III w p.n.e." to true, "W V wieku" to false, "W VII w p.n.e." to false, "W X w p.n.e" to false))
            addQuestion(5, "Kamień z Rosetty to;", "trudny",
                listOf("Płyta z czarnego granitu z wyryym napisem na trzy sposoby" to true, "Kamień milowy przy starożytnym szlaku" to false, "Kamień nagrobny cesarza" to false, "Kamień na górze piramidy" to false))
            addQuestion(5, "Z jakego okresu pochodzi Kamień z Rosetty?", "trudny",
                listOf("Z II w p.n.e." to true, "Z II wieku" to false, "Z VII wieku" to false, "Z IX wieku" to false))
            addQuestion(5, "Słynny król Babilonii to:", "trudny",
                listOf("Hammurabi" to true, "Ramzes" to false, "Saharabi" to false, "Babilon" to false))
            addQuestion(5, "Jak nazywa się zdobiona skrzynia przedstawiająca mieszkańców Ur?", "trudny",
                listOf("Sztandar z Ur" to true, "Arka z Ur" to false, "Szafa z Ur" to false, "Urska skrzynia" to false))

            addQuestion(5, "Który kontynent został zasiedlony przez człowieka jako ostatni?", "ekspert",
                listOf("Ameryka Południowa" to true, "Ameryka Północna" to false, "Afryka" to false, "Australia" to false))
            addQuestion(5, "Za pomocą czego Sumerowie psali na tabliczkah glinianych?", "ekspert",
                listOf("Za pomocą zaostrzonej trzciny" to true, "Za pomocą drewienka" to false, "Za pomocą pazura" to false, "Za pomocą pióra" to false))
            addQuestion(5, "Egipskie symbole władzy na mumii Tutenchamona:", "trudny",
                listOf("Bicz i laska pasterska" to true, "Berło i korona" to false, "Zwój papirusu i Laska" to false, "Klucz i berło" to false))
            addQuestion(5, "Podaj inną nazwę Sfinksa", "ekspert",
                listOf("Strażnik Piramid" to true, "Człowiek-lew" to false, "Feniks" to false, "Lew-człowiek" to false))
            addQuestion(5, "Komu była poświęcona świątynia w Abu Simbel?", "ekspert",
                listOf("Bogom Słońca" to true, "Hefrenowi" to false, "Bogowi Ra" to false, "Faraonowi Ramzesowi" to false))
            addQuestion(5, "Nad brzegami jakich mórz leżał starożytny Egipt?", "ekspert",
                listOf("Śródziemnym i Czerwonym" to true, "Egejskim i Żółtym" to false, "Śródziemnym i Wschodniochińskim" to false, "Barentsa i Czerwonym" to false))
            addQuestion(5, "Pierwsza kobieta faraon?", "ekspert",
                listOf("Hatszepsut" to true, "Amalasunta" to false, "Salomea" to false, "Kleopatra" to false))
            addQuestion(5, "Kto według Egipcjan osądzał ludzi po śmierci?", "ekspert",
                listOf("Ozyrys" to true, "Ra" to false, "Ramzes" to false, "Hatszepsut" to false))
            addQuestion(5, "Jakie funkcje pełnił król sumeryskiego miasta-państwa?", "ekspert",
                listOf("Był królem i kapłanem" to true, "Był królem i robotnikiem" to false, "Pełnił funkcję króla i pasterza" to false, "Był królem i namiestnikiem cesarza" to false))
            addQuestion(5, "Terakotowa armia to:", "ekspert",
                listOf("Około 8 tysiecy figur żołnierzy chińskich" to true, "Żołnierze wzięci w niewolę przez Chińczyków" to false, "Namalowana na ścianach armia" to false, "Dziesięci najważniejszych dowódców cesarza Chin" to false))


            addQuestion(6, "Wyjaśnij termin DEMOKRACJA", "łatwy",
                listOf("Sprawowanie władzy przez wszystkich wolnych obywateli" to true, "Rządy tyrana" to false, "Rządy króla" to false, "Rządy niewolników" to false))
            addQuestion(6, "Najważniejsze decyzje dotyczące ateńskiej polis podejmowało", "łatwy",
                listOf("Zgromadzenie ludowe" to true, "Otoczenie króla" to false, "Zgromadzenie religijne" to false, "Najbliższe otoczenie przywódcy wojskowego" to false))
            addQuestion(6, "Najwybitniejszy Ateńczyk w czasach demokracji ateńskiej to:", "łatwy",
                listOf("Perykles" to true, "Demostenes" to false, "Dajmos" to false, "Brutus" to false))
            addQuestion(6, "Kto sprawował władzę w Sparcie?", "łatwy",
                listOf("Dwóch królów" to true, "Król" to false, "Lud" to false, "Cesarz" to false))
            addQuestion(6, "Kim byli periojkowie?", "łatwy",
                listOf("Wolni ludzie, ale bez praw politycznych" to true, "Ubodzy rolnicy" to false, "Niewolnicy spartańscy" to false, "Obywatele z pełnymi prawami" to false))
            addQuestion(6, "W jakim wieku Spartanie przestawali służyć w wojsku?", "łatwy",
                listOf("Gdy ukończyli 60 lat" to true, "Po ukończeniu 30 lat" to false, "Po ukończeniu 50 lat" to false, "Zawsze podlegali służbie wojskowej" to false))
            addQuestion(6, "Rok bitwy pod Maratonem", "łatwy",
                listOf("490 p.n.e." to true, "480 p.n.e." to false, "499 p.n.e." to false, "500 p.n.e." to false))
            addQuestion(6, "Rok bitwy pod Termopilami", "łatwy",
                listOf("480 p.n.e." to true, "490 p.n.e." to false, "500 p.n.e." to false, "700 p.n.e." to false))
            addQuestion(6, "Rok biitwy pod Salaminą", "łatwy",
                listOf("480 p.n.e." to true, "490 p.n.e." to false, "500 p.n.e." to false, "700 p.n.e." to false))
            addQuestion(6, "Kim był Kserkses?", "łatwy",
                listOf("Król Perski" to true, "Dowódca Geków pod Salaminą" to false, "Dowódca Greków pod Termopilami" to false, "Główny kapłan Greków" to false))

            addQuestion(6, "Co wyznaczało granice Aten?", "normalny",
                listOf("Mury" to true, "Chaty rolników" to false, "Las" to false, "Pola uprawne" to false))
            addQuestion(6, "Co to jest Agora?", "normalny",
                listOf("To centralny plac Aten, inaczej rynek." to true, "To budowla przy wjeździe do Aten." to false, "To pomnik poświęcony bogom." to false, "To świątynia na cześć Ateny." to false))
            addQuestion(6, "Jedno z głównych zajęć mieszkańców Aten to:", "normalny",
                listOf("Uprawa winorośli." to true, "Górnictwo." to false, "To rzeźbiarstwo." to false, "To polityka." to false))
            addQuestion(6, "Dwa najważniejsze polis starożytnej Grecji to:", "normalny",
                listOf("Sparta i Ateny." to true, "Ateny i Ur." to false, "Sparta i Babilonia." to false, "Ateny i Saloniki." to false))
            addQuestion(6, "Co to jest danina?", "normalny",
                listOf("Obowiązkowa opłata na rzecz panującego." to true, "To dary dla świątyni." to false, "To posag płacony rodzinie męża." to false, "To płaskorzeźba." to false))
            addQuestion(6, "Kto dowodził oddziałem 300 Spartan w bitwie pod Termopilami?", "normalny",
                listOf("Leonidas" to true, "Kserkses" to false, "Sofokles" to false, "Perykles" to false))
            addQuestion(6, "Najwyższa góra Grecji - siedziba bogów?", "normalny",
                listOf("Olimp" to true, "Agora" to false, "Partenon" to false, "Akropol" to false))
            addQuestion(6, "Kim był hoplita?", "normalny",
                listOf("Greckim wojownikiem walczącym mieczem i włócznią." to true, "Kapłanem w świątyni Ateny." to false, "Był górnikiem." to false, "Rolnikiem." to false))
            addQuestion(6, "Atena była boginią:", "normalny",
                listOf("Mądrości." to true, "Ogniska domowego." to false, "Wojny." to false, "Niebios." to false))
            addQuestion(6, "Atrybutami Afrodyty były:", "normalny",
                listOf("gołąb i jabłko." to true, "Lira i pióro." to false, "Miecz i strzała." to false, "Owoc granatu i paw." to false))
            addQuestion(6, "Co to jest Partenon?", "normalny",
                listOf(" Największa świątynia, która była poświęcona Atenie." to true, "To ateński rynek." to false, "To brama, prowadząca do Akropolu" to false, "" to false))
            addQuestion(6, "Kto napisał Illiadę i Odyseję?", "normalny",
                listOf("Homer." to true, "Perykles." to false, "Sofokles." to false, "Eurypides." to false))

            addQuestion(6, "Hellenowie zamieszkiwali", "trudny",
                listOf("Starożytną Grecję" to true, "Starożytne Indie" to false, "Starożytne Chiny" to false, "Starożytny Rzym" to false))
            addQuestion(6, "Lakonia to:", "trudny",
                listOf("Kraina na południu Grecji" to true, "Tam powstała Sparta" to false, "Dom duchownych przywódców" to false, "Kraina w Azji Mniejszej" to false))
            addQuestion(6, "Falanga to:", "trudny",
                listOf("Szyk bojowy piechoty greckiej" to true, "Flaga greckich hoplitów" to false, "Miejsce bitwy" to false, "Specjalne miejsce w pałacu władcy" to false))
            addQuestion(6, "Leonidas to:", "trudny",
                listOf("Król Sparty" to true, "Władca Greków" to false, "Władca Persów" to false, "Dowódca w bitwie pod Salaminą" to false))
            addQuestion(6, "Wymień trzech greckich twórców tragedii", "trudny",
                listOf("Ajshylos, Sofokles, Eurypides" to true, "Ajshylos, Sofoklatos, Perykles" to false, "Leonidas, Temistokles, Homer" to false, "Homer, Wergiliusz, Justynian" to false))
            addQuestion(6, "Sojusz to:", "trudny",
                listOf("Porozumienie państw zawarte, aby sobie pomagać, nie atakować się." to true, "To wypowiedzenie wojny." to false, "To zbiór wszystkich praw." to false, "Specjalna budowla władcy." to false))
            addQuestion(6, "Dyskobol to rzeźba", "trudny",
                listOf("Myrona" to true, "Fidiasza" to false, "Michałą Anioła" to false, "Leonarda da Vnci" to false))
            addQuestion(6, "Posąg Zeusa w Olimpii był wykonany", "trudny",
                listOf("Ze złota i kości słoniowej" to true, "Ze złota i srebra" to false, "Z kamienia" to false, "Z marmuru" to false))
            addQuestion(6, "Arystofanes to:", "trudny",
                listOf("Twórca komedii" to true, "Autor Eneidy" to false, "Rzeźbiarz" to false, "Malarz" to false))
            addQuestion(6, "Olimpiada w starożytnej Grecji to:", "trudny",
                listOf("Okres między igrzyskami" to true, "Igrzyska sportowe" to false, "Miejscowość gdzie odbywały się igrzyska" to false, "Budowla, gdzie odbywały się igrzyska" to false))
            addQuestion(6, "Kto pokonał kogo w bitwie nad rzeczką Granik?", "trudny",
                listOf("Aleksander pokonał armie perską" to true, "Grecy pokonali Spartan" to false, "Kserkses pokonał Leonidasa" to false, "Dariusz pokonał Kserksesa" to false))
            addQuestion(6, "Kiedy była bitwa pod Issos?", "trudny",
                listOf("333 p.n.e." to true, "103 p.n.e." to false, "444 p.n.e." to false, "506 p.n.e." to false))


            addQuestion(6, "Jaką genezę mają biegi maratońskie?", "ekspert",
                listOf("Posłaniec biegł z Maratonu do Aten, aby ogłosić zwycięstwo Greków." to true, "Bitwa pod Maratonem trwała długo, tak jak biegi maratońskie." to false, "Grecy szybko biegli do boju." to false, "Grecy mieli daleką drogę do wojsk Perskich." to false))
            addQuestion(6, "Szyk bojowy Greków to:", "ekspert",
                listOf("Falanga" to true, "Flanka" to false, "Mur tarcz" to false, "Atak czołowy" to false))
            addQuestion(6, "Wyjaśnij znaczenie zwrotu: Wróć z tarczą lub na tarczy", "ekspert",
                listOf("Wróć jako zwycięzca (z tarczą) lub jako zabity w boju (na tarczy). Bo ucieczka z pola walki oznacza porzucenie tarczy." to true, "Zgiń lub przepadnij." to false, "Tarcza jest zbyt cenna, aby ją gubić." to false, "Nie walcz." to false))
            addQuestion(6, "Co znaczy zwrot: objęcia Morfeusza?", "ekspert",
                listOf("Sen" to true, "Uduszenie" to false, "Miłość" to false, "Pojmanie" to false))
            addQuestion(6, "Wyrocznia Apollina to inaczej:", "ekspert",
                listOf("Wyrocznia delficka" to true, "Wyrocznia Ateńska" to false, "Wyrocznia Boska" to false, "Świątynia Ateny" to false))
            addQuestion(6, "Wynalazkiem Archimedesa jest:", "ekspert",
                listOf("Zegar wodny i machina obronna." to true, "Koło i kalendarz." to false, "Żagiel" to false, "Proch" to false))
            addQuestion(6, "Kto wygrał bitwę pod Issos?", "ekspert",
                listOf("Macedończycy" to true, "Persowie" to false, "Grecy" to false, "Rzymianie" to false))
            addQuestion(6, "Ojciec Aleksandra Macedońskiego to:", "ekspert",
                listOf("Filip II" to true, "Aleksander I" to false, "Kserkses" to false, "Temistokles" to false))


            addQuestion(7, "Forum Romanum to:", "łatwy",
                listOf("Główny plac Rzymu, miejsce zebrań i obrad." to true, "Związek miast." to false, "Świątynia rzymska" to false, "Pomnik Cezara." to false))
            addQuestion(7, "Panteon to:", "łatwy",
                listOf("Świątynia poświęcona wszystkim bogom." to true, "Świątynia Ateny." to false, "Miejsce zebrań." to false, "Siedziba Cezara" to false))
            addQuestion(7, "Ilu było konsulów?", "łatwy",
                listOf("Dwóch" to true, "Dwunastu" to false, "150" to false, "120" to false))
            addQuestion(7, "Kto to jest dyktator?", "łatwy",
                listOf("Najwyższy urzędnik z największą władzą w sytuacji kryzysu." to true, "Bogaty kupiec." to false, "Urzędnik najniższej rangi." to false, "Człowiek pozbawiony praw." to false))
            addQuestion(7, "Dwaj bracia z legendy o początkach Rzymu to:", "łatwy",
                listOf("Romulus i Remus" to true, "Romuald i Reno" to false, "Roland i Rosmus" to false, "Redar i Rumos" to false))
            addQuestion(7, "Kim byli kwestorzy", "łatwy",
                listOf("Niżsi urzędnicy zarządzający skarbem." to true, "Żebracy." to false, "Zbierający podatki." to false, "Kucharze." to false))
            addQuestion(7, "Kim byli pretorzy?", "łatwy",
                listOf("Wyższymi urzędnikami strzegącymi porządku." to true, "Najlepszymi żołnierzami." to false, "Najniższą warstwą społęczną." to false, "Duchownymi." to false))

            addQuestion(7, "Kiedy założono Rzym?", "normalny",
                listOf("około 753 p.n.e." to true, "około 237 p.n.e." to false, "około 1000 p.n.e." to false, "około 539 p.n.e." to false))
            addQuestion(7, "Kiedy ustanowiono w Rzymie republikę?", "normalny",
                listOf("509 p.n.e." to true, "608 p.n.e." to false, "905 p.n.e." to false, "753 p.n.e." to false))
            addQuestion(7, "Co wydarzyło się w Rzymie w 44 r. p.n.e.?", "normalny",
                listOf("Zabito Juliusza Cezara." to true, "Rzym najechali barbarzyńcy." to false, "Gajusz Juliusz ogłosił się cezarem" to false, "Był pożar Rzymu." to false))
            addQuestion(7, "Data upadku Cesarstwa Zachodniorzymskiego:", "normalny",
                listOf("476" to true, "467" to false, "353" to false, "376" to false))
            addQuestion(7, "Kim był Wergiliusz?", "normalny",
                listOf("Poetą" to true, "Wynalazcą" to false, "Rzeźbiarzem" to false, "Wodzem" to false))

            addQuestion(7, "Kim był Hannibal?", "trudny",
                listOf("Kartagińskim dowódcą." to true, "Rzeźbiarzem" to false, "Dramatopisarzem" to false, "Dowódcą Rzymian" to false))
            addQuestion(7, "Ile było wojen punickich?", "ntrudny",
                listOf("Trzy" to true, "Pięć" to false, "Dwie" to false, "Cztery" to false))
            addQuestion(7, "Między kim były wojny punickie?", "trudny",
                listOf("Między Rzymem a Kartaginą." to true, "Między Rzymem a Persami." to false, "Między Rzymem a Grekami." to false, "Między Grecją a Kartaginą." to false))
            addQuestion(7, "Jowisz był odpowiednikiem greckiego...", "trudny",
                listOf("Zeusa" to true, "Posejdona" to false, "Hadesa" to false, "Aresa" to false))
            addQuestion(7, "Rzymska boginii ogniska domowego to...", "trudny",
                listOf("Junona" to true, "Artemida" to false, "Wenus" to false, "Minerwa" to false))

            addQuestion(7, "Kim był Attyla?", "ekspert",
                listOf("Wodzem Hunów." to true, "Rzymskim dowódcą." to false, "Greckim wodzem." to false, "Wodzem Kartaginy." to false))
            addQuestion(7, "Kim był Odoaker?", "ekspert",
                listOf("Władca Królestwa Italii, barbarzyńca." to true, "Wodzem greckim" to false, "Kartagińczykiem." to false, "Wodzem perskim" to false))
            addQuestion(7, "Kto zjednoczył ludy barbarzyńskie?", "ekspert",
                listOf("Atylla" to true, "Kserkses" to false, "Hannibal" to false, "Odoaker" to false))
            addQuestion(7, "Co stało się z Kartaginą po wojnach punickich?", "ekspert",
                listOf("Została zburzona przez Rzymian." to true, "Rozwinęla się gospodarczo." to false, "Odbudowała swą flotę i stała się potęgą morską." to false, "Przyłączona została do Rzymu." to false))
            addQuestion(7, "Co to jest Herkulanum?", "ekspert",
                listOf("Miasto zniszczne przez wybuch Wezuwiusza" to true, "Miasto w Kartaginie, zniszczone przez Rzymian." to false, "Greckie miasto." to false, "Perskie miasto." to false))


            addQuestion(8, "Co to jest ikona?", "łatwy",
                listOf("Obraz przedstawiający świętych lub sceny z ich życia." to true, "Rzeźba przedstawiająca króla." to false, "Pomnik." to false, "Kronika." to false))
            addQuestion(8, "Islam to...:", "łatwy",
                listOf("Jedna z religii." to true, "To obraz świętych." to false, "To kraina w Grecji." to false, "To święta księga." to false))
            addQuestion(8, "Co to jest Koran?", "łatwy",
                listOf("Święta księga islamu." to true, "To zbiór praw." to false, "Miejsce pielgrzymek." to false, "Religia." to false))
            addQuestion(8, "Dynastia to...?", "łatwy",
                listOf("Władcy z jednego rodu lub ród, z którego si wywodzą." to true, "To nazwa szyku wojskowego." to false, "To warstwa społęczna." to false, "To szlacheckie herby." to false))
            addQuestion(8, "Który władca zjednoczył Franków?", "łatwy",
                listOf("Karol Wielki." to true, "Pepin Krótki." to false, "Karol Młot" to false, "Filip Piękny." to false))
            addQuestion(8, "Co to jest ekskomunika?", "łatwy",
                listOf("Wykluczenie z życia kościoła - najwyższa kościelna kara." to true, "Komunia święta." to false, "Pokuta za grzechy." to false, "ważny tutuł w kościele katolickim." to false))
            addQuestion(8, "Zakony powstałe w czasie wypraw krzyżowych to:", "łatwy",
                listOf("Joannici, Templariusze." to true, "Zakony Braci Bosych i Jezuitów" to false, "Zakony Templariuszy i Marianów" to false, "Jeuitów i Józefitów." to false))
            addQuestion(8, "Freski to...?", "łatwy",
                listOf("Malowidła na ścianie." to true, "Układane z kawałków ceramiki obrazy." to false, "Rzeźby na ścianie." to false, "Płaskorzeźby." to false))

            addQuestion(8, "Hagia Sofia to...?", "normalny",
                listOf("Najpierw kościół chrześcijański, potem meczet islamski." to true, "Brama w Konstantynopolu." to false, "Żona cesarza bizantyńskiego." to false, "Władczyni Bizancjum." to false))
            addQuestion(8, "Gdzie znajduje się Czarny Kamień?", "normalny",
                listOf("W Mekce w świątyni Kaaba." to true, "W Medynie." to false, "W Rzymie." to false, "W Konstantynopolu." to false))
            addQuestion(8, "Allach to...?", "normalny",
                listOf("Imię boga w islamie." to true, "Prorok w islamie." to false, "To wyznawca islamu." to false, "To arabski święty." to false))
            addQuestion(8, "Co to jest dżihad?", "normalny",
                listOf("To święta wojna prowadzona w obronie wiary." to true, "To zemsta." to false, "To opłata za wjazd do miasta." to false, "to święto w islamie." to false))
            addQuestion(8, "Dynastia władająca państwem Franków od VIII wieku to?", "normalny",
                listOf("Karolingowie" to true, "Kapetyngowie" to false, "Windsorowie" to false, "Habsburgowie" to false))
            addQuestion(8, "Kto ukorzył się w Canossie?", "normalny",
                listOf("Cesarz Henryk IV" to true, "Król Filip Piękny" to false, "Grzegorz VI" to false, "Karol Wielki" to false))
            addQuestion(8, "W którym wieku była pierwsza wyprawa krzyżowa?", "normalny",
                listOf("XI" to true, "X" to false, "IX" to false, "XII" to false))
            addQuestion(8, "Co to jest inwestytura?", "normalny",
                listOf("To nadanie przez władcę stanowiska w kościele wraz z majątkiem ziemskim." to true, "Święta księga judaizmu." to false, "Święto muzułmanów." to false, "To święta wojna w obronie wiary." to false))

            addQuestion(8, "Kiedy zaczyna się średniowiecze?", "trudny",
                listOf("Od 476 r." to true, "Od 600 r." to false, "Od 500 r." to false, "Od 501 r." to false))
            addQuestion(8, "Data upadku Konstantynopola...?", "trudny",
                listOf("1453 r." to true, "476 r." to false, "1221 r." to false, "451 r." to false))
            addQuestion(8, "Mahomet w islamie uznawany jest za...?", "trudny",
                listOf("Proroka." to true, "Boga." to false, "Apostołą." to false, "Anioła." to false))
            addQuestion(8, "W którym roku była ucieczka Mahometa do Medyny?", "trudny",
                listOf("622" to true, "1453" to false, "476" to false, "689" to false))
            addQuestion(8, "Kim był Justynian Wielki?", "trudny",
                listOf("Cesarzem Bizancjum." to true, "Papieżem." to false, "Władcą Franków." to false, "Władcą Rzymu." to false))
            addQuestion(8, "Rok koronacji Karola Wielkiego na cesarza?", "trudny",
                listOf("800" to true, "843" to false, "1099" to false, "1291" to false))
            addQuestion(8, "Koronacja Ottona I na cesarza była w roku...?", "trudny",
                listOf("962" to true, "800" to false, "1291" to false, "1453" to false))

            addQuestion(8, "Jaki dokument przerwał prześladowanie chrześcijan?", "ekspert",
                listOf("Edykt Mediolański." to true, "Bulla papieża." to false, "Pismo Karola Wielkiego." to false, "Rozkaz Ottona I." to false))
            addQuestion(8, "Jakie znaczenie miał Kodeks Justyniana? ", "ekspert",
                listOf("Stał się podstawą dla innych systemów prawnych." to true, "Nie miał większego znaczenia." to false, "Bizantyńczycy nie stosowali go." to false, "Nie naprawił nic." to false))
            addQuestion(8, "Przykład wpływu nauki, kultury Arabów na Europę?", "ekspert",
                listOf("Arabowie przekazali system cyfr (cyfry arabskie), wprowadzili cyfrę zero." to true, "Wynaleźli proch i dynamit." to false, "Wynaleźli żagiel." to false, "Wynaleźli koło." to false))
            addQuestion(8, "W którym roku upadła Akka?", "ekspert",
                listOf("1291" to true, "1453" to false, "1301" to false, "996" to false))
            addQuestion(8, "Akka to była ...?", "ekspert",
                listOf("Ostatnia twierdza krzyżowców w Ziemi Świętej." to true, "Miejsce narodzin Mahometa." to false, "Przedmiot kultu islamu." to false, "Miejsce zwycięstwa krzyżowców." to false))
            addQuestion(8, "Inna nazwa zakonu Joannitów", "ekspert",
                listOf("Zakon maltański" to true, "Zakon Franciszkanów" to false, "Zakon Templariuszy" to false, "Krzyżacy" to false))
            addQuestion(8, "Jak zakończyła się krucjata dziecięca?", "ekspert",
                listOf("" to true, "Dzieci nie dotarły na miejsce, po drodze umierały." to false, "Odniosła sukces, zdobyła Akkę." to false, "Dzieci dotarły na miejsce, ale sukcesów nie osiągnęły." to false))


            addQuestion(9, "Kto to jest senior w średniowieczu?", "łatwy",
                listOf("To osoba nadająca ziemię wasalowi" to true, "To osoba otrzymująca ziemię od wasala." to false, "To starszy król." to false, "To osoba mająca władzę." to false))
            addQuestion(9, "Hołd lenny to...?", "łatwy",
                listOf("Uroczystość nadania lenna wasalowi." to true, "Potwierdzenie podległości panu." to false, "Oddanie hołdu władcy." to false, "Pokonany oddawał hołd zwycięzcy." to false))
            addQuestion(9, "Wyjaśnij termin suzeren.?", "łatwy",
                listOf("To sprawujący władzę król albo książę - najwyższy senior" to true, "To najniższy poddany." to false, "" to false, "" to false))
            addQuestion(9, "Czy można było być seniorem i wasalem jednocześnie?", "łatwy",
                listOf("Tak." to true, "Nie" to false, "Tylko jak się było królem." to false, "Tylko jak się było chłopem." to false))
            addQuestion(9, "Czym zajmowali się kopiści?", "łatwy",
                listOf("Przepisywaniem ksiąg." to true, "Remontem kopii." to false, "Ostrzyli kopie rycerzy." to false, "Wydawali rycerzom kopie." to false))

            addQuestion(9, "Paź to...?", "normalny",
                listOf("Pochodzący z rycerstwa chłopiec, który służył innemu rycerzowi, żeby sam mógł zostać rycerzem." to true, "To służący króla, pochodzący z niskich warstw społecznych." to false, "To chłop uprawiający ziemię rycerza." to false, "To kupiec." to false))
            addQuestion(9, "Giermek to...?", "normalny",
                listOf("Były paź, towarzysz rycerza w turniejach." to true, "TRzemieślnik lepiący garnki." to false, "Służacy panu chłop." to false, "Skazany na karę za przestępstwa." to false))
            addQuestion(9, "Co to jest lokacja?", "normalny",
                listOf("Założenie miejscowości." to true, "Miejsce, gdzie ulokowany był skarb króla." to false, "Uroczystość pasowania na rycerza." to false, "Królewski bal." to false))
            addQuestion(9, "Plebs to:", "normalny",
                listOf("Najbiedniejsi mieszczanie" to true, "Rolnicy" to false, "Ubodzy patrycjusze" to false, "Rycerze" to false))
            addQuestion(9, "Co było siedzibami zakonów?", "normalny",
                listOf("Klasztory" to true, "Kościoły" to false, "Dwory książęce" to false, "Katedry" to false))

            addQuestion(9, "Co to jest biblia pauperum?", "trudny",
                listOf("Biblia dla ubogich. Zawierała tylko ilustracje." to true, "Biblia ze złotymi klamrami." to false, "Biblia podarowana królowi." to false, "Biblia fałszywa." to false))
            addQuestion(9, "Kto stał na szczycie drabiny feudalnej?", "trudny",
                listOf("Władca, jako jedyny niebędący wasalem." to true, "Bogate chłopstwo." to false, "Rycerze." to false, "Duchowieństwo." to false))
            addQuestion(9, "Początki budownictwa romańskiego to:", "trudny",
                listOf("XI wiek" to true, "XII wiek" to false, "IX wiek" to false, "XV wiek" to false))
            addQuestion(9, "Pierwsze budowle w stylu gotyckim powstały w:", "trudny",
                listOf("XII wieku" to true, "XI wieku" to false, "IX wieku" to false, "XIII wieku" to false))
            addQuestion(9, "Strzelistość to cecha stylu:", "trudny",
                listOf("Gotyckiego" to true, "Romańskiego" to false, "Bizantyńskiego" to false, "Klasycznego" to false))

            addQuestion(9, "Literacki ideał rycerski to:", "ekspert",
                listOf("Roland" to true, "Karol I" to false, "Filip II" to false, "Hammurabi" to false))
            addQuestion(9, "Refektarz pełnł funkcję...?", "ekspert",
                listOf("Jadalni" to true, "Sypialni" to false, "Łazienki" to false, "Przedpokoju" to false))
            addQuestion(9, "Wirydarz to:", "ekspert",
                listOf("Wewnętrzny ogród." to true, "Schowek." to false, "Strych w klasztorze" to false, "Piwnica klasztorna." to false))
            addQuestion(9, "Wincenty Kadłubek był", "ekspert",
                listOf("Kronikarzem" to true, "Rycerzem" to false, "Biskupem" to false, "Królem" to false))
            addQuestion(9, "Czym zajmował się Gall Anonim?", "ekspert",
                listOf("Pisaniem kroniki." to true, "Pisaniem listów do króla." to false, "Przepisywaniem ksiąg." to false, "Opieką nad skarbcem króla." to false))


            addQuestion(10, "Przybycie Słowian na ziemie polskie:", "łatwy",
                listOf("połowa VI wieku" to true, "X wiek" to false, "VIII wiek" to false, "IV wiek" to false))
            addQuestion(10, "Powstanie państwa Wielkomorawskiego nastąpiło:", "łatwy",
                listOf("Około 830 roku" to true, "Około 1000 roku" to false, "Około 600 roku" to false, "Około 950 roku" to false))
            addQuestion(10, "Rok chrztu Polski", "łatwy",
                listOf("966" to true, "899" to false, "669" to false, "906" to false))
            addQuestion(10, "Swaróg, Perun to:", "łatwy",
                listOf("Bogowie Słowian." to true, "Legendarni władcy Słowian." to false, "Założyciele Polski" to false, "Pierwsi zakonnicy." to false))
            addQuestion(10, "Dagome Judex to dokument, w którym...", "łatwy",
                listOf("Mieszko I oddaje Polskę pod opiekę Stolicy Apostolskiej." to true, "Mieszko I zawarł prawa obowiązujące na jego ziemiach." to false, "Wyznanie wiary." to false, "okument o abdykacji." to false))

            addQuestion(10, "Data Chrztu Polski?", "normalny",
                listOf("966 r." to true, "999 r." to false, "1000 r." to false, "865 r." to false))
            addQuestion(10, "Gdzie był chrzest Polski?", "normalny",
                listOf("W Gnieźnie." to true, "W Poznaniu." to false, "W Warszawie." to false, "W Krakowie." to false))
            addQuestion(10, "Żona Mieszka I to...?", "normalny",
                listOf("Dobrawa" to true, "Dobromiła" to false, "Dąbrówka" to false, "Dorota" to false))
            addQuestion(10, "Data bitwy pod Cedynią?", "normalny",
                listOf("972" to true, "966" to false, "987" to false, "1000" to false))
            addQuestion(10, "Gdzie powstało w Polsce pierwsze biskupstwo?", "normalny",
                listOf("W Poznaniu" to true, "W Gnieźnie" to false, "W Krakowie" to false, "W Warszawie" to false))
            addQuestion(10, "Relikwie to?", "normalny",
                listOf("Szczątki zmarłych świętych." to true, "Szkatułka do przechowywania cennych rzeczy." to false, "Malowidła na ścianie." to false, "Skarby królewskie." to false))

            addQuestion(10, "Rok koronacji i śmierci Bolesława Chrobrego", "trudny",
                listOf("1025" to true, "997" to false, "1000" to false, "966" to false))
            addQuestion(10, "Rok Zjazdu Gnieźnieńskiego", "trudny",
                listOf("1000" to true, "966" to false, "875" to false, "1100" to false))
            addQuestion(10, "Kto został królem po śmierci Bolesława Chrobrego?", "trudny",
                listOf("Mieszko II" to true, "Łokietek" to false, "Mieszko I" to false, "Kazimierz Wielki" to false))
            addQuestion(10, "Rok ogłoszenia testamentu Bolesława Chrobrego:", "trudny",
                listOf("1138" to true, "1000" to false, "966" to false, "1109" to false))
            addQuestion(10, "Rok bitwy pod Legnicą", "trudny",
                listOf("1241" to true, "1227" to false, "1226" to false, "1138" to false))

            addQuestion(10, "Kim był Święty Wojciech?", "ekspert",
                listOf("Biskupem Pragi, misjonarzem, męczennikiem." to true, "Księdzem w Gnieźnie" to false, "Doradcą i skarbnikiem króla." to false, "Dowódcą wojsk pod Cedynią." to false))
            addQuestion(10, "Podaj przykłady zasług Cyryla i Metodego", "ekspert",
                listOf("Opracowali alfabet i wprowadzili do liturgii język słowiański." to true, "Dobrze doradzali królowi." to false, "Rozwinęli rolnictwo." to false, "Nauczyli budowy domów z cegły." to false))
            addQuestion(10, "Jakie państwo powstało po upadku państwa Wielkomorawskiego?", "ekspert",
                listOf("Czechy" to true, "Węgry" to false, "Serbia" to false, "Bośnia" to false))
            addQuestion(10, "Dwa plemiona słowiańskie na ziemiach polskich to:", "ekspert",
                listOf("Pomożanie, Mazowszanie." to true, "Rusowie, Goci." to false, "Gepidzi i Drewlanie." to false, "Wizygoci i Ostrogoci." to false))
            addQuestion(10, "Pierwsze państwa plemienne na ziemiach polskich to:", "ekspert",
                listOf("Wiślanie i Państwo Polan." to true, "Drewlanie i Frankowie." to false, "Wizygoci i Mazowszanie" to false, "Państwo Opolan i Państwo Waregów." to false))


            addQuestion(11, "Co to jest dzielnica senioralna?", "łatwy",
                listOf("To teren rządzony przez najstarszego z Piastów zgodnie z testamentem Krzywoustego." to true, "Część miasta należąca do seniora rodu." to false, "Stolica państwa." to false, "Państwo seniora." to false))
            addQuestion(11, "Trójpolówka to...?", "łatwy",
                listOf("Sposób uprawy: uprawa dwóch części roli w roku, trzecia to ugór. I na zmianę." to true, "Rodzaj broni palnej." to false, "Rodzaj ubrania średniowiecznego." to false, "Nieuprawna ziemia." to false))
            addQuestion(11, "Szczerbiec to...?", "łatwy",
                listOf("Miecz koronacyjny królów Polski." to true, "Miecz Mieszka I." to false, "Miecz podarowany przez Ottona I." to false, "Miecz z drewna." to false))
            addQuestion(11, "Kto to był starosta?", "łatwy",
                listOf("Urzędnik, którego mianował i odwoływał włądca. Był on namiestnikiem prowincji, ziemi." to true, "Najstarszy w rodzie." to false, "Władca." to false, "Zamożny kupiec." to false))
            addQuestion(11, "Kto założył Akademię Krakowską?", "łatwy",
                listOf("Kazimierz Wielki" to true, "Bolesław Chrobry." to false, "Krzywousty" to false, "Mieszko I" to false))

            addQuestion(11, "Kim był Władysław Wygnaniec?", "normalny",
                listOf("Najstarszym synem Bolesława Krzywoustego. Książę senior." to true, "Najstarszym Synem Bolesława Chrobrego." to false, "Najstarszy syn Mieszka I." to false, "Najstarszy syn Ottona I." to false))
            addQuestion(11, "Kiedy Konrad Mazowiecki sprowadził Krzyżaków?", "normalny",
                listOf("1226" to true, "1000" to false, "1241" to false, "1227" to false))
            addQuestion(11, "Podaj rok zjazdu w Gąsawie.", "normalny",
                listOf("1227" to true, "1226" to false, "1241" to false, "996" to false))
            addQuestion(11, "Pierwszy najazd Mongołów na Polskę był w roku...?", "normalny",
                listOf("1241" to true, "1227" to false, "1000" to false, "1226" to false))
            addQuestion(11, "Śmierć Leszka Białęgo była w roku...?", "normalny",
                listOf("1227" to true, "1287" to false, "121241" to false, "1100" to false))
            addQuestion(11, "Bitwa pod Legnicą była w roku...?", "normalny",
                listOf("1241" to true, "1226" to false, "1238" to false, "1240" to false))

            addQuestion(11, "Konsekwencje sprowadzenia Krzyżaków do Polski to:", "trudny",
                listOf("Długotrwałe konflikty z Krzyżakami, osłąbienie państwa." to true, "Zjednoczenie ziem polskich." to false, "Krzyżacy stali się sprzymierzeńcem Polski." to false, "Zyskaliśmy sojusznika." to false))
            addQuestion(11, "Główne osiągnięcie Przemysła II...?", "trudny",
                listOf("Zjednoczenie Wielkopolski i Pomorza Gdańskiego." to true, "Rozbicie Krzyżaków." to false, "Włączył do Polski Śląsk." to false, "Zawarł przymierze z Krzyżakami." to false))
            addQuestion(11, "Rok koronacji Władysława Łokietka...?", "trudny",
                listOf("1320" to true, "1241" to false, "1306" to false, "1333" to false))
            addQuestion(11, "Osiagnięciem Władysława łokietka jest:", "trudny",
                listOf("Zjednoczenie najważniejszych prowincji i przywrócenie statusu Królestwa Polsce." to true, "" to false, "" to false, "" to false))
            addQuestion(11, "Za koniec okresu rozbicia dzielnicowego uznaje się...?", "trudny",
                listOf("Koronację Władysława Łokietka na króla Polski." to true, "Śmierć Przemysła II." to false, "Objęcie władzy przez Wacława II." to false, "Bitwę pod Legnicą." to false))
            addQuestion(11, "Rok zawarcia unii w Krewie.", "trudny",
                listOf("1385" to true, "1364" to false, "1320" to false, "1306" to false))
            addQuestion(11, "Kiedy była Wielka wojna z zakonem krzyżackim?", "ekspert",
                listOf("1409-1411" to true, "1414-1418" to false, "1420-1423" to false, "1411-14013" to false))

            addQuestion(11, "Jeden ze sposobów walki Mongołów to stosowanie...?", "ekspert",
                listOf("Pozorowanych odwrotów." to true, "Walka do ostatniego żołnierza." to false, "Atakowanie tylko piechotą." to false, "Stosowali proch." to false))
            addQuestion(11, "Podczas jakiego wydarzenia został założony Zakon Krzyżacki?", "ekspert",
                listOf("Podczas III wyprawy krzyżowej." to true, "Podczas Zjazdu Gnieźnieńskiego." to false, "Podczas II wyprawy krzyżowej." to false, "W czasie najazdu Mongołów." to false))
            addQuestion(11, "W jakim roku była uczta u Wierzynka?", "ekspert",
                listOf("1364" to true, "1320" to false, "1241" to false, "1306" to false))
            addQuestion(11, "Cel uczty u Wierzynka?", "ekspert",
                listOf("Król pokazał, że jego państwo jest silne i bogate." to true, "Pojmanie Leszka Białego." to false, "Obłaskawienie Krzyżaków." to false, "Próba zgładzenia Kazimierza Wielkiego." to false))
            addQuestion(11, "Data soboru w Konstancji...? ", "ekspert",
                listOf("1414-1418" to true, "1410-1414" to false, "1241-1245" to false, "1333-1337" to false))
            addQuestion(11, "Data unii w Horodle?", "ekspert",
                listOf("1413" to true, "1410" to false, "1414" to false, "1418" to false))


            addQuestion(12, "Jakie towary sprowadzano z Indii?", "łatwy",
                listOf("Przyprawy, jedwab." to true, "Ziemniaki, jabłka." to false, "Gruszki, śliwki." to false, "Śliwki, winogrona." to false))
            addQuestion(12, "Odkrywca Ameryki", "łatwy",
                listOf("Krzysztof Kolumb" to true, "Vasco da Gama" to false, "Americo Vespucci" to false, "" to false))
            addQuestion(12, "Rok odkrycia Ameryki?", "łatwy",
                listOf("1492" to true, "1392" to false, "1592" to false, "1292" to false))
            addQuestion(12, "Rdzenne ludy Ameryki to np.:", "łatwy",
                listOf("Majowie, Aztekowie, Inkowie." to true, "Hindusi, Czeczeni, Inkowie." to false, "Aztekowie, Egipcjanie, Rzymianie." to false, "Grecy, Inkowie, Sumerowie." to false))
            addQuestion(12, "Nowy świat to określenie...?", "łatwy",
                listOf("dopiero co odkrytych lądów, jak Ameryka Południowa, Północna." to true, "To nowe tereny podbite przez Rzymian." to false, "To nowe zwyczaje u Hiszpanów." to false, "To święto wprowadzone przez królową Izabellę." to false))
            addQuestion(12, "Wynalazca druku to...?", "łatwy",
                listOf("Jan Gutenberg" to true, "Leonardo da Vinci" to false, "Ferdynand Magellan" to false, "Krzysztof Kolumb" to false))
            addQuestion(12, "Kolebka renesansu to:", "łatwy",
                listOf("Włochy" to true, "Hiszpania" to false, "Portugalia" to false, "Niemcy" to false))

            addQuestion(12, "Astrolabium to..?", "normalny",
                listOf("Przyrząd pomiarowy używany do nawigacji na morzu." to true, "Rodzaj laboratorium chemicznego." to false, "Książka astronomiczna." to false, "rodzaj broni." to false))
            addQuestion(12, "Karawela to...?", "normalny",
                listOf("Trójmasztowy żaglowiec." to true, "Broń palna." to false, "Rodzaj łuku." to false, "Rodzaj pojazdu na kołach." to false))
            addQuestion(12, "Dowódca wyprawy dookoła świata to...?", "normalny",
                listOf("Ferdynand Magellan" to true, "Vasco da Gama" to false, "Krzysztof Kolumb" to false, "Americo Vespucci" to false))
            addQuestion(12, "Podaj daty pierwszej wyprawy dookoła ziemi.", "normalny",
                listOf("1519–1522" to true, "1492-1502" to false, "1519-1529" to false, "1502-1506" to false))
            addQuestion(12, "Pozytywny skutek wielkich odkryć geograficznych?", "normalny",
                listOf("Rozwój wiedzy o świecie, rozwój nauki," to true, "Zdobycie złota." to false, "" to false, "" to false))












            addQuestion(12, "Jak napisać pytania do klasy VI?", "łatwy",
                listOf("Żeby je mama zaakceptowała" to true, "Poprawnie" to false, "Merytorycznie" to false, "Po lebkach" to false))



            addQuestion(18, "Co znaczy restauracja?", "łatwy",
                listOf("Odbudowa. Do władzy wracają obalone w rewolucji dynastie." to true, "Odświeżenie. Władza zostaje ta sama, ale łagodnieje." to false, "Ponowna rewolucja." to false, "Wznowienie rokowań." to false))
            addQuestion(18, "Co znaczy legitymizm?", "łatwy",
                listOf("Prawowity. Czyli że władza monarchów w Europie jest nienaruszalna. " to true, "Zmiana. Czyli władzę naród może zmianić." to false, "Przejęty podstępem, Czyli władza jest nielegalna." to false, "Przejęcie włądzy. Czyli nawoływanie do ponownej rewolucji" to false))
            addQuestion(18, "Wyjaśnij termin równowaga europejska", "łatwy",
                listOf("Żadne państwo nie może dominować nad innymi." to true, "Każde państwo w Europie ma mieć tyle samo wojska" to false, "Każde państwo w Europie, ma być tak samo bogate." to false, "Państwa mają mieć tyle samo powierzchni." to false))
            addQuestion(18, "Kiedy był początek i koniec kongresu wiedeńskiego?", "łatwy",
                listOf("1814-1815." to true, "1814-1816." to false, "1815-1816." to false, "1818-1820." to false))
            addQuestion(18, "Najważniejsze państwa na kongresie wiedeńskim to...?", "łatwy",
                listOf("Rosja, Prusy, Wielka Brytania, Austria, Francja." to true, "Rosja, Prusy, Włochy." to false, "Austria, Wielka Brytania, Włochy, Hiszpania." to false, "Hiszpania, Włochy, Prusy, Rosja." to false))
            addQuestion(18, "Jedna z przyczyn zwołania kongresu wiedeńsiego?", "łatwy",
                listOf("Chęć ustanowienia nowego ładu w Europie." to true, "Chęć powrotu do władzy dawnych władców." to false, "Wzbogacenie się Francji i Wielkiej Brytanii." to false, "Utrata terenów przez Francję." to false))
            addQuestion(18, "Jaki był najważniejszy cel kongresu wiedeńskiego w latach 1814–1815?", "łatwy",
                listOf("Przywrócenie legalnych władców i zrównoważenie sił w Europie." to true, "Rozdanie ziem polskich sąsiadom." to false, "Stworzenie stałej unii państw pod przywództwem Austrii." to false, "Podział świata między monarchię brytyjską i rosyjską." to false))
            addQuestion(18, "Co było istotą Świętego Przymierza?", "łatwy",
                listOf("Konfederacja państw niemieckich po kongresie." to false, "Pakt brytyjsko-francuski przeciwko Napoleonowi." to false, "Sojusz Rosji, Austrii i Prus oparty na zasadach chrześcijańskich." to true,  "Unia polsko-litewska z 1385 r." to false))
            addQuestion(18, "Co znaczy industrializacja?", "łatwy",
                listOf("Szybki rozwój przemysłu dzięki wprowadzeniu maszyn." to true, "Rozwój manufaktur." to false, "Rozwój rolnictwa." to false, "Przewaga rolnictwa nad przemysłem." to false))
            addQuestion(18, "Wyjaśnij termin rewolucja przemysłowa.", "łatwy",
                listOf("To szybkie przejście od produkcji rolno-rzemieślniczej do przemysłowej. Początek był w Wielkiej Brytanii XVIII w." to true, "To zmiany spowodowane rozwojem rolnictwa.." to false, "To walka robotników o lepsze pensje." to false, "To walka robotników o miejsca pracy." to false))
            addQuestion(18, "Rok skonstruowania maszyny parowej?", "łatwy",
                listOf("1763." to true, "1814." to false, "1815." to false, "1760." to false))
            addQuestion(18, "Kto to był James Watt?", "łatwy",
                listOf("Konstruktor maszyny parowej." to true, "Wynalazca telegrafu." to false, "Konstruktor pierwszego parowozu." to false, "Wynalazca żarówki." to false))
            addQuestion(18, "Zaznacz przyczyny rewolucji przemysłowej?", "łatwy",
                listOf("Przenoszenie się ludzi ze wsi do miast, poszukiwanie wydajniejszych środków produkcji." to true, "Protesty robotników, słabość przemysłu." to false, "Brak ziemi uprawnej, bogacenie się rolników." to false, "Nakaz króla aby zatrudniać więcej osób, bezrobocie w miastach." to false))
            addQuestion(18, "Rok udoskonalenia maszyny parowej?", "łatwy",
                listOf("1763." to true, "1814." to false, "1815." to false, "1760." to false))
            addQuestion(18, "Co to jest ideologia??", "łatwy",
                listOf("To zbiór poglądów na życie społeczne i gospodarcze w państwie." to true, "To zbiór praw i obowiązków obywateli." to false, "To rewolucja gospodarcza." to false, "To zmiany polityczne." to false))
            addQuestion(18, "Co to jest proletariat?", "łatwy",
                listOf("Warstwa ubogich robotników." to true, "Chłopi pracujący na roli." to false, "To wszyscy mieszkańcy danego miasta." to false, "To bezrobotni." to false))
            addQuestion(18, "Co to jest związek zawodowy?", "łatwy",
                listOf("To organizacja pracowników dbająca o ich prawa w pracy." to true, "To związek dwóch zawodów." to false, "To powiązanie przemysłu z rolnictwem." to false, "To osoby pracujące w jednym zakładzie." to false))
            addQuestion(18, "Kim był Adam Smith?", "łatwy",
                listOf("Głównym myślicielem liberalizmu." to true, "Gółwnym myślicielem konserwatyzmu." to false, "Głównym myślicielem komunizmu." to false, "Głównym myślicielem socjalizmu." to false))
            addQuestion(18, "Co to jest strajk?", "łatwy",
                listOf("Powstrzymanie się pracownika od pracy, żeby wymusić lepsze warunki." to true, "To zebranie wszystkich pracowników zakładu." to false, "To kradzież w miejscu pracy." to false, "To złamanie zasad pracowniczych." to false))
            addQuestion(18, "Co to jest Wiosna Ludów?", "łatwy",
                listOf("To wybuch 1848 r. w Europie wielu powstań antyrządowych i narodowych." to true, "To odnowienie moralne ludu." to false, "To pojawienie się w Europie wielu ideologii." to false, "To strajki robotników." to false))
            addQuestion(18, "Data Wiosny Ludów to...??", "łatwy",
                listOf("1848-1849." to true, "1814-1815." to false, "1866-1867." to false, "1763-1764." to false))
            addQuestion(18, "Dwie przyczyny Wiosny Ludów to...?", "łatwy",
                listOf("Nieurodzaj i głód, tendencje niepodległościowe (Polacy, Węgry) i zjednoczeniowe (Niemcy, Włosi)." to true, "Przenoszenie się ludzi do miast, boacenie się robotników." to false, "Rozwój przemysłu, industrializacja." to false, "Postanowienie kongresu wiedeńskiego i rewolucja przemysłowa." to false))

            addQuestion(18, "Co znaczy abdykacja?", "normalny",
                listOf("Zrzeczenie się władzy." to true, "Ponowna koronacja." to false, "Przywrócenie porządku." to false, "Wybór nowego władcy." to false))
            addQuestion(18, "Wyjaśnij termin Święte Przymierze.", "normalny",
                listOf("To sojusz Rosji, Austrii i Prus, zawarty w 1815 r." to true, "To sojusz Watykanu z państwami Europy." to false, "To pakt o nieagresji między Rosją a Włochami." to false, "To pakt Hiszpanii i Prus o dostarczaniu broni." to false))
            addQuestion(18, "Decyzje kongresu wiedeńskiego dotyczące Polski", "normalny",
                listOf("Zlikwidowano Księstwo Warszawskie, ziemie polskie zabrały Rosja, Prusy i Austria." to true, "Polska dostała Pomorze i Śląsk." to false, "Do Polski trafiło część Prus Wschodnich." to false, "Polska utraciła Śląsk." to false))
            addQuestion(18, "Data bitwy pod Waterloo?", "normalny",
                listOf("18 VI 1815" to true, "18 VI 1814" to false, "15 V 1815" to false, "15 V 1814" to false))
            addQuestion(18, "Data podpisania Świętego Przymierza?", "normalny",
                listOf("IX 1815" to true, "X 1814" to false, "XI 1815" to false, "IX 1816" to false))
            addQuestion(18, "W której bitwie w 1815 r. zakończył się ostatecznie epizod stu dni Napoleona?", "normalny",
                listOf("Pod Lipskiem." to false, "Pod Austerlitz." to false, "Pod Waterloo." to true,  "Pod Trafalgar." to false))
            addQuestion(18, "Kto na kongresie wiedeńskim reprezentował głównie interesy Francji?", "normalny",
                listOf("Klemens von Metternich." to false, "Robert Stewart Castlereagh." to false, "Charles de Talleyrand." to true,  "Książe Wellington." to false))
            addQuestion(18, "Co to jest manufaktura??", "normalny",
                listOf("Zakład, w którym prace wykonują robotnicy ręcznie." to true, "Duży zakład przemysłowy z wieloma maszynami." to false, "Zakład, w którym maszyny wytwarzają produkty." to false, "Zakłąd z maszynami parowymi." to false))
            addQuestion(18, "Co to jest uranizacja?", "normalny",
                listOf("Gwałtowny rozwój miast." to true, "Dokument wydany przez papieża Urbana II." to false, "Gwałtowny rozwój przemysłu." to false, "Gwałtowny rozwój rolnictwa." to false))
            addQuestion(18, "Co to jest kapitał?", "normalny",
                listOf("Środki finansowe potrzebne do zainwestowania np. w zakup nowych maszyn." to true, "To dług, który należy spłacić." to false, "To początek kryzysu gospodarczego." to false, "To przełomowa data w historii." to false))
            addQuestion(18, "Kim był Samuel Morse?", "normalny",
                listOf("Wynalazca telegrafu." to true, "Wynalazca maszyny parowej." to false, "Wynalazca żarówki." to false, "Wynalazca silnika elektrycznego." to false))
            addQuestion(18, "Kim był George Stephenson?", "normalny",
                listOf("Konstruktor lokomotywy parowej." to true, "Wynalazca telegrafu." to false, "Wynalazca żarówki." to false, "Wynalazca maszyny parowej." to false))
            addQuestion(18, "Jakie są założenia liberalizmu?", "normalny",
                listOf("Państwo nie ingeruje w gospodarkę, człowiek jest wolny i równy wobec prawa" to true, "Społeczeństwo bezklasowe, likwidacja własności prywatnej." to false, "Tradycyjne wartości, zachowanie dawnych monarchii." to false, "Wszystkie płace są równe, wszyscy ludzie są równi" to false))
            addQuestion(18, "Kim był Edmund Burke?", "normalny",
                listOf("Główny myśliciel konserwatyzmu." to true, "Główny myśliciel socjalizmu." to false, "Główny myśliciel kapitalizmu." to false, "Główny myśliciel liberalizmu" to false))
            addQuestion(18, "Główny myśliciel komunizmu to...?", "normalny",
                listOf("Karol Marks," to true, "Edmunt Burke." to false, "Adam Smith" to false, "Robert Owen" to false))
            addQuestion(18, "Przyczyny powstania ruchu robotniczego?", "normalny",
                listOf("Trudne warunki pracy spowodowały, że robotnicy zaczęli się organizować" to true, "Robotnicy nie chcieli pracować w soboty i zaczęli się organizować, żeby to wywalczyć." to false, "Wprowadzenie masowych zwolnień w zakładch." to false, "Obniżka wypłat o 30 % spowodowała, że robotnicy zaczęli strajkować." to false))
            addQuestion(18, "Gdzie miała miejsce rewolucja lipcowa?", "normalny",
                listOf("We Francji." to true, "W Belgii." to false, "W Hiszpanii." to false, "W Austrii." to false))
            addQuestion(18, "Co dało początek Wiośnie Ludów?", "normalny",
                listOf("Rewolucja lutowa we Francji." to true, "Rewolucja lipcowa we Francji. " to false, "Wybuch powstania na Węgrzech." to false, "Powstanie dekabrystów w Rosji." to false))
            addQuestion(18, "Co to parlament frankfurcki?", "normalny",
                listOf("Ogólnoniemiecki parlament zwołany w celu zjednoczenia Niemiec.," to true, "To Rada miasta Frankfurt." to false, "To niemiecki odpowiednik senatu." to false, "To niemiecki sejm, który uchwalił Wiosnę Ludów." to false))
            addQuestion(18, "Skutki Wiosny Ludów to...?", "normalny",
                listOf("Początkowo rządzący zgodzili się z rewolucjonistami, ostatecznie jednak przywrócili stary ład." to true, "Upadek wszystkich rządów w Europie." to false, "Reolucjoniści doszli do władzy." to false, "Władza przeszła w ręce robotników." to false))
            addQuestion(18, "Jakie były skutki Wiosny Ludów we Prusach?", "normalny",
                listOf("Król wycofał się z ustępstw, dał wolność słowa i religijną" to true, "Król przystał na wszystkie ustępstwa." to false, "Król abdykował, Francja stała się republiką." to false, "Powrócił Napoleon Bonaparte do władzy." to false))
            addQuestion(18, "Jakie były skutki Wiosny Ludów w Austrii?", "normalny",
                listOf("Cesarz abdykował po wycofaniu się z reformy uwłaszczenia chłopów, nowy cesarz Franciszek Józef przejął całkowity wpływ na rząd." to true, "Austria stała się republiką." to false, "Austria dała niezależność Węgrom." to false, "Rewolucjoniści zabili cesarza, ogłosili własny rząd." to false))

            addQuestion(18, "Podaj datę 100 dni Napoleona.", "trudny",
                listOf("III-VI 1815." to true, "III-VI 1814." to false, "II-V 1815." to false, "II-V 1814." to false))
            addQuestion(18, "Kim był Aleksaner I?", "trudny",
                listOf("Carem Rosji." to true, "Królem Hiszpanii." to false, "Cesarzem Austrii." to false, "Dowódcą w bitwie pod Waterloo." to false))
            addQuestion(18, "Kim był Charles Talleyrand?.", "trudny",
                listOf("Dawny biskup, dyplomata broniący interesów Francji na kongresie wiedeńskim." to true, "Jeden z głównodowodzących w bitwie pod Waterloo." to false, "Reprezentant Austrii na kongresie wiedeńskim." to false, "Dyplomata rosyjski, pomagał carowi." to false))
            addQuestion(18, "Kim był Klemens von Metternich?", "trudny",
                listOf("Austriackim dyplomatą na kongresie wiedeńskim." to true, "Organizatorem kongresu wiedeńskiego." to false, "Reprezentantem Rosji na Kongresie wiedeńskim." to false, "Przedstawicielem Prus na kongresie wiedeńskim." to false))
            addQuestion(18, "Kto był głównym dyplomatą Austrii na kongresie wiedeńskim?", "trudny",
                listOf("Klemens von Metternich" to true, "Charles de Talleyrand" to false, "Robert Castlereagh" to false, "Aleksander I" to false))
            addQuestion(18, "Jakie wydarzenie zakończyło sto dni Napoleona?", "trudny",
                listOf("Bitwa pod Austerlitz" to false, "Bitwa pod Waterloo" to true, "Bitwa pod Lipskiem" to false, "Bitwa pod Ligny" to false))
            addQuestion(18, "Podaj rok skonstruowania silnika elektrycznego.", "trudny",
                listOf("1831." to true, "1837." to false, "1814." to false, "1815." to false))
            addQuestion(18, "Podaj rok skonstruowania telegrafu.", "trudny",
                listOf("1837." to true, "1831." to false, "1814." to false, "1815." to false))
            addQuestion(18, "Kim był Michael Faraday?", "trudny",
                listOf("Fizykiem, którego odkrycia przyczyniły się do skonstruowania silnika elektrycznego." to true, "Angielskim konstruktorem maszyny parowej." to false, "Wynalazcą telegrafu." to false, "Wynalazcją żarówki." to false))

            addQuestion(18, "Kim był Robert Stewart Castlereagh?", "ekspert",
                listOf("Ministrem spraw zagranicznych Wielkiej Brytanii." to true, "Reprezentantem Francji na kongresie wiedeńskim." to false, "Królem Wielkiej Brytanii." to false, "Ministrem spraw zagranicznych Francji." to false))
            addQuestion(18, "Kim był Franciszek I?", "ekspert",
                listOf("Cesarzem Austrii." to true, "Królem Wielkiej Brytanii." to false, "Królem Hiszpanii." to false, "Carem Rosji." to false))
            addQuestion(18, "Kim był Fryderyk Wilhelm III?", "ekspert",
                listOf("Królem Prus." to true, "Cesarzem Austrii." to false, "Królem Wielkiej Brytanii." to false, "Carem Rosji." to false))
            addQuestion(18, "W oparciu o jakie zasady stworrzono łod wiedeński?", "ekspert",
                listOf("Te zasady to: restauracja, legitymizm, równowaga europejska." to true, "Te zasady to: równość, wolność, braterstwo." to false, "Te zasady to: jeden za wszystkich, wszyscy za jednego." to false, "Te zasady to: zdanie silniejszego jest ważniejsze, państwo słabe słucha silniejszego." to false))
            addQuestion(18, "Jaki był cel Świętego Przymierza?", "ekspert",
                listOf("Zachowanie ustaleń kongresu wiedeńskiego, zwalczanie rewolucji i powstań." to true, "Ograniczyć mocarstwowość Rosji." to false, "Przejąć ziemie Polski." to false, "Przeciwstawienie się Włochom.." to false))
            addQuestion(18, "Rola konresu wiedeńskiego w dziejach Europy?", "ekspert",
                listOf("ustalony nowy ład trwał aż do I wojny światowej." to true, "Ustalony ład był nietrwały, trwał tylko 10 lat." to false, "W Polsce nastąpił rozwój gospodarczy." to false, "Nowy ład nie sprawdził się, bo trwał tylko 5 lat." to false))
            addQuestion(18, "Co uchwalono podczas kongresu wiedeńskiego w sprawie niewolnictwa?", "ekspert",
                listOf("Deklarację o zakazie handlu niewolnikami." to true, "Deklarację o zniesieniu niewolnictwa." to false, "Deklarację o nieużywaniu przemocy wobec niewolników." to false, "Nie była poruszana sprawa niewolnictwa." to false))
            addQuestion(18, "Podaj rok pierwszego telegraficznego połączenia Europy i Ameryki.", "ekspert",
                listOf("1866." to true, "1831." to false, "1837." to false, "1815." to false))
            addQuestion(18, "Kim był Thomas Newcomen?", "ekspert",
                listOf("Wynalazcą silnika parowego zastosowanego w górnictwie." to true, "Wynalazcą telegrafu." to false, "Konstruktorem parowozu." to false, "Wynalazca żarówki." to false))
            addQuestion(18, "Kim był Charles Wheatstone?", "ekspert",
                listOf("Naukowiec i wynalazca. Opracował pierwszy telegraf, który potem udoskonalił Morse." to true, "Wynalazca silnika parowego." to false, "Wynlazca żarówki." to false, "Konstruktor parowozu." to false))
            addQuestion(18, "Co to jest czartyzm?.", "ekspert",
                listOf("Radykalny ruch polityczny w Wielkiej Brytanii. Chciał poprawy ekonomicznej robotników." to true, "Masowy ruch w Rosji chciał abdykacji cara." to false, "Związek robotników w Prusach." to false, "To inna nazwa komunizmu." to false))
            addQuestion(18, "Podaj daty ruchu czartystów.", "ekspert",
                listOf("1836–1848" to true, "1831-1837" to false, "1837-1848" to false, "1815-1837" to false))
            addQuestion(18, "Rok wydania Manifestu komunistycznego.", "ekspert",
                listOf("1848" to true, "1831" to false, "1837" to false, "1845." to false))
            addQuestion(18, "Dekabryści to...?", "ekspert",
                listOf("Grupa rosyjskich rewolucjonistów próbująca doknać przewrotu 1 1825 r." to true, "To radykalna grupa robotników pruskich próbująca wymusić zmiany." to false, "To węgierscy powstańcy chcący niepodległości." to false, "To polscy radykalni komuniści." to false))
            addQuestion(18, "Daty antytureckiego powstania w Grecji (1821–1822), ", "ekspert",
                listOf("1821–1822" to true, "1837-1848" to false, "1825-1831" to false, "1815-1816" to false))
            addQuestion(18, "Podaj datę powstania dekabrystów.", "ekspert",
                listOf("1836–1848" to true, "XII 1825" to false, "II 1837" to false, "VII 1848" to false))
            addQuestion(18, "Józef Bem to...?", "ekspert",
                listOf("Polski generał, naczelny dowódca wojsk węgierskich podczas powstania" to true, "Główny myśliciel konserwatyzmu." to false, "Polski patriota, uczestnik powstania dekabrystów." to false, "Polski generał. Brał udział w wojnie krymskiej." to false))


            addQuestion(19, "Uwłaszczenie to...?", "łatwy",
                listOf("Wykup przez chłopów ziemi." to true, "Zabranie chłopom ziemi przez magnatów." to false, "Zabranie chłopom ziemi przez państwo." to false, "Sprzedaż ziemi przez chłopów." to false))
            addQuestion(19, "Rok powstania Królestwa Polskiego, Wielkiego Księstwa Poznańskiego i Wolnego Miasta Krakowa?", "łatwy",
                listOf("1815" to true, "1837" to false, "1825" to false, "1836" to false))
            addQuestion(19, "Rok nadania konstytucji Królestwu Polskiemu?", "łatwy",
                listOf("1815" to true, "1814" to false, "1825" to false, "1836" to false))
            addQuestion(19, "Piotr Wysocki to...?", "łatwy",
                listOf("Podporucznik. Stał na czele Sprzysiężenia Podchorążych." to true, "Generał. Przewodził Towarzystwu Filomatów." to false, "Chorąży. Przewodził Towarzystwu Filaretów." to false, "Dekabrysta." to false))
            addQuestion(19, "Data wybuchu powstania listopadowego...??", "łatwy",
                listOf("29/30 XI 1830" to true, "29/30 XI 1815." to false, "29/30 XI 1837" to false, "29/30 XI 1825" to false))
            addQuestion(19, "Data bitwy pod Olszynką Grochowską...?", "łatwy",
                listOf("II 1831" to true, "XII 1825" to false, "VI 1815" to false, "XI 1837" to false))
            addQuestion(19, "Data wojny polsko-rosyjskiej?", "łatwy",
                listOf("II–X 1831" to true, "VI-XII 1837" to false, "VI-XII 1825" to false, "II 1831" to false))
            addQuestion(19, "Kim był Józef Chłopicki?", "łatwy",
                listOf("Pierwszym dyktatorem powstania listopadowego." to true, "Generał w wojnie polsko-rosyjskiej." to false, "Dowodzący obroną Warszawy." to false, "Dowódca pod Olszynką Grochowską." to false))
            addQuestion(19, "Co znaczy emigracja?", "łatwy",
                listOf("Opuszczenie ojczyzny na dłużej lub na stałe." to true, "Przyjazd do kraju po długiej nieobecności." to false, "Przyjęcie obywateli innych państw." to false, "Wyjazd na wakacje do innego kraju." to false))
            addQuestion(19, "Wielka Emigracja to...??", "łatwy",
                listOf("Masowe wyjazdy Polaków z kraju z przyczyn politycznych." to true, "Masowe powroty Polaków do kraju." to false, "Ucieczka wielu Polaków na wieś z obawy przed represjami." to false, "Dokument nakazujący Polakom wyjazd do Rosji.." to false))
            addQuestion(19, "Data wybuchu powstania krakowskiego...?", "łatwy",
                listOf("21/22 II 1846" to true, "21/22 XII 1825" to false, "21/22 VI 1847" to false, "20/21 XI 1847" to false))
            addQuestion(19, "Józef Bem to...?", "łatwy",
                listOf("II 1831" to true, "Dowódca powstańców na Węgrzech." to false, "Pierwszy dyktator powstania listopadowego." to false, "Przewodniczący Towarzystwa Filaretów." to false))
            addQuestion(19, "W jakich zaborach doszło do powstań w 1848 r.?", "łatwy",
                listOf("W austriackim i pruskim." to true, "W austriackim i rosyjskim.." to false, "W rosyjskim i pruskim." to false, "W austriackim, rosyjskim i pruskim." to false))
            addQuestion(19, "Dwaj główni twórcy romantyzmu to...?", "łatwy",
                listOf("Mickiewicz i Słowacki." to true, "Mickiewicz i Chłopicki." to false, "Słowacki i Bem" to false, "Słowacki i Lelewel." to false))

            addQuestion(19, "Ziemie zabrane to...?", "normalny",
                listOf("część obszaru Rzeczpospolitej włączona do Rosji w wyniku rozbiorów." to true, "ziemie zagarnięte przez Prusy." to false, "To ziemie zabrane przez Austrię w III rozbiorze." to false, "To ziemie utracone na rzecz Czech." to false))
            addQuestion(19, "Data zniesienia pańszczyzny w zaborze austriackim...?", "normalny",
                listOf("1848" to true, "1825" to false, "1823" to false, "1837" to false))
            addQuestion(19, "Data reformy uwłaszczeniowej w Wielkim Księstwie Poznańskim?", "normalny",
                listOf("1823" to true, "1825" to false, "1815" to false, "1837" to false))
            addQuestion(19, "Mikołaj I objął władzę w roku...?", "normalny",
                listOf("1825" to true, "1823" to false, "1815" to false, "1837" to false))
            addQuestion(19, "Kaliszanie to...?", "normalny",
                listOf("Liberalna opozycja w Królestwie Polskim." to true, "Tajne stowarzyszenie." to false, "Zakon. Działał na rzecz wolności Polski." to false, "Inna nazwa Filaretów." to false))
            addQuestion(19, "Kim był Franciszek Ksawery Drucki-Lubecki?", "normalny",
                listOf("Ministerm skarbu Królestwa Polskiego." to true, "Przewodniczącym Towarzystwa Filomatów." to false, "Księdzem, działaczem gospodarczym." to false, "Dyktatorem powstania." to false))
            addQuestion(19, "Data zerwania unii z Rosją...?", "normalny",
                listOf("25 I 1831" to true, "17 X 1825" to false, "24 VI 1815" to false, "17 XI 1837" to false))
            addQuestion(19, "Data bitwy o Warszawę...?", "normalny",
                listOf("6–7 IX 1831" to true, "10-11 X 1823" to false, "6-8 VI 1815" to false, "10-11 XI 1837" to false))
            addQuestion(19, "Kim był wielki książę Konstanty?", "normalny",
                listOf("Brat cara, sprawował rządy w Królestwie Polskim." to true, "Ministrem skarbu." to false, "Przewodniczący Towarzystwa Przyjaciół Nauk" to false, "Ministrem szkolnictwa." to false))
            addQuestion(19, "W jakiej dziedzinie zasłużył się Adam Czartoryski?", "normalny",
                listOf("W oświacie." to true, "W wojskowości." to false, "W przemyśle." to false, "W finansach." to false))
            addQuestion(19, "Emisariusz to...?", "normalny",
                listOf("Tajny wysłannik. Miał zebrać informacje o aktualnej sytuacji." to true, "Posłaniec wojskowy z rozkazami do dowódców." to false, "Powstaniec." to false, "Skazaniec." to false))
            addQuestion(19, "Rabacja to...?", "normalny",
                listOf("Ataki chłopskie na dwory szlacheckie w Galicji." to true, "Rekompensata za straty wojenne." to false, "Powstanie przeciwko zaborcom." to false, "Wystąpienie szlachty przeciw królowi." to false))
            addQuestion(19, "Kontrubucja to...?", "normalny",
                listOf("Danina płacona państwu zwycięskiemu przez pokonane." to true, "Kontratak wojsk." to false, "Zbiór praw danego państwa." to false, "Pobór przymusowy do wojska." to false))
            addQuestion(19, "Kim był Jakub Szekla?", "normalny",
                listOf("Jeden z przywódców chłopów w czasie rabacji." to true, "Dyktator powstania listopadowego" to false, "Minister skarbu" to false, "Działacz emigracyjny." to false))
            addQuestion(19, "Kim był Edward Dembowski?", "normalny",
                listOf("Jednym z organizatorów powstania krakowskiego. " to true, "Przywódca chłopów w trakcie rabacji." to false, "Przewodniczył kaliszanom." to false, "Dyktator powstania listopadowego." to false))
            addQuestion(19, "Racjonalizm to...?", "normalny",
                listOf("Prąd w filozofii uznający rozum za kryterium prawdy." to true, "Prąd w poezjii na początku XIX w." to false, "Pogląd, że Polska jest mesjaszem." to false, "Pogląd, że trzeba się poddać losowi." to false))

            addQuestion(19, "Katorga to...?", "trudny",
                listOf("Kara - niewolnicza praca w kopalniach czy przy wyrębie tajgi" to true, "Praca na roli." to false, "Praca na rzecz szlachty." to false, "Ciężka droga na Syberię." to false))
            addQuestion(19, "Amnestia to..?", "trudny",
                listOf("Darowanie lub złagodzenie kary." to true, "Poddanie się." to false, "Ustąpienie władcy z tronu." to false, "Przymusowa praca." to false))
            addQuestion(19, "Data wprowadzenia Statutu organicznego?", "trudny",
                listOf("1832" to true, "1823" to false, "1815" to false, "1837" to false))
            addQuestion(19, "Data ogłoszenia stanu wojennego w Królestwie Polskim?", "trudny",
                listOf("1833" to true, "1837" to false, "1832" to false, "1839" to false))
            addQuestion(19, "Data likwidacji Rzeczpospolitej Krakowskiej?", "trudny",
                listOf("1846" to true, "1831" to false, "1833" to false, "1823" to false))
            addQuestion(19, "Szymon Konarski to..?", "trudny",
                listOf("Emisariusz, działacz Wielkiej Emigracji." to true, "Uczestnik rabacji." to false, "Poeta romantyzmu." to false, "Przywódca rabacji." to false))
            addQuestion(19, "Piotr Ściegienny to...?", "trudny",
                listOf("Ksiądz. Działacz wolnosciowy wśród chłopów." to true, "Przywódca powstania krakowskiego." to false, "Przywódca rabacji." to false, "Działacz Wielkiej Emigracji." to false))
            addQuestion(19, "Data Porozumienia w Jarosławcu?", "trudny",
                listOf("IV 1848" to true, "X 1823" to false, "8 VI 1831" to false, "XI 1837" to false))
            addQuestion(19, "Data bitwy pod Miłosławiem?", "trudny",
                listOf("IV 1848" to true, "X 1823" to false, "8 VI 1831" to false, "XI 1837" to false))
            addQuestion(19, "Gubernator Galicji, ogłosił uwłaszczenie chłopów -kto taki?", "trudny",
                listOf("Franz von Stadion." to true, "Szymon Konarski." to false, "Jakub Szela." to false, "Piotr Ściegienny." to false))
            addQuestion(19, "Kim był Wojciech Chrzanowski?", "trudny",
                listOf("Generałem, twórcą pierwszej mapy ziem polskich." to true, "Ksiądz i emisariusz" to false, "Poeta romantyzmu." to false, "Jeden z przywódców rabacji." to false))
            addQuestion(19, "Kim był Józef Dembiński?", "trudny",
                listOf("Generałęm. Jednym z przywódców powstania listopadowego." to true, "Polskim kartografem." to false, "Gubernatorem Galicji" to false, "Polskim filozofem." to false))

            addQuestion(19, "Data wprowadzenia obowiązku szkolnego w zaborze pruskim?", "ekspert",
                listOf("1825" to true, "1823" to false, "1815" to false, "1837" to false))
            addQuestion(19, "Data otwarcia Zakładu Narodowego im. Ossolińskich we Lwowie?", "ekspert",
                listOf("1817" to true, "1823" to false, "1825" to false, "1837" to false))
            addQuestion(19, "Data powstania Towarzystwa Kredytowego Ziemskiego?", "ekspert",
                listOf("1825" to true, "1823" to false, "1817" to false, "1837" to false))
            addQuestion(19, "Data wystąpienia kaliszan?", "ekspert",
                listOf("1820" to true, "1823" to false, "1825" to false, "1837" to false))
            addQuestion(19, "Który Minister szkolnictwa, przyczynił się do rozwoju edukacji w Królestwie Polskim?", "ekspert",
                listOf("Stansław Kostka Potocki." to true, "Wojciech Chrzanowski." to false, "Piotr Ściegienny." to false, "Stanisław Staszic." to false))
            addQuestion(19, "Data wprowadzenia obowiązku szkolnego w zaborze pruskim?", "ekspert",
                listOf("1825" to true, "1823" to false, "1815" to false, "1837" to false))
            addQuestion(19, "Tomasz Zan to...?", "ekspert",
                listOf("Poeta romantyczny, współzałożyciel Towarzystwa Filomatów." to true, "Twórca pierwszej mapy ziem polskich." to false, "Gubernator Galicji." to false, "1837" to false))
            addQuestion(19, "Data bitwy pod Stoczkiem?", "ekspert",
                listOf("1831" to true, "1823" to false, "1815" to false, "1837" to false))
            addQuestion(19, "Data bitew pod Iganiami i Boremlem?", "ekspert",
                listOf("IV 1831" to true, "1825" to false, "1815" to false, "1837" to false))
            addQuestion(19, "Data bitew pod Wawrem i Dębem Wielkim?", "ekspert",
                listOf("1831" to true, "1823" to false, "1815" to false, "1837" to false))


            addQuestion(20, "Wyjaśnij termin wojna secesyjna?", "łatwy",
                listOf("To konflikt między Stanami Zjednoczonymi a Konfederacją Stanów Ameryki." to true, "To wojna we Francji." to false, "To wojna polsko-rosyjska." to false, "Walki między chłopami a szlachtą w Galicji" to false))
            addQuestion(20, "Data wojny secesyjnej?", "łatwy",
                listOf("1861-1865" to true, "1823-1825" to false, "1815-1820" to false, "1855-1863" to false))
            addQuestion(20, "Kim był Abraham Lincoln?", "łatwy",
                listOf("Prezydentem Stanów Zjednoczonych." to true, "Dowódca konfederatów pod Gettysburgiem." to false, "Generałem." to false, "Senatorem, zwolennikiem niewolnictwa." to false))
            addQuestion(20, "Rok powstania Królestwa Włoch?", "łatwy",
                listOf("1861" to true, "1863" to false, "1865" to false, "1857" to false))
            addQuestion(20, "Data ogłoszenia powstania II Rzeszy Niemieckiej?", "łatwy",
                listOf("18 I 1871" to true, "15 I 1870" to false, "18 II 1881" to false, "15 II 1879" to false))
            addQuestion(20, "Kto to był Giuseppe Garibaldi?", "łatwy",
                listOf("Polityk, rewolucjonista włoski, przyczynił się do zjednoczenia Włoch." to true, "Premierem Królestwa Sardynii." to false, "Królem Zjednoczonych Włoch." to false, "Generałem, potem premierem." to false))
            addQuestion(20, "Karol Darwin to twórca teorii...?", "łatwy",
                listOf("Ewolucji." to true, "Rewolucji." to false, "Heliocentrycznej." to false, "Mesjanizmu." to false))
            addQuestion(20, "Królowa Wiktoria to władczyni...?", "łatwy",
                listOf("Wielkiej Brytanii" to true, "Włoch" to false, "Hiszpanii" to false, "Austrii" to false))

            addQuestion(20, "Data wyboru Abrahama Lincolna na prezydenta?", "normalny",
                listOf("1860" to true, "1863" to false, "1865" to false, "1857" to false))
            addQuestion(20, "Data wydania dekretu o zniesieniu niewolnictwa?", "normalny",
                listOf("1863" to true, "1861" to false, "1866" to false, "1857" to false))
            addQuestion(20, "Kim był Robert Lee?", "normalny",
                listOf("Generałem konfederatów." to true, "Prezydentem Stanów Zjednoczonych." to false, "Twórcą teorii ewolucji." to false, "Premierem Królestwa Sardynii." to false))
            addQuestion(20, "Kim był Ulysses Grant?", "normalny",
                listOf("Generał wojsk Północy." to true, "Generał. Poniósł klęskę pod Gettysburgiem." to false, "Włoski rewolucjonista." to false, "Naukowiec, odkrył promienie X." to false))
            addQuestion(20, "Kto wsparł wyprawę tysiąca czerwonych koszul?", "normalny",
                listOf("Giuseppe Garibaldi." to true, "Wiktor Emanuel II." to false, "Camillo Cavour" to false, "Robert Lee." to false))
            addQuestion(20, "Data wojny Prus z Austrią?", "normalny",
                listOf("1866" to true, "1863" to false, "1865" to false, "1857" to false))
            addQuestion(20, "Data wojny francusko-pruskiej?", "normalny",
                listOf("1870–1871" to true, "1863-1871" to false, "1871-1872" to false, "1867-1868" to false))
            addQuestion(20, "Kim był Wilhelm I?", "normalny",
                listOf("Królem Prus w latach 1861–1888." to true, "Cesarzem Austrii w latach 1861-1888." to false, "Królem Sardynii." to false, "Królem Zjednoczonych Włoch." to false))

            addQuestion(20, "Data secesji Karoliny Południowej?", "trudny",
                listOf("1860" to true, "1863" to false, "1865" to false, "1857" to false))
            addQuestion(20, "Data powstania Skonfederowanych Stanów Ameryki?", "trudny",
                listOf("1861" to true, "1865" to false, "1863" to false, "1860" to false))
            addQuestion(20, "Data bitew pod Magentą i Solferino?", "trudny",
                listOf("1859" to true, "1860" to false, "1869" to false, "1857" to false))
            addQuestion(20, "wojny Prus i Austrii z Danią?", "trudny",
                listOf("1864" to true, "1863" to false, "1865" to false, "1857" to false))
            addQuestion(20, "Data zawarcia pokoju we Frankfurcie nad Menem?", "trudny",
                listOf("1871" to true, "1870" to false, "1861" to false, "1881" to false))
            addQuestion(20, "Data bitwy pod Sadową?", "trudny",
                listOf("1866" to true, "18671" to false, "1865" to false, "1869" to false))
            addQuestion(20, "Data powstania Związku Północno–niemieckiego?", "trudny",
                listOf("1867" to true, "1863" to false, "1877" to false, "1868" to false))

            addQuestion(20, "Data bitwy pod Gettysburgiem?", "ekspert",
                listOf("VII 1863" to true, "VIII 1864" to false, "XII 1870" to false, "VI 1870" to false))
            addQuestion(20, "Data wprowadzenia zakazu przywozu niewolników do Stanów Zjednoczonych?", "ekspert",
                listOf("1808" to true, "1863" to false, "1870" to false, "1860" to false))
            addQuestion(20, "Data kapitulacji wojsk Konfederacji?", "ekspert",
                listOf("VI 1865" to true, "VI 1863" to false, "II 1865" to false, "II 1857" to false))
            addQuestion(20, "Data ataku na Fort Sumter?", "ekspert",
                listOf("IV 1861" to true, "VI 1865" to false, "VII 1863" to false, "IV 1866" to false))
            addQuestion(20, "Data powstania Niemieckiego Związku Celnego?", "ekspert",
                listOf("1834" to true, "1837" to false, "1864" to false, "1831" to false))
            addQuestion(20, "Data zawarcia sojuszu Piemontu z Francją?", "ekspert",
                listOf("1858" to true, "1861" to false, "1863" to false, "1857" to false))
            addQuestion(20, "Data wojny Piemontu z Austrią?", "ekspert",
                listOf("1859" to true, "1858" to false, "1865" to false, "1857" to false))
            addQuestion(20, "Data powstania Czerwonego Krzyża?", "ekspert",
                listOf("1863" to true, "1866" to false, "1865" to false, "1861" to false))
            addQuestion(20, "Kim był Wiktor Emanuel II?", "ekspert",
                listOf("Pierwszym królem Zjednoczonych Włoch." to true, "Rewolucjonistą włoskim." to false, "Królem Hiszpanii." to false, "Cesarzem Austrii." to false))
            addQuestion(20, "Kim był Jarosław Dąbrowski?", "ekspert",
                listOf("Bohaterem Paryskiej Komuny." to true, "Założycielem Czerwonego Krzyża." to false, "Generał w bitwie pod Sadową." to false, "Ministrem skarbu." to false))
            addQuestion(20, "Co znaczy solidaryzm społeczny?", "ekspert",
                listOf("Współdziałanie różnych grup dla dobra ogółu." to true, "Dbanie o swoją grupę społeczną." to false, "Zwiększone podatki." to false, "Składka na ojczyznę." to false))


            addQuestion(21, "Data manifestacji patriotycznych w Królestwie Polskim?", "łatwy",
                listOf("1861" to true, "1863" to false, "1865" to false, "1860" to false))
            addQuestion(21, "Kim był Karol Marcinkowski?", "łatwy",
                listOf("Działaczem społecznym, propagatorem pracy organicznej." to true, "Właścicielem fabryki maszyn rolniczych." to false, "Twórcą Towarzystwa Rolniczego." to false, "Namiestnikiem Galicji." to false))
            addQuestion(21, "Wyjaśnij termin branka.", "łatwy",
                listOf("To nadzwyczajny pobór do armii carskiej." to true, "To wywózka na Syberię." to false, "To inaczej przesiedlenie." to false, "To ciężkie więzienie." to false))
            addQuestion(21, "Data wybuchu powstania styczniowego?", "łatwy",
                listOf("22 I 1863" to true, "2 stycznia 1865" to false, "15 I 1865" to false, "18 I 18667" to false))
            addQuestion(21, "Data ukazu o uwłaszczeniu w Królestwie Polskim ?", "łatwy",
                listOf("III 1864" to true, "I 1863" to false, "II 1865" to false, "IV 1863" to false))
            addQuestion(21, "Kim był Romuald Traugutt?", "łatwy",
                listOf("Ostatnim dyktatorem powstania styczniowego." to true, "Właścicielem fabryki maszyn rolniczych." to false, "Gubernatorem." to false, "Dowódcą oddziału partyzanckiego." to false))
            addQuestion(21, "Data protestu dzieci we Wrześni?", "łatwy",
                listOf("1901" to true, "1900" to false, "1902" to false, "1910" to false))
            addQuestion(21, "Kim był Otton von Bismarck?", "łatwy",
                listOf("Premierem Prus i późniejszego Cesarstwa Niemieckiego" to true, "Arcybiskupem." to false, "Namiestnikiem galicyjskim." to false, "Cesarz austriacki." to false))

            addQuestion(21, "Kim byli czerwoni?", "normalny",
                listOf("Radykalnym obozem narodowym, dążącym do wywołania powstania, by odzyskać niepodległość." to true, "Umiarkowanym obozem. Niepodległość chcieli uzyskać drogą reform. " to false, "Radykałami włoskimi." to false, "Zwolennicy pracy organicznej." to false))
            addQuestion(21, "Data mianowania Aleksandra Wielopolskiego dyrektorem Komisji Wyznań i Oświecenia Publicznego?", "normalny",
                listOf("1861" to true, "1863" to false, "1865" to false, "1860" to false))
            addQuestion(21, "Kim był Dezydery Chłąpowski?", "normalny",
                listOf("Generałem, propagatorem nowoczesnych metod uprawy ziemi." to true, "Współzałożycielem Narodowej Demokracji." to false, "Propagatorem sółdzielczości." to false, "Założycielem PPS." to false))
            addQuestion(21, "Kim był Aleksander II?", "normalny",
                listOf("Carem Rosji." to true, "Cesarzem Austrii." to false, "Królem Włoch." to false, "Królem Hiszpanii." to false))
            addQuestion(21, "Kim byli kosynierzy?", "normalny",
                listOf("Żołnierzami piechoty. Uzbrojeni byli w kosy osadzone pionowo na drzewcu" to true, "Robotnikami walczącymi z caratem." to false, "Chłopami, którzy się wzbogacili." to false, "Osobami wywiezionymi na Syberię." to false))
            addQuestion(21, "Data ogłoszenia manifestu Tymczasowego Rządu Narodowego?", "normalny",
                listOf("22 I 1863" to true, "22 XII 1861" to false, "20 II 1865" to false, "15 I 1860" to false))
            addQuestion(21, "Data śmierci Romualda Traugutta?", "normalny",
                listOf("VIII 1864" to true, "I 1863" to false, "XII 1865" to false, "IV 1860" to false))
            addQuestion(21, "Kim był Aleksander Apuchtin?", "normalny",
                listOf("Rosyjskim kuratorem warszawskim." to true, "Namiestnikiem carskim." to false, "Rosyjskim generałem." to false, "Gubernatorem." to false))
            addQuestion(21, "Data rozpoczęcia rugów pruskich?", "normalny",
                listOf("1885" to true, "1863" to false, "1861" to false, "1881" to false))
            addQuestion(21, "Data strajku szkolnego w Wielkopolsce?", "normalny",
                listOf("1906" to true, "1885" to false, "1863" to false, "1906" to false))

            addQuestion(21, "Co to jest Bazar?", "trudny",
                listOf("To ośrodek życia społęcznego i gospodarczego w Poznaniu." to true, "To inna nazwa strajku szkolnego w Wielkopolsce." to false, "Przymusowy pobór do wojska carskiego." to false, "Nazwa tajnego ugrupowania." to false))
            addQuestion(21, "Data wprowadzenia stanu wojennego w Królestwie Polskim ?", "trudny",
                listOf("1861" to true, "1885" to false, "1863" to false, "1906" to false))
            addQuestion(21, "Kim był Andrzej Zamoyski?", "trudny",
                listOf("Prezesem Towarzystwa Rolniczego." to true, "Dyktatorem powstania styczniowego." to false, "Namiestnikiem carskim." to false, "Kuratorem warszawskim." to false))
            addQuestion(21, "Kim był Marian Langiewicz?", "trudny",
                listOf("Zwolennikiem białych, dyktatorem powstania." to true, "Radykałęm, zwolennikiem czerwonych" to false, "Generałęm, gubernatorem carskim." to false, "Namiestnikiem carskim." to false))
            addQuestion(21, "Kim był Teodor Berg?", "trudny",
                listOf("Gumernatorem." to true, "Ostatnim dyktatorem powstania." to false, "Ministrem edukacji" to false, "Pomysłodawcą bezwzględnej rusyfikacji." to false))
            addQuestion(21, "Data powstania Szkoły Głównej Warszawskiej?", "trudny",
                listOf("1862" to true, "1885" to false, "1863" to false, "1906" to false))
            addQuestion(21, "Data aresztowania Romualda Traugutta?", "trudny",
                listOf("IV 1864" to true, "I 1863" to false, "III 1861" to false, "XII 1885" to false))
            addQuestion(21, "Ki był Michaił Murawjow?", "trudny",
                listOf("Gubernatorem Litwy i Białorusi." to true, "Namiestnikiem carskim." to false, "Pomysłodawcą tajnych kompletów." to false, "Założył Uniwersytet Latający." to false))
            addQuestion(21, "Cel powstania Komisji Kolonizacyjnej?", "trudny",
                listOf("Wzmożenie osadnictwa Niemców na ziemiach polskich." to true, "Kolonizację zamorskich krajów." to false, "Kolonizację Afryki." to false, "Propagowanie osadnictwa w Ameryce." to false))
            addQuestion(21, "Co to jest Hakata?", "trudny",
                listOf("Niemiecki Związek Kresów Wschodnich." to true, "Tajna organizacja czerwonych." to false, "Skupisko Polaków na emigracji." to false, "Stowarzyszenie spiskowców." to false))

            addQuestion(21, "Data powstania Bazaru?", "ekspert",
                listOf("1841" to true, "1863" to false, "1861" to false, "1835" to false))
            addQuestion(21, "Data założenia Towarzystwa Rolniczego ?", "ekspert",
                listOf("1858" to true, "1859" to false, "1861" to false, "1864" to false))
            addQuestion(21, "Kim był Agenor Gołuchowski?", "ekspert",
                listOf("Działaczem politcznym w Galicji, namiestnikiem." to true, "Premierem rządu wiedeńskiego." to false, "Ministrem skarbu." to false, "Ministrem Edukacji." to false))
            addQuestion(21, "Wyjaśnij termin żuawi śmierci.", "ekspert",
                listOf("To oddział wojskowy powstania styczniowego na wzór francuskiego żuawi." to true, "To wojskowi ścigający dezerterów." to false, "To kosynierzy." to false, "To cele śmierci." to false))
            addQuestion(21, "Data powołania A. Wielopolskiego naczelnikiem Rządu Cywilnego?", "ekspert",
                listOf("1862" to true, "I 1863" to false, "III 1864" to false, "XII 1885" to false))
            addQuestion(21, "Data aresztowania Romualda Traugutta?", "ekspert",
                listOf("IV 1864" to true, "I 1863" to false, "III 1861" to false, "XII 1885" to false))
            addQuestion(21, "Kim był Hauke-Bosak?", "ekspert",
                listOf("Generałem, dowódcą partyzantów." to true, "Pomysłodawca tajnych kompletów." to false, "Namiestnikiem." to false, "Dyktatorem powstania." to false))
            addQuestion(21, "Data powstania zabajkalskiego?", "ekspert",
                listOf("1866" to true, "1863" to false, "1869" to false, "1864" to false))
            addQuestion(21, "Data ogłoszenia tzw. noweli osadniczej?", "ekspert",
                listOf("1904" to true, "1905" to false, "1910" to false, "1885" to false))
            addQuestion(21, "Data wprowadzenia ustawy kagańcowej?", "ekspert",
                listOf("1908" to true, "1905" to false, "1901" to false, "1904" to false))

            addQuestion(22, "Co to jest Trójprzymierze?", "łatwy",
                listOf("Sojusz Włoch, Niemiec i Austro-Węgier." to true, "Sojusz Włoch, Hiszpanii i Wielkiej Brytanii." to false, "Sojusz Rosji, Hiszpanii i Francji." to false, "Sojusz Austro-Węgier i Rosji." to false))
            addQuestion(22, "Wyjaśnij termin państwa centralne", "łatwy",
                listOf("To państwa należące do trójprzymierza." to true, "To Niemcy, Włochy i Hiszpania." to false, "To Austro-Węgry i Hiszpania." to false, "To inaczej centralna Europa." to false))
            addQuestion(22, "Co to jest ententa?", "łatwy",
                listOf("To trójporozumienie." to true, "To trójprzymierze." to false, "To Wielka Brytania." to false, "To Niemcy." to false))
            addQuestion(22, "Co to jest aneksja?", "łatwy",
                listOf("Podporządkowanie części innego państwa." to true, "To przejęcie innego państwa." to false, "To zawarcie pokoju." to false, "To poddanie się." to false))
            addQuestion(22, "Data powstania trójporozumienia?", "łatwy",
                listOf("1907" to true, "1905" to false, "1882" to false, "1894" to false))
            addQuestion(22, "Data powstania trójprzymierza?", "łatwy",
                listOf("1882" to true, "1905" to false, "1907" to false, "1904" to false))
            addQuestion(22, "Co nazywano Wielką Wojną?", "łatwy",
                listOf("I wojnę światową." to true, "Wojnę Austro-Węgier i Serbii." to false, "Wojnę Niemców z Francją." to false, "Wojnę Rosji z Japonią." to false))
            addQuestion(22, "Daty I wojny światowej?", "łatwy",
                listOf("1914-1918" to true, "1914-1920" to false, "1918-1920" to false, "1916-1920" to false))
            addQuestion(22, "Gdzie Niemcy podpisały kapitulację?", "łatwy",
                listOf("W Compiegne." to true, "W Berlinie." to false, "W Paryżu." to false, "W Lyonie." to false))
            addQuestion(22, "Data Podpisania kapitulacji przez Niemcy?", "łatwy",
                listOf("11 XI 1918" to true, "11 XI 1920" to false, "1 IX 1918" to false, "1 IX 1914" to false))

            addQuestion(22, "Wyjaśnij termin kocioł bałkański?", "normalny",
                listOf("To zapalna na tle narodowościowym i religijnym część Bałkanów." to true, "To Serbia i Bośnia." to false, "To miasto w Serbii." to false, "To wojna Austro-Węgier z Rosją." to false))
            addQuestion(22, "Daty wojny rosyjsko–japońskiej?", "normalny",
                listOf("1904–1905" to true, "1914-915" to false, "1918-1919" to false, "1901-1902" to false))
            addQuestion(22, "Data I wojny bałkańskiej?", "normalny",
                listOf("1912" to true, "1914" to false, "1918" to false, "1913" to false))
            addQuestion(22, "Data II wojny bałkańskiej?", "normalny",
                listOf("1913" to true, "1914" to false, "1918" to false, "1912" to false))
            addQuestion(22, "Wojna błyskaiczna to?", "normalny",
                listOf("Szybkie pokonanie wroga, by siły skierować przeciw kolejnemu." to true, "Trwająca 3 dni." to false, "Trwająca tydzień." to false, "Pokonanie wroga w jeden dzień" to false))
            addQuestion(22, "Data zamachu w Sarajewie?", "normalny",
                listOf("28 VI 1914" to true, "26 VI 1913" to false, "20 V 1918" to false, "19 V 1914" to false))
            addQuestion(22, "Data przyłączenia się Włoch do ententy?", "normalny",
                listOf("1915" to true, "1914" to false, "1918" to false, "1916" to false))
            addQuestion(22, "Data ogłoszenia nieograniczonej wojny podwodnej?", "normalny",
                listOf("1917" to true, "1916" to false, "1915" to false, "1914" to false))

            addQuestion(22, "Data podpisania układu rosyjsko–francuskiego?", "trudny",
                listOf("1892" to true, "1898" to false, "1882" to false, "1902" to false))
            addQuestion(22, "Data podpisania porozumienia francusko–brytyjskiego?", "trudny",
                listOf("1904" to true, "1892" to false, "1907" to false, "1914" to false))
            addQuestion(22, "Data podpisania porozumienia rosyjsko–brytyjskiego?", "trudny",
                listOf("1907" to true, "1904" to false, "1892" to false, "1902" to false))
            addQuestion(22, "Data bitwy pod Cuszimą?", "trudny",
                listOf("1905" to true, "1907" to false, "1904" to false, "1906" to false))
            addQuestion(22, "Co to jest U-Boot?", "trudny",
                listOf("To niemiecki okręt podwodny." to true, "To specjalny karabin maszynowy." to false, "To niemiecki pociąg pancerny." to false, "To niemiecki admirał." to false))
            addQuestion(22, "Data wypowiedzenia wojny Serbii przez Austro–Węgry?", "trudny",
                listOf("28 VII 1914" to true, "16 VIII 1916" to false, "11 XI 1915" to false, "11 XI 1918" to false))
            addQuestion(22, "Data bitwy nad Marną?", "trudny",
                listOf("IX 1914" to true, "I 1916" to false, "X 1915" to false, "XII 1914" to false))
            addQuestion(22, "Data bitwy pod Verdun?", "trudny",
                listOf("1916" to true, "1915" to false, "1917" to false, "1918" to false))
            addQuestion(22, "Data bitwy pod Ypres?", "trudny",
                listOf("1915" to true, "1914" to false, "1917" to false, "1918" to false))

            addQuestion(22, "Daty wojny rosyjsko–tureckiej?", "ekspert",
                listOf("1877–1878" to true, "1871-1872" to false, "1869-1871" to false, "1878-1879" to false))
            addQuestion(22, "Data kongresu berlińskiego?", "ekspert",
                listOf("1878" to true, "1877" to false, "1879" to false, "1891" to false))
            addQuestion(22, "Kiedy Austro-Węgry zaanektowały Bośnię i Hercegowinę??", "ekspert",
                listOf("1908" to true, "1914" to false, "1917" to false, "1918" to false))
            addQuestion(22, "Kiedy Japonia przyłączyła się do ententy?", "ekspert",
                listOf("1914" to true, "1916" to false, "1917" to false, "1915" to false))
            addQuestion(22, "Kiedy Turcja przyłączyła się do państw centralnych?", "ekspert",
                listOf("1914" to true, "1915" to false, "1917" to false, "1918" to false))
            addQuestion(22, "Kiedy była bitwa o Gallipoli?", "ekspert",
                listOf("1915" to true, "1914" to false, "1917" to false, "1918" to false))
            addQuestion(22, "Kiedy Bułgaria przyłączyła się do państw centralnych?", "ekspert",
                listOf("1915" to true, "1914" to false, "1917" to false, "1916" to false))
            addQuestion(22, "Kiedy doszło do zatopienia Lusitanii?", "ekspert",
                listOf("1915" to true, "1914" to false, "1917" to false, "1918" to false))
            addQuestion(22, "Kiedy była bitwa nad Sommą?", "ekspert",
                listOf("1916" to true, "1914" to false, "1917" to false, "1915" to false))
            addQuestion(22, "Kiedy doszło do bitwy jutlandzkiej?", "ekspert",
                listOf("1916" to true, "1914" to false, "1917" to false, "1915" to false))
            addQuestion(22, "Kiedy Grecja przyłączyła się do ententy?", "ekspert",
                listOf("1917" to true, "1914" to false, "1916" to false, "1915" to false))
            addQuestion(22, "Kiedy był bunt marynarzy w Kilonii ?", "ekspert",
                listOf("XI 1918" to true, "IX 1917" to false, "X 1918" to false, "XI 1916" to false))


            addQuestion(23, "Państwa Wielkiej Czwórki to?", "łatwy",
                listOf("Stany Zjednoczone, Wielka Brytania, Francja i Włochy." to true, "Rosja, Francja, Włochy, Niemcy." to false, "Francja, Hiszpania, Włochy, Niemcy." to false, "Włochy, Austria, Hiszpania, Belgia." to false))
            addQuestion(23, "Komu podlegały wolne miasta?", "łatwy",
                listOf("Lidze Narodów." to true, "Wielkiej Czwórce." to false, "Niemcom." to false, "Francji." to false))
            addQuestion(23, "Kiedy podpisano traktat wersalski?", "łatwy",
                listOf("28 VI 1919" to true, "9 V 1919" to false, "11 XI 1918" to false, "28 VI 1920" to false))
            addQuestion(23, "Kiedy miał miejsce marsz na Rzym??", "łatwy",
                listOf("1922" to true, "1930" to false, "1926" to false, "1923" to false))
            addQuestion(23, "Nazizm to?", "łatwy",
                listOf("Narodowy socjalizm." to true, "Faszyzm." to false, "Niemiecki nacjonalizm." to false, "Związek socjalistyczny." to false))
            addQuestion(23, "Kiedy Hitler został kanclerzem?", "łatwy",
                listOf("I 1933" to true, "II 1938" to false, "V 1936." to false, "X 1935" to false))
            addQuestion(23, "Data układu w Rapallo?", "łatwy",
                listOf("1922" to true, "1924" to false, "Niemcom." to false, "1930" to false))

            addQuestion(23, "Co to jest demilitaryzacja?", "normalny",
                listOf("Ograniczenie, albo likwidacja możliwości wojskowych państwa." to true, "Pozbawienie jeńców broni." to false, "Wyścig zbrojeń." to false, "Szybkie zbrojenie się państwa." to false))
            addQuestion(23, "Kiedy oradowała konferencja paryska?", "normalny",
                listOf("XI 1918–VI 1919" to true, "XI 1918-1920." to false, "X 1919-1920." to false, "XII 1917-1918" to false))
            addQuestion(23, "Kiedy zawarto układ w Locarno?", "normalny",
                listOf("1925" to true, "1928" to false, "1919" to false, "1918" to false))
            addQuestion(23, "Data czarnego czwartku?", "normalny",
                listOf("24 X 1929" to true, "24 XI 1930" to false, "14 X 1938" to false, "24 XI 1939" to false))
            addQuestion(23, "Kiedy wprowadzono New Deal?", "normalny",
                listOf("1933" to true, "1936" to false, "1938" to false, "1935" to false))
            addQuestion(23, "Kim był Franklin Delano Roosevelt?", "normalny",
                listOf("Prezydentem Stanów Zjednoczonych." to true, "Generałem niemieckim." to false, "Włoskim przywódcą." to false, "Ministrem spraw zagranicznych Niemiec." to false))
            addQuestion(23, "Co to były czarne koszule?", "normalny",
                listOf("Uzbrojone oddziały włoskich faszystów." to true, "To faszyści niemieccy." to false, "To naziści." to false, "To oddziały żołnierzy niemieckich.." to false))
            addQuestion(23, "Kiedy przyjęto ustawy norymberskie?", "normalny",
                listOf("1935" to true, "1945" to false, "1938" to false, "1929" to false))

            addQuestion(23, "Kiedy podpisano traktat z Austrią ?", "trudny",
                listOf("1919" to true, "1920" to false, "1918" to false, "1917" to false))
            addQuestion(23, "Kiedy podpisano traktat z Węgrami?", "trudny",
                listOf("1920" to true, "1919" to false, "1921" to false, "1923" to false))
            addQuestion(23, "Data podpisania traktatu z Turcją?", "trudny",
                listOf("1920" to true, "1918" to false, "1919" to false, "1922" to false))
            addQuestion(23, "Kim był Kemal Mustafa?", "trudny",
                listOf("Przywódcą rewolucji w Turcji" to true, "Tureckim sułtanem." to false, "Tureckim ministrem edukacji." to false, "Tureckim władcą." to false))
            addQuestion(23, "Kiedy Mussolini przejął funkcję premiera?", "trudny",
                listOf("1922" to true, "1932" to false, "1924" to false, "1929" to false))
            addQuestion(23, "Kiedy istniała Republika Weimarska?", "trudny",
                listOf("1919–1933" to true, "1920-1938" to false, "1938-1938" to false, "1929-1938" to false))
            addQuestion(23, "Kiedy powstały pakty laterańskie?", "trudny",
                listOf("1929" to true, "1933" to false, "1926" to false, "1931" to false))
            addQuestion(23, "Data nocy kryształowej?", "trudny",
                listOf("1938" to true, "1933" to false, "1936" to false, "1932" to false))

            addQuestion(23, "Data wstąpienia Niemiec do Ligi Narodów?", "ekspert",
                listOf("1926" to true, "1930" to false, "1936" to false, "1925" to false))
            addQuestion(23, "Data wstąpienia Rosji do Ligi Narodów?", "ekspert",
                listOf("1934" to true, "1933" to false, "1929" to false, "1931" to false))
            addQuestion(23, "Wyjaśnij termin korporacja?", "ekspert",
                listOf("Zrzeszenie osób realizujących te same cele" to true, "To przekupstwo." to false, "To inaczej partia nazistowska." to false, "To podpisanie traktatu pokojowego" to false))
            addQuestion(23, "Co to jest pucz?", "ekspert",
                listOf("To przewrót, zamach stanu." to true, "To oddanie władzy." to false, "To zbrojne przejęcie władcy innego państwa." to false, "To spisek." to false))
            addQuestion(23, "Kiedy powstały Związki Włoskich Kombatantów?", "ekspert",
                listOf("1919" to true, "1920" to false, "1918" to false, "1925" to false))
            addQuestion(23, "Kiedy był pucz monachijski?", "ekspert",
                listOf("1923" to true, "1933" to false, "1926" to false, "1928" to false))
            addQuestion(23, "Kiedy powstała Narodowa Partia Faszystowska?", "ekspert",
                listOf("1921" to true, "1933" to false, "1920" to false, "1918" to false))
            addQuestion(23, "Kiedy podpalono Reichstag?", "ekspert",
                listOf("II 1933" to true, "IV 1931" to false, "IX 1937" to false, "V 1932" to false))


            addQuestion(24, "Kiedy Rada Regencyjna przekazała władzę Piłsudskiemu?", "łatwy",
                listOf("11 XI 1918" to true, "1 IV 1931" to false, "9 IX 1920" to false, "8 V 1919" to false))
            addQuestion(24, "Kto kierował Komitetem Narodowym Polski?", "łatwy",
                listOf("Roman Dmowski" to true, "Józef Piłsudski" to false, "Ignacy Daszyński" to false, "Ignacy Paderewski" to false))
            addQuestion(24, "Czego dotyczy cud nad Wisłą?", "łatwy",
                listOf("Bitwy Warszawskiej." to true, "Bitwy pid Komorowem." to false, "Bitwy nadniemeńskiej" to false, "Walk w Radzyminie." to false))
            addQuestion(24, "Kiedy była Bitwa Warszawska?", "łatwy",
                listOf("15 VIII 1920" to true, "11 XI 1918" to false, "9 IX 1920" to false, "8 V 1919" to false))
            addQuestion(24, "Kiedy podpisano pokój w Rydze?", "łatwy",
                listOf("18 III 1921" to true, "15 VIII 1920" to false, "9 IX 1920" to false, "8 V 1919" to false))
            addQuestion(24, "Pierwszym marszałkiem Polski był...?", "łatwy",
                listOf("Józef Piłsudski" to true, "Roman Dmowski" to false, "Ignacy Daszyński" to false, "Ignacy Paderewski" to false))
            addQuestion(24, "Jednym z postanowień pokoju ryskiego było...?", "łatwy",
                listOf("Ustalenie granic Polski z Rosją Sowiecką." to true, "Ustalenie granic z Niemcami." to false, "Zapłata odszkodowań Polsce." to false, "Podpisanie aktu o nieagresji." to false))
            addQuestion(24, "Kiedy wybuchło powstanie wielkopolskie?", "łatwy",
                listOf("27 XII 1918" to true, "30 XI 1918" to false, "9 IX 1919" to false, "8 V 1920" to false))
            addQuestion(24, "Kiedy uchwalono konstytucję marcową?", "łatwy",
                listOf("17 III 1921" to true, "1 III 1920" to false, "8 II 1923" to false, "17 III 1919" to false))
            addQuestion(24, "Kim był Gabriel Narutowicz?", "łatwy",
                listOf("Pierwszym prezydentem wolnej Polski." to true, "Pierwszym premierem II Rzeczypospolitej." to false, "Pierwszym marszałkiem II Rzeczypospolitej." to false, "Pierwszym naczelnikiem państwa." to false))

            addQuestion(24, "Kiedy powołano rząd Moraczewskiego?", "normalny",
                listOf("18 XI 1918" to true, "17 III 1921" to false, "8 II 1923" to false, "17 III 1919" to false))
            addQuestion(24, "Kim był Ignacy Daszyński?", "normalny",
                listOf("Premierem Tymczasowego Rządu ludowej Republiki Polskiej." to true, "Prezydentem II Rzeczypospolitej." to false, "Marszałkiem." to false, "Naczelnikiem państwa." to false))
            addQuestion(24, "Kim Był Ignacy Paderewski?", "normalny",
                listOf("Premierem." to true, "Prezydentem." to false, "Marszałkiem." to false, "Generałem." to false))
            addQuestion(24, "Co oznaczała koncepcja inkorporacyjna?", "normalny",
                listOf("Stworzenie państwa jednolitego narodowościowo." to true, "Walkę z Rosją." to false, "Wchłonięcie Czech." to false, "Pokojowe przejęcie części ziem rosyjskich." to false))
            addQuestion(24, "Kiedy była bitwa nadniemeńska?", "normalny",
                listOf("22–28 IX 1920" to true, "1 III 1920" to false, "8 II 1923" to false, "17 III 1919" to false))
            addQuestion(24, "Kim był Lucjan Żeligowski?", "normalny",
                listOf("Generałem, który zorganizował tzw. bunt." to true, "1 III 1920" to false, "8 II 1923" to false, "17 III 1919" to false))
            addQuestion(24, "Kim był Wincenty Witos?", "normalny",
                listOf("Trzykrotnym premierem." to true, "Generałem." to false, "Marszałkiem." to false, "Prezydentem." to false))
            addQuestion(24, "Michaił Tuchaczewski to?", "normalny",
                listOf("Rosyjski generał w bitwie nadniemeńskiej" to true, "Ministrem wojny." to false, "Marszałkiem." to false, "Zabójcą Narutowicza." to false))
            addQuestion(24, "Kiedy opracowano plan Bitwy Warszawskiej?", "normalny",
                listOf("5/6 VIII 1920" to true, "1/2 III 1920" to false, "8/9 II 1923" to false, "17/18 III 1919" to false))
            addQuestion(24, "Kiedy był plebiscyt na Warmii i Mazurach?", "normalny",
                listOf("11 VII 1920" to true, "1 III 1921" to false, "8 II 1923" to false, "17 III 1919" to false))

            addQuestion(24, "Co to jest unifikacja?", "trudny",
                listOf("To ujednolicenie różnych systemów w jeden." to true, "To wprowadzenie jednego rządu." to false, "To rozbiór." to false, "To przeniesienie własności." to false))
            addQuestion(24, "Kiedy powstał Tymczasowy Rząd Ludowy Republiki Polskiej?", "trudny",
                listOf("7 XI 1918" to true, "11.XI 1918" to false, "8 II 1920" to false, "17 III 1919" to false))
            addQuestion(24, "Kiedy wydano dekret o powołaniu Tymczasowego Naczelnika Państwa?", "trudny",
                listOf("22 XI 1918" to true, "11 XI 1919" to false, "8 II 1920" to false, "17 III 1919" to false))
            addQuestion(24, "Kiedy powołano rząd Paderewskiego?", "trudny",
                listOf("I 1919" to true, "22 XI 1918" to false, "7 XI 1918" to false, "17 III 1919" to false))
            addQuestion(24, "Co to jest linia Curzona?", "trudny",
                listOf("Linia drugiego rozbioru." to true, "Linia umocnień wojskowych." to false, "Granica z Czechami." to false, "Granica z Niemcami." to false))
            addQuestion(24, "Data zawarcia układu z Petlurą...?", "trudny",
                listOf("IV 1920" to true, "III 1921" to false, "II 1923" to false, "III 1919" to false))
            addQuestion(24, "Kiedy powołano Radę Obrony Państwa?", "trudny",
                listOf("VII 1920" to true, "XI 1918" to false, "II 1923" to false, "I 1919" to false))
            addQuestion(24, "Kiedy włączono Litwę Środkową do Polski?", "trudny",
                listOf("III 1922" to true, "II 1921" to false, "VII 1920" to false, "III 1921" to false))
            addQuestion(24, "Kim był Tadeusz Rozwadowski?", "trudny",
                listOf("Szefem Sztabu Generalnego." to true, "Przywódcą ludowców." to false, "Wicepremierem." to false, "Premierem." to false))
            addQuestion(24, "Kto stał na czele Frontu Morges?", "trudny",
                listOf("Władysłąw Sikorski." to true, "Gabriel Narutowicz." to false, "Władysław Grabski." to false, "Tadeusz Rozwadowski." to false))

            addQuestion(24, "Data powstania Rady Narodowej Księstwa Cieszyńskiego:", "ekspert",
                listOf("19 X 1918" to true, "2 XI 1918" to false, "18 II 1923" to false, "15 I 1919" to false))
            addQuestion(24, "Kiedy powstała Polska Komisja Likwidacyjna Galicji i Śląska Cieszyńskiego ?", "ekspert",
                listOf("28 X 1918" to true, "2 IX 1918" to false, "17 II 1919" to false, "20 I 1919" to false))
            addQuestion(24, "Kiedy Piłsudski otrzymał władzę cywilną?", "ekspert",
                listOf("14 XI 1918" to true, "11 XI 1918" to false, "14 XII 1919" to false, "15 I 1919" to false))
            addQuestion(24, "Kiedy Tymczasowy Komitet Rządzący we Lwowie?", "ekspert",
                listOf("24 XI 1918" to true, "14 XI 1918" to false, "16 II 1919" to false, "1 I 1919" to false))
            addQuestion(24, "Kiedy utworzono Zachodnioukraińską Republikę Ludową?", "ekspert",
                listOf("X/XI 1918" to true, "XII 1919" to false, "XI 1920" to false, "I 1919" to false))
            addQuestion(24, "Kiedy była polska ofensywa wiosenna??", "ekspert",
                listOf("1919" to true, "1918" to false, "1922" to false, "1925" to false))
            addQuestion(24, "Data Tymczasowego Komitetu Rewolucyjnego Polsk:", "ekspert",
                listOf("VII 1920" to true, "XI 1918" to false, "II 1923" to false, "I 1919" to false))
            addQuestion(24, "Kiedy bolszewicy przekazali Wilno Litwinom?", "ekspert",
                listOf("VII 1920" to true, "XII 1921" to false, "II 1923" to false, "V 1923" to false))
            addQuestion(24, "Kiedy bya bitwa pod Komarowem?", "ekspert",
                listOf("31 VIII 1920" to true, "9 XI 1921" to false, "8 II 1920" to false, "12 VIII 1922" to false))
            addQuestion(24, "Kim był Julian Marchlewski?", "ekspert",
                listOf("Stał na czele Tymczasowego Komitetu Rewolucyjnego Polski." to true, "Szefem Sztabu Generalnego." to false, "Dowódcą w Bitwie Warszawskiej" to false, "Premierem" to false))



            addQuestion(25, "Co znaczy wojna błyskawiczna (Blitzkrieg)?", "łatwy",
                listOf("Nowa strategia wojenna - atak na wybranych odcinkach." to true, "Wojna w 3 dni." to false, "Użycie lotnictwa w wojnie." to false, "Wdarcie się na całej długości granicy." to false))
            addQuestion(25, "Kiedy Niemcy napadli na Polskę?", "łatwy",
                listOf("1 IX 1939" to true, "1 IX 1938" to false, "1 X 1939" to false, "9 V 1939" to false))
            addQuestion(25, "Kiedy Armia Czerwona wkroczyła do Polski?", "łatwy",
                listOf("17 IX 1939" to true, "3 IX 1939" to false, "11 XI 1939" to false, "1 IX 1939" to false))
            addQuestion(25, "Adolf Hitler był...?", "łatwy",
                listOf("Kanclerzem III Rzeszy." to true, "Niemieckim generałem." to false, "Majorem lotnictwa." to false, "Dowódcą Marynarki Wojennej Niemiec." to false))
            addQuestion(25, "Kim był Józef Stalin?", "łatwy",
                listOf("Sekretarzem Generalnym Komitetu Centralnego Wszechzwiązkowej Komunistycznej partii." to true, "Prezydentem Rosji." to false, "Kanclerzem Rosji." to false, "Ministerm obrony Rosji." to false))
            addQuestion(25, "Co oznacza termin alianci?", "łatwy",
                listOf("Sojusznicy, czyli państwa walczące z Hitlerem." to true, "Państwa walczące po stronie Hitlera." to false, "To inaczej przeciwnicy." to false, "To państwa neutralne." to false))
            addQuestion(25, "Co znaczy kolaboracja?", "łatwy",
                listOf("Współpraca z wrogiem." to true, "Walka z wrogiem." to false, "Użycie wszelkich środków do zdobycia celu." to false, "Atak znienacka." to false))
            addQuestion(25, "Kiedy Niemcy zaatakowały Francję?", "łatwy",
                listOf("10 V 1940" to true, "1 IX 1940" to false, "17 IX 1939" to false, "1 XII 1941" to false))
            addQuestion(25, "Podaj daty bitwy o Anglię.", "łatwy",
                listOf("VII–X 1940" to true, "X-XII 1939" to false, "V-VIII 1940" to false, "11 XI 1941." to false))
            addQuestion(25, "Kim był Winstona Churchil?", "łatwy",
                listOf("Brytyjskim premierem." to true, "Prezydentem Stanów Zjednoczonych." to false, "Brytyjskim ministrem spraw zagranicznych." to false, "Amerykańskim generałem" to false))

            addQuestion(25, "Co znaczy bitwa graniczna?", "normalny",
                listOf("17 IX 1939" to true, "Walka Polaków przez pierwsze 3 dni" to false, "Wojna tocząca się na granicy wschodniej." to false, "Walka wojsk pogranicznych" to false))
            addQuestion(25, "Co znaczy \"dziwna wojna\"?", "normalny",
                listOf("Brak działań wojennych ze strony Francji i Anglii mimo wypowiedzenia wojny Niemcom." to true, "Stosowanie terroru na ziemiach podbitych" to false, "Wojna podjazdowa." to false, "Walka partyzantów." to false))
            addQuestion(25, "Kiedy była bitwa o Westerplatte?", "normalny",
                listOf("1–7 IX 1939" to true, "3-10 IX 1939" to false, "11 IX 1939" to false, "10-15 X 1939" to false))
            addQuestion(25, "Kiedy Francja i Wielka Brytania wypowiedziały Niemcom wojnę?", "normalny",
                listOf("3 IX 1939" to true, "15 IX 1939" to false, "11 X 1939" to false, "1 IX 1939" to false))
            addQuestion(25, "Kiedy skapitulowała Warszawa?", "normalny",
                listOf("28 IX 1939" to true, "13 IX 1939" to false, "11 XI 1939" to false, "1 X 1939" to false))

            addQuestion(25, "Co nazywamy \"polskimi Termopilami\"?", "trudny",
                listOf("Bitwę pod Wizną." to true, "Bitwę pod Mławą." to false, "Bitwę w Borach Tucholskich." to false, "Bitwę pod Mokrą." to false))
            addQuestion(25, "Dta bitwy nad Bzurą?", "trudny",
                listOf("9–22 IX 1939" to true, "13-18 IX 1939" to false, "11-23 XI 1939" to false, "1 X 1939" to false))
            addQuestion(25, "Kiedy władze (państwowe i wojskowe) ewakuowały się?", "trudny",
                listOf("6/7 IX 1939" to true, "11/12 IX 1939" to false, "11/12 XI 1939" to false, "3/4 IX 1939" to false))
            addQuestion(25, "Kiedy było internownie władz polskich w Rumunii?", "trudny",
                listOf("17/18 IX 1939" to true, "13/14 IX 1939" to false, "19/22 XI 1939" to false, "1/2 X 1939" to false))

            addQuestion(25, "Co to były Grupy Specjalne (Einsatzgruppen)?", "ekspert",
                listOf("Specjalne oddziały niemieckiej policji i służby bezpieczeństwa." to true, "To jednostki SS." to false, "To najbliżsi ludzie Hitlera." to false, "Specjalna grupa naukowców." to false))
            addQuestion(25, "Kiedy była prowokacja gliwicka?", "ekspert",
                listOf("31 VIII 1939" to true, "1 IX 1939" to false, "18 VII 1939" to false, "31 V 1939" to false))
            addQuestion(25, "Podaj datę kapitulacji Helu.", "ekspert",
                listOf("2 X 1939" to true, "13 IX 1939" to false, "11 X 1939" to false, "1/2 IX 1939" to false))
            addQuestion(25, "Kim był Franciszek Dąbrowski?", "ekspert",
                listOf("Kapitanem, obrońcą Helu" to true, "Dowodził obroną Poczty Gdańskiej." to false, "Dowódcą w bitwie pod Wizną." to false, "Dowódcą w bitwie pod Kockiem." to false))
            addQuestion(25, "Kim był Józef Urung?", "ekspert",
                listOf("Kontradmirałem - dowodził obroną Helu." to true, "Dowódcą w bitwie w Borach Tucholskich." to false, "Dowódcą Korpusu Ochrony Pogranicza. " to false, "Prezydentem Warszawy." to false))



            addQuestion(26, "Kiedy była prowokacja gliwicka?", "łatwy",
                listOf("31 VIII 1939" to true, "1 IX 1939" to false, "18 VII 1939" to false, "31 V 1939" to false))



            questionDao.insertAll(questions)
            answerDao.insertAll(answers)
        }
    }
}
