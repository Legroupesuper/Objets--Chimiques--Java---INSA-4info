/*Copyright (C) 2012 Andréolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

	This file is part of ChemicalLibSuper.
	ChemicalLibSuper is free software: you can redistribute it and/or modify
	it under the terms of the GNU Lesser General Public License as published by
	the Free Software Foundation, either version 3 of the License, or 
	(at your option) any later version.
	ChemicalLibSuper is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU Lesser General Public License for more details.
	You should have received a copy of the GNU Lesser General Public License
	along with ChemicalLibSuper.  If not, see <http://www.gnu.org/licenses/>
*/
	package org.chemicalmozart.model.implementations.factory;
import fr.insa.rennes.info.chemical.backend.Solution;import org.chemicalmozart.model.implementations.rythme.RythmPattern;
import org.chemicalmozart.model.implementations.solutionindentification.RythmePull;
import org.chemicalmozart.model.interfaces.factory.MozartSolutionFactory;
import fr.insa.rennes.info.chemical.backend.Solution;

import org.chemicalmozart.model.implementations.rythme.QQQQ4;
import org.chemicalmozart.model.implementations.rythme.EEQEEQ4;
import org.chemicalmozart.model.implementations.rythme.HH4;
import org.chemicalmozart.model.implementations.rythme.W4;
import org.chemicalmozart.model.implementations.rythme.SSSSEESSSSEE4;
import org.chemicalmozart.model.implementations.rythme.SSESSESSESSE4;
import org.chemicalmozart.model.implementations.rythme.SSEQQEE4;
import org.chemicalmozart.model.implementations.rythme.QSSEQQ4;
import org.chemicalmozart.model.implementations.rythme.QQSSEQ4;
import org.chemicalmozart.model.implementations.rythme.QEEESSQ4;
import org.chemicalmozart.model.implementations.rythme.ESSESSQESS4;
import org.chemicalmozart.model.implementations.rythme.EESSEQSSE4;
import org.chemicalmozart.model.implementations.rythme.SSSSEE2;
import org.chemicalmozart.model.implementations.rythme.EEESS2;
import org.chemicalmozart.model.implementations.rythme.QSSSS2;
import org.chemicalmozart.model.implementations.rythme.QSSE2;
import org.chemicalmozart.model.implementations.rythme.QESS2;
import org.chemicalmozart.model.implementations.rythme.SSEQ2;
import org.chemicalmozart.model.implementations.rythme.SSQE2;
import org.chemicalmozart.model.implementations.rythme.ESSQ2;
import org.chemicalmozart.model.implementations.rythme.ESSESS2;
import org.chemicalmozart.model.implementations.rythme.ESSSSE2;
import org.chemicalmozart.model.implementations.rythme.EEQ2;
import org.chemicalmozart.model.implementations.rythme.EEQQQ4;
import org.chemicalmozart.model.implementations.rythme.EQE2;
import org.chemicalmozart.model.implementations.rythme.QEE2;
import org.chemicalmozart.model.implementations.rythme.QEEQQ4;
import org.chemicalmozart.model.implementations.rythme.QQ2;
import org.chemicalmozart.model.implementations.rythme.QQEEQ4;
import org.chemicalmozart.model.implementations.rythme.QQQEE4;
import org.chemicalmozart.model.implementations.rythme.EQD2;
import org.chemicalmozart.model.implementations.rythme.QDE2;
import org.chemicalmozart.model.implementations.rythme.EDSEE2;
import org.chemicalmozart.model.implementations.rythme.SEDEDS2;
import org.chemicalmozart.model.implementations.rythme.SSSSSSSSSSSSSSSS4;
import org.chemicalmozart.model.implementations.rythme.QDEQDE4;
import org.chemicalmozart.model.implementations.rythme.EQDEQD4;
import org.chemicalmozart.model.implementations.rythme.SEDSSEEEQ4;
import org.chemicalmozart.model.implementations.rythme.ESSESEDEQ4;
import org.chemicalmozart.model.implementations.rythme.QEEQDE4;
import org.chemicalmozart.model.implementations.rythme.HQDE4;
import org.chemicalmozart.model.implementations.rythme.EQDH4;
public class MozartSolutionFactoryImpl implements MozartSolutionFactory{

