package com.markeloff.demo.java8lambdas.data;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Album {
	public String name = "";
	public ArrayList<Track> tracks;
	public ArrayList<Artist> musicians;
	
	public Album(String name, ArrayList<Track> tracks, ArrayList<Artist> musicians){
		this.name = name;
		this.tracks = tracks;
		this.musicians = musicians;
	}
	
	public Stream<Artist> getMusicians(){
		return musicians.stream();
	}
}


