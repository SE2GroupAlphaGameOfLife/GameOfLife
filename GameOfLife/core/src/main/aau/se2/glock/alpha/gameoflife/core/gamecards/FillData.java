package aau.se2.glock.alpha.gameoflife.core.gamecards;

import java.util.ArrayList;
import java.util.List;

public class FillData {
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
    Event e45 = new Event(500,0,"Du fliegst mit einem Heißluftballon über den Grand Canyon. Du erhältst 500 LP.");
    Event e46 = new Event(0,20000,"Du lässt dich für deine Firma fotografieren und kommst auf die Titelseite eines Finanzmagazins. Du kassierst dafür € 20.000.");
    Event e47 = new Event(-150,0,"Du glaubst, dass du dich in der Welt gut auskennst, und suchst den Eifelturm auf einem Stadtplan von Rom. Du verlierst 150 LP.");
    Event e48 = new Event(-100,0,"Du kehrst von deiner Weltreise früher zurück, weil du deine Mama vermisst hast. Du verlierst 100 LP.");
    Event e49 = new Event(0,60000,"Du verkaufst deine Teddybär-Sammlung für € 60.000.");
    Event e50 = new Event(0,10000,"Du bist deiner Firma dein ganzes Leben lang treu geblieben. Dafür wirst du mit € 10.000 belohnt.");

    public List<Event> addData = new ArrayList<>();
    public List<Card> cardList = new ArrayList<>();

    public void fillEventList() {
        addData.add(e1);
        addData.add(e2);
        addData.add(e3);
        addData.add(e4);
        addData.add(e5);
        addData.add(e6);
        addData.add(e7);
        addData.add(e8);
        addData.add(e9);
        addData.add(e10);
        addData.add(e11);
        addData.add(e12);
        addData.add(e13);
        addData.add(e14);
        addData.add(e15);
        addData.add(e16);
        addData.add(e17);
        addData.add(e18);
        addData.add(e19);
        addData.add(e20);
        addData.add(e21);
        addData.add(e22);
        addData.add(e23);
        addData.add(e24);
        addData.add(e25);
        addData.add(e26);
        addData.add(e27);
        addData.add(e28);
        addData.add(e29);
        addData.add(e30);
        addData.add(e31);
        addData.add(e32);
        addData.add(e33);
        addData.add(e34);
        addData.add(e35);
        addData.add(e36);
        addData.add(e37);
        addData.add(e38);
        addData.add(e39);
        addData.add(e40);
        addData.add(e41);
        addData.add(e42);
        addData.add(e43);
        addData.add(e44);
        addData.add(e45);
        addData.add(e46);
        addData.add(e47);
        addData.add(e48);
        addData.add(e49);
        addData.add(e50);
    }

    private void fillPseudoCard(){
        for (int i = 0; i < addData.size()/4; i++) {
            cardList.add(new Card());
            cardList.get(i).fillEvents();
        }
    }

    public void fillCardList() {
        fillPseudoCard();

        int index = 0;
        for (int i = 0; i < cardList.size(); i++) {
            for (int j = 0; j < 4; j++) {
                cardList.get(i).addEvent(j, addData.get(index + j));
            }
            index = index + 4;
        }


    }

    public void cardToStack() {
        //s1.addCards(cardList);

    }


}


