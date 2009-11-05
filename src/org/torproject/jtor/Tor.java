package org.torproject.jtor;


import java.security.Security;
import java.util.List;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.torproject.jtor.circuits.Circuit;
import org.torproject.jtor.circuits.impl.CircuitImpl;
import org.torproject.jtor.circuits.impl.ConnectionManagerImpl;
import org.torproject.jtor.directory.Directory;
import org.torproject.jtor.directory.RouterDescriptor;
import org.torproject.jtor.directory.impl.DirectoryImpl;
import org.torproject.jtor.directory.impl.DocumentParserFactoryImpl;
import org.torproject.jtor.directory.parsing.DocumentParserFactory;

public class Tor {
	private final Directory directory;
	private final DocumentParserFactory parserFactory;
	private final ConnectionManagerImpl linkManager;
	private final Logger logger;
	
	public Tor() {
		this(new ConsoleLogger());
	}
	
	public Tor(Logger logger) {
		Security.addProvider(new BouncyCastleProvider());
		this.logger = logger;
		this.directory = new DirectoryImpl(logger);
		parserFactory = new DocumentParserFactoryImpl(logger);
		linkManager = new ConnectionManagerImpl();
	}
	
	public Circuit createCircuitFromNicknames(List<String> nicknamePath) {
		final List<RouterDescriptor> path = directory.getRouterListByNames(nicknamePath);
		return createCircuit(path);
	}
	public Circuit createCircuit(List<RouterDescriptor> path) {
		return CircuitImpl.create(linkManager, path);
	}
	
	public Logger getLogger() {
		return logger;
	}
	
	public Directory getDirectory() {
		return directory;
	}
	
	public DocumentParserFactory getDocumentParserFactory() {
		return parserFactory;
	}

	public ConnectionManagerImpl getLinkManager() {
		return linkManager;
	}
}