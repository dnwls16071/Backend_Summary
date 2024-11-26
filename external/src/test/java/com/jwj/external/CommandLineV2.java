package com.jwj.external;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.DefaultApplicationArguments;

import java.util.List;
import java.util.Set;

@Slf4j
public class CommandLineV2 {

	public static void main(String[] args) {
		for (String arg : args) {
			log.info("arg {}", arg);
		}

		DefaultApplicationArguments defaultApplicationArguments = new DefaultApplicationArguments(args);
		log.info("SourceArgs = {}", List.of(defaultApplicationArguments.getSourceArgs()));
		log.info("NonOptionArgs = {}", defaultApplicationArguments.getNonOptionArgs());
		log.info("OptionNames = {}", defaultApplicationArguments.getOptionNames());

		Set<String> optionNames = defaultApplicationArguments.getOptionNames();
		for (String optionName : optionNames) {
			log.info("option args {}={}", optionName, defaultApplicationArguments.getOptionValues(optionName));
		}
		List<String> url = defaultApplicationArguments.getOptionValues("url");
		List<String> username = defaultApplicationArguments.getOptionValues("username");
		List<String> password = defaultApplicationArguments.getOptionValues("password");
		List<String> mode = defaultApplicationArguments.getOptionValues("mode");
		log.info("url={}", url);
		log.info("username={}", username);
		log.info("password={}", password);
		log.info("mode={}", mode);
	}
}
