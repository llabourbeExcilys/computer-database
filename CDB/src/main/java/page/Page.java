package page;

import java.util.List;

public class Page {

	private int nbByPage;

	public Page(int nbByPage) {
		super();
		this.nbByPage = nbByPage;
	}
	
	//page start at 1
	public <T> void show (List<T> list, int pageToShow) {
		
		System.out.println("Displaying page n°"+pageToShow);
		
		int i = 0;
		int currentPage = 1;
		for(T t : list) {
			i++;
			if(i%nbByPage==0) {
				currentPage++;
				i=0;
			}
			
			if(currentPage == pageToShow) 
				System.out.println(t);
			
			if(currentPage > pageToShow)
				return;
		}
	}
	
	
	// GETTER AND SETTER
	public int getNbByPage() {return nbByPage;}
	public void setNbByPage(int nbByPage) {	this.nbByPage = nbByPage;}
	
}
