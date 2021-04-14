package Requests;

import Model.Event;
import Model.Person;
import Model.User;

/**
 * info from user about load
 */
public class LoadRequest {

    private User[] users;
    private Person[] persons;
    private Event[] events;

    public LoadRequest(){
        users = null;
        persons = null;
        events = null;
    }

    public LoadRequest(User[] u, Person[] p, Event[] e) {
        users = u;
        persons = p;
        events = e;
    }


    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] u) {
        users = u;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] p) {
        persons = p;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] e) {
        events = e;
    }
}
