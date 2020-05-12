package data.lectura;

import utils.utils.hoursProblem;

import java.util.ArrayList;

public class Nodo {
    private ArrayList<Integer> durations;
    private ArrayList<String> startHours;
    private ArrayList<String> arriveHours;
    private int size = 0;

    public int getSize(){ return this.size; }

    public Nodo() {
        this.durations = new ArrayList<Integer>();
        this.startHours = new ArrayList<String>();
        this.arriveHours = new ArrayList<String>();
    }

    public Nodo(ArrayList<Integer> durations, ArrayList<String> startHours, ArrayList<String> arriveHours) {
        this.durations = durations;
        this.startHours = startHours;
        this.arriveHours = arriveHours;
        this.size = arriveHours.size();
    }

    public int addNewFlight(int duration, String startHour, String arriveHour){
        this.durations.add(duration);
        this.startHours.add(startHour);
        this.arriveHours.add(arriveHour);
        this.size += 1;
        return 1;
    }

    public int lowerCost(String startHourTravel){
        int minDuration = 999999999;
        int index = 1;
        int minutes = 0;
        for (int i = 0; i < this.getDurations().size(); i++) {
            minutes = this.getDurations().get(i) + hoursProblem.restHours(startHourTravel, this.getStartHours().get(i));
            if (minDuration > minutes){
                index = i;
                minDuration = minutes;
            }
        }
        return index;
    }

    public void printNodo(){
        for (int i = 0; i < this.getDurations().size(); i++) {
            System.out.println("    #" + i + " - Duration: " + this.getDurations().get(i).toString() + " - Start time: " + this.getStartHours().get(i) + " - Arrive Time: " + this.getArriveHours().get(i));
        }
        return;
    }


    public ArrayList<Integer> getDurations() {
        return durations;
    }

    public ArrayList<String> getStartHours() {
        return startHours;
    }

    public ArrayList<String> getArriveHours() {
        return arriveHours;
    }
}
