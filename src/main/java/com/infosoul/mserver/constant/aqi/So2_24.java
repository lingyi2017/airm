package com.infosoul.mserver.constant.aqi;

public enum So2_24 {

	c0(0), c1(50), c2(150), c3(475), c4(800), c5(1600), c6(2100), c7(2620);

	private final int index;

	So2_24(int index) {
		this.index = index;
	}

	public int getValue() {
		return index;
	}

	public String getCls(int value) {
		String cls = null;
		if (value == So2_24.c0.getValue()) {
			cls = So2_24.c0.name();
		} else if (value == So2_24.c1.getValue()) {
			cls = So2_24.c1.name();
		} else if (value == So2_24.c2.getValue()) {
			cls = So2_24.c2.name();
		} else if (value == So2_24.c3.getValue()) {
			cls = So2_24.c3.name();
		} else if (value == So2_24.c4.getValue()) {
			cls = So2_24.c4.name();
		} else if (value == So2_24.c5.getValue()) {
			cls = So2_24.c5.name();
		} else if (value == So2_24.c6.getValue()) {
			cls = So2_24.c6.name();
		} else if (value == So2_24.c7.getValue()) {
			cls = So2_24.c7.name();
		}
		return cls;
	}

	public int getBpH(float Cp) {
		int bpH = -1;
		if (Cp < So2_24.c1.getValue() || Cp == So2_24.c1.getValue()) {
			bpH = So2_24.c1.getValue();
		} else if (Cp < So2_24.c2.getValue() || Cp == So2_24.c2.getValue()) {
			bpH = So2_24.c2.getValue();
		} else if (Cp < So2_24.c3.getValue() || Cp == So2_24.c3.getValue()) {
			bpH = So2_24.c3.getValue();
		} else if (Cp < So2_24.c4.getValue() || Cp == So2_24.c4.getValue()) {
			bpH = So2_24.c4.getValue();
		} else if (Cp < So2_24.c5.getValue() || Cp == So2_24.c5.getValue()) {
			bpH = So2_24.c5.getValue();
		} else if (Cp < So2_24.c6.getValue() || Cp == So2_24.c6.getValue()) {
			bpH = So2_24.c6.getValue();
		} else if (Cp < So2_24.c7.getValue() || Cp == So2_24.c7.getValue()) {
			bpH = So2_24.c7.getValue();
		}
		return bpH;
	}

	public int getBpL(float Cp) {
		int bpH = -1;
		if (Cp < So2_24.c1.getValue() || Cp == So2_24.c1.getValue()) {
			bpH = So2_24.c0.getValue();
		} else if (Cp < So2_24.c2.getValue() || Cp == So2_24.c2.getValue()) {
			bpH = So2_24.c1.getValue();
		} else if (Cp < So2_24.c3.getValue() || Cp == So2_24.c3.getValue()) {
			bpH = So2_24.c2.getValue();
		} else if (Cp < So2_24.c4.getValue() || Cp == So2_24.c4.getValue()) {
			bpH = So2_24.c3.getValue();
		} else if (Cp < So2_24.c5.getValue() || Cp == So2_24.c5.getValue()) {
			bpH = So2_24.c4.getValue();
		} else if (Cp < So2_24.c6.getValue() || Cp == So2_24.c6.getValue()) {
			bpH = So2_24.c5.getValue();
		} else if (Cp < So2_24.c7.getValue() || Cp == So2_24.c7.getValue()) {
			bpH = So2_24.c6.getValue();
		}
		return bpH;
	}

	public int calcIAqiH(int bpH) {
		int iAqiH = IAQI.valueOf(So2_24.c0.getCls(bpH)).getValue();
		return iAqiH;
	}

}
