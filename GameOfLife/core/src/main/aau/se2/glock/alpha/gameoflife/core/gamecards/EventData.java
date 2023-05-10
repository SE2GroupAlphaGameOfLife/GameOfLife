package aau.se2.glock.alpha.gameoflife.core.gamecards;

import java.util.ArrayList;
import java.util.List;

public class EventData {
    Event e1 = new Event(200, 0, "Du liest ein Lexikon von A bis Z durch. Weil du danach ziemlich schlau bist, erhältst du 200 LP.");
    Event e2 = new Event(200, 0, "Du malst nach einem Foto ein Familienporträt. Es ist kein Meisterwerk, aber allen gefällt's. Du erhältst 200 LP.");
    Event e3 = new Event(0, 20000, "Du übernachtest in enem skandinvischen Eishotel. Für deine Frostbeueln erhälst du € 20.000. Schmerzensgeld.");
    Event e4 = new Event(0, -12000, "Du bewirfst einen Star mit faulen Eiern. Zahle € 12.000 Strafe.");
    Event e5 = new Event(-100, 0, "Hola! Ein Freund bringt dir für den Urlaub etwas Spanisch bei, aber wenn du auf Mallorca nach dem Weg fragst, laufen alle vor dir davon. Du verlierst 100 LP.");
    Event e6 = new Event(0, 10000, "Du erfindest ein neues Zubehör für Handys und verkaufst das Patent für mickrige € 10.000. Dabei hättest du damit Millionen verdienen können!");
    Event e7 = new Event(0, 20000, "Du gehst mit deinen beiden dicken Nachbarinnen zum Bingo-Spielen und gewinnst € 20.000.");
    Event e8 = new Event(650, 0, "Wer hat den ganzen Kuchen gegessen? Na,du! Du wolltest den Kuchenessen-Weltrekord brechen! Du erhältst 650 LP.");
    Event e9 = new Event(-400, 0, "Du kannst deine ganze Schulklasse überreden,im Park Hundehaufen einzusammeln. Alle sind sauer auf dich und du verlierst 400 LP.");
    Event e10 = new Event(-275, 0, "Du leihst das Auto deines Chefs unf fährst gegen die Wand. Du kriegst Ärger mit deinem Chef und verlierst 275 LP.");
    Event e11 = new Event(500, 0, "Deine Freunde gehen Skifahren und du passt auf ihre Haustiere auf: eine Tarantel, ein Leguan und sechs Hunde. Du erhältst dafür 500 LP.");
    Event e12 = new Event(0, -35000, "Du beleidigst einen Prominenten und wirst verklagt. Du zahlst € 35.000.");
    Event e13 = new Event(0, 25000, "Du schreibst ein Buch mit dem Titel 'Socken und Sandalen - Sinn oder Unsinn?'. Es wird ein Bestseller und du verdienst € 25.000.");
    Event e14 = new Event(0, 10000, "Deine Website gewinnt einen großen Internet-Preis. Du erhältst € 10.000.");
    Event e15 = new Event(0, 50000, "Dank deines grünen Daumens gewinnst du den 'Gärtner des Jahres'-Preis und € 50.000.");
    Event e16 = new Event(250, 0, "Du bist gegen Schokolade allergisch! Was für ein Alptraum! Du verlierst 250 LP.");
    Event e17 = new Event(0, 45000, "Weg mit den Gummihandschuhen! Du erhältst € 45.000 Gewinn aus dem Verkauf deiner Idee für einen selbstreinigenden Ofen.");
    Event e18 = new Event(300, 0, "Du hilfst deine Großeltern dabei inn ihrem Wohnzimmer ein Internet-Cafe für Senioren einzurichten. Du erhältst 300 LP.");
    Event e19 = new Event(0, 60000, "Du wirst von einer Modelagentur entdeckt! Für deinen Vertrag erhältst du € 60.000.");
    Event e20 = new Event(0, -10000, "Du zahlst € 10.000 für die Ausbildung deines Hundes. Danach kann er weder Sitz noch Männchen machen.");
    Event e21 = new Event(0, -30000, "Jemand geht mit deiner Kreditkarte einkaufen! Leider kannst du den Diebstahl nicht beweisen und musst € 30.000 zahlen.");
    Event e22 = new Event(0, 15000, "Deine Schwester telefoniert andauernd mit ihrem Freund in Australien. Du zahlst ihre Telefonrechung von € 15.000.");
    Event e23 = new Event(0, 50000, "Herzlichen Glückwunsch! Du gewinnst in der Superhirn-Quizshow € 50.000.");
    Event e24 = new Event(-100, 0, "Du willst den Weltrekord in der Disziplin 'Niemals-die-Zähne-putzen' brechen. Dumme Idee! Du verlierst 100 LP.");
    Event e25 = new Event(0, 45000, "Du verkaufst ein Parfüm, das deinen Namen trägt. Es stinkt abscheulich, bringt dir aber € 45.000 Gewinn ein!");
    Event e26 = new Event(-400, 0, "Du bleichst die Haare deiner Mutter viel zu lange, so dass sie schließlich ausfallen. Du verlierst 400 LP.");
    Event e27 = new Event(-150, 0, "Du bist von einem Fernsehstar völlig besessen und verfolgst ihn überall hin. Du verlierst 150 LP.");
    Event e28 = new Event(0, 25000, "Du erfindest die 'Selbstwaschenden Socken' und verdienst € 25.000.");
    Event e29 = new Event(0, 30000, "Du reist für deinen Job durch die ganze Welt! Du bist zwar total fertig, verdienst aber in diesem Jahr einen Bonus von € 30.000.");
    Event e30 = new Event(0, -15000, "Verklage deinen lauten Nachbarn, weil sie Tag und Nacht Geige spielen. Du gwinnst den Prozess und kassierst € 15.000.");
    Event e31 = new Event(2000, 0, "Du trittst mit deiner Popband in einem Fußballstadion auf. Du erhältst 2.000 LP.");
    Event e32 = new Event(250, 0, "Du trittst in einer Fernsehshow als Experte auf. Du erhältst 250 LP.");
    Event e33 = new Event(0, 60000, "Du richtest eine verrückte neue Website ein, die mehr Leute anklicken als Facebook. Du verdienst damit € 60.000.");
    Event e34 = new Event(200, 0, "Du entschuldigst dich bei jemandem, den du in der Schule immer geärgert hast, mit einem Tanz. Du erhältst 200 LP.");
    Event e35 = new Event(850, 0, "Du baust im Garten eine umweltfreundliche Lehmhütte und wohnst ab sofort darin. du erhältst 850 LP.");
    Event e36 = new Event(-300,0,"Du findest heraus, wie man ein Auto kurzschließen kann und nutzt dein neues Wissen für den Diebstahl. Du verlierst 300 LP.");
    Event e37 = new Event(0,40000,"Ho ho ho! Du hilfst dem Weihnachtsmann und erfindest das am häufigsten gewünschte Weihnachtsgeschenk. Du gewinnst € 40.000.");
    Event e38 = new Event(-350,0,"Du hast deine Oma zum letzten Mal vor zwei Jahren besucht! Schäm dich! Du verlierst 350 LP.");
    Event e39 = new Event(-350,0,"Du nutzt jede Gelegenheit, in der Sonne zu liegen, um braun zu werden. Jetzt siehst du aus wie eine vertrocknete Kartoffel und verlierst 350 LP.");
    Event e40 = new Event(-100,0,"Du baust eine Maschiene, die atomatisch Aufsätze schreibt. Du bist der Held vieler Studenten, verlierst aber leider 100 LP.");
    Event e41 = new Event(0,25000,"Auf einer Geschäftsreise nach Japan wirst du wie ein VIP behandelt. Man schenkt dir einen Satz Golfschläger, den du für € 25.000 verkaufst.");
    Event e42 = new Event(0,40000,"Die Geschichte deiner Familie ist so spannend, dass du ein Buch darüber schreibst. Du verdienst € 40.000.");
    Event e43 = new Event(230,0,"Du bläst Glasskulpturen, um die sich die Kunstwelt reißt! Du erhältst 230 LP.");
    Event e44 = new Event(250,0,"Ein berühmter Chefkock ist bei dir zu Gast und lobt deine selbstgemachten Riesen-Bratwürstchen. Du erhältst 250 LP.");
    Event e45 = new Event(0,20000,"Du lässt dich für deine Firma fotografieren und kommst auf die Titelseite eines Finanzmagazins. Du kassierst dafür € 20.000.");
    Event e46 = new Event(0,10000,"Du bist deiner Firma dein ganzes Leben lang treu geblieben. Dafür wirst du mit € 10.000 belohnt.");
    Event e47 = new Event(500,0,"Du fliegst mit einem Heißluftballon über den Grand Canyon. Du erhältst 500 LP.");
    Event e48 = new Event(-150,0,"Du glaubst, dass du dich in der Welt gut auskennst, und suchst den Eifelturm auf einem Stadtplan von Rom. Du verlierst 150 LP.");
    Event e49 = new Event(-600,0,"Du knackst einen Sicherheitscode der NASA und fliegst ins Weltall. Dieser Ausflug ist ziemlich gefährlich und kostet Millionen. Du verlierst 600 LP.");
    Event e50 = new Event(-300,0,"Di stellst einer Gruppe Investoren deine Idee für de 'Männer-Business-Rock' vor. Leider lachen sie dich aus! Du verlierst 300 LP.");
    Event e51 = new Event(-100,0,"Du kehrst von deiner Weltreise früher zurück, weil du deine Mama vermisst hast. Du verlierst 100 LP.");
    Event e52 = new Event(0,60000,"Du verkaufst deine Teddybär-Sammlung für € 60.000.");
    Event e53 = new Event(500,0,"Du bist so schlau, dass du ein Transportsystem erfunden hast, durch das es nie wieder Verkehrsstau gibt. Du gewinnst 500 LP.");
    Event e54 = new Event(-200,0,"Du sitzt neben Götz George im Flugzeug. Leider ist der Flug sehr unruhig und dein Orangensaft kippt um. Peinlich! Du verlierst 200 LP.");
    Event e55 = new Event(250,0,"Du hilfst deinem besten Freund bei seinen Hochzeitsvorbereitungen, obwohl du Hochzeiten nicht leiden kannst. Du erhältst 250 LP.");
    Event e56 = new Event(700,0,"Du trittst im Zirkus als menschliche Kanonenkugel auf. Du fliegst hoch, landest sicher und gewinnst 700 LP.");
    Event e57 = new Event(100,0,"Du lässt dich als Code-Knacker ausbilden. Danach machst du eine Karriere beim BKA. Du gewinnst 100 LP.");
    Event e58 = new Event(0,90000,"Du arbeitest als Promi-Friseur und sorgst dafür, dass Brad Pitt noch besser aussieht. Du kassierst dafür € 90.000.");
    Event e59 = new Event(-500,0,"Du vergisst, die Kinder deiner Schwester von der Schule abzuholen. Das kann sie dir nicht verzeihen. Du verlierst 500 LP.");
    Event e60 = new Event(-500,0,"Weil du einen Kick brauchst, klaust du etwas in einer Boutique. Du verlierst 500 LP.");
    Event e61 = new Event(0,55000,"Du nimmst Unterricht im Comic-Zeichnen und erfindest die neue Mickey Maus. Du verdienst € 55.000.");
    Event e62 = new Event(0,25000,"Man gibt dir ein cooles neues Büro und einepersönliche Assistentin. Du erhältst einen Bonus von € 25.000.");
    Event e63 = new Event(0,45000,"Deine alte Großtante hinterlässt dir angeschlagenes Porzellan-Geschirr. Auf dem Flohmarkt verdienst du damit € 45.000. Guter Schnitt!");
    Event e64 = new Event (1000,0,"Du findest einen Beweis, dass es den Yeti wirklich gibt. Du erhältst 1.000 LP.");
    Event e65 = new Event(-300,0,"Du verbringst viel Zeit damit herauszufinden, ob Elvis Presley wirklich tot ist. Für diese Zeitverschwendung verlierst du 300 LP.");
    Event e66 = new Event(0,70000,"Du komponierst einen Hit und kassiert € 70.000. Gratuliere!");
    Event e67 = new Event(-600,0,"In der Zeitung bezeichnet man dich als 'Höllen-Nachbarn', weil du so laut bist. Du verlierst 600 LP.");
    Event e68 = new Event(0,15000,"Das Zimmer in deinem Lieblingshotel ist nicht frei, weil es völlig überbucht ist. Als Schadenersatz erhältst du € 15.000.");
    Event e69 = new Event(0,50000,"Eines deiner Gemälde schafft es in den Louvre. Bist du ein neuer Picasso? Du erhältst € 50.000.");
    Event e70 = new Event(0,25000,"Du züchtest eine neue, superkleine Hunderasse! Alle finden sie total süß. Mit dem ersten Wurf verdienst du € 25.000.");
    Event e71 = new Event(0,20000,"Du schreibst ein Kinderbuch nach den Geschichten, die dir deine Mama früher erzählt hat. Du verdienst damit € 20.000.");
    Event e72 = new Event(0,25000,"Du gewinnst ein Schlittenhunderennen quer durch Alaska. Du erhältst € 25.000.");
    Event e73 = new Event(0,55000,"Du untersuchst deinen Stammbaum und findest einen reichen Onkel, der dir Geld vererbt. Du kassierst € 55.000.");
    Event e74 = new Event(0,32000,"Du hast so viel gearbeitet, dass dich dein Chef mit einer Woche Las Vegas belohnt. Bei deiner Rückkehr bist du um € 32.000 reicher!");
    Event e75 = new Event(0,60000,"Euer Familiengeschäft hat mehr Erfolg, nachdem du für deine Mutter einen Laptop angeschafft hast. Du machst € 60.000 Gewinn.");
    Event e76 = new Event(850,0,"Dein Zierkürbis ist so groß geworden, dass er im Fernsehen gezeigt wird. Du gewinnst 850 LP.");
    Event e77 = new Event(-150,0,"Du meldest dich zu einem Kurs an, gehst aber nur zweimal hin. Du verlierst 150 LP.");
    Event e78 = new Event(0,30000,"Dein Arbeitgeber wird zum 'besten Chef der Welt' gewählt. Um das zu feiern, schenken sie dir € 30.000.");
    Event e79 = new Event(0,25000,"Dein Hund wird zum 'süßesten Hundilein' gewählt. Du erhältst € 25.000 Preisgeld.");
    Event e80 = new Event(0,50000,"Du wirst total Snowboard-süchtig und gewinnst die Weltmeisterschaft. Dafür kassierst du € 50.000.");
    Event e81 = new Event(150,0,"Du lernst deine Lieblings-Popband kennen und sie machen ein Foto mit dir. Du erhältst 150 LP.");
    Event e82 = new Event(-1000,0,"Du mobbst jemanden aus deiner Firma. Du verlierst 1.000 LP, weil so etwas gar nicht nett ist.");
    Event e83 = new Event(350,0,"Du schenkst deinem Vater zu Weihnachten einen Mungo. So ein Tierchen hatte er sich schon lange gewünscht! Du gewinnst 350 LP.");
    Event e84 = new Event(900,0,"Du sitzt in einer Fernsehshow in einer Badewanne voller Spinnen, um deine Angst davor zu überwinden. Du gewinnst 900 LP.");
    Event e85 = new Event(200,0,"Du kannst alle Hauptstädte der Welt aufsagen. Und zwar in alphabetischer Reihenfolge! Du gewinnst 200 LP.");
    Event e86 = new Event(-700,0,"Du verschickst eine private E-Mail aus Versehen an die ganze Firma! Wie peinlich! Du verlierst 700 LP.");
    Event e87 = new Event(500,0,"Du schaffst es, dass sich deine zwei alten Onkel nach 10 Jahren wieder versöhnen. Du erhältst 500 LP.");
    Event e88 = new Event(800,0,"Für einen Spendenaufruf läufst du in einem Gorilla-Kostüm quer durch die Sahara. Du gewinnst 800 LP.");
    Event e89 = new Event(400,0,"Du erfindest einen Fernseher, der über Gedanken ferngesteuert wird. Du erhältst 400 LP.");
    Event e90 = new Event(400,0,"Du stellst auf der Weihnachtsfeier deines Büros etwas sehr Dummes an. Dein Chef ist stinksauer und du verlierst 400 LP.");
    Event e91 = new Event(750,0,"Du nähst deiner Schwester ein wunderschönes Brautkleid. Du gewinnst 750 LP.");
    Event e92 = new Event(800,0,"Du gewinnst eine Reise nach Australien und gehst Tauchen. Du gewinnst 800 LP.");
    Event e93 = new Event(500,0,"Du gründest an der Universität einen Anti-Rauch-Club. Du erhältst 500 LP.");
    Event e94 = new Event(-300,0,"Du bist schlecht gelaunt und ärgerst deine Kollegen. Dein Chef zwingt dich, dich bei Ihnen zu entschuldigen. Du verlierst 300 LP.");
    Event e95 = new Event(700,0,"Du baust das Haus deines Nachbarn so um, dass er mit seinem Rollstuhl besser darin zurecht kommt. Du erhältst 700 LP.");
    Event e96 = new Event(850,0,"Du besteigst einen aktiven Vulkan und kannst dem Lavastrom ausweichen. Du erhältst 850 LP, du Draufgänger!");
    Event e97 = new Event(-350,0,"Du programmeirst den gemeinsten Computer-Virus, den es jemals gab. Du verlierst 350 LP.");
    Event e98 = new Event(0,30000,"Ein Babyfoto von dir wird für eine Werbeaktion verwendet. Du warst ja so süß! Du verdienst € 30.000.");
    Event e99 = new Event(0,70000,"Du kassierst € 70.000 Gewinn aus Aktien, die deine Oma für dich gekauft hat, als du noch ein Baby warst. Wie nett von ihr!");
    Event e100 = new Event(0,17000,"Einer deiner Schnappschüsse von einer Reise nach Peru gewinnt einen Fotowettbewerb. du erhältst € 17.000.");


