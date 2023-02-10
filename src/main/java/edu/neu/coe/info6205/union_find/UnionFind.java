package edu.neu.coe.info6205.union_find;

import java.util.Random;

public class UnionFind {

	public static void main(String[] args) {
		int[] numberOfSites = new int[] {500,1000,2000,4000,8000,16000};
		for (int runs=1; runs <=5; runs++) {
			System.out.printf("Runs: %d%n", runs);
			for (int n: numberOfSites) {
				System.out.printf("Sites: %d%nConnections: %d%n", n, count(n));
			}
		}
	}
	
	public static int count(int n) {
		int connections = 0;
		Random random = new Random();
		UF_HWQUPC uf = new UF_HWQUPC(n, true);	
		while (uf.components() > 1) {
			int site1 = random.nextInt(n);
			int site2 = random.nextInt(n);
			if (!uf.connected(site1, site2)) {
				uf.union(site1, site2);
			}
			connections++;
		}
		return connections;
	}

}
