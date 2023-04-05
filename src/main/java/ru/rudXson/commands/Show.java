package ru.rudXson.commands;

import ru.rudXson.datatype.Flat;

import java.util.Iterator;
import java.util.PriorityQueue;

public class Show implements Command{
    private final PriorityQueue<Flat> flats;

    public Show (PriorityQueue<Flat> flats){
        this.flats = flats;
    }
    @Override
    public String getDescription(){
        return "show all collection items";
    }
    @Override
    public void execute(String args[]){
        Iterator<Flat> iter = flats.iterator();
        if(!iter.hasNext()){
            System.out.println("No elements to show!");
        }
        else{
            while (iter.hasNext()){
                Flat flat = iter.next();
                System.out.println(flat);
            }
        }

    }
}
