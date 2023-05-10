package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Service {


    protected static List<Participant> participants;
    protected static List<Participant> groups;
    protected static List<Participant> successors;


    public Service(){
        participants = new ArrayList<>();
        successors = new ArrayList<>();
        groups = new ArrayList<>();
    }

    public abstract void myAbstractMethod();

}
