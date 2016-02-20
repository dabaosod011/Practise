package com.markeloff.demo.java8lambdas.data;

import java.util.HashSet;

public class Artist {
	public String name = "";
	public HashSet<String> members;
	public String origin = "";
	
	public Artist(){
		this.name = "noname";
		this.members = null;
		this.origin = "noorigin";
	}
	
	public Artist(String name, HashSet members, String origin){
		this.name = name;
		this.members = members;
		this.origin = origin;
	}
}
