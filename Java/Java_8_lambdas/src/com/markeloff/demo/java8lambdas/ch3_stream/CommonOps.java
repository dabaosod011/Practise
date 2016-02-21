package com.markeloff.demo.java8lambdas.ch3_stream;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.markeloff.demo.java8lambdas.data.Track;

public class CommonOps {
	public static void main(String[] args) {
		List<String> collected = Stream.of("a", "b", "c").collect(Collectors.toList());
		assertEquals(Arrays.asList("a", "b", "c"), collected);

		List<String> collected2 = Stream.of("hello", "world").map(string -> string.toUpperCase())
				.collect(Collectors.toList());
		assertEquals(Arrays.asList("HELLO", "WORLD"), collected2);

		List<String> beginningWithNumbers = Stream.of("a", "1abc", "abc1")
				.filter(value -> value.charAt(0) <= '9' && value.charAt(0) >= '0').collect(Collectors.toList());
		assertEquals(Arrays.asList("1abc"), beginningWithNumbers);

		List<Integer> together = Stream.of(Arrays.asList(1, 2), Arrays.asList(3, 4))
				.flatMap(numbers -> numbers.stream()).collect(Collectors.toList());
		assertEquals(Arrays.asList(1, 2, 3, 4), together);

		List<Track> tracks = Arrays.asList(new Track("Bakai", 524), new Track("Violets for Your Furs", 378),
				new Track("Time Was", 451));
		Track shortestTrack = tracks.stream().min(Comparator.comparing(track -> track.length)).get();
		assertEquals(tracks.get(1), shortestTrack);

		int count = Stream.of(1, 2, 3).reduce(0, (acc, element) -> acc + element);
		assertEquals(6, count);

	}
}