    public List<Event> eventList = new ArrayList<>();
    public List<Card> cardList = new ArrayList<>();

    /**
     * Adds events to event list.
     */
    public void fillEventList() {
        eventList.add(e1);
        eventList.add(e2);
        eventList.add(e3);
        eventList.add(e4);
        eventList.add(e5);
        eventList.add(e6);
        eventList.add(e7);
        eventList.add(e8);
        eventList.add(e9);
        eventList.add(e10);
        eventList.add(e11);
        eventList.add(e12);
        eventList.add(e13);
        eventList.add(e14);
        eventList.add(e15);
        eventList.add(e16);
        eventList.add(e17);
        eventList.add(e18);
        eventList.add(e19);
        eventList.add(e20);
        eventList.add(e21);
        eventList.add(e22);
        eventList.add(e23);
        eventList.add(e24);
        eventList.add(e25);
        eventList.add(e26);
        eventList.add(e27);
        eventList.add(e28);
        eventList.add(e29);
        eventList.add(e30);
        eventList.add(e31);
        eventList.add(e32);
        eventList.add(e33);
        eventList.add(e34);
        eventList.add(e35);
        eventList.add(e36);
        eventList.add(e37);
        eventList.add(e38);
        eventList.add(e39);
        eventList.add(e40);
        eventList.add(e41);
        eventList.add(e42);
        eventList.add(e43);
        eventList.add(e44);
        eventList.add(e45);
        eventList.add(e46);
        eventList.add(e47);
        eventList.add(e48);
        eventList.add(e49);
        eventList.add(e50);
        eventList.add(e51);
        eventList.add(e52);
        eventList.add(e53);
        eventList.add(e54);
        eventList.add(e55);
        eventList.add(e56);
        eventList.add(e57);
        eventList.add(e58);
        eventList.add(e59);
        eventList.add(e60);
        eventList.add(e61);
        eventList.add(e62);
        eventList.add(e63);
        eventList.add(e64);
        eventList.add(e65);
        eventList.add(e66);
        eventList.add(e67);
        eventList.add(e68);
        eventList.add(e69);
        eventList.add(e70);
        eventList.add(e71);
        eventList.add(e72);
        eventList.add(e73);
        eventList.add(e74);
        eventList.add(e75);
        eventList.add(e76);
        eventList.add(e77);
        eventList.add(e78);
        eventList.add(e79);
        eventList.add(e80);
        eventList.add(e81);
        eventList.add(e82);
        eventList.add(e83);
        eventList.add(e84);
        eventList.add(e85);
        eventList.add(e86);
        eventList.add(e87);
        eventList.add(e88);
        eventList.add(e89);
        eventList.add(e90);
        eventList.add(e91);
        eventList.add(e92);
        eventList.add(e93);
        eventList.add(e94);
        eventList.add(e95);
        eventList.add(e96);
        eventList.add(e97);
        eventList.add(e98);
        eventList.add(e99);
        eventList.add(e100);
    }

    /**
     * not relevant for others.
     * fills up card list for further implementations.
     */
    private void fillPseudoCard(){
        for (int i = 0; i < eventList.size()/4; i++) {
            cardList.add(new Card());
            cardList.get(i).fillEvents();
        }
    }

    /**
     * Calls fillPseudoCard to fill up Array.
     */
    public void fillCardList() {
        fillPseudoCard();

        int index = 0;
        for (int i = 0; i < cardList.size(); i++) {
            for (int j = 0; j < 4; j++) {
                cardList.get(i).setEvent(j, eventList.get(index + j));
            }
            index = index + 4;
        }


    }

    public void cardToStack() {
        //s1.addCards(cardList);

    }


}


