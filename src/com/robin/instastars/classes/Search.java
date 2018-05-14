package com.robin.instastars.classes;

import java.util.ArrayList;

public class Search {
	private String keyword;
	private boolean result;
	private ArrayList<Actor_Class>actors;
	public ArrayList<Actor_Class> getActors() {
		return actors;
	}
	public void setActors(ArrayList<Actor_Class> actors) {
		this.actors = actors;
	}
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public ArrayList<Actor_Class> getindexofsearch(int indexofsearchfile)
	{
		ArrayList<Actor_Class> actors=new ArrayList<Actor_Class>();
		for(int ii=0;ii<this.actors.size();ii++)
		{
			if(this.actors.get(ii).getFName().contains(keyword)||this.actors.get(ii).getLName().contains(keyword))
				actors.add(this.actors.get(ii));
		}
		return actors;
	}
	public ArrayList<Actor_Class> getindexofsearchall(ArrayList<Actor_Class>allactors)
	{
		for(int ii=0;ii<this.actors.size();ii++)
		{
			if(this.actors.get(ii).getFName().contains(keyword)||this.actors.get(ii).getLName().contains(keyword))
				allactors.add(this.actors.get(ii));
		}
		return allactors;
	}
}