	public Solution createRythmicPull() {
		Solution result = new Solution();
		result.add(new RythmePull());
		QQQQ4 qqqq4 = new QQQQ4();
		result.add(new RythmPattern(qqqq4.getDuration(), qqqq4.getListNotes()));

		EEQEEQ4 eeqeeq4 = new EEQEEQ4();
		result.add(new RythmPattern(eeqeeq4.getDuration(), eeqeeq4.getListNotes()));

		HH4 hh4 = new HH4();
		result.add(new RythmPattern(hh4.getDuration(), hh4.getListNotes()));

		W4 w4 = new W4();
		result.add(new RythmPattern(w4.getDuration(), w4.getListNotes()));

		SSSSEESSSSEE4 sssseessssee4 = new SSSSEESSSSEE4();
		result.add(new RythmPattern(sssseessssee4.getDuration(), sssseessssee4.getListNotes()));

		SSESSESSESSE4 ssessessesse4 = new SSESSESSESSE4();
		result.add(new RythmPattern(ssessessesse4.getDuration(), ssessessesse4.getListNotes()));

		SSEQQEE4 sseqqee4 = new SSEQQEE4();
		result.add(new RythmPattern(sseqqee4.getDuration(), sseqqee4.getListNotes()));

		QSSEQQ4 qsseqq4 = new QSSEQQ4();
		result.add(new RythmPattern(qsseqq4.getDuration(), qsseqq4.getListNotes()));

		QQSSEQ4 qqsseq4 = new QQSSEQ4();
		result.add(new RythmPattern(qqsseq4.getDuration(), qqsseq4.getListNotes()));

		QEEESSQ4 qeeessq4 = new QEEESSQ4();
		result.add(new RythmPattern(qeeessq4.getDuration(), qeeessq4.getListNotes()));

		ESSESSQESS4 essessqess4 = new ESSESSQESS4();
		result.add(new RythmPattern(essessqess4.getDuration(), essessqess4.getListNotes()));

		EESSEQSSE4 eesseqsse4 = new EESSEQSSE4();
		result.add(new RythmPattern(eesseqsse4.getDuration(), eesseqsse4.getListNotes()));

		SSSSEE2 ssssee2 = new SSSSEE2();
		result.add(new RythmPattern(ssssee2.getDuration(), ssssee2.getListNotes()));

		EEESS2 eeess2 = new EEESS2();
		result.add(new RythmPattern(eeess2.getDuration(), eeess2.getListNotes()));

		QSSSS2 qssss2 = new QSSSS2();
		result.add(new RythmPattern(qssss2.getDuration(), qssss2.getListNotes()));

		QSSE2 qsse2 = new QSSE2();
		result.add(new RythmPattern(qsse2.getDuration(), qsse2.getListNotes()));

		QESS2 qess2 = new QESS2();
		result.add(new RythmPattern(qess2.getDuration(), qess2.getListNotes()));

		SSEQ2 sseq2 = new SSEQ2();
		result.add(new RythmPattern(sseq2.getDuration(), sseq2.getListNotes()));

		SSQE2 ssqe2 = new SSQE2();
		result.add(new RythmPattern(ssqe2.getDuration(), ssqe2.getListNotes()));

		ESSQ2 essq2 = new ESSQ2();
		result.add(new RythmPattern(essq2.getDuration(), essq2.getListNotes()));

		ESSESS2 essess2 = new ESSESS2();
		result.add(new RythmPattern(essess2.getDuration(), essess2.getListNotes()));

		ESSSSE2 esssse2 = new ESSSSE2();
		result.add(new RythmPattern(esssse2.getDuration(), esssse2.getListNotes()));

		EEQ2 eeq2 = new EEQ2();
		result.add(new RythmPattern(eeq2.getDuration(), eeq2.getListNotes()));

		EEQQQ4 eeqqq4 = new EEQQQ4();
		result.add(new RythmPattern(eeqqq4.getDuration(), eeqqq4.getListNotes()));

		EQE2 eqe2 = new EQE2();
		result.add(new RythmPattern(eqe2.getDuration(), eqe2.getListNotes()));

		QEE2 qee2 = new QEE2();
		result.add(new RythmPattern(qee2.getDuration(), qee2.getListNotes()));

		QEEQQ4 qeeqq4 = new QEEQQ4();
		result.add(new RythmPattern(qeeqq4.getDuration(), qeeqq4.getListNotes()));

		QQ2 qq2 = new QQ2();
		result.add(new RythmPattern(qq2.getDuration(), qq2.getListNotes()));

		QQEEQ4 qqeeq4 = new QQEEQ4();
		result.add(new RythmPattern(qqeeq4.getDuration(), qqeeq4.getListNotes()));

		QQQEE4 qqqee4 = new QQQEE4();
		result.add(new RythmPattern(qqqee4.getDuration(), qqqee4.getListNotes()));

		EQD2 eqd2 = new EQD2();
		result.add(new RythmPattern(eqd2.getDuration(), eqd2.getListNotes()));

		QDE2 qde2 = new QDE2();
		result.add(new RythmPattern(qde2.getDuration(), qde2.getListNotes()));

		EDSEE2 edsee2 = new EDSEE2();
		result.add(new RythmPattern(edsee2.getDuration(), edsee2.getListNotes()));

		SEDEDS2 sededs2 = new SEDEDS2();
		result.add(new RythmPattern(sededs2.getDuration(), sededs2.getListNotes()));

		SSSSSSSSSSSSSSSS4 ssssssssssssssss4 = new SSSSSSSSSSSSSSSS4();
		result.add(new RythmPattern(ssssssssssssssss4.getDuration(), ssssssssssssssss4.getListNotes()));

		QDEQDE4 qdeqde4 = new QDEQDE4();
		result.add(new RythmPattern(qdeqde4.getDuration(), qdeqde4.getListNotes()));

		EQDEQD4 eqdeqd4 = new EQDEQD4();
		result.add(new RythmPattern(eqdeqd4.getDuration(), eqdeqd4.getListNotes()));

		SEDSSEEEQ4 sedsseeeq4 = new SEDSSEEEQ4();
		result.add(new RythmPattern(sedsseeeq4.getDuration(), sedsseeeq4.getListNotes()));

		ESSESEDEQ4 essesedeq4 = new ESSESEDEQ4();
		result.add(new RythmPattern(essesedeq4.getDuration(), essesedeq4.getListNotes()));

		QEEQDE4 qeeqde4 = new QEEQDE4();
		result.add(new RythmPattern(qeeqde4.getDuration(), qeeqde4.getListNotes()));

		HQDE4 hqde4 = new HQDE4();
		result.add(new RythmPattern(hqde4.getDuration(), hqde4.getListNotes()));

		EQDH4 eqdh4 = new EQDH4();
		result.add(new RythmPattern(eqdh4.getDuration(), eqdh4.getListNotes()));

		return result;
	}
}