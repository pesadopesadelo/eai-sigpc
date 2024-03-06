package eaismart.util.generator;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Iekiny Marcel
 * Feb 15, 2021
 */
public abstract class JavaFileGenerator extends GenericFileGenerator{
	
	protected List<String> imports;
	
	public JavaFileGenerator() {}
	
	public JavaFileGenerator(String fileName, String appCode, String entityName) {
		super(fileName, appCode, entityName); 
	}
	
	@Override
	public final void generate() throws IOException { // Template Method 
		addPackageDefinition();
		addImportsDefinition();
		addAuthorNDate();
		addClassDefinition();
		save();
	}
	
	private void addImportsDefinition() {
		imports.forEach(imp->this.content.append("import " + imp + ";" + System.lineSeparator()));
		this.content.append(System.lineSeparator()); 
	}
	
	private void addAuthorNDate() {
		content.append("/**" + System.lineSeparator());
		content.append("* @author eai-smart" + System.lineSeparator()); 
		content.append("* " + new SimpleDateFormat("MMM dd, yyyy").format(new Date()) + System.lineSeparator()); 
		content.append("*/" + System.lineSeparator());
	}
	
	protected abstract void addPackageDefinition();
	protected abstract void addClassDefinition();
	
}
