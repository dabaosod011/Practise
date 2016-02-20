package com.markeloff.demo.java8lambdas.ch3_stream;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommonOps {
	public static void main(String[] args) {
		List<String> collected = Stream.of("a", "b", "c").collect(Collectors.toList());
		assertEquals(Arrays.asList("a", "b", "c"), collected);

		List<String> collected2 = Stream.of("hello", "world")
				.map(string -> string.toUpperCase())
				.collect(Collectors.toList());
		assertEquals(Arrays.asList("HELLO", "WORLD"), collected2);

		List<String> beginningWithNumbers = Stream.of("a", "1abc", "abc1")
				.filter(value -> value.charAt(0) <= '9' && value.charAt(0) >= '0')
				.collect(Collectors.toList());
		assertEquals(Arrays.asList("1abc"), beginningWithNumbers);
	}
}
