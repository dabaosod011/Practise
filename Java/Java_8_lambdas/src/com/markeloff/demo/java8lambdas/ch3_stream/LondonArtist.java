package com.markeloff.demo.java8lambdas.ch3_stream;

import java.util.ArrayList;
import java.util.HashSet;

import com.markeloff.demo.java8lambdas.data.Artist;

public class LondonArtist {
	public static void main(String[] args) {
		ArrayList<Artist> allArtists = new ArrayList<Artist>();

		HashSet<String> members = new HashSet<String>();
		members.clear();
		members.add("JiaJu Huang");
		members.add("JiaQiang Huang");
		allArtists.add(new Artist("Beyond", members, "HongKong"));

		members.clear();
		members.add("John Lennon");
		allArtists.add(new Artist("The Beatles", members, "Liverpool"));

		members.clear();
		members.add("Justin");
		allArtists.add(new Artist("Fake", members, "London"));

		long count = 0;
		for (Artist artist : allArtists) {
			if (artist.origin.equals("London")) {
				count++;
			}
		}
		System.out.println("Count = " + count);

		count = allArtists.stream().filter(artist -> {
			System.out.println(artist.name + " is from " + artist.origin);
			return artist.origin.equals("London");
		}).count();
		System.out.println("Count = " + count);
	}
}
