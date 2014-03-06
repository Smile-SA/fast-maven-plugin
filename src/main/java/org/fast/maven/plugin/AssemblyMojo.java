/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.fast.maven.plugin;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.http.ProtocolVersion;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.message.BasicRequestLine;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.esigate.Driver;
import org.esigate.HttpErrorPage;
import org.esigate.Renderer;
import org.esigate.aggregator.AggregateRenderer;
import org.esigate.esi.EsiRenderer;

/**
 * Generate a set of HTML pages with reusable components found in modules folder
 * 
 * @goal assembly
 * @phase generate-resources
 * 
 * @author Alexis Thaveau
 * @author Nicolas Richeton
 */
public class AssemblyMojo extends AbstractMojo {
	/**
	 * Filter page to generate
	 */
	private static final IOFileFilter PAGES_TO_GENERATE_FILTER = new SuffixFileFilter(".html");
	/**
	 * Filter components to configure
	 */
	private static final IOFileFilter COMPONENTS_TO_CONFIGURE = new SuffixFileFilter(".html");
	/**
	 * The directory containing pages to generate.
	 * 
	 * @parameter property="project.build.outputDirectory/pages"
	 * @required
	 */
	private File pagesDirectory;
	/**
	 * The directory containing template and block.
	 * 
	 * @parameter property="project.build.outputDirectory/modules"
	 * @required
	 */
	private File modulesDirectory;
	/**
	 * The output directory for assembly result.
	 * 
	 * @parameter property="project.build.directory/generated-html"
	 * @required
	 */
	private File outputDirectory;
	/**
	 * Pages and modules charset to use.
	 * 
	 * @parameter property="charset" default-value="UTF-8"
	 */
	private String charset;

    /**
     * Thread pool size
     *
     * @parameter property="threads" default-value=1
     */
    private int threads;

	/* package */String getCharset() {
		return charset;
	}

    /**
     * ExecutorService to parallelize some tasks
     */
    private ExecutorService executor;

    /**
     * tasks
     */
    private List<Future> tasks = new ArrayList<Future>();

	/**
	 * @throws MojoExecutionException
	 *             thrown if modules are not found
	 */
	public void execute() throws MojoExecutionException {
		Properties prop = new Properties();
		prop.put("remoteUrlBase", outputDirectory.getAbsolutePath() + "/modules");
		prop.put("uriEncoding", charset);
		StaticDriver driver = new StaticDriver("modules", prop);
		try {
			checkStructure();
			init(driver);
            this.executor = Executors.newFixedThreadPool(this.threads);
			assemblePages(driver);
			copyStaticResources();

            this.executor.shutdown();
            this.executor.awaitTermination(10, TimeUnit.MINUTES);
            for(Future task : tasks){
                task.get();
            }

		} catch (HttpErrorPage e) {
			throw new MojoExecutionException("Error", e);
		} catch (IOException e) {
			throw new MojoExecutionException("Error", e);
		}catch(InterruptedException e){
            throw new MojoExecutionException("Error", e);
        } catch (ExecutionException e) {
            throw new MojoExecutionException("Error", e);
        }
    }

	/**
	 * Check directory structure
	 */
	private void checkStructure() throws MojoExecutionException {
		if (!this.modulesDirectory.exists()) {
			throw new MojoExecutionException("Directory modules not found [" + modulesDirectory.getAbsolutePath() + "]");
		} else if (!this.pagesDirectory.exists()) {
			throw new MojoExecutionException("Directory pages not found [" + pagesDirectory.getAbsolutePath() + "]");
		}
	}

	/**
	 * Init driver
	 * 
	 * @param driver
	 * @throws IOException
	 */
	private void init(StaticDriver driver) throws IOException {
		getLog().info("Initialize driver with resources in folder " + modulesDirectory.getPath());
		@SuppressWarnings("rawtypes")
		Collection files = FileUtils.listFiles(this.modulesDirectory, COMPONENTS_TO_CONFIGURE, FileFilterUtils.trueFileFilter());
		for (Object file : files) {
			File source = (File) file;
			String fileName = getRelativePath(modulesDirectory, source);
			String content = FileUtils.readFileToString(source, charset);
			driver.addResource(fileName, content, charset);
			getLog().debug("Add resource " + fileName + " charset=" + charset);
		}
	}

	/**
	 * Copie les resources du repertoire static dans generated-html
	 * 
	 * @throws MojoExecutionException
	 */
	private void copyStaticResources() throws IOException {
		getLog().info("Copy static resources from " + pagesDirectory.getPath() + " to " + outputDirectory.getPath());
		FileUtils.copyDirectory(pagesDirectory, outputDirectory, new NotFileFilter(COMPONENTS_TO_CONFIGURE), true);
	}

	/**
	 * Process pages
	 * 
	 * @param driver
	 * @throws IOException
	 * @throws HttpErrorPage
	 */
    private void assemblePages(Driver driver) throws IOException, HttpErrorPage {
        getLog().info("Assemble pages");
        // Find all html page to render
        @SuppressWarnings("rawtypes")
        Collection files = FileUtils.listFiles(pagesDirectory, PAGES_TO_GENERATE_FILTER, FileFilterUtils.trueFileFilter());


        final File output = this.outputDirectory;
        for (final Object ofilename : files) {
            Future task = this.executor.submit(new Runnable() {
                @Override
                public void run() {
                    final List<Renderer> renderers = new ArrayList<Renderer>();
                    renderers.add(new AggregateRenderer());
                    renderers.add(new EsiRenderer());


                    File filePage = (File) ofilename;
                    String page = getRelativePath(pagesDirectory, filePage);
                    try {
                        String content = FileUtils.readFileToString(filePage, charset);
                        BasicHttpEntityEnclosingRequest request = new BasicHttpEntityEnclosingRequest(new BasicRequestLine("GET", page, new ProtocolVersion("HTTP", 1, 1)));
                        for (Renderer renderer : renderers) {
                            StringWriter stringWriter = new StringWriter();
                            renderer.render(request, content, stringWriter);
                            content = stringWriter.toString();
                        }
                        String result = content.replaceAll("<!--#\\$", "<!--\\$");
                        File file = new File(output + "/" + page);
                        FileUtils.writeStringToFile(file, result, charset);
                    } catch (IOException ioe) {
                        throw new RuntimeException(ioe);
                    } catch (HttpErrorPage ex) {
                        throw new RuntimeException(ex);
                    }

                }
            });
            tasks.add(task);
        }

    }

	/**
	 * Return relative path
	 * 
	 * @param directory
	 * @param file
	 * @return
	 */
	private String getRelativePath(File directory, File file) {
		return directory.toURI().relativize(file.toURI()).getPath();
	}
}
