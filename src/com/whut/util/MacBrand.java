package com.whut.util;

public class MacBrand {
	
	
	
	public int getCompany(String clientName) {
		// TODO Auto-generated method stub

		String mac = clientName.substring(0, 8).replace(":", "").toUpperCase();
		String[] apple = { "00CDFE", "18AF61", "CC4463", "6C72E7", "60FEC5",
				"00A040", "000D93", "7CF05F", "A4B197", "0C74C2", "403004",
				"4860BC", "50EAD6", "28E02C", "60C547", "7C11BE", "003EE1",
				"44D884", "001CB3", "64B9E8", "34159E", "58B035", "F0B479",
				"109ADD", "40A6D9", "087402", "285AEB", "28F076", "EC852F",
				"286ABA", "705681", "7CD1C3", "F0DCE2", "B065BD", "A82066",
				"BC6778", "68967B", "848506", "B4F0AB", "10DDB1", "04F7E4",
				"34C059", "F0D1A9", "BC3BAF", "D0E140", "F832E4 ", "8C7C92",
				"7831C1", "F437B7" };

		String[] xiaomi = { "F8A45F", "8CBEBE", "640980", "98FAE3", "185936",
				"9C99A0", "14F65A", "0C1DAF", "28E31F", "F0B429", "D4970B",
				"F48B32", "ACF7F3", "009EC8", "7C1DD9", "A086C6", "584498",
				"FC64BA", "C46AB7", "68DFDD", "64B473", "7451BA", "3480B3",
				"2082C0" };

		String[] sony = { "AC9B0A", "BC6E64", "A0E453", "1C7B21", "709E29",
				"00EB2D", "205476", "303926", "B8F934", "FC0FE6", "6C23B9",
				"58170C", "A8E3EE", "2421AB", "00219E", "001FA7", "001E45",
				"001813", "001315", "0013A9", "4040A7", "40B837", "C43ABE",
				"307512", "4C21D0", "94CE2C", "D05162", "5453ED" };
		for (int i = 0; i < apple.length; i++) {
			if (mac.equals(apple[i]))
				return 0;
		}
		for (int i = 0; i < xiaomi.length; i++) {
			if (mac.equals(xiaomi[i]))
				return 2;
		}
		for (int i = 0; i < sony.length; i++) {
			if (mac.equals(sony[i]))
				return 3;
		}
		return -1;
	}

}
