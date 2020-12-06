package com.bj25.study.java.junit;

import org.junit.jupiter.api.Test;

class LeanJunitTests {

	@Test
	void run() {

		int n = 3;

		while(n-- > 0) {
			System.out.println(n);
		}

		System.out.println(n);
	}

}
