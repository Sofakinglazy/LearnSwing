package model;

import java.util.*;

public class MessageServer implements Iterable<Message>{
	private Map<Integer, List<Message>> servers;
	private List<Message> selected;
	
	public MessageServer(){
		servers = new HashMap<>();
		selected = new ArrayList<>();
		
		List<Message> messages = new ArrayList<>();
		messages.add(new Message("Weather", "Today's weather sucks!"));
		messages.add(new Message("Lunch", "Lunch is not an option."));
		servers.put(0, messages);
		
		messages = new ArrayList<>();
		messages.add(new Message("Book", "Still havent finished any book."));
		messages.add(new Message("Programming", "Divide and conquer."));
		servers.put(1, messages);
	}
	
	public void setSelectedServers(Set<Integer> ids){
		selected.clear();
		for (Integer id : ids){
			if(servers.containsKey(id)){
				selected.addAll(servers.get(id));
			}
		}
	}
	
	public int getSelectedSize(){
		return selected.size();
	}

	@Override
	public Iterator<Message> iterator() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return new MessageIterator(selected);
	}
	
}

class MessageIterator implements Iterator<Message>{
	
	private Iterator<Message> iterator;

	public MessageIterator(List<Message> messages) {
		iterator = messages.iterator();
	}
	
	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public Message next() {
		return iterator.next();
	}

	@Override
	public void remove() {
		iterator.remove();
	}
	
}
