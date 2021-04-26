import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

/*
Myles Page
Cs 1450 002
Monday - Wednesday
Due 04-21-2021
Assignment 9
Linked list
*/


public class PageMylesAssignment9 {
	
	public static void main(String[] args) throws FileNotFoundException {
		
		//makes the lists
		EventLinkedList singleEventList = new EventLinkedList();
		DoubleLinkedList doublyEventList = new DoubleLinkedList(); 
		
		//read in original file
		File eventsFile = new File("OlympicEvents.txt");
		Scanner eventsRead = new Scanner(eventsFile);
		
		//while read in has more 
		while(eventsRead.hasNext()) {
			//creast the varables
			String venue = eventsRead.next();
			String type = eventsRead.next();
			int time = eventsRead.nextInt();
			String name = eventsRead.nextLine();
			//creats the event 
			Event event = new Event(venue,type,time,name);
			//add the event 
			singleEventList.addinOrder(event);
		}
		
		//print the list 
		singleEventList.printList();
		
		//reads in replacement 
		File replaceFile = new File("OlympicEventsReplace.txt");
		Scanner replaceRead = new Scanner(replaceFile);
		
		//while replace in has more 
		while(replaceRead.hasNext()) {
			//creats the variable
			String venue = replaceRead.next();
			String type = replaceRead.next();
			int time = replaceRead.nextInt();
			String name = replaceRead.nextLine();
			//creatsthe event 
			Event replaceEvent = new Event(venue,type,time,name);
			//add in the replace
			singleEventList.removeAndReplaceEvent(replaceEvent);
		}
		//prints the new list 
		singleEventList.printList();
		
		//placeholder
		int x = 0;
		//move from single to double
		while(EventLinkedList.getSize() > x) {
			doublyEventList.build(singleEventList, singleEventList.removeEvent());
			x++;
		}
		//print new list 
		doublyEventList.printNodes();
		
	}
}

class Event{
	//variables
	private String venue;
	private String type;
	private int time;
	private String name;
	
	//constructor
	public Event(String venue, String type, int time, String name) {
		this.venue = venue;
		this.type = type;
		this.time = time;
		this.name = name;
	}
	
	//getters
	public String getVenue() {
		return venue;
	}
	public String getType() {
		return type;
	}
	public int getTime() {
		return time;
	}
	public String getName() {
		return name;
	}
	
	//to string 
	public String toString() {
		return String.format("%-4s\t%-10s\t%-20s\t%s", this.time, this.type, this.name, this.venue);
	}
	
	//compare to 
	public int compareTo(Event otherEvent) {
		if (this.time < otherEvent.time) {
			return -1;
		}
		else if (this.time > otherEvent.time) {
			return 1;
		}
		else {
			return 0;
		}
	}
}


class EventLinkedList{
	//variable
	private Node head;
	private static int size;
	
	//constructor
	public EventLinkedList() {
		this.head = null;
		this.size = 0;
	}
	
	//getter
	public static int getSize() {
		return size;
	}
	
	//add in function 
	public void addinOrder (Event eventToAdd) {
		//varables
		Node previous = null;
		Node current = head;
		boolean larger = false;
		
		//check if found
		while(current != null && !larger) {
			
			int compare = current.event.compareTo(eventToAdd);
			
			if(compare == 1){
				larger = true;
			}
			
			else {
				previous = current;
				current = current.next;
			}
		}
		
		//new node
		Node node = new Node(eventToAdd);
		
		//add new node
		if (previous == null) { 
			head = node;
			size++;
        } 
        else { 
            previous.next = node;
            size++;
        }
		
		//add
		node.next = current;
		
	}
	
	public void removeAndReplaceEvent(Event replacementEvent) {
		//variable
		Node previous = null;
		Node current = head;
		
		boolean found = false;
		
		//find 
		while (current != null && !found) {
			if (current.event.getTime() == replacementEvent.getTime()) {
				found = true;   
			}
			else {
				previous = current;
				current = current.next;
			}
		}
		
		//if found replace with new event 
		if(found) {
			current = null;
			Node replacement = new Node(replacementEvent);
			previous = replacement;
		}
		
	}
	
	public Event removeEvent() {
		
		//if empty
		if (head == null) {
            return null;
		}
		
        //node
        Node temp = head;
        
        //variables
        String venue = head.event.getVenue();
        String type = head.event.getType();
        int time = head.event.getTime();
        String name = head.event.getName();
        
        //new event
        Event tempEvent = new Event(venue, type, time, name);
        
        //add new head
        head = head.next;
        //return event
        return tempEvent;
	}
	
	public void printList() {
		//node
		Node current = head;
		
		System.out.println("\nOlimpic Events\n");
		System.out.println("Time \tType \t\t Name \t\t\tVenue");
		
		//node by node
		while (current != null) { 
			System.out.println(current.event.toString());
			current = current.next;
		}
		
		
	}
	
	//node class
	private class Node{
		//variables
		private Event event;
		Node next;
		
		//constructor
		public Node(Event event) {
			this.event = event;
			this.next = null;
		}
	}
}

class DoubleLinkedList{
	//variable
	private Node head;
	private Node tail;
	
	//constructor
	public DoubleLinkedList(){
		this.head = null;
		this.tail = null;
	}
	
	public void build (EventLinkedList EventList, Event eventToAdd) {
		
		Node node = new Node(eventToAdd);
		
		if(head == null) {  
            head = tail = node;  
            //head's previous will be null  
            head.previous = null;  
            //tail's next will be null  
            tail.next = null;  
        }  
        else {  
            //add node to the end of list. tail->next set to node  
            tail.next = node;  
            //node->previous set to tail  
            node.previous = tail;  
            //node becomes new tail  
            tail = node;  
            //tail's next point to null  
            tail.next = null;  
        }
	}
	
	public void printNodes() {  
        //Node current will point to head  
        Node current = head;  
        if(head == null) {  
            System.out.println("Doubly linked list is empty");  
            return;  
        }  
        System.out.println("\nOlympic Events Double Linked List");  
        while(current != null) {  
            //Print each node and then go to next.  
            System.out.println(current.event + " ");  
            current = current.next;  
        }  
    } 
	
	//node class
	class Node{ 
		//variables
		Event event;  
		Node previous;  
		Node next;  
	   
		//constructor
		public Node(Event event) {  
			this.event = event;
		}  
	}
}