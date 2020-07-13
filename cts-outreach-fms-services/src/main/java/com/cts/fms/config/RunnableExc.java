package com.cts.fms.config;

import java.io.FileNotFoundException;

@FunctionalInterface
public interface RunnableExc {
	void run() throws FileNotFoundException;
}
