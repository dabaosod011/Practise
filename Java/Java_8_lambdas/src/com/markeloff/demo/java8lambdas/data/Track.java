package com.markeloff.demo.java8lambdas.data;

public class Track {
	public String name = "";
	public int length = 0;

	public Track() {
		this.name = "notrack";
		this.length = 0;
	}

	public Track(String name, int length) {
		this.name = name;
		this.length = length;
	}
}
