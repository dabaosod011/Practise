package com.markeloff.demo.java8lambdas.ch3_stream;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import static org.junit.Assert.assertEquals;
import com.markeloff.demo.java8lambdas.data.Artist;

public class LondonArtist {
	public static void main(String[] args) {
		String BeyondMembers[] = { "JiaJu Huang", "JiaQiang Huang", "GuanZhong Huang", "ShiRong Ye" };
		String BeatlesMembers[] = { "John Lennon" };
		String FakeMembers[] = { "Justin" };

		List<Artist> allArtists = Arrays.asList(
				new Artist("The Beyond", new HashSet<String>(Arrays.asList(BeyondMembers)), "HongKong"),
				new Artist("The Beatles", new HashSet<String>(Arrays.asList(BeatlesMembers)), "Livepool"),
				new Artist("The Fake", new HashSet<String>(Arrays.asList(FakeMembers)), "London")
				);

		//	Traditional way
		long count = 0;
		for (Artist artist : allArtists) {
			if (artist.origin.equals("London")) {
				count++;
			}
		}
		assertEquals(1, count);
		
		//	Java 8 Lambda way
		count = allArtists.stream().filter(artist -> {
			System.out.println(artist.name + " is from " + artist.origin);
			return artist.origin.equals("London");
		}).count();
		assertEquals(1, count);
	}
}
