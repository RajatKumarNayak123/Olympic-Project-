import java.io.*;
import java.util.*;

public class Main {
    // constant defination
    private static final int ATHLETE_ID = 0;
    private static final int ATHLETE_NAME = 1;
    private static final int ATHLETE_SEX = 2;
    private static final int ATHLETE_AGE = 3;
    private static final int ATHLETE_HEIGHT = 4;
    private static final int ATHLETE_WEIGHT = 5;
    private static final int ATHLETE_TEAM = 6;
    private static final int ATHLETE_NOC = 7;
    private static final int ATHLETE_GAMES = 8;
    private static final int ATHLETE_YEAR = 9;
    private static final int ATHLETE_SEASON = 10;
    private static final int ATHLETE_CITY = 11;
    private static final int ATHLETE_SPORT = 12;
    private static final int ATHLETE_EVENT = 13;
    private static final int ATHLETE_MEDAL = 14;

    public static void main(String[] args) {

        List<Athlete> athleteEventList = athleteEvents();
         yearWiseGoldMedalCount(athleteEventList);
        goldMedalistsUnder30In1980(athleteEventList);
       eventWiseMedalTally1980(athleteEventList);
       footballGoldWinnersByYear(athleteEventList);
       topFemaleGoldMedalist(athleteEventList);
       athletesWithMultipleOlympics(athleteEventList);
    }
    public static List<Athlete> athleteEvents() {
        String file = "athlete_events.csv";
        List<Athlete> athleteEventList = new ArrayList<>();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file)))
        {
            String readsline;
            while ((readsline = bufferedReader.readLine()) != null) {
                String[] data = readsline.split(",");

                Athlete athleteEvent = new Athlete();
                athleteEvent.setID(data[ATHLETE_ID]);
                athleteEvent.setName(data[ATHLETE_NAME]);
                athleteEvent.setSex(data[ATHLETE_SEX]);
                athleteEvent.setAge(data[ATHLETE_AGE]);
                athleteEvent.setHeight(data[ATHLETE_HEIGHT]);
                athleteEvent.setWeight(data[ATHLETE_WEIGHT]);
                athleteEvent.setTeam(data[ATHLETE_TEAM]);
                athleteEvent.setNOC(data[ATHLETE_NOC]);
                athleteEvent.setGames(data[ATHLETE_GAMES]);
                athleteEvent.setYear(data[ATHLETE_YEAR]);
                athleteEvent.setSeason(data[ATHLETE_SEASON]);
                athleteEvent.setCity(data[ATHLETE_CITY]);
                athleteEvent.setSport(data[ATHLETE_SPORT]);
                athleteEvent.setEvent(data[ATHLETE_EVENT]);
                athleteEvent.setMedal(data[ATHLETE_MEDAL]);

                athleteEventList.add(athleteEvent);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return athleteEventList;
    }

    // 1.display Year Wise Number of Gold medals WonBy each player.
    public static void yearWiseGoldMedalCount(List<Athlete> athleteEventList) {

        Map<String, Map<String, Integer>> map = new TreeMap<>();

        for(Athlete athleteEvent : athleteEventList) {
            String year = athleteEvent.getYear();
            if(athleteEvent.getMedal().contains("Gold")) {
                if(!map.containsKey(year)) {
                    map.put(year, new HashMap<>());
                }
                Map<String, Integer> playerWonGold = map.get(year);
                playerWonGold.put(athleteEvent.getName(), playerWonGold.getOrDefault(athleteEvent.getName(), 0) + 1);
            }
        }

        for(Map.Entry<String, Map<String, Integer>> entry : map.entrySet()) {
            String year = entry.getKey();
            Map<String, Integer> dataValue = entry.getValue();
            System.out.println("Displaying year wise number of gold medals won by each player :");
            System.out.println("Year: "+year + " : ");
            for(Map.Entry<String, Integer> value : dataValue.entrySet()) {
                System.out.println(value.getKey() + " : " + value.getValue());
            }
            System.out.println();
        }
    }

    public static void goldMedalistsUnder30In1980(List<Athlete> athleteEventList) {

        Map<String,Integer> map =  new HashMap<>();

        for(Athlete events : athleteEventList){
            String year = events.getYear();
            String age = events.getAge();
            String name = events.getName();
            String medal = events.getMedal();

            if(year.contains("1980") && medal.contains("Gold") && Integer.parseInt(age) < 30){
                map.put(name, Integer.parseInt(age));
            }
         System.out.println("Displaying Athletes Who Won GoldMedal In 1980 And AgeIsLess Than 30Years: ");
            for(Map.Entry<String,Integer> entry : map.entrySet()){
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }
        }
    }

    public static void eventWiseMedalTally1980(List<Athlete> athleteEventList) {

        Map<String,Map<String,Integer>> map = new HashMap<>();

        for(Athlete atheleteEvent : athleteEventList){
            if(atheleteEvent.getYear().contains("1980")){
                String event = atheleteEvent.getEvent();
                String medal = atheleteEvent.getMedal();

                if(!event.equals("")){
                    map.putIfAbsent(event, new HashMap<>());
                    Map<String,Integer> medalWin = map.get(event);

                    if (medal != null && (medal.contains("Gold") || medal.contains("Silver") || medal.contains("Bronze"))){
                        medalWin.put(medal, medalWin.getOrDefault(medal, 0) + 1);
                    }

                    if (medalWin.isEmpty()) {
                        map.remove(event);
                    }
                }
            }
        }

        System.out.println("Event Wise Number Of Gold Silver Bronze Medals InY ear1980: ");
        for (Map.Entry<String, Map<String, Integer>> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    // 4.display Gold Winner Of Football Of EveryOlympic
    public static void footballGoldWinnersByYear(List<Athlete> athleteEventList) {

        Map<String, Set<String>> goldWinners = new TreeMap<>();

        for(Athlete athleteEvent : athleteEventList) {
            if(athleteEvent.getMedal().contains("Gold") && athleteEvent.getSport().contains("Football")) {
                if(!goldWinners.containsKey(athleteEvent.getYear())) {
                    goldWinners.put(athleteEvent.getYear(), new HashSet<>());
                }
                Set<String> goldWinner = goldWinners.get(athleteEvent.getYear());
                goldWinner.add(athleteEvent.getTeam());
            }
        }
     System.out.println("Displaying Gold Winner Of Football Of Every Olympic :");
        for(Map.Entry<String, Set<String>> entry : goldWinners.entrySet()) {
            System.out.println(entry.getKey());
            for(String set : entry.getValue()) {
                System.out.println(set);
            }
        }
    }
    //5.female Athlete Won Maximum Number Of Gold All Olympics
    public static void topFemaleGoldMedalist(List<Athlete> athleteEventList) {

        Map<String, Integer> femaleGoldMedals = new HashMap<>();

        for(Athlete athleteEvent : athleteEventList) {
            if(athleteEvent.getSex().contains("F") && athleteEvent.getMedal().contains("Gold")) {
                femaleGoldMedals.put(athleteEvent.getName(), femaleGoldMedals.getOrDefault(athleteEvent.getName(), 0) + 1);
            }
        }

        int maxGold = 0;
        String femaleName = "";

        for(Map.Entry<String, Integer> entry : femaleGoldMedals.entrySet()) {
            if(entry.getValue() > maxGold) {
                maxGold = entry.getValue();
                femaleName = entry.getKey();
            }
        }
        System.out.println("Female Athlete Won Maximum Number Of Gold All Olympics: ");
        System.out.println(femaleName + " : " + maxGold);
    }
   //6.name Of Athlete Participated In More Than Three Olympics
    public static void athletesWithMultipleOlympics(List<Athlete> athleteEventList) {
        Map<String ,Integer> map = new HashMap<>();

        for(Athlete events : athleteEventList) {
            String name = events.getName();

            map.put(name, map.getOrDefault(name, 0)+1);
        }
     System.out.println("Name Of Athlete Participated In More Than Three Olympics: ");
        for(Map.Entry<String,Integer> entry: map.entrySet()){
            if(entry.getValue() > 3){
                System.out.println(entry.getKey() + " " + entry.getValue());
            }
        }
    }


}
