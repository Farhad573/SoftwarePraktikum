package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Service {


    protected static List<Participant> participants;
    protected static List<Pair> pairs;
    protected static List<Participant> successors;


    public Service(){
        participants = new ArrayList<>();
        successors = new ArrayList<>();
        pairs = new ArrayList<>();
    }

    public abstract void myAbstractMethod();

}
