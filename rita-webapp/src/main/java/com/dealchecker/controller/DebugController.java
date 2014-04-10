package com.dealchecker.controller;

import java.text.SimpleDateFormat;

import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/debug")
public final class DebugController {

	@RequestMapping(value="/holidays", method=RequestMethod.GET)
	public String showDebug(Model model) {
		DateTime todayPlusOne = new DateTime().plusDays(1);
		DateTime sevenDaysAhead = todayPlusOne.plusDays(7);
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		
		model.addAttribute("defaultDepartureDate", df.format(todayPlusOne.toDate()));
		model.addAttribute("defaultReturnDate", df.format(sevenDaysAhead.toDate()));

		return "debug/holiday-debug-search";
	}
}
